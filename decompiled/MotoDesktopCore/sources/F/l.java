package F;

import android.database.DataSetObserver;
import androidx.appcompat.widget.Q;
import androidx.viewpager.widget.ViewPager;

/* JADX INFO: loaded from: classes.dex */
public final class l extends DataSetObserver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f136a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Object f137b;

    public /* synthetic */ l(int i2, Object obj) {
        this.f136a = i2;
        this.f137b = obj;
    }

    public l(G.a aVar) {
        this.f136a = 1;
        this.f137b = aVar;
    }

    @Override // android.database.DataSetObserver
    public final void onChanged() {
        switch (this.f136a) {
            case 0:
                ((ViewPager) this.f137b).f();
                break;
            case 1:
                G.a aVar = (G.a) this.f137b;
                if (aVar != null) {
                    G.a.s(aVar);
                }
                break;
            default:
                Q q2 = (Q) this.f137b;
                if (q2.f1070y.isShowing()) {
                    q2.show();
                }
                break;
        }
    }

    @Override // android.database.DataSetObserver
    public final void onInvalidated() {
        switch (this.f136a) {
            case 0:
                ((ViewPager) this.f137b).f();
                break;
            case 1:
                onChanged();
                break;
            default:
                ((Q) this.f137b).dismiss();
                break;
        }
    }
}
