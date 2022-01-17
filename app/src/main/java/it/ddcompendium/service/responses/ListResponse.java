package it.ddcompendium.service.responses;

import java.util.List;

public class ListResponse<T> {
    private List<T> data;
    private Status status;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
