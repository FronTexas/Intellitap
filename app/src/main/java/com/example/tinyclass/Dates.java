package com.example.tinyclass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fahran on 2/21/2015.
 */
public class Dates implements Parcelable {
    String date;
    String month;
    boolean isTutotAvailable;


    public static final Creator<Dates> CREATOR = new Creator<Dates>() {

        @Override
        public Dates createFromParcel(Parcel source) {
            return new Dates(source);
        }

        @Override
        public Dates[] newArray(int size) {
            return new Dates[size];
        }
    };

    public Dates(String date, String month, boolean isTutotAvailable) {
        this.date = date;
        this.month = month;
        this.isTutotAvailable = isTutotAvailable;
    }

    public String getDate() {
        return date;
    }

    public String getMonth() {
        return getMonth();
    }

    public boolean isTutorAvailable() {
        return isTutotAvailable;
    }

    public Dates(Parcel in) {
        date = in.readString();
        month = in.readString();
        isTutotAvailable = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(date);
        out.writeString(month);
        out.writeByte((byte) (isTutotAvailable ? 1 : 0));
    }
}