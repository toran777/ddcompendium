package it.ddcompendium.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {
    private Integer id;
    private User recommendedBy;
    private Spell recommendation;
}
