package com.example.fronoman.intellitap;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
public class CalendarDates extends Fragment {

    private GridView gvDates;

    // array that contains the list of dates in a particular week.
    ArrayList<DateIntellitapp> dates;

    // Refer to view of highlighted date in the dates arraylist
    private View highlighted_dates;

    private float scaleDP;

    @IdRes
    final int TV_DATES_ID = 555;

    @IdRes
    final int BLUE_CIRCLE_ID = 445;

    @IdRes
    final int RED_CIRCLE_ID = 446;

    private DateSelectedListener dateSelectedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calendar_dates_fragment, null);

        dates = getArguments().getParcelableArrayList(C.DATES_ARRAYLIST_KEY);
        scaleDP = ((MainActivity) getActivity()).scaleDP;

        try {
            initializeViews(v);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return v;
    }

    public void setOnDateSelectedListener(DateSelectedListener listener) {
        dateSelectedListener = listener;
    }

    public void initializeViews(View v) throws ParseException {
        gvDates = (GridView) v.findViewById(R.id.gvDates);
        if (dates != null)
            fillDates();
    }

    public void fillDates() throws ParseException {

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int today_month = cal.get(Calendar.MONTH);
        int today_day_month = cal.get(Calendar.DAY_OF_MONTH);
        int today_year = cal.get(Calendar.YEAR);

        String today_date = today_month + 1 + "." + today_day_month + "." + today_year;
        if (today_month % 10 == today_day_month)
            today_date = "0" + today_date;

        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
        Date dateInstance = sdf.parse(today_date);

        // todays mm/dd/yyyy in milliseconds
        long todaysdateMilli = dateInstance.getTime();

        final RelativeLayout[] dates_r = new RelativeLayout[dates.size()];
        final TypefaceIntellitap tfi = ((MainActivity) getActivity()).tfi;


        for (int i = 0; i < 7; i++) {
            DatesRelativeLayout r = new DatesRelativeLayout(getActivity());
            r.date = dates.get(i);

            TextView tvDates = new TextView(getActivity());
            tvDates.setId(TV_DATES_ID);
            tvDates.setText(dates.get(i).getDate());

            View blue_circle = new View(getActivity());
            blue_circle.setId(BLUE_CIRCLE_ID);

            View red_circle = null;


            int circle_size = 25;
            if (dates.get(i).getDateInMilli() == todaysdateMilli) {
                tvDates.setTextColor(getResources().getColor(android.R.color.white));

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (circle_size * scaleDP + 0.5f), (int) (circle_size * scaleDP + 0.5f));
                blue_circle.setLayoutParams(params);
                blue_circle.setBackground(getResources().getDrawable(R.drawable.circle_blue));
                params.addRule(RelativeLayout.CENTER_IN_PARENT);

                red_circle = new View(getActivity());
                red_circle.setId(RED_CIRCLE_ID);
                RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams((int) (circle_size * 3 * scaleDP + 0.5f), (int) (circle_size * 3 * scaleDP + 0.5f));
                params2.addRule(RelativeLayout.ABOVE, TV_DATES_ID);
                params2.addRule(RelativeLayout.CENTER_HORIZONTAL);
                red_circle.setLayoutParams(params2);
                red_circle.setBackground(getResources().getDrawable(R.drawable.circle_red));

                highlighted_dates = r;
            } else if (dates.get(i).isTutorAvailable()) {
                tfi.setTypeface(tvDates, TypefaceIntellitap.ROBOTO_BOLD);
                tvDates.setTextColor(getResources().getColor(R.color.GreenIntellitap));

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (circle_size * scaleDP + 0.5f), (int) (circle_size * scaleDP + 0.5f));
                blue_circle.setLayoutParams(params);
                blue_circle.setBackground(getResources().getDrawable(R.drawable.circle));
                params.addRule(RelativeLayout.CENTER_IN_PARENT);

            } else {
                tfi.setTypeface(tvDates, TypefaceIntellitap.ROBOTO);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (circle_size * scaleDP + 0.5f), (int) (circle_size * scaleDP + 0.5f));
                blue_circle.setLayoutParams(params);
                blue_circle.setBackground(getResources().getDrawable(R.drawable.circle));
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
            }


            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            tvDates.setLayoutParams(params);
            r.addView(tvDates);
            r.addView(blue_circle);
            tvDates.bringToFront();

            if (red_circle != null) {
                Log.d("Getting into red circle", "Getting into red circle");
                r.addView(red_circle);
                red_circle.bringToFront();
            }

            dates_r[i] = r;

            // when particular r is clicked it set the blue circle at highlighted dates to white and update the highlighted dates to the view that just
            // clicked
            r.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (highlighted_dates != null) {
                        highlighted_dates.findViewById(BLUE_CIRCLE_ID).setBackground(getResources().getDrawable(R.drawable.circle));
                        tfi.setTypeface((TextView) highlighted_dates.findViewById(TV_DATES_ID), TypefaceIntellitap.ROBOTO_BOLD);
                        ((TextView) highlighted_dates.findViewById(TV_DATES_ID)).setTextColor(getResources().getColor(R.color.GreenIntellitap));
                    }


                    v.findViewById(BLUE_CIRCLE_ID).setBackground(getResources().getDrawable(R.drawable.circle_blue));
                    tfi.setTypeface((TextView) v.findViewById(TV_DATES_ID), TypefaceIntellitap.ROBOTO);
                    ((TextView) v.findViewById(TV_DATES_ID)).setTextColor(getResources().getColor(android.R.color.white));
                    highlighted_dates = v;

                    dateSelectedListener.onDateSelectedListener(((DatesRelativeLayout) v).date);
                    Log.d("date on r", "" + ((DatesRelativeLayout) v).date);
                }
            });
        }

        gvDates.setAdapter(new DatesAdapter(dates_r));
    }

    class DatesAdapter extends BaseAdapter {
        RelativeLayout[] dates_r;

        public DatesAdapter(RelativeLayout[] dates_r) {
            this.dates_r = dates_r;
        }

        @Override
        public int getCount() {
            return dates_r.length;
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


            return dates_r[position];
        }
    }

    public interface DateSelectedListener {
        public void onDateSelectedListener(DateIntellitapp date_selected);
    }

    class DatesRelativeLayout extends RelativeLayout {
        DateIntellitapp date;

        public DatesRelativeLayout(Context context) {
            super(context);
        }
    }
}

