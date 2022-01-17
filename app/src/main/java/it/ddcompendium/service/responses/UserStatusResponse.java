package it.ddcompendium.service.responses;

import it.ddcompendium.entities.User;

public class UserStatusResponse {
    private User data;
    private Status status;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
