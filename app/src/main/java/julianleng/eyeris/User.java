package julianleng.eyeris;

import java.util.ArrayList;

/**
 * Created by Thomas on 4/11/2017.
 */

public class User {

    private String username;
    private String userID;
    private ArrayList<String> userPosts = new ArrayList<>();
    private ArrayList<String> userComments = new ArrayList<>();
    private ArrayList<String> savedPosts = new ArrayList<>();
    private double searchRadius;


    //constructor
    public User(){

    }

    public double getSearchRadius(){
        return this.searchRadius;
    }

    public void setSearchRadius(double newRadius){
        this.searchRadius = newRadius;
    }


}
