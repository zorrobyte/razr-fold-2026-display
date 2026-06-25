package C;

import android.view.View;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class k implements r {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ View f49a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ArrayList f50b;

    public k(View view, ArrayList arrayList) {
        this.f49a = view;
        this.f50b = arrayList;
    }

    @Override // C.r
    public final void a(s sVar) {
        sVar.v(this);
        this.f49a.setVisibility(8);
        ArrayList arrayList = this.f50b;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((View) arrayList.get(i2)).setVisibility(0);
        }
    }

    @Override // C.r
    public final void b() {
    }

    @Override // C.r
    public final void c() {
    }

    @Override // C.r
    public final void d() {
    }
}
