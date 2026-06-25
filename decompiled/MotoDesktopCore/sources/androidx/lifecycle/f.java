package androidx.lifecycle;

import android.util.Log;
import com.motorola.mobiledesktop.core.uinput.EventType;
import f.C0128a;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class f extends c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final C0128a f1704a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public b f1705b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final WeakReference f1706c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f1707d;

    public f(d dVar) {
        C0128a c0128a = new C0128a();
        new HashMap();
        this.f1704a = c0128a;
        this.f1707d = false;
        new ArrayList();
        this.f1706c = new WeakReference(dVar);
        this.f1705b = b.f1697b;
    }

    public final void a(a aVar) {
        b bVar;
        switch (e.f1702a[aVar.ordinal()]) {
            case 1:
            case 2:
                bVar = b.f1698c;
                break;
            case 3:
            case EventType.EVENT_MSC /* 4 */:
                bVar = b.f1699d;
                break;
            case EventType.EVENT_SW /* 5 */:
                bVar = b.f1700e;
                break;
            case 6:
                bVar = b.f1696a;
                break;
            default:
                throw new IllegalArgumentException("Unexpected event value " + aVar);
        }
        b(bVar);
    }

    public final void b(b bVar) {
        if (this.f1705b == bVar) {
            return;
        }
        this.f1705b = bVar;
        if (this.f1707d) {
            return;
        }
        this.f1707d = true;
        if (((d) this.f1706c.get()) == null) {
            Log.w("LifecycleRegistry", "LifecycleOwner is garbage collected, you shouldn't try dispatch new events from it.");
        } else {
            this.f1704a.getClass();
        }
        this.f1707d = false;
    }
}
