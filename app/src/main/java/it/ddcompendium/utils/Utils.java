package it.ddcompendium.utils;

import it.ddcompendium.R;
import it.ddcompendium.entities.Character;

public class Utils {
    public static int getResourceFromClass(Character character) {
        int resource = 0;

        switch (character.getClasse()) {
            case "Sorcerer":
                resource = R.drawable.sorcerer;
                break;
            case "Bard":
                resource = R.drawable.bard;
                break;
            case "Cleric":
                resource = R.drawable.cleric;
                break;
            case "Barbarian":
                resource = R.drawable.barbarian;
                break;
            case "Wizard":
                resource = R.drawable.wizard;
                break;
            case "Druid":
                resource = R.drawable.druid;
                break;
            case "Paladin":
                resource = R.drawable.paladin;
                break;
        }

        return resource;
    }
}
