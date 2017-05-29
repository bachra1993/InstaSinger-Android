package com.esprit.instasinger.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.esprit.instasinger.Data.Videos;
import com.esprit.instasinger.R;

/**
 * Created by bechirkaddech on 11/16/16.
 */

public class RecycleView_videos extends RecyclerView.Adapter<RecycleView_videos.RecycleView_Holder> {

    Context context;
    int ressource;
    ArrayList<Videos> videoList ;


    public RecycleView_videos(Context context, int ressource, ArrayList<Videos> videoList) {
        this.context = context;
        this.ressource = ressource;
        this.videoList = videoList;
    }

    @Override
    public RecycleView_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        RecycleView_Holder vh = new RecycleView_Holder(view);

        return vh;


    }

    VideoView a ;


    @Override
    public void onBindViewHolder(final RecycleView_Holder holder, int position) {
        final Videos video = videoList.get(position);

        System.out.println("madrid");
        System.out.println(video.getSinger());
        System.out.println(video.getSong());
        System.out.println(video.getSongPicture());
        a = holder.video_player ;


        holder.video_player.setVideoURI(Uri.parse(video.getURL()));
            if (holder.video_player.isPlaying() == false ){
                Picasso.with(context).load(video.getSongPicture()).into(holder.video_picture);
            }

        holder.video_player.requestFocus();
        holder.song.setText(video.getSinger());
        holder.video_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                holder.video_picture.setVisibility(View.VISIBLE);

                Picasso.with(context).load(video.getSongPicture()).into(holder.video_picture);
                System.out.println("wfé");
            }
        });




    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }



    class RecycleView_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_nom;
        VideoView video_player ;
        ImageView video_picture ;
        TextView song ;
        public RecycleView_Holder(View itemView) {
            super(itemView);

            video_picture = (ImageView) itemView.findViewById(R.id.video_picture);
            song =(TextView) itemView.findViewById(R.id.song);
            itemView.setOnClickListener(this);

            video_player = (VideoView) itemView.findViewById(R.id.video_player);





            itemView.setTag(this);

        }

        @Override
        public void onClick(View view) {
            System.out.println("i'm taped");
           // holder.video_player.start();
            System.out.println("recycle position");
                    int pos = getAdapterPosition() ;
            System.out.println(pos);
            RecycleView_Holder holder = (RecycleView_Holder )(view.getTag());
            holder.video_picture.setVisibility(View.INVISIBLE);
            holder.video_player.start();

        }
    }








}
