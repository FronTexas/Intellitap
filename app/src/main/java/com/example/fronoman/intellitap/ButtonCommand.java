package com.example.fronoman.intellitap;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.MyTextView;

/**
 * Created by Fahran on 1/18/2015.
 */
public class ButtonCommand extends LinearLayout {

    private LinearLayout llMain;
    private MyTextView tvCommand;
    private RelativeLayout rlButton;
    private float scaleDP;

    public ButtonCommand(Context context, String command) {
        super(context);

        scaleDP = ((MainActivity) context).scaleDP;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.button_command, null);
        tvCommand = (MyTextView) v.findViewById(R.id.tvCommand);
        tvCommand.setText(command);

        llMain = (LinearLayout) v.findViewById(R.id.llMain);

        rlButton = (RelativeLayout) v.findViewById(R.id.rlButton);

        addView(v);
    }

    public void setTextSize(int textSize) {
        tvCommand.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    public void setButtonColor(int color) {
        rlButton.setBackgroundColor(color);
    }

    public void setPadding(int padding_all) {
        padding_all = (int) (padding_all * scaleDP + 0.5f);
        llMain.setPadding(padding_all, padding_all, padding_all, padding_all);
    }

    public void setPadding(int left, int top, int right, int bottom) {
        left = (int) (left * scaleDP + 0.5f);
        top = (int) (top * scaleDP + 0.5f);
        right = (int) (right * scaleDP + 0.5f);
        bottom = (int) (bottom * scaleDP + 0.5f);
        llMain.setPadding(left, top, right, bottom);


    }
}
