package com.kartega.ophelia.ea_utilities.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.kartega.ophelia.ea_utilities.interfaces.CompressListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Ahmet Kılıç on 2019-06-19.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public class CompressImageTask extends AsyncTask<Void, Void, String> {

    private int desiredWidth = 1280;
    private int desiredHeight = 720;
    private int desiredQuality = 70;

    private String folderName = "compressed_images";
    private String fileNameExtra = "_compressed";

    private String path;
    private CompressListener compressListener;

    public CompressImageTask(String path, CompressListener compressListener) {
        this.path = path;
        this.compressListener = compressListener;
    }

    public void setDesiredWidth(int desiredWidth) {
        this.desiredWidth = desiredWidth;
    }

    public void setDesiredHeight(int desiredHeight) {
        this.desiredHeight = desiredHeight;
    }

    public void setDesiredQuality(int desiredQuality) {
        this.desiredQuality = desiredQuality;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void setFileNameExtra(String fileNameExtra) {
        this.fileNameExtra = fileNameExtra;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return compressImage(path);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (compressListener != null)
            compressListener.onCompleted(s);
    }

    private String compressImage(String path) {
        String name = FileUtils.getNameFromPath(path);
        String extension = FileUtils.getExtensionFromPath(path);

        Bitmap unscaledBitmap = decodeFile(path);
        Bitmap scaledBitmap = createScaledBitmap(unscaledBitmap);

        File newFile = new File(getTempImageFolder().getAbsolutePath(), name + fileNameExtra + "." + extension);


        String strMyImagePath = newFile.getAbsolutePath();
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(newFile);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, desiredQuality, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }

        scaledBitmap.recycle();

        if (strMyImagePath == null) {
            return path;
        }
        return strMyImagePath;
    }

    public void deleteTempImages() {
        FileUtils.deleteFileOrFolder(getTempImageFolder());
    }

    public File getTempImageFolder() {
        File mFolder = new File(Environment.getExternalStorageDirectory() + File.separator + folderName);
        if (!mFolder.exists()) {
            if (mFolder.mkdir())
                Log.v("FolderController", "folder created!");
        }
        return mFolder;
    }

    private Bitmap decodeFile(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight);

        return BitmapFactory.decodeFile(path, options);
    }

    private Bitmap createScaledBitmap(Bitmap unscaledBitmap) {
        Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight());
        Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight());
        Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }

    private int calculateSampleSize(int srcWidth, int srcHeight) {
        {
            final float srcAspect = (float) srcWidth / (float) srcHeight;
            final float dstAspect = (float) desiredWidth / (float) desiredHeight;

            if (srcAspect > dstAspect) {
                return srcWidth / desiredWidth;
            } else {
                return srcHeight / desiredHeight;
            }
        }
    }

    private Rect calculateSrcRect(int srcWidth, int srcHeight) {
        return new Rect(0, 0, srcWidth, srcHeight);
    }

    private Rect calculateDstRect(int srcWidth, int srcHeight) {
        final float srcAspect = (float) srcWidth / (float) srcHeight;
        final float dstAspect = (float) desiredWidth / (float) desiredHeight;

        if (srcAspect > dstAspect) {
            return new Rect(0, 0, desiredWidth, (int) (desiredWidth / srcAspect));
        } else {
            return new Rect(0, 0, (int) (desiredHeight * srcAspect), desiredHeight);
        }
    }
}