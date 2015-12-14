package ryan.versememorizer;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ryan on 8/26/2015.
 */
public class menu_fragVerses extends Fragment {
    View rootview;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater ,ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        rootview = inflater.inflate(R.layout.verses_layout, container, false);
        ListView lv = (ListView)rootview.findViewById(R.id.listView);
        registerForContextMenu(lv);
        return rootview;
    }
    @Override
    public void onCreateContextMenu(final ContextMenu menu,
                                    final View v, final ContextMenu.ContextMenuInfo menuInfo) {

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
           // menu.setHeaderTitle(Countries[info.position]);
            String[] menuItems = {"Delete"};
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
    }
    @Override
    public boolean onContextItemSelected(final MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if(item.getTitle()=="Delete"){
            HashMap<String,String> selected =  list.get(info.position);
            list.remove(info.position);
            FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getContext());
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, FeedReaderContract.FeedEntry.COLUMN_NAME_TEXT + "='" + selected.get("text")+"'", null);
            adapter.notifyDataSetChanged();
        }
        return false;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        try {
            FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getContext());
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    FeedReaderContract.FeedEntry._ID,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_BOOK,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_CH,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_START,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_END,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TEXT,
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

            list = new ArrayList<HashMap<String, String>>(c.getCount());
            c.moveToFirst();
            for(int i = 0; i < c.getCount(); i++) {
                String verse = c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_BOOK)) + " " +
                        c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CH)) + ":" +
                        c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_START));
                if(!c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_END)).equals( c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_START))) ){
                   verse += "-" + c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_END));
                }
                String text = c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TEXT));
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("verse", verse);
                item.put("text", text);
                list.add(item);
                c.moveToNext();
            }
            Activity a = getActivity();
            ListView listView = (ListView)rootview.findViewById(R.id.listView);
            String[] from = new String[] { "verse", "text" };

            int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

            int nativeLayout = android.R.layout.two_line_list_item;
            adapter = new SimpleAdapter(getContext(), list, nativeLayout , from, to);
            listView.setAdapter(adapter);
        }catch(Exception ex)
        {
            Log.println(7, "Exception", ex.getMessage());
        }

    }
}
