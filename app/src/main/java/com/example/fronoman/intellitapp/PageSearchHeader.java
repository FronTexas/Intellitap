package com.example.fronoman.intellitapp;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fron.customviews.TypefaceIntellitap;

import java.util.HashMap;

/**
 * Created by Fahran on 1/12/2015.
 */
public class PageSearchHeader extends LinearLayout {
    private EditText etSearchBox;
    private LinearLayout llRecentSearch;
    private TextView tvRecentSearchText;
    private RelativeLayout rlName, rlSkills, rlLocation;
    private TextView tvName, tvSkills, tvLocation;
    private View name_selector, skills_selector, location_selector;

    public PageSearchHeader(final Context context, final OnSearchListener searchListener) {
        super(context);
        TypefaceIntellitap tfi = ((MainActivity) context).tfi;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.page_search_header, null);

        etSearchBox = (EditText) v.findViewById(R.id.etSearchBox);
        etSearchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideRecentSearch();

                    HashMap<String, String> query = new HashMap<>();

                    if (name_selector.getVisibility() == VISIBLE) {
                        query.put("identifier", etSearchBox.getText().toString());
                    } else if (skills_selector.getVisibility() == VISIBLE) {
                        query.put("skill", etSearchBox.getText().toString());
                    } else if (location_selector.getVisibility() == VISIBLE) {
                        query.put("city", etSearchBox.getText().toString());
                    }

                    searchListener.onSearch(query);
                }
                return false;
            }
        });
        tfi.setTypeface(etSearchBox, TypefaceIntellitap.ROBOTO_BOLD);

        buildSearchOptions(v);

        llRecentSearch = (LinearLayout) v.findViewById(R.id.llRecentSearch);
        tvRecentSearchText = (TextView) v.findViewById(R.id.tvRecentSearchText);
        tfi.setTypeface(tvRecentSearchText, TypefaceIntellitap.ROBOTO_BOLD);

        addView(v);
    }

    private void buildSearchOptions(View v) {
        TypefaceIntellitap tfi = ((MainActivity) getContext()).tfi;
        rlName = (RelativeLayout) v.findViewById(R.id.rlName);
        rlSkills = (RelativeLayout) v.findViewById(R.id.rlSkills);
        rlLocation = (RelativeLayout) v.findViewById(R.id.rlLocation);

        name_selector = v.findViewById(R.id.name_selector);
        skills_selector = v.findViewById(R.id.skills_selector);
        location_selector = v.findViewById(R.id.location_selector);

        RelativeLayout[] rls = new RelativeLayout[]{rlName, rlSkills, rlLocation};
        for (RelativeLayout rl : rls) {
            rl.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.equals(rlName))
                        name_selector.setVisibility(VISIBLE);
                    else
                        name_selector.setVisibility(INVISIBLE);

                    if (v.equals(rlSkills))
                        skills_selector.setVisibility(VISIBLE);
                    else
                        skills_selector.setVisibility(INVISIBLE);

                    if (v.equals(rlLocation))
                        location_selector.setVisibility(VISIBLE);
                    else
                        location_selector.setVisibility(INVISIBLE);
                }
            });
        }

        tvName = (TextView) v.findViewById(R.id.tvNameText);
        tvSkills = (TextView) v.findViewById(R.id.tvSkillsText);
        tvLocation = (TextView) v.findViewById(R.id.tvLocationText);

        TextView[] tvs = new TextView[]{tvName, tvSkills, tvLocation};
        for (TextView tv : tvs) {
            tfi.setTypeface(tv, TypefaceIntellitap.ROBOTO_BOLD);
        }
    }

    public void hideRecentSearch() {
        llRecentSearch.setVisibility(GONE);
    }

    public interface OnSearchListener {
        public void onSearch(HashMap<String, String> query);
    }

}
