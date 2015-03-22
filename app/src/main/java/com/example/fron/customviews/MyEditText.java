package com.example.fron.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.fronoman.intellitapp.R;

/**
 * Created by Fahran on 1/18/2015.
 */
public class MyEditText extends EditText {

    private Context context;
    private int tf;

    private final String[] typefaces = new String[]{"roboto_bold.ttf", "roboto_bold_italic.ttf", "roboto_italic.ttf", "roboto_regular.ttf"};


    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyEditText, 0, 0);

        try {
            this.tf = a.getInt(R.styleable.MyEditText_typeface, 3);
            if (!isInEditMode())
                init();
        } finally {
//            a.recycle();
        }
    }

    private void init() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), typefaces[tf]);
        setTypeface(font);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(tf);
    }
}
