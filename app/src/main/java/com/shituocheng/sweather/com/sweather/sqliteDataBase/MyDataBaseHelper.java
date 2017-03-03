package com.shituocheng.sweather.com.sweather.sqliteDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.shituocheng.sweather.com.sweather.bean.CityModel;
import java.util.ArrayList;


/**
 * Created by shituocheng on 16/6/29.
 */
public class MyDataBaseHelper extends SQLiteOpenHelper {


    private static final String CREATE_CITES_TABLE = "CREATE TABLE " + Constant.TABLE_NAME + "("
            + Constant.KEY_ID + " INTEGER PRIMARY KEY, "+ Constant.CITY_NAME + " TEXT, " + Constant.CITY_ID + " TEXT);";

    private final ArrayList<CityModel> mCityModels = new ArrayList<>();

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists" + Constant.TABLE_NAME);
    }

    //向数据库插入一条数据

    public void addData(CityModel cityModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.CITY_NAME,cityModel.getName());
        contentValues.put(Constant.CITY_ID,String.valueOf(cityModel.getCity_id()));

        db.insert(Constant.TABLE_NAME,null,contentValues);
        Log.d("添加成功","成功添加数据");
        db.close();
    }

    //加载数据库数据

    public ArrayList<CityModel> loadData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "select * from" +Constant.TABLE_NAME;
        Cursor cursor = db.query(Constant.TABLE_NAME,new String[]{Constant.KEY_ID,Constant.CITY_NAME,
                Constant.CITY_ID},null,null,null,null," desc");
        if (cursor.moveToFirst()){
            do {
                CityModel cityModel = new CityModel();
                cityModel.setCity_id(Integer.getInteger(cursor.getString(cursor.getColumnIndex(Constant.CITY_ID))));
                cityModel.setName(cursor.getString(cursor.getColumnIndex(Constant.CITY_NAME)));
                mCityModels.add(cityModel);
            }while (cursor.moveToNext());
        }
        Log.d("读取数据","读取数据成功");
        cursor.close();
        db.close();
        return mCityModels;
    }

    //删除一条数据库的数据

    public void deleteData(String cityName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constant.TABLE_NAME,Constant.CITY_NAME+" = ? ", new String[]{cityName});
        Log.d("删除数据","删除数据成功");
        db.close();
    }
}
