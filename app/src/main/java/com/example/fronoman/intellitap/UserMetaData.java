package com.example.fronoman.intellitap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.fron.customviews.MyTextView;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by Fahran on 1/25/2015.
 */
public class UserMetaData extends LinearLayout {

    private MyTextView tvName, tvInstitute, tvLocation;
    private CircularImageView ivProviderPicture;
    private User user;

    private float scaleDP;
    private int profpicsize;

    public UserMetaData(Context context, User user) {
        super(context);
        this.user = user;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.area_provider_meta_data, null);

        scaleDP = ((MainActivity) context).scaleDP;
        profpicsize = (int) (100 * scaleDP + 0.5f);


        tvName = (MyTextView) v.findViewById(R.id.tvName);
        tvName.setText(user.getFullName());

        tvInstitute = (MyTextView) v.findViewById(R.id.tvInstitute);
        tvInstitute.setText(user.education.get(0).schoolName);

        tvLocation = (MyTextView) v.findViewById(R.id.tvLocation);
        tvLocation.setText(user.city);

        ivProviderPicture = (CircularImageView) v.findViewById(R.id.ivProviderPicture);

        addView(v);

    }


    public void setName(String name) {
        tvName.setText(name);
    }

    public void setInstitute(String institute) {
        tvInstitute.setText(institute);
    }

    public void setLocation(String location) {
        tvLocation.setText(location);
    }

    public void loadProfilePicture() {
        if (user.profilePhotoUrl.length() > 1) {
            ivProviderPicture.setBackgroundResource(android.R.color.transparent);
            Picasso.with(getContext()).load(user.profilePhotoUrl).resize(profpicsize, profpicsize).centerCrop().into(ivProviderPicture);
        }
    }
}
