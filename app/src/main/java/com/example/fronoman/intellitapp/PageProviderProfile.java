package com.example.fronoman.intellitapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.MyTextView;
import com.example.fron.customviews.TypefaceIntellitap;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by Fahran on 1/13/2015.
 */
public class PageProviderProfile extends Fragment {

    public static String FRAGMENT_TAG = "prpage";

    private TypefaceIntellitap tfi;
    private Tutor tutor;

    private float scaleDP;
    private int profpicsize;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_profider_profile, null);
        scaleDP = ((MainActivity) getActivity()).scaleDP;
        profpicsize = (int) (200 * scaleDP + 0.5f);

        tfi = ((MainActivity) getActivity()).tfi;

        tutor = getArguments().getParcelable(C.TUTOR_KEY);


        ((MainActivity) getActivity()).setActionBarTitle(tutor.getFullName());
        ((MainActivity) getActivity()).setActionBarColor(getResources().getColor(R.color.RedIntellitap));

        ListView lvProfilePage = (ListView) v.findViewById(R.id.lvProfilePage);
        TextView tvBookText = (TextView) v.findViewById(R.id.tvBookText);
        tfi.setTypeface(tvBookText, TypefaceIntellitap.ROBOTO_BOLD);

        View provider_meta_data_area = buildProviderMetaDataArea(inflater);
        View provider_numbers = buildProviderNumbersArea(inflater);

        CalendarCard calendar_card = new CalendarCard(getActivity());
        calendar_card.turnOffTimeSlotClickListener();

        View expertise = buildExpertiseArea(inflater);
        View preferredLocation = buildPreferredLocation(inflater);
        View userComment = buildUserCommentArea(inflater);

        View[] views = new View[]{provider_meta_data_area, provider_numbers, calendar_card, expertise, preferredLocation, userComment};

        ProfilePageAdapter mAdapter = new ProfilePageAdapter(views);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(mAdapter);
        animationAdapter.setAbsListView(lvProfilePage);
        lvProfilePage.setAdapter(animationAdapter);

        RelativeLayout rlBook = (RelativeLayout) v.findViewById(R.id.rlBookButton);
        rlBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageBooking pb = new PageBooking();
                Bundle b = new Bundle();
                b.putParcelable(C.TUTOR_KEY, tutor);
                pb.setArguments(b);
                ((MainActivity) getActivity()).replaceFragments(pb, true, PageBooking.FRAGMENT_TAG);
            }
        });


        return v;
    }


    private View buildProviderMetaDataArea(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.area_provider_meta_data, null);
        TextView tvName = (TextView) v.findViewById(R.id.tvName);
        tvName.setText(tutor.firstName + " " + tutor.lastName);


        TextView tvInstitute = (TextView) v.findViewById(R.id.tvInstitute);
        tvInstitute.setText(tutor.education.get(0).schoolName);


        TextView tvLocation = (TextView) v.findViewById(R.id.tvLocation);
        tvLocation.setText(tutor.city);

        CircularImageView ivProviderPicture = (CircularImageView) v.findViewById(R.id.ivProviderPicture);
        ivProviderPicture.setBackgroundResource(android.R.color.transparent);
        if (tutor.profilePhotoUrl.length() > 0)
            Picasso.with(getActivity()).load(tutor.profilePhotoUrl).resize(profpicsize, profpicsize).centerCrop().into(ivProviderPicture);

        tfi.setTypeface(tvName, TypefaceIntellitap.ROBOTO_BOLD);
        tfi.setTypeface(tvInstitute, TypefaceIntellitap.ROBOTO);
        tfi.setTypeface(tvLocation, TypefaceIntellitap.ROBOTO);

        return v;

    }

    private View buildProviderNumbersArea(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.area_provider_numbers, null);

        TextView tvRating = (TextView) v.findViewById(R.id.tvRating);
        tvRating.setText("" + tutor.stars);

        TextView tvAverageRatingText = (TextView) v.findViewById(R.id.tvAverageRatingText);
        TextView tvHourlyRate = (TextView) v.findViewById(R.id.tvHourlyRate);
        TextView tvHourlyRateText = (TextView) v.findViewById(R.id.tvHourlyRateText);
        TextView tvMaxStudents = (TextView) v.findViewById(R.id.tvMaxStudents);
        TextView tvMaxStudentsText = (TextView) v.findViewById(R.id.tvMaxStudentsText);

        TextView[] roboto_italics = new TextView[]{tvRating, tvHourlyRate, tvMaxStudents};
        TextView[] roboto_bold = new TextView[]{tvAverageRatingText, tvHourlyRateText, tvMaxStudentsText};

        for (TextView t : roboto_italics)
            tfi.setTypeface(t, TypefaceIntellitap.ROBOTO_BOLD_ITALIC);

        for (TextView t : roboto_bold)
            tfi.setTypeface(t, TypefaceIntellitap.ROBOTO_BOLD);

        return v;

    }

    private View buildExpertiseArea(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.area_expertise, null);
        MyTextView tvExpertise = (MyTextView) v.findViewById(R.id.tvExpertise);
        StringBuffer skills = new StringBuffer();
        int i = 0;
        for (Skill s : tutor.skills) {
            skills.append(s.skillName);
            if (i != tutor.skills.size() - 1) {
                skills.append(", ");
            }
            i++;
        }
        tvExpertise.setText(skills.toString());
        return v;
    }

    private View buildPreferredLocation(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.area_preffered_location, null);
        return v;
    }

    private View buildUserCommentArea(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.area_user_comment, null);
        TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
        TextView tvUserComment = (TextView) v.findViewById(R.id.tvUserComment);

        tvUserComment.setText("\"Very Punctual\"");

        LinearLayout llStars = (LinearLayout) v.findViewById(R.id.llRatingStar);
        for (int i = 0; i < 5; i++) {
            View stars = inflater.inflate(R.layout.stars, null);
            llStars.addView(stars);
        }
        return v;
    }

    private class ProfilePageAdapter extends BaseAdapter {

        private View[] views;

        public ProfilePageAdapter(View[] views) {
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
