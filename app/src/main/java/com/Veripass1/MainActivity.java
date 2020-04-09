package com.Veripass1;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.View;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.app.AlertDialog;

public class MainActivity extends Activity {
	

	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
		setContentView(R.layout.activity_main);		
		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.barratitulo1);
		
        // Hacemos la conexion a la bd
        Handler_sqlite usdbh = new Handler_sqlite(this,"DBUsuarios", null, 1);
        final SQLiteDatabase db = usdbh.getWritableDatabase();

        //Creamos nuestra consulta
        final Cursor c = db.rawQuery(" SELECT idusuario, clavemaestra FROM Usuarios", null);        
        ////Si quisieramos tambien manipular los correos, declarariamos algo asi como esto:
        //correos = new String[c.getCount()];

        // Preguntamos si no existe un registro de Usuario
        if (!c.moveToFirst()) {
         	    
        	lanzarUsuarioNew();
            
        }
        else{ 
        	String url = "http://www.webgratis.com.ar";
        	TextView textEnlace = (TextView)findViewById(R.id.textVLink);

        	textEnlace.setText(Html.fromHtml("<a href=" + url + ">Desarrollado por Elvis M.</a>"));
        	textEnlace.setMovementMethod(LinkMovementMethod.getInstance());
        	        	
        	
        	Button butonSalir = (Button)findViewById(R.id.btnSalir);
        	butonSalir.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
        	
        	Button butonAyuda = (Button)findViewById(R.id.btnAyuda);
        	butonAyuda.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					lanzarayuda();
				}
			});
        	
        	final ToggleButton btntypepass = (ToggleButton)findViewById(R.id.tglButonPass);
        	final EditText Pass = (EditText) findViewById(R.id.edTextingClaveUsureg);                
             
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
        	
        	
        	Button boton = (Button) findViewById(R.id.btnIngresar);
    		boton.setOnClickListener(new OnClickListener() {
    			
                @Override
                public void onClick(View v) {
                	                	                	
                	String txtPass = Pass.getText().toString();
                	String passDesencript="";
                	
                	if (db!=null && txtPass.trim().length()!=0)
                	{            		            	                   
                		String passbd = c.getString(1); 
                		
                		try {
                			passDesencript = CodificadorAES.desencriptar(passbd);
                		} catch (Exception e1) {
                			// TODO Auto-generated catch block
                			e1.printStackTrace();
                		} 
                		                                        	
						if (passDesencript.equalsIgnoreCase(txtPass))
                		{                			                			                                                                                                                                                                       
                                  mensajeylanzar();                                                                                                        
                		}
                		else{                			
                			
                			new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Atencion!!")
                            .setMessage("Su contraseña no es correcta. Porfavor intente de nuevo!!!")
                            .setPositiveButton("OK",null)
                            .show();                			
                		}
                                        		
                	}
                	else{        
                		
                		new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Atencion!!")
                        .setMessage("La contraseña es demasiado corta.")
                        .setPositiveButton("OK",null)
                        .show();
                		                		              		                		
                	}
                	
                }
    		});
        	
        }
        
        
		
	}
	
	public void mensajeylanzar()
	{
		AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);  
        dialogo1.setTitle("Bienvenido!!");  
        dialogo1.setMessage("Su contraseña es correcta. Recuerde al salir cerrar su sesion con el boton del candadito que se encuentra arriba en el tope de su pantalla. Esto para mayor seguridad de sus datos.");
        dialogo1.setCancelable(false);  
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialogo1, int id) {  
            	lanzarBandeja_entrada();
            }  
        });
        dialogo1.show(); 
		
	}
	
	public void lanzarayuda()
	{
		Intent inew1 = new Intent(this, Ayuda.class);
		startActivity(inew1);
	}
	
	public void lanzarBandeja_entrada()
	{
		Intent i = new Intent(this, BandejaEntrada.class);
		startActivity(i);
	}
	public void lanzarUsuarioNew()
	{
		Intent i = new Intent(this, UsuarioNuevo.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}			

}
