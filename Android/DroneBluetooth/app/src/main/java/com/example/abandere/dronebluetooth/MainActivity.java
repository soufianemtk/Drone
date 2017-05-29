package com.example.abandere.dronebluetooth;

import android.app.Activity;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int REQUEST_ENABLE_BT = 1 ;
    int resource = R.id.layoutBT;
    Button buttonConnecterserveur ;
    Button buttonConnecterclient ;
    TextView textServeur;
    TextView textClient;

    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public UsbManager usbMan;
    public PendingIntent mPermissionIntent;
    public UsbBroadcastReceiver usbBroadcastReceiver;
    public Intent receiverIntent;
    public Handler mHandler;


    @Override
    /*creation des boutons et des textes(connecter client et connecter serveur)
    * ajout de listner sur les deux boutons
    * verification de la compatibilité de l'appareil avec le bluetooth
    * activation du bluetooth ci celui ci n'est pas activé
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonConnecterserveur = (Button) findViewById(R.id.buttonConnecterserveur);
        buttonConnecterclient = (Button) findViewById(R.id.buttonConnecterclient);
        textServeur  = (TextView)findViewById(R.id.textView3);
        textClient  = (TextView)findViewById(R.id.textView4);

        buttonConnecterserveur.setOnClickListener((View.OnClickListener) this);
        buttonConnecterclient.setOnClickListener((View.OnClickListener) this);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this.getApplicationContext(), "Bluetooth non supporté sur cet appareil", Toast.LENGTH_LONG).show();
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

    }

    @Override

    public void onClick(View v) {

        switch (v.getId()) {
            /*Si l'utilisateur clique sur le bouton connecter serveur
            *modification de l'interface graphique
            * creation du thread serveur
            * lancement de la méthode run
            * demande de connexion USB au drone
            * parcourir la liste des appareils connectés en USB
            * detection du drone
            * envoi d'une demande de connexion USB
            * attente de la réponse
            * lancement de l'activité commande drone serveur
             */
            case R.id.buttonConnecterserveur:

                //v.setVisibility(View.INVISIBLE);
                //textClient.setVisibility(View.VISIBLE);
                //buttonConnecterserveur = (Button) findViewById(R.id.buttonConnecterserveur);

                Button buttonS1 = (Button) findViewById(R.id.buttonConnecterserveur);
                buttonConnecterserveur.setText("SERVEUR EN ATTENTE");
                buttonS1.setVisibility(View.INVISIBLE);

                Button buttonC1 = (Button) findViewById(R.id.buttonConnecterclient);
                buttonC1.setText("Client se connecte");

                View textC = findViewById(R.id.textView4);
                textC.setVisibility(View.VISIBLE);
                AcceptThread thread1 = new AcceptThread();
                thread1.run();
                ConnectedThread thread3= new ConnectedThread(thread1.getSocket());
                thread3.run();
                mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(Constant.ACTION_USB_PERMISSION), 0);
                IntentFilter filter = new IntentFilter(Constant.ACTION_USB_PERMISSION);
                usbMan = (UsbManager) getSystemService(Context.USB_SERVICE);
                receiverIntent = getBaseContext().registerReceiver(usbBroadcastReceiver, filter);
                this.usbBroadcastReceiver = UsbBroadcastReceiver.getInstance((UsbManager) getSystemService(Context.USB_SERVICE));



                UsbAccessory[] accessoryList = usbMan.getAccessoryList();
                if (accessoryList == null) {
                    Toast.makeText(this.getApplicationContext(), "No connected drone", Toast.LENGTH_LONG).show();
                } else {
                    usbMan.requestPermission(accessoryList[0], mPermissionIntent);


                    Toast.makeText(this.getApplicationContext(), "Drone found", Toast.LENGTH_LONG).show();

                    if (usbMan.hasPermission(accessoryList[0])) {
                        Toast.makeText(this.getApplicationContext(), "Permission acceptée", Toast.LENGTH_LONG).show();
                    } else if (!usbMan.hasPermission(accessoryList[0])) {
                        Toast.makeText(this.getApplicationContext(), "Permission refusée", Toast.LENGTH_LONG).show();

                    }
                }

                Intent CommandesDroneServeur = new Intent(this, CommandesDroneServeur.class);
                startActivity(CommandesDroneServeur);


            break;

            case R.id.buttonConnecterclient:

            /*Si l'utilisateur clique sur le bouton connecter client
            *modification de l'interface graphique
            * creation du thread client
            * lancement de la méthode run
            * si la connexion est acceptée
            * lancement de l'activité commande drone client
             */
                v.setVisibility(View.INVISIBLE);
                textServeur.setVisibility(View.VISIBLE);
                buttonConnecterclient.setText("CLIENT SE CONNECTE");

                ConnectThread thread2 = new ConnectThread(mBluetoothAdapter.getRemoteDevice("14:30:C6:27:AC:BD"));
                thread2.run();
                SystemClock.sleep(5000);
                //ConnectedThread thread4= new ConnectedThread(thread2.getSocket());


                if(ConnectThread.connectionAccepted == 1) {

                    Intent CommandesDroneClient = new Intent(this, CommandesDroneClient.class);
                    startActivity(CommandesDroneClient);

                } else {
                    Toast.makeText(this.getApplicationContext(), "Bluetooth connection fail", Toast.LENGTH_LONG).show();
                }


            break;

        }

    }

}
