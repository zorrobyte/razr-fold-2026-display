package com.android.launcher3.icons.mono;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.launcher3.icons.R$color;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ThemedIconDrawable.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ThemedIconDrawable extends FastBitmapDrawable {
    public static final Companion Companion = new Companion(null);
    private final Bitmap bgBitmap;
    private final BlendModeColorFilter bgFilter;
    private final int colorBg;
    private final int colorFg;
    private final Paint mBgPaint;
    private final BlendModeColorFilter monoFilter;
    private final Bitmap monoIcon;
    private final Paint monoPaint;

    /* JADX INFO: compiled from: ThemedIconDrawable.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int[] getColors(Context context) {
            context.getClass();
            Resources resources = context.getResources();
            return new int[]{resources.getColor(R$color.themed_icon_background_color), resources.getColor(R$color.themed_icon_color)};
        }
    }

    /* JADX INFO: compiled from: ThemedIconDrawable.kt */
    public final class ThemedConstantState extends FastBitmapDrawable.FastBitmapConstantState {
        private final int colorBg;
        private final int colorFg;
        private final Bitmap mono;
        private final Bitmap whiteShadowLayer;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ThemedConstantState(BitmapInfo bitmapInfo, Bitmap bitmap, Bitmap bitmap2, int i, int i2) {
            super(bitmapInfo);
            bitmapInfo.getClass();
            bitmap.getClass();
            bitmap2.getClass();
            this.mono = bitmap;
            this.whiteShadowLayer = bitmap2;
            this.colorBg = i;
            this.colorFg = i2;
        }

        @Override // com.android.launcher3.icons.FastBitmapDrawable.FastBitmapConstantState
        public ThemedIconDrawable createDrawable() {
            return new ThemedIconDrawable(this);
        }

        public final BitmapInfo getBitmapInfo() {
            BitmapInfo bitmapInfo = this.mBitmapInfo;
            bitmapInfo.getClass();
            return bitmapInfo;
        }

        public final int getColorBg() {
            return this.colorBg;
        }

        public final int getColorFg() {
            return this.colorFg;
        }

        public final Bitmap getMono() {
            return this.mono;
        }

        public final Bitmap getWhiteShadowLayer() {
            return this.whiteShadowLayer;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThemedIconDrawable(ThemedConstantState themedConstantState) {
        super(themedConstantState.getBitmapInfo());
        themedConstantState.getClass();
        int colorFg = themedConstantState.getColorFg();
        this.colorFg = colorFg;
        int colorBg = themedConstantState.getColorBg();
        this.colorBg = colorBg;
        this.monoIcon = themedConstantState.getMono();
        BlendMode blendMode = BlendMode.SRC_IN;
        BlendModeColorFilter blendModeColorFilter = new BlendModeColorFilter(colorFg, blendMode);
        this.monoFilter = blendModeColorFilter;
        Paint paint = new Paint(3);
        paint.setColorFilter(blendModeColorFilter);
        this.monoPaint = paint;
        this.bgBitmap = themedConstantState.getWhiteShadowLayer();
        BlendModeColorFilter blendModeColorFilter2 = new BlendModeColorFilter(colorBg, blendMode);
        this.bgFilter = blendModeColorFilter2;
        Paint paint2 = new Paint(3);
        paint2.setColorFilter(blendModeColorFilter2);
        this.mBgPaint = paint2;
    }

    public static final int[] getColors(Context context) {
        return Companion.getColors(context);
    }

    @Override // com.android.launcher3.icons.FastBitmapDrawable
    protected void drawInternal(Canvas canvas, Rect rect) {
        canvas.getClass();
        rect.getClass();
        canvas.drawBitmap(this.bgBitmap, (Rect) null, rect, this.mBgPaint);
        canvas.drawBitmap(this.monoIcon, (Rect) null, rect, this.monoPaint);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.launcher3.icons.FastBitmapDrawable
    public ThemedConstantState newConstantState() {
        BitmapInfo bitmapInfo = this.mBitmapInfo;
        bitmapInfo.getClass();
        return new ThemedConstantState(bitmapInfo, this.monoIcon, this.bgBitmap, this.colorBg, this.colorFg);
    }

    @Override // com.android.launcher3.icons.FastBitmapDrawable
    protected void updateFilter() {
        super.updateFilter();
        int i = this.mIsDisabled ? (int) (this.mDisabledAlpha * 255) : 255;
        this.mBgPaint.setAlpha(i);
        this.mBgPaint.setColorFilter(this.mIsDisabled ? new BlendModeColorFilter(FastBitmapDrawable.getDisabledColor(this.colorBg), BlendMode.SRC_IN) : this.bgFilter);
        this.monoPaint.setAlpha(i);
        this.monoPaint.setColorFilter(this.mIsDisabled ? new BlendModeColorFilter(FastBitmapDrawable.getDisabledColor(this.colorFg), BlendMode.SRC_IN) : this.monoFilter);
    }
}
