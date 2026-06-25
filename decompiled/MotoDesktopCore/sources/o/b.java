package O;

import android.view.ViewTreeObserver;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* JADX INFO: loaded from: classes.dex */
public final class b implements ViewTreeObserver.OnPreDrawListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f204a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f205b;

    public /* synthetic */ b(int i2, Object obj) {
        this.f204a = i2;
        this.f205b = obj;
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public final boolean onPreDraw() {
        switch (this.f204a) {
            case 0:
                d dVar = (d) this.f205b;
                float rotation = dVar.f227n.getRotation();
                if (dVar.f220g != rotation) {
                    dVar.f220g = rotation;
                }
                break;
            default:
                ((CoordinatorLayout) this.f205b).o(0);
                break;
        }
        return true;
    }
}
