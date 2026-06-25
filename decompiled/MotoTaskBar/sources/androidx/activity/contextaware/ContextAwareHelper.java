package androidx.activity.contextaware;

import android.content.Context;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* JADX INFO: compiled from: ContextAwareHelper.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ContextAwareHelper {
    private volatile Context context;
    private final Set listeners = new CopyOnWriteArraySet();

    public final void addOnContextAvailableListener(OnContextAvailableListener onContextAvailableListener) {
        onContextAvailableListener.getClass();
        Context context = this.context;
        if (context != null) {
            onContextAvailableListener.onContextAvailable(context);
        }
        this.listeners.add(onContextAvailableListener);
    }

    public final void clearAvailableContext() {
        this.context = null;
    }

    public final void dispatchOnContextAvailable(Context context) {
        context.getClass();
        this.context = context;
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((OnContextAvailableListener) it.next()).onContextAvailable(context);
        }
    }
}
