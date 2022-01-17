package it.ddcompendium.recyclerview.adapters;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.ddcompendium.R;
import it.ddcompendium.entities.Spell;

public class SpellsAdapter extends RecyclerView.Adapter<SpellsAdapter.Holder> {
    private final List<Spell> mSpells;
    private final OnSpellClick mOnSpellClick;

    public SpellsAdapter(List<Spell> spells, OnSpellClick onSpellClick) {
        this.mSpells = spells;
        this.mOnSpellClick = onSpellClick;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spell_item, parent, false);
        return new Holder(view, mOnSpellClick);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.name.setText(mSpells.get(position).getName());
        holder.description.setText(Html.fromHtml(mSpells.get(position).getDesc(), Html.FROM_HTML_MODE_LEGACY));
    }

    @Override
    public int getItemCount() {
        return mSpells.size();
    }

    public interface OnSpellClick {
        void onClick(int position);
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, description;
        OnSpellClick mOnSpellClick;

        public Holder(@NonNull View itemView, OnSpellClick onSpellClick) {
            super(itemView);
            name = itemView.findViewById(R.id.spellName);
            description = itemView.findViewById(R.id.spellDescription);

            mOnSpellClick = onSpellClick;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnSpellClick.onClick(getAdapterPosition());
        }
    }
}
