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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


//Firebase
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class AccountActivity extends AppCompatActivity {
    FirebaseUser mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.v_account);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.v_account);
        initToolBar(drawer, "AccounT");
        FillAutoCompletion();

        //retrieve mUser from Login (either through + information if

        WriteToDatabase();

    }

    private void FillAutoCompletion() {
        //TODO: Abstrahieren
        String[] tanzschulen = {"Stuttgart", "Böblingen", "Ludwigsburg"};


        //Create Array Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, tanzschulen);
        //Find TextView control
        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.account_tanzschule);
        //Set the number of characters the user must type before the drop down list is shown
        acTextView.setThreshold(1);
        //Set the adapter
        acTextView.setAdapter(adapter);
    }

    private boolean CheckCompletion() {
        //if(everything is written down)
        return true;
    }

    private User AssembleDataIntoUser() {
        //Get data from textview items
        User user = new User("firstname", "lastname");
        return user;
    }


    private boolean WriteToDatabase() {
        DatabaseReference DatabaseRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference UsersRef = DatabaseRef.child("users");

        Map<String, User> users = new HashMap<String, User>();
        users.put("alanisawesome", new User("June 23, 1912", "Alan Turing"));
        users.put("gracehop", new User("December 9, 1906", "Grace Hopper"));

        UsersRef.setValue(users);
        return true;
    }

    //TODO: Unify with Navigation -> don't have the same code twice
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
    }
}
