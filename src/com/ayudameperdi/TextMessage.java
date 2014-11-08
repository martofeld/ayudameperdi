package com.ayudameperdi;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class TextMessage {

	private String numero;
	private boolean sent;
	private PendingIntent pi;
	
	public TextMessage(PendingIntent pi){
		this.pi = pi;
	}
	
	public TextMessage(String numero){
		this.pi = null;
		this.numero = numero;
	}
	
	public void sendMessage(String mensaje){
		SmsManager sms = SmsManager.getDefault();
        Log.e("Message",mensaje);
        Log.e("NUMERO", this.numero);
		sms.sendTextMessage(this.numero, null, mensaje, null, this.pi);
	}
	
	public void setSent(boolean b){
		this.sent=b;
	}
	
	public boolean getSent(){
		return this.sent;
	}
	
	public void setNumber(String number){
		this.numero = number;
	}
}
