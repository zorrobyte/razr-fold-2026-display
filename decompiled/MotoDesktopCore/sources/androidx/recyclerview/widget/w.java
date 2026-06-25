package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public class w extends ViewGroup.MarginLayoutParams {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Rect f1919a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f1920b;

    public w(int i2, int i3) {
        super(i2, i3);
        this.f1919a = new Rect();
        this.f1920b = true;
    }

    public w(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f1919a = new Rect();
        this.f1920b = true;
    }

    public w(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
        this.f1919a = new Rect();
        this.f1920b = true;
    }

    public w(ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
        this.f1919a = new Rect();
        this.f1920b = true;
    }

    public w(w wVar) {
        super((ViewGroup.LayoutParams) wVar);
        this.f1919a = new Rect();
        this.f1920b = true;
    }
}
