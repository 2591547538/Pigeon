package com.pigeon.communication.privacy;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.List;



public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {



    private SurfaceHolder mHolder;
    private Camera mCamera;

    private Context mContext;

    private String flashMode = Camera.Parameters.FLASH_MODE_OFF;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        mContext = context;

        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (mCamera != null) {
                mCamera.setPreviewDisplay(holder);
                optimizeCameraDimens(mCamera);
                mCamera.startPreview();
            }
        } catch (IOException e) {
        }
    }

    public void refreshCamera(Camera camera) {
        if (mHolder.getSurface() == null) {
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (camera != null) {
                camera.setDisplayOrientation(90);
            }
        }


        setCamera(camera);
        try {
            mCamera.setPreviewDisplay(mHolder);

            optimizeCameraDimens(mCamera);

            mCamera.startPreview();
        } catch (Exception e) {
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        refreshCamera(mCamera);
    }

    public void setCamera(Camera camera) {
        mCamera = camera;

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void optimizeCameraDimens(Camera mCamera) {
        final int width = Screens.getScreenWidth(mContext);
        final int height = Screens.getScreenHeight(mContext);

        final List<Camera.Size> mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();


        Camera.Size mPreviewSize;
        if (mSupportedPreviewSizes != null) {
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
            float ratio;
            if (mPreviewSize.height >= mPreviewSize.width)
                ratio = (float) mPreviewSize.height / (float) mPreviewSize.width;
            else
                ratio = (float) mPreviewSize.width / (float) mPreviewSize.height;

            setMeasuredDimension(width, (int) (width * ratio));

            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            parameters.setFlashMode(flashMode);
            mCamera.setParameters(parameters);
        }
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;

        if (w > h)
            targetRatio = (double) w / h;

        if (sizes == null)
            return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {

            double ratio = (double) size.width / size.height;
            if (size.height >= size.width)
                ratio = (float) size.height / size.width;

            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;

            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }

        return optimalSize;
    }

    public void setFlashMode(String mode) {
        flashMode = mode;
    }
}