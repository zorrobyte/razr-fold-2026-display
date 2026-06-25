package androidx.constraintlayout.widget;

import android.graphics.Canvas;
import android.view.View;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public abstract class ConstraintHelper extends View {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int[] f1358a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f1359b;

    private void setIds(String str) {
        if (str == null) {
            return;
        }
        int i2 = 0;
        while (true) {
            int iIndexOf = str.indexOf(44, i2);
            if (iIndexOf == -1) {
                a(str.substring(i2));
                return;
            } else {
                a(str.substring(i2, iIndexOf));
                i2 = iIndexOf + 1;
            }
        }
    }

    public final void a(String str) {
    }

    public int[] getReferencedIds() {
        return Arrays.copyOf(this.f1358a, this.f1359b);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
    }

    @Override // android.view.View
    public final void onMeasure(int i2, int i3) {
        setMeasuredDimension(0, 0);
    }

    public void setReferencedIds(int[] iArr) {
        this.f1359b = 0;
        for (int i2 : iArr) {
            setTag(i2, null);
        }
    }

    @Override // android.view.View
    public final void setTag(int i2, Object obj) {
        int i3 = this.f1359b + 1;
        int[] iArr = this.f1358a;
        if (i3 > iArr.length) {
            this.f1358a = Arrays.copyOf(iArr, iArr.length * 2);
        }
        int[] iArr2 = this.f1358a;
        int i4 = this.f1359b;
        iArr2[i4] = i2;
        this.f1359b = i4 + 1;
    }
}
