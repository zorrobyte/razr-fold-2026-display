package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.appcompat.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class M extends ViewGroup.MarginLayoutParams {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final float f1034a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f1035b;

    public M(int i2, int i3) {
        super(i2, i3);
        this.f1035b = -1;
        this.f1034a = 0.0f;
    }

    public M(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f1035b = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.LinearLayoutCompat_Layout);
        this.f1034a = typedArrayObtainStyledAttributes.getFloat(R$styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
        this.f1035b = typedArrayObtainStyledAttributes.getInt(R$styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
        typedArrayObtainStyledAttributes.recycle();
    }

    public M(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
        this.f1035b = -1;
    }
}
