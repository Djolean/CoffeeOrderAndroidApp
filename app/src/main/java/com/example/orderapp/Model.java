package com.example.orderapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Model implements Parcelable {
    private String mDrinkName;
    private String mDrinkDetail;
    private int mDrinkPhoto;

    public Model(String drinkName, String drinkDetail, int drinkPhoto) {
        mDrinkName = drinkName;
        mDrinkDetail = drinkDetail;
        mDrinkPhoto = drinkPhoto;
    }

    protected Model(Parcel in) {
        mDrinkName = in.readString();
        mDrinkDetail = in.readString();
        mDrinkPhoto = in.readInt();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public String getmDrinkName() {
        return mDrinkName;
    }

    public String getmDrinkDetail() {
        return mDrinkDetail;
    }

    public int getmDrinkPhoto() {
        return mDrinkPhoto;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDrinkName);
        dest.writeString(mDrinkDetail);
        dest.writeInt(mDrinkPhoto);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
