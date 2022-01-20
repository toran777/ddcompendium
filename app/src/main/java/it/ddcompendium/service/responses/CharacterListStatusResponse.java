package it.ddcompendium.service.responses;

import java.util.List;

import it.ddcompendium.entities.Character;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterListStatusResponse {
    private List<Character> data;
    private Status status;
}
