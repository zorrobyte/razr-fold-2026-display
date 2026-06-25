package com.android.settingslib.graph;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.PathParser;
import com.motorola.taskbar.R$array;
import com.motorola.taskbar.R$bool;
import com.motorola.taskbar.R$color;
import com.motorola.taskbar.R$integer;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.util.Utils;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ThemedBatteryDrawable.kt */
/* JADX INFO: loaded from: classes.dex */
public class ThemedBatteryDrawable extends Drawable {
    public static final Companion Companion = new Companion(null);
    private int backgroundColor;
    private int batteryLevel;
    private final Path boltPath;
    private boolean charging;
    private int[] colorLevels;
    private final Context context;
    private int criticalLevel;
    private boolean dualTone;
    private final Paint dualToneBackgroundFill;
    private final Paint errorPaint;
    private final Path errorPerimeterPath;
    private int fillColor;
    private final Paint fillColorStrokePaint;
    private final Paint fillColorStrokeProtection;
    private final Path fillMask;
    private final Paint fillPaint;
    private final RectF fillRect;
    private int intrinsicHeight;
    private int intrinsicWidth;
    private final Function0 invalidateRunnable;
    private boolean invertFillIcon;
    private int levelColor;
    private final Path levelPath;
    private final RectF levelRect;
    private final Rect padding;
    private final Path perimeterPath;
    private final Path plusPath;
    private boolean powerSaveEnabled;
    private final Matrix scaleMatrix;
    private final Path scaledBolt;
    private final Path scaledErrorPerimeter;
    private final Path scaledFill;
    private final Path scaledPerimeter;
    private final Path scaledPlus;
    private final Path unifiedPath;

    /* JADX INFO: compiled from: ThemedBatteryDrawable.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ThemedBatteryDrawable(Context context, int i) {
        context.getClass();
        this.context = context;
        this.perimeterPath = new Path();
        this.scaledPerimeter = new Path();
        this.errorPerimeterPath = new Path();
        this.scaledErrorPerimeter = new Path();
        this.fillMask = new Path();
        this.scaledFill = new Path();
        this.fillRect = new RectF();
        this.levelRect = new RectF();
        this.levelPath = new Path();
        this.scaleMatrix = new Matrix();
        this.padding = new Rect();
        this.unifiedPath = new Path();
        this.boltPath = new Path();
        this.scaledBolt = new Path();
        this.plusPath = new Path();
        this.scaledPlus = new Path();
        this.fillColor = -65281;
        this.backgroundColor = -65281;
        this.levelColor = -65281;
        this.invalidateRunnable = new Function0() { // from class: com.android.settingslib.graph.ThemedBatteryDrawable$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return ThemedBatteryDrawable.invalidateRunnable$lambda$0(this.f$0);
            }
        };
        this.criticalLevel = context.getResources().getInteger(R$integer.config_criticalBatteryWarningLevel);
        Paint paint = new Paint(1);
        paint.setColor(i);
        paint.setDither(true);
        paint.setStrokeWidth(5.0f);
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        BlendMode blendMode = BlendMode.SRC;
        paint.setBlendMode(blendMode);
        paint.setStrokeMiter(5.0f);
        Paint.Join join = Paint.Join.ROUND;
        paint.setStrokeJoin(join);
        this.fillColorStrokePaint = paint;
        Paint paint2 = new Paint(1);
        paint2.setDither(true);
        paint2.setStrokeWidth(5.0f);
        paint2.setStyle(style);
        paint2.setBlendMode(BlendMode.CLEAR);
        paint2.setStrokeMiter(5.0f);
        paint2.setStrokeJoin(join);
        this.fillColorStrokeProtection = paint2;
        Paint paint3 = new Paint(1);
        paint3.setColor(i);
        paint3.setAlpha(255);
        paint3.setDither(true);
        paint3.setStrokeWidth(0.0f);
        Paint.Style style2 = Paint.Style.FILL_AND_STROKE;
        paint3.setStyle(style2);
        this.fillPaint = paint3;
        Paint paint4 = new Paint(1);
        paint4.setColor(Utils.getColorStateListDefaultColor(context, R$color.batterymeter_plus_color));
        paint4.setAlpha(255);
        paint4.setDither(true);
        paint4.setStrokeWidth(0.0f);
        paint4.setStyle(style2);
        paint4.setBlendMode(blendMode);
        this.errorPaint = paint4;
        Paint paint5 = new Paint(1);
        paint5.setColor(i);
        paint5.setAlpha(255);
        paint5.setDither(true);
        paint5.setStrokeWidth(0.0f);
        paint5.setStyle(style2);
        this.dualToneBackgroundFill = paint5;
        float f = context.getResources().getDisplayMetrics().density;
        this.intrinsicHeight = (int) (20.0f * f);
        this.intrinsicWidth = (int) (f * 12.0f);
        Resources resources = context.getResources();
        TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(R$array.batterymeter_color_levels);
        typedArrayObtainTypedArray.getClass();
        TypedArray typedArrayObtainTypedArray2 = resources.obtainTypedArray(R$array.batterymeter_color_values);
        typedArrayObtainTypedArray2.getClass();
        int length = typedArrayObtainTypedArray.length();
        this.colorLevels = new int[length * 2];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            this.colorLevels[i3] = typedArrayObtainTypedArray.getInt(i2, 0);
            if (typedArrayObtainTypedArray2.getType(i2) == 2) {
                this.colorLevels[i3 + 1] = Utils.getColorAttrDefaultColor(this.context, typedArrayObtainTypedArray2.getThemeAttributeId(i2, 0));
            } else {
                this.colorLevels[i3 + 1] = typedArrayObtainTypedArray2.getColor(i2, 0);
            }
        }
        typedArrayObtainTypedArray.recycle();
        typedArrayObtainTypedArray2.recycle();
        loadPaths();
    }

    private final int batteryColorForLevel(int i) {
        return (this.charging || this.powerSaveEnabled) ? this.fillColor : getColorForLevel(i);
    }

    private final int getColorForLevel(int i) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = this.colorLevels;
            if (i2 >= iArr.length) {
                return i3;
            }
            int i4 = iArr[i2];
            int i5 = iArr[i2 + 1];
            if (i <= i4) {
                return i2 == iArr.length + (-2) ? this.fillColor : i5;
            }
            i2 += 2;
            i3 = i5;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invalidateRunnable$lambda$0(ThemedBatteryDrawable themedBatteryDrawable) {
        themedBatteryDrawable.invalidateSelf();
        return Unit.INSTANCE;
    }

    private final void loadPaths() {
        String string = this.context.getResources().getString(R$string.config_batterymeterPerimeterPath);
        string.getClass();
        this.perimeterPath.set(PathParser.createPathFromPathData(string));
        this.perimeterPath.computeBounds(new RectF(), true);
        String string2 = this.context.getResources().getString(R$string.config_batterymeterErrorPerimeterPath);
        string2.getClass();
        this.errorPerimeterPath.set(PathParser.createPathFromPathData(string2));
        this.errorPerimeterPath.computeBounds(new RectF(), true);
        String string3 = this.context.getResources().getString(R$string.config_batterymeterFillMask);
        string3.getClass();
        this.fillMask.set(PathParser.createPathFromPathData(string3));
        this.fillMask.computeBounds(this.fillRect, true);
        String string4 = this.context.getResources().getString(R$string.config_batterymeterBoltPath);
        string4.getClass();
        this.boltPath.set(PathParser.createPathFromPathData(string4));
        String string5 = this.context.getResources().getString(R$string.config_batterymeterPowersavePath);
        string5.getClass();
        this.plusPath.set(PathParser.createPathFromPathData(string5));
        this.dualTone = this.context.getResources().getBoolean(R$bool.config_batterymeterDualTone);
    }

    private final void postInvalidate() {
        final Function0 function0 = this.invalidateRunnable;
        unscheduleSelf(new Runnable(function0) { // from class: com.android.settingslib.graph.ThemedBatteryDrawable$sam$java_lang_Runnable$0
            private final /* synthetic */ Function0 function;

            {
                function0.getClass();
                this.function = function0;
            }

            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                this.function.mo2224invoke();
            }
        });
        final Function0 function02 = this.invalidateRunnable;
        scheduleSelf(new Runnable(function02) { // from class: com.android.settingslib.graph.ThemedBatteryDrawable$sam$java_lang_Runnable$0
            private final /* synthetic */ Function0 function;

            {
                function02.getClass();
                this.function = function02;
            }

            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                this.function.mo2224invoke();
            }
        }, 0L);
    }

    private final void updateSize() {
        Rect bounds = getBounds();
        bounds.getClass();
        if (bounds.isEmpty()) {
            this.scaleMatrix.setScale(1.0f, 1.0f);
        } else {
            this.scaleMatrix.setScale(bounds.right / 12.0f, bounds.bottom / 20.0f);
        }
        this.perimeterPath.transform(this.scaleMatrix, this.scaledPerimeter);
        this.errorPerimeterPath.transform(this.scaleMatrix, this.scaledErrorPerimeter);
        this.fillMask.transform(this.scaleMatrix, this.scaledFill);
        this.scaledFill.computeBounds(this.fillRect, true);
        this.boltPath.transform(this.scaleMatrix, this.scaledBolt);
        this.plusPath.transform(this.scaleMatrix, this.scaledPlus);
        float fMax = Math.max((bounds.right / 12.0f) * 3.0f, 6.0f);
        this.fillColorStrokePaint.setStrokeWidth(fMax);
        this.fillColorStrokeProtection.setStrokeWidth(fMax);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        float fHeight;
        canvas.getClass();
        canvas.saveLayer(null, null);
        this.unifiedPath.reset();
        this.levelPath.reset();
        this.levelRect.set(this.fillRect);
        int i = this.batteryLevel;
        float f = i / 100.0f;
        if (i >= 95) {
            fHeight = this.fillRect.top;
        } else {
            RectF rectF = this.fillRect;
            fHeight = (rectF.height() * (1 - f)) + rectF.top;
        }
        this.levelRect.top = (float) Math.floor(fHeight);
        this.levelPath.addRect(this.levelRect, Path.Direction.CCW);
        this.unifiedPath.addPath(this.scaledPerimeter);
        if (!this.dualTone) {
            this.unifiedPath.op(this.levelPath, Path.Op.UNION);
        }
        this.fillPaint.setColor(this.levelColor);
        if (this.charging) {
            this.unifiedPath.op(this.scaledBolt, Path.Op.DIFFERENCE);
            if (!this.invertFillIcon) {
                canvas.drawPath(this.scaledBolt, this.fillPaint);
            }
        }
        if (this.dualTone) {
            canvas.drawPath(this.unifiedPath, this.dualToneBackgroundFill);
            canvas.save();
            canvas.clipRect(0.0f, getBounds().bottom - (getBounds().height() * f), getBounds().right, getBounds().bottom);
            canvas.drawPath(this.unifiedPath, this.fillPaint);
            canvas.restore();
        } else {
            this.fillPaint.setColor(this.fillColor);
            canvas.drawPath(this.unifiedPath, this.fillPaint);
            this.fillPaint.setColor(this.levelColor);
            if (this.batteryLevel <= 15 && !this.charging) {
                canvas.save();
                canvas.clipPath(this.scaledFill);
                canvas.drawPath(this.levelPath, this.fillPaint);
                canvas.restore();
            }
        }
        if (this.charging) {
            canvas.clipOutPath(this.scaledBolt);
            if (this.invertFillIcon) {
                canvas.drawPath(this.scaledBolt, this.fillColorStrokePaint);
            } else {
                canvas.drawPath(this.scaledBolt, this.fillColorStrokeProtection);
            }
        } else if (this.powerSaveEnabled) {
            canvas.drawPath(this.scaledErrorPerimeter, this.errorPaint);
            canvas.drawPath(this.scaledPlus, this.errorPaint);
        }
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.intrinsicHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.intrinsicWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        rect.getClass();
        super.onBoundsChange(rect);
        updateSize();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    public void setBatteryLevel(int i) {
        this.invertFillIcon = i >= 67 ? true : i <= 33 ? false : this.invertFillIcon;
        this.batteryLevel = i;
        this.levelColor = batteryColorForLevel(i);
        invalidateSelf();
    }

    public final void setCharging(boolean z) {
        this.charging = z;
        postInvalidate();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.fillPaint.setColorFilter(colorFilter);
        this.fillColorStrokePaint.setColorFilter(colorFilter);
        this.dualToneBackgroundFill.setColorFilter(colorFilter);
    }

    public final void setColors(int i, int i2, int i3) {
        if (!this.dualTone) {
            i = i3;
        }
        this.fillColor = i;
        this.fillPaint.setColor(i);
        this.fillColorStrokePaint.setColor(this.fillColor);
        this.backgroundColor = i2;
        this.dualToneBackgroundFill.setColor(i2);
        this.levelColor = batteryColorForLevel(this.batteryLevel);
        invalidateSelf();
    }

    public final void setPowerSaveEnabled(boolean z) {
        this.powerSaveEnabled = z;
        postInvalidate();
    }
}
