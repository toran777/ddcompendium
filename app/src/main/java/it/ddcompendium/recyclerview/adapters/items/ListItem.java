package it.ddcompendium.recyclerview.adapters.items;

public abstract class ListItem {
    public static final int TYPE_USER = 0;
    public static final int TYPE_SPELLS = 1;

    abstract public int getType();
}
