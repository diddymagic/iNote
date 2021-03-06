package com.echo.iNote;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Notes implements Parcelable {
    String title;
    String textBody;
    String date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notes)) return false;
        Notes notes = (Notes) o;
        return getColors() == notes.getColors() &&
                Objects.equals(getTitle(), notes.getTitle()) &&
                Objects.equals(getTextBody(), notes.getTextBody()) &&
                Objects.equals(getDate(), notes.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getTextBody(), getDate(), getColors());
    }

    int colors;
    protected Notes(Parcel in) {
        title = in.readString();
        textBody = in.readString();
        date = in.readString();
        colors = in.readInt();
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    public Notes(String title, String textBody, String date,int colors) {
        this.title = title;
        this.textBody = textBody;
        this.date = date;
        this.colors = colors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }
    @Override
    public int describeContents() {
        return 0;
    }
public void setDate(String date){
        this.date = date;
}
public void setColors(int colors){
        this.colors = colors;
}
public int getColors(){
        return colors;
}
public String getDate(){
        return date;
}
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(textBody);
        dest.writeString(date);
        dest.writeInt(colors);
    }
}