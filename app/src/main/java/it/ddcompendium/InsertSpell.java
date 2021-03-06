package it.ddcompendium;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import it.ddcompendium.entities.Character;
import it.ddcompendium.entities.Spell;
import it.ddcompendium.service.responses.Status;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.service.CharactersService;
import it.ddcompendium.service.SpellsService;
import it.ddcompendium.service.impl.CharactersServiceImpl;
import it.ddcompendium.service.impl.SpellsServiceImpl;

public class InsertSpell extends DialogFragment {
    // Variables
    private final Context mContext;
    private final Spell mSpell;
    private final Integer mId;
    // UI Components
    private Spinner mCharacters;
    private SpellsService mSpellsService;
    private CharactersService mCharactersService;

    public InsertSpell(Context context, Spell spell, Integer id) {
        this.mContext = context;
        this.mSpell = spell;
        this.mId = id;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_insert_spell, null);

        mCharacters = view.findViewById(R.id.spinner);

        mSpellsService = new SpellsServiceImpl(getContext());
        mCharactersService = new CharactersServiceImpl(getContext());

        mCharactersService.getAll(mId, new Callback<List<Character>>() {
            @Override
            public void onSuccess(List<Character> characters) {
                ArrayAdapter<Character> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, characters);
                mCharacters.setAdapter(adapter);
            }

            @Override
            public void onFailure(Status status) {
                Toast.makeText(getContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton(R.string.add, (dialogInterface, i) -> {
            Object o = mCharacters.getSelectedItem();

            if (o != null) {
                Character character = (Character) o;
                mSpellsService.insert(character.getId(), mSpell.getId(), new Callback<Status>() {
                    @Override
                    public void onSuccess(Status status) {
                        Toast.makeText(mContext, "Spell added successfully to " + character.getName(), Toast.LENGTH_SHORT).show();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Status status) {
                        Toast.makeText(mContext, status.getMessage(), Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                });
            } else {
                dismiss();
            }
        })
                .setNegativeButton(R.string.dismiss, (dialogInterface, i) -> dismiss())
                .setView(view);

        return builder.create();
    }
}