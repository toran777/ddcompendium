package it.ddcompendium.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import it.ddcompendium.R;

public class TextViewButton extends LinearLayout {
    // UI Components
    private final TextView mDescription;
    private final TextView mButton;

    public TextViewButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.TextViewButton, 0, 0);

        String text1 = array.getString(R.styleable.TextViewButton_android_title);
        String text2 = array.getString(R.styleable.TextViewButton_android_subtitle);

        array.recycle();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.double_text_view, this, true);

        mDescription = view.findViewById(R.id.textDescription);
        mButton = view.findViewById(R.id.textButton);

        mDescription.setText(text1);
        mButton.setText(text2);
    }
}
