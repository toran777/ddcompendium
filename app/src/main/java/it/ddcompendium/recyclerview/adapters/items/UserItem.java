package it.ddcompendium.recyclerview.adapters.items;

import it.ddcompendium.entities.User;

public class UserItem extends ListItem {
    private final User data;

    public UserItem(User data) {
        this.data = data;
    }

    public User getData() {
        return data;
    }

    @Override
    public int getType() {
        return TYPE_USER;
    }
}
