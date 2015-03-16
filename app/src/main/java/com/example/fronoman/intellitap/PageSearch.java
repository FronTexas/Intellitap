package com.example.fronoman.intellitap;

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

    RestAdapter adapter;
    IntellitappService service;

    private List<Tutor> tutors;

    //TODO remove this once search service already done. This is fake!
    private List<Tutor> fake_tutors;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //TODO remove this once search service already done. This is fake!
//        buildFakeTutors();


        View v = inflater.inflate(R.layout.page_search, null);
        ((MainActivity) getActivity()).setActionBarColor(getResources().getColor(R.color.BlueIntellitap));
        ((MainActivity) getActivity()).setActionBarTitle("Search");

        adapter = new RestAdapter.Builder()
                .setEndpoint(C.MAIN_URL)
                .build();

        service = adapter.create(IntellitappService.class);


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

//    public void buildFakeTutors() {
//        fake_tutors = new ArrayList<>();
//        String[] names = new String[]{"Larry Page", "Stephen Hawking", "Richard Hendricks", "Elon Musk", "Linus Trovalds"};
//        String[] institutes = new String[]{"Google", "Cambridge University", "Pied Piper", "Tesla motors", "University Of Helsinki"};
//        String[] skills = new String[]{"Algorithm Design", "Astrophysics", "Compression Algorithm", "How to be awesome", "Operating System (OS)"};
//        String[] city = new String[]{"Mountain View , CA", "Trinity Lane , Cambridge", "Palo Alto, CA", "Palo Alto , CA", "San Fransisco , CA"};
//        String[] photoUrl = new String[]{"https://lh3.googleusercontent.com/-k2GLxHmgJJ0/UUCSHMw2XGI/AAAAAAADO1I/i9oZkWDgINY/w617-h618-no/LarryPage2012_03_edited.JPG", "http://goo.gl/kwAhFd", "http://goo.gl/qWGVzU", "https://pbs.twimg.com/profile_images/420314816444502016/xj5TnUsx.jpeg", "http://goo.gl/eUSjLO"};
//
//        for (int i = 0; i < names.length; i++) {
//            Tutor t = new Tutor();
//            String[] fullname = names[i].split(" ");
//
//            String firstname = fullname[0];
//            String lastname = fullname[1];
//
//            t.firstName = firstname;
//            t.lastName = lastname;
//
//            t.education = institutes[i];
//            t.skills = skills[i];
//            t.city = city[i];
//            t.profilePhotoUrl = photoUrl[i];
//            t.isTutor = true;
//
//
//            fake_tutors.add(t);
//        }
//
//    }

    public void onSearch(HashMap<String, String> queries) {

        String skill, identifier, city, state;

        skill = null;
        identifier = null;
        city = null;
        state = null;

        if (queries.get("skill") != null) {
            skill = queries.get("skill");
//            // TODO this is only temporary. Since the search service only works on city, other than city search i will just use fake data
//            populateListingCards("skill", skill);
        }

        if (queries.get("identifier") != null) {
            identifier = queries.get("identifier");
//            // TODO this is only temporary. Since the search service only works on city, other than city search i will just use fake data
//            populateListingCards("identifier", identifier);
        }

        if (queries.get("city") != null) {
            city = queries.get("city");
        }

        Log.d("Queries", "skill = " + skill + ", identifier = " + identifier + ", city = " + city + ", state = " + state);


        // fill provider_listing_cards and conncect it with respective user object
        service.listTutors(skill, identifier, city, state, new Callback<ArrayList<Tutor>>() {
            @Override
            public void success(ArrayList<Tutor> tutors, Response response) {
                Log.d("Searching tutuors", "response = " + response.toString());
                ArrayList<ProviderListingCard> provider_listing_cards = new ArrayList<>();
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
                    provider_listing_cards.add(plc);
                }
                searchAdapter.plcs = provider_listing_cards;
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Searching tutors", "retrofit error = " + error.toString());
            }
        });

    }

//    public void populateListingCards(String query_type, String query) {
//        Log.d("Populating listing cards with fake data ", "Getting into populateListingCards");
//        ArrayList<ProviderListingCard> plcs = new ArrayList<>();
//        for (final Tutor t : fake_tutors) {
//            if (query_type == "skill") {
//                if (t.skills.toLowerCase().contains(query.toLowerCase())) {
//                    ProviderListingCard plc = new ProviderListingCard(getActivity(), t);
//                    plc.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            PageProviderProfile pp = new PageProviderProfile();
//                            Bundle b = new Bundle();
//                            b.putParcelable(C.TUTOR_KEY, t);
//                            pp.setArguments(b);
//                            ((MainActivity) getActivity()).replaceFragments(pp, true, PageProviderProfile.FRAGMENT_TAG);
//                        }
//                    });
//                    plcs.add(plc);
//                }
//            } else if (query_type == "identifier") {
//                if (t.firstName.toLowerCase().contains(query.toLowerCase()) || t.lastName.toLowerCase().contains(query.toLowerCase())) {
//                    ProviderListingCard plc = new ProviderListingCard(getActivity(), t);
//                    plc.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            PageProviderProfile pp = new PageProviderProfile();
//                            Bundle b = new Bundle();
//                            b.putParcelable(C.TUTOR_KEY, t);
//                            pp.setArguments(b);
//                            ((MainActivity) getActivity()).replaceFragments(pp, true, PageProviderProfile.FRAGMENT_TAG);
//                        }
//                    });
//                    plcs.add(plc);
//                }
//            }
//        }
//
//        searchAdapter.plcs = plcs;
//        searchAdapter.notifyDataSetChanged();
//    }

    private class SearchPageAdapter extends BaseAdapter {

        public ArrayList<ProviderListingCard> plcs;

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
            plcs.get(position).loadProfilePhoto();
            return plcs.get(position);
        }
    }


}
