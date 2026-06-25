package I;

import android.os.Handler;
import android.view.View;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public final class e implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f162a = 0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Object f163b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Object f164c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f165d;

    public e(f fVar, CoordinatorLayout coordinatorLayout, View view) {
        this.f165d = fVar;
        this.f163b = coordinatorLayout;
        this.f164c = view;
    }

    public e(s.b bVar, Handler handler, s.d dVar) {
        this.f163b = bVar;
        this.f164c = handler;
        this.f165d = dVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object objCall;
        f fVar;
        OverScroller overScroller;
        Object obj = this.f164c;
        Object obj2 = this.f163b;
        switch (this.f162a) {
            case 0:
                View view = (View) obj;
                if (view != null && (overScroller = (fVar = (f) this.f165d).f167d) != null) {
                    CoordinatorLayout coordinatorLayout = (CoordinatorLayout) obj2;
                    if (!overScroller.computeScrollOffset()) {
                        ((AppBarLayout.BaseBehavior) fVar).y(coordinatorLayout, (AppBarLayout) view);
                    } else {
                        fVar.w(coordinatorLayout, view, fVar.f167d.getCurrY(), Integer.MIN_VALUE, Integer.MAX_VALUE);
                        WeakHashMap weakHashMap = l.f2836a;
                        view.postOnAnimation(this);
                    }
                    break;
                }
                break;
            default:
                try {
                    objCall = ((Callable) obj2).call();
                } catch (Exception unused) {
                    objCall = null;
                }
                ((Handler) obj).post(new J.b(this, objCall, 3));
                break;
        }
    }
}
