package com.esprit.instasinger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView txt;
    private Button logout;

    @Override
    protected void onStart() {
        super.onStart();
        final Intent intent = new Intent(this, LoginActivity.class);
        txt = (TextView) findViewById(R.id.hello);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            // String name = user.getDisplayName();
            String email = user.getEmail();
            //  Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }

        if (mAuth.getCurrentUser() != null) {
            txt.setText(user.getEmail());
            // User is signed in.
        } else {
            startActivity(intent);        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        txt = (TextView) findViewById(R.id.hello);
        logout = (Button) findViewById(R.id.logout);
        final Intent intent = new Intent(this, LoginActivity.class);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            mAuth.signOut();
                startActivity(intent);
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
           // String name = user.getDisplayName();
            String email = user.getEmail();
          //  Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }

        if (mAuth.getCurrentUser() != null) {
            txt.setText(user.getEmail());
            // User is signed in.
        } else {
            startActivity(intent);        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}