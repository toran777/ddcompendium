package it.ddcompendium.entities;

public class Recommendation {
    private Integer id;
    private User recommendedBy;
    private Spell recommendation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getSuggestedBy() {
        return recommendedBy;
    }

    public void setSuggestedBy(User suggestedBy) {
        this.recommendedBy = suggestedBy;
    }

    public Spell getSuggested() {
        return recommendation;
    }

    public void setSuggested(Spell suggested) {
        this.recommendation = suggested;
    }
}
