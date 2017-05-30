package com.esprit.instasinger;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.esprit.instasinger.Data.Artist;
import com.esprit.instasinger.Data.Music;
import com.esprit.instasinger.Fragments.ChatFragment.ListMessage_Fragment;
import com.esprit.instasinger.Fragments.Home_Fragment;
import com.esprit.instasinger.Fragments.MusicFragment.ArtistTop50_Fragment;
import com.esprit.instasinger.Fragments.MusicFragment.Favorite_Fragment;
import com.esprit.instasinger.Fragments.MusicFragment.MusicHome_Fragment;
import com.esprit.instasinger.Fragments.MusicFragment.MusicPicker_Fragment;
import com.esprit.instasinger.Fragments.MusicFragment.PlaySong_Fragment;
import com.esprit.instasinger.Fragments.MusicFragment.TopArtist_Fragment;
import com.esprit.instasinger.Fragments.OtherFollowers_Fragment;
import com.esprit.instasinger.Fragments.OtherFollowing_Fragment;
import com.esprit.instasinger.Fragments.OtherProfile_Fragment;
import com.esprit.instasinger.Fragments.Profile_Fragment;
import com.esprit.instasinger.Fragments.Search_Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.esprit.instasinger.R.id.contentContainer;

public class Tab_Bar_Main extends AppCompatActivity implements Search_Fragment.OnFragmentSendUID
        , OtherProfile_Fragment.OnFragmentSendOtherUID, OtherProfile_Fragment.OnFragmentSendOtherUIDF ,
  MusicPicker_Fragment.OnFragmentSendMusic , TopArtist_Fragment.OnFragmentSendTopArtist , Favorite_Fragment.OnFragmentSendFavoriteMusic{

    private FirebaseAuth mAuth;
    TabItem home ;
    TabItem search ;
    TabItem shoot ;
    TabItem notification ;
    TabItem profil ;
    AppBarLayout appbar ;
    TabLayout tabs ;









    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);





        setContentView(R.layout.tab_menu);














        final Intent intent = new Intent(this, LoginActivity.class);
        // startActivity(intent);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            // String name = user.getDisplayName();
            String email = user.getEmail();
            //  Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }

        if (user != null) {
          //  getFragmentManager().beginTransaction().add(R.id.contentContainer,new Profile_Fragment()).commit();\
            getFragmentManager().beginTransaction().replace(contentContainer,new Home_Fragment()).commit();


            // User is signed in.
        } else {
            startActivity(intent);        }

       // getFragmentManager().beginTransaction().replace(R.id.contentContainer,new Search_Fragment()).commit();



        appbar = (AppBarLayout) findViewById(R.id.appbar);
        home = (TabItem) findViewById(R.id.home);
        tabs = (TabLayout) findViewById(R.id.tabs);



        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int  pos = tab.getPosition();


                switch (pos) {

                    case 0:

                        getFragmentManager().beginTransaction().replace(contentContainer,new Home_Fragment()).commit();

                        return;
                    case 1:
                        getFragmentManager().beginTransaction().replace(contentContainer,new Search_Fragment()).commit();

                        return;
                    case 2:
                        /*
                        Fragment fragment = new MusicPicker_Fragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.contentContainer, fragment)
                                .commit();
                                */

                        getFragmentManager().beginTransaction().replace(contentContainer,new MusicHome_Fragment()).commit();



                        return;

                    case 3:
                        getFragmentManager().beginTransaction().replace(contentContainer,new ListMessage_Fragment()).commit();


                        return;
                    case 4:
                        getFragmentManager().beginTransaction().replace(contentContainer,new Profile_Fragment()).commit();


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
                        getFragmentManager().beginTransaction().replace(contentContainer,new Home_Fragment()).commit();

                        return;
                    case 1:
                        getFragmentManager().beginTransaction().replace(contentContainer,new Search_Fragment()).commit();

                        return;
                    case 2:
                        /*
                        Fragment fragment = new MusicPicker_Fragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.contentContainer, fragment)
                                .commit();
                                */

                        getFragmentManager().beginTransaction().replace(contentContainer,new MusicHome_Fragment()).commit();



                        return;

                    case 3:
                        getFragmentManager().beginTransaction().replace(contentContainer,new ListMessage_Fragment()).commit();


                        return;
                    case 4:
                        getFragmentManager().beginTransaction().replace(contentContainer,new Profile_Fragment()).commit();


                        return;

                    default:
                        return;



                }

            }
        });




/*


        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_recents) {



                    getFragmentManager().beginTransaction().replace(R.id.contentContainer,new Search_Fragment()).commit();



                }


                if (tabId == R.id.tab_friends) {

                 //   getFragmentManager().beginTransaction().replace(R.id.contentContainer,new Profile_Fragment()).commit();



                    Fragment fragment = new Profile_Fragment();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.contentContainer, fragment)
                            .commit();

                    RecyclerView gridview = (RecyclerView) findViewById(R.id.grid_videos);




                    System.out.println("profile taped ");


                }

                if (tabId == R.id.tab_food) {

                    Fragment fragment = new MusicPicker_Fragment();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.contentContainer, fragment)
                            .commit();

                }


            }
        });

*/





    }


    @Override
    public void onSendUID(String UID) {


        Bundle arguments = new Bundle();
        arguments.putString(OtherProfile_Fragment.ARG_ITEM_ID,
                UID);
        System.out.println("ahmed");
        System.out.println(arguments);
        Fragment fragment = new OtherProfile_Fragment();
        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction()
                .replace(contentContainer, fragment)
                .commit();



    }

    @Override
    public void onSendOtherUID(String UID) {
        Bundle arguments = new Bundle();
        arguments.putString(OtherFollowers_Fragment.ARG_ITEM_ID,
                UID);
        System.out.println(arguments);
        Fragment fragment = new OtherFollowers_Fragment();
        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction()
                .replace(contentContainer, fragment)
                .commit();



    }

    @Override
    public void onSendOtherUIDF(String UID) {
        Bundle arguments = new Bundle();
        arguments.putString(OtherFollowing_Fragment.ARG_ITEM_ID,
                UID);
        System.out.println(arguments);
        Fragment fragment = new OtherFollowing_Fragment();
        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction()
                .replace(contentContainer, fragment)
                .commit();


    }


    //Sending music from search view
    @Override
    public void onSendUIDMusic(Music musicToSend) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(PlaySong_Fragment.ARG_PARAM1 , musicToSend);



        System.out.println(arguments);
        Fragment fragment = new PlaySong_Fragment();
        PlaySong_Fragment f = new PlaySong_Fragment() ;

        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction()
                .replace(contentContainer, fragment)
                .commit();



    }


    //Send artist to Top 5o Artist songs
    @Override
    public void onSendUIDTopArtist(Artist artisttoSend) {

        Bundle arguments = new Bundle();
        arguments.putParcelable(ArtistTop50_Fragment.ARG_PARAM1 , artisttoSend);


        System.out.println("beyrem");
        System.out.println(arguments);
        Fragment fragment = new ArtistTop50_Fragment();

        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction()
                .replace(R.id.music_Container, fragment)
                .commit();


    }

    @Override
    public void onSendUIDFavoriteMusic(Music musicToSend) {

        Bundle arguments = new Bundle();
        arguments.putParcelable(PlaySong_Fragment.ARG_PARAM1 , musicToSend);



        System.out.println(arguments);
        Fragment fragment = new PlaySong_Fragment();
        PlaySong_Fragment f = new PlaySong_Fragment() ;

        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction()
                .replace(contentContainer, fragment)
                .commit();

    }


/*

    @Override
    protected void onStart() {
        super.onStart();

        final Intent intent = new Intent(this, LoginActivity.class);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            // String name = user.getDisplayName();
            String email = user.getEmail();
            //  Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }

        if (user != null) {

            // User is signed in.
        } else {
            startActivity(intent);        }



    }
*/

}
