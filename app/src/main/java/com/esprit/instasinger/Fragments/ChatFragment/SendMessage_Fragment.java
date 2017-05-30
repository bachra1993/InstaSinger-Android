package com.esprit.instasinger.Fragments.ChatFragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import com.esprit.instasinger.Adapter.MessageAdapter;
import com.esprit.instasinger.Data.Message;
import com.esprit.instasinger.Data.User;
import com.esprit.instasinger.R;

public class SendMessage_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String ARG_PARAM3 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<Message> ListText = new ArrayList<Message>();
    ArrayList<String>  ListMessageKey = new ArrayList<String>();
    ListView lv_ListText ;
    MessageAdapter messageAdapter ;
    Button send_text ;
    EditText text_box ;
    TextView tv_user_to_text ;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRefInsert = database.getReference();
    DatabaseReference myRefInsert2 = database.getReference();



    public SendMessage_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SendMessage_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SendMessage_Fragment newInstance(String param1, String param2) {
        SendMessage_Fragment fragment = new SendMessage_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_send_message_, container, false);

        lv_ListText = (ListView) view.findViewById(R.id.lv_ListText);
        send_text = (Button) view.findViewById(R.id.send_text);
        text_box = (EditText) view.findViewById(R.id.text_box);
        tv_user_to_text = (TextView) view.findViewById(R.id.tv_user_to_text);


        lv_ListText.setDivider(null);
        lv_ListText.setDividerHeight(0);



        messageAdapter = new MessageAdapter(getActivity(), R.layout.grid_message, ListText);



        return view ;
    }


    @Override
    public void onStart() {
        super.onStart();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        System.out.println("Verification");
        System.out.println(user.getUid());
        System.out.println(ARG_PARAM3);

        myRefInsert.child("users").child(ARG_PARAM3).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println("user info");
                        User user = dataSnapshot.getValue(User.class);
                        System.out.println(dataSnapshot);

                        tv_user_to_text.setText(user.getFullname());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );






        myRefInsert.child("User_Message").child(user.getUid()).child(ARG_PARAM3).addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        System.out.println("list message");
                        System.out.println(dataSnapshot.getKey());
                        ListMessageKey.add(dataSnapshot.getKey());


                        myRefInsert.child("Messages").child(dataSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Message message = dataSnapshot.getValue(Message.class);

                                System.out.println("my message");
                                System.out.println(dataSnapshot);
                                System.out.println(message.getText());
                                ListText.add(message);
                                lv_ListText.setAdapter(messageAdapter);

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



                send_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Message message = new Message();

                        message.setText(text_box.getText().toString());
                        message.setFromId(user.getUid());
                        message.setToId("11p3lZq3RcXiSQUiDKscK2Q0ldr2");
                        message.setTime("date");


                        String key = myRefInsert.push().getKey();
                        HashMap<String, String> msgList = new HashMap<>();
                        msgList.put(key.toString(), "2");
                        myRefInsert2.child("User_Message").child(user.getUid()).child(ARG_PARAM3).child(key).setValue("1");
                        myRefInsert2.child("User_Message").child(ARG_PARAM3).child(user.getUid()).child(key).setValue("1");

                        myRefInsert.child("Messages").child(key).setValue(message);
                        System.out.println("keyyy");
                        System.out.println(key);

                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(send_text.getWindowToken(), 0);
                        text_box.setText("");

                    }
                });

    }
}
