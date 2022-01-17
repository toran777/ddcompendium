package it.ddcompendium;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import it.ddcompendium.entities.Spell;
import it.ddcompendium.entities.User;
import it.ddcompendium.requests.Callback;
import it.ddcompendium.service.RecommendationService;
import it.ddcompendium.service.UsersService;
import it.ddcompendium.service.impl.RecommendationServiceImpl;
import it.ddcompendium.service.impl.UsersServiceImpl;
import it.ddcompendium.service.responses.Status;

public class ShareSpell extends DialogFragment {
    private static final String TAG = ShareSpell.class.getSimpleName();
    // Variables
    private final Context mContext;
    private final Spell mSpell;
    private final Integer userId;
    private final RecommendationService mService;
    private final UsersService mUserService;
    // UI Components
    private EditText mUsername;

    public ShareSpell(Context context, Spell spell, Integer id) {
        this.mContext = context;
        this.mSpell = spell;
        this.userId = id;
        mUserService = new UsersServiceImpl(context);
        mService = new RecommendationServiceImpl(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_share_spell, null);

        mUsername = view.findViewById(R.id.selectUsername);

        builder.setPositiveButton(R.string.share, (dialogInterface, i) -> getUser())
                .setNegativeButton(R.string.dismiss, (dialogInterface, i) -> dismiss())
                .setView(view);

        return builder.create();
    }

    private void getUser() {
        String username = mUsername.getText().toString();

        if (!username.isEmpty()) {
            mUserService.findUser(username, new Callback<User>() {
                @Override
                public void onSuccess(User user) {
                    mService.add(user.getId(), userId, mSpell.getId(), new Callback<Status>() {
                        @Override
                        public void onSuccess(Status status) {
                            Toast.makeText(mContext, "Spell shared successfully with " + user.getUsername(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Status status) {
                            Toast.makeText(mContext, status.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(Status status) {
                    Toast.makeText(mContext, status.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
