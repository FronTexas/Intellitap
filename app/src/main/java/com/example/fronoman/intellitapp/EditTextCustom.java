package com.example.fronoman.intellitapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.InputType;
import android.text.method.TransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.fron.customviews.MyEditText;

/**
 * Created by Fahran on 1/18/2015.
 */
public class EditTextCustom extends LinearLayout {

    private MyEditText etMain;
    private View blue_highlighter;
    private float scaleDP;

    public EditTextCustom(Context context, String hint) {
        super(context);

        scaleDP = getResources().getDisplayMetrics().density;


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.edit_text_custom, null);

        etMain = (MyEditText) v.findViewById(R.id.etMain);
        etMain.setSingleLine();
        etMain.setImeOptions(FOCUS_DOWN);
        blue_highlighter = v.findViewById(R.id.blue_highlighter);

        etMain.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    changeColor(blue_highlighter, android.R.color.white, getResources().getColor(R.color.BlueIntellitap));
                } else {
                    changeColor(blue_highlighter, getResources().getColor(R.color.BlueIntellitap), android.R.color.white);
                }
            }
        });


        if (hint.length() > 0)
            etMain.setHint(hint);
        addView(v);
    }

    public void changeColor(final View v, int colorFrom, int colorTo) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }


    public void setTextSize(int size) {
        etMain.setTextSize((int) (size * scaleDP + 0.5f));
    }

    public void setTextColor(int color) {
        etMain.setTextColor(color);
    }

    public void setText(String text) {
        etMain.setText(text);
    }

    public String getText() {
        return etMain.getText().toString();
    }

    public void setInputType(int inputType) {
        etMain.setInputType(InputType.TYPE_CLASS_TEXT | inputType);
    }

    public void setTransformationMethod(TransformationMethod tm) {
        etMain.setTransformationMethod(tm);
    }

}
