package it.ddcompendium.recyclerview.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.ddcompendium.R;
import it.ddcompendium.entities.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder> {
    private final List<User> mUsers;
    private final OnUserClick mOnUserClick;

    public UserAdapter(List<User> users, OnUserClick onUserClick) {
        this.mUsers = users;
        this.mOnUserClick = onUserClick;
    }

    @NonNull
    @Override
    public UserAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.Holder(view, mOnUserClick);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.Holder holder, int position) {
        holder.username.setText(mUsers.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public interface OnUserClick {
        void onClick(int position);
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView username;
        OnUserClick mOnUserClick;

        public Holder(@NonNull View itemView, OnUserClick onSpellClick) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            mOnUserClick = onSpellClick;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnUserClick.onClick(getAdapterPosition());
        }
    }
}
