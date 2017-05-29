package com.example.abandere.dronebluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class CommandesDroneClient extends AppCompatActivity implements View.OnClickListener {

    private BluetoothSocket mmSocket;

    /* création des boutons commandes drone
    * on place des listner sur chaque bouton
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commandes_droneclient);

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
        buttonTleft.setOnClickListener((View.OnClickListener)this);

    }

    @Override
    /* quand on clique sur un bouton
    *on écrit sur la socket de communication le chiffre correspondant
    * a la commande envoyee */

    public void onClick(View v) {

        byte [] tab = new byte[1];

        switch (v.getId()){
            case R.id.buttonUp :

                tab[0]=1;
                ConnectedThread.getInstance(mmSocket).write(tab);

                break;

            case R.id.buttonTleft :

                tab[0]=2;
                ConnectedThread.getInstance(mmSocket).write(tab);

                break;

            case R.id.buttonTright :

                tab[0]=3;
                ConnectedThread.getInstance(mmSocket).write(tab);
              
                break;

            case R.id.buttonFwd :

                tab[0]=4;
                ConnectedThread.getInstance(mmSocket).write(tab);

                break;

            case R.id.buttonMvleft :

                tab[0]=5;
                ConnectedThread.getInstance(mmSocket).write(tab);

                break;

            case R.id.buttonMvRight :

                tab[0]=6;
                ConnectedThread.getInstance(mmSocket).write(tab);

                break;

            case R.id.buttonHover :

                tab[0]=7;
                ConnectedThread.getInstance(mmSocket).write(tab);

                break;

            case R.id.buttonBwd :

                tab[0]=8;
                ConnectedThread.getInstance(mmSocket).write(tab);

                break;

            case R.id.buttonDown :

                tab[0]=9;
                ConnectedThread.getInstance(mmSocket).write(tab);

                break;

            case R.id.buttonTakeoff :

                tab[0]=10;
                ConnectedThread.getInstance(mmSocket).write(tab);

                break;

            case R.id.buttonAdjust :

                tab[0]=11;
                ConnectedThread.getInstance(mmSocket).write(tab);
                break;

            case R.id.buttonLand:

                tab[0]=12;
                ConnectedThread.getInstance(mmSocket).write(tab);

                break;

            case R.id.buttonEnd :

                tab[0]=13;
                ConnectedThread.getInstance(mmSocket).write(tab);

                break;

            case R.id.buttonEmergency :

                tab[0]=14;
                ConnectedThread.getInstance(mmSocket).write(tab);

                break;
            default:

        }
    }

}
