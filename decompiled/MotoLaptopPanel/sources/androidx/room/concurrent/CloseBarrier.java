package androidx.room.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: CloseBarrier.kt */
/* JADX INFO: loaded from: classes.dex */
public final class CloseBarrier {
    private final AtomicInteger blockers;
    private final Function0 closeAction;
    private final AtomicBoolean closeInitiated;

    public CloseBarrier(Function0 function0) {
        function0.getClass();
        this.closeAction = function0;
        this.blockers = new AtomicInteger(0);
        this.closeInitiated = new AtomicBoolean(false);
    }
}
