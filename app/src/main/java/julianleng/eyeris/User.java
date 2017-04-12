package julianleng.eyeris;

/**
 * Created by Thomas on 4/11/2017.
 */

public class User {

    private String username;
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
