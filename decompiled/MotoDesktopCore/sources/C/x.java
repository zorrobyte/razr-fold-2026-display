package C;

import android.animation.TimeInterpolator;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class x extends s {

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public int f101z;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public ArrayList f99x = new ArrayList();

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public boolean f100y = true;

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public boolean f97A = false;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public int f98B = 0;

    @Override // C.s
    public final void A(Y.r rVar) {
        this.f89s = rVar;
        this.f98B |= 8;
        int size = this.f99x.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((s) this.f99x.get(i2)).A(rVar);
        }
    }

    @Override // C.s
    public final void B(TimeInterpolator timeInterpolator) {
        this.f98B |= 1;
        ArrayList arrayList = this.f99x;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((s) this.f99x.get(i2)).B(timeInterpolator);
            }
        }
        this.f75d = timeInterpolator;
    }

    @Override // C.s
    public final void C(n nVar) {
        super.C(nVar);
        this.f98B |= 4;
        for (int i2 = 0; i2 < this.f99x.size(); i2++) {
            ((s) this.f99x.get(i2)).C(nVar);
        }
    }

    @Override // C.s
    public final void D() {
        this.f98B |= 2;
        int size = this.f99x.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((s) this.f99x.get(i2)).D();
        }
    }

    @Override // C.s
    public final void E(long j2) {
        this.f73b = j2;
    }

    @Override // C.s
    public final String G(String str) {
        String strG = super.G(str);
        for (int i2 = 0; i2 < this.f99x.size(); i2++) {
            StringBuilder sb = new StringBuilder();
            sb.append(strG);
            sb.append("\n");
            sb.append(((s) this.f99x.get(i2)).G(str + "  "));
            strG = sb.toString();
        }
        return strG;
    }

    public final void H(s sVar) {
        this.f99x.add(sVar);
        sVar.f80i = this;
        long j2 = this.f74c;
        if (j2 >= 0) {
            sVar.z(j2);
        }
        if ((this.f98B & 1) != 0) {
            sVar.B(this.f75d);
        }
        if ((this.f98B & 2) != 0) {
            sVar.D();
        }
        if ((this.f98B & 4) != 0) {
            sVar.C(this.f90t);
        }
        if ((this.f98B & 8) != 0) {
            sVar.A(this.f89s);
        }
    }

    @Override // C.s
    public final void a(r rVar) {
        super.a(rVar);
    }

    @Override // C.s
    public final void b(View view) {
        for (int i2 = 0; i2 < this.f99x.size(); i2++) {
            ((s) this.f99x.get(i2)).b(view);
        }
        this.f77f.add(view);
    }

    @Override // C.s
    public final void d(y yVar) {
        if (s(yVar.f103b)) {
            for (s sVar : this.f99x) {
                if (sVar.s(yVar.f103b)) {
                    sVar.d(yVar);
                    yVar.f104c.add(sVar);
                }
            }
        }
    }

    @Override // C.s
    public final void f(y yVar) {
        int size = this.f99x.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((s) this.f99x.get(i2)).f(yVar);
        }
    }

    @Override // C.s
    public final void g(y yVar) {
        if (s(yVar.f103b)) {
            for (s sVar : this.f99x) {
                if (sVar.s(yVar.f103b)) {
                    sVar.g(yVar);
                    yVar.f104c.add(sVar);
                }
            }
        }
    }

    @Override // C.s
    /* JADX INFO: renamed from: j */
    public final s clone() {
        x xVar = (x) super.clone();
        xVar.f99x = new ArrayList();
        int size = this.f99x.size();
        for (int i2 = 0; i2 < size; i2++) {
            xVar.H(((s) this.f99x.get(i2)).clone());
        }
        return xVar;
    }

    @Override // C.s
    public final void l(ViewGroup viewGroup, z zVar, z zVar2, ArrayList arrayList, ArrayList arrayList2) {
        long j2 = this.f73b;
        int size = this.f99x.size();
        for (int i2 = 0; i2 < size; i2++) {
            s sVar = (s) this.f99x.get(i2);
            if (j2 > 0 && (this.f100y || i2 == 0)) {
                long j3 = sVar.f73b;
                if (j3 > 0) {
                    sVar.E(j3 + j2);
                } else {
                    sVar.E(j2);
                }
            }
            sVar.l(viewGroup, zVar, zVar2, arrayList, arrayList2);
        }
    }

    @Override // C.s
    public final void u(View view) {
        super.u(view);
        int size = this.f99x.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((s) this.f99x.get(i2)).u(view);
        }
    }

    @Override // C.s
    public final void v(r rVar) {
        super.v(rVar);
    }

    @Override // C.s
    public final void w(View view) {
        for (int i2 = 0; i2 < this.f99x.size(); i2++) {
            ((s) this.f99x.get(i2)).w(view);
        }
        this.f77f.remove(view);
    }

    @Override // C.s
    public final void x(ViewGroup viewGroup) {
        super.x(viewGroup);
        int size = this.f99x.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((s) this.f99x.get(i2)).x(viewGroup);
        }
    }

    @Override // C.s
    public final void y() {
        if (this.f99x.isEmpty()) {
            F();
            m();
            return;
        }
        C0004e c0004e = new C0004e();
        c0004e.f30b = this;
        Iterator it = this.f99x.iterator();
        while (it.hasNext()) {
            ((s) it.next()).a(c0004e);
        }
        this.f101z = this.f99x.size();
        if (this.f100y) {
            Iterator it2 = this.f99x.iterator();
            while (it2.hasNext()) {
                ((s) it2.next()).y();
            }
            return;
        }
        for (int i2 = 1; i2 < this.f99x.size(); i2++) {
            ((s) this.f99x.get(i2 - 1)).a(new C0004e(2, (s) this.f99x.get(i2)));
        }
        s sVar = (s) this.f99x.get(0);
        if (sVar != null) {
            sVar.y();
        }
    }

    @Override // C.s
    public final void z(long j2) {
        this.f74c = j2;
        if (j2 >= 0) {
            int size = this.f99x.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((s) this.f99x.get(i2)).z(j2);
            }
        }
    }
}
