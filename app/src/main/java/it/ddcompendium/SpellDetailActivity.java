package it.ddcompendium;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;
import java.util.Set;

import it.ddcompendium.entities.Spell;
import it.ddcompendium.entities.User;

public class SpellDetailActivity extends AppCompatActivity {
    // UI Components
    private Toolbar mToolbar;
    private LinearLayout mLayout;

    // Variables
    private Spell mSpell;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_detail);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mLayout = findViewById(R.id.spell_content);

        mSpell = getIntent().getParcelableExtra("spell");
        mUser = getIntent().getParcelableExtra("user");

        setTitle(mSpell.getName());

        populateView();
    }

    private void populateView() {
        HashMap<String, String> values = mSpell.getValues();
        Set<String> keys = values.keySet();

        for (String s : keys) {
            TextView tvDesc = new TextView(this);
            tvDesc.setText(s);
            tvDesc.setTextSize(20);
            tvDesc.setTypeface(tvDesc.getTypeface(), Typeface.BOLD);
            mLayout.addView(tvDesc);

            TextView tvCont = new TextView(this);
            tvCont.setPadding(0, 10, 0, 0);
            tvCont.setTextSize(16);
            tvCont.setText(Html.fromHtml(values.get(s), Html.FROM_HTML_MODE_LEGACY));
            mLayout.addView(tvCont);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            new InsertSpell(this, mSpell, mUser.getId()).show(getSupportFragmentManager(), null);
        }

        return true;
    }
}