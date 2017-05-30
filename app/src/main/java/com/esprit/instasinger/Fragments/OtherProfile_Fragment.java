package com.esprit.instasinger.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
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
import android.widget.TextView;

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
import com.esprit.instasinger.Data.User;
import com.esprit.instasinger.Data.Videos;
import com.esprit.instasinger.R;


public class OtherProfile_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public static final String ARG_ITEM_ID = "madrid";

    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String OtherUserUID;



    private  String url ;
    TextView username ;
    CircleImageView profile_picture;
    TextView following;
    TextView followers;
    TextView tv_posts ;
    Button follow ;
    int ConnectedFollowingCount;
    int FollowersCount ;
    TextView usern ;
    RecyclerView gridview ;
    String ConnectedUserFullName;
    String ConnectedUserProfilePicture ;
    ArrayList<Videos> v = new ArrayList<Videos>();



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference myRefInsert = database.getReference();

    private ProgressDialog pDialog;


    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }






    OtherProfile_Fragment.OnFragmentSendOtherUID UIDSend ;

    public  interface   OnFragmentSendOtherUID {
        public void onSendOtherUID(String UID);
    }


    OtherProfile_Fragment.OnFragmentSendOtherUIDF UIDSendF ;

    public  interface   OnFragmentSendOtherUIDF {
        public void onSendOtherUIDF(String UID);
    }






    public OtherProfile_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OtherProfile_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OtherProfile_Fragment newInstance(String param1, String param2) {
        OtherProfile_Fragment fragment = new OtherProfile_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_ID, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);



       // showProgressDialog();


        System.out.println(ARG_ITEM_ID);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_ITEM_ID);
            System.out.println(mParam1);
            final FirebaseUser userr = FirebaseAuth.getInstance().getCurrentUser();


            myRef.child("users").child(userr.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User ConnectedUser = dataSnapshot.getValue(User.class);
                    System.out.println("conenctedUser"+ConnectedUser.getFullname());
                    ConnectedFollowingCount = ConnectedUser.getFollowingCount() ;
                    ConnectedUserFullName = ConnectedUser.getFullname();
                    ConnectedUserProfilePicture = ConnectedUser.getProfilePictureURL() ;
                    System.out.println("nono"+ConnectedUserFullName);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            }) ;


            myRef.child("users").child(mParam1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    final User user = dataSnapshot.getValue(User.class);

                    Log.v("url", user.getProfilePictureURL());
                    url = user.getProfilePictureURL();
                    usern.setText(user.getFullname());
                    username.setText("@"+user.getUsername());
                    OtherUserUID = dataSnapshot.getKey() ;
                    Picasso.with(getActivity()).load(Uri.parse(url)).into(profile_picture);
                    System.out.println("chek user");
                    System.out.println(user.getFollowersCount());
                    followers.setText("Followers "+user.getFollowersCount());
                    following.setText("Following "+user.getFollowingCount());


                    FollowersCount = user.getFollowersCount();
                    System.out.println("followers"+FollowersCount);








                    myRef.child("following").child(userr.getUid()).child(OtherUserUID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                follow.setText("Following");
                                System.out.println("déja Following");
                            }
                            else {
                                follow.setText("Follow");

                                System.out.println("Not Following");
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });







                    follow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(follow.getText()=="Follow")
                            {

                            User FollwingUser = new User() ;
                            FollwingUser.setFullname(user.getFullname());
                            FollwingUser.setProfilePictureURL(user.getProfilePictureURL());

                            System.out.println("okok"+ConnectedUserFullName);

                            myRefInsert.child("following").child(userr.getUid()).child(OtherUserUID).child("fullname").setValue(user.getFullname());
                            myRefInsert.child("following").child(userr.getUid()).child(OtherUserUID).child("profilePictureURL").
                                    setValue(user.getProfilePictureURL());

                            myRefInsert.child("users").child(userr.getUid()).child("followingCount").setValue(ConnectedFollowingCount += 1);

                            myRefInsert.child("users").child(OtherUserUID).child("followersCount").setValue(FollowersCount += 1);


                            myRefInsert.child("followers").child(OtherUserUID).child(userr.getUid()).child("fullname").setValue(ConnectedUserFullName);
                            myRefInsert.child("followers").child(OtherUserUID).child(userr.getUid()).child("profilePictureURL").
                                    setValue(ConnectedUserProfilePicture);

                        }
                        else {

                                System.out.println("unffolowing");

                                myRefInsert.child("following").child(userr.getUid()).child(OtherUserUID).child("fullname").setValue(null);
                                myRefInsert.child("following").child(userr.getUid()).child(OtherUserUID).child("profilePictureURL").
                                        setValue(null);



                                myRefInsert.child("users").child(userr.getUid()).child("followingCount").setValue(ConnectedFollowingCount -= 1);

                                myRefInsert.child("users").child(OtherUserUID).child("followersCount").setValue(FollowersCount -= 1);



                                myRefInsert.child("followers").child(OtherUserUID).child(userr.getUid()).child("fullname").setValue(null);
                                myRefInsert.child("followers").child(OtherUserUID).child(userr.getUid()).child("profilePictureURL").
                                        setValue(null);


                            }




                        }
                    });



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            myRef.child("videos").child(mParam1).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Videos video = dataSnapshot.getValue(Videos.class);
                    Map<String, String> videosDictionnay = (HashMap<String,String>) dataSnapshot.getValue();
                     System.out.println("ios");
                    System.out.println(video.getSongPicture());


                    v.add(video);
                    tv_posts.setText("Posts "+ Integer.toString(v.size()));


                    System.out.println(dataSnapshot);

                    //  gridview.setAdapter(thumbAdapter);
                    System.out.println("video size ");


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


            System.out.println("test"+userr.getUid());
            System.out.println("test"+OtherUserUID);
/*




**/



            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_other_profile_, container, false);


        username = (TextView) view.findViewById(R.id.username);
        usern = (TextView) view.findViewById(R.id.usern);

        profile_picture =(CircleImageView)view.findViewById(R.id.profile_picture);
        gridview = (RecyclerView) view.findViewById(R.id.grid_videos);
        followers = (TextView) view.findViewById(R.id.tv_followers);
        following = (TextView) view.findViewById(R.id.tv_following);
        follow = (Button) view.findViewById(R.id.follow);
        tv_posts = (TextView) view.findViewById(R.id.tv_posts);


        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               UIDSend.onSendOtherUID(OtherUserUID);



            }
        });



        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UIDSendF.onSendOtherUIDF(OtherUserUID);


            }
        });





        return view ;

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            UIDSend = (OtherProfile_Fragment.OnFragmentSendOtherUID)activity ;
            UIDSendF = (OtherProfile_Fragment.OnFragmentSendOtherUIDF)activity ;

        }catch (ClassCastException e ) {
            throw  new ClassCastException(activity.toString() + " Must implement the interface");
        }
    }

    public void setupRecyclerView(RecyclerView recyclerView) {

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        // snapHelper.attachToRecyclerView(recyclerView);


        final LinearLayoutManager lm = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false) ;


        recyclerView.setLayoutManager(lm);

        recyclerView.setAdapter(new RecycleView_videos(getActivity(),R.layout.grid_item,v));

        //  recyclerView.setOnFlingListener(snapHelper);
        // SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        // snapHelper.attachToRecyclerView(recyclerView);
        hideProgressDialog();

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
