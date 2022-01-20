package it.ddcompendium.recyclerview.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.ddcompendium.R;
import it.ddcompendium.entities.Character;
import it.ddcompendium.utils.Utils;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.Holder> {
    private final List<Character> characters;
    private final OnCharacterClick mOnCharacterClick;

    public CharactersAdapter(List<Character> characters, OnCharacterClick onCharacterClick) {
        this.characters = characters;
        this.mOnCharacterClick = onCharacterClick;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_item, parent, false);
        return new Holder(view, mOnCharacterClick);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.nome.setText(characters.get(position).getName());

        int resource = Utils.getResourceFromClass(characters.get(position));

        holder.image.setImageResource(resource);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public interface OnCharacterClick {
        void onClick(int position);
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView nome;
        final ImageView image;
        final OnCharacterClick mOnCharacterClick;

        public Holder(@NonNull View itemView, OnCharacterClick onCharacterClick) {
            super(itemView);
            nome = itemView.findViewById(R.id.characterName);
            image = itemView.findViewById(R.id.characterImage);

            mOnCharacterClick = onCharacterClick;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnCharacterClick.onClick(getAdapterPosition());
        }
    }
}
