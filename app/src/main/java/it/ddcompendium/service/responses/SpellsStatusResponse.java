package it.ddcompendium.service.responses;

import java.util.List;

import it.ddcompendium.entities.Spell;
import it.ddcompendium.entities.Status;

public class SpellsStatusResponse {
    private List<Spell> spells;
    private Status status;

    public List<Spell> getSpells() {
        return spells;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
