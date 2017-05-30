package com.esprit.instasinger.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import com.esprit.instasinger.Data.Like;
import com.esprit.instasinger.Data.Videos;
import com.esprit.instasinger.Fragments.Comment_Fragment;
import com.esprit.instasinger.R;
import com.esprit.instasinger.Tab_Bar_Main;

/**
 * Created by bechirkaddech on 11/16/16.
 */

public class RecycleView_videos_home extends RecyclerView.Adapter<RecycleView_videos_home.RecycleView_Holder> {


    Videos video ;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    Context context;
    int ressource;
    ArrayList<Videos> videoList ;
    ArrayList<String> videoListliked;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public RecycleView_videos_home(Context context, int ressource, ArrayList<Videos> videoList,ArrayList<String> videoListliked) {
        this.context = context;
        this.ressource = ressource;
        this.videoList = videoList;
        this.videoListliked = videoListliked;

    }


    @Override
    public RecycleView_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_home, parent, false);
        // set the view's size, margins, paddings and layout parameters

        RecycleView_Holder vh = new RecycleView_Holder(v);
        return vh;



    }

    @Override
    public void onBindViewHolder(final RecycleView_Holder holder, int position) {
        video = videoList.get(position);
        holder.video_player.setVideoURI(Uri.parse(video.getURL()));
        holder.video_player.requestFocus();

        holder.like_btn.setTag(position);
        holder.share_btn.setTag(position);

        if(isliked(video.getComments()))
        {
            holder.like_btn.setImageResource(R.drawable.liked_home_an);
        }
        else
        {
            holder.like_btn.setImageResource(R.drawable.like_home_an);


        }

        System.out.println(video.getSongPicture());

        Picasso.with(context).load(video.getSongPicture()).into(holder.video_picture);
        Picasso.with(context).load(video.getSongPicture()).into(holder.song_picture);

        holder.lv_singer.setText(video.getSinger());
        holder.lv_song.setText(video.getSong());
        holder.song_picture.startAnimation(AnimationUtils.loadAnimation(context,R.anim.animation));
        holder.comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                Comment_Fragment fragment = new Comment_Fragment();
                Bundle args = new Bundle();
                args.putString(Comment_Fragment.ARG_PARAM1, video.getComments());
                fragment.setArguments(args);
                ((Tab_Bar_Main)context).getFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer, fragment).addToBackStack(null)
                        .commit();


            }
        });


        holder.like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x=5000;
                Videos video = videoList.get((Integer) holder.like_btn.getTag());
                Like videoUID = new Like();

                if(isliked(video.getComments())) {
                    System.out.println("dislikedddddd :" + holder.like_btn.getTag());
                    myRef.child("Likes").child(user.getUid()).child(video.getComments()).removeValue();
                    holder.like_btn.setImageResource(R.drawable.like_home_an);
                    for(int i=0; i<videoListliked.size(); i++){
                        if(videoListliked.get(i)== video.getComments())
                        {
                            videoListliked.remove(i);
                            return;
                        }
                    }



                }
                else {

                    System.out.println("likeeeeeeddddd :" + holder.like_btn.getTag());
                    videoUID.setVideoUID(video.getComments());
                    videoListliked.add(video.getComments());
                    myRef.child("Likes").child(user.getUid()).child(video.getComments()).setValue(videoUID);
                    holder.like_btn.setImageResource(R.drawable.liked_home_an);


                }

            }
        });


        holder.comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                Comment_Fragment fragment = new Comment_Fragment();
                Bundle args = new Bundle();
                args.putString(Comment_Fragment.ARG_PARAM1, video.getComments());
                fragment.setArguments(args);
                ((Tab_Bar_Main)context).getFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer, fragment).addToBackStack(null)
                        .commit();


            }
        });




        holder.video_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                System.out.println("wfé");
                holder.video_picture.setVisibility(View.VISIBLE);

                Picasso.with(context).load(video.getSongPicture()).into(holder.song_picture);

            }
        });



/*
        try {
            //holder.btn_like.setTag(position);
            holder.video_player.setImageBitmap(retriveVideoFrameFromVideo(video.getURL()));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }*/

    }




    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class RecycleView_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        VideoView video_player ;
        //  LikeButton btn_like;
        ImageButton like_btn ;
        ImageButton share_btn ;
        ImageButton comment_btn ;
        ImageView video_picture ;
        TextView lv_song ;
        TextView lv_singer ;
        CircleImageView song_picture ;


        public RecycleView_Holder(View itemView) {
            super(itemView);
            context = itemView.getContext();

            video_player = (VideoView) itemView.findViewById(R.id.video_player);
            comment_btn = (ImageButton) itemView.findViewById(R.id.comment_btn);
            like_btn = (ImageButton) itemView.findViewById(R.id.like_btn);
            share_btn = (ImageButton) itemView.findViewById(R.id.share_btn);
            video_picture = (ImageView) itemView.findViewById(R.id.video_picture);
            lv_song = (TextView) itemView.findViewById(R.id.lv_song);
            lv_singer = (TextView) itemView.findViewById(R.id.lv_singer);
            song_picture = (CircleImageView) itemView.findViewById(R.id.song_picture);
            itemView.setOnClickListener(this);

            share_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Videos video = videoList.get((Integer) share_btn.getTag());
                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    myIntent.setType("image/jpeg");
                    String shareBody = video.getURL();
                    String shareSub = "#instasinger #music #insta";
                    myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                    myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                    myIntent.putExtra(Intent.EXTRA_STREAM, video.getSongPicture());

                    context.startActivity(Intent.createChooser(myIntent,"Share Video"));





                }
            });

            itemView.setTag(this);






            //  btn_like = (LikeButton) itemView.findViewById(R.id.btn_like_home);
          /*  btn_like.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    System.out.println("likeeeeeeddddd :" + btn_like.getTag());

                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    System.out.println("dislikedddddd :" + btn_like.getTag());

                }
            });
*/
        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();
            System.out.println(pos);
            System.out.println("im taped");
            RecycleView_Holder holder = (RecycleView_Holder)(view.getTag());
            holder.video_picture.setVisibility(View.INVISIBLE);
            holder.video_player.start();

        }
    }
    public boolean isliked(String s)
    {
        boolean result=true;

        for (String x: videoListliked) {
            System.out.println("soumri");

            System.out.println(x+"______"+s);
            if (x.equals(s))
            {
                result=true;
                break;

            }
            else
            {
                result = false;
            }
        }
        return result;
    }


}
