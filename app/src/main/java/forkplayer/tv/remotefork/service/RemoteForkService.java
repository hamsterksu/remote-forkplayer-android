package forkplayer.tv.remotefork.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;
import forkplayer.tv.remotefork.MainActivity;
import forkplayer.tv.remotefork.R;
import timber.log.Timber;

/**
 * Created by hamsterksu on 25.12.16.
 */

public class RemoteForkService extends Service {

    private RemoteHttpServer server;

    private ForkBinder binder = new ForkBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.d("RemoteForkService.onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("RemoteForkService.onDestroy");
    }

    private void stopMe() {
        if (server != null) {
            Timber.d("RemoteForkService.stopMe");
            server.stop();
            server = null;
            stopForeground(true);
        }
    }

    private void startMe() {
        if (server != null) {
            return;
        }
        server = listen();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(PendingIntent.getActivity(this, 1000, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(false)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.ic_device)
                .setContentTitle("RemoteFork")
                .setContentText("Service tool is running");

        Notification notification = builder.build();
        startForeground(1000, notification);
    }

    private RemoteHttpServer listen() {
        RemoteHttpServer server = new RemoteHttpServer();
        try {
            server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        } catch (IOException e) {
            e.printStackTrace();
            server = null;
        }
        return server;
    }

    public static void start(Context context) {
        context.startService(new Intent(context, RemoteForkService.class));
    }

    public class ForkBinder extends Binder {
        public boolean isStarted() {
            return server != null;
        }

        public void stop() {
            stopMe();
            stopSelf();
        }

        public void start() {
            startMe();
        }
    }
}
