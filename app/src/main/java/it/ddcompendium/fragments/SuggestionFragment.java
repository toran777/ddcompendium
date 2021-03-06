package it.ddcompendium.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import it.ddcompendium.R;
import it.ddcompendium.SpellDetailActivity;
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

public class SuggestionFragment extends Fragment implements SpellsAdapter.OnSpellClick, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = SuggestionFragment.class.getSimpleName();
    private final List<ListItem> mItems = new ArrayList<>();
    // UI Components
    private RecyclerView mRecyclerView;
    // Variables
    private RecommendationService mService;
    private RecommendationAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private User mUser;
    private final ItemTouchHelper.SimpleCallback mHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            ListItem item = mItems.get(viewHolder.getAdapterPosition());
            Log.i(TAG, "onSwiped: " + viewHolder.getAdapterPosition());
            if (item.getType() == ListItem.TYPE_SPELLS) {
                SpellItem spellItem = (SpellItem) item;
                mService.delete(spellItem.getId(), new Callback<Status>() {
                    @Override
                    public void onSuccess(Status status) {
                        Toast.makeText(getContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
                        getAllRecommendations();
                    }

                    @Override
                    public void onFailure(Status status) {
                        Toast.makeText(getContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
                        mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                });
            } else {
                mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_suggestion_fragment, container, false);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new RecommendationAdapter(mItems, this);
        mRecyclerView.setAdapter(mAdapter);
        new ItemTouchHelper(mHelper).attachToRecyclerView(mRecyclerView);

        mService = new RecommendationServiceImpl(getContext());
        mSwipeRefreshLayout.setOnRefreshListener(this);

        Activity activity = getActivity();

        if (activity != null)
            mUser = activity.getIntent().getParcelableExtra("user");

        getAllRecommendations();

        setHasOptionsMenu(true);

        return view;
    }

    private List<ListItem> parseItems(List<Recommendation> recommendations) {
        List<ListItem> items = new ArrayList<>();
        User currentUser = new User();
        for (Recommendation r : recommendations) {
            if (!r.getRecommendedBy().getUsername().equals(currentUser.getUsername())) {
                UserItem item = new UserItem(r.getRecommendedBy());
                items.add(item);
                currentUser = r.getRecommendedBy();
            }

            SpellItem spellItem = new SpellItem(r.getRecommendation(), r.getId());
            items.add(spellItem);
        }

        return items;
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getContext(), SpellDetailActivity.class);
        intent.putExtra("spell", ((SpellItem) mItems.get(position)).getData());
        intent.putExtra("user", mUser);
        startActivity(intent);
    }

    public void getAllRecommendations() {
        mSwipeRefreshLayout.setRefreshing(true);
        mService.getAll(mUser, new Callback<List<Recommendation>>() {
            @Override
            public void onSuccess(List<Recommendation> recommendations) {
                if (!mItems.isEmpty())
                    mItems.clear();
                List<ListItem> items = parseItems(recommendations);
                mItems.addAll(items);
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Status status) {
                Toast.makeText(getContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem search = menu.findItem(R.id.menu_search);
        search.setVisible(false);
    }

    @Override
    public void onRefresh() {
        getAllRecommendations();
    }
}
