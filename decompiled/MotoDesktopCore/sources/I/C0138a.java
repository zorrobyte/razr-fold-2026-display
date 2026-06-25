package i;

import androidx.appcompat.app.f;
import java.util.ArrayList;

/* JADX INFO: renamed from: i.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0138a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final f f2617a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final f f2618b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final f[] f2619c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final ArrayList f2620d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final int f2621e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final int f2622f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final int f2623g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final int f2624h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final int f2625i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final String f2626j;

    public C0138a() {
        f fVar = new f(this, 2);
        this.f2617a = fVar;
        f fVar2 = new f(this, 3);
        this.f2618b = fVar2;
        f fVar3 = new f(this, 4);
        f fVar4 = new f(this, 5);
        f fVar5 = new f(this, 6);
        f fVar6 = new f(this, 8);
        f fVar7 = new f(this, 9);
        f fVar8 = new f(this, 7);
        this.f2619c = new f[]{fVar, fVar3, fVar2, fVar4, fVar5, fVar8};
        ArrayList arrayList = new ArrayList();
        this.f2620d = arrayList;
        this.f2621e = 0;
        this.f2622f = 0;
        this.f2623g = 0;
        this.f2624h = 0;
        this.f2625i = 0;
        this.f2626j = null;
        arrayList.add(fVar);
        arrayList.add(fVar2);
        arrayList.add(fVar3);
        arrayList.add(fVar4);
        arrayList.add(fVar6);
        arrayList.add(fVar7);
        arrayList.add(fVar8);
        arrayList.add(fVar5);
    }

    public final int a() {
        if (this.f2625i == 8) {
            return 0;
        }
        return this.f2622f;
    }

    public final int b() {
        if (this.f2625i == 8) {
            return 0;
        }
        return this.f2621e;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        String str = "";
        sb.append("");
        if (this.f2626j != null) {
            str = "id: " + this.f2626j + " ";
        }
        sb.append(str);
        sb.append("(");
        sb.append(this.f2623g);
        sb.append(", ");
        sb.append(this.f2624h);
        sb.append(") - (");
        sb.append(this.f2621e);
        sb.append(" x ");
        sb.append(this.f2622f);
        sb.append(") wrap: (0 x 0)");
        return sb.toString();
    }
}
