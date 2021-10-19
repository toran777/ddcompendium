package it.ddcompendium.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.LinkedHashMap;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public String getRitual() {
        return ritual;
    }

    public void setRitual(String ritual) {
        this.ritual = ritual;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public void setCastingTime(String castingTime) {
        this.castingTime = castingTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
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
