package julianleng.eyeris;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;
import java.text.ParseException;

/**
 * Created by kyle on 5/17/17.
 */

public class Comment implements Parcelable{
    private String user;
    private Date time;
    private int vote;

    public Comment(){

    }

    public Comment(Parcel parcel){
        this.user = parcel.readString();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date time = (Date)formatter.parse(parcel.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.vote =  parcel.readInt();
    }

    public String getUser() {
        return user;
    }

    public Date getTime() {
        return time;
    }

    public int getVote() {
        return vote;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getUser());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dest.writeString(format.format(this.getTime()));
        dest.writeInt(this.getVote());
    }

    public static Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
