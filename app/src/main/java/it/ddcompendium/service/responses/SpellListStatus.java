package it.ddcompendium.service.responses;

import java.util.List;

import it.ddcompendium.entities.Spell;

public class SpellListStatus {
    private List<Spell> data;
    private Status status;

    public List<Spell> getData() {
        return data;
    }

    public void setData(List<Spell> data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
