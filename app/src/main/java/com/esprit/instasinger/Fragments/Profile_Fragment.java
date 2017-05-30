package com.esprit.instasinger.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import com.esprit.instasinger.Adapter.RecycleView_videos;
import com.esprit.instasinger.Adapter.ThumbAdapter;
import com.esprit.instasinger.Data.User;
import com.esprit.instasinger.Data.Videos;
import com.esprit.instasinger.LoginActivity;
import com.esprit.instasinger.R;

public class Profile_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    //Data Base References

    FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference myRefvideos = database.getReference();

    ArrayList<Videos> v = new ArrayList<Videos>();
    RecyclerView gridview ;
    ThumbAdapter thumbAdapter ;

    GoogleApiClient mGoogleApiClient;
    boolean mSignInClicked;
    private  String url ;
    TextView username ;
    CircleImageView profile_picture;

    Button edit_profile ;
    TextView usern ;

    Button logout ;
    TextView tv_following ;
    TextView tv_followers ;
    TextView tv_posts ;
    private ProgressDialog pDialog;


    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }












    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText edit_nom;
    EditText edit_prenom;

    EditText edit_age;
    Button add_person;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int VideoSize ;


    public Profile_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile_Fragment newInstance(String param1, String param2) {
        Profile_Fragment fragment = new Profile_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_, container, false);
        final Intent intent = new Intent(getActivity(), LoginActivity.class);

        username = (TextView) view.findViewById(R.id.username);
        profile_picture =(CircleImageView)view.findViewById(R.id.profile_picture);
        gridview = (RecyclerView) view.findViewById(R.id.grid_videos);
        tv_following = (TextView) view.findViewById(R.id.tv_following);
        tv_followers = (TextView) view.findViewById(R.id.tv_followers);
        tv_posts = (TextView) view.findViewById(R.id.tv_posts);
        usern = (TextView) view.findViewById(R.id.usern);
        logout = (Button) view.findViewById(R.id.logout);

        edit_profile = (Button) view.findViewById(R.id.edit_profil);


        // title_toolbar = (TextView) view.findViewById(R.id.title_toolbar);

        thumbAdapter = new ThumbAdapter(getActivity(),R.layout.grid_item,v);




        // edit_profile = (ImageView) view.findViewById(R.id.edit_profile);





        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();










        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new Edit_Profil_Fragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer, fragment)
                        .commit();

            }
        });


        tv_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new Following_Fragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer, fragment)
                        .commit();

            }
        });



        tv_followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new Followers_Fragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer, fragment)
                        .commit();

            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
               try {
                   Auth.GoogleSignInApi.signOut(mGoogleApiClient);
               }
               catch (Exception e)
               {
                   System.out.println(e);
               }
                startActivity(intent);


            }
        });










        return view;
    }
    protected void revokeAccess() {

        Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        mGoogleApiClient.disconnect();
                        mGoogleApiClient.connect();
                        // Clear data and go to login activity
                    }
                });
    }
    @Override
    public void onStart() {
        super.onStart();



        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);


v.clear();
       // showProgressDialog();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.v("test", user.getEmail());

        myRef.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                Log.v("url", user.getProfilePictureURL());
                url = user.getProfilePictureURL();


                //   Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/HelveticaNeueIt.ttf");

                // username.setTypeface(custom_font);

                // Typeface custom_font2 = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/HelveticaNeue Light.ttf");
                //   tv_followers.setTypeface(custom_font2);
                //  tv_following.setTypeface(custom_font2);
                // tv_posts.setTypeface(custom_font2);
                //  usern.setTypeface(custom_font2);

                username.setText(user.getFullname());
                usern.setText("@"+user.getUsername());
                //   title_toolbar.setText(user.getUsername());
                //   profile_picture.setImageURI(Uri.parse(url));
                Picasso.with(getActivity()).load(Uri.parse(url)).into(profile_picture);
                tv_followers.setText("Followers "+user.getFollowersCount());
                tv_following.setText("Following "+user.getFollowingCount());

                mParam1 = user.getFullname() ;


                //   new DownLoadImageTask(profile_picture).execute(user.getProfilePictureURL());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        myRefvideos.child("videos").child(user.getUid()).addChildEventListener(new ChildEventListener() {
                                                                                   @Override
                                                                                   public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                                                       Videos video = dataSnapshot.getValue(Videos.class);
                                                                                       Map<String, String> videosDictionnay = (HashMap<String,String>) dataSnapshot.getValue();

                                                                                       System.out.println("ios");
                                                                                       System.out.println(video.getSongPicture());


                                                                                       v.add(video);


                                                                                       System.out.println(dataSnapshot);

                                                                                       //  gridview.setAdapter(thumbAdapter);
                                                                                       System.out.println("video size ");

                                                                                       VideoSize = v.size() ;

                                                                                       System.out.println(video.getURL());
                                                                                       setupRecyclerView(gridview);

                                                                                   }

                                                                                   @Override
                                                                                   public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                                                                   }

                                                                                   @Override
                                                                                   public void onChildRemoved(DataSnapshot dataSnapshot) {

                                                                                   }

                                                                                   @Override
                                                                                   public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                                                                   }
                                                                                   @Override
                                                                                   public void onCancelled(DatabaseError databaseError) {
                                                                                   }

                                                                               }

        )
        ;





    }


    public void setupRecyclerView(RecyclerView recyclerView) {

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        // snapHelper.attachToRecyclerView(recyclerView);


        final LinearLayoutManager lm = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false) ;


        recyclerView.setLayoutManager(lm);

        recyclerView.setAdapter(new RecycleView_videos(getActivity(),R.layout.grid_item,v));
        tv_posts.setText("Post "+Integer.toString(v.size()));

        //  recyclerView.setOnFlingListener(snapHelper);
        // SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        // snapHelper.attachToRecyclerView(recyclerView);
        //hideProgressDialog();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean scrollingUp;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                scrollingUp = dx < 0;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int visiblePosition = scrollingUp ? lm.findFirstVisibleItemPosition() : lm.findLastVisibleItemPosition();
                int completelyVisiblePosition = scrollingUp ? lm
                        .findFirstCompletelyVisibleItemPosition() : lm
                        .findLastCompletelyVisibleItemPosition();
                if (visiblePosition != completelyVisiblePosition) {
                    recyclerView.smoothScrollToPosition(visiblePosition);
                    return;
                }
            }
        });

    }


}
