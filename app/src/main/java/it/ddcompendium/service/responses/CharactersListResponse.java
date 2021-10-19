package it.ddcompendium.service.responses;

import it.ddcompendium.entities.Character;
import it.ddcompendium.entities.Status;

public class CharactersListResponse {
    private Character[] characters;
    private Status status;

    public Character[] getCharacters() {
        return characters;
    }

    public void setCharacters(Character[] characters) {
        this.characters = characters;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
