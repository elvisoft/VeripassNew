package com.Veripass1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class BandejaEntrada extends Activity {
	Intent intent = new Intent();
	int CantRegCorreo;
	int CantRegSitiosW,CantPc, CantTarjet, CantBanca, CantCajF,CantRedS,CantMob,CantOtros;
	private static ArrayList<Activity> activities=new ArrayList<Activity>();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
		setContentView(R.layout.bandeja_entrada_usu);		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.barratitulo1);
		
		ImageView imgSalir = (ImageView) findViewById(R.id.imagCerrar);				
    	imgSalir.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub								
				finish();
				intent.setClass(BandejaEntrada.this, MainActivity.class);
                startActivity(intent);												
			}
		});
		
		ListView lv = (ListView)findViewById(R.id.listView);                
        Handler_sqlite usdbh = new Handler_sqlite(this,"ClavesxCategoria", null, 1);
        final SQLiteDatabase db = usdbh.getWritableDatabase();
        
        Cursor c = db.rawQuery("select idcateg from ClavesxCategoria where idcateg=1", null);
        CantRegCorreo = c.getCount();        
        Cursor c1 = db.rawQuery("select idcateg from ClavesxCategoria where idcateg=2", null);
        CantRegSitiosW = c1.getCount();
        Cursor c2 = db.rawQuery("select idcateg from ClavesxCategoria where idcateg=3", null);
        CantPc = c2.getCount();
        Cursor c3 = db.rawQuery("select idcateg from ClavesxCategoria where idcateg=4", null);
        CantTarjet = c3.getCount();
        Cursor c4 = db.rawQuery("select idcateg from ClavesxCategoria where idcateg=5", null);
        CantBanca = c4.getCount();
        Cursor c5 = db.rawQuery("select idcateg from ClavesxCategoria where idcateg=6", null);
        CantCajF = c5.getCount();
        Cursor c6 = db.rawQuery("select idcateg from ClavesxCategoria where idcateg=7", null);
        CantRedS = c6.getCount();
        Cursor c7 = db.rawQuery("select idcateg from ClavesxCategoria where idcateg=8", null);
        CantMob = c7.getCount();
        Cursor c8 = db.rawQuery("select idcateg from ClavesxCategoria where idcateg=9", null);
        CantOtros = c8.getCount();
        
        int[] listCantReg = new int[] {CantRegCorreo,CantRegSitiosW,CantPc,CantTarjet,CantBanca,CantCajF,CantRedS,CantMob,CantOtros};
        
        ArrayList<ItemCategoria> itemsCategoria = obtenerItems(listCantReg);        
        ItemCategoriaAdapter adapter = new ItemCategoriaAdapter(this, itemsCategoria);        
        lv.setAdapter(adapter); 
                
       /* SQLiteDatabase db = usdbh.getReadableDatabase();
        DatabaseUtils.queryNumEntries(db, "users",
                        "uname=? AND pwd=?", new String[] {CantRegCorreo,CantRegSitiosW});*/        
        db.close();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {            	            	
            	Intent intentCategoria = new Intent(BandejaEntrada.this, MostrarRegxCategoria.class);
            	intentCategoria.putExtra("pos", posicion+1);
                startActivity(intentCategoria);
            	
            }
        });
                                                       
        ImageView img = (ImageView) findViewById(R.id.ImgAgregarRegNew);
        img.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	intent.setClass(BandejaEntrada.this, AltaRegistro.class);
                startActivity(intent);
            }
        });
        
    }    	
	
	
    public ArrayList<ItemCategoria> obtenerItems(int[] listcantreg) {
    	ArrayList<ItemCategoria> items = new ArrayList<ItemCategoria>();
    	
    	items.add(new ItemCategoria(1, "Cuentas de Correo Email", "("+listcantreg[0]+")", "drawable/mail32"));
    	items.add(new ItemCategoria(2, "Sitios Web", "("+listcantreg[1]+")", "drawable/web32"));
    	items.add(new ItemCategoria(3, "PC Escritorio", "("+listcantreg[2]+")", "drawable/windowb32"));
    	items.add(new ItemCategoria(4, "Tarjetas de Credito", "("+listcantreg[3]+")", "drawable/creditcard"));
    	items.add(new ItemCategoria(5, "Cuentas Bancarias", "("+listcantreg[4]+")", "drawable/bank"));
    	items.add(new ItemCategoria(6, "Cajas Fuertes", "("+listcantreg[5]+")", "drawable/cajafuerte"));
    	items.add(new ItemCategoria(7, "Redes Sociales", "("+listcantreg[6]+")", "drawable/facebook"));
    	items.add(new ItemCategoria(8, "Telefonos y Moviles", "("+listcantreg[7]+")", "drawable/pdablue"));
    	items.add(new ItemCategoria(9, "Otras Contrasenias", "("+listcantreg[8]+")", "drawable/folderlocked32"));
    	
    	return items;
    }       
    
    
    
}