package com.example.fronoman.intellitap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.MyTextView;

/**
 * Created by Fahran on 1/30/2015.
 */
public class CommentCard extends LinearLayout {

    private MyTextView tvUserName, tvUserComment;
    private LinearLayout llRatingStar;
    private MyTextView tvUpvotes, tvDownvotes;

    private RelativeLayout rlSeeAllReview;

    private ImageView ivProviderPicture;

    public CommentCard(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.area_user_comment, null);

        initializeViews(v);


        addView(v);
    }

    public void initializeViews(View v) {
        tvUserName = (MyTextView) v.findViewById(R.id.tvUserName);
        tvUserComment = (MyTextView) v.findViewById(R.id.tvUserComment);
        llRatingStar = (LinearLayout) v.findViewById(R.id.llRatingStar);
        tvUpvotes = (MyTextView) v.findViewById(R.id.tvUpvotes);
        tvDownvotes = (MyTextView) v.findViewById(R.id.tvDownvotes);
        rlSeeAllReview = (RelativeLayout) v.findViewById(R.id.rlSeeAllReview);
        ivProviderPicture = (ImageView) v.findViewById(R.id.ivProviderPicture);
    }

    public void setName(String name) {
        tvUserName.setText(name);
    }

    public void setComment(String comment) {
        tvUserComment.setText(comment);
    }

    public void setRating(double rating) {
        // TODO change this to
        int rating_int = (int) rating;

        for (int i = 0; i < rating_int; i++) {
            ImageView ivStar = new ImageView(getContext());
//            ivStar.get
//            llRatingStar.addView();
        }
    }


}
