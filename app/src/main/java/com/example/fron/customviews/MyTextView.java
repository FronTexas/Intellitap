package com.example.fron.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.fronoman.intellitapp.R;


/**
 * Created by Fahran on 1/13/2015.
 */
public class MyTextView extends TextView {
    private Context context;
    private int tf;

    private final String[] typefaces = new String[]{"roboto_bold.ttf", "roboto_bold_italic.ttf", "roboto_italic.ttf", "roboto_regular.ttf", "Action_Man.ttf", "helsinki.ttf"};

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyTextView, 0, 0);

        try {
            this.tf = a.getInt(R.styleable.MyTextView_typeface, 3);
            if (!isInEditMode())
                init();
        } finally {
            a.recycle();
        }


    }

    private void init() {
        setTypeface(TypefaceCache.get(getContext().getAssets(), typefaces[tf]));
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(tf);
    }
}
