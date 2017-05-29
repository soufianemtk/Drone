package com.example.abandere.dronebluetooth;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class CommandesDroneServeur extends AppCompatActivity {


    public UsbManager usbMan;
    public PendingIntent mPermissionIntent;
    public UsbBroadcastReceiver usbBroadcastReceiver;
    public Intent receiverIntent;

/*Creation d'un Handler qui va gérer les messgaes échangés
*grace à la méthode handleMessage le serveur envoie au drone
* les commandes envoyées par le client selon la valeur du chiffre recu */

    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {



            if (msg.what == 1){



                byte[] tab = new byte[1];
                tab = (byte[])msg.obj;

                switch (tab[0]){
                    case 1 :

                        UsbBroadcastReceiver.getInstance(usbMan).sendToAccessory("Up");
                        Log.d("",     "commande up envoyee");

                        break;

                    case 2 :
                        UsbBroadcastReceiver.getInstance(usbMan).sendToAccessory("TurnLeft");
                        break;

                    case 3 :
                        UsbBroadcastReceiver.getInstance(usbMan).sendToAccessory("TurnRight");
                        break;

                    case 4 :
                        UsbBroadcastReceiver.getInstance(usbMan).sendToAccessory("Forward");
                        break;

                    case 5 :
                        UsbBroadcastReceiver.getInstance(usbMan).sendToAccessory("MovLeft");
                        break;

                    case 6 :
                        UsbBroadcastReceiver.getInstance(usbMan).sendToAccessory("MovRight");
                        break;

                    case 7 :
                        UsbBroadcastReceiver.getInstance(usbMan).sendToAccessory("Hover");
                        break;

                    case 8 :
                        UsbBroadcastReceiver.getInstance(usbMan).sendToAccessory("Backward");
                        break;

                    case 9 :
                        UsbBroadcastReceiver.getInstance(usbMan).sendToAccessory("Down");
                        break;

                    case 10 :
                        UsbBroadcastReceiver.getInstance(usbMan).sendToAccessory("takeOff");
                        break;

                    case 11 :
                        UsbBroadcastReceiver.getInstance(usbMan).sendToAccessory("Adjust");
                        break;

                    case 12 :
                        UsbBroadcastReceiver.getInstance(usbMan).sendToAccessory("Land");
                        break;

                    case 13 :
                        UsbBroadcastReceiver.getInstance(usbMan).sendToAccessory("End");
                        break;

                    case 14 :
                        UsbBroadcastReceiver.getInstance(usbMan).sendToAccessory("Emergency");
                        break;

                }

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commandes_droneserveur);

        //Button buttonConnect = (Button) findViewById(R.id.buttonConnect); //bouton
        //buttonConnect.setOnClickListener(this);

        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(Constant.ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(Constant.ACTION_USB_PERMISSION);
        usbMan = (UsbManager) getSystemService(Context.USB_SERVICE);
        receiverIntent = getBaseContext().registerReceiver(usbBroadcastReceiver, filter);
        this.usbBroadcastReceiver = UsbBroadcastReceiver.getInstance((UsbManager) getSystemService(Context.USB_SERVICE));



    }



}


