package com.example.fronoman.intellitapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.MyTextView;
import com.example.fron.customviews.TypefaceIntellitap;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Fahran on 3/19/2015.
 */
public class PageQuestionFragment extends Fragment {
    @InjectView(R.id.rootLayout)
    RelativeLayout rootLayout;

    @InjectView(R.id.tvQuestion1)
    MyTextView tvQuestion1;

    @InjectView(R.id.tvQuestion2)
    MyTextView tvQuestion2;

    @InjectView(R.id.etAnswer)
    EditText etAnswer;

    @InjectView(R.id.llViews)
    LinearLayout llViews;

    private User user;

    private OnQuestionAnswered oq;


    public static final int NO_LINER = 0;
    public static final int ONE_LINER = 1;
    public static final int MULTIPLE_LINER = 2;

    private int llViewsType;
    private String[] hints;


    private final HashMap<Integer, Integer> bg_to_et_hintColor = new HashMap<>();
    private TypefaceIntellitap tfi;
    private float scaleDP;
    private Question question;
    private String answerType;

    private Employment employment;
    private Education education;


    public static PageQuestionFragment newInstance() {
        return new PageQuestionFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_question_fragment, null);
        user = ((SignUpInitActivity) getActivity()).user;


        ButterKnife.inject(this, v);
        this.tfi = new TypefaceIntellitap(getActivity());
        this.scaleDP = getResources().getDisplayMetrics().density;


        int[] bgColors = new int[]{getResources().getColor(R.color.RedIntellitap), getResources().getColor(R.color.BlueIntellitap), getResources().getColor(R.color.GreenIntellitap)};
        int[] etHintColors = new int[]{getResources().getColor(R.color.RedWhiteTransparent), getResources().getColor(R.color.BlueWhiteTransparent), getResources().getColor(R.color.GreenWhiteTransparent)};

        for (int i = 0; i < bgColors.length; i++) {
            bg_to_et_hintColor.put(bgColors[i], etHintColors[i]);
        }

        if (answerType.equals(User.EMPLOYMENT)) {
            employment = new Employment();
        } else if (answerType.equals(User.EDUCATION)) {
            education = new Education();
        }


        initializeViews();


        return v;
    }

    public void initializeViews() {
        // There is time where I don't need question 1
        tvQuestion1.setText("" + question.question1);
        tvQuestion2.setText("" + question.question2);

        tfi.setTypeface(etAnswer, TypefaceIntellitap.ROBOTO_BOLD);
        etAnswer.setHint(hints[0]);

        etAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                TextView textView = new TextView(getActivity());
                textView.setTextColor(getResources().getColor(android.R.color.white));
                int big_textSize = 20;

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT);

                String answer = v.getText().toString();
                textView.setText("" + answer);
                v.setText("");


                if (answerType.equals(User.SKILLS)) {
                    Skill skill = new Skill(answer);
                    ((Tutor) user).skills.add(skill);
                } else if (answerType.equals(User.TAG_LINE)) {
                    ((Tutor) user).tagline = answer;
                } else if (answerType.equals(User.ABOUT)) {
                    user.about = answer;
                }
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, big_textSize);
                tfi.setTypeface(textView, TypefaceIntellitap.ROBOTO);
                llViews.addView(textView);
                if (llViewsType == NO_LINER) {
                    // If its reached here, go to the next question
                    oq.onQuestionAnswered(answer);
                }

                return true;
            }
        });
    }

    public void setQuestions(Question question) {
        this.question = question;
    }

    public void setllViewsType(int type) {
        llViewsType = type;
    }

    public void setHints(String[] hints) {
        this.hints = hints;
    }


    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public void setOnQuestionAnswerListener(OnQuestionAnswered oq) {
        this.oq = oq;
    }


}

interface OnQuestionAnswered {
    public void onQuestionAnswered(String answer);
}
