package com.elsawaf.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class Ingredient extends SugarRecord implements Parcelable {

    @SerializedName("ingredient")
    private String name;
    private String measure;
    private double quantity;

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }

    public double getQuantity() {
        return quantity;
    }


    public Ingredient() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.measure);
        dest.writeDouble(this.quantity);
    }

    protected Ingredient(Parcel in) {
        this.name = in.readString();
        this.measure = in.readString();
        this.quantity = in.readDouble();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
