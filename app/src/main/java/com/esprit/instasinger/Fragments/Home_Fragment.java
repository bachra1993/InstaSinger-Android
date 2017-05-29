package com.esprit.instasinger.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import com.esprit.instasinger.Adapter.RecycleView_videos_home;
import com.esprit.instasinger.Adapter.ThumbAdapter;
import com.esprit.instasinger.Data.Videos;
import com.esprit.instasinger.R;


public class Home_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    //Data Base References


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference myRefvideos = database.getReference();

    ArrayList<Videos> v = new ArrayList<Videos>();
    ArrayList<String> f = new ArrayList<String>();

    RecyclerView gridview ;
    ThumbAdapter thumbAdapter ;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int VideoSize ;
    CircleImageView song_picture ;


    public Home_Fragment() {
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
    public static Home_Fragment newInstance(String param1, String param2) {
        Home_Fragment fragment = new Home_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_home_, container, false);


        gridview = (RecyclerView) view.findViewById(R.id.grid_videos);


       // song_picture = (CircleImageView) view.findViewById(R.id.song_picture);



        // title_toolbar = (TextView) view.findViewById(R.id.title_toolbar);

        //thumbAdapter = new ThumbAdapter(getActivity(),R.layout.grid_item_home,v);






        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();




        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        v.clear();
        f.clear();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myRefvideos.child("following").child(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshotfollowing, String s) {

                System.out.println(dataSnapshotfollowing.getKey());

        myRefvideos.child("videos").child(dataSnapshotfollowing.getKey()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Videos video = dataSnapshot.getValue(Videos.class);
                Map<String, String> videosDictionnay = (HashMap<String,String>) dataSnapshot.getValue();

                v.add(video);


                System.out.println("video info ");
                System.out.println(video.getSinger());





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
        });

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
        });


        myRefvideos.child("Likes").child(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                f.add(dataSnapshot.getKey());

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
        });




    }


    public void setupRecyclerView(RecyclerView recyclerView) {

        LinearSnapHelper snapHelper = new LinearSnapHelper();
       // snapHelper.attachToRecyclerView(recyclerView);


        final LinearLayoutManager lm = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false) ;


        recyclerView.setLayoutManager(lm);

        recyclerView.setAdapter(new RecycleView_videos_home(getActivity(),R.layout.grid_item_home,v,f));

      //  recyclerView.setOnFlingListener(snapHelper);
       // SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
       // snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean scrollingUp;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                scrollingUp = dy < 0;
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
