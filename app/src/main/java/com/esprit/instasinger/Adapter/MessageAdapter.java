package com.esprit.instasinger.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daasuu.bl.ArrowDirection;
import com.daasuu.bl.BubbleLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import com.esprit.instasinger.Data.Message;
import com.esprit.instasinger.R;

/**
 * Created by bechirkaddech on 12/28/16.
 */
public class MessageAdapter extends ArrayAdapter<Message> {

    Context context;
    int ressource;



    public MessageAdapter(Context context, int resource, ArrayList<Message> MessageList) {
        super(context, resource, MessageList);
        this.context=context;
        this.ressource=resource;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        View view=convertView;
        MessageAdapter_Holder holder=new MessageAdapter_Holder();
        if (view==null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(ressource,parent,false);

            holder.message_text = (TextView) view.findViewById(R.id.message_text);
            holder.chat_view = (BubbleLayout) view.findViewById(R.id.chat_view);

            //  holder.tv_text = (TextView) view.findViewById(R.id.tv_text);

            //   holder.profile_picture = (CircleImageView) view.findViewById(R.id.profile_picture);

            view.setTag(holder);
        }
        else {
            holder=(MessageAdapter_Holder) view.getTag();
        }

        // holder.chat_view.setArrowPosition(ChatMessageView.ArrowPosition.RIGHT);


        // holder.chat_view.setArrowGravity(ChatMessageView.ArrowGravity.END);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)holder.chat_view.getLayoutParams();



        if (getItem(position).getFromId().equals(user.getUid() )){
            holder.chat_view.setArrowDirection(ArrowDirection.RIGHT);

            params.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);




            holder.chat_view.setLayoutParams(params);




            holder.message_text.setText(getItem(position).getText());


        }
        else
        {
            holder.chat_view.setArrowDirection(ArrowDirection.LEFT);
            params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);



            holder.chat_view.setLayoutParams(params);

            holder.message_text.setText(getItem(position).getText());

        }






        // holder.tv_text.setText(getItem(position).getText());

        // Picasso.with(getContext()).load(Uri.parse(getItem(position).getUserPostedPicture())).into(holder.profile_picture);


        return view;
    }


    class MessageAdapter_Holder{
        TextView message_text;
        BubbleLayout chat_view ;



    }







}