package com.example.fronoman.intellitapp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import com.example.fron.customviews.TypefaceIntellitap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Fahran on 3/18/2015.
 */
public class PageSelectPlaceActivity extends FragmentActivity {

    private Fragment map;
    private AutoCompleteTextView atvSearchBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_select_place);

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.fronoman.intellitap",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("Map error!! ", "KeyHash:" + Base64.encodeToString(md.digest(),
                        Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Map error!!", "e  =" + e.toString());

        } catch (NoSuchAlgorithmException e) {
            Log.d("Map error!!", "e  =" + e.toString());
        }


        TypefaceIntellitap tfi = new TypefaceIntellitap(this);

        atvSearchBox = (AutoCompleteTextView)

                findViewById(R.id.atvSearchBox);

        tfi.setTypeface(atvSearchBox, TypefaceIntellitap.ROBOTO);

        map =

                getSupportFragmentManager()

                        .

                                findFragmentById(R.id.map);


    }
}
