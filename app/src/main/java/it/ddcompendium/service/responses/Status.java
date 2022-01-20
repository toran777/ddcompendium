package it.ddcompendium.service.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    private Integer code;
    private String message;

    public Status(String message) {
        this.message = message;
    }
}
