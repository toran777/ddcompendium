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

import java.util.List;

import it.ddcompendium.entities.Character;
import it.ddcompendium.entities.Spell;
import it.ddcompendium.entities.Status;
import it.ddcompendium.entities.User;
import it.ddcompendium.recyclerview.adapters.SpellsAdapter;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.service.SpellsService;
import it.ddcompendium.service.impl.SpellsServiceImpl;

public class CharacterDetailActivity extends AppCompatActivity implements SpellsAdapter.OnSpellClick {
    // UI Components
    private CollapsingToolbarLayout mCtl;
    private ImageView mImage;
    private RecyclerView mRecyclerView;

    // Variables
    private User mUser;
    private SpellsAdapter mAdapter;
    private SpellsService mService;
    private Character mCharacter;
    private ItemTouchHelper.SimpleCallback mHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mService.delete(mCharacter.getId(), mCharacter.getSpells().get(viewHolder.getAdapterPosition()).getId(), new Callback<Status>() {
                @Override
                public void onSuccess(Status status) {
                    Toast.makeText(getApplicationContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
                    mCharacter.getSpells().remove(viewHolder.getAdapterPosition());
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
        mUser = getIntent().getParcelableExtra("user");
        mCtl.setTitle(mCharacter.getName());
        int resource;

        switch (mCharacter.getClasse()) {
            case "Sorcerer":
                resource = R.drawable.sorcerer;
                break;
            case "Bard":
                resource = R.drawable.bard;
                break;
            case "Cleric":
                resource = R.drawable.cleric;
                break;
            case "Barbarian":
                resource = R.drawable.barbarian;
                break;
            case "Wizard":
                resource = R.drawable.wizard;
                break;
            case "Druid":
                resource = R.drawable.druid;
                break;
            case "Paladin":
                resource = R.drawable.paladin;
                break;
            default:
                resource = 0;
        }

        mImage.setImageResource(resource);

        mService = new SpellsServiceImpl(this);

        initRecyclerView(mCharacter.getSpells());
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
        intent.putExtra("spell", mCharacter.getSpells().get(position));
        intent.putExtra("user", mUser);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("character", mCharacter);
        setResult(0, intent);
        finish();
    }
}