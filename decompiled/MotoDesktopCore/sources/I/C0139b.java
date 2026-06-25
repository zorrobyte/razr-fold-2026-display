package i;

import androidx.appcompat.app.f;
import java.util.ArrayList;

/* JADX INFO: renamed from: i.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0139b extends C0138a {

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public f f2627k = this.f2618b;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public int f2628l = 0;

    public C0139b() {
        this.f2620d.clear();
        this.f2620d.add(this.f2627k);
        int length = this.f2619c.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.f2619c[i2] = this.f2627k;
        }
    }

    public final void c(int i2) {
        if (this.f2628l == i2) {
            return;
        }
        this.f2628l = i2;
        ArrayList arrayList = this.f2620d;
        arrayList.clear();
        if (this.f2628l == 1) {
            this.f2627k = this.f2617a;
        } else {
            this.f2627k = this.f2618b;
        }
        arrayList.add(this.f2627k);
        f[] fVarArr = this.f2619c;
        int length = fVarArr.length;
        for (int i3 = 0; i3 < length; i3++) {
            fVarArr[i3] = this.f2627k;
        }
    }
}
