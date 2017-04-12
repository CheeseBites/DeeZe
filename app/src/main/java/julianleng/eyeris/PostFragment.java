package julianleng.eyeris;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.location.Location;
import android.app.FragmentTransaction;


import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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
    updateLocation locationUpdate = null;

    public PostFragment() {

    }

    //fragment takes location from activity
    public interface updateLocation {
        public Location getLocation();
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    //still figuring out buttons in fragments
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        //submit
        final Button submitButton = (Button) view.findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get location, submit the post
                submitPost();
            }
        });

        //cancel
        final Button cancelButton = (Button) view.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //change activity to homepage

            }
        });

        return view;
    }


    private void submitPost() {
        //1.send to Firebase 2. return with unique key
        postId = submitToFirebase();

        //3.send to geofire with unique ID + longitude/latitude
        mLastLocation = locationUpdate.getLocation();//interfaces with MainActivity to return Location
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
        latitude = mLastLocation.getLatitude();
        longitude = mLastLocation.getLongitude();

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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof updateLocation) {
            locationUpdate = (updateLocation) activity;
        } else {
            throw new ClassCastException();
        }
    }
}