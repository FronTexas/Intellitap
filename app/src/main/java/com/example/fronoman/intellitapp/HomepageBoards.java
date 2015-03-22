package com.example.fronoman.intellitapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.MyTextView;
import com.example.fron.customviews.TypefaceIntellitap;

import java.util.ArrayList;

/**
 * Created by Fahran on 1/25/2015.
 */
public class HomepageBoards extends LinearLayout {
    private ImageView ivTitle;
    private MyTextView tvTitle;
    private LinearLayout llNotification;
    private MyTextView tvTotalNotif, tvTitleNotif;
    private LinearLayout llContent;

    private ArrayList<View> views;


    public HomepageBoards(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.card_homepage_boards, null);

        llContent = (LinearLayout) v.findViewById(R.id.llContent);

        ivTitle = (ImageView) v.findViewById(R.id.ivTitle);
        tvTitle = (MyTextView) v.findViewById(R.id.tvTitle);

        llNotification = (LinearLayout) v.findViewById(R.id.llNotification);
        tvTotalNotif = (MyTextView) v.findViewById(R.id.tvTotalNotif);
        tvTitleNotif = (MyTextView) v.findViewById(R.id.tvTitleNotif);

        views = new ArrayList<>();


        addView(v);
    }


    public void setImageTitle(int img_resource) {
        ivTitle.setImageResource(img_resource);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void showNotif() {
        llNotification.setVisibility(VISIBLE);
    }

    public void hideNotif() {
        llNotification.setVisibility(GONE);
    }

    public void setViewContents(ArrayList<View> views) {

        for (int i = 0; i < Math.min(3, views.size()); i++) {
            llContent.addView(views.get(i));
        }

        if (views.size() > 3) {
            RelativeLayout rlShowMore = new RelativeLayout(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin = 10;
            float scaleDP = ((MainActivity) getContext()).scaleDP;
            params.setMargins((int) (margin * scaleDP), (int) (margin * scaleDP), (int) (margin * scaleDP), (int) (margin * scaleDP));
            rlShowMore.setLayoutParams(params);

            TextView tvShowMore = new TextView(getContext());
            ((MainActivity) getContext()).tfi.setTypeface(tvShowMore, TypefaceIntellitap.ROBOTO_BOLD);
            tvShowMore.setTextColor(getResources().getColor(R.color.BlueIntellitap));
            RelativeLayout.LayoutParams params_tvshowmore = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params_tvshowmore.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            tvShowMore.setLayoutParams(params_tvshowmore);
            tvShowMore.setText("Show more");

            rlShowMore.addView(tvShowMore);

            llContent.addView(rlShowMore);
        }


    }

    public void setContentOrientation(int orientation) {
        llContent.setOrientation(orientation);
    }

    public void setNotifCounter(int counter) {
        tvTotalNotif.setText("" + counter);
    }

    public void incrementNotifCounter() {
        tvTotalNotif.setText("" + (Integer.parseInt(tvTotalNotif.getText().toString())) + 1);
    }

    public void setNotifTitle(String notifTitle) {
        tvTitleNotif.setText(notifTitle);
    }


}
