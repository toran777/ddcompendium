package it.ddcompendium.recyclerview.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.ddcompendium.R;
import it.ddcompendium.recyclerview.adapters.items.ListItem;
import it.ddcompendium.recyclerview.adapters.items.SpellItem;
import it.ddcompendium.recyclerview.adapters.items.UserItem;

public class RecommendationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ListItem> items;
    private final SpellsAdapter.OnSpellClick mOnSpellClick;

    public RecommendationAdapter(List<ListItem> items, SpellsAdapter.OnSpellClick onSpellClick) {
        this.items = items;
        this.mOnSpellClick = onSpellClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ListItem.TYPE_SPELLS) {
            View view = inflater.inflate(R.layout.spell_item, parent, false);
            return new SpellsAdapter.Holder(view, mOnSpellClick);
        } else {
            View view = inflater.inflate(R.layout.layout_header, parent, false);
            return new HeaderHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == ListItem.TYPE_USER) {
            HeaderHolder viewHolder = (HeaderHolder) holder;
            UserItem user = (UserItem) items.get(position);
            viewHolder.textView.setText(user.getData().getUsername());
        } else {
            SpellItem spell = (SpellItem) items.get(position);
            SpellsAdapter.Holder viewHolder = (SpellsAdapter.Holder) holder;
            viewHolder.description.setText(spell.getData().getDesc());
            viewHolder.name.setText(spell.getData().getName());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    private static class HeaderHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public HeaderHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.username);
        }
    }
}
