package it.ddcompendium.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import it.ddcompendium.R;
import it.ddcompendium.patterns.Observer;

public class ProgressBarButton extends RelativeLayout implements View.OnClickListener, Observer {
    // UI Components
    private final Button mButton;
    private final ProgressBar mProgressBar;

    // Variables
    private final String mButtonText;

    public ProgressBarButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.ProgressBarButton, 0, 0);

        mButtonText = array.getString(R.styleable.ProgressBarButton_android_text);
        array.recycle();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.progress_bar_button, this, true);

        mProgressBar = view.findViewById(R.id.progressBar);
        mButton = view.findViewById(R.id.buttonProgressBar);
        mButton.setText(mButtonText);
        mButton.setOnClickListener(this);
    }

    public void setEnabled(boolean value) {
        mButton.setEnabled(value);
    }

    @Override
    public void onUpdate() {
        mButton.setEnabled(true);
        mButton.setText(mButtonText);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        mButton.setEnabled(false);
        mButton.setText("");
        mProgressBar.setVisibility(View.VISIBLE);
        super.callOnClick();
    }
}
