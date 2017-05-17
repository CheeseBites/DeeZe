package julianleng.eyeris;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.List;

public class HomeFragment extends android.support.v4.app.Fragment{public static final String POSTS = "posts";
    private RecyclerView mPostsRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<ScrollablePosts, HomePageAdapter.HomeViewHolder> mFirebaseAdapter;
    private static final String TAG = "HomeFragment";
    public HomeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);
        rootView.setTag(TAG);

        Spinner filterspinner = (Spinner)rootView.findViewById(R.id.filterspinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.filters,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterspinner.setAdapter(spinnerAdapter);

        mPostsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setStackFromEnd(true);
        mLinearLayoutManager.setReverseLayout(true);
        //Initializing Database
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ScrollablePosts, HomePageAdapter.HomeViewHolder>(
                ScrollablePosts.class,
                R.layout.card_layout,
                HomePageAdapter.HomeViewHolder.class,
                mFirebaseDatabaseReference.child(POSTS))
        {
            @Override
            protected void populateViewHolder(HomePageAdapter.HomeViewHolder viewHolder, ScrollablePosts item, int position) {
                viewHolder.post_title.setText(item.getPost_title());
                viewHolder.post_content.setText(item.getPost_content());
                viewHolder.post_date.setText(item.getPost_date());
            }
        };



        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount){
                super.onItemRangeInserted(positionStart, itemCount);
                int postsCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1|| (positionStart >= (postsCount -1) && lastVisiblePosition == (positionStart -1))){
                    mPostsRecyclerView.scrollToPosition(positionStart);
                }
            }
        });
        mPostsRecyclerView.setLayoutManager(mLinearLayoutManager);
        mPostsRecyclerView.setAdapter(mFirebaseAdapter);
        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
