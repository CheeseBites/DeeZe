package julianleng.eyeris;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class HomeFragment extends android.support.v4.app.Fragment implements RecyclerViewClickListener{public static final String POSTS = "posts";
    private RecyclerView mPostsRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<ScrollablePosts, HomePageAdapter.HomeViewHolder> mFirebaseAdapter;
    private static final String TAG = "HomeFragment";

    public RecyclerViewClickListener itemListener = new RecyclerViewClickListener() {
        @Override
        public void recyclerViewListClicked(View v, int position) {
            Log.i("HomeFragment", "It worked succesfully 2 " + position + " ");
            mFirebaseAdapter.getItem(2);
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            HomePageAdapter.HomeViewHolder post = (HomePageAdapter.HomeViewHolder) mPostsRecyclerView.getChildViewHolder(v);
            HomeFragmentDetail f =  new HomeFragmentDetail();
            Bundle args = new Bundle();
            args.putString("title", (String) post.post_title.getText());
            args.putString("content", (String) post.post_content.getText());
            args.putParcelableArrayList("comment", post.post_comment);
            f.setArguments(args);
            ft.replace(R.id.main_container, f).commit();
            Log.i("HomeFragment", "End of pain in the ass " + position + " ");
        }
    };

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Log.i("HomeFragment", "This is here for lies " + position + " ");
        //mFirebaseAdapter.getItem(2);
        //final FragmentTransaction ft = getFragmentManager().beginTransaction();
        //ft.replace(R.id.main_container, new HomeFragmentDetail(), "HomeFragmentDetail");

    }

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
                viewHolder.post_comment = item.getPost_comments();
                viewHolder.itemListener = itemListener;
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
