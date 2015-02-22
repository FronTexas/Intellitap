package com.example.fronoman.intellitap;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.MyTextView;
import com.example.fron.customviews.TypefaceIntellitap;

import java.util.ArrayList;

/**
 * Created by Fahran on 1/15/2015.
 */
public class PageBooking extends Fragment {

    public static final String FRAGMENT_TAG = "pagebooking";
    private RelativeLayout rlBook;
    private Tutor tutor;

    EditText etMeetingPlace;

    private LinearLayout llWDYWTL;
    private ArrayList<User> invited_user;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_booking, null);


        ((MainActivity) getActivity()).setActionBarColor(getResources().getColor(R.color.GreenIntellitap));
        ((MainActivity) getActivity()).setActionBarTitle("Book");

        tutor = getArguments().getParcelable(C.TUTOR_KEY);
        invited_user = new ArrayList<>();


        View area_wdywtl = buildWDYWTLArea(inflater);
        View calendar_card = new CalendarCard(getActivity());
        View area_meeting_place = buildMeetingPlaceArea(inflater);
        View area_invite_people = buildInvitePeopleArea(inflater);
        View book_button = inflater.inflate(R.layout.button_command, null);
        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Appointment appointment = new Appointment(tutor);
                StringBuffer skills = new StringBuffer();
                for (int i = 0; i < llWDYWTL.getChildCount(); i++) {
                    TextView tv = (TextView) llWDYWTL.getChildAt(i);
                    if (tv.getCurrentTextColor() == getResources().getColor(R.color.GreenIntellitap)) {
                        skills.append(tv.getText());
                        if (i != llWDYWTL.getChildCount() - 1) {
                            skills.append(",");
                        }
                    }
                }
                appointment.dayAndDate = "Monday , Jan 1st 2015";
                appointment.startEndTime = "8:00 - 9:00";
                appointment.expertise = skills.toString();
                appointment.invited_user = invited_user;
                appointment.location = etMeetingPlace.getText().toString();


                ((MainActivity) getActivity()).user.booked_appoinment.add(appointment);

                ((MainActivity)getActivity()).replaceFragments(new PageHome(),false,PageHome.FRAGMENT_TAG);
            }
        });

        View[] views = new View[]{area_wdywtl, calendar_card, area_meeting_place, area_invite_people, book_button};

        LinearLayout llMain = (LinearLayout) v.findViewById(R.id.llMain);
        for (View v_ : views) {
            llMain.addView(v_);
        }


        return v;
    }

    private View buildInvitePeopleArea(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.area_invite_people, null);

        final LinearLayout llFriendList = (LinearLayout) v.findViewById(R.id.llFriendsList);


        final AutoCompleteTextView atv = (AutoCompleteTextView) v.findViewById(R.id.atvInvitePeople);
        final String[] names = new String[]{"Karthik Konath", "Mark Daniel", "Rakan Stanboully"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, names);
        atv.setAdapter(adapter);
        ((MainActivity) getActivity()).tfi.setTypeface(atv, TypefaceIntellitap.ROBOTO_BOLD);

        atv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = ((TextView) view).getText().toString();
                View friend_listing = buildFriendList(name);
                llFriendList.addView(friend_listing);
                atv.setText("");

                User u = new User();
                String[] full_name = name.split(" ");
                u.firstName = full_name[0];
                u.lastName = full_name[1];
                invited_user.add(u);
            }

            public View buildFriendList(String name) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.friend_listing, null);
                ImageView userPicture = (ImageView) v.findViewById(R.id.ivUserPicture);
                MyTextView tvUserName = (MyTextView) v.findViewById(R.id.tvUserName);
                tvUserName.setText(name);
                return v;
            }

        });


        return v;
    }

    private View buildMeetingPlaceArea(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.area_meeting_place, null);
        etMeetingPlace = (EditText) v.findViewById(R.id.etMeetingPlace);
        TypefaceIntellitap tfi = ((MainActivity) getActivity()).tfi;
        tfi.setTypeface(etMeetingPlace, TypefaceIntellitap.ROBOTO_BOLD);
        return v;
    }

    private View buildWDYWTLArea(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.area_wdwtl, null);
        llWDYWTL = (LinearLayout) v.findViewById(R.id.llWdwtl);
        String[] skills = tutor.skills.split(",");
        TextView[] tvs = new TextView[skills.length];
        int i = 0;
        for (TextView tv : tvs) {
            tv = createTextView(skills[i]);
            if (tvs.length == 1) {
                tv.setTextColor(getResources().getColor(R.color.GreenIntellitap));
                ((MainActivity) getActivity()).tfi.setTypeface(tv, TypefaceIntellitap.ROBOTO_BOLD);
            }
            llWDYWTL.addView(tv);
            i++;
        }

        return v;
    }

    private TextView createTextView(String text) {
        TypefaceIntellitap tfi = ((MainActivity) getActivity()).tfi;
        TextView tv = new TextView(getActivity());
        tv.setText(text);
        tfi.setTypeface(tv, TypefaceIntellitap.ROBOTO);
        tv.setTextColor(getResources().getColor(R.color.BlueIntellitap));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                TypefaceIntellitap tfi = ((MainActivity) getActivity()).tfi;
                if (tv.getCurrentTextColor() == getResources().getColor(R.color.BlueIntellitap)) {
                    tfi.setTypeface(tv, TypefaceIntellitap.ROBOTO_BOLD);
                    changeTextViewColor(tv, getResources().getColor(R.color.BlueIntellitap), getResources().getColor(R.color.GreenIntellitap));
                } else {
                    tfi.setTypeface(tv, TypefaceIntellitap.ROBOTO);
                    changeTextViewColor(tv, getResources().getColor(R.color.GreenIntellitap), getResources().getColor(R.color.BlueIntellitap));
                }
            }
        });
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        return tv;
    }

    private void changeTextViewColor(final TextView tv, int colorFrom, int colorTo) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tv.setTextColor((int) animation.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

    private class PageBookingAdapter extends BaseAdapter {

        private View[] views;

        public PageBookingAdapter(View[] views) {
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_YES) {
            Log.d(getClass().getName(), "Inside yes keyboard hidden");
            rlBook.setVisibility(View.VISIBLE);
        } else if (newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_NO) {
            Log.d(getClass().getName(), "Inside no keyboard hidden");
            rlBook.setVisibility(View.INVISIBLE);
        }
    }
}
