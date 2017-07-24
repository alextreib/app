package alextexamplecom.salsa_company;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;


//Firebase
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout m_drawerLayout;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v_start);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.v_start);
        m_drawerLayout = drawer;
        initToolBar(drawer, getString(R.string.app_name));
    }

    @Override
    public void onBackPressed() {
        setContentView(R.layout.v_start);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.v_start);
        initToolBar(drawer, getString(R.string.app_name));
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setttings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuitem) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = menuitem.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.account_settings) {
            SetNavItem(R.layout.v_account, R.id.v_account, menuitem.getTitle().toString());
            return true;
        }

        return super.onOptionsItemSelected(menuitem);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuitem) {
        // Is called when the user clicks on an item of the navigation bar
        menuitem.setChecked(true);

        //close the drawer before new content is loaded -> error because R.id.v_start can't be found
        m_drawerLayout.closeDrawer(GravityCompat.START);

        int id = menuitem.getItemId();
        if (id == R.id.nav_tanzkursplan) {
            SetNavItem(R.layout.v_tanzkursplan, R.id.v_tanzkursplan, menuitem.getTitle().toString());
            Intent intent_main = new Intent(this, Tanzkursplan.class);
            startActivity(intent_main);
        } else if (id == R.id.nav_kursinhalte) {
            SetNavItem(R.layout.v_tanzkursinhalt, R.id.v_tanzkursinhalt, menuitem.getTitle().toString());
        } else if (id == R.id.nav_tanzpartys) {
            SetNavItem(R.layout.v_tanzpartys, R.id.v_tanzpartys, menuitem.getTitle().toString());
        } else if (id == R.id.nav_app_teilen) {
            //open share options
        } else if (id == R.id.nav_kontakt) {
            SetNavItem(R.layout.v_contact, R.id.v_contact, menuitem.getTitle().toString());
        }


        return true;
    }

    public void SetNavItem(int LayoutID, int drawerID, String title) {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World2!");


        // store app title to 'app_title' node
        //String key= myRef.getKey();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        Log.d(TAG, title);
        setContentView(LayoutID);

        DrawerLayout drawer = (DrawerLayout) findViewById(drawerID);
        m_drawerLayout = drawer;

        initToolBar(drawer, title);
    }

    public void initToolBar(DrawerLayout drawer, String toolbarTitle) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //toolbarTitle is usually the name of the navigation item
        toolbar.setTitle(toolbarTitle);

        setSupportActionBar(toolbar);

        //Start the NavigationBar (Toggle available)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //Set the actionbar listener
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        SetNavListener();
    }

    public void SetNavListener() {
        //Set the navigation listener
        NavigationView navigationView = (NavigationView) findViewById(R.id.sidebar_navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
