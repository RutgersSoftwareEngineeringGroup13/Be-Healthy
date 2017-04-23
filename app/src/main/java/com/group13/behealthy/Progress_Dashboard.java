package com.group13.behealthy;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by stephan on 3/4/17.
 */

public class Progress_Dashboard extends AppCompatActivity{

    float testdata[] = {98.8f, 123.4f, 126.4f};
    String calories[] = {"Carbs", "Fats", "Protein"};


    private static String TAG = MainActivity.class.getSimpleName();

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String Progress_Dashboard;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dashboard);

        setupPieChart();

        Runnable runnable = new Runnable() {
            public void run() {
                mNavItems.add(new NavItem("Dashboard", "View Your Progress", R.drawable.ic_action_home));
                mNavItems.add(new NavItem("Friends", "View Your Friends' Profile", R.drawable.ic_action_friends));
                mNavItems.add(new NavItem("Pictures", "View Your Pictures", R.drawable.ic_action_pictures));
                mNavItems.add(new NavItem("Food Intake", "Edit Your Daily Food Intake", R.drawable.ic_action_food));
                mNavItems.add(new NavItem("Macronutrients", "Edit Your Macronutrient Goals", R.drawable.ic_action_macros));
                mNavItems.add(new NavItem("Preferences", "Change Your Preferences", R.drawable.ic_action_settings));
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();



        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        Progress_Dashboard = getTitle().toString();

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /*
            * Called when a particular item from the navigation drawer
            * is selected.
            * */
            private void selectItemFromDrawer(int position) {
                mDrawerList.setItemChecked(position, true);
                mDrawerLayout.closeDrawer(mDrawerPane);
                //Go to specific location depending on user input
                Fragment fragment = null;
                switch(position){
                    case 0: startActivity(new Intent(Progress_Dashboard.this, Progress_Dashboard.class));
                        break;
                    case 1: startActivity(new Intent(Progress_Dashboard.this, Progress_Dashboard.class));
                        break;
                    case 2: startActivity(new Intent(Progress_Dashboard.this, Progress_Dashboard.class));
                        break;
                    case 3: setTitle(mNavItems.get(position).mTitle);
                        fragment = new Food_Intake();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.mainContent, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case 4: startActivity(new Intent(Progress_Dashboard.this, Progress_Dashboard.class));
                        break;
                    case 5: setTitle(mNavItems.get(position).mTitle);
                        fragment = new Preferences();
                        transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.mainContent, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    default: break;
                }

            }
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle("Nav");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle("Nav2");
            }
        };

        // These 2 lines crash progress_dashboard for some reason
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle other action bar items...

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    class DrawerListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            }
            else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText( mNavItems.get(position).mTitle );
            subtitleView.setText( mNavItems.get(position).mSubtitle );
            iconView.setImageResource(mNavItems.get(position).mIcon);

            return view;
        }

    }
    private void setupPieChart() {
        // Populating a list of PieEntries:
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < testdata.length; i++) {
            pieEntries.add(new PieEntry(testdata[i], calories[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Calories you should consume today");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(30f);

        PieData data = new PieData(dataSet);

        // Get the chart:
        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.animateXY(1400, 1400);
        chart.setTouchEnabled( true );
        chart.setRotationEnabled( true );
        chart.isUsePercentValuesEnabled();
        //chart.getCircleBox();
        chart.getHoleRadius();
        chart.setHoleRadius(1f);
        chart.isUsePercentValuesEnabled();
        chart.setUsePercentValues( true );
        chart.setData(data);

        //chart.invalidate();
        Legend l = chart.getLegend();
        l.setEnabled(false);
        Description des = chart.getDescription();
        des.setEnabled(false);


    }


}