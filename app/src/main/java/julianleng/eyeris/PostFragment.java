package julianleng.eyeris;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.location.Location;
import android.app.FragmentTransaction;


import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
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
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;


public class PostFragment extends android.support.v4.app.Fragment implements locationHandler.locationChanged{

    private static final String FIRE_DB = "https://eyeris-b8879.firebaseio.com";
    private static final String GEO_FIRE_REF = FIRE_DB + "/geofire";

    //setup firebase
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl(FIRE_DB);

    DatabaseReference geoRef = FirebaseDatabase.getInstance().getReferenceFromUrl(GEO_FIRE_REF);
    GeoFire geoFire = new GeoFire(geoRef);

    private android.support.v4.app.Fragment fragment;
    private FragmentManager fragmentManager;

    double latitude = 0.0;
    double longitude = 0.0;
    String postTitle;
    String postContent;
    String postTags;
    String postId ;
    String username;
    String postDate;
    private Location mLastLocation = null;
    locationHandler locationHandler;

    EditText eTitle;
    EditText eContent;
    EditText eTags;
    TextView eLongitude;
    TextView eLatitude;



    public PostFragment() {

    }


    @Override
    public void newLocation() {
        Log.i("UPDATE","POST UPDATED");
        getLastLocation();
        eLatitude.setText(String.valueOf(latitude));
        eLongitude.setText(String.valueOf(longitude));
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationHandler = new locationHandler(getContext());
        Log.i("location", "handler");

        fragmentManager = getFragmentManager();
        fragment = new PostFragment();
    }


    @Nullable
    @Override
    //still figuring out buttons in fragments
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        getLastLocation();

        eTitle = (EditText) view.findViewById(R.id.editTitle);
        eContent = (EditText) view.findViewById(R.id.editContent);
        eTags = (EditText) view.findViewById(R.id.editTags);



        //submit
        final Button submitButton = (Button) view.findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // get location, submit the post
                postTitle = eTitle.getText().toString();
                postContent = eContent.getText().toString();
                postTags = eTags.getText().toString();
                postDate = DateFormat.getDateTimeInstance().format(new Date("dd/MM/yyyy"));
                submitPost();
                //change fragment to Home
                fragment = new HomeFragment();
                final android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
            }
        });

        //cancel
        final Button cancelButton = (Button) view.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //change fragment to homepage
                onStop();
                fragment = new HomeFragment();
                final android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
            }
        });

        return view;
    }


    private void submitPost() {
        //1.send to Firebase 2. return with unique key
        this.postId = submitToFirebase();
        Log.d("myTag", postId);

        //3.send to geofire with unique ID + longitude/latitude
        submitToGeofire(this.postId);
    }

    //submits to Firebase and returns the unique string generated
    private String submitToFirebase() {
        //send postTitle, postContent, postTags, current User username
        DatabaseReference postsRef = ref.child("posts");
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue(new ScrollablePosts(postDate, postContent, postTitle));
        return newPostRef.getKey();
    }

    private void submitToGeofire(String postId) {
        //gets location, saves longitude and latitude
        getLastLocation();

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

    private void getLastLocation(){
        mLastLocation = locationHandler.getLocation();
        longitude = locationHandler.getLongitude();
        latitude = locationHandler.getLatitude();

        if(mLastLocation == null){
            Log.i("fuck", "fuck");
        }
    }

}