package com.esprit.instasinger.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import com.esprit.instasinger.Data.User;
import com.esprit.instasinger.R;

import static android.app.Activity.RESULT_OK;


public class Edit_Profil_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private StorageReference mStorageRef;
    private  Uri imageUri;
    private String DOWNLOAD_URL;
    private  String url ;
    private static final int PICK_IMAGE = 100;

    EditText _nameText;
    EditText _usernameText;
    EditText _emailText;
    //@InjectView(R.id.input_password) EditText _passwordText;

    ImageView _picture;

    Button _done;

    private static final String TAG = "SignupActivity";


    private ProgressDialog pDialog;


    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }
    public Edit_Profil_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Edit_Profil_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Edit_Profil_Fragment newInstance(String param1, String param2) {
        Edit_Profil_Fragment fragment = new Edit_Profil_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
    User user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Updating...");
        pDialog.setCancelable(false);

        mStorageRef = FirebaseStorage.getInstance().getReference();



        myRef.child("users").child(user1.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                user = dataSnapshot.getValue(User.class);
                Log.v("url", user.getProfilePictureURL());
                url = user.getProfilePictureURL();


                _usernameText.setText(user.getUsername());
                _nameText.setText(user.getFullname());
                _emailText.setText(user1.getEmail());

                Picasso.with(getActivity()).load(Uri.parse(url)).into(_picture);

                mParam1 = user.getFullname() ;


                //   new DownLoadImageTask(profile_picture).execute(user.getProfilePictureURL());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            _picture.setImageURI(imageUri);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit__profil_, container, false);

        _usernameText = (EditText) view.findViewById(R.id.input_username);
        _nameText = (EditText) view.findViewById(R.id.input_name);
        _emailText = (EditText) view.findViewById(R.id.input_email);
        _picture = (ImageView) view.findViewById(R.id.input_picture);
        _done = (Button) view.findViewById(R.id.btn_done);


        _picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        _done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });





        // Inflate the layout for this fragment
        return view;
    }


    public void update() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }


        showProgressDialog();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();

        // TODO: code signup
        // FireBase



        StorageReference myfileRef = mStorageRef.child(user1.getUid()+".jpg");




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
                Toast.makeText(getActivity(), "TASK FAILED", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "TASK SUCCEEDED", Toast.LENGTH_SHORT).show();
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                DOWNLOAD_URL = downloadUrl.getPath();
                Log.v("DOWNLOAD URL", DOWNLOAD_URL);
                Toast.makeText(getActivity(), DOWNLOAD_URL, Toast.LENGTH_SHORT).show();

                User user2 = new User();
                user2.setEmail(String.valueOf(_emailText.getText()));
                user2.setFollowersCount(user.getFollowersCount());
                user2.setFollowingCount(user.getFollowingCount());
                user2.setFullname(String.valueOf(_nameText.getText()));
                user2.setProfilePictureURL(String.valueOf(downloadUrl));

                //user2.setProfilePictureURL(String.valueOf(DOWNLOAD_URL));
                user2.setUsername(String.valueOf(_usernameText.getText()));
                System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

                myRef.child("users").child(user1.getUid()).setValue(user2);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onSignupSuccess or onSignupFailed
                                // depending on success
                                onSignupSuccess();
                                // onSignupFailed();
                                hideProgressDialog();
                            }
                        }, 3000);



            }
        });








        // ...
    }






    public void onSignupSuccess() {
        Fragment fragment = new Profile_Fragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, fragment)
                .commit();    }

    public void onSignupFailed() {
        //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String username = _usernameText.getText().toString();
        String email = _emailText.getText().toString();
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



        return valid;
    }


}
