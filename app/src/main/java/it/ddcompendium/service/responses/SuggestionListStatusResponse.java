package it.ddcompendium.service.responses;

import java.util.List;

import it.ddcompendium.entities.Recommendation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionListStatusResponse {
    private List<Recommendation> data;
    private Status status;
}
