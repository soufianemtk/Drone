package com.example.abandere.droneandroid;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;


public class CommandesDrone extends AppCompatActivity implements View.OnClickListener {


    private UsbBroadcastReceiver usbBroadcastReceiver;

    @Override
    /*creation des boutons de commandes drone
    * ajout d'un listener sur chaque bouton*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commandes_drone);

        this.usbBroadcastReceiver = UsbBroadcastReceiver.getInstance((UsbManager) getSystemService(Context.USB_SERVICE));

        Button buttonUp = (Button) findViewById(R.id.buttonUp);
        buttonUp.setOnClickListener((View.OnClickListener) this);

        Button buttonTleft = (Button) findViewById(R.id.buttonTleft);
        buttonTleft.setOnClickListener((View.OnClickListener)this);

        Button buttonTright = (Button) findViewById(R.id.buttonTright);
        buttonTright.setOnClickListener((View.OnClickListener)this);

        Button buttonForward = (Button) findViewById(R.id.buttonFwd);
        buttonForward.setOnClickListener((View.OnClickListener)this);

        Button buttonMvLeft = (Button) findViewById(R.id.buttonMvleft);
        buttonMvLeft.setOnClickListener((View.OnClickListener)this);

        Button buttonMvRight = (Button) findViewById(R.id.buttonMvRight);
        buttonMvRight.setOnClickListener((View.OnClickListener)this);

        Button buttonHover = (Button) findViewById(R.id.buttonHover);
        buttonHover.setOnClickListener((View.OnClickListener)this);

        Button buttonBackward = (Button) findViewById(R.id.buttonBwd);
        buttonBackward.setOnClickListener((View.OnClickListener)this);

        Button buttonDown = (Button) findViewById(R.id.buttonDown);
        buttonDown.setOnClickListener((View.OnClickListener) this);

        Button buttonTakeoff = (Button) findViewById(R.id.buttonTakeoff);
        buttonTakeoff.setOnClickListener((View.OnClickListener)this);

        Button buttonAdjust = (Button) findViewById(R.id.buttonAdjust);
        buttonAdjust.setOnClickListener((View.OnClickListener)this);

        Button buttonLand = (Button) findViewById(R.id.buttonLand);
        buttonLand.setOnClickListener((View.OnClickListener)this);

        Button buttonEnd = (Button) findViewById(R.id.buttonEnd);
        buttonEnd.setOnClickListener((View.OnClickListener)this);

        Button buttonEmergency = (Button) findViewById(R.id.buttonEmergency);
        buttonEmergency.setOnClickListener((View.OnClickListener)this);

    }

    @Override
    public void onClick(View v) {
        /*envoie des commandes au drone */
        switch (v.getId()){
            case R.id.buttonUp :
                usbBroadcastReceiver.sendToAccessory("Up");
                break;

            case R.id.buttonTleft :
                usbBroadcastReceiver.sendToAccessory("TurnLeft");
                break;

            case R.id.buttonTright :
                usbBroadcastReceiver.sendToAccessory("TurnRight");
                break;

            case R.id.buttonFwd :
                usbBroadcastReceiver.sendToAccessory("Forward");
                break;

            case R.id.buttonMvleft :
                usbBroadcastReceiver.sendToAccessory("MovLeft");
                break;

            case R.id.buttonMvRight :
                usbBroadcastReceiver.sendToAccessory("MovRight");
                break;

            case R.id.buttonHover :
                usbBroadcastReceiver.sendToAccessory("Hover");
                break;

            case R.id.buttonBwd :
                usbBroadcastReceiver.sendToAccessory("Backward");
                break;

            case R.id.buttonDown :
                usbBroadcastReceiver.sendToAccessory("Down");
                break;

            case R.id.buttonTakeoff :
                usbBroadcastReceiver.sendToAccessory("takeOff");
                break;

            case R.id.buttonAdjust :
                usbBroadcastReceiver.sendToAccessory("Adjust");
                break;

            case R.id.buttonLand:
                usbBroadcastReceiver.sendToAccessory("Land");
                break;

            case R.id.buttonEnd :
                usbBroadcastReceiver.sendToAccessory("End");
                break;

            case R.id.buttonEmergency :
                usbBroadcastReceiver.sendToAccessory("Emergency");
                break;
            default:

        }
    }

}
