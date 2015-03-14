package com.example.fronoman.intellitap;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.MyTextView;
import com.example.fron.customviews.TypefaceIntellitap;
import com.example.tinyclass.DateIntellitapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Fahran on 2/21/2015.
 */
public class CalendarCard extends LinearLayout implements WeekDates.DateSelectedListener {

    int today_month, today_day_week, today_day_month, today_year;
    long today_millisecond;

    private final String[] days = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
    private final String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    private final int[] monthTotalDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private LinearLayout llCalendarCard;
    private GridView gvDays;
    private ViewPager pagerDates;

    private boolean turnOffOnClickListener;

    // Month in the top of the card
    private MyTextView tvMonth;

    //    Day Information , includes day selected and timeslot
    private MyTextView tvStateOfDay, tvDayAndDate;
    private GridView gvTimeSlots;

    private ArrayList<ArrayList<DateIntellitapp>> dates_arraylist_list;
    PagerDatesAdapter pagerDatesAdapter;


    public CalendarCard(Context context) {
        super(context);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        today_month = cal.get(Calendar.MONTH);
        today_day_week = cal.get(Calendar.DAY_OF_WEEK);
        today_day_month = cal.get(Calendar.DAY_OF_MONTH);
        today_year = cal.get(Calendar.YEAR);

        String today_mdy = today_month + 1 + "." + today_day_month + "." + today_year;
        if (today_month % 10 == today_month) {
            today_mdy = "0" + today_mdy;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
        Date dateInstance = null;
        try {
            dateInstance = sdf.parse(today_mdy);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        today_millisecond = dateInstance.getTime();

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


        // get dates in the first week
        ArrayList<DateIntellitapp> date_arraylist = dates_arraylist_list.get(0);

        // get date intellitapp for exactly todays date
        DateIntellitapp di = date_arraylist.get(today_day_week - 1);

        // get Today's timeslots
        ArrayList<TimeSlot> timeSlots = di.getTimeSlots();
        buildTimeSlots(timeSlots);

    }

    public void buildTimeSlots(ArrayList<TimeSlot> timeSlots) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View[] time_slot_item = new View[timeSlots.size()];


        for (int i = 0; i < time_slot_item.length; i++) {
            time_slot_item[i] = inflater.inflate(R.layout.time_slot_item, null);
            MyTextView tvTime = (MyTextView) time_slot_item[i].findViewById(R.id.tvTime);
            MyTextView tvDuration = (MyTextView) time_slot_item[i].findViewById(R.id.tvDuration);


            tvTime.setText(timeSlots.get(i).time);
            tvDuration.setText(timeSlots.get(i).duration);
        }
        gvTimeSlots.setAdapter(new CalendarAdapter(time_slot_item));
        if (!turnOffOnClickListener)
            gvTimeSlots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    View selected_red_bar = view.findViewById(R.id.selected_red_bar);
                    Log.d("Red selector problem", "selected color = " + view.getTag() + ", resource red = " + getResources().getColor(R.color.RedIntellitap));
                    if (view.getTag() != null && (int) view.getTag() - getResources().getColor(R.color.RedIntellitap) == 0) {
                        changeColorAnimation(selected_red_bar, (int) view.getTag(), getResources().getColor(android.R.color.white));
                        view.setTag(getResources().getColor(android.R.color.white));
                    } else {
                        changeColorAnimation(selected_red_bar, selected_red_bar.getSolidColor(), getResources().getColor(R.color.RedIntellitap));
                        view.setTag(getResources().getColor(R.color.RedIntellitap));
                    }
                }
            });
    }

    public void turnOffTimeSlotClickListener() {
        turnOffOnClickListener = true;
        if (gvTimeSlots != null) {
            gvTimeSlots.setOnItemClickListener(null);
        }
    }

    public void changeColorAnimation(final View v, int colorFrom, int colorTo) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }


    public void fillPagerDates() {
        ArrayList<DateIntellitapp> dates = new ArrayList<>();
        int left = totalLeft(today_day_week);
        int n = today_day_month - left;

        String[] time_slot = {"09:00", "11:00", "13:00", "15:00", "17:00"};
        String[] duration = {"1h", "1h", "1h", "1h", "1h"};
        ArrayList<TimeSlot> time_slots = new ArrayList<>();

        for (int i = 0; i < time_slot.length; i++) {
            TimeSlot ts = new TimeSlot();
            ts.duration = duration[i];
            ts.time = time_slot[i];
            time_slots.add(ts);
        }


        for (int i = n; i < n + 14; i++) {
            int month;
            int day_month;
            int year = 0;
            if (i <= 0) {
                // go to previous month
                month = today_month - 1;

                // if month == 0 then the prev month is  and the year is moved back for a year
                if (month == -1) {
                    month = 11;
                    year -= 1;
                }
                day_month = monthTotalDays[month] - i;

            } else if (i > monthTotalDays[today_month]) {
                month = today_month + 1;

                // if month == 12 then the next month is January and the year is moved front for a year
                if (month == 12) {
                    month = 0;
                    year += 1;
                }
                day_month = i - monthTotalDays[today_month] + 1;

            } else {
                month = today_month;
                day_month = i;
                year = today_year;
            }


            DateIntellitapp date = new DateIntellitapp(day_month, month, year, true);
            date.setTimeSlots(time_slots);
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

    @Override
    public void onDateSelectedListener(DateIntellitapp date_selected) {
        tvStateOfDay.setVisibility(View.VISIBLE);
        try {
            if (today_millisecond - date_selected.getDateInMilli() == 0) {
                tvStateOfDay.setText("Today");
            } else if (today_millisecond - date_selected.getDateInMilli() == -82800000 || today_millisecond - date_selected.getDateInMilli() == -86400000) {
                tvStateOfDay.setText("Tommorow");
            } else if (today_millisecond - date_selected.getDateInMilli() == 82800000 || today_millisecond - date_selected.getDateInMilli() == 86400000) {
                tvStateOfDay.setText("Yesterday");
            } else {
                tvStateOfDay.setVisibility(View.GONE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date date = new Date();
        try {
            date.setTime(date_selected.getDateInMilli());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        tvDayAndDate.setText(days[cal.get(Calendar.DAY_OF_WEEK) - 1] + " , " + months[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.DAY_OF_MONTH));
        buildTimeSlots(date_selected.getTimeSlots());

    }

    class PagerDatesAdapter extends FragmentStatePagerAdapter {


        public PagerDatesAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            // A list of all dates in a week from Sunday to Saturday
            WeekDates week_dates = new WeekDates();

            // listening for a calendar click
            week_dates.setOnDateSelectedListener(CalendarCard.this);

            Bundle b = new Bundle();
            b.putParcelableArrayList(C.DATES_ARRAYLIST_KEY, dates_arraylist_list.get(position));
            week_dates.setArguments(b);
            return week_dates;
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
