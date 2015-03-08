package com.example.fronoman.intellitap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.MyTextView;
import com.example.fron.customviews.TypefaceIntellitap;
import com.example.tinyclass.Dates;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Fahran on 2/21/2015.
 */
public class CalendarCard extends LinearLayout {

    int today_month, today_day_week, today_day_month;

    private final String[] days = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
    private final String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    private final int[] monthTotalDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private LinearLayout llCalendarCard;
    private GridView gvDays;
    private ViewPager pagerDates;

    // Month in the top of the card
    private MyTextView tvMonth;

    //    Day Information , includes day selected and timeslot
    private MyTextView tvStateOfDay, tvDayAndDate;
    private GridView gvTimeSlots;

    private ArrayList<ArrayList<Dates>> dates_arraylist_list;
    PagerDatesAdapter pagerDatesAdapter;

    public CalendarCard(Context context) {
        super(context);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        today_month = cal.get(Calendar.MONTH);
        today_day_week = cal.get(Calendar.DAY_OF_WEEK);
        today_day_month = cal.get(Calendar.DAY_OF_MONTH);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.card_calendar, null);

        dates_arraylist_list = new ArrayList<>();

        pagerDatesAdapter = new PagerDatesAdapter(((MainActivity) context).getSupportFragmentManager());


        initializeViews(v);
        fillDays();
        fillPagerDates();
        buildDayInformation(v);

        addView(v);
    }

    public void initializeViews(View v) {
        llCalendarCard = (LinearLayout) v.findViewById(R.id.llCalendarCard);
        gvDays = (GridView) v.findViewById(R.id.gvDays);
        pagerDates = (ViewPager) v.findViewById(R.id.pagerDates);
        tvMonth = (MyTextView) v.findViewById(R.id.tvMonth);
        tvMonth.setText(months[today_month]);
    }


    private void buildDayInformation(View v) {
        tvStateOfDay = (MyTextView) v.findViewById(R.id.tvStateOfDay);
        tvDayAndDate = (MyTextView) v.findViewById(R.id.tvDayAndDate);
        gvTimeSlots = (GridView) v.findViewById(R.id.gvTimeSlots);

        tvStateOfDay.setText("Today");
        tvDayAndDate.setText(days[today_day_week - 1] + " , " + months[today_month] + " " + today_day_month);


        View[] time_slot_item = new View[5];
        String[] time_slot = {"09:00", "11:00", "13:00", "15:00", "17:00"};
        String[] duration = {"1h", "1h", "1h", "1h", "1h"};

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < time_slot_item.length; i++) {
            time_slot_item[i] = inflater.inflate(R.layout.time_slot_item, null);
            MyTextView tvTime = (MyTextView) time_slot_item[i].findViewById(R.id.tvTime);
            MyTextView tvDuration = (MyTextView) time_slot_item[i].findViewById(R.id.tvDuration);
            tvTime.setText(time_slot[i]);
            tvDuration.setText(duration[i]);
        }
        for (View ts : time_slot_item) {
            Log.d("Calendar time slot populating error", "CTSPE -- TS = " + ts);
        }
        gvTimeSlots.setAdapter(new CalendarAdapter(time_slot_item));

    }


    public void fillPagerDates() {
        ArrayList<Dates> dates = new ArrayList<>();
        int left = totalLeft(today_day_week);
        int n = today_day_month - left;
        for (int i = n; i < n + 14; i++) {
            int month;
            int day_month;
            if (i <= 0) {
                // go to previous month
                month = today_month - 1;

                // if month == 0 then the prev month is December
                if (month == -1) {
                    month = 11;
                }
                day_month = monthTotalDays[month] - i;

            } else {
                month = today_month;
                day_month = i;
            }



            Dates date = new Dates("" + (day_month), months[month], true);
            dates.add(date);

            // if its finishing first week or if its finishing 2nd week
            if (i == n + 6 || i == n + 13) {
                dates_arraylist_list.add(dates);
                dates = new ArrayList<>();
            }
        }
        pagerDates.setAdapter(pagerDatesAdapter);
    }

    /*@return
        how many dates are in the left of current date */
    public int totalLeft(int day_week) {
        return day_week - 1;
    }

    public void fillDays() {
        String[] days = new String[]{"S", "M", "T", "W", "T", "F", "S"};
        RelativeLayout[] days_r = new RelativeLayout[days.length];
        TypefaceIntellitap tfi = ((MainActivity) getContext()).tfi;
        for (int i = 0; i < 7; i++) {
            RelativeLayout r = new RelativeLayout(getContext());
            TextView tvday = new TextView(getContext());
            tvday.setText(days[i]);
            tfi.setTypeface(tvday, TypefaceIntellitap.ROBOTO_BOLD);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            tvday.setLayoutParams(params);
            r.addView(tvday);
            days_r[i] = r;
        }

        gvDays.setAdapter(new CalendarAdapter(days_r));
    }

    class PagerDatesAdapter extends FragmentStatePagerAdapter {


        public PagerDatesAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            // A list of all dates in a week from Sunday to Saturday
            CalendarDates cdates = new CalendarDates();
            Bundle b = new Bundle();
            b.putParcelableArrayList(C.DATES_ARRAYLIST_KEY, dates_arraylist_list.get(position));
            cdates.setArguments(b);
            return cdates;
        }

        @Override
        public int getCount() {
            // TODO fix this to be more dynamic
            return dates_arraylist_list.size();
        }
    }

    class CalendarAdapter extends BaseAdapter {

        private View[] views;

        public CalendarAdapter(View[] views) {
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
