package com.Veripass1;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MuestraRegistro extends Activity {
	String[] registros;
	String password;
	Intent intent = new Intent();
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
        setContentView(R.layout.muestra_registro);		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.barratitulover_reg);
                               
        Bundle extras = getIntent().getExtras();
	    //en pos tenemos la posicion del registro seleccionado
		final String nombCuenta = extras.getString("NombreCuenta");	
		 
	    Handler_sqlite usdbh = new Handler_sqlite(this,"ClavesxCategoria", null, 1);
        final SQLiteDatabase db = usdbh.getWritableDatabase();
        final String[] args = new String[] {nombCuenta};
        
        //Creamos nuestra consulta
        Cursor c = db.rawQuery(" SELECT nombrecuenta, password, usuarionick, correoemail, sitioweb, comentario, strCategoria FROM ClavesxCategoria where nombrecuenta=?", args);
        registros = new String[c.getCount()];
        ////Si quisieramos tambien manipular los correos, declararamos algo asi como esto:
        //correos = new String[c.getCount()];

        // Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            // Recorremos el cursor hasta que no haya mas registros
        	TextView txtNombreCuenta = (TextView) findViewById(R.id.textNombreCuenta);
        	txtNombreCuenta.setText(c.getString(0));        	        
        	
        	try {
				password = CodificadorAES.desencriptar(c.getString(1)) ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	TextView txtPass = (TextView) findViewById(R.id.textPassword);
        	txtPass.setText(password);
        	TextView txtNick = (TextView) findViewById(R.id.textVNick);
        	txtNick.setText(c.getString(2));
        	TextView txtcorreo = (TextView) findViewById(R.id.textVEmail);
        	txtcorreo.setText(c.getString(3));
        	TextView txtSitio = (TextView) findViewById(R.id.textVSitioWeb);
        	txtSitio.setText(c.getString(4));
        	TextView txtComentario = (TextView) findViewById(R.id.textVComentarios);
        	txtComentario.setText(c.getString(5));
        	            
        }

        ImageView img = (ImageView) findViewById(R.id.ImgEditarRegNew);
        img.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	intent.setClass(MuestraRegistro.this, ModificarRegistro.class);
            	Bundle bundle = new Bundle();
                bundle.putString("NombreCuenta", nombCuenta);
                //bundle.putString("Correo", correos[position]);
                intent.putExtras(bundle);                
                startActivity(intent);
            }
        });
        
        final AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        ImageView img2 = (ImageView) findViewById(R.id.ImgEliminarRegNew);
        img2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {            	
                
            	
                dialogo1.setTitle("Importante");  
                dialogo1.setMessage("Desea eliminar este registro de contrasenia ?");
                dialogo1.setCancelable(false);  
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialogo1, int id) {  
                                            	 
                         db.execSQL("DELETE FROM ClavesxCategoria WHERE nombrecuenta=?",args);
                         db.close();

                         Toast.makeText(getBaseContext(),"Se ha eliminado correctamente el registro: '"+ nombCuenta + "'", Toast.LENGTH_LONG).show();
                         intent.setClass(MuestraRegistro.this,BandejaEntrada.class);
                         startActivity(intent);
                    	
                    }  
                });  
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialogo1, int id) {  
                       
                    }  
                });            
                dialogo1.show();
                
            }
        });        
        
	}		
    
    

}
