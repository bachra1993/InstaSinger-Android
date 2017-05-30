package com.esprit.instasinger.Sqlight;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bechir on 12/10/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "MusicManager";

    // Contacts table name
    public static final String TABLE_MUSIC = "Song";

    // Restaurent Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_SONG = "song";
    public static final String KEY_SINGER = "singer";
    public static final String KEY_PICTURE = "picture";
    public static final String KEY_PREVIEW = "preview";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //requete de creation de basedonne
        String CREATE_RESTAURENTS_TABLE="CREATE TABLE "+ TABLE_MUSIC +" ( "
                + KEY_ID + " INTEGER PRIMARY KEY , "+KEY_SONG+" TEXT, "+KEY_SINGER+" TEXT,"
                + KEY_PICTURE+ " TEXT," +KEY_PREVIEW+" TEXT"+" )";
        db.execSQL(CREATE_RESTAURENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUSIC);
        onCreate(db);
    }

}
