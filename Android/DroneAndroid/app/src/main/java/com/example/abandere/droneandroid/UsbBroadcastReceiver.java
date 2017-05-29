package com.example.abandere.droneandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.ParcelFileDescriptor;
import android.provider.SyncStateContract;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class UsbBroadcastReceiver extends BroadcastReceiver {


    private static UsbBroadcastReceiver instance;
    private static final String TAG = UsbBroadcastReceiver.class.getSimpleName();

    private Context appContext;
    private UsbAccessory mAccessory;
    private FileInputStream mInputStream;
    private FileOutputStream mOutputStream;
    private ParcelFileDescriptor mFileDescriptor;
    private UsbManager usbMan;

    public UsbBroadcastReceiver (UsbManager usbMan) {
        this.usbMan = usbMan;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        appContext = context;
        if (Constant.ACTION_USB_PERMISSION.equals(action)) {
            synchronized (this) {
                mAccessory = intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                    if(mAccessory != null){
                        mFileDescriptor = usbMan.openAccessory(mAccessory);
                        FileDescriptor fd = mFileDescriptor.getFileDescriptor();
                        mInputStream = new FileInputStream(fd);
                        mOutputStream = new FileOutputStream(fd);
                    }
                } else {
                    Log.d(TAG, "permission denied for accessory " + mAccessory);
                }
            }
        }
    }

    public static UsbBroadcastReceiver getInstance(UsbManager usbMan) {
        if (instance == null){
            instance = new UsbBroadcastReceiver(usbMan);
        }
        return instance;
    }

    public void sendToAccessory(final String message) {
        if (mFileDescriptor != null) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Log.d("USB", "Writing data: " + message);
                        mOutputStream.write(message.getBytes());
                        Log.d("USB", "Done writing: " + message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public FileOutputStream getmOutputStream() {
        return mOutputStream;
    }


    public ParcelFileDescriptor getmFileDescriptor() {
        return mFileDescriptor;
    }
}
