package it.ddcompendium;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.ddcompendium.entities.Spell;
import it.ddcompendium.entities.User;
import it.ddcompendium.recyclerview.adapters.UserAdapter;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.service.RecommendationService;
import it.ddcompendium.service.UsersService;
import it.ddcompendium.service.impl.RecommendationServiceImpl;
import it.ddcompendium.service.impl.UsersServiceImpl;
import it.ddcompendium.service.responses.Status;

public class ShareActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, UserAdapter.OnUserClick {
    private final List<User> mUsers = new ArrayList<>();
    private RecommendationService mServiceRecs;
    private UsersService mServiceUser;
    private UserAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private User mUser;
    private Spell mSpell;
    private boolean submitted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        mSpell = getIntent().getParcelableExtra("spell");
        mUser = getIntent().getParcelableExtra("user");

        mAdapter = new UserAdapter(mUsers, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mServiceRecs = new RecommendationServiceImpl(this);
        mServiceUser = new UsersServiceImpl(this);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        MenuItem search = menu.findItem(R.id.menu_search);
        SearchView searchView = ((SearchView) search.getActionView());
        searchView.setOnQueryTextListener(this);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        submitted = true;
        mServiceUser.findUser(query, new Callback<User>() {
            @Override
            public void onSuccess(User user) {
                if (!mUsers.isEmpty())
                    mUsers.clear();
                mUsers.add(user);
                mAdapter.notifyItemInserted(0);
                submitted = false;
            }

            @Override
            public void onFailure(Status status) {
                Toast.makeText(getApplicationContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.equals("") && !submitted) {
            mUsers.clear();
            mAdapter.notifyItemRemoved(0);
        }
        return false;
    }

    @Override
    public void onClick(int position) {
        User user = mUsers.get(position);

        mServiceRecs.add(mUser.getId(), user.getId(), mSpell.getId(), new Callback<Status>() {
            @Override
            public void onSuccess(Status status) {
                Toast.makeText(getApplicationContext(), "Spell shared successfully with " + user.getUsername(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Status status) {
                Toast.makeText(getApplicationContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}