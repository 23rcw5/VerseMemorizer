package ryan.versememorizer;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ryan on 8/26/2015.
 */
public class menu_fragMain extends Fragment {
    View rootview;
    String dailyVerse;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater ,ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        rootview = inflater.inflate(R.layout.main_layout, container, false);
        return rootview;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        try {
            TextView tv = (TextView) rootview.findViewById(R.id.textView3);
            if(dailyVerse == null) {
                AsyncTask at = new ESVService().execute("daily");
               // = at.get().toString().split('[');
                dailyVerse = at.get().toString();
            }

            tv.append(dailyVerse);
        }catch(Exception ex)
        {
            Log.println(7, "Exception", ex.getMessage());
        }

    }
}
