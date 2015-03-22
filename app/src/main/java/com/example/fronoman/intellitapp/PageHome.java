package com.example.fronoman.intellitapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fron.customviews.MyTextView;


import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;

/**
 * Created by Fahran on 1/25/2015.
 */
public class PageHome extends Fragment {
    private ListView lvMain;
    private float scaleDP;

    public static String FRAGMENT_TAG = "page_home";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarColor(getResources().getColor(R.color.RedIntellitap));
        ((MainActivity) getActivity()).setActionBarTitle("Homepage");
        scaleDP = ((MainActivity) getActivity()).scaleDP;


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (25 * scaleDP + 0.5f), (int) (25 * scaleDP + 0.5f));
        ImageView action_bar_action = new ImageView(getActivity());
        action_bar_action.setImageResource(R.drawable.searchicon);
        action_bar_action.setLayoutParams(params);
        action_bar_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragments(new PageSearch(), true, PageSearch.FRAGMENT_TAG);
            }
        });
        ((MainActivity) getActivity()).setActionBarAction(action_bar_action);

        View v = inflater.inflate(R.layout.page_home, null);
        lvMain = (ListView) v.findViewById(R.id.lvMain);

        View pmd = buildProviderMetaData(inflater);

        User user = ((MainActivity) getActivity()).user;
        View booked_appointment_board = new View(getActivity());
        if (user.booked_appoinment != null && user.booked_appoinment.size() > 0) {
            booked_appointment_board = buildBookedAppointment(inflater, user);
        }

        HomepageBoards teaching_request_board = buildTeachingRequest(inflater);

        HomepageBoards chat_notif = buildChatNotif(inflater);

        HomepageBoards weekly_summary = buildWeeklySummary(inflater);
        View[] views = new View[]{pmd, booked_appointment_board, teaching_request_board, chat_notif, weekly_summary};
        PageHomeAdapter phadapter = new PageHomeAdapter(views);
        lvMain.setAdapter(phadapter);


        return v;
    }

    public HomepageBoards buildBookedAppointment(LayoutInflater inflater, User user) {
        HomepageBoards hpb = new HomepageBoards(getActivity());
        hpb.hideNotif();
        ArrayList<Appointment> appointments = user.booked_appoinment;
        ArrayList<View> appointments_view = new ArrayList<>();
        for (Appointment ap : appointments) {
            appointments_view.add(buildAppointmentItem(inflater, ap));
        }

        hpb.setTitle("Approved tutoring request");
        hpb.setImageTitle(R.drawable.tutoring_approved_icon);
        hpb.setViewContents(appointments_view);

        return hpb;

    }

    public View buildProviderMetaData(LayoutInflater inflater) {
        UserMetaData v = new UserMetaData(getActivity(), ((MainActivity) getActivity()).user);
        v.loadProfilePicture();
        return v;
    }

    public HomepageBoards buildTeachingRequest(LayoutInflater inflater) {
        StopWatch s = new StopWatch();
        HomepageBoards teaching_request = new HomepageBoards(getActivity());
        teaching_request.setImageTitle(R.drawable.tutoring_request_icon);
        teaching_request.showNotif();
        teaching_request.setNotifCounter(5);

        String[] names = new String[]{"Mark Daniel", "Rakan Stanboully", "Karthik Konath", "Taylor Swift"};
        String[] dayAndDates = new String[]{"Monday, Jan 1st 2015", "Monday, Jan 1st 2015", "Tuesday, Jan 2nd 2015", "Tuesday, Jan 2nd 2015"};
        String[] times = new String[]{"07:30 - 08:30", "09:30 - 11:30", "09:30 - 11:30", "12:30 - 14:30"};
        String[] locations = new String[]{"View Point Apartments, West Campus", "San Jacinto Drom, UT Campus", "Frost Tower, Downtown", "Frank Erwin, UT Campus"};

        ArrayList<View> views = new ArrayList<>();

        s.start();
        for (int i = 0; i < names.length; i++) {
            User user = new User();
            String[] fullName = names[i].split(" ");
            user.firstName = fullName[0];
            user.lastName = fullName[1];


            Appointment appointment = new Appointment(user);
            appointment.dayAndDate = dayAndDates[i];
            appointment.startEndTime = times[i];
            appointment.location = locations[i];
            // TODO GACKY AND FAKE ANYWAY
            appointment.invited_user.add(user);


            views.add(buildAppointmentItem(inflater, appointment));
            s.split();
            Log.d("Homepage performance:", "After build appointment item = " + s.getSplitTime());
        }
        s.split();
        Log.d("Homepage performance:", "After filling appointments item loop = " + s.getSplitTime());
        s.stop();

        teaching_request.setViewContents(views);


        return teaching_request;
    }

    private View buildAppointmentItem(LayoutInflater inflater, Appointment appointment) {
        View v = inflater.inflate(R.layout.area_teaching_request_item, null);
        MyTextView tvName = (MyTextView) v.findViewById(R.id.tvName);
        tvName.setText(appointment.user.getFullName());

        MyTextView tvSubject = (MyTextView) v.findViewById(R.id.tvSubject);
        if (appointment.user instanceof Tutor) {
            StringBuffer subject_chosen = new StringBuffer();
            int i = 0;
            for (Skill s : appointment.skills) {
                subject_chosen.append(s.skillName);
                Log.d("Appointment error", "Skill name = " + s.skillName);
                if (i != appointment.skills.size() - 1) {
                    subject_chosen.append(", ");
                }
                i++;
            }
            tvSubject.setText(subject_chosen.toString());
        } else {
            tvSubject.setVisibility(View.GONE);
        }


        MyTextView tvTotalOtherStudents = (MyTextView) v.findViewById(R.id.tvTotalOtherStudent);
        if (appointment.invited_user.size() > 0)
            tvTotalOtherStudents.setVisibility(View.GONE);

        MyTextView tvDayAndDate = (MyTextView) v.findViewById(R.id.tvDayAndDate);
        tvDayAndDate.setText(appointment.dayAndDate);

        MyTextView tvTime = (MyTextView) v.findViewById(R.id.tvTime);
        tvTime.setText(appointment.startEndTime);

        MyTextView tvLocation = (MyTextView) v.findViewById(R.id.tvLocation);
        tvLocation.setText(appointment.location);

        return v;
    }

    private HomepageBoards buildWeeklySummary(LayoutInflater inflater) {
        HomepageBoards weekly_summary = new HomepageBoards(getActivity());

        weekly_summary.setTitle("Weekly Summary");
        weekly_summary.setImageTitle(R.drawable.document_icon);
        weekly_summary.hideNotif();
        weekly_summary.setContentOrientation(LinearLayout.HORIZONTAL);


        ArrayList<View> view_content = new ArrayList<>();

        int[] weekly_summary_icon = new int[]{R.drawable.dollar_icon, R.drawable.clock_icon, R.drawable.teach_icon};
        String[] weekly_summary_content = new String[]{"$450", "10 hr", "5 classes"};
        String[] weekly_summary_description = new String[]{"money made", "working hours", "classes teached"};
        int[] text_color = new int[]{getResources().getColor(R.color.BlueIntellitap), getResources().getColor(R.color.Gray4d), getResources().getColor(R.color.Gray4d)};

        for (int i = 0; i < weekly_summary_icon.length; i++) {
            View weekly_summary_item = inflater.inflate(R.layout.area_weekly_summary_item, null);
            ImageView ivSummaryIcon = (ImageView) weekly_summary_item.findViewById(R.id.ivSummaryIcon);
            ivSummaryIcon.setImageResource(weekly_summary_icon[i]);

            TextView tvSummaryContent = (TextView) weekly_summary_item.findViewById(R.id.tvSummaryContent);
            tvSummaryContent.setText(weekly_summary_content[i]);
            tvSummaryContent.setTextColor(text_color[i]);

            TextView tvSummaryDescription = (TextView) weekly_summary_item.findViewById(R.id.tvSummaryDescription);
            tvSummaryDescription.setText(weekly_summary_description[i]);

            if (i != weekly_summary_icon.length - 1) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i == 0) {
                    params.rightMargin = (int) (20 * scaleDP + 0.5f);
                } else
                    params.rightMargin = (int) (13 * scaleDP + 0.5f);
                weekly_summary_item.setLayoutParams(params);
            }


            view_content.add(weekly_summary_item);
        }

        weekly_summary.setViewContents(view_content);

        return weekly_summary;


    }

    private HomepageBoards buildChatNotif(LayoutInflater inflater) {
        HomepageBoards h = new HomepageBoards(getActivity());
        h.setTitle("Chat");
        h.setImageTitle(R.drawable.message_notif_icon);
        h.setNotifCounter(10);


        ArrayList<View> views = new ArrayList<>();
        String[] names = new String[]{"Ariana Grande", "Taylor Swift", "Linnea Dale", "test"};
        String[] msg_preview = new String[]{"You are the coolest dude!", "We should go to cat cafe...", "Can I come by to your apart...", "test"};

        for (int i = 0; i < names.length; i++) {
            View chat_preview_view = inflater.inflate(R.layout.area_chat_preview, null);
            MyTextView tv_name = (MyTextView) chat_preview_view.findViewById(R.id.tvName);
            MyTextView tv_chat_preview = (MyTextView) chat_preview_view.findViewById(R.id.tvChatPreview);
            tv_name.setText(names[i]);
            tv_chat_preview.setText(msg_preview[i]);
            views.add(chat_preview_view);
        }

        h.setViewContents(views);
        return h;
    }

    private class PageHomeAdapter extends BaseAdapter {

        private View[] views;

        public PageHomeAdapter(View[] views) {
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
