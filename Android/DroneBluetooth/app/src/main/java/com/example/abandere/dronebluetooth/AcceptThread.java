package com.example.abandere.dronebluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import static java.util.UUID.fromString;


public class AcceptThread extends Thread {

    private final BluetoothServerSocket mmServerSocket;
    private BluetoothAdapter mBluetoothAdapter;
    public static final String NAME = "DroneBluetooth";
    public UsbManager usbMan ;
    public UsbBroadcastReceiver usbBroadcastReceiver;
    private BluetoothSocket socket;





    public BluetoothSocket getSocket(){
        return this.socket;
    }
    /*thread du serveur créé lors de la demande de connexion bluetooth
    *ce thread crée un bluetoothServeursocket qui va rester en écoute et attendre
    * la reception d'une demande de connexion bluetooth
    * une fois la connexion établie le socket bluetoothServeur est fermé
    * et on lance le thread de communication (connected thread)
     */
    public AcceptThread() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        BluetoothServerSocket tmp = null;
        try {
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, ConnectThread.MY_UUID);
        } catch (IOException e) { }
        mmServerSocket = tmp;
    }


   public void run() {
       BluetoothSocket socket = null;

       while (true) {
           try {
               socket = mmServerSocket.accept();
               this.socket=socket;
           } catch (IOException e) {
               break;
           }
           if (socket != null) {
//Une connection a ete accepte
               try {
                   mmServerSocket.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }



               break;
           }
       }
   }


    /** cette méthode permet de ferme le socket serveur*/
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) { }
    }







}
