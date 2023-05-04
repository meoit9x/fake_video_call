package nat.pink.base.ui.home;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nat.pink.base.customView.camera.CameraHelper;
import nat.pink.base.customView.camera.CameraListener;
import nat.pink.base.customView.camera.RoundTextureView;
import nat.pink.base.customView.camera.glsurface.GLUtil;
import nat.pink.base.customView.camera.glsurface.RoundCameraGLSurfaceView;
import nat.pink.base.databinding.ActivityVideoCallAnswerBinding;
import nat.pink.base.model.ObjectCalling;
import nat.pink.base.utils.Const;
import nat.pink.base.utils.Utils;

public class VideoCallAnswerActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener, CameraListener {

    private ActivityVideoCallAnswerBinding binding;
    private ObjectCalling objectCalling;
    private CameraHelper cameraHelper;
    private RoundTextureView textureView;
    private static final int CAMERA_ID = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private Camera.Size previewSize;
    private int squarePreviewSize;
    private RoundCameraGLSurfaceView roundCameraGLSurfaceView;
    private ScreenReceiver mReceiver;

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
        }

        binding = ActivityVideoCallAnswerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        textureView = binding.texturePreview;
        roundCameraGLSurfaceView = binding.cameraGlSurfaceView;
        roundCameraGLSurfaceView.setFragmentShaderCode(GLUtil.FRAG_SHADER_SCULPTURE);
        roundCameraGLSurfaceView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    private void initEvent() {
        binding.ivBack.setOnClickListener(v -> finish());
        binding.itemButtomFooter.llCancelCall.setOnClickListener(v -> finish());
    }

    private void initData() {
        Uri uri = Uri.parse(objectCalling.getPathVideo());
        if (uri == null)
            return;
        binding.videoView.setVideoURI(uri);
        binding.videoView.start();
    }

    void initCamera() {
        cameraHelper = new CameraHelper.Builder()
                .cameraListener(this)
                .specificCameraId(CAMERA_ID)
                .previewOn(textureView)
                .previewViewSize(new Point(textureView.getLayoutParams().width, textureView.getLayoutParams().height))
                .rotation(getWindowManager().getDefaultDisplay().getRotation())
                .build();
        cameraHelper.start();
    }

    @Override
    public void onGlobalLayout() {
        roundCameraGLSurfaceView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        int withScreen = Utils.getWithScreen(this);

        FrameLayout.LayoutParams textureViewLayoutParams = (FrameLayout.LayoutParams) textureView.getLayoutParams();

        textureViewLayoutParams.width = (withScreen / 360) * 118;
        textureViewLayoutParams.height = (withScreen / 360) * 190;

        textureView.setLayoutParams(textureViewLayoutParams);

        initCamera();
    }

    @Override
    protected void onPause() {
        if (cameraHelper != null) {
            cameraHelper.stop();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cameraHelper != null) {
            cameraHelper.start();
        }
    }


    @Override
    public void onCameraOpened(Camera camera, final int cameraId, final int displayOrientation, boolean isMirror) {
        previewSize = camera.getParameters().getPreviewSize();
        squarePreviewSize = Math.min(previewSize.width, previewSize.height);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                {
                    ViewGroup.LayoutParams layoutParams = textureView.getLayoutParams();
                    //横屏
                    if (displayOrientation % 180 == 0) {
                        layoutParams.height = layoutParams.width * previewSize.height / previewSize.width;
                    }
                    //竖屏
                    else {
                        layoutParams.height = layoutParams.width * previewSize.width / previewSize.height;
                    }
                    textureView.setLayoutParams(layoutParams);
                }
                roundCameraGLSurfaceView.init(cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT, displayOrientation, squarePreviewSize, squarePreviewSize);
                radiusCamera();

            }
        });
    }

    @Override
    public void onPreview(final byte[] nv21, Camera camera) {
    }

    @Override
    public void onCameraClosed() {
    }

    @Override
    public void onCameraError(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {

    }

    @Override
    protected void onDestroy() {
        if (cameraHelper != null) {
            cameraHelper.release();
        }
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        Utils.clearFlags(this);
        super.onDestroy();
    }

    private void radiusCamera() {
        int progress = 15;
        int max = 100;
        textureView.setRadius(progress * Math.min(textureView.getWidth(), textureView.getHeight()) / 2 / max);
        textureView.turnRound();
    }

    public class ScreenReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Intent returnIntent = new Intent();
                VideoCallAnswerActivity.this.setResult(OutCommingActivity.RESULT_PAUSE, returnIntent);
                finish();
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {

            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);

            List<String> permissions = new ArrayList<String>();

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);

            }
            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[0]), 111);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);


                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);

                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
}
