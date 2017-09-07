package com.yalantis.ucrop.util;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;

import com.yalantis.ucrop.model.ImageState;

public class CropUtils {

    /**
     * Crops the ImageState's Bitmap based on its arguments.
     *
     * @return Cropped Bitmap
     */
    public static Bitmap crop(ImageState imageState) {

        float currentAngle = imageState.getCurrentAngle();
        Bitmap bitmap = imageState.getBitmap();

        // Rotate if needed
        if (currentAngle != 0) {
            Matrix tempMatrix = new Matrix();
            tempMatrix.setRotate(currentAngle, bitmap.getWidth() / 2, bitmap.getHeight() / 2);

            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                    tempMatrix, true);
            if (bitmap != rotatedBitmap) {
                bitmap.recycle();
            }
            bitmap = rotatedBitmap;
        }

        RectF cropRect = imageState.getCropRect();
        RectF currentImageRect = imageState.getCurrentImageRect();
        float currentScale = imageState.getCurrentScale();

        int cropOffsetX = Math.round((cropRect.left - currentImageRect.left) / currentScale);
        int cropOffsetY = Math.round((cropRect.top - currentImageRect.top) / currentScale);
        int croppedImageWidth = Math.round(cropRect.width() / currentScale);
        int croppedImageHeight = Math.round(cropRect.height() / currentScale);

        boolean shouldCrop = shouldCrop(croppedImageWidth, croppedImageHeight, cropRect, currentImageRect);

        if (shouldCrop) {
            return Bitmap.createBitmap(bitmap, cropOffsetX, cropOffsetY,
                    croppedImageWidth, croppedImageHeight);
        } else {
            return bitmap;
        }
    }


    /**
     * Check whether an image should be cropped at all or just file can be copied to the destination path.
     * For each 1000 pixels there is one pixel of error due to matrix calculations etc.
     *
     * @return - true if image must be cropped, false - if original image fits requirements
     */
    private static boolean shouldCrop(int width, int height, RectF cropRect,
            RectF currentImageRect) {
        int pixelError = 1;
        pixelError += Math.round(Math.max(width, height) / 1000f);
        return  Math.abs(cropRect.left - currentImageRect.left) > pixelError
                || Math.abs(cropRect.top - currentImageRect.top) > pixelError
                || Math.abs(cropRect.bottom - currentImageRect.bottom) > pixelError
                || Math.abs(cropRect.right - currentImageRect.right) > pixelError;
    }
}
