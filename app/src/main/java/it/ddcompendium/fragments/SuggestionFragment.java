package it.ddcompendium.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.ddcompendium.R;
import it.ddcompendium.entities.Recommendation;
import it.ddcompendium.entities.User;
import it.ddcompendium.recyclerview.adapters.RecommendationAdapter;
import it.ddcompendium.recyclerview.adapters.SpellsAdapter;
import it.ddcompendium.recyclerview.adapters.items.ListItem;
import it.ddcompendium.recyclerview.adapters.items.SpellItem;
import it.ddcompendium.recyclerview.adapters.items.UserItem;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.service.RecommendationService;
import it.ddcompendium.service.impl.RecommendationServiceImpl;
import it.ddcompendium.service.responses.Status;

public class SuggestionFragment extends Fragment implements SpellsAdapter.OnSpellClick {
    private final List<ListItem> mItems = new ArrayList<>();
    // UI Components
    private RecyclerView mRecyclerView;
    // Variables
    private RecommendationService mService;
    private RecommendationAdapter mAdapter;
    private User mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_spells_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new RecommendationAdapter(mItems);
        mRecyclerView.setAdapter(mAdapter);

        mService = new RecommendationServiceImpl(getContext());

        Activity activity = getActivity();

        if (activity != null)
            mUser = activity.getIntent().getParcelableExtra("user");

        mService.getAll(mUser, new Callback<List<Recommendation>>() {
            @Override
            public void onSuccess(List<Recommendation> recommendations) {
                if (mItems.size() > 0) {
                    mItems.clear();
                }
                List<ListItem> items = parseItems(recommendations);
                mItems.addAll(items);
                mAdapter.notifyItemRangeChanged(0, mItems.size());
            }

            @Override
            public void onFailure(Status status) {
                Toast.makeText(getContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        setHasOptionsMenu(true);

        return view;
    }

    private List<ListItem> parseItems(List<Recommendation> recommendations) {
        List<ListItem> items = new ArrayList<>();
        User currentUser = new User();
        for (Recommendation r : recommendations) {
            if (!r.getSuggestedBy().getUsername().equals(currentUser.getUsername())) {
                UserItem item = new UserItem(r.getSuggestedBy());
                items.add(item);
                currentUser = r.getSuggestedBy();
            }

            SpellItem spellItem = new SpellItem(r.getSuggested());
            items.add(spellItem);
        }

        return items;
    }

    @Override
    public void onClick(int position) {
    }
}
