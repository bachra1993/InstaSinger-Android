package com.esprit.instasinger.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import com.esprit.instasinger.Data.User;
import com.esprit.instasinger.R;

import static com.esprit.instasinger.R.id.profile_picture;

/**
 * Created by bechirkaddech on 11/19/16.
 */

public class UserAdapter extends ArrayAdapter<User> {
    Context context;
    int ressource;



    public UserAdapter(Context context, int resource, ArrayList<User> UserList) {
        super(context, resource, UserList);
        this.context=context;
        this.ressource=resource;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=convertView;
        UserAdapter_Holder holder=new UserAdapter_Holder();
        if (view==null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(ressource,parent,false);

            holder.tv_nom = (TextView) view.findViewById(R.id.tv_name);
            holder.profile_picture = (CircleImageView) view.findViewById(profile_picture);

            view.setTag(holder);
        }
        else {
            holder=(UserAdapter_Holder) view.getTag();
        }
        holder.tv_nom.setText(getItem(position).getFullname());
        System.out.println(getItem(position).getProfilePictureURL());
        Picasso.with(getContext()).load(Uri.parse(getItem(position).getProfilePictureURL())).into(holder.profile_picture);


        return view;
    }


    class UserAdapter_Holder{
        TextView tv_nom;
        CircleImageView profile_picture;

    }







}
