package com.ayudameperdi;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper{
	
	private SQLiteDatabase base;
	private String name;
	private int version = 0;
	private static final String TBLNAME = "tblContactos";
	
	public DataBase(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.name = name;
		this.version = version;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE "+TBLNAME+" (nombre TEXT, telefono TEXT, PRIMARY KEY (telefono))";	//Creo una tabla
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXIST "+TBLNAME);						//Borro la tabla si existe
		db.execSQL("CREATE TABLE "+TBLNAME+" (nombre TEXT, telefono TEXT, PRIMARY KEY (telefono))");	//La vuelvo a crear
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getVersion(){
		return this.version;
	}
	
	public void insertInto(ContentValues cv){
		base = this.getWritableDatabase();
		base.insert(TBLNAME, null, cv);
	}
	
	public void update(String condition, ContentValues cv){
		base = this.getWritableDatabase();
		base.update(TBLNAME, cv, condition, null);
	}
	
	public void delete(String condition){
		base = this.getWritableDatabase();
		base.delete(TBLNAME, condition, null);
	}

	public ArrayList<Contact> getContacts(){
		
		ArrayList<Contact> contactos = new ArrayList<Contact>();
		
		String sql = "select * from " + TBLNAME;
		
		base = this.getReadableDatabase();
		
		Cursor cursor = base.rawQuery(sql, null);	
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact(cursor.getString(0),cursor.getString(1));
		        // Adding contact to list
		        contactos.add(contact);
		    } while (cursor.moveToNext());
		}
		
		// return contact list
		return contactos;
	}
	
	public boolean userExists(String key){
		base = this.getReadableDatabase();
		String sql = "select telefono from " + TBLNAME + " where telefono='"+key+"'";
		Cursor cursor = base.rawQuery(sql, null);
		
        if(cursor.getCount() == 1){
        	return true;
        } else {
        	return false;
        }
	}
	
	public int userCount(){
		base = this.getReadableDatabase();
		String sql = "select telefono from " + TBLNAME	;
		Cursor cursor = base.rawQuery(sql, null);
		
        return cursor.getCount();
	}
}
