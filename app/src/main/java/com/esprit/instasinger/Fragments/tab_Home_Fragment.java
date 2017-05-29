package com.esprit.instasinger.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esprit.instasinger.R;


public class tab_Home_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TabLayout tabs ;



    public tab_Home_Fragment() {
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
    public static tab_Home_Fragment newInstance(String param1, String param2) {
        tab_Home_Fragment fragment = new tab_Home_Fragment();
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
        View view =  inflater.inflate(R.layout.fragment_tab_home_, container, false);

        tabs = (TabLayout) view.findViewById(R.id.tabs);




        getFragmentManager().beginTransaction().replace(R.id.music_Container,new Home_Fragment()).commit();


        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int  pos = tab.getPosition();
                switch (pos) {

                    case 0:
                        getFragmentManager().beginTransaction().replace(R.id.music_Container,new Home_Fragment()).commit();

                        return;
                    case 1:
                        getFragmentManager().beginTransaction().replace(R.id.music_Container,new Like_Home_Fragment()).commit();



                        return;
                    case 2:
                        getFragmentManager().beginTransaction().replace(R.id.music_Container,new Home_Fragment()).commit();



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

            }
        });





        return view ;

    }


}
