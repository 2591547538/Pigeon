package com.pigeon.communication.privacy;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class He extends AppCompatActivity implements View.OnClickListener
{ImageView i;
    Uri uri;
    Bitmap b;
    public static final String FILE_CONTENT_FILEPROVIDER = "com.example.bao.cropimage.fileprovider";

   public static final String APP_NAME = "bao";

    public static final int REQUEST_CODE_TAKE_PHOTO = 11111;

    public static final int REQUEST_CODE_SELECT_PICTURE = 11112;
    public static final int REQUEST_CODE_CROP_PICTURE = 11113;



    public String DATE = "";

    private String photo_image;


    private CheckPermission checkPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hew);


        findViewById(R.id.btn_album).setOnClickListener(this);
i= (ImageView) findViewById(R.id.image_result);
        checkPermission = new CheckPermission(this)
        {
            @Override
            public void permissionSuccess()
            {
                takePhoto();
            }

            @Override
            public void negativeButton()
            {

                Toast.makeText(He.this, getString(R.string.Recording), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void takePhoto() {
        DATE = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date());
        if (isSdCardExist())
        {
            photo_image = createImagePath(APP_NAME + DATE);
            File file = new File(photo_image);
            if (!file.getParentFile().exists())
            {
                file.getParentFile().mkdirs();
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Android7.0以上URI
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {

                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                uri = FileProvider.getUriForFile(this, FILE_CONTENT_FILEPROVIDER, file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            }
            try
            {
                this.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
            } catch (ActivityNotFoundException anf)
            {
                Toast.makeText(He.this, getString(R.string.camera_not_prepared), Toast.LENGTH_SHORT).show();

            }
        } else
        {
            Toast.makeText(He.this, getString(R.string.sdcard_no_exist), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {


            case R.id.btn_album:
                openAlbum();
                break;
        }
    }

    private void openAlbum() {
        DATE = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date());
        if (isSdCardExist())
        {
            Intent intent;
            if (Build.VERSION.SDK_INT < 19)
            {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
            } else
            {
                intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            startActivityForResult(intent, REQUEST_CODE_SELECT_PICTURE);
        } else
        {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_TAKE_PHOTO://
                if (!TextUtils.isEmpty(photo_image))
                {
                    uri = data.getData();

                     b= getBitmapFromUri(uri,this);
                    i.setImageBitmap(b);
                        }

                break;

            case REQUEST_CODE_SELECT_PICTURE:
                if (data != null)
                {
                    Uri uri = data.getData();
                    if (uri != null)
                    {
                        uri = data.getData();

                         b= getBitmapFromUri(uri,this);
                        i.setImageBitmap(b);
                    }
                }
                break;
            case REQUEST_CODE_CROP_PICTURE:

                break;

        }
    }


    public  Bitmap getBitmapFromUri(Uri uri, Context mContext) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void send7(View v){
        String fileName = System.currentTimeMillis() + ".jpeg";

        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "pigeon";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String savepath = storePath + "/" + fileName;

       // String path = GetPathFromUri.getInstance().getPath(this, uri);
if(b==null){
              Toast.makeText(He.this, getString(R.string.No_images), Toast.LENGTH_SHORT).show();

}else{
    File saveii=new File(savepath);
    if (!saveii.exists()) {
        saveii.getParentFile().mkdirs();

        try {
            saveii.createNewFile();
            FileOutputStream fos = new FileOutputStream(saveii);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Intent i = new Intent();
            i.putExtra("bian", savepath);
            setResult(20, i);
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
    }
    public boolean isSdCardExist()
    {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
    public static String createImagePath(String imageName)
    {
         String PICTURE_DIR = Environment.getExternalStorageDirectory()
                + File.separator + "pictures" + File.separator + "cropUtils";
        String dir = PICTURE_DIR;
        File destDir = new File(dir);
        if (!destDir.exists())
        {
            destDir.mkdirs();
        }
        File file = null;
        if (!TextUtils.isEmpty(imageName))
        {
            file = new File(dir, imageName + ".jpeg");
        }
        return file.getAbsolutePath();
    }

}
