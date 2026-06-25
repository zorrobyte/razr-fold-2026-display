package androidx.fragment.app;

import android.view.View;
import h.C0135b;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class N implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1581a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ArrayList f1582b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Map f1583c;

    public /* synthetic */ N(ArrayList arrayList, C0135b c0135b, int i2) {
        this.f1581a = i2;
        this.f1582b = arrayList;
        this.f1583c = c0135b;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str;
        Map map = this.f1583c;
        int i2 = 0;
        ArrayList arrayList = this.f1582b;
        switch (this.f1581a) {
            case 0:
                int size = arrayList.size();
                while (i2 < size) {
                    View view = (View) arrayList.get(i2);
                    WeakHashMap weakHashMap = v.l.f2836a;
                    String transitionName = view.getTransitionName();
                    if (transitionName != null) {
                        Iterator it = map.entrySet().iterator();
                        while (true) {
                            if (it.hasNext()) {
                                Map.Entry entry = (Map.Entry) it.next();
                                if (transitionName.equals(entry.getValue())) {
                                    str = (String) entry.getKey();
                                }
                            } else {
                                str = null;
                            }
                        }
                        view.setTransitionName(str);
                    }
                    i2++;
                }
                break;
            default:
                int size2 = arrayList.size();
                while (i2 < size2) {
                    View view2 = (View) arrayList.get(i2);
                    WeakHashMap weakHashMap2 = v.l.f2836a;
                    view2.setTransitionName((String) map.get(view2.getTransitionName()));
                    i2++;
                }
                break;
        }
    }
}
