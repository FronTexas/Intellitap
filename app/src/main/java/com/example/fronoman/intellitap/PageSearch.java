package com.example.fronoman.intellitap;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.fron.customviews.TypefaceIntellitap;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Fahran on 1/12/2015.
 */
public class PageSearch extends Fragment implements PageSearchHeader.OnSearchListener {

    public static final String FRAGMENT_TAG = "page_search";
    private TypefaceIntellitap tfi;
    private PageSearchHeader pageSearchHeader;
    private ListView lvSearchPage;
    private EditText etSearchBox;
    private SearchPageAdapter searchAdapter;


    private List<Tutor> tutors;


    IntellitappService service;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_search, null);
        ((MainActivity) getActivity()).setActionBarColor(getResources().getColor(R.color.BlueIntellitap));
        ((MainActivity) getActivity()).setActionBarTitle("Search");

        service = ((MainActivity) getActivity()).service;


        tfi = ((MainActivity) getActivity()).tfi;
        searchAdapter = new SearchPageAdapter();

        lvSearchPage = (ListView) v.findViewById(R.id.lvSearchPage);

        pageSearchHeader = new PageSearchHeader(getActivity(), this);

        lvSearchPage.addHeaderView(pageSearchHeader);


        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(searchAdapter);
        scaleInAnimationAdapter.setAbsListView(lvSearchPage);
        lvSearchPage.setAdapter(scaleInAnimationAdapter);

        return v;
    }


    public void onSearch(HashMap<String, String> queries) {

        // To reset search results
        searchAdapter.plcs = new ArrayList<>();
        searchAdapter.notifyDataSetChanged();

        String skill, identifier, city, state;

        skill = null;
        identifier = null;
        city = null;
        state = null;

        if (queries.get("skill") != null) {
            skill = queries.get("skill");
        }
        if (queries.get("identifier") != null) {
            identifier = queries.get("identifier");
        }
        if (queries.get("city") != null) {
            city = queries.get("city");
        }

        Log.d("Queries", "skill = " + skill + ", identifier = " + identifier + ", city = " + city + ", state = " + state);


        // fill provider_listing_cards and conncect it with respective user object
        service.listTutors(skill, identifier, city, state, new Callback<ArrayList<Tutor>>() {
            @Override
            public void success(ArrayList<Tutor> tutors, Response response) {
                ArrayList<View> searc_page_results = new ArrayList<>();
                if (tutors.size() == 0) {
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View unicorn = inflater.inflate(R.layout.unicorn, null);
                    searc_page_results.add(unicorn);

                } else {
                    for (final Tutor t : tutors) {
                        ProviderListingCard plc = new ProviderListingCard(getActivity(), t);
                        plc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PageProviderProfile pp = new PageProviderProfile();
                                Bundle b = new Bundle();
                                b.putParcelable(C.TUTOR_KEY, t);
                                pp.setArguments(b);
                                ((MainActivity) getActivity()).replaceFragments(pp, true, PageProviderProfile.FRAGMENT_TAG);
                            }
                        });
                        searc_page_results.add(plc);
                    }
                }
                searchAdapter.plcs = searc_page_results;
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Searching tutors", "retrofit error = " + error.toString());
                ArrayList<View> searc_page_results = new ArrayList<>();
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View unicorn = inflater.inflate(R.layout.unicorn, null);
                searc_page_results.add(unicorn);

                searchAdapter.plcs = searc_page_results;
                searchAdapter.notifyDataSetChanged();

            }
        });

    }


    private class SearchPageAdapter extends BaseAdapter {

        public ArrayList<View> plcs;

        @Override
        public int getCount() {
            if (plcs == null) return 0;
            else
                return plcs.size();
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
            if (plcs.get(position) instanceof ProviderListingCard)
                ((ProviderListingCard) plcs.get(position)).loadProfilePhoto();
            return plcs.get(position);
        }
    }


}
