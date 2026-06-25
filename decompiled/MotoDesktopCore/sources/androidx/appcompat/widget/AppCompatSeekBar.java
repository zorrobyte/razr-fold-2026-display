package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import androidx.appcompat.R$attr;

/* JADX INFO: loaded from: classes.dex */
public class AppCompatSeekBar extends SeekBar {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final C0085w f949a;

    /* JADX WARN: Illegal instructions before constructor call */
    public AppCompatSeekBar(Context context, AttributeSet attributeSet) {
        int i2 = R$attr.seekBarStyle;
        super(context, attributeSet, i2);
        C0085w c0085w = new C0085w(this);
        this.f949a = c0085w;
        c0085w.d(attributeSet, i2);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        C0085w c0085w = this.f949a;
        Drawable drawable = c0085w.f1337f;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        SeekBar seekBar = c0085w.f1336e;
        if (drawable.setState(seekBar.getDrawableState())) {
            seekBar.invalidateDrawable(drawable);
        }
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.f949a.f1337f;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public final synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.f949a.g(canvas);
    }
}
