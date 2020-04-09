package com.Veripass1;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UsuarioNuevo extends Activity {
	
	Intent intent = new Intent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg_nuevo_usu);
		
		Handler_sqlite usdbh = new Handler_sqlite(this, "DBUsuarios", null, 1);
		final SQLiteDatabase db = usdbh.getWritableDatabase();
		
		Cursor c = db.rawQuery(" SELECT idusuario, clavemaestra FROM Usuarios", null);
		
		if (c.moveToFirst()) {
			intent.setClass(UsuarioNuevo.this, MainActivity.class);
            startActivity(intent);
            db.close();
		}
		
		String url = "http://www.webgratis.com.ar";
    	TextView textEnlace = (TextView)findViewById(R.id.textVlinkweb);

    	textEnlace.setText(Html.fromHtml("<a href=" + url + ">Desarrollado por Elvis M.</a>"));
    	    	    	
    	textEnlace.setMovementMethod(LinkMovementMethod.getInstance());
		
		Button boton = (Button) findViewById(R.id.btnGuardar);
		boton.setOnClickListener(new OnClickListener() {
	        
            @Override
            public void onClick(View v) {
            	
            	EditText Pass = (EditText) findViewById(R.id.editTxtPassword);
                EditText RepitePass = (EditText) findViewById(R.id.editTxtRepitaPass);
                String textPass = Pass.getText().toString();
                String RepitetxtPass = RepitePass.getText().toString();
                
            	if (db!=null && textPass.equalsIgnoreCase(RepitetxtPass) && textPass.trim().length()!=0
            			&& RepitetxtPass.trim().length()!=0)
            	{            		            	                   

                    ContentValues nuevoRegistro = new ContentValues();
                    
                    try {
						nuevoRegistro.put("clavemaestra", CodificadorAES.encriptar(textPass));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
                                        
                    //nuevoRegistro.put("email", RepitePass.getText().toString());                    

                    db.insert("Usuarios", null, nuevoRegistro);                    
                    db.close();                                        
                    
                   mensajeylanzar();
            		
            	}
            	else{            		
            		
            		new AlertDialog.Builder(UsuarioNuevo.this)
                    .setTitle("Atencion!!")
                    .setMessage("Porfavor ingrese correctamente la contraseña!!!")
                    .setPositiveButton("Aceptar",null)
                    .show();
            	}
            	
            }
		});
	}
	
	
	public void mensajeylanzar()
	{
		AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);  
        dialogo1.setTitle("Bienvenido!!");  
        dialogo1.setMessage("Su contraseña se ha guardado correctamente. Recuerde al salir cerrar su sesion con el boton candadito que se encuentra arriba en el tope de su pantalla.");
        dialogo1.setCancelable(false);  
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialogo1, int id) {  
            	intent.setClass(UsuarioNuevo.this, BandejaEntrada.class);
                startActivity(intent);
            }  
        });
        dialogo1.show(); 
		
	}
	
	
}
