package com.Veripass1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ItemCategoriaAdapter extends BaseAdapter {

	
	protected Activity activity;
	protected ArrayList<ItemCategoria> items;
	
	
	public ItemCategoriaAdapter(Activity activity, ArrayList<ItemCategoria> items) {
		this.activity = activity;
		this.items = items;
	}


	@Override
	public int getCount() {
		return items.size();
	}


	@Override
	public Object getItem(int position) {
		return items.get(position);
	}


	@Override
	public long getItemId(int position) {
		return items.get(position).getId();
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		
        if(convertView == null) {
        	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	vi = inflater.inflate(R.layout.list_item_categoria, null);
        }
            
        ItemCategoria item = items.get(position);
        
        ImageView image = (ImageView) vi.findViewById(R.id.imagen);
        int imageResource = activity.getResources().getIdentifier(item.getRutaImagen(), null, activity.getPackageName());
        image.setImageDrawable(activity.getResources().getDrawable(imageResource));
        
        TextView nombre = (TextView) vi.findViewById(R.id.nombre);
        nombre.setText(item.getNombre());
        
        TextView tipo = (TextView) vi.findViewById(R.id.cantidadpass);
        tipo.setText(item.getcantidadpass());

        return vi;
	}

	
}
