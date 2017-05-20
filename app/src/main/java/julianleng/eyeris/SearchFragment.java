package julianleng.eyeris;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static com.firebase.ui.auth.IdpResponse.getIntent;

import java.util.ArrayList;
import java.util.List;




public class SearchFragment extends android.support.v4.app.Fragment {

    private static final String FIRE_DB = "https://eyeris-b8879.firebaseio.com";
    private static final String POST_FIRE_REF = FIRE_DB + "/posts";
    EditText eInputText;
    String searchedText;
    AutoCompleteTextView text;
    View view1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        view1 = view;
        eInputText = (EditText) view.findViewById(R.id.editSearch);

        return view;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl(FIRE_DB);
        DatabaseReference postRef = FirebaseDatabase.getInstance().getReferenceFromUrl(POST_FIRE_REF);
        //PostFire postFire = new PostFire(postRef);
        final List<PostsDTO> lstPosts = new ArrayList<PostsDTO>();
        searchedText = eInputText.getText().toString();

        postRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child : children){
                    PostsDTO posts = child.getValue(PostsDTO.class);
                    lstPosts.add(posts);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        SearchMethod(searchedText,lstPosts);
        return true;
    }

    public SearchFragment(){

    }

    public void SearchMethod(String text, List<PostsDTO> lstOfPosts){

        List<String> lstTitle = new ArrayList<>();
        for (PostsDTO p: lstOfPosts) {
            if(p.postTitle.contains(text)){
                lstTitle.add(p.postTitle);
            }
        }
        RenderListofSearchedPost(lstTitle);
    }

    public void RenderListofSearchedPost(List<String> lstMatchedPosts){

        text = (AutoCompleteTextView) view1.findViewById(R.id.autocomplete);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,lstMatchedPosts);
        text.setAdapter(adapter);
        text.setThreshold(2);
    }

    //DTO

    public class PostsDTO {

        String postContent;
        String postDate;
        String postTitle;
        String postVotes;

        //get
        public String getPostContent(String postContent){
            return postContent;
        }
        public String getPostDate(String postDate){
            return postDate;
        }
        public String getPostTitle(String postTitle){
            return postTitle;
        }
        public String getPostVotes(String postVotes){
            return postVotes;
        }

        //set
        public void setPostContent(String postContent){
            this.postContent = postContent;
        }
        public void setPostDate(String postDate){
            this.postDate = postDate;
        }
        public void setPostTitle(String postTitle){
            this.postTitle = postTitle;
        }
        public void setPostVotes(String postVotes){
            this.postVotes = postVotes;
        }
    }

     /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflator();
        inflater.inflate(R.menu.menu_searchable_activity, menu);
        return true;
    }*/
}