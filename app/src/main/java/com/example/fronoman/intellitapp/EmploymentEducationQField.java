package com.example.fronoman.intellitapp;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

class EmploymentEducationQField extends LinearLayout {

    EditText etName;

    EditText etPosition;

    LinearLayout llMonthStart;
    MyTextView tvMonthStart;

    EditText etYearStarts;

    LinearLayout llMonthEnd;
    MyTextView tvMonthEnd;

    EditText etYearEnd;

    RelativeLayout rlSave;

    MyTextView tvSave;

    LinearLayout llIsCurrent;

    View whiteSelector;

    private TextView[] fields;

    private TypefaceIntellitap tfi;

    boolean isCurrent;

    String[] months_name = new String[]{"January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December"};


    public EmploymentEducationQField(final Context context, String[] hints, final String answerType, final User user) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.employment_education_fields, null);
        initializeView(v);

        etName.setHint(hints[0]);

        etPosition.setHint(hints[1]);


        handleAlpha(etName);
        handleAlpha(etPosition);
        handleAlpha(etYearStarts);
        handleAlpha(etYearEnd);

        fields = new TextView[]{etName, etPosition, tvMonthStart, etYearStarts, tvMonthEnd, etYearEnd};


        tfi = ((SignUpInitActivity) context).tfi;

        tfi.setTypeface(etName, TypefaceIntellitap.ROBOTO_BOLD);
        tfi.setTypeface(etPosition, TypefaceIntellitap.ROBOTO);
        tfi.setTypeface(etYearStarts, TypefaceIntellitap.ROBOTO);
        tfi.setTypeface(etYearEnd, TypefaceIntellitap.ROBOTO);

        llMonthStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthsOption mo = MonthsOption.newInstance();
                FragmentManager fm = ((SignUpInitActivity) context).getSupportFragmentManager();
                mo.show(fm, "");
                mo.setOnMonthsSelectedListener(new MonthsOption.OnMonthsSelectedListener() {
                    @Override
                    public void onMonthSelected(String month) {
                        tvMonthStart.setText(month);
                    }
                });
            }
        });

        llMonthEnd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthsOption mo = MonthsOption.newInstance();
                FragmentManager fm = ((SignUpInitActivity) context).getSupportFragmentManager();
                mo.show(fm, "");
                mo.setOnMonthsSelectedListener(new MonthsOption.OnMonthsSelectedListener() {
                    @Override
                    public void onMonthSelected(String month) {
                        tvMonthEnd.setText(month);
                    }
                });
            }
        });

        rlSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rlSave.setVisibility(GONE);
                if (answerType.equals(User.EMPLOYMENT)) {
                    Employment employment = new Employment();
                    employment.company = etName.getText().toString();
                    employment.position = etPosition.getText().toString();
                    String month = "" + (Arrays.asList(months_name).indexOf(tvMonthStart.getText().toString()) + 1);
                    if (month.length() == 1) {
                        month = "0" + month;
                    }
                    String startDate = etYearStarts.getText().toString() + "-" + month + "-01";
                    employment.startDate = startDate;
                    month = "" + Arrays.asList(months_name).indexOf(tvMonthEnd.getText().toString());
                    if (month.length() == 1) {
                        month = "0" + month;
                    }

                    String endDate = etYearEnd.getText().toString() + "-" + month + "-01";
                    employment.endDate = endDate;
                    employment.isCurrent = isCurrent;

                    ((Tutor) user).employments.add(employment);
                    Log.d("Test", "user employments = " + ((Tutor) user).employments);
                } else if (answerType.equals(User.EDUCATION)) {
                    Education education = new Education();
                    education.schoolName = etName.getText().toString();
                    education.concentration1 = etPosition.getText().toString();

                    String month = "" + (Arrays.asList(months_name).indexOf(tvMonthStart.getText().toString()) + 1);
                    if (month.length() == 1) {
                        month = "0" + month;
                    }
                    String startDate = etYearStarts.getText().toString() + "-" + month + "-01";
                    education.startDate = startDate;

                    month = "" + Arrays.asList(months_name).indexOf(tvMonthEnd.getText().toString());
                    if (month.length() == 1) {
                        month = "0" + month;
                    }

                    String endDate = etYearEnd.getText().toString() + "-" + month + "-01";
                    education.endDate = endDate;

                    user.education.add(education);
                    Log.d("Test", "user education = " + user.education);
                }
            }
        });

        llIsCurrent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (whiteSelector.getVisibility() == VISIBLE) {
                    whiteSelector.setVisibility(INVISIBLE);
                    isCurrent = false;
                    llMonthEnd.setVisibility(VISIBLE);
                    etYearEnd.setVisibility(VISIBLE);
                } else {
                    llMonthEnd.setVisibility(INVISIBLE);
                    etYearEnd.setVisibility(INVISIBLE);
                    whiteSelector.setVisibility(VISIBLE);
                    isCurrent = true;
                }
            }
        });

        addView(v);
    }

    public void initializeView(View v) {
        etName = (EditText) v.findViewById(R.id.etName);
        etPosition = (EditText) v.findViewById(R.id.etPosition);
        llMonthStart = (LinearLayout) v.findViewById(R.id.llMonthStart);
        tvMonthStart = (MyTextView) v.findViewById(R.id.tvMonthStart);
        etYearStarts = (EditText) v.findViewById(R.id.etYearStarts);
        llMonthEnd = (LinearLayout) v.findViewById(R.id.llMonthEnd);
        tvMonthEnd = (MyTextView) v.findViewById(R.id.tvMonthEnd);
        etYearEnd = (EditText) v.findViewById(R.id.etYearEnd);
        rlSave = (RelativeLayout) v.findViewById(R.id.rlSave);
        tvSave = (MyTextView) v.findViewById(R.id.tvSave);
        llIsCurrent = (LinearLayout) v.findViewById(R.id.llIsCurrent);
        whiteSelector = v.findViewById(R.id.whiteSelector);
    }

    private void handleAlpha(final TextView t) {
        t.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                rlSave.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    t.setAlpha(1f);
                } else {
                    t.setAlpha(0.5f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}

