package com.esprit.instasinger.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import com.esprit.instasinger.Adapter.UserAdapter;
import com.esprit.instasinger.Data.User;
import com.esprit.instasinger.Fragments.ChatFragment.SendMessage_Fragment;
import com.esprit.instasinger.R;


public class Search_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SearchView search_view ;
    TabLayout tabs ;



    private String url;
    TextView username;
    CircleImageView profile_picture;
    Button following;
    Button followers;
    RecyclerView gridview;
    String OtherUserUID;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    SwipeMenuListView lv_following;
    UserAdapter userAdapter;
    ArrayList<User> UserList = new ArrayList<User>();
    ArrayList<String> ListUID = new ArrayList<String>();


    OnFragmentSendUID UIDSend ;
    public  interface   OnFragmentSendUID {
        public void onSendUID(String UID);
    }



    public Search_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Search_Fragment newInstance(String param1, String param2) {
        Search_Fragment fragment = new Search_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_search_, container, false);


        lv_following = (SwipeMenuListView) view.findViewById(R.id.lv_following);
        userAdapter = new UserAdapter(getActivity(), R.layout.follow_item, UserList);
        search_view = (SearchView) view.findViewById(R.id.search_view);
        tabs = (TabLayout) view.findViewById(R.id.tabs);



        lv_following.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                UIDSend.onSendUID(ListUID.get(i));





            }
        });



        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int  pos = tab.getPosition();
                switch (pos) {

                    case 0:
                        fetchUsersMostFollower () ;




                        return;
                    case 1:
                        fetchUsers();



                        return;



                    default:
                        return;



                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int  pos = tab.getPosition();
                switch (pos) {

                    case 0:
                        fetchUsersMostFollower () ;




                        return;
                    case 1:
                        fetchUsers();



                        return;



                    default:
                        return;



                }

            }
        });




        return view;


    }


    @Override
    public void onStart() {
        super.onStart();

        Toast.makeText(getActivity(), "Swipe left a user to  send a message", Toast.LENGTH_SHORT).show();



        fetchUsersMostFollower () ;



        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println(query);

                userAdapter.clear();

                myRef.child("users").orderByChild("username").equalTo(query).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        // User user  = dataSnapshot.getValue(User.class);

                        System.out.println("Looking for ahmado");
                        System.out.println(dataSnapshot);

                        Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                        System.out.println("useeeeeeeer");
                        System.out.println(value.get("fullname"));
                        OtherUserUID = dataSnapshot.getKey();
                        System.out.println(OtherUserUID);
                        ListUID.clear();
                        ListUID.add(OtherUserUID);


                        String fullname = (String) value.get("fullname");
                        String profilePictureURL = (String) value.get("profilePictureURL");


                        //System.out.println(user);


                        User user = new User(fullname, profilePictureURL);
                        UserList.add(user);


                        lv_following.setAdapter(userAdapter);

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




                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }




    public void fetchUsersMostFollower() {

        userAdapter.clear();



        myRef.child("users").orderByChild("followersCount").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // User user  = dataSnapshot.getValue(User.class);


                System.out.println("search");
                System.out.println(dataSnapshot);

                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                System.out.println("useeeeeeeer");
                System.out.println(value.get("fullname"));
                OtherUserUID = dataSnapshot.getKey();
                System.out.println(OtherUserUID);
                ListUID.add(OtherUserUID);






                String fullname = (String) value.get("fullname");
                String profilePictureURL = (String) value.get("profilePictureURL");


                //System.out.println(user);


                User user = new User(fullname, profilePictureURL);
                UserList.add(user);
                Collections.reverse(ListUID);
                Collections.reverse(UserList);
                lv_following.setAdapter(userAdapter);

                SwipeMenuCreator creator = new SwipeMenuCreator() {

                    @Override
                    public void create(SwipeMenu menu) {
                        // create "open" item
                        SwipeMenuItem openItem = new SwipeMenuItem(
                                getActivity());
                        // set item background
                        openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                                0xCE)));
                        // set item width
                        openItem.setWidth(dp2px(90));
                        // set item title
                        openItem.setTitle("Message");
                        // set item title fontsize
                        openItem.setTitleSize(18);
                        // set item title font color
                        openItem.setTitleColor(Color.WHITE);
                        // add to menu
                        menu.addMenuItem(openItem);


                    }
                };

// set creator
                lv_following.setMenuCreator(creator);
                lv_following.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);


                lv_following.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {


                        switch (index) {
                            case 0:
                                System.out.println("swipe menu taped");
                                System.out.println(position);
                                Fragment fragment = new SendMessage_Fragment();
                                SendMessage_Fragment.ARG_PARAM3 = ListUID.get(position) ;
                                getFragmentManager().beginTransaction().replace(R.id.contentContainer,fragment).commit();



                                break;

                        }

                        return false;
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

    }




    public void fetchUsers () {
        userAdapter.clear();
        ListUID.clear();


        myRef.child("users").orderByChild("fullname").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // User user  = dataSnapshot.getValue(User.class);


                System.out.println("search");
                System.out.println(dataSnapshot);

                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                System.out.println("useeeeeeeer");
                System.out.println(value.get("fullname"));
                OtherUserUID = dataSnapshot.getKey();
                System.out.println(OtherUserUID);
                ListUID.add(OtherUserUID);


                String fullname = (String) value.get("fullname");
                String profilePictureURL = (String) value.get("profilePictureURL");


                //System.out.println(user);


                User user = new User(fullname, profilePictureURL);
                UserList.add(user);

                lv_following.setAdapter(userAdapter);

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


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }








    @Override
    public void onAttach(Activity    activity) {
        super.onAttach(activity);
        try {
            UIDSend = (OnFragmentSendUID)activity ;

        }catch (ClassCastException e ) {
            throw  new ClassCastException(activity.toString() + " Must implement the interface");
        }
    }
}