package com.Veripass1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemSelectedListener;

public class ModificarRegistro extends Activity {	
	Spinner listacategoria;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
        setContentView(R.layout.altaregistro);		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.barratitulonewreg);
        

        Bundle bundle = getIntent().getExtras();
        final String nombreOld = bundle.getString("NombreCuenta");
        
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
		
        
        // Hacemos la conexion a la bd
        final Handler_sqlite usdbh = new Handler_sqlite(this,"ClavesxCategoria", null, 1);
        final SQLiteDatabase db = usdbh.getWritableDatabase();
        String[] args = new String[] {nombreOld};
        String correo = "";
        String pass = "";
        String categ = "";
        String usuarionick = "";
        String sitioweb = "";
        String comentario = "";
        int idcateg=0;
        Cursor c = db.rawQuery(" SELECT nombrecuenta, correoemail, password, strCategoria, idcateg, usuarionick, sitioweb, comentario FROM ClavesxCategoria WHERE nombrecuenta=?", args);
        
        String query2 =	"Create Table ClavesxCategoria (idclavecat integer primary key not null, idcateg integer, " +
				"nombrecuenta varchar(25), usuarionick varchar(25), sitioweb varchar(70), comentario text, "+
				"password varchar(120), correoemail varchar(80), strCategoria varchar(25))";
        
        // Nos aseguramos de que existe el registro
        if (c.moveToFirst()) {
            // Recorremos el cursor hasta que no haya mas registros
            do {
                correo = c.getString(1);
                pass = c.getString(2);
                categ = c.getString(3);
                idcateg=c.getInt(4);
                usuarionick =c.getString(5);
                sitioweb =c.getString(6);
                comentario =c.getString(7);
                
            } while (c.moveToNext());
        }
         
        EditText txtNombreCuenta = (EditText) findViewById(R.id.TxtNombCuenta);
        EditText txtCorreo = (EditText) findViewById(R.id.TxtCorreo);
        EditText txtPass = (EditText) findViewById(R.id.TxtPass);
        EditText txtNick = (EditText) findViewById(R.id.TxtNick);
        EditText txtWeb = (EditText) findViewById(R.id.TxtWeb);
        EditText txtComentario = (EditText) findViewById(R.id.TxtComentario);        
                
        listacategoria.setSelection(idcateg-1);
        txtNombreCuenta.setText(nombreOld);
        txtCorreo.setText(correo);
        txtNick.setText(usuarionick);
        txtWeb.setText(sitioweb);
        txtComentario.setText(comentario);
        
        try {
			txtPass.setText(CodificadorAES.desencriptar(pass));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}                        
        
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

                    db.update("ClavesxCategoria", nuevoRegistro,"nombrecuenta='"+nombreOld+"'",null);	                    
                    db.close();	                    
                    Toast.makeText(getBaseContext(), "Se ha modificado correctamente el registro: '" + nombreCuenta.getText().toString() + "'", Toast.LENGTH_LONG).show();

                    intent.setClass(ModificarRegistro.this, BandejaEntrada.class);
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
