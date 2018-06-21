
package com.pigeon.communication.privacy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.Toast;


//import com.pigeon.communication.privacy.databinding.ActivityCameraBinding;


//import com.pigeon.communication.privacy.databinding.ActivityCameraBinding;

import com.pigeon.communication.privacy.databinding.ActivityCameraBinding;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class CameraActivity extends AppCompatActivity {
    private int recLen = 32;
    private ActivityCameraBinding mBinding;

    private static final int FOCUS_AREA_SIZE = 500;
    private Camera mCamera;
    private CameraPreview mPreview;
    private MediaRecorder mediaRecorder;
    private String url_file;
    private static boolean cameraFront = false;
    private static boolean flash = false;
    private long countUp;
    private int quality = CamcorderProfile.QUALITY_480P;


    public static void startActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, CameraActivity.class);
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_camera);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initialize();
    }


    private int findFrontFacingCamera() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                cameraFront = true;
                break;
            }
        }
        return cameraId;
    }


    private int findBackFacingCamera() {
        int cameraId = -1;

        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                cameraFront = false;
                break;
            }
        }
        return cameraId;
    }
    public void onResume() {
        super.onResume();
        if (!hasCamera(getApplicationContext())) {

            Toast.makeText(getApplicationContext(), R.string.dont_have_camera_error, Toast.LENGTH_SHORT).show();
            setResult(MainActivity.RESULT_CODE_FOR_RECORD_VIDEO_FAILED);
            releaseCamera();
            releaseMediaRecorder();
            finish();
        }
        if (mCamera == null) {
            releaseCamera();
            final boolean frontal = cameraFront;

            int cameraId = findFrontFacingCamera();
            if (cameraId < 0) {
                switchCameraListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CameraActivity.this, R.string.dont_have_front_camera, Toast.LENGTH_SHORT).show();
                    }
                };

                cameraId = findBackFacingCamera();
                if (flash) {
                    mPreview.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    mBinding.buttonFlash.setImageResource(R.mipmap.ic_flash_on_white);
                }
            } else if (!frontal) {
                cameraId = findBackFacingCamera();
                if (flash) {
                    mPreview.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    mBinding.buttonFlash.setImageResource(R.mipmap.ic_flash_on_white);
                }
            }

            mCamera = Camera.open(cameraId);
            mPreview.refreshCamera(mCamera);
            reloadQualities(cameraId);

        }
    }

    public void initialize() {
        mPreview = new CameraPreview(CameraActivity.this, mCamera);
        mBinding.cameraPreview.addView(mPreview);
        mBinding.buttonCapture.setOnClickListener(captrureListener);
        mBinding.buttonChangeCamera.setOnClickListener(switchCameraListener);
        mBinding.buttonQuality.setOnClickListener(qualityListener);
        mBinding.buttonFlash.setOnClickListener(flashListener);
        mBinding.cameraPreview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    try {
                        focusOnTouch(event);
                    } catch (Exception e) {
                    }
                }
                return true;
            }
        });

    }

    private void reloadQualities(int idCamera) {
        SharedPreferences prefs = getSharedPreferences("RECORDING", Context.MODE_PRIVATE);

        quality = prefs.getInt("QUALITY", CamcorderProfile.QUALITY_480P);

        changeVideoQuality(quality);

        final ArrayList<String> list = new ArrayList<String>();

        int maxQualitySupported = CamcorderProfile.QUALITY_480P;

        if (CamcorderProfile.hasProfile(idCamera, CamcorderProfile.QUALITY_480P)) {
            list.add("480p");
            maxQualitySupported = CamcorderProfile.QUALITY_480P;
        }
        if (CamcorderProfile.hasProfile(idCamera, CamcorderProfile.QUALITY_720P)) {
            list.add("720p");
            maxQualitySupported = CamcorderProfile.QUALITY_720P;
        }
        if (CamcorderProfile.hasProfile(idCamera, CamcorderProfile.QUALITY_1080P)) {
            list.add("1080p");
            maxQualitySupported = CamcorderProfile.QUALITY_1080P;
        }
        if (CamcorderProfile.hasProfile(idCamera, CamcorderProfile.QUALITY_2160P)) {
            list.add("2160p");
            maxQualitySupported = CamcorderProfile.QUALITY_2160P;
        }

        if (!CamcorderProfile.hasProfile(idCamera, quality)) {
            quality = maxQualitySupported;
            updateButtonText(maxQualitySupported);
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        mBinding.listOfQualities.setAdapter(adapter);

        mBinding.listOfQualities.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);

                mBinding.buttonQuality.setText(item);

                if (item.equals("480p")) {
                    changeVideoQuality(CamcorderProfile.QUALITY_480P);
                } else if (item.equals("720p")) {
                    changeVideoQuality(CamcorderProfile.QUALITY_720P);
                } else if (item.equals("1080p")) {
                    changeVideoQuality(CamcorderProfile.QUALITY_1080P);
                } else if (item.equals("2160p")) {
                    changeVideoQuality(CamcorderProfile.QUALITY_2160P);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mBinding.listOfQualities.animate().setDuration(200).alpha(0)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    mBinding.listOfQualities.setVisibility(View.GONE);
                                }
                            });
                } else {
                    mBinding.listOfQualities.setVisibility(View.GONE);
                }
            }

        });

    }

    View.OnClickListener qualityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!recording) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                        && mBinding.listOfQualities.getVisibility() == View.GONE) {
                    mBinding.listOfQualities.setVisibility(View.VISIBLE);
                    mBinding.listOfQualities.animate().setDuration(200).alpha(95)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                }

                            });
                } else {
                    mBinding.listOfQualities.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    View.OnClickListener flashListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!recording && !cameraFront) {
                if (flash) {
                    flash = false;
                    mBinding.buttonFlash.setImageResource(R.mipmap.ic_flash_off_white);
                    setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                } else {
                    flash = true;
                    mBinding.buttonFlash.setImageResource(R.mipmap.ic_flash_on_white);
                    setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                }
            }
        }
    };


    View.OnClickListener switchCameraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!recording) {
                int camerasNumber = Camera.getNumberOfCameras();
                if (camerasNumber > 1) {
                    releaseCamera();
                    chooseCamera();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.only_have_one_camera
                            , Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


    public void chooseCamera() {
        if (cameraFront) {
            int cameraId = findBackFacingCamera();
            if (cameraId >= 0) {

                mCamera = Camera.open(cameraId);

                mPreview.refreshCamera(mCamera);
                reloadQualities(cameraId);
            }
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId >= 0) {

                mCamera = Camera.open(cameraId);
                if (flash) {
                    flash = false;
                    mBinding.buttonFlash.setImageResource(R.mipmap.ic_flash_off_white);
                    mPreview.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                }
                // mPicture = getPictureCallback();
                mPreview.refreshCamera(mCamera);
                reloadQualities(cameraId);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private boolean hasCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    boolean recording = false;
    View.OnClickListener captrureListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (recording) {
                mediaRecorder.stop();
                stopChronometer();
                mBinding.buttonCapture.setImageResource(R.mipmap.player_record);
                changeRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                releaseMediaRecorder();
                Toast.makeText(CameraActivity.this, R.string.video_captured, Toast.LENGTH_SHORT).show();
                recording = false;
                Intent intent = new Intent();
                intent.putExtra(MainActivity.INTENT_EXTRA_VIDEO_PATH, url_file);
                setResult(MainActivity.RESULT_CODE_FOR_RECORD_VIDEO_SUCCEED, intent);
                releaseCamera();
                releaseMediaRecorder();
                finish();
            } else {
                if (!prepareMediaRecorder()) {
                    Toast.makeText(CameraActivity.this, getString(R.string.camera_init_fail), Toast.LENGTH_SHORT).show();
                    setResult(MainActivity.RESULT_CODE_FOR_RECORD_VIDEO_FAILED);
                    releaseCamera();
                    releaseMediaRecorder();
                    finish();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            mediaRecorder.start();
                            startChronometer();
                            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                                changeRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            } else {
                                changeRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            }
                            mBinding.buttonCapture.setImageResource(R.mipmap.player_stop);
                            timet();
                        } catch (final Exception ex) {
                            Log.i("---", "Exception in thread");
                            setResult(MainActivity.RESULT_CODE_FOR_RECORD_VIDEO_FAILED);
                            releaseCamera();
                            releaseMediaRecorder();
                            finish();
                        }
                    }
                });
                recording = true;
            }
        }

        private void timet() {

            Timer timer = new Timer();
            timer.schedule(task, 1000, 1000);
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                recLen--;
                if (recLen == 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            onKeyDown(1,null);
                            onKeyUp(1,null);
                            File file1 = new File(url_file);
                            if (!file1.exists()) {
                                Looper.prepare();
                                Toast.makeText(CameraActivity.this,getString(R.string.camera_too_long) , Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }


                        }
                    }).start();


                }
            }
        };
    };

    private void changeRequestedOrientation(int orientation) {
        setRequestedOrientation(orientation);
    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            mCamera.lock();
        }
    }

    private boolean prepareMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mediaRecorder.setCamera(mCamera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (cameraFront) {
                mediaRecorder.setOrientationHint(270);
            } else {
                mediaRecorder.setOrientationHint(90);
            }
        }

        mediaRecorder.setProfile(CamcorderProfile.get(quality));

        Date d = new Date();
        String fileName = System.currentTimeMillis() + ".mp4";
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "pigeon";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String savepath = storePath + "/" + fileName;
        url_file = savepath;
        File file1 = new File(url_file);
        if (file1.exists()) {
            file1.delete();
        }

        mediaRecorder.setOutputFile(url_file);
        mediaRecorder.setMaxDuration(CameraConfig.MAX_DURATION_RECORD);
        mediaRecorder.setMaxFileSize(CameraConfig.MAX_FILE_SIZE_RECORD);
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            releaseMediaRecorder();
            return false;
        }
        return true;

    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    private void changeVideoQuality(int quality) {
        SharedPreferences prefs = getSharedPreferences("RECORDING", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("QUALITY", quality);
        editor.commit();
        this.quality = quality;
        updateButtonText(quality);
    }

    private void updateButtonText(int quality) {
        if (quality == CamcorderProfile.QUALITY_480P)
            mBinding.buttonQuality.setText("480p");
        if (quality == CamcorderProfile.QUALITY_720P)
            mBinding.buttonQuality.setText("720p");
        if (quality == CamcorderProfile.QUALITY_1080P)
            mBinding.buttonQuality.setText("1080p");
        if (quality == CamcorderProfile.QUALITY_2160P)
            mBinding.buttonQuality.setText("2160p");
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {
        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    public void setFlashMode(String mode) {
        try {
            if (getPackageManager().hasSystemFeature(
                    PackageManager.FEATURE_CAMERA_FLASH)
                    && mCamera != null
                    && !cameraFront) {

                mPreview.setFlashMode(mode);
                mPreview.refreshCamera(mCamera);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.changing_flashLight_mode,
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void startChronometer() {
        mBinding.textChrono.setVisibility(View.VISIBLE);
        final long startTime = SystemClock.elapsedRealtime();
        mBinding.textChrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer arg0) {
                countUp = (SystemClock.elapsedRealtime() - startTime) / 1000;
                if (countUp % 2 == 0) {
                    mBinding.chronoRecordingImage.setVisibility(View.VISIBLE);
                } else {
                    mBinding.chronoRecordingImage.setVisibility(View.INVISIBLE);
                }

                String asText = String.format("%02d", countUp / 60) + ":" + String.format("%02d", countUp % 60);
                mBinding.textChrono.setText(asText);
            }
        });
        mBinding.textChrono.start();
    }

    private void stopChronometer() {
        mBinding.textChrono.stop();
        mBinding.chronoRecordingImage.setVisibility(View.INVISIBLE);
        mBinding.textChrono.setVisibility(View.INVISIBLE);
    }

    public static void reset() {
        flash = false;
        cameraFront = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (recording) {
            mediaRecorder.stop();
            if (mBinding.textChrono != null && mBinding.textChrono.isActivated())
                mBinding.textChrono.stop();
            releaseMediaRecorder();
            recording = false;
            File mp4 = new File(url_file);
            if (mp4.exists() && mp4.isFile()) {
                mp4.delete();
            }
        }
        setResult(MainActivity.RESULT_CODE_FOR_RECORD_VIDEO_CANCEL);
        releaseCamera();
        releaseMediaRecorder();
        finish();
        return super.onKeyDown(keyCode, event);
    }

    private void focusOnTouch(MotionEvent event) {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            if (parameters.getMaxNumMeteringAreas() > 0) {
                Rect rect = calculateFocusArea(event.getX(), event.getY());
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
                meteringAreas.add(new Camera.Area(rect, 800));
                parameters.setFocusAreas(meteringAreas);
                mCamera.setParameters(parameters);
                mCamera.autoFocus(mAutoFocusTakePictureCallback);
            } else {
                mCamera.autoFocus(mAutoFocusTakePictureCallback);
            }
        }
    }

    private Rect calculateFocusArea(float x, float y) {
        int left = clamp(Float.valueOf((x / mPreview.getWidth()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);
        int top = clamp(Float.valueOf((y / mPreview.getHeight()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);
        return new Rect(left, top, left + FOCUS_AREA_SIZE, top + FOCUS_AREA_SIZE);
    }

    private int clamp(int touchCoordinateInCameraReper, int focusAreaSize) {
        int result;
        if (Math.abs(touchCoordinateInCameraReper) + focusAreaSize / 2 > 1000) {
            if (touchCoordinateInCameraReper > 0) {
                result = 1000 - focusAreaSize / 2;
            } else {
                result = -1000 + focusAreaSize / 2;
            }
        } else {
            result = touchCoordinateInCameraReper - focusAreaSize / 2;
        }
        return result;
    }

    private Camera.AutoFocusCallback mAutoFocusTakePictureCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {
            } else {
            }
        }
    };

}

