package com.example.drummond.voteapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Drummond on 2015/10/13.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_ACCOUNT = "create table Account ("+ "id integer primary key autoincrement, "
    + "account text, "
    + "password text, "
    + "Email text, "
    + "safetycode text)";

    public static final String CREATE_VOTE = "create table Vote ("+ "id integer primary key autoincrement, "
            + "account text, "
            + "voteNum integer, "
            + "title text, "
            + "item1 text, "
            + "item1_amount integer, "
            + "item2 text, "
            + "item2_amount integer, "
            + "item3 text, "
            + "item3_amount integer, "
            + "item4 text, "
            + "item4_amount integer, "
            + "item5 text, "
            + "item5_amount integer, "
            + "item6 text, "
            + "item6_amount integer, "
            + "item7 text, "
            + "item7_amount integer, "
            + "item8 text, "
            + "item8_amount integer, "
            + "item9 text, "
            + "item9_amount integer, "
            + "item10 text, "
            + "item10_amount integer, "
            + "amount integer)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_ACCOUNT);
        db.execSQL(CREATE_VOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("drop table if exists Account");
        db.execSQL("drop table if exists Vote");
        onCreate(db);
    }

}
