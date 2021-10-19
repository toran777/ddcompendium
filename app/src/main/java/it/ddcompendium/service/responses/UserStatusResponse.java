package it.ddcompendium.service.responses;

import it.ddcompendium.entities.Status;
import it.ddcompendium.entities.User;

public class UserStatusResponse {
    private User user;
    private Status status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
