package com.menafi.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.menafi.domain.Menafi;
import com.menafi.domain.MenafiTutar;
import com.menafi.domain.Person;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sqllite_database";//database adı
    private String TABLE_NAME;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PERSONS_TABLE = "CREATE TABLE " + "PERSONS_TABLE" + "("
                + "id  INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name  TEXT,"
                + "tel_no  TEXT,"
                + "deleted INTEGER,"
                + "recordTimestamp INTEGER"
                + ")";

        String CREATE_MENAFI_TABLE="CREATE TABLE " +"MENAFI"+"("
                +"id  INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"name TEXT,"
                +"recordDateTimestamp INTEGER"
                +")";


        String CREATE_AMOUNT_MENAFI="CREATE TABLE " + "MENAFI_TUTAR  ("
                +"id        	INTEGER NOT NULL,"
                +"menafi_id 	INTEGER NOT NULL,"
                +"person_id 	INTEGER NOT NULL,"
                +"to_paid      	FLOAT,"
                +"to_be_paid	FLOAT,"
                +"PRIMARY KEY(id),"
                +"FOREIGN KEY(person_id)"
                +"REFERENCES PERSONS_TABLE(id),"
                +"FOREIGN KEY(menafi_id)"
                +"REFERENCES MENAFI(id)"
                +")";

        String CREATE_SPENDINGS_TABLE="CREATE TABLE" +"SPENDINGS_TABLE"+"("
                +"id  INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"date TEXT,"
                +"amount_spending DOUBLE"
                +"remaining_amount DOUBLE"
                +")";
        String CREATE_MESSAGE_TO_SEND="CREATE TABLE" +"MESSAGE_TO_SEND"+"("
                +"id  INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"date TEXT,"
                +"message TEXT,"
                +")";

        String CREATE_PEOPLES_TO_SEND="CREATE TABLE" +"PEOPLES_TO_SEND"+"("
                +"id  INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"date TEXT,"
                +"tel_no,"
                +")";
        db.execSQL(CREATE_PERSONS_TABLE);
        db.execSQL(CREATE_MENAFI_TABLE);
        db.execSQL(CREATE_AMOUNT_MENAFI);
//        db.execSQL(CREATE_SPENDINGS_TABLE);
//        db.execSQL(CREATE_MESSAGE_TO_SEND);
//        db.execSQL(CREATE_PEOPLES_TO_SEND);
    }


    public void delete(String tableName,String columnName,int columnValue){ //id si belli olan row u silmek için

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, columnName + " = ?",new String[] { String.valueOf(columnValue) });
        db.close();

    }

    public void insert(ContentValues values,String tableName) {
        //kitapEkle methodu ise adı üstünde Databese veri eklemek için
        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KITAP_ADI, kitap_adi);
//        values.put(KITAP_YAZARI, kitap_yazari);
//        values.put(KITAP_BASIM_YILI, kitap_basim_yili);
//        values.put(KITAP_FIYATI, kitap_fiyat);

        db.insert(tableName, null, values);
        db.close(); //Database Bağlantısını kapattık*/
    }

    public List<Person> getPersons(){
        List<Person> persons=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM PERSONS_TABLE";
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                Person person=new Person();
                person.setId(cursor.getInt(0));
                person.setName(cursor.getString(1));
                person.setTelNo(cursor.getString(2));
//                person.setRecordTimestamp(cursor.getLong(5));

                persons.add(person);

            } while (cursor.moveToNext());
        }
        db.close();

        return persons;
    }


    public List<MenafiTutar> getMenafiTutars(int menafiId ){
        List<MenafiTutar> menafiTutars=new ArrayList<MenafiTutar>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT m.*,p.name,p.tel_no FROM MENAFI_TUTAR m INNER JOIN PERSONS_TABLE p ON p.id=m.person_id WHERE m.menafi_id='"+menafiId+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                MenafiTutar menafiTutar=new MenafiTutar();
                menafiTutar.setId(cursor.getInt(0));
                menafiTutar.setMenafiId(cursor.getInt(1));
                menafiTutar.setPersonId(cursor.getInt(2));
                menafiTutar.setToPaid(cursor.getFloat(3));
                menafiTutar.setToBePaid(cursor.getFloat(4));
                menafiTutar.setName(cursor.getString(5));
                menafiTutar.setTelNo(cursor.getString(6));

                menafiTutars.add(menafiTutar);


            } while (cursor.moveToNext());
        }
        db.close();

        return menafiTutars;
    }


    public List<Menafi> getMenafiler(){
        List<Menafi> menafiler=new ArrayList<Menafi>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM MENAFI";
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                Menafi menafi=new Menafi();
                menafi.setId(cursor.getInt(0));
                menafi.setName(cursor.getString(1));
                menafi.setTimestampRecordDate(2);
//                person.setRecordTimestamp(cursor.getLong(5));

                menafiler.add(menafi);

            } while (cursor.moveToNext());
        }
        db.close();

        return menafiler;
    }




    public void updateRows(String tableName,ContentValues[] values,String[] whereIds) {

        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KITAP_ADI, kitap_adi);
//        values.put(KITAP_FIYATI, kitap_fiyat);

        for(int i=0;i<values.length;i++){
            db.update(tableName,values[i],whereIds[i],null);
        }
        db.close(); //Database Bağlantısını kapattık*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
