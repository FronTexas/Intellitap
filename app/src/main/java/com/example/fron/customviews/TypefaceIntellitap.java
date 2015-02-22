package com.example.fron.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TypefaceIntellitap implements Serializable {
    public static final int ROBOTO = 7;
    public static final int ROBOTO_BOLD = 8;
    public static final int ROBOTO_BOLD_ITALIC = 9;

    public Typeface roboto, roboto_bold, roboto_bold_italic;

    public TypefaceIntellitap(Context context) {

        roboto = Typeface.createFromAsset(context.getAssets(), "roboto_regular.ttf");
        roboto_bold = Typeface.createFromAsset(context.getAssets(), "roboto_bold.ttf");
        roboto_bold_italic = Typeface.createFromAsset(context.getAssets(), "roboto_bold_italic.ttf");

    }

    public void setTypeface(TextView v, int typeface) {
        if (typeface == ROBOTO) {
            v.setTypeface(roboto);
        } else if (typeface == ROBOTO_BOLD) {
            v.setTypeface(roboto_bold);
        } else if (typeface == ROBOTO_BOLD_ITALIC) {
            v.setTypeface(roboto_bold_italic);
        }
    }
}
