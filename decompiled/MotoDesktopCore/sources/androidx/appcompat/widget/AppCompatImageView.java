package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import b.AbstractC0122a;

/* JADX INFO: loaded from: classes.dex */
public class AppCompatImageView extends ImageView {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final C0080q f942a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final C0084v f943b;

    public AppCompatImageView(Context context) {
        this(context, null, 0);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppCompatImageView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        m0.a(context);
        C0080q c0080q = new C0080q(this);
        this.f942a = c0080q;
        c0080q.d(attributeSet, i2);
        C0084v c0084v = new C0084v(this, 1);
        this.f943b = c0084v;
        c0084v.d(attributeSet, i2);
    }

    @Override // android.widget.ImageView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        C0080q c0080q = this.f942a;
        if (c0080q != null) {
            c0080q.a();
        }
        C0084v c0084v = this.f943b;
        if (c0084v != null) {
            c0084v.a();
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        C0080q c0080q = this.f942a;
        if (c0080q != null) {
            return c0080q.b();
        }
        return null;
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C0080q c0080q = this.f942a;
        if (c0080q != null) {
            return c0080q.c();
        }
        return null;
    }

    public ColorStateList getSupportImageTintList() {
        n0 n0Var;
        C0084v c0084v = this.f943b;
        if (c0084v == null || (n0Var = (n0) c0084v.f1319c) == null) {
            return null;
        }
        return n0Var.f1276a;
    }

    public PorterDuff.Mode getSupportImageTintMode() {
        n0 n0Var;
        C0084v c0084v = this.f943b;
        if (c0084v == null || (n0Var = (n0) c0084v.f1319c) == null) {
            return null;
        }
        return n0Var.f1277b;
    }

    @Override // android.widget.ImageView, android.view.View
    public final boolean hasOverlappingRendering() {
        return ((((ImageView) this.f943b.f1318b).getBackground() instanceof RippleDrawable) ^ true) && super.hasOverlappingRendering();
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C0080q c0080q = this.f942a;
        if (c0080q != null) {
            c0080q.e();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int i2) {
        super.setBackgroundResource(i2);
        C0080q c0080q = this.f942a;
        if (c0080q != null) {
            c0080q.f(i2);
        }
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        C0084v c0084v = this.f943b;
        if (c0084v != null) {
            c0084v.a();
        }
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        C0084v c0084v = this.f943b;
        if (c0084v != null) {
            c0084v.a();
        }
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i2) {
        C0084v c0084v = this.f943b;
        if (c0084v != null) {
            ImageView imageView = (ImageView) c0084v.f1318b;
            if (i2 != 0) {
                Drawable drawableA = AbstractC0122a.a(imageView.getContext(), i2);
                if (drawableA != null) {
                    Rect rect = G.f992a;
                }
                imageView.setImageDrawable(drawableA);
            } else {
                imageView.setImageDrawable(null);
            }
            c0084v.a();
        }
    }

    @Override // android.widget.ImageView
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        C0084v c0084v = this.f943b;
        if (c0084v != null) {
            c0084v.a();
        }
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        C0080q c0080q = this.f942a;
        if (c0080q != null) {
            c0080q.h(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        C0080q c0080q = this.f942a;
        if (c0080q != null) {
            c0080q.i(mode);
        }
    }

    public void setSupportImageTintList(ColorStateList colorStateList) {
        C0084v c0084v = this.f943b;
        if (c0084v != null) {
            if (((n0) c0084v.f1319c) == null) {
                c0084v.f1319c = new n0();
            }
            n0 n0Var = (n0) c0084v.f1319c;
            n0Var.f1276a = colorStateList;
            n0Var.f1279d = true;
            c0084v.a();
        }
    }

    public void setSupportImageTintMode(PorterDuff.Mode mode) {
        C0084v c0084v = this.f943b;
        if (c0084v != null) {
            if (((n0) c0084v.f1319c) == null) {
                c0084v.f1319c = new n0();
            }
            n0 n0Var = (n0) c0084v.f1319c;
            n0Var.f1277b = mode;
            n0Var.f1278c = true;
            c0084v.a();
        }
    }
}
