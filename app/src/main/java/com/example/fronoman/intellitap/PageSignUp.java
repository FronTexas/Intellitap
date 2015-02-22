package com.example.fronoman.intellitap;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.PasswordAuthentication;
import java.util.ArrayList;

/**
 * Created by Fahran on 1/18/2015.
 */
public class PageSignUp extends Fragment {

    private LinearLayout llMain;
    public static final String FRAGMENT_TAG = "pagesignup";

    private boolean sign_up_button_shown;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sign_up_button_shown = false;
        ((MainActivity) getActivity()).setActionBarColor(getResources().getColor(R.color.GreenIntellitap));
        ((MainActivity) getActivity()).setActionBarTitle("Sign up");

        View v = inflater.inflate(R.layout.page_sign_up, null);

        llMain = (LinearLayout) v.findViewById(R.id.llMain);

        View signUpCard = buildSignUpCard(inflater);
        llMain.addView(signUpCard);

        return v;
    }

    public View buildSignUpCard(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.card_sign_up, null);

        RelativeLayout rlPictures = (RelativeLayout) v.findViewById(R.id.rlPictures);
        rlPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        LinearLayout llCardMain = (LinearLayout) v.findViewById(R.id.llMainCard);

        String[] hints = new String[]{"First Name", "Last Name", "Email", "Password", "Confirm Password", "Phone", "City", "State"};
        ArrayList<EditTextCustom> ets = new ArrayList<>();

        for (int i = 0; i < hints.length; i++) {
            EditTextCustom et = new EditTextCustom(v.getContext(), hints[i]);
            llCardMain.addView(et);
            if (hints[i].contains("Password")) {
                et.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else if (hints[i].equals("Phone"))
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
            ets.add(et);
        }

        View areYouTutor = inflater.inflate(R.layout.area_are_you_tutor, null);
        boolean isTutor = buildAreYouTutor(areYouTutor, llCardMain);
        llCardMain.addView(areYouTutor);
        if (isTutor)
            llCardMain.addView(new EditTextCustom(getActivity(), "List of skills"));

        return v;
    }

    public boolean buildAreYouTutor(View v, final LinearLayout llCardMain) {

        final TextView tvYes = (TextView) v.findViewById(R.id.tvYes);
        final TextView tvNo = (TextView) v.findViewById(R.id.tvNo);

        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvYes.getCurrentTextColor() != getResources().getColor(R.color.GreenIntellitap)) {
                    changeColor(tvNo, tvNo.getCurrentTextColor(), getResources().getColor(android.R.color.black));
                    changeColor(tvYes, tvYes.getCurrentTextColor(), getResources().getColor(R.color.GreenIntellitap));
                    if (!(llCardMain.getChildAt(llCardMain.getChildCount() - 1) instanceof ButtonCommand)) {
                        setUpButtonSignUp(llCardMain);
                    }
                    EditTextCustom listOfSkills = new EditTextCustom(getActivity(), "List of skills");
                    llCardMain.addView(listOfSkills, llCardMain.getChildCount() - 1);
                    listOfSkills.requestFocus();
                }
            }
        });

        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvNo.getCurrentTextColor() != getResources().getColor(R.color.GreenIntellitap)) {
                    changeColor(tvYes, tvYes.getCurrentTextColor(), getResources().getColor(android.R.color.black));
                    changeColor(tvNo, tvNo.getCurrentTextColor(), getResources().getColor(R.color.GreenIntellitap));
                    if (!(llCardMain.getChildAt(llCardMain.getChildCount() - 1) instanceof ButtonCommand)) {
                        setUpButtonSignUp(llCardMain);
                    }
                    if (llCardMain.getChildCount() == 11) {
                        // remove list of skills
                        llCardMain.removeViewAt(9);
                    }
                }
            }
        });

        // if yes is green then it is a user
        return tvYes.getCurrentTextColor() == getResources().getColor(R.color.GreenIntellitap);

    }

    public void setUpButtonSignUp(LinearLayout llMain) {
        ButtonCommand button_sign_up = new ButtonCommand(getActivity(), "Sign me up");

        button_sign_up.setPadding(0, 20, 0, 0);
        button_sign_up.setTextSize(17);
        button_sign_up.setButtonColor(getResources().getColor(R.color.RedIntellitap));
        llMain.addView(button_sign_up);

        button_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogConfirmationCode d = DialogConfirmationCode.newInstance();
                FragmentManager fm = getFragmentManager();
                d.show(fm, "");
            }
        });
    }

    public void changeColor(final View v, int colorFrom, int colorTo) {
        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (v instanceof TextView) {
                    ((TextView) v).setTextColor((int) animation.getAnimatedValue());
                }
            }
        });
        animator.start();
    }

    private class PageSignUpAdapter extends BaseAdapter {

        private View[] views;

        public PageSignUpAdapter(View[] views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return views[position];
        }
    }

}
