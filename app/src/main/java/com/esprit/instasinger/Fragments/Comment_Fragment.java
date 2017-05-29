package com.esprit.instasinger.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import com.esprit.instasinger.Adapter.CommentsAdapter;
import com.esprit.instasinger.Data.Comment;
import com.esprit.instasinger.Data.User;
import com.esprit.instasinger.R;

import static com.esprit.instasinger.R.id.lv_Music;


public class Comment_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "madrid";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText comment_Box ;
    Button send_comment ;
    CommentsAdapter commentsAdapter ;
    public User userInfo = new User() ;
    FrameLayout comment_frame ;

    ArrayList<Comment> commentArrayList = new ArrayList<Comment>();
    SwipeMenuListView lv_Comment ;


    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRefInsert = database.getReference();



    public Comment_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Comment_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Comment_Fragment newInstance(String param1, String param2) {
        Comment_Fragment fragment = new Comment_Fragment();
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

            System.out.println("my passed key2");
            System.out.println(mParam1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }









    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_comment_, container, false);


        comment_Box = (EditText) view.findViewById(R.id.comment_Box);
        send_comment = (Button) view.findViewById(R.id.send_comment);
        comment_frame = (FrameLayout) view.findViewById(R.id.comment_frame);


        lv_Comment = (SwipeMenuListView) view.findViewById(lv_Music);
        commentsAdapter = new CommentsAdapter(getActivity(),R.layout.comment_item, commentArrayList);

        return view ;
    }

    @Override
    public void onStart() {
        super.onStart();

        commentArrayList.clear();


        Calendar c = Calendar.getInstance();
        int minute = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR);
        int day = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        System.out.println("wa9et" + day);

        final String commentDate =        hour+":"+minute+" - "+day+"/"+month+"/"+year ;

        System.out.println(commentDate);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        myRefInsert.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userInfo = dataSnapshot.getValue(User.class);

                //   new DownLoadImageTask(profile_picture).execute(user.getProfilePictureURL());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });











        myRefInsert.child("comments").child(mParam1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                final Comment c = dataSnapshot.getValue(Comment.class);



                commentArrayList.add(c);

                lv_Comment.setAdapter(commentsAdapter);

                System.out.println(c.getUserPostedName());


                System.out.println("user uid ");
                System.out.println(user.getUid());
                System.out.println("comment uid");
                System.out.println(c.getUserId());


                if(user.getUid()==c.getUserId()){
                    System.out.println("condition done");
                }
                else {
                    System.out.println("condition not done ");
                }


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
                        openItem.setTitle("Delete");
                        // set item title fontsize
                        openItem.setTitleSize(18);
                        // set item title font color
                        openItem.setTitleColor(Color.WHITE);
                        // add to menu
                        menu.addMenuItem(openItem);


                    }
                };

// set creator
                lv_Comment.setMenuCreator(creator);
                lv_Comment.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

                lv_Comment.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {


                        switch (index) {
                            case 0:

                                System.out.println("swipe menu taped");
                                System.out.println(position);
                                Comment m = new Comment();
                                m = commentArrayList.get(position);
                                if (user.getUid().equals(m.getUserId())) {


                                    myRefInsert.child("comments").child(mParam1).child(dataSnapshot.getKey()).removeValue();

                                    commentArrayList.remove(position);
                                    commentsAdapter.notifyDataSetChanged();
                                    Toast toast = Toast.makeText(getActivity(), "Comment deleted", Toast.LENGTH_SHORT);
                                    toast.show();


                                }
                                else {

                                    System.out.println("cant delete this shit");
                                    Toast toast = Toast.makeText(getActivity(), "can't delete this comment", Toast.LENGTH_SHORT);
                                    toast.show();
                                }

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


        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                Comment comment = new Comment();

                comment.setText(comment_Box.getText().toString());
                comment.setDate(commentDate);
                comment.setUserPostedName(userInfo.getFullname());
                comment.setUserPostedPicture(userInfo.getProfilePictureURL());
                comment.setUserPostedUsername(userInfo.getUsername());
                comment.setUserId(user.getUid());

                myRefInsert.child("comments").child(mParam1).push().setValue(comment);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(comment_frame.getWindowToken(), 0);
                comment_Box.setText("");

            }
        });



    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


}
