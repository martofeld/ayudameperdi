package com.ayudameperdi;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ConfigActivity extends Activity{
	
	private DataBase db;
	private ArrayList<Contact> contactos = new ArrayList<Contact>();
	private ContactAdapter ca;
	private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        
        getWindow().setBackgroundDrawableResource(R.drawable.config_activity);
        
        db = new DataBase(this, "AyudaMePerdi", null, 1);
        
        contactos = db.getContacts();
        ca = new ContactAdapter(this, contactos);
        lv = (ListView)findViewById(R.id.lvContactos);
        lv.setAdapter(ca);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
        	   @Override
        	   public void onItemClick(AdapterView<?> adapter, View view, final int position, long arg) {
        		   DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        			    @Override
        			    public void onClick(DialogInterface dialog, int which) {
        			        switch (which){
        			        case DialogInterface.BUTTON_POSITIVE:
        			            db.delete("telefono='"+contactos.get(position).getNumber()+"'");
        			            contactos.remove(position);
        			            lv.setAdapter(ca);
        			            break;

        			        case DialogInterface.BUTTON_NEGATIVE:
        			            //No button clicked
        			            break;
        			        case DialogInterface.BUTTON_NEUTRAL:
        			            TextMessage tm = new TextMessage(contactos.get(position).getNumber());
        			            tm.sendMessage("Confirmacion de contacto.");
        			            break;
        			        }
        			    }
        			};
        			
        			builder.setMessage("OPCIONES").setPositiveButton("Eliminar\nContacto", dialogClickListener)
        			    .setNegativeButton("Cancelar", dialogClickListener).setNeutralButton("Probar\nMensaje", dialogClickListener).show();
        	   } 
        	});
    }

    public void addContact(View v){
    	String name = ((EditText)findViewById(R.id.txtNombre)).getText().toString();
    	String phone = ((EditText)findViewById(R.id.txtNumero)).getText().toString();
    	contactos.add(new Contact(name, phone));
    	Toast.makeText(this, "Contacto Agregado", Toast.LENGTH_SHORT).show();
    	
    	clear();
    	insert(name, phone);
    }
    
    private void clear(){
    	((EditText)findViewById(R.id.txtNumero)).setText("");
    	((EditText)findViewById(R.id.txtNombre)).setText("");
    	((EditText)findViewById(R.id.txtNumero)).setHint("Telefono");
    	((EditText)findViewById(R.id.txtNombre)).setHint("Nombre");
        lv.setAdapter(ca);
    }
    
    private void insert(String name, String phone){
    	if(db.userExists(phone)){
    		Toast.makeText(this, "Ya esta el usuario", Toast.LENGTH_SHORT).show();
    	}
    	else{
	        ContentValues cv = new ContentValues();
	        cv.put("nombre", name);
	        cv.put("telefono", phone);
	        
	        db.insertInto(cv);
    	}
    }
}
