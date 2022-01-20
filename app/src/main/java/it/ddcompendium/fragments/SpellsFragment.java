package it.ddcompendium.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.ddcompendium.R;
import it.ddcompendium.SpellDetailActivity;
import it.ddcompendium.entities.Spell;
import it.ddcompendium.service.responses.Status;
import it.ddcompendium.entities.User;
import it.ddcompendium.recyclerview.adapters.SpellsAdapter;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.service.SpellsService;
import it.ddcompendium.service.impl.SpellsServiceImpl;

public class SpellsFragment extends Fragment implements SpellsAdapter.OnSpellClick, SearchView.OnQueryTextListener {
    private final List<Spell> mSpells = new ArrayList<>();
    private final List<Spell> mCurrentList = new ArrayList<>();
    // UI Components
    private RecyclerView mRecyclerView;
    // Variables
    private SpellsService mService;
    private SpellsAdapter mAdapter;
    private User mUser;
    private int mOffset = 30;
    private boolean isInSearch = false;
    private boolean submitted = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_spells_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new SpellsAdapter(mSpells, this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && !isInSearch) {
                    mOffset += 30;
                    getSpells();
                }
            }
        });

        mService = new SpellsServiceImpl(getContext());

        Activity activity = getActivity();

        if (activity != null)
            mUser = activity.getIntent().getParcelableExtra("user");

        getSpells();
        setHasOptionsMenu(true);

        return view;
    }

    private void getSpells() {
        mService.getAll(mOffset, new Callback<List<Spell>>() {
            @Override
            public void onSuccess(List<Spell> spells) {
                mSpells.addAll(spells);
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
        startActivity(intent);
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
        isInSearch = true;
        submitted = true;
        mCurrentList.clear();
        mCurrentList.addAll(mSpells);
        mSpells.clear();
        mService.search(query, new Callback<List<Spell>>() {
            @Override
            public void onSuccess(List<Spell> spells) {
                mSpells.addAll(spells);
                mAdapter.notifyItemRangeChanged(0, mSpells.size());
            }

            @Override
            public void onFailure(Status status) {
                Toast.makeText(getContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.equals("") && submitted) {
            submitted = false;
            isInSearch = false;
            mSpells.clear();
            mSpells.addAll(mCurrentList);
            mAdapter.notifyItemRangeChanged(0, mSpells.size());
            mCurrentList.clear();
        }
        return false;
    }
}
