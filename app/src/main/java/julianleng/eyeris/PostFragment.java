package julianleng.eyeris;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.location.Location;


import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PostFragment extends android.support.v4.app.Fragment{

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("path/to/geofire");
    GeoFire geoFire = new GeoFire(ref);
    double latitude;
    double longitude;
    String postTitle;
    String postContent;
    String postTags;
    String postId;
    String username;
    String postDate;
    Location mLastLocation;

    public PostFragment() {

    }

    //fragment takes location from activity
    public interface OnSubmit {
        public Location getLocation ();
    }

    //this isn't right, but the general idea of how the buttons work
    public void onCreate() {

        final Button submitButton = (Button) findViewById(R.id.Submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get location, submit the post
                mLastLocation = new MainActivity.getLocation();
                submitPost();
            }
        });

        final Button cancelButton = (Button) findViewById(R.id.Cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //change activity to homepage
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create an instance of GoogleAPIClient.

        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    private void submitPost() {
        //1.send to Firebase 2. return with unique key
        postId = submitToFirebase();

        //3.send to geofire with unique ID + longitude/latitude
        submitToGeofire(postId);
    }

    //submits to Firebase and returns the unique string generated
    private String submitToFirebase() {
        //send postTitle, postContent, postTags, current User username
        DatabaseReference postsRef = ref.child("posts");
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue(new ScrollablePosts(postDate, postTitle, postContent));
        return postsRef.getKey();
    }

    private void submitToGeofire(String postId) {
        //somehow get mLastLocation
        this.latitude = this.mLastLocation.getLatitude();
        this.longitude = this.mLastLocation.getLongitude();

        geoFire.setLocation(postId, new GeoLocation(this.latitude, this.longitude),
                new GeoFire.CompletionListener() {
                    @Override
                    public void onComplete(String key, DatabaseError error) {
                        if (error != null) {
                            System.err.println("There was an error saving the location to GeoFire: " + error);
                        } else {
                            System.out.println("Location saved on server successfully!");
                        }
                    }
                });
    }

}