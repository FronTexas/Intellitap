package com.example.fronoman.intellitapp;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Fahran on 1/18/2015.
 */
public class PageSignUp extends Fragment {

    private LinearLayout llMain;
    public static final String FRAGMENT_TAG = "pagesignup";

    private boolean sign_up_button_shown;

    private IntellitappService service;

    private ArrayList<EditTextCustom> signUpFields;

    private boolean isTutor;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sign_up_button_shown = false;

        service = ((MainActivity) getActivity()).service;

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

        LinearLayout llCardMain = (LinearLayout) v.findViewById(R.id.llMainCard);

        String[] hints = new String[]{"First Name", "Last Name", "Email", "Password", "Confirm Password", "Phone", "City", "State"};
        signUpFields = new ArrayList<>();

        for (int i = 0; i < hints.length; i++) {
            EditTextCustom et = new EditTextCustom(v.getContext(), hints[i]);
            llCardMain.addView(et);
            if (hints[i].contains("Password")) {
                et.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else if (hints[i].equals("Phone"))
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
            signUpFields.add(et);
        }

        View areYouTutor = inflater.inflate(R.layout.area_are_you_tutor, null);
        buildAreYouTutor(areYouTutor, llCardMain);
        llCardMain.addView(areYouTutor);

        return v;
    }

    public void buildAreYouTutor(View v, final LinearLayout llCardMain) {

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
                    isTutor = true;
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
                    isTutor = false;
                }
            }
        });
    }

    public void setUpButtonSignUp(LinearLayout llMain) {
        ButtonCommand button_sign_up = new ButtonCommand(getActivity(), "Sign me up");

        button_sign_up.setPadding(0, 20, 0, 0);
        button_sign_up.setTextSize(17);
        button_sign_up.setButtonColor(getResources().getColor(R.color.RedIntellitap));
        llMain.addView(button_sign_up);

        final String[] signUp_key = new String[]{"firstName", "lastName", "email", "password", "confirmPassword", "phone", "city", "state"};

        button_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "";
                String firstName = "";
                String lastName = "";
                if (isTutor)
                    ((MainActivity) getActivity()).user = new Tutor();
                else
                    ((MainActivity) getActivity()).user = new User();

                ((MainActivity) getActivity()).user.signingUpInitialize(signUpFields, signUp_key);
                ((MainActivity) getActivity()).user.isTutor = isTutor;
                HashMap<String, String> sign_up_body_post = new HashMap<>();
                for (int i = 0; i < signUp_key.length; i++) {
                    if (signUp_key[i].equals("email")) {
                        email = signUpFields.get(i).getText().toString();
                        sign_up_body_post.put("uniqueIdentifier", email);
                    } else if (signUp_key[i].equals("firstName")) {
                        firstName = signUpFields.get(i).getText().toString();
                    } else if (signUp_key[i].equals("lastName")) {
                        lastName = signUpFields.get(i).getText().toString();
                    }
                    if (!signUp_key[i].equals("confirmPassword"))
                        sign_up_body_post.put(signUp_key[i], signUpFields.get(i).getText().toString());
                }
                sign_up_body_post.put("isTutor", "" + isTutor);

                Log.d("Signing up ", "signup post map size = " + sign_up_body_post.size());
                Log.d("Signing up ", "signup post map = " + sign_up_body_post);


                service.newUser(firstName, lastName, email, sign_up_body_post, new Callback<Object>() {
                    @Override
                    public void success(Object s, Response response) {
                        Log.d("Signing up ", "s = " + s);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Signing up", "error = " + error.toString());
                    }
                });

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

