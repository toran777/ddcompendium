package it.ddcompendium.recyclerview.adapters.items;

import it.ddcompendium.entities.Spell;

public class SpellItem extends ListItem {
    private final Integer id;
    private final Spell data;

    public SpellItem(Spell data, Integer id) {
        this.data = data;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Spell getData() {
        return data;
    }

    @Override
    public int getType() {
        return TYPE_SPELLS;
    }
}
