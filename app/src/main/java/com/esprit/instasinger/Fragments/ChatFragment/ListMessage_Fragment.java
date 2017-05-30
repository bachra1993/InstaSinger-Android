package com.esprit.instasinger.Fragments.ChatFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.esprit.instasinger.Adapter.ListMessageAdapter;
import com.esprit.instasinger.Data.User;
import com.esprit.instasinger.Fragments.Search_Fragment;
import com.esprit.instasinger.R;

public class ListMessage_Fragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    ListView lv_listMessage;
    ArrayList<User> ListUserMessage = new ArrayList<User>();
    ArrayList<String> ListUID = new ArrayList<String>();

    ListMessageAdapter listMessageAdapter ;
    FloatingActionButton floatingActionButton ;


    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRefInsert = database.getReference();






    public ListMessage_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListMessage_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListMessage_Fragment newInstance(String param1, String param2) {
        ListMessage_Fragment fragment = new ListMessage_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_list_message_, container, false);


        lv_listMessage =(ListView) view.findViewById(R.id.lv_listMessage);
        listMessageAdapter = new ListMessageAdapter(getActivity(), R.layout.grid_list_message, ListUserMessage);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);



        lv_listMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Fragment fragment = new SendMessage_Fragment();
                SendMessage_Fragment.ARG_PARAM3 = ListUID.get(i) ;
                getFragmentManager().beginTransaction().replace(R.id.contentContainer,fragment).commit();

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.contentContainer,new Search_Fragment()).commit();

            }
        });


        return view ;
    }


    @Override
    public void onStart() {
        super.onStart();


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myRefInsert.child("User_Message").child(user.getUid()).addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        System.out.println("messageti");
                        System.out.println(dataSnapshot);

                        System.out.println("key");
                        System.out.println(dataSnapshot.getKey());



                        //fetch users Info

                        myRefInsert.child("users").child(dataSnapshot.getKey()).addValueEventListener(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        System.out.println("user info");
                                    //    System.out.println(dataSnapshot);
                                        User user = dataSnapshot.getValue(User.class);

                                        System.out.println(user.getFullname());
                                        ListUserMessage.add(user);
                                        ListUID.add(dataSnapshot.getKey());
                                        lv_listMessage.setAdapter(listMessageAdapter);

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }
                        );


                                //Fetch Message
                                myRefInsert.child("User_Message").child(user.getUid()).child(dataSnapshot.getKey()).addChildEventListener(
                                        new ChildEventListener() {
                                            @Override
                                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                System.out.println("2eme lista");
                                                System.out.println(dataSnapshot);

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
                }
        );






    }
}
