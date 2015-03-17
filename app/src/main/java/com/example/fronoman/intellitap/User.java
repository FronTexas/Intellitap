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
    Boolean isTutor;
    ArrayList<Education> education;


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
        firstName = "";
        lastName = "";
        phone = "";
        city = "";
        userId = "";
        email = "";
        profilePhotoUrl = "";
        booked_appoinment = new ArrayList<>();
        education = new ArrayList<>();
    }

    public User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        phone = in.readString();
        city = in.readString();
        userId = in.readString();
        email = in.readString();
        profilePhotoUrl = in.readString();
        in.readTypedList(booked_appoinment, Appointment.CREATOR);
        in.readList(education, Education.class.getClassLoader());


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
        out.writeTypedList(booked_appoinment);
        out.writeList(education);


    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

}

class Education implements Parcelable {
    String concentration1;
    String concentration2;
    int educationId;
    String endDate;
    String level;
    String description;
    String schoolName;
    int userId;
    String startDate;

    public Education() {

    }

    public Education(Parcel in) {
        concentration1 = in.readString();
        concentration2 = in.readString();
        educationId = in.readInt();
        endDate = in.readString();
        level = in.readString();
        description = in.readString();
        schoolName = in.readString();
        userId = in.readInt();
        startDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(concentration1);
        out.writeString(concentration2);
        out.writeInt(educationId);
        out.writeString(endDate);
        out.writeString(level);
        out.writeString(description);
        out.writeString(schoolName);
        out.writeInt(userId);
        out.writeString(startDate);
    }
}