package com.airbnb.lottie.model.layer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.DropShadowKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;

/* JADX INFO: loaded from: classes.dex */
public class ImageLayer extends BaseLayer {
    private BaseKeyframeAnimation colorFilterAnimation;
    private DropShadowKeyframeAnimation dropShadowAnimation;
    private final Rect dst;
    private BaseKeyframeAnimation imageAnimation;
    private final LottieImageAsset lottieImageAsset;
    private final Paint paint;
    private final Rect src;

    ImageLayer(LottieDrawable lottieDrawable, Layer layer) {
        super(lottieDrawable, layer);
        this.paint = new LPaint(3);
        this.src = new Rect();
        this.dst = new Rect();
        this.lottieImageAsset = lottieDrawable.getLottieImageAssetForId(layer.getRefId());
        if (getDropShadowEffect() != null) {
            this.dropShadowAnimation = new DropShadowKeyframeAnimation(this, this, getDropShadowEffect());
        }
    }

    private Bitmap getBitmap() {
        Bitmap bitmap;
        BaseKeyframeAnimation baseKeyframeAnimation = this.imageAnimation;
        if (baseKeyframeAnimation != null && (bitmap = (Bitmap) baseKeyframeAnimation.getValue()) != null) {
            return bitmap;
        }
        Bitmap bitmapForId = this.lottieDrawable.getBitmapForId(this.layerModel.getRefId());
        if (bitmapForId != null) {
            return bitmapForId;
        }
        LottieImageAsset lottieImageAsset = this.lottieImageAsset;
        if (lottieImageAsset != null) {
            return lottieImageAsset.getBitmap();
        }
        return null;
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.KeyPathElement
    public void addValueCallback(Object obj, LottieValueCallback lottieValueCallback) {
        super.addValueCallback(obj, lottieValueCallback);
        if (obj == LottieProperty.COLOR_FILTER) {
            if (lottieValueCallback == null) {
                this.colorFilterAnimation = null;
                return;
            } else {
                this.colorFilterAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback);
                return;
            }
        }
        if (obj == LottieProperty.IMAGE) {
            if (lottieValueCallback == null) {
                this.imageAnimation = null;
            } else {
                this.imageAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            }
        }
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    public void drawLayer(Canvas canvas, Matrix matrix, int i) {
        Bitmap bitmap = getBitmap();
        if (bitmap == null || bitmap.isRecycled() || this.lottieImageAsset == null) {
            return;
        }
        float fDpScale = Utils.dpScale();
        this.paint.setAlpha(i);
        BaseKeyframeAnimation baseKeyframeAnimation = this.colorFilterAnimation;
        if (baseKeyframeAnimation != null) {
            this.paint.setColorFilter((ColorFilter) baseKeyframeAnimation.getValue());
        }
        canvas.save();
        canvas.concat(matrix);
        this.src.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        if (this.lottieDrawable.getMaintainOriginalImageBounds()) {
            this.dst.set(0, 0, (int) (this.lottieImageAsset.getWidth() * fDpScale), (int) (this.lottieImageAsset.getHeight() * fDpScale));
        } else {
            this.dst.set(0, 0, (int) (bitmap.getWidth() * fDpScale), (int) (bitmap.getHeight() * fDpScale));
        }
        DropShadowKeyframeAnimation dropShadowKeyframeAnimation = this.dropShadowAnimation;
        if (dropShadowKeyframeAnimation != null) {
            dropShadowKeyframeAnimation.applyTo(this.paint, matrix, i);
        }
        canvas.drawBitmap(bitmap, this.src, this.dst, this.paint);
        canvas.restore();
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.animation.content.DrawingContent
    public void getBounds(RectF rectF, Matrix matrix, boolean z) {
        super.getBounds(rectF, matrix, z);
        if (this.lottieImageAsset != null) {
            float fDpScale = Utils.dpScale();
            rectF.set(0.0f, 0.0f, this.lottieImageAsset.getWidth() * fDpScale, this.lottieImageAsset.getHeight() * fDpScale);
            this.boundsMatrix.mapRect(rectF);
        }
    }
}
