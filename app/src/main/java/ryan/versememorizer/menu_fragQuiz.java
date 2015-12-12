package ryan.versememorizer;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ryan on 8/26/2015.
 */
public class menu_fragQuiz extends Fragment {
    View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater ,ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        rootview = inflater.inflate(R.layout.quiz_layout, container, false);
        return rootview;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        try {
            TextView tv2 = (TextView) rootview.findViewById(R.id.textView);
            if(tv2!=null) {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                String red = "red";
                SpannableString redSpan = new SpannableString(red);
                redSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, red.length(), 0);
                builder.append(redSpan);
                String green = " green";
                SpannableString greenSpan = new SpannableString(green);
                greenSpan.setSpan(new ForegroundColorSpan(Color.GREEN), 0, green.length(), 0);
                builder.append(greenSpan);

                tv2.setText(builder, TextView.BufferType.SPANNABLE);
            }
        }catch(Exception ex)
        {
            Log.println(7, "Exception", ex.getMessage());
        }

    }
}
