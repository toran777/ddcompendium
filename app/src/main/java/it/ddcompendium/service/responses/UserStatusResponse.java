package it.ddcompendium.service.responses;

import it.ddcompendium.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusResponse {
    private User data;
    private Status status;
}
