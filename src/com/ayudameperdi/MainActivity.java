package com.ayudameperdi;

import java.util.ArrayList;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	public static ImageButton help;
	private Geoloc mGeoloc;
	private DataBase mDataBase;
	private ActionBar mActionBar;
	private ImageView mImageView;
	public boolean recived = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        
        //Creating objects
    	mDataBase = new DataBase(this, "AyudaMePerdi", null, 1);
    	mGeoloc = new Geoloc(this, 5000, 200, this);
    	mActionBar = getSupportActionBar();
    	mActionBar.hide();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.options) {
            Intent b = new Intent(this, ConfigActivity.class);
            startActivity(b);
        	return true;
        }
        if (id == R.id.about){
        	Intent b = new Intent(this, AboutActivity.class);
            startActivity(b);
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    public void loadButton()
    {
    	setContentView(R.layout.activity_main);
    	help = (ImageButton) findViewById(R.id.btnHelp);
    	help.setImageResource(R.drawable.button_up);
		help.setAdjustViewBounds(true);
		help.setMaxHeight(300);
		help.setMaxWidth(300);
    	help.setOnTouchListener(helpOnTouchListener);
    	mImageView = (ImageView)findViewById(R.id.imgResponse);
    	mImageView.setImageDrawable(null);
    	mActionBar.show();
    }
    
    void sendMessage(){
    	String direccion = mGeoloc.getLocation();
    	Log.d("direccion", direccion);
    	
    	ArrayList<Contact> contactos = mDataBase.getContacts();

    	TextMessage mTextMessage = new TextMessage(getPi());
    	
    	if(contactos.isEmpty()){
    		Toast.makeText(this, "No hay contactos".toUpperCase(), Toast.LENGTH_LONG).show();
    		return;
    	}
    	for(Contact c : contactos){
    		mTextMessage.setNumber(c.getNumber());
    		mTextMessage.sendMessage("Me perdi en " +direccion);
    	}
    	Toast.makeText(this, "MENSAJES ENVIADOS", Toast.LENGTH_LONG).show();
    	mImageView.setImageResource(R.drawable.tick);
    }
    
    private PendingIntent getPi(){
	  String DELIVERED = "delivered";

      Intent deliveryIntent = new Intent(DELIVERED);

      PendingIntent pi = PendingIntent.getBroadcast(
       getApplicationContext(), 0, deliveryIntent,
       PendingIntent.FLAG_UPDATE_CURRENT);
     
     registerReceiver(new BroadcastReceiver() {
	       @Override
	      public void onReceive(Context context, Intent intent) {
	    	   Toast.makeText(getApplicationContext(), "No te preocupes, el mensaje fue enviado con exito. Quedate donde estas.".toUpperCase(), Toast.LENGTH_LONG).show();
	    	   mImageView.setImageResource(R.drawable.dostick);
	      }
      }, new IntentFilter(DELIVERED));
     
     return pi;
  }
    
    OnTouchListener helpOnTouchListener = new OnTouchListener(){
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch(event.getAction())
			{
				case MotionEvent.ACTION_UP:
					help.setImageResource(R.drawable.button_up);
					help.setAdjustViewBounds(true);
					help.setMaxHeight(300);
					help.setMaxWidth(300);
					sendMessage();		
					return true;
				case MotionEvent.ACTION_DOWN:
					help.setImageResource(R.drawable.button_down);
					help.setAdjustViewBounds(true);
					help.setMaxHeight(300);
					help.setMaxWidth(300);
					mImageView.setImageDrawable(null);
					return true;				
			}
			return false;
		}        
    };
}
