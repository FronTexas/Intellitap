package com.example.fronoman.intellitap;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fahran on 2/13/2015.
 */
public class Tutor extends User {

    int stars;
    Boolean isTutor;
    String skills;
    String employment;
    String datesAvailable;


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Tutor> CREATOR = new Creator<Tutor>(){

        @Override
        public Tutor createFromParcel(Parcel source) {
           return new Tutor(source);
        }

        @Override
        public Tutor[] newArray(int size) {
            return new Tutor[size];
        }
    };

    public Tutor(){

    }


    public Tutor(Parcel in){
        super(in);
        stars = in.readInt();
        isTutor = in.readByte() != 0;
        skills = in.readString();
        employment = in.readString();
        datesAvailable = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out,flags);
        out.writeInt(stars);
        out.writeByte((byte)(isTutor ? 1:0));
        out.writeString(skills);
        out.writeString(employment);
        out.writeString(education);
        out.writeString(datesAvailable);
    }
}
