package it.ddcompendium.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import it.ddcompendium.CharacterDetailActivity;
import it.ddcompendium.InsertCharacter;
import it.ddcompendium.R;
import it.ddcompendium.entities.Character;
import it.ddcompendium.service.responses.Status;
import it.ddcompendium.entities.User;
import it.ddcompendium.patterns.Observer;
import it.ddcompendium.recyclerview.adapters.CharactersAdapter;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.service.CharactersService;
import it.ddcompendium.service.impl.CharactersServiceImpl;

public class CharactersFragment extends Fragment implements CharactersAdapter.OnCharacterClick, View.OnClickListener, Observer<Character>, SearchView.OnQueryTextListener {
    private final String TAG = CharactersFragment.class.getSimpleName();
    // Variables
    private final List<Character> mFullList = new ArrayList<>();
    private final List<Character> mCharacters = new ArrayList<>();
    // UI Components
    private RecyclerView mRecyclerView;
    private FloatingActionButton mButton;
    private CharactersAdapter mAdapter;
    private CharactersService mService;
    private final ItemTouchHelper.SimpleCallback mHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Character character = mCharacters.get(viewHolder.getAdapterPosition());
            Log.i(TAG, "onSwiped: " + character.getId());
            mService.delete(character.getId(), new Callback<Status>() {
                @Override
                public void onSuccess(Status status) {
                    Toast.makeText(getContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
                    mFullList.remove(character);
                    mCharacters.remove(character);
                    mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                }

                @Override
                public void onFailure(Status status) {
                    Toast.makeText(getContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
                    mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
        }
    };
    private User mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_character_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mButton = view.findViewById(R.id.fab);
        mButton.setOnClickListener(this);

        mService = new CharactersServiceImpl(getContext());

        initRecyclerView();

        Activity activity = getActivity();

        if (activity != null)
            mUser = activity.getIntent().getParcelableExtra("user");

        getCharacters();
        setHasOptionsMenu(true);

        return view;
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CharactersAdapter(mCharacters, this);
        mRecyclerView.setAdapter(mAdapter);
        new ItemTouchHelper(mHelper).attachToRecyclerView(mRecyclerView);
    }

    private void getCharacters() {
        mService.getAll(mUser.getId(), new Callback<List<Character>>() {
            @Override
            public void onSuccess(List<Character> characters) {
                if (mCharacters.size() > 0) {
                    mCharacters.clear();
                    mFullList.clear();
                }
                mFullList.addAll(characters);
                mCharacters.addAll(characters);
                mAdapter.notifyItemRangeChanged(0, mCharacters.size());
            }

            @Override
            public void onFailure(Status status) {
                Toast.makeText(getContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(int position) {
        Character character = mCharacters.get(position);
        Log.i(TAG, "onClick: " + character);
        Intent intent = new Intent(getContext(), CharacterDetailActivity.class);
        intent.putExtra("character", character);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        new InsertCharacter(getContext(), this, mUser).show(getParentFragmentManager(), "create character");
    }

    @Override
    public void onUpdate(Character c) {
        mCharacters.add(c);
        mFullList.add(c);
        mAdapter.notifyItemInserted(mCharacters.size());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_search) {
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setOnQueryTextListener(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (mCharacters.size() > 0) {
            mCharacters.clear();
            mAdapter.notifyItemRangeRemoved(0, mFullList.size());
        }

        for (Character c : mFullList) {
            if (c.getName().toLowerCase().contains(newText.toLowerCase()) || c.getClasse().toLowerCase().contains(newText.toLowerCase())) {
                mCharacters.add(c);
            }
        }

        mAdapter.notifyItemRangeInserted(0, mCharacters.size());

        return false;
    }
}
