package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import androidx.appcompat.R$styleable;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: androidx.appcompat.widget.w, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0085w extends C0084v {

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final SeekBar f1336e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public Drawable f1337f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public ColorStateList f1338g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public PorterDuff.Mode f1339h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public boolean f1340i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public boolean f1341j;

    public C0085w(SeekBar seekBar) {
        super(seekBar, 0);
        this.f1338g = null;
        this.f1339h = null;
        this.f1340i = false;
        this.f1341j = false;
        this.f1336e = seekBar;
    }

    @Override // androidx.appcompat.widget.C0084v
    public final void d(AttributeSet attributeSet, int i2) {
        super.d(attributeSet, i2);
        SeekBar seekBar = this.f1336e;
        f0.b bVarM = f0.b.m(seekBar.getContext(), attributeSet, R$styleable.AppCompatSeekBar, i2, 0);
        Drawable drawableG = bVarM.g(R$styleable.AppCompatSeekBar_android_thumb);
        if (drawableG != null) {
            seekBar.setThumb(drawableG);
        }
        Drawable drawableF = bVarM.f(R$styleable.AppCompatSeekBar_tickMark);
        Drawable drawable = this.f1337f;
        if (drawable != null) {
            drawable.setCallback(null);
        }
        this.f1337f = drawableF;
        if (drawableF != null) {
            drawableF.setCallback(seekBar);
            WeakHashMap weakHashMap = v.l.f2836a;
            drawableF.setLayoutDirection(seekBar.getLayoutDirection());
            if (drawableF.isStateful()) {
                drawableF.setState(seekBar.getDrawableState());
            }
            f();
        }
        seekBar.invalidate();
        int i3 = R$styleable.AppCompatSeekBar_tickMarkTintMode;
        TypedArray typedArray = (TypedArray) bVarM.f2538c;
        if (typedArray.hasValue(i3)) {
            this.f1339h = G.c(typedArray.getInt(R$styleable.AppCompatSeekBar_tickMarkTintMode, -1), this.f1339h);
            this.f1341j = true;
        }
        if (typedArray.hasValue(R$styleable.AppCompatSeekBar_tickMarkTint)) {
            this.f1338g = bVarM.e(R$styleable.AppCompatSeekBar_tickMarkTint);
            this.f1340i = true;
        }
        bVarM.n();
        f();
    }

    public final void f() {
        Drawable drawable = this.f1337f;
        if (drawable != null) {
            if (this.f1340i || this.f1341j) {
                Drawable drawableMutate = drawable.mutate();
                this.f1337f = drawableMutate;
                if (this.f1340i) {
                    drawableMutate.setTintList(this.f1338g);
                }
                if (this.f1341j) {
                    this.f1337f.setTintMode(this.f1339h);
                }
                if (this.f1337f.isStateful()) {
                    this.f1337f.setState(this.f1336e.getDrawableState());
                }
            }
        }
    }

    public final void g(Canvas canvas) {
        if (this.f1337f != null) {
            int max = this.f1336e.getMax();
            if (max > 1) {
                int intrinsicWidth = this.f1337f.getIntrinsicWidth();
                int intrinsicHeight = this.f1337f.getIntrinsicHeight();
                int i2 = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
                int i3 = intrinsicHeight >= 0 ? intrinsicHeight / 2 : 1;
                this.f1337f.setBounds(-i2, -i3, i2, i3);
                float width = ((r0.getWidth() - r0.getPaddingLeft()) - r0.getPaddingRight()) / max;
                int iSave = canvas.save();
                canvas.translate(r0.getPaddingLeft(), r0.getHeight() / 2);
                for (int i4 = 0; i4 <= max; i4++) {
                    this.f1337f.draw(canvas);
                    canvas.translate(width, 0.0f);
                }
                canvas.restoreToCount(iSave);
            }
        }
    }
}
