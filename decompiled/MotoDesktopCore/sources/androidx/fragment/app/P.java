package androidx.fragment.app;

import android.widget.ListView;
import com.motorola.mobiledesktop.core.uinput.EventType;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class P implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1584a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Object f1585b;

    public /* synthetic */ P(int i2, Object obj) {
        this.f1584a = i2;
        this.f1585b = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f1584a) {
            case 0:
                ListView listView = ((ListFragment) this.f1585b).mList;
                listView.focusableViewAvailable(listView);
                break;
            case 1:
                ((AbstractComponentCallbacksC0098j) this.f1585b).callStartTransitionListener();
                break;
            case 2:
                ((z) this.f1585b).G();
                break;
            case 3:
                C0104p c0104p = (C0104p) this.f1585b;
                if (c0104p.f1643c.getAnimatingAway() != null) {
                    c0104p.f1643c.setAnimatingAway(null);
                    z zVar = c0104p.f1644d;
                    AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j = c0104p.f1643c;
                    zVar.S(abstractComponentCallbacksC0098j, abstractComponentCallbacksC0098j.getStateAfterAnimating(), 0, 0, false);
                }
                break;
            case EventType.EVENT_MSC /* 4 */:
                ((C0106s) this.f1585b).f1652b.setLayerType(0, null);
                break;
            default:
                H.j((ArrayList) this.f1585b, 4);
                break;
        }
    }
}
