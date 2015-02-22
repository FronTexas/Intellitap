package com.example.fronoman.intellitap;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fahran on 2/19/2015.
 */
public class Appointment implements Parcelable {
    User user;
    String expertise;
    ArrayList<User> invited_user;
    String dayAndDate;
    String startEndTime;
    String location;


    public static final Creator<Appointment> CREATOR = new Creator<Appointment>(){

        @Override
        public Appointment createFromParcel(Parcel source) {
            return new Appointment(source);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    public Appointment(Parcel in){
        user = in.readParcelable(getClass().getClassLoader());
        expertise = in.readString();
        in.readTypedList(invited_user,User.CREATOR);
        dayAndDate = in.readString();
        startEndTime = in.readString();
        location = in.readString();
    }

    public Appointment(User user){
        this.user = user;
        invited_user = new ArrayList<>();
        if(user instanceof Tutor)
            expertise = ((Tutor)user).skills;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(user,0);
        out.writeString(expertise);
        out.writeTypedList(invited_user);
        out.writeString(dayAndDate);
        out.writeString(startEndTime);
        out.writeString(location);
    }
}
