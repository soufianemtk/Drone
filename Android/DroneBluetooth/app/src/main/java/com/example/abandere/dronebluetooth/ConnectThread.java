package com.example.abandere.dronebluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/** le thread du client*/
public class ConnectThread extends Thread {

    private  BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private BluetoothAdapter mBluetoothAdapter;
    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static ConnectedThread connectedThread;
    public static int connectionAccepted = 0;

    /*thread du client crée un bluetoothsocket qui va envoyer une demande de connexion bluetooth
    * une fois la connexion établie le socket bluetoothclient est fermé
    * et on lance le thread de communication (connected thread)
     */
    public ConnectThread(BluetoothDevice device) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        BluetoothSocket tmp = null;
        mmDevice = device;


        try {

            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) { }
        mmSocket = tmp;
    }
    public BluetoothSocket getSocket(){
        return this.mmSocket;
    }

    public void run () {

        mBluetoothAdapter.cancelDiscovery();
        try {

            mmSocket.connect();
            connectionAccepted =1;
        } catch (IOException connectException) {
            connectException.printStackTrace();
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                closeException.printStackTrace();
            }
            return;
        }



    }

    /*permet de supprimer le socket du client */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }

  }