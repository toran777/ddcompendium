package it.ddcompendium.service.responses;

import java.util.List;

import it.ddcompendium.entities.Recommendation;

public class SuggestionListStatusResponse {
    private List<Recommendation> data;
    private Status status;

    public List<Recommendation> getData() {
        return data;
    }

    public void setData(List<Recommendation> data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
