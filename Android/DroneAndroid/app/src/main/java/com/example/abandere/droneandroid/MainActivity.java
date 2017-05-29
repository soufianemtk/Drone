package com.example.abandere.droneandroid;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {

    public UsbManager usbMan;
    public PendingIntent mPermissionIntent;
    public UsbBroadcastReceiver usbBroadcastReceiver;
    public Intent receiverIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonConnect = (Button) findViewById(R.id.buttonConnect); //bouton
        buttonConnect.setOnClickListener(this);

        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(Constant.ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(Constant.ACTION_USB_PERMISSION);
        usbMan = (UsbManager) getSystemService(Context.USB_SERVICE);
        usbBroadcastReceiver = UsbBroadcastReceiver.getInstance(usbMan);
        receiverIntent = getBaseContext().registerReceiver(usbBroadcastReceiver, filter);
    }

    @Override
    public void onClick(View v) {
            /*Creation du bouton connect
            *parcourir la liste des appareils connectés en USB
            * detection du drone
            * envoi d'une demande de connexion USB
            * attente de la réponse
            * lancement de l'activité commande drone
            */
            Button buttonConnect = (Button) findViewById(R.id.buttonConnect);
            buttonConnect.setText("Connecting...");

        //Demande de connexion USB

        UsbAccessory[] accessoryList = usbMan.getAccessoryList();
        if (accessoryList == null) {
            Toast.makeText(this.getApplicationContext(), "No connected drone", Toast.LENGTH_LONG).show();
        } else {
            usbMan.requestPermission(accessoryList[0], mPermissionIntent);

            Toast.makeText(this.getApplicationContext(), "Drone found", Toast.LENGTH_LONG).show();

            if (usbMan.hasPermission(accessoryList[0]) == true){
                Toast.makeText(this.getApplicationContext(), "Permission acceptée", Toast.LENGTH_LONG).show();
            } else if (usbMan.hasPermission(accessoryList[0]) == false){
                Toast.makeText(this.getApplicationContext(), "Permission refusée", Toast.LENGTH_LONG).show();
            }
            //Création de l'intention
            Intent CommandesDrone = new Intent(this, CommandesDrone.class);
            startActivity(CommandesDrone);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
