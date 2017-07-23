package alextexamplecom.salsa_company;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.BindView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private FirebaseAuth mAuth;
    private UserCredentials m_userCred;

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_resetpwd)
    TextView _resetpwdButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserCredentials m_userCred = new UserCredentials();
        m_userCred = m_userCred.ReadCredi(getFilesDir());
        if (!m_userCred.IsEmpty()) {
            //Credentials already saved
            onLoginSuccess(m_userCred);
        } else {
            //Sign in process -> without saved credentials
            setContentView(R.layout.v_login);
            ButterKnife.bind(this);

            mAuth = FirebaseAuth.getInstance();
            _loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

            _resetpwdButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reset_pwd();

                }
            });
        }
    }

    public void login() {
        Log.d(TAG, "Login");

        //formatting validation
        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_Light_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        m_userCred = new UserCredentials(email, password);

        //TODO: remove
        progressDialog.dismiss();
        onLoginSuccess(m_userCred);
        //Authentification via Firebase instance mAuth
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Success -> close dialogs and setview to v_start
                            progressDialog.dismiss();
                            onLoginSuccess(m_userCred);
                        } else {
                            // Failed -> message and nothing.
                            progressDialog.dismiss();
                            onLoginFailed();
                        }
                    }
                });
    }

    public void onLoginSuccess(UserCredentials userCred) {
        //postprocessing
        Log.d(TAG, "Login successful");
        Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_LONG).show();

        //Write credentialsToFile
        m_userCred.WriteCredentialsToFile(getFilesDir());
        //Get user data -> map it to the database and give it to the navigationactivity
        FirebaseUser firebaseUser = mAuth.getCurrentUser();




        //Start Navigation activity
        Intent intent_nav = new Intent(this, NavigationActivity.class);
        //intent_nav.putExtra("Test",new UserCredentials());
        startActivity(intent_nav);
    }

    public void onLoginFailed() {
        Log.w(TAG, "signInWithEmail:failure");
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public boolean validate() {
        //check spelling
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


    private void reset_pwd() {
        String email = _emailText.getText().toString();

        //Spelling check
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //Spelling not correct
            Toast.makeText(getBaseContext(), "Gib deine E-Mail Adresse ein", Toast.LENGTH_LONG).show();
        } else {
            //Spelling correct, send a reset mail
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Success -> close dialogs and setview to v_start
                                Toast.makeText(getBaseContext(), "E-Mail wurde versendet", Toast.LENGTH_LONG).show();
                            } else {
                                // Failed -> message and nothing.
                                Toast.makeText(getBaseContext(), "Fehler beim Versenden der E-Mail", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}