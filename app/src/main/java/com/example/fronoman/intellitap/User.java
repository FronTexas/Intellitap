package com.example.fronoman.intellitap;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Fahran on 2/19/2015.
 */
public class User implements Parcelable {
    String firstName;
    String lastName;
    String phone;
    String city;
    String userId;
    String email;
    String profilePhotoUrl;
    String education;

    // TODO in the futuer maybe we need to seperate between successfully booked appointment and requested(but not approved) appointment
    ArrayList<Appointment> booked_appoinment;


    public static final Creator<User> CREATOR = new Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User() {
        booked_appoinment = new ArrayList<>();
    }

    public User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        phone = in.readString();
        city = in.readString();
        userId = in.readString();
        email = in.readString();
        profilePhotoUrl = in.readString();
        education = in.readString();
        in.readTypedList(booked_appoinment, Appointment.CREATOR);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(firstName);
        out.writeString(lastName);
        out.writeString(phone);
        out.writeString(city);
        out.writeString(userId);
        out.writeString(email);
        out.writeString(profilePhotoUrl);
        out.writeString(education);
        out.writeTypedList(booked_appoinment);

    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
