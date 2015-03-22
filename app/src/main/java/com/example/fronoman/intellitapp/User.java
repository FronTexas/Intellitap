package com.example.fronoman.intellitapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Fahran on 2/19/2015.
 */
public class User implements Parcelable {
    String firstName;
    String lastName;
    String phone;
    String city;
    String state;
    String userId;
    String email;
    String profilePhotoUrl;
    ArrayList<Education> education = new ArrayList<>();
    String uniqueIdentifier;
    String password;
    String about;
    boolean isTutor;

    /*
    * These constants are used to address which instance variable of user / tutor is being edited
    * */
    public static final String SKILLS = "skills";
    public static final String TAG_LINE = "tag_line";
    public static final String ABOUT = "about";
    public static final String EMPLOYMENT = "employment";
    public static final String EDUCATION = "education";
    public static final String PROF_PIC = "prof_pic";

    // TODO in the futuer maybe we need to seperate between successfully booked appointment and requested(but not approved) appointment
    ArrayList<Appointment> booked_appoinment = new ArrayList<>();


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

    public void signingUpInitialize(ArrayList<EditTextCustom> signUpFields, String[] signUp_key) {
        for (int i = 0; i < signUp_key.length; i++) {
            if (signUp_key[i].equals("email")) {
                email = signUpFields.get(i).getText().toString();
                uniqueIdentifier = email;
            }
            if (signUp_key[i].equals("firstName")) {
                firstName = signUpFields.get(i).getText().toString();
            }
            if (signUp_key[i].equals("lastName")) {
                lastName = signUpFields.get(i).getText().toString();
            }

            if (signUp_key[i].equals("password")) {
                password = signUpFields.get(i).getText().toString();
            }
            if (signUp_key[i].equals("phone")) {
                phone = signUpFields.get(i).getText().toString();
            }

            if (signUp_key[i].equals("city")) {
                city = signUpFields.get(i).getText().toString();
            }
            if (signUp_key[i].equals("state")) {
                state = signUpFields.get(i).getText().toString();
            }
        }
    }

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
        about = in.readString();


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
        out.writeString(about);


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

    public static final String SCHOOL_NAME = "School";
    public static final String DEGREE = "Degree";
    public static final String START_END_YEAR = "Year start - year end";

    public static final Creator<Education> CREATOR = new Creator<Education>() {

        @Override
        public Education createFromParcel(Parcel source) {
            return new Education(source);
        }

        @Override
        public Education[] newArray(int size) {
            return new Education[size];
        }
    };

    @Override
    public String toString() {
        return "School_name = " + schoolName + ", degree = " + concentration1 + ", start year = " + startDate + ", end year =" + endDate;
    }

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