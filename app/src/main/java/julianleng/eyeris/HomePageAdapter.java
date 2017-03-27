package julianleng.eyeris;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by julianleng on 3/21/17.
 */

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.HomeViewHolder>{

    private List<ScrollablePosts> listItems;
    private Context mContext;

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        public TextView post_date;
        public TextView post_content;
        public TextView post_title;
        public TextView time_ago;
        public TextView votecount;

        public HomeViewHolder(View v){
            super(v);
            post_date=(TextView)itemView.findViewById(R.id.post_date);
            post_content=(TextView)itemView.findViewById(R.id.post_content);
            post_title=(TextView)itemView.findViewById(R.id.post_title);
            time_ago=(TextView)itemView.findViewById(R.id.time_ago);
            votecount=(TextView)itemView.findViewById(R.id.vote_count);

        }


    }

    public HomePageAdapter(List<ScrollablePosts> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }

    //Called when a new post is created
    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new HomeViewHolder(v);
    }

    //After creation, the data is set in onBindViewHolder, Binding the data to the view.
    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {

        final ScrollablePosts item = listItems.get(position);
        holder.post_title.setText(item.getPost_title());
        holder.post_content.setText(item.getPost_content());
        holder.post_date.setText(item.getPost_date());
        holder.votecount.setText("" + item.getPost_votes());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    //The cards class


}
