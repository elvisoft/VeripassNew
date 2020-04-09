package com.Veripass1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MostrarRegxCategoria extends Activity {
	
	String[] registros;
    //String[] correos;
    Intent intent = new Intent();
    private static ArrayList<Activity> activities=new ArrayList<Activity>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
		setContentView(R.layout.reg_x_categoria);		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.barratitulo1);
		
		TextView titulo = (TextView)findViewById(R.id.textTitulo1);
		titulo.setText("VeriPass. Registros encontrados:");
		Bundle extras = getIntent().getExtras();
	    //en pos tenemos la posicion del registro seleccionado
		int posi = extras.getInt("pos");	
		 int i = 0;
	    Handler_sqlite usdbh = new Handler_sqlite(this,"ClavesxCategoria", null, 1);
        final SQLiteDatabase db = usdbh.getWritableDatabase();
	    
      //Creamos nuestra consulta
        Cursor c = db.rawQuery(" SELECT nombrecuenta, usuarionick, idcateg FROM ClavesxCategoria where idcateg="+posi, null);
        registros = new String[c.getCount()];
        ////Si quisieramos tambien manipular los correos, declarariamos algo asi como esto:
        //correos = new String[c.getCount()];

        // Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            // Recorremos el cursor hasta que no haya mas registros
        	TextView txtMostrar = (TextView) findViewById(R.id.txtMuestraCategoria);
        	txtMostrar.setText("Registros encontrados:");
            do {
                registros[i] = c.getString(0);
                //correos[i] = c.getString(1);
                i++;
            } while (c.moveToNext());
        }
        
        ImageView img = (ImageView) findViewById(R.id.ImgAgregarRegNew);
        img.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               // your code here
            	intent.setClass(MostrarRegxCategoria.this, AltaRegistro.class);
                startActivity(intent);
            }
        });
        
        ImageView imgSalir = (ImageView) findViewById(R.id.imagCerrar);				
    	imgSalir.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub								
				finish();
				intent.setClass(MostrarRegxCategoria.this, MainActivity.class);
                startActivity(intent);																
			}
		});
        
        
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, registros);
        ListView lstOpciones = (ListView) findViewById(R.id.Registros);
        //lstOpciones.setScrollContainer(true);
        lstOpciones.setAdapter(adaptador);
                        	    	    	  
	        //Llevar a otro layout para ver si lo desea modificar o eliminar
	        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	            @Override
	            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	                // Acciones necesarias al hacer click
	                intent.setClass(MostrarRegxCategoria.this, MuestraRegistro.class);
	                Bundle bundle = new Bundle();
	                bundle.putString("NombreCuenta", registros[position]);
	                //bundle.putString("Correo", correos[position]);
	                intent.putExtras(bundle);
	                startActivity(intent);
	            }
	        });
	    
		}		    
	
	
	    
	       
	}

