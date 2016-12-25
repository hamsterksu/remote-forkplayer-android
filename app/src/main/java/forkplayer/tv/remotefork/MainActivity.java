package forkplayer.tv.remotefork;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import forkplayer.tv.remotefork.service.RemoteForkService;

public class MainActivity extends AppCompatActivity {

    private TextView status;
    private ImageView btnStatus;

    private RemoteForkService.ForkBinder service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Upload playlist action: Not Implemented", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        status = (TextView) findViewById(R.id.status);
        btnStatus = (ImageView) findViewById(R.id.btn_status);
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (service == null) {
                    return;
                }
                if (service.isStarted()) {
                    service.stop();
                } else {
                    service.start();
                }
                updateStatus(service);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService(new Intent(this, RemoteForkService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void updateStatus(RemoteForkService.ForkBinder service) {
        this.service = service;
        boolean connected = service != null && service.isStarted();
        if (connected) {
            status.setText("Status: Connected");
            btnStatus.setImageLevel(1);
        } else {
            status.setText("Status: Disconnected");
            btnStatus.setImageLevel(0);
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            updateStatus((RemoteForkService.ForkBinder) iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            updateStatus(null);
        }
    };
}
