package it.ddcompendium.service.responses;

import java.util.List;

import it.ddcompendium.entities.Spell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpellListStatus {
    private List<Spell> data;
    private Status status;
}
