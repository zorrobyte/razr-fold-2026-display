package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.RectF;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class C {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f975a = 0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f976b = false;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public float f977c = -1.0f;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public float f978d = -1.0f;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public float f979e = -1.0f;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int[] f980f = new int[0];

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f981g = false;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final TextView f982h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final Context f983i;

    static {
        new RectF();
        new ConcurrentHashMap();
    }

    public C(TextView textView) {
        this.f982h = textView;
        this.f983i = textView.getContext();
    }

    public static int[] a(int[] iArr) {
        int length = iArr.length;
        if (length == 0) {
            return iArr;
        }
        Arrays.sort(iArr);
        ArrayList arrayList = new ArrayList();
        for (int i2 : iArr) {
            if (i2 > 0 && Collections.binarySearch(arrayList, Integer.valueOf(i2)) < 0) {
                arrayList.add(Integer.valueOf(i2));
            }
        }
        if (length == arrayList.size()) {
            return iArr;
        }
        int size = arrayList.size();
        int[] iArr2 = new int[size];
        for (int i3 = 0; i3 < size; i3++) {
            iArr2[i3] = ((Integer) arrayList.get(i3)).intValue();
        }
        return iArr2;
    }

    public final boolean b() {
        if (d() && this.f975a == 1) {
            if (!this.f981g || this.f980f.length == 0) {
                float fRound = Math.round(this.f978d);
                int i2 = 1;
                while (Math.round(this.f977c + fRound) <= Math.round(this.f979e)) {
                    i2++;
                    fRound += this.f977c;
                }
                int[] iArr = new int[i2];
                float f2 = this.f978d;
                for (int i3 = 0; i3 < i2; i3++) {
                    iArr[i3] = Math.round(f2);
                    f2 += this.f977c;
                }
                this.f980f = a(iArr);
            }
            this.f976b = true;
        } else {
            this.f976b = false;
        }
        return this.f976b;
    }

    public final boolean c() {
        boolean z2 = this.f980f.length > 0;
        this.f981g = z2;
        if (z2) {
            this.f975a = 1;
            this.f978d = r0[0];
            this.f979e = r0[r1 - 1];
            this.f977c = -1.0f;
        }
        return z2;
    }

    public final boolean d() {
        return !(this.f982h instanceof AppCompatEditText);
    }

    public final void e(float f2, float f3, float f4) {
        if (f2 <= 0.0f) {
            throw new IllegalArgumentException("Minimum auto-size text size (" + f2 + "px) is less or equal to (0px)");
        }
        if (f3 <= f2) {
            throw new IllegalArgumentException("Maximum auto-size text size (" + f3 + "px) is less or equal to minimum auto-size text size (" + f2 + "px)");
        }
        if (f4 <= 0.0f) {
            throw new IllegalArgumentException("The auto-size step granularity (" + f4 + "px) is less or equal to (0px)");
        }
        this.f975a = 1;
        this.f978d = f2;
        this.f979e = f3;
        this.f977c = f4;
        this.f981g = false;
    }
}
