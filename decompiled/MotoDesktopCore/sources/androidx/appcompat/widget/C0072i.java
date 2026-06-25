package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.appcompat.R$attr;
import androidx.appcompat.view.menu.C0058b;

/* JADX INFO: renamed from: androidx.appcompat.widget.i, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0072i extends AppCompatImageView implements InterfaceC0075l {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ C0074k f1213c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0072i(C0074k c0074k, Context context) {
        super(context, null, R$attr.actionOverflowButtonStyle);
        this.f1213c = c0074k;
        setClickable(true);
        setFocusable(true);
        setVisibility(0);
        setEnabled(true);
        setTooltipText(getContentDescription());
        setOnTouchListener(new C0058b(this, this));
    }

    @Override // androidx.appcompat.widget.InterfaceC0075l
    public final boolean a() {
        return false;
    }

    @Override // androidx.appcompat.widget.InterfaceC0075l
    public final boolean b() {
        return false;
    }

    @Override // android.view.View
    public final boolean performClick() {
        if (super.performClick()) {
            return true;
        }
        playSoundEffect(0);
        this.f1213c.l();
        return true;
    }

    @Override // android.widget.ImageView
    public final boolean setFrame(int i2, int i3, int i4, int i5) {
        boolean frame = super.setFrame(i2, i3, i4, i5);
        Drawable drawable = getDrawable();
        Drawable background = getBackground();
        if (drawable != null && background != null) {
            int width = getWidth();
            int height = getHeight();
            int iMax = Math.max(width, height) / 2;
            int paddingLeft = (width + (getPaddingLeft() - getPaddingRight())) / 2;
            int paddingTop = (height + (getPaddingTop() - getPaddingBottom())) / 2;
            background.setHotspotBounds(paddingLeft - iMax, paddingTop - iMax, paddingLeft + iMax, paddingTop + iMax);
        }
        return frame;
    }
}
