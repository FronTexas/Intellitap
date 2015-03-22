package com.example.tinyclass;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.fronoman.intellitapp.TimeSlot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Fahran on 2/21/2015.
 */
public class DateIntellitapp implements Parcelable {
    /*
    * An object that encapsulates date ,month ,year ,isTutorAvailable
    * and tutor's time slotes in a particular date
    * */
    int date;
    int month;
    int year;
    boolean isTutotAvailable;
    private final String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    private ArrayList<TimeSlot> timeSlots;

    public DateIntellitapp(int date, int month, int year, boolean isTutotAvailable) {
        this.date = date;
        this.month = month;
        this.isTutotAvailable = isTutotAvailable;
        this.year = year;
    }

    public ArrayList<TimeSlot> getTimeSlots() {
        return timeSlots;
    }


    public DateIntellitapp(Parcel in) {
        date = in.readInt();
        month = in.readInt();
        isTutotAvailable = in.readByte() != 0;
        year = in.readInt();
    }

    public void setTimeSlots(ArrayList<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public long getDateInMilli() throws ParseException {
        String mdy = month + 1 + "." + date + "." + year;
        if (month % 10 == month) {
            mdy = "0" + mdy;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
        Date date = sdf.parse(mdy);

        return date.getTime();
    }

    @Override
    public String toString() {
        return month + "/" + date + "/" + year + " " + isTutotAvailable;
    }

    public static final Creator<DateIntellitapp> CREATOR = new Creator<DateIntellitapp>() {

        @Override
        public DateIntellitapp createFromParcel(Parcel source) {
            return new DateIntellitapp(source);
        }

        @Override
        public DateIntellitapp[] newArray(int size) {
            return new DateIntellitapp[size];
        }
    };


    public String getDate() {
        return "" + date;
    }

    public String getMonth() {
        return months[month];
    }

    public boolean isTutorAvailable() {
        return isTutotAvailable;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(date);
        out.writeInt(month);
        out.writeByte((byte) (isTutotAvailable ? 1 : 0));
        out.writeInt(year);
    }
}

