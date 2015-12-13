package ryan.versememorizer;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by Ryan on 12/11/2015.
 */
public class menu_fragAdd extends Fragment {
    View rootview;
    boolean first = true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater ,ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        rootview = inflater.inflate(R.layout.addverse_layout, container, false);
        return rootview;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        try {
            Button addButton = (Button)rootview.findViewById(R.id.button2);
            addButton.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // TODO Auto-generated method stub
                    int action = event.getActionMasked();
                    if (action == MotionEvent.ACTION_DOWN)
                    {
                        EditText txt = (EditText) rootview.findViewById(R.id.editText);
                        String book = txt.getText().toString().replace("Book: ", "");
                        txt = (EditText) rootview.findViewById(R.id.editText2);
                        String ch = txt.getText().toString().replace("CH: ", "");
                        txt = (EditText) rootview.findViewById(R.id.editText3);
                        String start = txt.getText().toString().replace("Verse Start: ", "");
                        txt = (EditText) rootview.findViewById(R.id.editText4);
                        String end = txt.getText().toString().replace("Verse End: ", "");
                        AsyncTask s = new ESVService().execute(book, ch + ":" + start + "-" + end);
                        try {
                            String temp = book + " " + ch + ":" +start;
                            if(start.equals(end)) temp += "-" + end;
                            String verse = s.get().toString();
                            verse = verse.substring((temp.length()-1));
                            // Gets the data repository in write mode
                            FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getContext());
                            SQLiteDatabase db = mDbHelper.getWritableDatabase();

                            // Create a new map of values, where column names are the keys
                            ContentValues values = new ContentValues();
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_BOOK, book);
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CH, ch);
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_START, start);
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_END, end);
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TEXT, verse);

                            // Insert the new row, returning the primary key value of the new row
                            long newRowId;
                            newRowId = db.insert(
                                    FeedReaderContract.FeedEntry.TABLE_NAME,
                                    null,
                                    values);
                            TextView tv = (TextView) rootview.findViewById(R.id.textView5);
                            tv.append(""+newRowId);
                        } catch (Exception e) {
                        }
                        return true;
                    }
                    else return false;


                }
            });
        }catch(Exception ex)
        {
            Log.println(7, "Exception", ex.getMessage());
        }

    }
}
