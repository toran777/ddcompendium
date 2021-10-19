package it.ddcompendium.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Character implements Parcelable {
    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };
    private int id;
    private String name;
    private String classe;
    private List<Spell> spells;
    private int idUser;

    public Character() {
    }

    protected Character(Parcel in) {
        id = in.readInt();
        name = in.readString();
        classe = in.readString();
        spells = in.createTypedArrayList(Spell.CREATOR);
        idUser = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(classe);
        parcel.writeTypedList(spells);
        parcel.writeInt(idUser);
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

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return name;
    }
}