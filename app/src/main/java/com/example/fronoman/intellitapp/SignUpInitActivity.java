package com.example.fronoman.intellitapp;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.fron.customviews.MyTextView;
import com.example.fron.customviews.TypefaceIntellitap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Fahran on 3/19/2015.
 */
public class SignUpInitActivity extends FragmentActivity implements OnQuestionAnswered {
    @InjectView(R.id.viewPager)
    ViewPager mViewPager;

    @InjectView(R.id.tvNext)
    MyTextView tvNext;

    @InjectView(R.id.llCircleIndicator)
    LinearLayout llCircleIndicator;

    User user;


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private int[] colors;

    private int RED;
    private int BLUE;
    private int GREEN;

    private String[] question1;
    private String[] question2;

    private ArrayList<Question> questions;
    private int[] llViewsType;
    private String[][] hints;
    private String[] answer_type;
    private int[] question_type;
    RestAdapter adapter;
    IntellitappService service;

    TypefaceIntellitap tfi;

    private static final int PHOTO_QUESTION = -1;
    private static final int REGULAR_QUESTION = 0;
    private static final int EMPLOYMENT_EDUCATION_QUESTION = 1;

    float scaleDP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_init_activity);
        ButterKnife.inject(this);
        RED = getResources().getColor(R.color.RedIntellitap);
        BLUE = getResources().getColor(R.color.BlueIntellitap);
        GREEN = getResources().getColor(R.color.GreenIntellitap);


        user = getIntent().getExtras().getParcelable(C.USER_KEY);

        adapter = new RestAdapter.Builder()
                .setEndpoint(C.MAIN_URL)
                .build();

        service = adapter.create(IntellitappService.class);

        initializeArraysAndList();
        initializeQuestions();

        tfi = new TypefaceIntellitap(this);
        scaleDP = getResources().getDisplayMetrics().density;


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (mSectionsPagerAdapter.getCount() - 1) && position < (colors.length - 1)) {
                    mViewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));
                } else {
                    mViewPager.setBackgroundColor(colors[colors.length - 1]);
                }

                if (position == questions.size() - 1) {
                    tvNext.setText("Done");
                    tvNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Tagline and about me
                            HashMap<String, String> map = new HashMap<>();
                            if (user.isTutor)
                                map.put("tagline", ((Tutor) user).tagline);
                            map.put("aboutMe", user.about);
                            service.updateUser(user.email, map, new Callback<Objects>() {
                                @Override
                                public void success(Objects objects, Response response) {
                                    Log.d("Test", "Update user success = " + response);
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Log.d("Test", "Update user error =" + error);
                                }
                            });

                            // Employments
                            if (user.isTutor) {
                                ArrayList<Employment> employments = ((Tutor) user).employments;
                                HashMap[] maps_array = new HashMap[employments.size()];
                                for (int i = 0; i < employments.size(); i++) {
                                    map = new HashMap<String, String>();
                                    map.put("company", employments.get(i).company);
                                    map.put("position", employments.get(i).position);
                                    map.put("startDate", employments.get(i).startDate);
                                    if (!employments.get(i).isCurrent)
                                        map.put("endDate", employments.get(i).endDate);
                                    map.put("isCurrent", "" + employments.get(i).isCurrent);
                                    maps_array[i] = map;
                                }
                                service.addEmployments(user.email, maps_array, new Callback<Object>() {
                                    @Override
                                    public void success(Object o, Response response) {
                                        Log.d("Test", "Employment success = " + response);
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Log.d("Test", "Employment error =" + error);
                                    }
                                });
                            }

                            // Education
                            ArrayList<Education> educations = user.education;
                            HashMap[] maps_array = new HashMap[educations.size()];
                            int i = 0;
                            for (Education education : educations) {
                                map = new HashMap<String, String>();
                                map.put("schoolName", education.schoolName);
                                map.put("concentration1", education.concentration1);
                                map.put("startDate", education.startDate);
                                maps_array[i] = map;
                                i++;
                            }
                            service.addEducation(user.email, maps_array, new Callback<Object>() {
                                @Override
                                public void success(Object o, Response response) {
                                    Log.d("Test", "Education success =" + response);
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Log.d("Test", "Education error =" + error);
                                }
                            });

                            Intent intent = new Intent(SignUpInitActivity.this, MainActivity.class);
                            Bundle b = new Bundle();
                            b.putString(C.FRAGMENT_TAG_KEY, PageHome.FRAGMENT_TAG);
                            b.putParcelable(C.USER_KEY, user);
                            intent.putExtras(b);
                            startActivity(intent);
                        }
                    });


                } else {
                    tvNext.setText("Next");
                    tvNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToNextPage();
                        }
                    });
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextPage();
            }
        });


    }

    private void initializeArraysAndList() {
        if (user.isTutor) {
            colors = new int[]{BLUE, RED, GREEN, GREEN, BLUE, BLUE};
            llViewsType = new int[]{PageQuestionFragment.NO_LINER, PageQuestionFragment.ONE_LINER, PageQuestionFragment.NO_LINER, PageQuestionFragment.NO_LINER, PageQuestionFragment.MULTIPLE_LINER, PageQuestionFragment.MULTIPLE_LINER};
            hints = new String[][]{{""}, {"e.g classes that you aced"}, {"Answer here"}, {"Answer here"}, {Employment.COMPANY, Employment.POSITION}, {Education.SCHOOL_NAME, Education.DEGREE}};
            answer_type = new String[]{User.PROF_PIC, User.SKILLS, User.TAG_LINE, User.ABOUT, User.EMPLOYMENT, User.EDUCATION};
            question_type = new int[]{PHOTO_QUESTION, REGULAR_QUESTION, REGULAR_QUESTION, REGULAR_QUESTION, EMPLOYMENT_EDUCATION_QUESTION, EMPLOYMENT_EDUCATION_QUESTION};
            question1 = new String[]{"", "What", "If you are a brand,", "Tell the world", "", ""};
            question2 = new String[]{"", "are you good at?", "what is your tagline?", "about yourself", "Employment", "Education"};
        } else {
            colors = new int[]{BLUE, GREEN, BLUE};
            llViewsType = new int[]{PageQuestionFragment.NO_LINER, PageQuestionFragment.ONE_LINER, PageQuestionFragment.MULTIPLE_LINER};
            hints = new String[][]{{""}, {"Answer here"}, {Education.SCHOOL_NAME, Education.DEGREE}};
            answer_type = new String[]{User.PROF_PIC, User.ABOUT, User.EDUCATION};
            question_type = new int[]{PHOTO_QUESTION, REGULAR_QUESTION, EMPLOYMENT_EDUCATION_QUESTION};
            question1 = new String[]{"", "Tell the world", ""};
            question2 = new String[]{"", "about yourself", "Education"};
        }


    }

    public void goToNextPage() {
        if (mViewPager.getCurrentItem() < questions.size())
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        for (int i = 0; i < question1.length; i++) {
            Question q = new Question(question1[i], question2[i]);
            questions.add(q);
        }
    }

    @Override
    public void onQuestionAnswered(String answer) {
        goToNextPage();
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;
            if (position == 0) {
                f = new AddPictureFragment();
            } else if (question_type[position] == REGULAR_QUESTION) {

                f = PageQuestionFragment.newInstance();
                ((PageQuestionFragment) f).setQuestions(questions.get(position));
                ((PageQuestionFragment) f).setllViewsType(llViewsType[position]);
                ((PageQuestionFragment) f).setHints(hints[position]);
                ((PageQuestionFragment) f).setOnQuestionAnswerListener(SignUpInitActivity.this);
                ((PageQuestionFragment) f).setAnswerType(answer_type[position]);
            } else if (question_type[position] == EMPLOYMENT_EDUCATION_QUESTION) {
                f = EmploymentEducationQFragment.newInstance();
                ((EmploymentEducationQFragment) f).setHints(hints[position]);
                Log.d("Error", "Question 2 " + questions.get(position).question2);
                ((EmploymentEducationQFragment) f).setTitle(questions.get(position).question2);
                ((EmploymentEducationQFragment) f).setAnswerType(answer_type[position]);
            }

            return f;
        }

        @Override
        public int getCount() {
            return colors.length;
        }
    }


}

class Question {
    String question1;
    String question2;

    public Question(String q1, String q2) {
        question1 = q1;
        question2 = q2;
    }
}

