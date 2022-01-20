package it.ddcompendium;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import it.ddcompendium.entities.Character;
import it.ddcompendium.entities.User;
import it.ddcompendium.patterns.Observer;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.service.CharactersService;
import it.ddcompendium.service.impl.CharactersServiceImpl;
import it.ddcompendium.service.responses.Status;

public class InsertCharacter extends DialogFragment {
    // Variables
    private final Context mContext;
    private final Observer<Character> mObserver;
    private final User mUser;
    // UI Components
    private EditText mName;
    private Spinner mClasses;
    private CharactersService mService;

    public InsertCharacter(Context context, Observer<Character> observer, User user) {
        this.mContext = context;
        this.mObserver = observer;
        this.mUser = user;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_insert_character, null);

        mName = view.findViewById(R.id.characterInputName);
        mClasses = view.findViewById(R.id.spinner);

        mService = new CharactersServiceImpl(getContext());

        builder.setPositiveButton(R.string.create, (dialogInterface, i) -> {
            String name = mName.getText().toString();
            String classe = mClasses.getSelectedItem().toString();
            Character character = new Character();
            character.setName(name);
            character.setClasse(classe);
            character.setIdUser(mUser.getId());

            Log.d("TAG", "onCreateDialog: " + character);

            if (name.length() > 1) {
                mService.insert(character, new Callback<Status>() {
                    @Override
                    public void onSuccess(Status status) {
                        Toast.makeText(mContext, "Character created successfully", Toast.LENGTH_SHORT).show();
                        mObserver.onUpdate(character);
                        dismiss();
                    }

                    @Override
                    public void onFailure(Status status) {
                        Toast.makeText(mContext, status.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        })
                .setNegativeButton(R.string.dismiss, (dialogInterface, i) -> dismiss())
                .setView(view);

        return builder.create();
    }
}