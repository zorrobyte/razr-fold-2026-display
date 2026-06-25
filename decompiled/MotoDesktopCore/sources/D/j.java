package d;

import android.view.View;
import android.view.animation.Interpolator;
import androidx.appcompat.widget.u0;
import java.util.ArrayList;
import java.util.Iterator;
import v.m;
import v.n;

/* JADX INFO: loaded from: classes.dex */
public final class j {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Interpolator f2406c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public n f2407d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f2408e;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public long f2405b = -1;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final u0 f2409f = new u0(this);

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final ArrayList f2404a = new ArrayList();

    public final void a() {
        if (this.f2408e) {
            Iterator it = this.f2404a.iterator();
            while (it.hasNext()) {
                ((m) it.next()).b();
            }
            this.f2408e = false;
        }
    }

    public final void b() {
        View view;
        if (this.f2408e) {
            return;
        }
        for (m mVar : this.f2404a) {
            long j2 = this.f2405b;
            if (j2 >= 0) {
                mVar.c(j2);
            }
            Interpolator interpolator = this.f2406c;
            if (interpolator != null && (view = (View) mVar.f2837a.get()) != null) {
                view.animate().setInterpolator(interpolator);
            }
            if (this.f2407d != null) {
                mVar.d(this.f2409f);
            }
            View view2 = (View) mVar.f2837a.get();
            if (view2 != null) {
                view2.animate().start();
            }
        }
        this.f2408e = true;
    }
}
