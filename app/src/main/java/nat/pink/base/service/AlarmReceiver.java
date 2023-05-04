package nat.pink.base.service;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nat.pink.base.MainActivity;
import nat.pink.base.R;
import nat.pink.base.model.ObjectCalling;
import nat.pink.base.ui.home.VideoCallActivity;
import nat.pink.base.utils.Const;
import nat.pink.base.utils.PreferenceUtil;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Const.ACTION_CALL_VIDEO)) {
            PreferenceUtil.clearEdit(context, PreferenceUtil.KEY_CALLING_VIDEO);
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra(Const.PUT_EXTRAL_OBJECT_CALL, intent.getSerializableExtra(Const.PUT_EXTRAL_OBJECT_CALL));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setAction(Const.ACTION_CALL_VIDEO);
            i.setType(intent.getType());
            context.startActivity(i);
//            ObjectCalling objectCalling = (ObjectCalling) intent.getSerializableExtra(Const.PUT_EXTRAL_OBJECT_CALL);
//            buildNotification(context, objectCalling);
        } else if (intent.getAction().equals(Const.ACTION_SHOW_NOTI)) {
            PreferenceUtil.clearEdit(context, PreferenceUtil.KEY_CURRENT_TIME_NOTI);
            ObjectCalling objectCalling = (ObjectCalling) intent.getSerializableExtra(Const.PUT_EXTRAL_OBJECT_CALL);
            if (NotificationManagerCompat.from(context).areNotificationsEnabled())
                showNotification(context, objectCalling);
        } else if (intent.getAction().equals(Const.ACTION_SHOW_POP_UP)) {
            PreferenceUtil.clearEdit(context, PreferenceUtil.KEY_SHOW_POPUP);
            ObjectCalling objectCalling = (ObjectCalling) intent.getSerializableExtra(Const.PUT_EXTRAL_OBJECT_CALL);
            Gson gson = new Gson();
            String json = gson.toJson(objectCalling);
//            Intent i = new Intent(context, ChatHeadService.class);
//            i.setAction(Const.ACTION_SHOW_POP_UP);
//            i.setType(json);
//            context.startService(i);
        } else if (intent.getAction().equals(Const.ACTION_COMMING_VIDEO)) {
            PreferenceUtil.clearEdit(context, PreferenceUtil.KEY_COMMING_VIDEO);
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra(Const.PUT_EXTRAL_OBJECT_CALL, intent.getSerializableExtra(Const.PUT_EXTRAL_OBJECT_CALL));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setAction(Const.ACTION_COMMING_VIDEO);
            i.setType(intent.getType());
            context.startActivity(i);
        } else if (intent.getAction().equals(Const.ACTION_CALL_VOICE)) {
            PreferenceUtil.clearEdit(context, PreferenceUtil.KEY_CALLING_VOICE);
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra(Const.PUT_EXTRAL_OBJECT_CALL, intent.getSerializableExtra(Const.PUT_EXTRAL_OBJECT_CALL));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setAction(Const.ACTION_CALL_VOICE);
            i.setType(intent.getType());
            context.startActivity(i);
        } else if (intent.getAction().equals(Const.ACTION_COMMING_VOICE)) {
            PreferenceUtil.clearEdit(context, PreferenceUtil.KEY_COMMING_VOICE);
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra(Const.PUT_EXTRAL_OBJECT_CALL, intent.getSerializableExtra(Const.PUT_EXTRAL_OBJECT_CALL));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setAction(Const.ACTION_COMMING_VOICE);
            i.setType(intent.getType());
            context.startActivity(i);
        }
    }

    private void showNotification(Context context, ObjectCalling objectCalling) {
        Uri imageUri = objectCalling.getPathImage() == null || objectCalling.getPathImage().equals("") ? null : Uri.parse(objectCalling.getPathImage());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            builder.setSmallIcon(R.drawable.ic_messenger);
            builder.setColor(context.getResources().getColor(R.color.color_057BF7));
        } else {
            builder.setSmallIcon(R.drawable.ic_messenger);
        }

        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(Const.ACTION_CREAT_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
        Date resultdate = new Date(yourmilliseconds);

        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
        contentView.setImageViewUri(R.id.ava, imageUri);
        contentView.setTextViewText(R.id.ext_name, objectCalling.getName());
        contentView.setTextViewText(R.id.ext_title, objectCalling.getMessage());
        contentView.setTextViewText(R.id.time, sdf.format(resultdate));

        builder.setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            builder.setCustomBigContentView(contentView);
        } else {
            builder.setCustomContentView(contentView);
        }
        builder.setContentTitle("Custom Notification");
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            mNotificationManager.createNotificationChannel(new NotificationChannel("my_call_app", "Call App", NotificationManager.IMPORTANCE_DEFAULT));
            builder.setChannelId("my_call_app");
        }


        // Will display the notification in the notification bar
        mNotificationManager.notify(1, builder.build());
    }

    private void buildNotification(Context context, ObjectCalling objectCalling) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        Intent fullScreenIntent = new Intent(context, VideoCallActivity.class);

        fullScreenIntent.putExtra(Const.PUT_EXTRAL_OBJECT_CALL, objectCalling);
        fullScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        fullScreenIntent.setAction(Const.ACTION_CALL_VIDEO);
        fullScreenIntent.setType(Const.ACTION_CALL_VIDEO);

        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setCategory(NotificationCompat.CATEGORY_CALL)
                        .setContentTitle(context.getResources().getString(R.string.app_name))
                        .setContentText("app demo")
                        .setOngoing(true)
                        .setContentIntent(fullScreenPendingIntent)
                        .setFullScreenIntent(fullScreenPendingIntent, true);

        notificationBuilder.setAutoCancel(true);
        notificationManager.createNotificationChannel(new NotificationChannel("my_call_app", "Call App", NotificationManager.IMPORTANCE_HIGH));
        notificationBuilder.setChannelId("my_call_app");
        notificationManager.notify(456789, notificationBuilder.build());
    }
}


