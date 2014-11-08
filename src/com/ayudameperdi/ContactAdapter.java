package com.ayudameperdi;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactAdapter extends ArrayAdapter<Contact>{
	Activity context;
	ArrayList<Contact> datos;
	
	private static final String KEY_ROWID = "telefono";
	private static final String KEY_NAME = "nombre";
    private static final String KEY_PH_NO = "telefono";
	
	public ContactAdapter (Activity context, ArrayList<Contact> datos){
		super(context, R.layout.rows, datos);
		this.context = context;
		this.datos = datos;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = context.getLayoutInflater();
		View item = inflater.inflate(R.layout.rows, parent, false);
		TextView name = (TextView)item.findViewById(R.id.txtListNombre);
		TextView phone = (TextView)item.findViewById(R.id.txtListNumero);
		
		name.setText(datos.get(position).getName());
		phone.setText(datos.get(position).getNumber());
		
		return item;
	}
}