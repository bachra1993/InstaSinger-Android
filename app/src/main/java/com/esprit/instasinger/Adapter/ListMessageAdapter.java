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

import static com.esprit.instasinger.R.id.profile_picture_message;

/**
 * Created by bechirkaddech on 12/29/16.
 */

public class ListMessageAdapter extends ArrayAdapter<User> {
    Context context;
    int ressource;


    public ListMessageAdapter(Context context, int resource, ArrayList<User> UserList) {
        super(context, resource, UserList);
        this.context = context;
        this.ressource = resource;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ListMessageAdapter_Holder holder = new ListMessageAdapter_Holder();
        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(ressource, parent, false);

            holder.tv_message_name = (TextView) view.findViewById(R.id.tv_message_name);
            holder.profile_picture_message = (CircleImageView) view.findViewById(profile_picture_message);

            view.setTag(holder);
        } else {
            holder = (ListMessageAdapter_Holder) view.getTag();
        }
        holder.tv_message_name.setText(getItem(position).getFullname());
        System.out.println(getItem(position).getProfilePictureURL());
        Picasso.with(getContext()).load(Uri.parse(getItem(position).getProfilePictureURL())).into(holder.profile_picture_message);


        return view;
    }


    class ListMessageAdapter_Holder {
        TextView tv_message_name;
        CircleImageView profile_picture_message;

    }
}


