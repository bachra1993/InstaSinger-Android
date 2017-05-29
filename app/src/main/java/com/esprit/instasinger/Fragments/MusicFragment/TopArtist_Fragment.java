package com.esprit.instasinger.Fragments.MusicFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
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

import com.esprit.instasinger.Adapter.ArtistAdapter;
import com.esprit.instasinger.Data.Artist;
import com.esprit.instasinger.R;


public class TopArtist_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<Artist> ArtistList = new ArrayList<Artist>();

    ArtistAdapter artistAdapter;
    GridView lv_Music ;
    private ProgressDialog pDialog;



    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }



    //Passing Data
    TopArtist_Fragment.OnFragmentSendTopArtist UIDSend ;
    public  interface   OnFragmentSendTopArtist {
        public void onSendUIDTopArtist(Artist artisttoSend);
    }




    public TopArtist_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopArtist_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopArtist_Fragment newInstance(String param1, String param2) {
        TopArtist_Fragment fragment = new TopArtist_Fragment();
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
        View view =  inflater.inflate(R.layout.fragment_top_artist_, container, false);


        lv_Music = (GridView) view.findViewById(R.id.lv_Music);
        artistAdapter = new ArtistAdapter(getActivity(),R.layout.artist_item, ArtistList);




        lv_Music.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                System.out.println("artist taped");
                UIDSend.onSendUIDTopArtist(ArtistList.get(i));


            }
        });



        return view ;
    }


    @Override
    public void onStart() {
        super.onStart();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);


        showProgressDialog();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://api.deezer.com/chart/0";


// Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //      System.out.println(response);


                        try {
                            System.out.println(response);
                            System.out.println("lol");
                            JSONObject ok = response.getJSONObject("artists");



                            System.out.println(ok);
                            JSONArray data = ok.getJSONArray("data");

                            for (int i = 0; i < data.length(); i++) {

                                Artist artist = new Artist();
                                System.out.println("test music");
                                JSONObject Data = data.getJSONObject(i);
                                System.out.println(Data.getString("name"));
                                //Artist




                                artist.setName(Data.getString("name"));
                                artist.setArtist_picture(Data.getString("picture_big"));
                                artist.setTop_url(Data.getString("tracklist"));
                                ArtistList.add(artist);


                                lv_Music.setAdapter(artistAdapter);

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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            UIDSend = (TopArtist_Fragment.OnFragmentSendTopArtist)activity ;

        }catch (ClassCastException e ) {
            throw  new ClassCastException(activity.toString() + " Must implement the interface");
        }
    }


}
