package alextexamplecom.salsa_company;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnSuccessListener;
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
import android.widget.ImageView;


//Firebase
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout m_drawerLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    private Tanzkursplan m_tanzkursplan;

    private final String m_toolBarTitle="Salsa Company";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v_start);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.v_start);
        m_drawerLayout = drawer;
        initToolBar(drawer);
    }

    @Override
    public void onBackPressed() {
        setContentView(R.layout.v_start);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.v_start);
        initToolBar(drawer);
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
            SetTanzkursplan();
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

    private void SetTanzkursplan() {
        m_tanzkursplan = new Tanzkursplan();
        m_tanzkursplan.SetImage(
                NavigationActivity.this,
                (ImageView) findViewById(R.id.c_tanzkursplan_oben_view),
                "tanzkursplan/stuttgart/oben.png");
        m_tanzkursplan.SetImage(
                NavigationActivity.this,
                (ImageView) findViewById(R.id.c_tanzkursplan_unten_view),
                "tanzkursplan/stuttgart/unten.png");

    }

    public void SetNavItem(int LayoutID, int drawerID, String title) {
        Log.d(TAG, title);
        setContentView(LayoutID);

        DrawerLayout drawer = (DrawerLayout) findViewById(drawerID);
        m_drawerLayout = drawer;

        initToolBar(drawer);
    }

    public void initToolBar(DrawerLayout drawer) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(m_toolBarTitle);
        setSupportActionBar(toolbar);

        //Start the NavigationBar (Toggle available)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //Set the actionbar listener
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Set the navigation listener
        NavigationView navigationView = (NavigationView) findViewById(R.id.sidebar_navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
