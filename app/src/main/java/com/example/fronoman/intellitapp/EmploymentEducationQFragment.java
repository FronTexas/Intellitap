package com.example.fronoman.intellitapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.MyTextView;
import com.example.fron.customviews.TypefaceIntellitap;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Fahran on 3/20/2015.
 */
public class EmploymentEducationQFragment extends Fragment {

    MyTextView tvTitle;

    LinearLayout llMain;

    TextView tvPlus;


    private String[] hints;

    private String answerType;
    private String title;

    public static EmploymentEducationQFragment newInstance() {
        return new EmploymentEducationQFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.employment_education_question_fragment, null);
        initializeView(v);
        final User user = ((SignUpInitActivity) getActivity()).user;
        tvTitle.setText(title);
        // if ll main only contains plus button fill it with eeq
        if (llMain.getChildCount() == 1) {
            EmploymentEducationQField eeq = new EmploymentEducationQField(getActivity(), hints, answerType, user);
            // -2 because I want to add just above the plus button
            llMain.addView(eeq, llMain.getChildCount() - 1);
        }

        tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmploymentEducationQField eeq = new EmploymentEducationQField(getActivity(), hints, answerType, user);
                // -2 because I want to add just above the plus button
                llMain.addView(eeq, llMain.getChildCount() - 1);
            }
        });
        return v;
    }

    private void initializeView(View v) {
        tvTitle = (MyTextView) v.findViewById(R.id.tvTitle);
        llMain = (LinearLayout) v.findViewById(R.id.llMain);
        tvPlus = (MyTextView) v.findViewById(R.id.tvPlus);

    }

    public void setHints(String[] hints) {
        this.hints = hints;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}






