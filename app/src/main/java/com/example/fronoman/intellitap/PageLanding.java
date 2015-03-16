package com.example.fronoman.intellitap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Fahran on 1/18/2015.
 */
public class PageLanding extends Activity {

    public static String FRAGMENT_TAG = "planding";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.page_landing);

        RelativeLayout rlSignUpButton = (RelativeLayout) findViewById(R.id.rlSignUpButton);
        rlSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PageLanding.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(C.FRAGMENT_TAG_KEY, PageSignUp.FRAGMENT_TAG);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        LinearLayout llSignInField = (LinearLayout) findViewById(R.id.llSignInField);
        String[] hints = new String[]{"Email", "Password"};
        for (String hint : hints) {
            llSignInField.addView(new EditTextCustom(this, hint));
        }
        super.onCreate(savedInstanceState);
    }


}
