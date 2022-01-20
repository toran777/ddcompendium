package it.ddcompendium.service.responses;

import it.ddcompendium.entities.Character;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterStatusResponse {
    private Character data;
    private Status status;
}
