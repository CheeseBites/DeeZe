package julianleng.eyeris;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by julianleng on 3/21/17.
 * Edit by kyleastudillo on 5/8/17.
 */

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.HomeViewHolder>{

    private List<ScrollablePosts> listItems;
    private Context mContext;
    public RecyclerViewClickListener itemListener;

    public static class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView post_date;
        public TextView post_content;
        public TextView post_title;
        public TextView time_ago;
        public TextView votecount;
        public RecyclerViewClickListener itemListener;


        public HomeViewHolder(View v){
            super(v);
            post_date=(TextView)itemView.findViewById(R.id.post_date);
            post_content=(TextView)itemView.findViewById(R.id.post_content);
            post_title=(TextView)itemView.findViewById(R.id.post_title);
            time_ago=(TextView)itemView.findViewById(R.id.time_ago);
            votecount=(TextView)itemView.findViewById(R.id.vote_count);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Log.i("HomePageAdapter", "I pushed button " + this.getLayoutPosition() + " ");
            Log.i("HomePageAdapter", "I pushed button " + v.getId() + " ");
            Log.i("HomePageAdapter", "I pushed button " + this.itemListener.toString() + " ");
            this.itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }

    }


    public HomePageAdapter(List<ScrollablePosts> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }

    public HomePageAdapter(Context mContext, RecyclerViewClickListener itemListener) {
        this.mContext = mContext;
        this.itemListener = itemListener;
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

    public ScrollablePosts getItem(int position){
        return listItems.get(position);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    //The cards class


}
