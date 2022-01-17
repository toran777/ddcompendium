package it.ddcompendium.service.responses;

import it.ddcompendium.entities.Character;

public class CharacterStatusResponse {
    private Character data;
    private Status status;

    public Character getData() {
        return data;
    }

    public void setData(Character data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
