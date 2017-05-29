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

import com.esprit.instasinger.Data.Artist;
import com.esprit.instasinger.R;

/**
 * Created by bechirkaddech on 11/28/16.
 */

public class ArtistAdapter extends ArrayAdapter<Artist> {


    Context context;
    int ressource;



    public ArtistAdapter(Context context, int resource, List<Artist> artist) {
        super(context, resource, artist);
        this.context=context;
        this.ressource=resource;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=convertView;
        ArtistAdapter_Holder holder=new ArtistAdapter_Holder();
        if (view==null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(ressource,parent,false);

            holder.tv_nom = (TextView) view.findViewById(R.id.tv_name);
            holder.profile_picture = (ImageView) view.findViewById(R.id.profile_picture);

            view.setTag(holder);
        }
        else {
            holder=(ArtistAdapter_Holder) view.getTag();
        }
        holder.tv_nom.setText(getItem(position).getName());
        // System.out.println(getItem(position).getProfilePictureURL());
        Picasso.with(getContext()).load(Uri.parse(getItem(position).getArtist_picture())).into(holder.profile_picture);


        return view;
    }


    class ArtistAdapter_Holder{
        TextView tv_nom;
        ImageView profile_picture;


    }






}

