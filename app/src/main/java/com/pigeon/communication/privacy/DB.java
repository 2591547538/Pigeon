package com.pigeon.communication.privacy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DB extends SQLiteOpenHelper {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AGA = "aga";
    public static final String AGX = "agx";
    public static final String AGY = "agy";
    public static final String AGZ = "agz";
    public static final String AGF = "agf";
    public static final String AGM = "agm";
    public static final String AGN = "agn";
    public static final String UP = "up";
    public static final String MSGA  =  "msga";
    public static final String MSGB  =  "msgb";
    public static final String MOO  =  "moo";
    public DB(Context context) {
        super(context,"aAxxrerhrrei2.db", null, 1
        );
    }

    public static void Creatgroupname(SQLiteDatabase db,String s){
        String dbcqreate="create table if not exists "+s+"("+ ID+" integer primary key autoincrement,"+ NAME+" varchar(40)," +AGM+" integer)";
        db.execSQL(dbcqreate);
    }

    public static void Createdb(SQLiteDatabase db,String s){
        String dbcqreate="create table if not exists "+s+"("+ ID+" integer primary key autoincrement,"+ NAME+" varchar(40)," +AGA+" varchar(40),"+AGX+" varchar(2000),"+ AGY+" varchar(640),"+AGZ+" varchar(140),"+ AGF+" integer)";
    db.execSQL(dbcqreate);
}
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table "+"user"+"("+ ID+" integer primary key autoincrement,"+ NAME+" varchar(40)," +AGA+" varchar(40),"+AGX+" varchar(400),"+ AGY+" varchar(640),"+AGZ+" varchar(140),"+ AGF+" varchar(140),"+ AGM+" varchar(140),"+ AGN+"  integer)";
        db.execSQL(sql);
        String sqll = "create table "+"mast"+"("+ ID+" integer primary key autoincrement,"+ NAME+" varchar(40)," +AGA+" varchar(40),"+AGX+" varchar(400),"+ AGY+" varchar(640),"+AGZ+" varchar(140),"+ AGF+" varchar(140),"+ AGM+" varchar(140),"+ AGN+" varchar(140),"+ UP+" varchar(140),"+MSGA+" varchar(400),"+MSGB+" varchar(400),"+ MOO+" integer)";
        db.execSQL(sqll);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
