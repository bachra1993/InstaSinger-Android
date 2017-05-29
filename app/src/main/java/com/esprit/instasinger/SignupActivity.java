package com.esprit.instasinger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.esprit.instasinger.Data.User;

public class SignupActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private static final String TAG = "SignupActivity";
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private StorageReference mStorageRef;
    private  Uri imageUri;
    private String DOWNLOAD_URL;

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_username) EditText _usernameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;
    @InjectView(R.id.input_picture) ImageView _picture;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        //Firebase
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();



        _picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            _picture.setImageURI(imageUri);
        }
    }

    public void signup() {

        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: code signup
        // FireBase

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            // On complete call either onSignupSuccess or onSignupFailed
                                            // depending on success
                                            //onSignupSuccess();
                                            onSignupFailed();
                                            progressDialog.dismiss();
                                        }
                                    }, 3000);
                        }
                        else
                        {

                            StorageReference myfileRef = mStorageRef.child(mAuth.getCurrentUser().getUid()+".jpg");




                            _picture.setDrawingCacheEnabled(true);
                            _picture.buildDrawingCache();
                            Bitmap bitmap = _picture.getDrawingCache();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();

                            UploadTask uploadTask = myfileRef.putBytes(data);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(getBaseContext(), "TASK FAILED", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getBaseContext(), "TASK SUCCEEDED", Toast.LENGTH_SHORT).show();
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    DOWNLOAD_URL = downloadUrl.getPath();
                                    Log.v("DOWNLOAD URL", DOWNLOAD_URL);
                                    Toast.makeText(getBaseContext(), DOWNLOAD_URL, Toast.LENGTH_SHORT).show();

                                    User user = new User();
                                    user.setEmail(String.valueOf(_emailText.getText()));
                                    user.setFollowersCount(0);
                                    user.setFollowingCount(0);
                                    user.setFullname(String.valueOf(_nameText.getText()));
                                    user.setProfilePictureURL(String.valueOf(downloadUrl));
                                    user.setUsername(String.valueOf(_usernameText.getText()));


                                    myRef.child("users").child(mAuth.getCurrentUser().getUid()).setValue(user);
                                    new android.os.Handler().postDelayed(
                                            new Runnable() {
                                                public void run() {
                                                    // On complete call either onSignupSuccess or onSignupFailed
                                                    // depending on success
                                                    onSignupSuccess();
                                                    // onSignupFailed();
                                                    progressDialog.dismiss();
                                                }
                                            }, 3000);
                                }
                            });







                        }

                        // ...
                    }
                });


    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String username = _usernameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhh");
        System.out.println(username);
        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhh");
        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError(" Name at least 3 characters");
            System.out.println(name);
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (username.isEmpty() || username.length() < 6) {
            _nameText.setError(" Username at least 6 characters");
            valid = false;
            System.out.println(username);
        } else {
            _nameText.setError(null);
            System.out.println(username);

        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 15) {
            _passwordText.setError("between 4 and 15 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}

