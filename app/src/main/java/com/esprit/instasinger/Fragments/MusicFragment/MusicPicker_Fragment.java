package com.esprit.instasinger.Fragments.MusicFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.esprit.instasinger.Adapter.MusicAdapter;
import com.esprit.instasinger.Data.Music;
import com.esprit.instasinger.R;


public class MusicPicker_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SearchView search_view ;

    private ProgressDialog pDialog;
    ArrayList<Music> SongList = new ArrayList<Music>();
    GridView lv_Music ;
    MusicAdapter musicAdapter;

    String searchText = "y" ;



    //Passing Data
    MusicPicker_Fragment.OnFragmentSendMusic UIDSend ;
    public  interface   OnFragmentSendMusic {
        public void onSendUIDMusic(Music musicToSend);
    }



    public MusicPicker_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicPicker_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicPicker_Fragment newInstance(String param1, String param2) {
        MusicPicker_Fragment fragment = new MusicPicker_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }
    String song ;

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
        View view =  inflater.inflate(R.layout.fragment_music_picker_, container, false);

        lv_Music = (GridView) view.findViewById(R.id.lv_Music);
        musicAdapter = new MusicAdapter(getActivity(),R.layout.music_item, SongList);
        search_view = (SearchView) view.findViewById(R.id.search_view);


       // setHasOptionsMenu(true);


        lv_Music.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                UIDSend.onSendUIDMusic(SongList.get(i));


            }
        });





        return view ;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (searchText.equals("") == false) {

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);


            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            String url = "https://api.deezer.com/search?q=" + searchText;


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
                                    String singerPicture = artist.getString("picture_big");


                                    //Song
                                    song = Data.getString("title_short");
                                    String preview = Data.getString("preview");


                                    music.setSong(song);
                                    music.setSinger(singer);
                                    music.setPreview(preview);
                                    music.setSingerPicture(singerPicture);
                                    SongList.add(music);

                                    System.out.println(artist.getString("name"));

                                    lv_Music.setAdapter(musicAdapter);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            hideProgressDialog();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    hideProgressDialog();
                }
            });
// Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    @Override
    public void onStart() {
        super.onStart();







        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchText = query ;

                final JSONArray[] data = {null};



                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Loading...");
                pDialog.setCancelable(false);







                showProgressDialog();
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url ="https://api.deezer.com/search?q="+query;


// Request a string response from the provided URL.
                JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,(JSONObject) null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Display the first 500 characters of the response string.
                                //      System.out.println(response);






                                try {
                                    System.out.println("lol");



                                            System.out.println(response.getString("total"));
                                     data[0] = response.getJSONArray("data");

                                    for (int i = 0; i < data[0].length() ; i ++) {
                                        JSONObject Data = new JSONObject() ;



                                        Music music = new Music();

                                         Data  = data[0].getJSONObject(i);
                                        System.out.println(     Data.getString("title_short"));
                                        //Artist
                                        JSONObject artist = Data.getJSONObject("artist");
                                        String singer = artist.getString("name");
                                        String singerPicture = artist.getString("picture_big");


                                        //Song
                                        song = Data.getString("title_short");
                                        String preview = Data.getString("preview");




                                        music.setSong(song);
                                        music.setSinger(singer);
                                        music.setPreview(preview);
                                        music.setSingerPicture(singerPicture);
                                        SongList.add(0,music);

                                        System.out.println(artist.getString("name"));

                                        lv_Music.setAdapter(musicAdapter);

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                hideProgressDialog();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        hideProgressDialog() ;
                    }
                });
// Add the request to the RequestQueue.
                queue.add(stringRequest);



                search_view.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });















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


}
