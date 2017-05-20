package julianleng.eyeris;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;
public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.HomeViewHolder>  {

    private List<ScrollablePosts> listItems;
    private Context mContext;

    public static class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView post_date;
        public TextView post_content;
        public TextView post_title;
        public TextView votecount;
        public ImageButton vote_button;
        private boolean pressed;
        public int mCounter = 0;
        public HomeViewHolder(View v) {
            super(v);
            post_date = (TextView) itemView.findViewById(R.id.post_date);
            post_content = (TextView) itemView.findViewById(R.id.post_content);
            post_title = (TextView) itemView.findViewById(R.id.post_title);
            votecount = (TextView) itemView.findViewById(R.id.vote_count);
            votecount.setText("0");
            vote_button = (ImageButton) itemView.findViewById(R.id.upvote_button);
            pressed = false;
            vote_button.setOnClickListener(this);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.upvote_button:
                if (!isPressed()) { // I assume upvote   is checkbox
                    setPressed(true);
                    votecount.setText("" + ++mCounter);
                } else {
                    setPressed(false);
                    votecount.setText("" + --mCounter);
                }
                    break;
            }
        }
        public boolean isPressed(){
            return pressed;
        }

        public void setPressed(boolean press){
            this.pressed = press;
        }
    }



    //Creates viewholders for the recyclerview to use.
    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new HomeViewHolder(v);
    }

    //After creating the few viewholders, they're re-binded(re-used) when scrolled out of parent view.
    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {

        final ScrollablePosts item = listItems.get(position);
        holder.post_title.setText(item.getPost_title());
        holder.post_content.setText(item.getPost_content());
        holder.post_date.setText(item.getPost_date());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override public int getItemViewType(int position) {
        return position;
    }

}


