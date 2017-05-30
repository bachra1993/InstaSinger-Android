package com.esprit.instasinger.Fragments.MusicFragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.esprit.instasinger.Adapter.MusicAdapter;
import com.esprit.instasinger.Data.Artist;
import com.esprit.instasinger.Data.Music;
import com.esprit.instasinger.R;
import com.esprit.instasinger.Sqlight.MusicDao;


public class ArtistTop50_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<Music> SongList = new ArrayList<Music>();
    SwipeMenuListView lv_Music ;
    MusicAdapter musicAdapter;
    String song;

    private static Artist artistPassed = new Artist();


    //Passing Data
    MusicPicker_Fragment.OnFragmentSendMusic UIDSend ;
    public  interface   OnFragmentSendMusic {
        public void onSendUIDMusic(Music musicToSend);
    }



    public ArtistTop50_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArtistTop50_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArtistTop50_Fragment newInstance(String param1, String param2) {
        ArtistTop50_Fragment fragment = new ArtistTop50_Fragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1,artistPassed);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            artistPassed = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_artist_top50_, container, false);


        lv_Music = (SwipeMenuListView) view.findViewById(R.id.lv_Music);
        musicAdapter = new MusicAdapter(getActivity(),R.layout.music_item, SongList);

        lv_Music.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                UIDSend.onSendUIDMusic(SongList.get(i));


            }
        });


        return  view ;
    }


    @Override
    public void onStart() {
        super.onStart();

        final MusicDao musicDao = new MusicDao(getActivity());


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = artistPassed.getTop_url();


// Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //      System.out.println(response);


                        try {
                            System.out.println("lol");
                            System.out.println(response.getString("total"));
                            JSONArray data = response.getJSONArray("data");

                            for (int i = 0; i < data.length(); i++) {

                                Music music = new Music();

                                JSONObject Data = data.getJSONObject(i);
                                System.out.println(Data.getString("title_short"));
                                //Artist
                                JSONObject artist = Data.getJSONObject("artist");
                                String singer = artist.getString("name");
                                //String singerPicture = artist.getString("picture_big");





                                //Song
                                song = Data.getString("title_short");
                                String preview = Data.getString("preview");


                                music.setSong(song);
                                music.setSinger(singer);
                                music.setPreview(preview);
                                music.setSingerPicture(artistPassed.getArtist_picture());
                                SongList.add(music);


                                System.out.println(artist.getString("name"));


                                lv_Music.setAdapter(musicAdapter);

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
                                        openItem.setTitle("favorite");
                                        // set item title fontsize
                                        openItem.setTitleSize(18);
                                        // set item title font color
                                        openItem.setTitleColor(Color.WHITE);
                                        // add to menu
                                        menu.addMenuItem(openItem);


                                    }
                                };

// set creator
                                lv_Music.setMenuCreator(creator);
                                lv_Music.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

                                lv_Music.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {


                                        switch (index) {
                                            case 0:
                                                System.out.println("swipe menu taped");
                                                System.out.println(position);
                                                Music m = new Music();
                                                m = SongList.get(position);




                                                musicDao.addMusic(m);
                                                Toast.makeText(getActivity(), "Song Added to favorites",
                                                        Toast.LENGTH_SHORT).show();



                                                System.out.println(m.getSinger());

                                                break;

                                        }

                                        return false;
                                    }
                                });


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                       // hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

               // hideProgressDialog();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            UIDSend = (MusicPicker_Fragment.OnFragmentSendMusic)activity ;

        }catch (ClassCastException e ) {
            throw  new ClassCastException(activity.toString() + " Must implement the interface");
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


}

