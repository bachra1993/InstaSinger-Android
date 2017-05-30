package com.esprit.instasinger.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

import com.esprit.instasinger.Data.Videos;

/**
 * Created by bechirkaddech on 11/27/16.
 */

public class ThumbAdapter  extends ArrayAdapter<Videos> {

    Context context;
    int ressource;
    ThumbAdapter_Holder holder=new ThumbAdapter_Holder();




    public ThumbAdapter(Context context, int resource, ArrayList<Videos> videoList) {
        super(context, resource, videoList);
        this.context=context;
        this.ressource=resource;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=convertView;
        if (view==null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(ressource,parent,false);

           // holder.video_thum = (ImageView) view.findViewById(R.id.video_thum);

            view.setTag(holder);
        }
        else {
            holder=(ThumbAdapter_Holder) view.getTag();
        }


        System.out.println("test url");
        System.out.println(getItem(position).getURL());



        BitMapTask task = (BitMapTask) new BitMapTask(getItem(position).getURL())
                .setBitMapLoaded(new BitMapTask.OnBitmapLoaded() {
                    @Override
                    public void loadBitmap(Bitmap bitmap) {
                        if(bitmap != null){
                            holder.video_thum.setImageBitmap(bitmap);
                        }
                    }
                }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        return view;
    }


class ThumbAdapter_Holder{
   ImageView video_thum ;

}


    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable
    {



        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        }
        finally
        {
            if (mediaMetadataRetriever != null)
            {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    public static class BitMapTask extends AsyncTask<Void, Void, Bitmap> {

        public interface OnBitmapLoaded{
            void loadBitmap(Bitmap bitmap);
        }

        private OnBitmapLoaded bitmapLoaded;
        private String url;

        public BitMapTask(String url){
            this.url = url;
            System.out.println("ok lets go ");
            System.out.println(url);
        }

        public BitMapTask setBitMapLoaded(OnBitmapLoaded bitMapLoaded){
            this.bitmapLoaded = bitMapLoaded;
            return this;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bb = null;
            try {
                 bb =  retriveVideoFrameFromVideo(url);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return  bb ;

        }

        @Override
        protected void onPostExecute(Bitmap result){
            if(bitmapLoaded != null) bitmapLoaded.loadBitmap(result);
        }
    }








}

