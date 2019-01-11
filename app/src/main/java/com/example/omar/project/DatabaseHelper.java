package com.example.omar.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static  final String TABLE_NAME ="incident_table";
    private static  final String TAG ="DatabaseHelper";
    private static  final String COL1 ="ID";
    private static  final String COL2 ="type";
    private static  final String COL3 ="detail";
    private static  final String COL4 ="Description";
    private static  final String COL5 ="Long";
    private static  final String COL6 ="Lat";
    private static  final String COL7 ="image";
public DatabaseHelper(Context context){

    super(context,TABLE_NAME, null,1);
}
    @Override
    public void onCreate(SQLiteDatabase db) {
    // String createTable="create table " + TABLE_NAME + " (ID integer PRIMARY KEY AUTOINCREMENT,  "+COL2+" TEXT" +COL2+ "text "+COL3+" text "+COL4+" text )";
        String createTable="create table incident_table ( ID integer PRIMARY KEY AUTOINCREMENT, type TEXT , detail TEXT, Description TEXT, Long DOUBLE, Lat  DOUBLE, image TEXT)";
     db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE  "+TABLE_NAME  );
    onCreate(db);

    }
    public String BitmapToString(Bitmap img){
        ByteArrayOutputStream bb=new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG,100,bb);
       byte[] b=bb.toByteArray();
       String photo=Base64.encodeToString(b,Base64.DEFAULT);
       return photo;

    }
    public Bitmap stringToBitmap(String img){
    try{
        byte[] b=Base64.decode(img,Base64.DEFAULT);
        Bitmap bitmap=BitmapFactory.decodeByteArray(b,0,b.length);
        return bitmap;
    }
    catch (Exception esp){
        Log.d(TAG,"DatabaseHelper: stringToBitmap : "+ esp.getMessage());
        return null;
    }
    }
    public Cursor getData(){
    SQLiteDatabase db=this.getWritableDatabase();
    if(db.isOpen()){
        Cursor data= db.rawQuery("select * from "+TABLE_NAME, null);

        return data;
    }
    else {
        Log.d(TAG,"DatabaseHelper: getData : errror");
        return null;
    }
    //Cursor data= db.rawQuery("select * from "+TABLE_NAME, null);



    }
    public Cursor getDataFilter(String detail,String type){
    SQLiteDatabase db=this.getWritableDatabase();
    String query="select * from incident_table where detail="+detail+" and type="+type;
    Cursor data=db.rawQuery(query,null);
    return data;
    }

    public boolean addData(String type, String detail, String Description, Double longitude, Double latitude, String img,long date) {
    SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
         contentValues.put(COL2,type);
        Log.d(TAG, "addData: Adding: "+ type+ "to "+ TABLE_NAME);
         contentValues.put(COL3,detail);
        Log.d(TAG, "addData: Adding: "+ detail+ "to "+ TABLE_NAME);
         contentValues.put(COL4,Description);
        Log.d(TAG, "addData: Adding: "+ Description+ "to "+ TABLE_NAME);
        contentValues.put(COL5,longitude);
        Log.d(TAG, "addData: Adding: "+ longitude+ "to "+ TABLE_NAME);
        contentValues.put(COL6,latitude);
        Log.d(TAG, "addData: Adding: "+ latitude+ "to "+ TABLE_NAME);
        contentValues.put(COL7,img);
        Log.d(TAG, "addData: Adding: "+ " photo "+ "to "+ TABLE_NAME);
        long result=db.insert(TABLE_NAME,null, contentValues);
        if(result==-1){
            return false;
        }
        else{
            return true;
        }

    }
}

