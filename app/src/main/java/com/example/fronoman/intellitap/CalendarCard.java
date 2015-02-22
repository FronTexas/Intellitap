package com.example.fronoman.intellitap;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.TypefaceIntellitap;
import com.example.tinyclass.Dates;

import java.util.ArrayList;

/**
 * Created by Fahran on 2/21/2015.
 */
public class CalendarCard extends LinearLayout {
    private LinearLayout llCalendarCard;
    private GridView gvDays;
    private ViewPager pagerDates;

    private ArrayList<ArrayList<Dates>> dates_arraylist_list;

    public CalendarCard(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.card_calendar, null);


        initializeViews(v);
        fillDays();
        fillPagerDates();


        addView(v);
    }

    public void initializeViews(View v) {
        llCalendarCard = (LinearLayout) v.findViewById(R.id.llCalendarCard);
        gvDays = (GridView) v.findViewById(R.id.gvDays);
    }

    public void fillPagerDates() {
        ArrayList<Dates> dates = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            if (i == 6) {
                dates_arraylist_list.add(dates);
                dates = new ArrayList<>();
            }
            Dates date = new Dates("" + i, "February", true);
            dates.add(date);
        }
        pagerDates.setAdapter(new PagerDatesAdapter());
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
            Bundle b = new Bundle();
            b.putParcelableArrayList(C.DATES_ARRAYLIST_KEY, dates_arraylist_list.get(position));
            CalendarDates cdates = new CalendarDates();
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
