package julianleng.eyeris;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyle on 4/11/17.
 */


public class HomeFragmentDetail extends Fragment {

    private ListView lv;
    private static final String TAG = "HomeFragmentDetail";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_home_fragment_detail,container,false);
        rootView.setTag(TAG);

        //lv = (ListView) rootView.findViewById(R.id.recycler_view);
        Bundle args = getArguments();

        //Get args
        String title = args.getString("title");
        String content = args.getString("content");



        TextView TVTitle = (TextView) rootView.findViewById(R.id.detail_title);
        TVTitle.setText(title);

        TextView TVContent = (TextView) rootView.findViewById(R.id.detail_content_text);
        TVContent.setText(content);

        List<String> comments = new ArrayList<String>();
        comments.add("This is so cool");
        comments.add("Hello World");

        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                comments);

        lv.setAdapter(arrayAdapter);
        */
        return rootView;
    }


}
