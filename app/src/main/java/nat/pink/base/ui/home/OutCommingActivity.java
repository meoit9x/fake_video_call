package nat.pink.base.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import nat.pink.base.R;
import nat.pink.base.databinding.ActivityVideoCallingBinding;
import nat.pink.base.model.ObjectCalling;
import nat.pink.base.utils.Config;
import nat.pink.base.utils.Const;
import nat.pink.base.utils.Utils;

public class OutCommingActivity extends AppCompatActivity {

    private ActivityVideoCallingBinding binding;
    private ObjectCalling objectCalling;
    private Timer timer;
    private boolean showIconVideo;
    private String timeString = "";
    private long mElapsedTime;
    private Handler handlerTime = new Handler();
    private Runnable updateTime;
    private ScreenReceiver mReceiver;

    public static final int RESULT_PAUSE = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utils.overLockScreen(this);
        super.onCreate(savedInstanceState);

        Utils.showFullScreen(this);

        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);

        if (getIntent() != null) {
            Serializable serializable = getIntent().getSerializableExtra(Const.PUT_EXTRAL_OBJECT_CALL);
            if (serializable instanceof ObjectCalling) {
                objectCalling = (ObjectCalling) serializable;
            }
            showIconVideo = getIntent().getBooleanExtra("show_icon_video", false);
        }

        binding = ActivityVideoCallingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mElapsedTime = 0;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        binding.swCamera.setVisibility(showIconVideo ? View.VISIBLE : View.GONE);
        startCallTimer();
    }

    private void initEvent() {
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
        binding.itemButtomFooter.llCancelCall.setOnClickListener(v -> {
            finish();
        });
    }

    private void initData() {
        if (objectCalling != null) {
            Glide.with(this).load(Uri.parse(objectCalling.getPathImage())).into(binding.ivCall);
            Glide.with(this).load(Uri.parse(objectCalling.getPathImage())).fitCenter().into(binding.ivContent);
            binding.txtName.setText(objectCalling.getName());
            updateTime = () -> {
                timeString = "" + getDurationString((int) mElapsedTime);
                binding.extTimer.setText(timeString.isEmpty() && showIconVideo ? getResources().getText(R.string.title_calling) : !showIconVideo ? getResources().getText(R.string.title_calling) : timeString);
            };
            timer = new Timer();
            Handler handler = new Handler();
            Runnable update = () -> {
                if (!showIconVideo) {
                    Intent intent = new Intent(this, VideoCallAnswerActivity.class);
                    intent.putExtra(Const.PUT_EXTRAL_OBJECT_CALL, objectCalling);
                    startActivityForResult(intent, Config.CHECK_TURN_OFF_VOICE_INCOMING);
                }
            };
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    handler.post(update);
                }
            }, 500);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mElapsedTime = 0;
        if (timer != null) {
            timer.cancel();
        }
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        timer = null;
        Utils.clearFlags(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void startCallTimer() {
        if (timer == null) {
            timer = new Timer();
            mElapsedTime = 0;
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    mElapsedTime += 1;
                    handlerTime.post(updateTime);
                }
            }, 0, 1000);
        }
    }

    public String getDurationString(int seconds) {

        if (seconds < 0 || seconds > 2000000)
            seconds = 0;
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        if (hours == 0)
            return twoDigitString(minutes) + " : " + twoDigitString(seconds);
        else
            return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    public String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    public class ScreenReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Intent returnIntent = new Intent();
                OutCommingActivity.this.setResult(RESULT_PAUSE, returnIntent);
                finish();
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.CHECK_TURN_OFF_VOICE_INCOMING) {
            if (resultCode == OutCommingActivity.RESULT_PAUSE) {
                setResult(resultCode, data);
            }
            finish();
        }
    }
}

