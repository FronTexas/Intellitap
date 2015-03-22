package com.example.fronoman.intellitapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fron.customviews.TypefaceIntellitap;

import retrofit.RestAdapter;


/**
 * Created by Fahran on 1/12/2015.
 */
public class MainActivity extends FragmentActivity {

    private final String[] typefaces_name = new String[]{"roboto_bold.ttf", "roboto_bold_italic.ttf", "roboto_italic.ttf", "roboto_regular.ttf"};
    public Typeface[] typefaces;
    private View actionbar;

    private TextView tvActionBarTitle;
    private LinearLayout actionBarAction;
    private int action_bar_current_color;

    private final int fragment_space_id = R.id.fragment_space;

    public TypefaceIntellitap tfi;
    public float scaleDP;

    public User user;

    RestAdapter adapter;
    IntellitappService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        adapter = new RestAdapter.Builder()
                .setEndpoint(C.MAIN_URL)
                .build();

        service = adapter.create(IntellitappService.class);

        setContentView(R.layout.main_activity);
        createTypefaceArray();

//        // TODO this is fake. In the future User will be the one who logged in not only Matthew mc :)
//        buildUser();


        // default color
        action_bar_current_color = getResources().getColor(android.R.color.transparent);

        actionbar = findViewById(R.id.action_bar_main);

        tfi = new TypefaceIntellitap(this);
        scaleDP = getResources().getDisplayMetrics().density;

        tvActionBarTitle = (TextView) findViewById(R.id.tvActionBarTitle);
        tfi.setTypeface(tvActionBarTitle, TypefaceIntellitap.ROBOTO_BOLD);

        actionBarAction = (LinearLayout) findViewById(R.id.actionBarAction);

        if (getIntent().getExtras() != null) {
            User user = getIntent().getExtras().getParcelable(C.USER_KEY);
            if (user != null) {
                this.user = user;
            }
            String fragment_tag_passed = getIntent().getExtras().getString(C.FRAGMENT_TAG_KEY);
            if (fragment_tag_passed.equals(PageSignUp.FRAGMENT_TAG)) {
                replaceFragments(new PageSignUp(), false, PageSignUp.FRAGMENT_TAG);
            } else if (fragment_tag_passed.equals(PageHome.FRAGMENT_TAG)) {
                replaceFragments(new PageHome(), false, PageHome.FRAGMENT_TAG);
            }
        }


    }

    public void createTypefaceArray() {
        typefaces = new Typeface[typefaces_name.length];
        int i = 0;
        for (String t : typefaces_name) {
            Typeface font = Typeface.createFromAsset(getAssets(), t);
            typefaces[i] = font;
        }
    }

//    public void buildUser() {
//        user = new User();
//        user.firstName = "Matthew";
//        user.lastName = "Mcconaughey";
//        Education education = new Education();
//        education.schoolName = "The University of Texas At Austin";
//        user.education.add(education);
//        user.city = "Austin , TX";
//        user.profilePhotoUrl = "http://longhornleasing.com/blog/wp-content/uploads/2014/12/original.jpg";
//    }

    public void replaceFragments(Fragment fragment, boolean addToBackStack, String tag) {
        showActionBar();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        actionBarAction.removeAllViews();
        transaction.replace(fragment_space_id, fragment, tag).commit();
    }

    public void setActionBarColor(int color) {
        if (action_bar_current_color == getResources().getColor(android.R.color.transparent)) {
            actionbar.setBackgroundColor(color);
        } else {
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), action_bar_current_color, color);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    actionbar.setBackgroundColor((int) animation.getAnimatedValue());
                }
            });
            colorAnimation.start();
        }
        action_bar_current_color = color;
    }

    public void setActionBarAction(View action_view) {
        actionBarAction.addView(action_view);
    }

    public void hideActionBar() {
        actionbar.setVisibility(View.GONE);
    }

    public void showActionBar() {
        actionbar.setVisibility(View.VISIBLE);
    }

    public void setActionBarTitle(String title) {
        tvActionBarTitle.setText(title);
    }
}
