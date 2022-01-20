package it.ddcompendium.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.LinkedHashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Spell implements Parcelable {
    public static final Creator<Spell> CREATOR = new Creator<Spell>() {
        @Override
        public Spell createFromParcel(Parcel in) {
            return new Spell(in);
        }

        @Override
        public Spell[] newArray(int size) {
            return new Spell[size];
        }
    };
    private int id;
    private String name;
    private String desc;
    private String page;
    private String range;
    private String components;
    private String ritual;
    private String duration;
    private String concentration;
    private String castingTime;
    private String level;
    private String school;
    private String classe;

    protected Spell(Parcel in) {
        id = in.readInt();
        name = in.readString();
        desc = in.readString();
        page = in.readString();
        range = in.readString();
        components = in.readString();
        ritual = in.readString();
        duration = in.readString();
        concentration = in.readString();
        castingTime = in.readString();
        level = in.readString();
        school = in.readString();
        classe = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(desc);
        parcel.writeString(page);
        parcel.writeString(range);
        parcel.writeString(components);
        parcel.writeString(ritual);
        parcel.writeString(duration);
        parcel.writeString(concentration);
        parcel.writeString(castingTime);
        parcel.writeString(level);
        parcel.writeString(school);
        parcel.writeString(classe);
    }

    public HashMap<String, String> getValues() {
        HashMap<String, String> values = new LinkedHashMap<>();
        values.put("Class", classe);
        values.put("Level", level);
        values.put("Range", range);
        values.put("Duration", duration);
        values.put("School", school);
        values.put("Page", page);
        values.put("Ritual", ritual);
        values.put("Components", components);
        values.put("Concentration", concentration);
        values.put("Casting time", castingTime);
        values.put("Description", desc);

        return values;
    }
}
