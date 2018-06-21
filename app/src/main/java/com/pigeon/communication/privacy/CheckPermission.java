package com.pigeon.communication.privacy;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;


public abstract class CheckPermission
{
    private Activity activity;

    public CheckPermission(Activity activity)
    {
        this.activity = activity;
    }

    //存储
    public static final int REQUEST_CODE_PERMISSION_STORAGE = 100;
    //相机
    public static final int REQUEST_CODE_PERMISSION_CAMERA = 101;



    public void permission(int permissionType)
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            switch (permissionType)
            {
                case REQUEST_CODE_PERMISSION_STORAGE:
                    AndPermission.with(activity)
                            .requestCode(REQUEST_CODE_PERMISSION_STORAGE)
                            .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE)
                            .callback(permissionListener)

                            .rationale(new RationaleListener()
                            {
                                @Override
                                public void showRequestPermissionRationale(int requestCode, Rationale rationale)
                                {
                                    AndPermission.rationaleDialog(activity, rationale)
                                            .show();
                                }
                            })
                            .start();
                    break;
                case REQUEST_CODE_PERMISSION_CAMERA:
                    AndPermission.with(activity)
                            .requestCode(REQUEST_CODE_PERMISSION_CAMERA)
                            .permission(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                            .callback(permissionListener)

                            .rationale(new RationaleListener()
                            {
                                @Override
                                public void showRequestPermissionRationale(int requestCode, Rationale rationale)
                                {
                                    AndPermission.rationaleDialog(activity, rationale)
                                            .show();
                                }
                            })
                            .start();
                    break;
            }
        }
    }


    private PermissionListener permissionListener = new PermissionListener()
    {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions)
        {
            permissionSuccess();
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions)
        {
            String title = activity.getString(R.string.permission_request_error);
            String message = "请您到设置页面手动授权，否则功能无法正常使用！";
            switch (requestCode)
            {
                case REQUEST_CODE_PERMISSION_STORAGE:
                    message = activity.getString(R.string.permission_storage);
                    break;
                case REQUEST_CODE_PERMISSION_CAMERA:
                    message = activity.getString(R.string.permission_image);
                    break;
            }

            if (AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions))
            {
                AndPermission.defaultSettingDialog(activity, requestCode)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("好，去设置")
                        .setNegativeButton(activity.getString(R.string.cancel_value), new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                negativeButton();
                            }
                        })
                        .show();
            }
        }
    };

    public abstract void permissionSuccess();


    public void negativeButton()
    {
        activity.finish();
    }
}
