package alextexamplecom.salsa_company;

import android.app.ProgressDialog;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.R.attr.data;
import static android.R.attr.path;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private FirebaseAuth mAuth;

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        //formatting validation
        if (!validate()) {
            onLoginFailed(null);
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

        UserCredentials userCre= new UserCredentials(email,password);
        WriteCredentialsToFile(UserCredentials.encrypt(userCre));

        //TODO: remove
        onLoginSuccess(progressDialog);
        //Authentification via Firebase instance mAuth
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Success -> close dialogs and setview to v_start
                            onLoginSuccess(progressDialog);
                        }
                        else {
                            // Failed -> message and nothing.
                            onLoginFailed(progressDialog);
                        }
                    }
                });
    }

    public void onLoginSuccess(ProgressDialog progressDialog) {
        //postprocessing
        Log.d(TAG, "signInWithEmail:success");
        Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();

        //Get user data -> map it to the database
        FirebaseUser user = mAuth.getCurrentUser();

        //Start Navigation activity
        Intent intent_nav = new Intent(LoginActivity.this, NavigationActivity.class);
        startActivity(intent_nav);
    }

    public void onLoginFailed(ProgressDialog progressDialog) {
        Log.w(TAG, "signInWithEmail:failure");
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        progressDialog.dismiss();
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


    private boolean WriteCredentialsToFile(String data)
    {
        File file = new File(getFilesDir(), "credentials.user");

        // Save
        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
            return true;
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
            return false;
        }
    }

    private boolean ReadCredi(String data)
    {
        File file = new File(getFilesDir(), "credentials.user");

        // Save
        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
            return true;
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
            return false;
        }
    }
}
