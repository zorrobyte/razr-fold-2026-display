package J;

import I.e;
import Y.t;
import Y.w;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.B;
import androidx.appcompat.widget.C0084v;
import com.google.android.material.behavior.SwipeDismissBehavior;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import s.i;
import v.l;
import x.C0165b;

/* JADX INFO: loaded from: classes.dex */
public final class b implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f186a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Object f187b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object f188c;

    public b(w wVar, t tVar) {
        this.f186a = 1;
        this.f187b = wVar;
        this.f188c = tVar;
    }

    public b(SwipeDismissBehavior swipeDismissBehavior, View view, boolean z2) {
        this.f186a = 0;
        this.f188c = swipeDismissBehavior;
        this.f187b = view;
    }

    public /* synthetic */ b(Object obj, Object obj2, int i2) {
        this.f186a = i2;
        this.f188c = obj;
        this.f187b = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object obj = this.f187b;
        Object obj2 = this.f188c;
        switch (this.f186a) {
            case 0:
                C0165b c0165b = ((SwipeDismissBehavior) obj2).f2066a;
                if (c0165b != null && c0165b.g()) {
                    WeakHashMap weakHashMap = l.f2836a;
                    ((View) obj).postOnAnimation(this);
                    break;
                }
                break;
            case 1:
                ((w) obj).f451c.setProgress(((t) obj2).b());
                break;
            case 2:
                C0084v c0084v = (C0084v) obj2;
                B b2 = (B) c0084v.f1319c;
                if (b2.f972k) {
                    Typeface typeface = (Typeface) obj;
                    b2.f971j = typeface;
                    TextView textView = (TextView) ((WeakReference) c0084v.f1318b).get();
                    if (textView != null) {
                        textView.setTypeface(typeface, b2.f970i);
                    }
                }
                break;
            default:
                ((i) ((e) obj2).f165d).a(obj);
                break;
        }
    }
}
