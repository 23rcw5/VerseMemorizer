package ryan.versememorizer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ryan on 8/26/2015.
 */
public class menu_fragVerses extends Fragment {
    View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater ,ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.verses_layout, container, false);
        return rootview;
    }
}
