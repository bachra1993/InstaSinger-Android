package com.esprit.instasinger.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.esprit.instasinger.Data.FavoriteMusic;
import com.esprit.instasinger.R;

/**
 * Created by bechirkaddech on 11/21/16.
 */

public class MusicFavoriteAdapter extends ArrayAdapter<FavoriteMusic> {


    Context context;
    int ressource;



    public MusicFavoriteAdapter(Context context, int resource, List<FavoriteMusic> music) {
        super(context, resource, music);
        this.context=context;
        this.ressource=resource;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=convertView;
        MusicAdapter_Holder holder=new MusicAdapter_Holder();
        if (view==null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(ressource,parent,false);

            holder.tv_nom = (TextView) view.findViewById(R.id.tv_name);
            holder.profile_picture = (ImageView) view.findViewById(R.id.profile_picture);
            holder.singer = (TextView) view.findViewById(R.id.singer);

            view.setTag(holder);
        }
        else {
            holder=(MusicAdapter_Holder) view.getTag();
        }
        holder.tv_nom.setText(getItem(position).getSong());
        holder.singer.setText(getItem(position).getSinger());
       // System.out.println(getItem(position).getProfilePictureURL());
        Picasso.with(getContext()).load(Uri.parse(getItem(position).getSingerPicture())).into(holder.profile_picture);


        return view;
    }


    class MusicAdapter_Holder{
        TextView tv_nom;
        ImageView profile_picture;
        TextView singer ;

    }






}
