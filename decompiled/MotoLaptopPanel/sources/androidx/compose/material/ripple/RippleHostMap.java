package androidx.compose.material.ripple;

import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: compiled from: RippleContainer.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class RippleHostMap {
    private final Map indicationToHostMap = new LinkedHashMap();
    private final Map hostToIndicationMap = new LinkedHashMap();

    public final RippleHostKey get(RippleHostView rippleHostView) {
        return (RippleHostKey) this.hostToIndicationMap.get(rippleHostView);
    }

    public final RippleHostView get(RippleHostKey rippleHostKey) {
        return (RippleHostView) this.indicationToHostMap.get(rippleHostKey);
    }

    public final void remove(RippleHostKey rippleHostKey) {
        RippleHostView rippleHostView = (RippleHostView) this.indicationToHostMap.get(rippleHostKey);
        if (rippleHostView != null) {
        }
        this.indicationToHostMap.remove(rippleHostKey);
    }

    public final void set(RippleHostKey rippleHostKey, RippleHostView rippleHostView) {
        this.indicationToHostMap.put(rippleHostKey, rippleHostView);
        this.hostToIndicationMap.put(rippleHostView, rippleHostKey);
    }
}
