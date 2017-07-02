package alextexamplecom.salsa_company;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView m_navigationView;
    private DrawerLayout m_drawerLayout;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Start the NavigationBar (Toggle available)
        m_drawerLayout = (DrawerLayout) findViewById(R.id.v_start);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, m_drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        m_drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        m_navigationView = (NavigationView) findViewById(R.id.sidebar_navigation);
        m_navigationView.setNavigationItemSelectedListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.v_start);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //close the drawer -> and close the application
            drawer.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_account, menu);
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
            setContentView(R.layout.v_account);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuitem) {
        // Is called when the user clicks on an item of the navigation bar
        int id = menuitem.getItemId();
        menuitem.setChecked(true);

        //close the drawer before new content is loaded -> error because R.id.v_start can't be found
        m_drawerLayout.closeDrawer(GravityCompat.START);

        if (id == R.id.nav_tanzkursplan) {
            setContentView(R.layout.v_tanzkursplan);
        } else if (id == R.id.nav_kursinhalte) {
            setContentView(R.layout.v_tanzkursinhalt);
        } else if (id == R.id.nav_tanzpartys) {

        } else if (id == R.id.nav_kontakt) {

        } else if (id == R.id.nav_app_teilen) {

        } else if (id == R.id.nav_kontakt) {

        }

        return true;
    }
}
