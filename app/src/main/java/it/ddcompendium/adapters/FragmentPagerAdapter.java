package it.ddcompendium.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import it.ddcompendium.fragments.CharactersFragment;
import it.ddcompendium.fragments.SpellsFragment;

public class FragmentPagerAdapter extends FragmentStateAdapter {

    public FragmentPagerAdapter(FragmentActivity fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new CharactersFragment();
                break;
            case 1:
                fragment = new SpellsFragment();
                break;
        }

        assert fragment != null;
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
