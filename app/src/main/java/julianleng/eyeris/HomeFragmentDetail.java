package julianleng.eyeris;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kyle on 4/11/17.
 */


public class HomeFragmentDetail extends Fragment {

    private ListView lv;
    private static final String TAG = "HomeFragmentDetail";
    public List<String> comments;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_home_fragment_detail,container,false);
        rootView.setTag(TAG);

        lv = (ListView) rootView.findViewById(R.id.detail_comment);
        Bundle args = getArguments();

        //Get args
        String title = args.getString("title");
        String content = args.getString("content");



        TextView TVTitle = (TextView) rootView.findViewById(R.id.detail_title);
        TVTitle.setText(title);

        TextView TVContent = (TextView) rootView.findViewById(R.id.detail_content_text);
        TVContent.setText(content);

        String[] comments = {"Hello World","This is so cool"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                lv.getContext(),
                R.layout.item_comment,
                comments
                );

        lv.setAdapter(arrayAdapter);

        return rootView;
    }


}
