package com.example.fronoman.intellitapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.TypefaceIntellitap;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Fahran on 1/12/2015.
 */
public class ProviderListingCard extends RelativeLayout {

    // size of profile picture in dp
    int profile_picture_size;
    float scaleDP;

    Tutor tutor;
    Context context;

    private TextView tvRating, tvAverageRatingText, tvDollarSign, tvRates, tvHourlyRateText, tvMaxStudents, tvMaxStudentsText;
    private TextView tvName, tvInstitute, tvLocation, tvRelevantExpertiseText, tvRelevantExpertiseList, tvPreferredLocationText, tvPreferredLocationList;
    private CircularImageView ivProviderPicture;

    public ProviderListingCard(Context context, Tutor tutor) {
        super(context);
        this.context = context;
        scaleDP = ((MainActivity) context).scaleDP;

        this.tutor = tutor;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.card_provider_listing, null);
        initializeViews(v);
        addView(v);

        profile_picture_size = (int) (125 * scaleDP + 0.5f);
    }

    private void initializeViews(View v) {
        TypefaceIntellitap tfe = ((MainActivity) getContext()).tfi;
        ArrayList<TextView> roboto_bold_italics = new ArrayList<>();
        ArrayList<TextView> roboto_bolds = new ArrayList<>();
        ArrayList<TextView> robotos = new ArrayList<>();

        ivProviderPicture = (CircularImageView) v.findViewById(R.id.ivProviderPicture);


        tvRating = (TextView) v.findViewById(R.id.tvRating);
        tvRating.setText("" + tutor.stars);
        roboto_bold_italics.add(tvRating);

        tvAverageRatingText = (TextView) v.findViewById(R.id.tvAverageRatingText);
        roboto_bolds.add(tvAverageRatingText);

        tvDollarSign = (TextView) v.findViewById(R.id.tvDollarSign);
        tvRates = (TextView) v.findViewById(R.id.tvRates);
        roboto_bold_italics.add(tvDollarSign);
        roboto_bold_italics.add(tvRates);

        tvHourlyRateText = (TextView) v.findViewById(R.id.tvHourlyRateText);
        roboto_bolds.add(tvHourlyRateText);

        tvMaxStudents = (TextView) v.findViewById(R.id.tvMaxStudents);
        roboto_bold_italics.add(tvMaxStudents);

        tvMaxStudentsText = (TextView) v.findViewById(R.id.tvMaxStudentsText);
        roboto_bolds.add(tvMaxStudentsText);

        tvName = (TextView) v.findViewById(R.id.tvName);
        tvName.setText(tutor.firstName + " " + tutor.lastName);

        tvInstitute = (TextView) v.findViewById(R.id.tvInstitute);
        Log.d("JSON Tutor", "tutor.education = " + tutor.education);
        tvInstitute.setText(tutor.education.get(0).schoolName);

        tvLocation = (TextView) v.findViewById(R.id.tvLocation);
        tvLocation.setText(tutor.city);

        tvRelevantExpertiseText = (TextView) v.findViewById(R.id.tvRelevantExpertiseText);
        tvRelevantExpertiseList = (TextView) v.findViewById(R.id.tvRelevantExpertiseList);
        if (tutor.skills.size() > 0) {
            int i = 0;
            StringBuffer sb = new StringBuffer();
            for (Skill s : tutor.skills) {
                sb.append(s.skillName);
                if (i != tutor.skills.size() - 1) {
                    sb.append(", ");
                }
                i++;
            }
            tvRelevantExpertiseList.setText(sb.toString());
        }
        tvPreferredLocationText = (TextView) v.findViewById(R.id.tvPreferredLocationText);
        tvPreferredLocationList = (TextView) v.findViewById(R.id.tvPreferredLocationList);


        roboto_bolds.add(tvName);
        robotos.add(tvInstitute);
        robotos.add(tvLocation);
        roboto_bold_italics.add(tvRelevantExpertiseText);
        robotos.add(tvRelevantExpertiseList);
        roboto_bold_italics.add(tvPreferredLocationText);
        robotos.add(tvPreferredLocationList);

        for (TextView t : robotos) {
            tfe.setTypeface(t, TypefaceIntellitap.ROBOTO);
        }

        for (TextView t : roboto_bolds) {
            tfe.setTypeface(t, TypefaceIntellitap.ROBOTO_BOLD);
        }

        for (TextView t : roboto_bold_italics) {
            tfe.setTypeface(t, TypefaceIntellitap.ROBOTO_BOLD_ITALIC);
        }

    }

    public void loadProfilePhoto() {
        Log.d("profile url", "profile url = " + tutor.profilePhotoUrl);
        if (tutor.profilePhotoUrl.length() > 0) {
            ivProviderPicture.setBackgroundResource(android.R.color.transparent);
            Picasso.with(context).load(tutor.profilePhotoUrl).resize(profile_picture_size, profile_picture_size).centerCrop().into(ivProviderPicture);
        }
    }
}
