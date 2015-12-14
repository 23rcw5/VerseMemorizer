package ryan.versememorizer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ryan on 8/26/2015.
 */
public class menu_fragQuiz extends Fragment {
    View rootview;
    String quizVerse;
    String correctText;
    String oldText = "";
    SpannableStringBuilder builder = new SpannableStringBuilder();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater ,ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        rootview = inflater.inflate(R.layout.quiz_layout, container, false);
        Button b = (Button)rootview.findViewById(R.id.button);
        registerForContextMenu(b);
        EditText input = (EditText)rootview.findViewById(R.id.editText5);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               /* EditText input = (EditText)rootview.findViewById(R.id.editText5);
                if(input.getText().toString().equals(correctText.substring(0,input.getText().toString().length()-1))){
                    input.setTextColor(Color.GREEN);
                }
                else input.setTextColor(Color.RED);*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                String userText =  s.toString();
                TextView tv = (TextView) rootview.findViewById(R.id.textView6);
                if(userText.equals(oldText)) {
                    userText += " ";
                }
                if(userText.isEmpty()) {
                    builder = new  SpannableStringBuilder();
                }
                else if(userText.charAt(userText.length()-1) == ' ') builder.append(' ');
                else{
                    String grader = tv.getText().toString();
                    if (userText.length() < grader.length()) { //if user deletes
                        builder.delete(builder.length() - 1, builder.length());
                    }
                    else {
                        SpannableString span;
                        span = new SpannableString("" + userText.charAt(userText.length() - 1));
                        if (userText.charAt(userText.length() - 1) == correctText.charAt(userText.length() - 1)) {
                            span.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 1, 0);
                        } else {
                            span.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, 0);
                        }
                        builder.append(span);
                    }
                }
                oldText = userText;
                tv.setText(builder, TextView.BufferType.SPANNABLE);
            }
        });

        return rootview;
    }
    @Override
    public void onCreateContextMenu(final ContextMenu menu,
                                    final View v, final ContextMenu.ContextMenuInfo menuInfo) {
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_NAME_BOOK,
                FeedReaderContract.FeedEntry.COLUMN_NAME_CH,
                FeedReaderContract.FeedEntry.COLUMN_NAME_START,
                FeedReaderContract.FeedEntry.COLUMN_NAME_END,
        };
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_BOOK + " not NULL";
        String[] selectionArgs = null;
        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_BOOK + " DESC";

        Cursor c = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        List<String> array = new ArrayList<>();
        while(c.moveToNext()){
            String verse = c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_BOOK)) + " " +
                    c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CH)) + ":" +
                    c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_START));
            array.add(verse);
        }
        for (int i = 0; i<array.size(); i++) {
            menu.add(Menu.NONE, i, i, array.get(i));
        }
    }
    @Override
    public boolean onContextItemSelected(final MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        quizVerse = item.getTitle().toString();
        TextView tv2 = (TextView) rootview.findViewById(R.id.textView);
        tv2.setText(quizVerse);
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_NAME_TEXT,
        };
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_BOOK + " = '" + "Jeremiah" + "'";
        String[] selectionArgs = null;
        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                null;

        Cursor c = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        c.moveToFirst();
        correctText = c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TEXT)).replaceAll("[[0-9]]","").substring(4);
        return false;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        /*try {
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
        }*/

    }
}
