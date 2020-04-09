
package com.Veripass1;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;


public class Ayuda extends Activity {

	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
			
		setContentView(R.layout.ayuda);	
		
		Button butonAtras = (Button)findViewById(R.id.btnIratras);
    	butonAtras.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				iratras();
			}
		});
	
    	Button sharingButton = (Button) findViewById(R.id.btnCompartir);
    	sharingButton.setOnClickListener(new View.OnClickListener() {   
    		public void onClick(View v) { 
    		shareIt();
    		}
    		});		
	
	}
	
	private void shareIt() {
		//sharing implementation here
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		String shareBody = "Recomiendo descargar Veripass: administrador de contraseñas. Descargar desde google play, mas info de la aplicacion en: http://www.webgratis.com.ar";
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Recomiendo Veripass administrador de contraseñas");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		startActivity(Intent.createChooser(sharingIntent, "Comparte Veripass"));
		
	}
	
	public void iratras()
	{
		finish();
		Intent inew1 = new Intent(this, MainActivity.class);
		startActivity(inew1);
	}
}
