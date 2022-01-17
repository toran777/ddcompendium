package it.ddcompendium.recyclerview.adapters.items;

import it.ddcompendium.entities.Spell;

public class SpellItem extends ListItem {
    private final Spell data;

    public SpellItem(Spell data) {
        this.data = data;
    }

    public Spell getData() {
        return data;
    }

    @Override
    public int getType() {
        return TYPE_SPELLS;
    }
}
