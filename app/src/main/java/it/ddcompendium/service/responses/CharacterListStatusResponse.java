package it.ddcompendium.service.responses;

import java.util.List;

import it.ddcompendium.entities.Character;

public class CharacterListStatusResponse {
    private List<Character> data;
    private Status status;

    public List<Character> getData() {
        return data;
    }

    public void setData(List<Character> data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
