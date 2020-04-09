package com.Veripass1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class BarraPersonalizadaActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        setContentView(R.layout.activity_main);

        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.barratitulo1);
    }
}