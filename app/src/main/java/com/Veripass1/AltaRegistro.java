package com.Veripass1;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemSelectedListener;

public class AltaRegistro extends Activity {
	  Spinner listacategoria;
	  Intent intent = new Intent();
	  private static ArrayList<Activity> activities=new ArrayList<Activity>();
	  @Override
	    protected void onCreate(Bundle savedInstanceState) 
	    {
	        super.onCreate(savedInstanceState);
	        
	        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
	        setContentView(R.layout.altaregistro);		
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.barratitulonewreg);	        	        	        	        
	        
			
			listacategoria = (Spinner) findViewById(R.id.spinner1);
			listacategoria.setOnItemSelectedListener(new OnItemSelectedListener() {
		        	
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						
						//Toast.makeText(SpinnerProjectActivity.this, ListaOpciones.getSelectedItem().toString(), Toast.LENGTH_LONG).show();						
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						}				
		        });
			
			final ToggleButton btntypepass = (ToggleButton)findViewById(R.id.tglBtnPassClaves);
        	final EditText Pass = (EditText) findViewById(R.id.TxtPass);                
             
        	btntypepass.setOnClickListener(new View.OnClickListener() {
        	    @SuppressLint("NewApi")
				public void onClick(View arg0)
        	    {
        	        if(btntypepass.isChecked())
        	        {  	Pass.setInputType(InputType.TYPE_CLASS_TEXT); }

        	        
        	        else
        	        	{Pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);}
        	    }
        	});
			
			
	       // listacategoria = (Spinner) findViewById(R.id.spinner1);
	        //String []opciones={"Cuentas de Correo Email","Sitios Web","PC Escritorio","Tarjetas de Credito","Cuentas Bancarias", "Cajas Fuertes","Redes Sociales","Telefonos y Moviles","Otras Contrasenias"};
	        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones);
	        //listacategoria.setAdapter(adapter);    
	        
	        Handler_sqlite usdbh = new Handler_sqlite(this,"ClavesxCategoria", null, 1);
	        final SQLiteDatabase db = usdbh.getWritableDatabase();	        	        
	        
	        ImageView img = (ImageView) findViewById(R.id.ImgGuardarRegNew);
	        img.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	               // your code here
	            	        	       
	                if (db != null) {	                   
	                	String selecCateg=listacategoria.getSelectedItem().toString();
	                	int posi = listacategoria.getSelectedItemPosition();
	                    EditText nombreCuenta = (EditText) findViewById(R.id.TxtNombCuenta);
	                    EditText password = (EditText) findViewById(R.id.TxtPass);	                    
	                    EditText nick = (EditText) findViewById(R.id.TxtNick);
	                    EditText correo = (EditText) findViewById(R.id.TxtCorreo);
	                    EditText sitioweb = (EditText) findViewById(R.id.TxtWeb);	                    
	                    EditText comentario = (EditText) findViewById(R.id.TxtComentario);	                    
	                    
	                    String nombcuent = nombreCuenta.getText().toString();
	                    String passstr = password.getText().toString();	                    	                    
	                    String nickstr =  nick.getText().toString();
	                    
	                    if (nombcuent.trim().length()!=0 && passstr.trim().length()!=0 && nickstr.trim().length()!=0 && selecCateg.trim().length()!=0)
	                    {
	                    ContentValues nuevoRegistro = new ContentValues();
	                    nuevoRegistro.put("strCategoria", selecCateg);
	                    nuevoRegistro.put("idcateg", posi+1);
	                    nuevoRegistro.put("nombrecuenta", nombreCuenta.getText().toString());
	                    	                    	                    
	                    try {
							nuevoRegistro.put("password", CodificadorAES.encriptar(passstr));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
	                    nuevoRegistro.put("usuarionick", nick.getText().toString());
	                    nuevoRegistro.put("correoemail",correo.getText().toString());
	                    nuevoRegistro.put("sitioweb", sitioweb.getText().toString());
	                    nuevoRegistro.put("comentario", comentario.getText().toString());

	                    db.insert("ClavesxCategoria", null, nuevoRegistro);	                    
	                    db.close();	                    
	                    Toast.makeText(getBaseContext(), "Se ha dado de alta correctamente la cuenta: '" + nombreCuenta.getText().toString() + "'", Toast.LENGTH_LONG).show();

	                    intent.setClass(AltaRegistro.this, BandejaEntrada.class);
	                    startActivity(intent);
	                    }
	                    else{
	                    	Toast.makeText(getBaseContext(), "Atencion: Datos incompletos!! ", Toast.LENGTH_LONG).show();
	                    }
	                }
	            	            	
	            }
	        });
	        	        	        
	    }
	  	
	
}
