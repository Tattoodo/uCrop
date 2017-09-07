package com.yalantis.ucrop.model;

import android.graphics.Bitmap;
import android.graphics.RectF;

/**
 * Created by Oleksii Shliama [https://github.com/shliama] on 6/21/16.
 */
public class ImageState {

    private Bitmap mBitmap;
    private RectF mCropRect;
    private RectF mCurrentImageRect;

    private float mCurrentScale, mCurrentAngle;

    public ImageState(Bitmap bitmap, RectF cropRect, RectF currentImageRect, float currentScale, float currentAngle) {
        mBitmap = bitmap;
        mCropRect = cropRect;
        mCurrentImageRect = currentImageRect;
        mCurrentScale = currentScale;
        mCurrentAngle = currentAngle;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public RectF getCropRect() {
        return mCropRect;
    }

    public RectF getCurrentImageRect() {
        return mCurrentImageRect;
    }

    public float getCurrentScale() {
        return mCurrentScale;
    }

    public float getCurrentAngle() {
        return mCurrentAngle;
    }
}
