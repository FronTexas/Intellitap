package com.example.fronoman.intellitap;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.MyTextView;
import com.example.fron.customviews.TypefaceIntellitap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Fahran on 1/15/2015.
 */
public class PageBooking extends Fragment {

    public static final String FRAGMENT_TAG = "pagebooking";
    private RelativeLayout rlBook;
    private Tutor tutor;

    private AutoCompleteTextView atvMeetingPlace;

    private LinearLayout llWDYWTL;
    private ArrayList<User> invited_user;

    private static final String LOG_TAG = "Intellitapp";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyBjXLMCAeR_cRygd1BtHxFAYWXPAhpalCI";

    private ArrayList<String> resultList_global;


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

                for (int i = 0; i < llWDYWTL.getChildCount(); i++) {
                    TextView tv = (TextView) llWDYWTL.getChildAt(i);
                    if (tv.getCurrentTextColor() == getResources().getColor(R.color.GreenIntellitap)) {
                        Skill skill = new Skill();
                        skill.skillName = tv.getText().toString();
                        appointment.skills.add(skill);
                    }
                }
                appointment.dayAndDate = "Monday , Jan 1st 2015";
                appointment.startEndTime = "8:00 - 9:00";
                appointment.invited_user = invited_user;
                appointment.location = atvMeetingPlace.getText().toString();


                ((MainActivity) getActivity()).user.booked_appoinment.add(appointment);

                ((MainActivity) getActivity()).replaceFragments(new PageHome(), false, PageHome.FRAGMENT_TAG);
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
        resultList_global = new ArrayList<>();
        atvMeetingPlace = (AutoCompleteTextView) v.findViewById(R.id.atvMeetingPlace);
        atvMeetingPlace.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_textview));
        TypefaceIntellitap tfi = ((MainActivity) getActivity()).tfi;
        tfi.setTypeface(atvMeetingPlace, TypefaceIntellitap.ROBOTO_BOLD);
        return v;
    }

    private View buildWDYWTLArea(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.area_wdwtl, null);
        llWDYWTL = (LinearLayout) v.findViewById(R.id.llWdwtl);
        ArrayList<Skill> skills = tutor.skills;
        TextView[] tvs = new TextView[skills.size()];
        int i = 0;
        for (TextView tv : tvs) {
            tv = createTextView(skills.get(i).skillName);
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


    private class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

        public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList_global.size();
        }

        @Override
        public String getItem(int index) {
            return resultList_global.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    Log.d("Google places", "Getting into perform filtering");
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        Log.d("Google places", " inside constraint!");

                        Connection con = new Connection(constraint.toString());
                        con.execute();

                        Log.d("Google places", " inside constraint : resultList_global = " + resultList_global);


                        // Assign the data to the FilterResults
                        filterResults.values = resultList_global;
                        filterResults.count = resultList_global.size();

                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    Log.d("Google places", "Getting into publish results");
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
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

    private class Connection extends AsyncTask {
        private String input;

        public Connection(String input) {
            this.input = input;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            resultList_global = autocomplete(input);
            Log.d("Google places api error", "Results list global in asynctask = " + resultList_global);
            return resultList_global;
        }

        private ArrayList<String> autocomplete(String input) {
            ArrayList<String> resultList = null;

            HttpURLConnection conn = null;
            StringBuilder jsonResults = new StringBuilder();
            try {
                StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
                sb.append("?key=" + API_KEY);
                sb.append(";=country:us");
                sb.append("&input;=" + URLEncoder.encode(input, "utf8"));


                URL url = new URL(sb.toString());
                conn = (HttpURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(conn.getInputStream());

                // Load the results into a StringBuilder
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    Log.d("Google places api error", "inside red buffer 1024");
                    jsonResults.append(buff, 0, read);
                }
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error processing Places API URL", e);
                return resultList;
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error connecting to Places API", e);
                return resultList;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            try {
                // Create a JSON object hierarchy from the results
                JSONObject jsonObj = new JSONObject(jsonResults.toString());
                JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

                // Extract the Place descriptions from the results
                resultList = new ArrayList<String>(predsJsonArray.length());
                for (int i = 0; i < predsJsonArray.length(); i++) {
                    resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Cannot process JSON results", e);
            }

            for (String s : resultList) {
                Log.d("Google places", "s = " + s);
            }
            return resultList;
        }
    }

}


