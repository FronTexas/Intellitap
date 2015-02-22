package com.example.fronoman.intellitap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

/**
 * Created by Fahran on 1/19/2015.
 */
public class DialogConfirmationCode extends DialogFragment {

    public static DialogConfirmationCode newInstance() {
        return new DialogConfirmationCode();
    }

    public DialogConfirmationCode() {
        // empty constructor is required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.dialog_confirmation_code, null);
        RelativeLayout rlConfirm = (RelativeLayout) v.findViewById(R.id.rlConfirm);
        rlConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragments(new PageHome(), false, PageSearch.FRAGMENT_TAG);
                dismiss();
            }
        });


        return v;
    }
}
