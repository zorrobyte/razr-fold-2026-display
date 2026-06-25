package F;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public abstract class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final DataSetObservable f115a = new DataSetObservable();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public DataSetObserver f116b;

    public abstract void a(ViewGroup viewGroup, int i2, Object obj);

    public void b(View view) {
    }

    public void c(ViewGroup viewGroup) {
        b(viewGroup);
    }

    public abstract int d();

    public int e(Object obj) {
        return -1;
    }

    public CharSequence f(int i2) {
        return null;
    }

    public float g(int i2) {
        return 1.0f;
    }

    public abstract Object h(ViewGroup viewGroup, int i2);

    public abstract boolean i(View view, Object obj);

    public void j() {
        synchronized (this) {
            try {
                DataSetObserver dataSetObserver = this.f116b;
                if (dataSetObserver != null) {
                    dataSetObserver.onChanged();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.f115a.notifyChanged();
    }

    public void k(DataSetObserver dataSetObserver) {
        this.f115a.registerObserver(dataSetObserver);
    }

    public void l(Parcelable parcelable, ClassLoader classLoader) {
    }

    public Parcelable m() {
        return null;
    }

    public void n(int i2, View view, Object obj) {
    }

    public void o(ViewGroup viewGroup, int i2, Object obj) {
        n(i2, viewGroup, obj);
    }

    public void p(View view) {
    }

    public void q(ViewGroup viewGroup) {
        p(viewGroup);
    }

    public void r(DataSetObserver dataSetObserver) {
        this.f115a.unregisterObserver(dataSetObserver);
    }
}
