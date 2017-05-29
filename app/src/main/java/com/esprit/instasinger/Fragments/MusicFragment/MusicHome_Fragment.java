package com.esprit.instasinger.Fragments.MusicFragment;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import com.esprit.instasinger.R;


public class MusicHome_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FloatingActionButton search_button ;
    ImageView imageView ;


    TabLayout tabs ;
    String URLPicture = "https://cdns-images.dzcdn.net/images/artist/15899685d14010a641b1fbb4fb960f98/500x500-000000-80-0-0.jpg";



    public MusicHome_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicHome_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicHome_Fragment newInstance(String param1, String param2) {
        MusicHome_Fragment fragment = new MusicHome_Fragment();
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
        View view =  inflater.inflate(R.layout.fragment_music_home_, container, false);

        AppBarLayout appbar = (AppBarLayout) view.findViewById(R.id.appbar);
        tabs = (TabLayout) view.findViewById(R.id.tabs);
        search_button = (FloatingActionButton) view.findViewById(R.id.search_button);
        imageView = (ImageView) view.findViewById(R.id.imageView);


        Picasso.with(getActivity()).load(Uri.parse(URLPicture)).into(imageView);




        getFragmentManager().beginTransaction().replace(R.id.music_Container,new BestOf_fragment()).commit();


        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int  pos = tab.getPosition();
                switch (pos) {

                    case 0:
                        String URLPicture = "https://cdns-images.dzcdn.net/images/artist/15899685d14010a641b1fbb4fb960f98/500x500-000000-80-0-0.jpg";
                        Picasso.with(getActivity()).load(Uri.parse(URLPicture)).into(imageView);

                        getFragmentManager().beginTransaction().replace(R.id.music_Container,new BestOf_fragment()).commit();

                        return;
                    case 1:
                        URLPicture = "https://cdns-images.dzcdn.net/images/artist/aceaee4d98c8d94640a6b2e5a3bdf843/500x500-000000-80-0-0.jpg";
                        Picasso.with(getActivity()).load(Uri.parse(URLPicture)).into(imageView);

                        getFragmentManager().beginTransaction().replace(R.id.music_Container,new TopArtist_Fragment()).commit();



                        return;

                    case 2 :

                        getFragmentManager().beginTransaction().replace(R.id.music_Container,new Favorite_Fragment()).commit();

                        return ;



                    default:
                        return;



                }






            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.music_Container,new MusicPicker_Fragment()).commit();


            }
        });


        return view ;

    }


}
