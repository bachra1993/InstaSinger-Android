package com.esprit.instasinger.Sqlight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.esprit.instasinger.Data.Music;

/**
 * Created by bechirkaddech on 10/25/16.
 */
public class MusicDao extends DatabaseHandler {

    public MusicDao(Context context) {
        super(context);
    }

    public void addMusic(Music favoriteMusic) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_SONG, favoriteMusic.getSong());
        values.put(DatabaseHandler.KEY_SINGER, favoriteMusic.getSinger());

        values.put(DatabaseHandler.KEY_PICTURE, favoriteMusic.getSingerPicture());
        values.put(DatabaseHandler.KEY_PREVIEW, favoriteMusic.getPreview());






        //inserting row
        db.insert(DatabaseHandler.TABLE_MUSIC,null,values);
        db.close();
    }


    public Music getMusic(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHandler.TABLE_MUSIC,new String[]{
                        DatabaseHandler.KEY_ID, DatabaseHandler.KEY_SONG,DatabaseHandler.KEY_SINGER,DatabaseHandler.KEY_PICTURE,
                DatabaseHandler.KEY_PREVIEW
                },KEY_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        Music  musicF = new Music(Integer.parseInt(
                cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)




        );
        return musicF;
    }





    // Getting All Restaurent
    public List<Music> getAllRMusic() {


        List<Music> musicList = new ArrayList<Music>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_MUSIC;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Music music = new Music();
                music.setId(Integer.parseInt(cursor.getString(0)));
                music.setSong(cursor.getString(1));
                music.setSinger(cursor.getString(2));
                music.setSingerPicture(cursor.getString(3));
                music.setPreview(cursor.getString(4));



                // Adding contact to list
                musicList.add(music);
            } while (cursor.moveToNext());
        }

        // return contact list
        return musicList;
    }









    public int getMusicCount() {
        String countQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_MUSIC;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }




    public void deleteMusic(Music music) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseHandler.TABLE_MUSIC, KEY_ID + " = ?", new String[] { String.valueOf(music.getId()) });
        db.close();
    }







}
