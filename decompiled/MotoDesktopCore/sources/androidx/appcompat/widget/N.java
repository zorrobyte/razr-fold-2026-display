package androidx.appcompat.widget;

import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class N implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1040a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Q f1041b;

    public /* synthetic */ N(Q q2, int i2) {
        this.f1040a = i2;
        this.f1041b = q2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Q q2 = this.f1041b;
        switch (this.f1040a) {
            case 0:
                I i2 = q2.f1049c;
                if (i2 != null) {
                    i2.setListSelectionHidden(true);
                    i2.requestLayout();
                }
                break;
            default:
                I i3 = q2.f1049c;
                if (i3 != null) {
                    WeakHashMap weakHashMap = v.l.f2836a;
                    if (i3.isAttachedToWindow() && q2.f1049c.getCount() > q2.f1049c.getChildCount() && q2.f1049c.getChildCount() <= q2.f1059m) {
                        q2.f1070y.setInputMethodMode(2);
                        q2.show();
                        break;
                    }
                }
                break;
        }
    }
}
