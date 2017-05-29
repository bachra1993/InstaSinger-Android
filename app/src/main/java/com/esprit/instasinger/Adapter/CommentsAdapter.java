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
import com.esprit.instasinger.Data.Comment;
import com.esprit.instasinger.R;

/**
 * Created by bechirkaddech on 12/7/16.
 */

public class CommentsAdapter extends ArrayAdapter<Comment> {

    Context context;
    int ressource;



    public CommentsAdapter(Context context, int resource, ArrayList<Comment> CommentList) {
        super(context, resource, CommentList);
        this.context=context;
        this.ressource=resource;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=convertView;
        CommentsAdapter_Holder holder=new CommentsAdapter_Holder();
        if (view==null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(ressource,parent,false);

            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_text = (TextView) view.findViewById(R.id.tv_text);
            holder.tv_date = (TextView) view.findViewById(R.id.tv_date);


            holder.profile_picture = (CircleImageView) view.findViewById(R.id.profile_picture);

            view.setTag(holder);
        }
        else {
            holder=(CommentsAdapter_Holder) view.getTag();
        }
        holder.tv_name.setText(getItem(position).getUserPostedName());
        holder.tv_text.setText(getItem(position).getText());
        holder.tv_date.setText(getItem(position).getDate());


        Picasso.with(getContext()).load(Uri.parse(getItem(position).getUserPostedPicture())).into(holder.profile_picture);


        return view;
    }


    class CommentsAdapter_Holder{
        TextView tv_name;
        TextView tv_text;
        TextView tv_date ;
        CircleImageView profile_picture;

    }







}
