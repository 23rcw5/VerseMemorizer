package ryan.versememorizer;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Ryan on 8/26/2015.
 */
public class menu_fragVerses extends Fragment {
    View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater ,ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        rootview = inflater.inflate(R.layout.verses_layout, container, false);
        return rootview;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        try {
            String[] items = new String[]{"John 1:1", "Matthew 4:1"};
            Activity a = getActivity();
            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(a, android.R.layout.simple_list_item_1, items);
            ListView listView = (ListView)rootview.findViewById(R.id.listView);
            listView.setAdapter(itemsAdapter);
        }catch(Exception ex)
        {
            Log.println(7, "Exception", ex.getMessage());
        }

    }
}
