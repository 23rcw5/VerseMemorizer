package ryan.versememorizer;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class NavDrawer extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    public void onResume()
    {
        super.onResume();


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment objFragment = null;
        FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction trans = manager.beginTransaction();

        switch (position)
        {
            case 0:
                objFragment = manager.findFragmentByTag("fragMain");
                if(objFragment != null) {
                    trans.replace(R.id.container, objFragment, "fragMain");
                    trans.commit();
                    //manager.popBackStack();
                }
                else {
                    objFragment = new menu_fragMain();
                    trans.replace(R.id.container, objFragment, "fragMain");
                    //trans.addToBackStack(null);
                    trans.commit();
                }
                break;
            case 1:
                objFragment = manager.findFragmentByTag("fragVerses");
                if(objFragment != null) {
                    trans.replace(R.id.container, objFragment, "fragVerses");
                    trans.commit();
                    //manager.popBackStack();
                }
                else {
                    objFragment = new menu_fragVerses();
                    trans.replace(R.id.container, objFragment, "fragVerses");
                   // trans.addToBackStack(null);
                    trans.commit();
                }
                break;
            case 2:
                objFragment = manager.findFragmentByTag("fragQuiz");
                if(objFragment != null) {
                    trans.replace(R.id.container, objFragment, "fragQuiz");
                    trans.commit();
                    //manager.popBackStack();
                }
                else {
                    objFragment = new menu_fragQuiz();
                    trans.replace(R.id.container, objFragment, "fragQuiz");
                    //trans.addToBackStack(null);
                    trans.commit();
                }
                break;
            case 3:
                objFragment = manager.findFragmentByTag("fragAdd");
                if(objFragment != null) {
                    trans.replace(R.id.container, objFragment, "fragAdd");
                    trans.commit();
                    //manager.popBackStack();
                }
                else {
                    objFragment = new menu_fragAdd();
                    trans.replace(R.id.container, objFragment, "fragAdd");
                    //trans.addToBackStack(null);
                    trans.commit();
                }
                break;
        }

       /* FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, objFragment)
                .commit();*/
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                //mTitle = getString();
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
           // getMenuInflater().inflate(R.menu.nav_drawer, menu);
           // restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
       }
        else if(id == R.id.action_example) {
            try {
                //Verse verse = new Verse("John", "1", "1", "1", getApplicationContext());
                String[] array = {"Psalm", "117"};
                AsyncTask s = new ESVService().execute( "James", "1:1-5");
                Log.println(7,"esv",s.get().toString());
            }catch (Exception ex){
                Log.v("exception", ex.toString());
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_nav_drawer, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((NavDrawer) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
