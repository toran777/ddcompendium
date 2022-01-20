package it.ddcompendium;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import it.ddcompendium.entities.Character;
import it.ddcompendium.entities.Spell;
import it.ddcompendium.entities.User;
import it.ddcompendium.recyclerview.adapters.SpellsAdapter;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.service.CharactersService;
import it.ddcompendium.service.SpellsService;
import it.ddcompendium.service.impl.CharactersServiceImpl;
import it.ddcompendium.service.impl.SpellsServiceImpl;
import it.ddcompendium.service.responses.Status;
import it.ddcompendium.utils.Utils;

public class CharacterDetailActivity extends AppCompatActivity implements SpellsAdapter.OnSpellClick {
    private final List<Spell> mSpells = new ArrayList<>();
    // UI Components
    private CollapsingToolbarLayout mCtl;
    private ImageView mImage;
    private RecyclerView mRecyclerView;
    // Variables
    private SpellsAdapter mAdapter;
    private SpellsService mSpellsService;
    private CharactersService mCharactersService;
    private Character mCharacter;
    private final ItemTouchHelper.SimpleCallback mHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mSpellsService.delete(mCharacter.getId(), mSpells.get(viewHolder.getAdapterPosition()).getId(), new Callback<Status>() {
                @Override
                public void onSuccess(Status status) {
                    Toast.makeText(getApplicationContext(), "Spell successfully deleted", Toast.LENGTH_SHORT).show();
                    mSpells.remove(viewHolder.getAdapterPosition());
                    mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                }

                @Override
                public void onFailure(Status status) {
                    Toast.makeText(getApplicationContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
                    mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        mImage = findViewById(R.id.characterImage);
        mCtl = findViewById(R.id.collapsingToolbarLayout);
        mRecyclerView = findViewById(R.id.recyclerView);

        mCharacter = getIntent().getParcelableExtra("character");
        mCharacter.setSpells(new ArrayList<>());
        mCtl.setTitle(mCharacter.getName());
        int resource = Utils.getResourceFromClass(mCharacter);
        mImage.setImageResource(resource);

        mSpellsService = new SpellsServiceImpl(this);
        mCharactersService = new CharactersServiceImpl(this);

        initRecyclerView(mSpells);

        mCharactersService.getOne(mCharacter.getId(), new Callback<Character>() {
            @Override
            public void onSuccess(Character character) {
                mSpells.addAll(character.getSpells());
                mAdapter.notifyItemRangeChanged(0, mSpells.size());
            }

            @Override
            public void onFailure(Status status) {
                Toast.makeText(getApplicationContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView(List<Spell> spells) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new SpellsAdapter(spells, this);
        mRecyclerView.setAdapter(mAdapter);
        new ItemTouchHelper(mHelper).attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, SpellDetailActivity.class);
        intent.putExtra("spell", mSpells.get(position));
        User user = new User();
        user.setId(mCharacter.getIdUser());
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}