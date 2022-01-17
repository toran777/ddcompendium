package it.ddcompendium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import it.ddcompendium.adapters.FragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {
    // UI Components
    private ViewPager2 mViewPager;
    private FragmentStateAdapter mAdapter;
    private TabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mAdapter = new FragmentPagerAdapter(this);

        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(mAdapter);
        mTabs = findViewById(R.id.tabLayout);

        ArrayList<String> titles = new ArrayList<>();
        titles.add("CHARACTERS");
        titles.add("SPELLS");
        titles.add("RECOMMEND");

        new TabLayoutMediator(mTabs, mViewPager, (tab, position) -> tab.setText(titles.get(position))).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem logout = menu.findItem(R.id.menu_logout);
        MenuItem search = menu.findItem(R.id.menu_search);
        search.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                logout.setVisible(false);
                mTabs.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                logout.setVisible(true);
                mTabs.setVisibility(View.VISIBLE);
                return true;
            }
        });
        SearchView searchView = ((SearchView) search.getActionView());
        searchView.setMaxWidth(Integer.MAX_VALUE);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.remove("id");
            edit.apply();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}