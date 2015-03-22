package com.example.fronoman.intellitapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fron.customviews.TypefaceIntellitap;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Fahran on 3/21/2015.
 */
public class MonthsOption extends DialogFragment {
    private String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December"};

    private OnMonthsSelectedListener om;

    LinearLayout llMonthsName;


    public static MonthsOption newInstance() {
        return new MonthsOption();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        float scaleDP = ((SignUpInitActivity) getActivity()).scaleDP;
        TypefaceIntellitap tfi = ((SignUpInitActivity) getActivity()).tfi;

        View v = inflater.inflate(R.layout.months_option, null);
        llMonthsName = (LinearLayout) v.findViewById(R.id.llMonthsName);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = (int) (5 * scaleDP + 0.5f);
        for (int i = 0; i < months.length; i++) {
            TextView tv = new TextView(getActivity());
            tv.setText(months[i]);
            tv.setTextColor(getResources().getColor(android.R.color.white));
            tv.setLayoutParams(params);
            llMonthsName.addView(tv);
            tfi.setTypeface(tv, TypefaceIntellitap.ROBOTO_BOLD);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    om.onMonthSelected(((TextView) v).getText().toString());
                    dismiss();
                }
            });
        }


        return v;
    }

    public void setOnMonthsSelectedListener(OnMonthsSelectedListener om) {
        this.om = om;
    }

    interface OnMonthsSelectedListener {
        public void onMonthSelected(String month);
    }


}


