package it.ddcompendium.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}