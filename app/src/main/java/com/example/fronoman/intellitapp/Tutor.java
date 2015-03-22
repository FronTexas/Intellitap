package com.example.fronoman.intellitapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Fahran on 2/13/2015.
 */
public class Tutor extends User {

    int stars;
    ArrayList<Skill> skills = new ArrayList<>();
    ArrayList<Employment> employments = new ArrayList<>();
    String tagline;
//    ArrayList<DateIntellitapp> datesAvailable;


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Tutor> CREATOR = new Creator<Tutor>() {

        @Override
        public Tutor createFromParcel(Parcel source) {
            return new Tutor(source);
        }

        @Override
        public Tutor[] newArray(int size) {
            return new Tutor[size];
        }
    };

    public Tutor() {

    }

    public Tutor(Parcel in) {
        super(in);
        stars = in.readInt();
        isTutor = in.readByte() != 0;
        in.readList(skills, Skill.class.getClassLoader());
        in.readList(employments, Employment.class.getClassLoader());
        tagline = in.readString();
//      datesAvailable = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeInt(stars);
        out.writeByte((byte) (isTutor ? 1 : 0));
        out.writeList(skills);
        out.writeList(employments);
        out.writeString(tagline);
    }
}

class Skill implements Parcelable {
    String skillName;
    int skillId;

    public Skill() {

    }

    public Skill(String skillName) {
        this.skillName = skillName;
    }


    public Skill(Parcel in) {
        skillName = in.readString();
        skillId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(skillName);
        out.writeInt(skillId);
    }

    @Override
    public String toString() {
        return "Skillname : " + skillName;
    }
}

class Employment implements Parcelable {
    boolean isCurrent;
    String endDate;
    int employmentId;
    String description;
    String company;
    String position;
    int userId;
    String startDate;

    public static final Creator<Employment> CREATOR = new Creator<Employment>() {

        @Override
        public Employment createFromParcel(Parcel source) {
            return new Employment(source);
        }

        @Override
        public Employment[] newArray(int size) {
            return new Employment[size];
        }
    };

    public static final String COMPANY = "Company";
    public static final String POSITION = "Position";
    public static final String START_END_YEAR = "Year starts - year ends";

    public Employment() {
        isCurrent = false;
    }

    public Employment(Parcel in) {
        isCurrent = in.readByte() != 0;
        endDate = in.readString();
        employmentId = in.readInt();
        description = in.readString();
        company = in.readString();
        position = in.readString();
        userId = in.readInt();
        startDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeByte((byte) (isCurrent ? 1 : 0));
        out.writeString(endDate);
        out.writeInt(employmentId);
        out.writeString(description);
        out.writeString(company);
        out.writeString(position);
        out.writeInt(userId);
        out.writeString(startDate);
    }

    @Override
    public String toString() {
        return "Company = " + company + ", Position = " + position + ", Start year = " + startDate + "end year = " + endDate;

    }
}



