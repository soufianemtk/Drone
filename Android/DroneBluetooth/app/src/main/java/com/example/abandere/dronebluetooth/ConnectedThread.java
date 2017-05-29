package com.example.abandere.dronebluetooth;

import android.bluetooth.BluetoothSocket;

 import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.LogRecord;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class ConnectedThread extends Thread {

    private BluetoothSocket mmSocket;
    private InputStream mmInStream;
    private OutputStream mmOutStream;
    public final int MESSAGE_READ = 1;
    public static ConnectedThread connectedThread;
    public static Handler mHandler;

    public UsbBroadcastReceiver usbBroadcastReceiver;

    /*connected thread c'est le thread de communication
    *il est crée une fois que la communication bluetooth est établie
    */

    public ConnectedThread(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;


    }

    /*cette méthode permet au serveur de lire ce qui est stocké sur le buffer
    *et d'appeler implicitement la méthode handleMessage via la méthode obtainMessage
    * */
    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()


        while (true) {
            try {

                bytes = mmInStream.read(buffer);


                mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();

            } catch (IOException e) {
                break;
            }
        }
    }

    /* cette méthode est appelée à partir de la main activity et permet au client d'écrire sur le socket  */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) { }
    }

    /* cette méthode permet de fermer le socket de communication*/
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }

    /*
    * cette méthode permet de retourner le connected thread existant ou d'en creer un nouveau si celui-ci n'existait pas auparavant(singleton)
    * */
    public static ConnectedThread getInstance(BluetoothSocket mmSocket) {
        if (connectedThread == null){
            connectedThread = new ConnectedThread(mmSocket);
        }
        return connectedThread;
    }

    /* cette méthode permet au serveur de lui faire connaitre le handler utilisé*/

    public void setHandler (Handler mHandler){
        this.mHandler = mHandler;
    }


}

