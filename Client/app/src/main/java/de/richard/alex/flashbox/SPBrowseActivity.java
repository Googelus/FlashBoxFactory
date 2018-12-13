package de.richard.alex.flashbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class SPBrowseActivity extends AppCompatActivity {

    private static Context thisContext;
    private static List<CardStack> stacks;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spbrowse);

        // get stacks
        stacks = HauptmenuActivity.getExampleStacks();
        thisContext = SPBrowseActivity.this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newStack();
            }
        });

    }

    private void newStack() {
        Intent ii = new Intent(this, MakeStackActivity.class);
        startActivity(ii);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spbrowse, menu);
        return true;
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

        public PlaceholderFragment() {
        }

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_spbrowse, container, false);
            // TODO: hier Fragment editieren


            TextView cardNumber = rootView.findViewById(R.id.cardnumber);
            TextView nameView = rootView.findViewById(R.id.stackname);
            TextView authorView = rootView.findViewById(R.id.author);
            Button play = rootView.findViewById(R.id.Play);
            Button edit = rootView.findViewById(R.id.edit_stack);

            nameView.setText(SPBrowseActivity.stacks.get(getArguments().getInt(ARG_SECTION_NUMBER)-1).getName());
            authorView.setText(SPBrowseActivity.stacks.get(getArguments().getInt(ARG_SECTION_NUMBER)-1).getAuthor());
            cardNumber.setText("Kartenanzahl: " + SPBrowseActivity.stacks.get(getArguments().getInt(ARG_SECTION_NUMBER)-1).getSize());

            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playStack(getArguments().getInt(ARG_SECTION_NUMBER)-1);
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editStack(getArguments().getInt(ARG_SECTION_NUMBER)-1);
                }
            });

            return rootView;
        }

    }

    private static void playStack(int stacknumber) {
        Intent i = new Intent(thisContext, PlayActivity.class);
        i.putExtra(HauptmenuActivity.EXTRA_STACK, stacknumber + "");
        thisContext.startActivity(i);
    }

    private static  void editStack(int stacknumber) {
        //TODO: editability
        // BrowseCards? -> delete Cards
        // add Card
        Intent i = new Intent(thisContext, MakeCardActivity.class);
        i.putExtra(HauptmenuActivity.EXTRA_STACK, stacknumber + "");
        thisContext.startActivity(i);
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return stacks.size();
        }
    }



}
