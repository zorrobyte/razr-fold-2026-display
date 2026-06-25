package F;

import android.view.View;
import java.util.Comparator;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class c implements Comparator {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f118a;

    public /* synthetic */ c(int i2) {
        this.f118a = i2;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.f118a) {
            case 0:
                return ((h) obj).f126b - ((h) obj2).f126b;
            case 1:
                WeakHashMap weakHashMap = v.l.f2836a;
                float z2 = ((View) obj).getZ();
                float z3 = ((View) obj2).getZ();
                if (z2 > z3) {
                    return -1;
                }
                return z2 < z3 ? 1 : 0;
            default:
                byte[] bArr = (byte[]) obj;
                byte[] bArr2 = (byte[]) obj2;
                if (bArr.length != bArr2.length) {
                    return bArr.length - bArr2.length;
                }
                for (int i2 = 0; i2 < bArr.length; i2++) {
                    byte b2 = bArr[i2];
                    byte b3 = bArr2[i2];
                    if (b2 != b3) {
                        return b2 - b3;
                    }
                }
                return 0;
        }
    }
}
