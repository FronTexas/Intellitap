package com.example.fronoman.intellitap;

import android.os.Bundle;
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
import com.example.tinyclass.Dates;

import java.util.ArrayList;

/**
 * Created by Fahran on 2/21/2015.
 */
public class CalendarDates extends Fragment {

    private GridView gvDates;

    // array that contains the list of dates in a particular week.
    ArrayList<Dates> dates;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calendar_dates_fragment, null);

        dates = getArguments().getParcelableArrayList(C.DATES_ARRAYLIST_KEY);

        initializeViews(v);


        return v;
    }

    public void initializeViews(View v) {
        gvDates = (GridView) v.findViewById(R.id.gvDates);
        if (dates != null)
            fillDates();
    }

    public void fillDates() {
        RelativeLayout[] dates_r = new RelativeLayout[dates.size()];
        TypefaceIntellitap tfi = ((MainActivity) getActivity()).tfi;
        for (int i = 0; i < 7; i++) {
            RelativeLayout r = new RelativeLayout(getActivity());
            TextView tvDates = new TextView(getActivity());
            tvDates.setText(dates.get(i).getDate());

            if (dates.get(i).isTutorAvailable()) {
                tfi.setTypeface(tvDates, TypefaceIntellitap.ROBOTO_BOLD);
                tvDates.setTextColor(getResources().getColor(R.color.GreenIntellitap));
                tvDates.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            } else {
                tfi.setTypeface(tvDates, TypefaceIntellitap.ROBOTO);
            }
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            tvDates.setLayoutParams(params);
            r.addView(tvDates);
            dates_r[i] = r;
        }

        gvDates.setAdapter(new DatesAdapter(dates_r));
    }

    class DatesAdapter extends BaseAdapter{
        RelativeLayout[]  dates_r;

        public DatesAdapter(RelativeLayout[] dates_r){
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
}

