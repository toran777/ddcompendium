package it.ddcompendium.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.ddcompendium.MainActivity;
import it.ddcompendium.R;
import it.ddcompendium.SpellDetailActivity;
import it.ddcompendium.entities.Character;
import it.ddcompendium.entities.Spell;
import it.ddcompendium.entities.Status;
import it.ddcompendium.entities.User;
import it.ddcompendium.recyclerview.adapters.SpellsAdapter;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.service.SpellsService;
import it.ddcompendium.service.impl.SpellsServiceImpl;

public class SpellsFragment extends Fragment implements SpellsAdapter.OnSpellClick, SearchView.OnQueryTextListener {
    private static final String TAG = SpellsFragment.class.getSimpleName();
    // UI Components
    private RecyclerView mRecyclerView;

    // Variables
    private SpellsService mService;
    private SpellsAdapter mAdapter;
    private List<Spell> mSpells = new ArrayList<>();
    private List<Spell> mFullList = new ArrayList<>();
    private ActivityResultLauncher<Intent> mResultLauncher;
    private User mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_spells_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new SpellsAdapter(mSpells, this);
        mRecyclerView.setAdapter(mAdapter);

        mService = new SpellsServiceImpl(getContext());

        Activity activity = getActivity();

        if (activity != null)
            mUser = activity.getIntent().getParcelableExtra("user");

        getSpells();
        setHasOptionsMenu(true);

        mResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Intent data = result.getData();

            if (data != null) {
                Character character = data.getParcelableExtra("character");
                Spell spell = data.getParcelableExtra("spell");
                ((MainActivity) getActivity()).onSpellAdded(character, spell);
            }
        });

        return view;
    }

    private void getSpells() {
        mService.getAll(new Callback<List<Spell>>() {
            @Override
            public void onSuccess(List<Spell> spells) {
                if (mSpells.size() > 0) {
                    mSpells.clear();
                    mFullList.clear();
                }

                mSpells.addAll(spells);
                mFullList.addAll(spells);
                mAdapter.notifyItemRangeChanged(0, mSpells.size());
            }

            @Override
            public void onFailure(Status status) {
                Toast.makeText(getContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getContext(), SpellDetailActivity.class);
        intent.putExtra("spell", mSpells.get(position));
        intent.putExtra("user", mUser);
        mResultLauncher.launch(intent);
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
        if (mSpells.size() > 0) {
            mSpells.clear();
            mAdapter.notifyItemRangeRemoved(0, mFullList.size());
        }

        for (Spell s : mFullList) {
            if (s.getName().toLowerCase().contains(newText.toLowerCase())) {
                mSpells.add(s);
            }
        }

        mAdapter.notifyItemRangeInserted(0, mSpells.size());

        return false;
    }
}
