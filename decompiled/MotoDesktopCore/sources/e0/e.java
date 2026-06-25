package e0;

import X.C;
import X.v0;
import android.content.ClipData;
import com.motorola.android.dragdrop.IMotoDragDropRfCallback;
import com.motorola.android.dragdrop.MotoDragShadow;
import com.motorola.mobiledesktop.core.bean.RfDragShadow;

/* JADX INFO: loaded from: classes.dex */
public final class e extends IMotoDragDropRfCallback.Stub {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ g f2470a;

    public e(g gVar) {
        this.f2470a = gVar;
    }

    public final void onEventForRf(int i2, String str) {
        try {
            C c2 = this.f2470a.f2480h;
            if (c2 != null) {
                ((X.A) c2).onEventForRf(i2, str);
            }
        } catch (Exception unused) {
        }
    }

    public final void onStartDragForRf(long j2, ClipData clipData, MotoDragShadow motoDragShadow, int i2) {
        RfDragShadow rfDragShadow = new RfDragShadow(motoDragShadow.getmWidth(), motoDragShadow.getmHeight(), motoDragShadow.getmShadowTouchX(), motoDragShadow.getmShadowTouchY(), motoDragShadow.getmBitmap());
        if (clipData != null) {
            v0.a("FwkDragDropManager", "onStartDragForRf getText:" + ((Object) clipData.getItemAt(0).getText()) + " uri:" + clipData.getItemAt(0).getUri());
        }
        try {
            C c2 = this.f2470a.f2480h;
            if (c2 != null) {
                ((X.A) c2).a(j2, clipData, rfDragShadow, i2);
            }
        } catch (Exception unused) {
        }
    }
}
