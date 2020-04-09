package com.Veripass1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class Handler_sqlite extends SQLiteOpenHelper {
	
	public Handler_sqlite(Context context, String nombre, CursorFactory factory, int version) {
		super(context, nombre, factory, version);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		
		String query="Create Table Usuarios (idusuario integer primary key not null, " +
				"clavemaestra varchar(50)) ";
		String query2 =	"Create Table ClavesxCategoria (idclavecat integer primary key not null, idcateg integer, " +
				"nombrecuenta varchar(25), usuarionick varchar(25), sitioweb varchar(70), comentario text, "+
				"password varchar(120), correoemail varchar(80), strCategoria varchar(25))";
		db.execSQL(query);
		db.execSQL(query2);
	}
	@Override
	public  void onUpgrade(SQLiteDatabase db, int version, int versionueva)
	{
		db.execSQL("Drop Table if exists Usuarios");
		onCreate(db);
	}
		
}
