package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.util.NamedListenerSet;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: NotifEvent.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class NotifEvent {
    private final String traceName;

    private NotifEvent(String str) {
        this.traceName = str;
    }

    public /* synthetic */ NotifEvent(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    public final void dispatchTo(NamedListenerSet namedListenerSet) {
        namedListenerSet.getClass();
        String str = this.traceName;
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice(str);
        }
        try {
            Iterator itNamedIterator = namedListenerSet.namedIterator();
            while (itNamedIterator.hasNext()) {
                NamedListenerSet.NamedListener namedListener = (NamedListenerSet.NamedListener) itNamedIterator.next();
                String name = namedListener.getName();
                Object listener = namedListener.getListener();
                zIsEnabled = Trace.isEnabled();
                if (zIsEnabled) {
                    TraceUtilsKt.beginSlice(name);
                }
                try {
                    dispatchToListener((NotifCollectionListener) listener);
                    Unit unit = Unit.INSTANCE;
                    if (zIsEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                } finally {
                    if (zIsEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                }
            }
            Unit unit2 = Unit.INSTANCE;
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public abstract void dispatchToListener(NotifCollectionListener notifCollectionListener);
}
