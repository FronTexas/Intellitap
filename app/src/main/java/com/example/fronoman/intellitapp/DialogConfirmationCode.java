package com.example.fronoman.intellitapp;

import android.content.Intent;
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
                Intent i = new Intent(getActivity(), SignUpInitActivity.class);
                Bundle b = new Bundle();
                b.putParcelable(C.USER_KEY, ((MainActivity) getActivity()).user);
                i.putExtras(b);
                startActivity(i);
                dismiss();
            }
        });


        return v;
    }
}
