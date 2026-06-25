package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.selects.SelectClause1;

/* JADX INFO: compiled from: Channel.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ReceiveChannel {
    void cancel(CancellationException cancellationException);

    SelectClause1 getOnReceiveCatching();

    ChannelIterator iterator();

    Object receive(Continuation continuation);

    /* JADX INFO: renamed from: receiveCatching-JP2dKIU */
    Object mo2213receiveCatchingJP2dKIU(Continuation continuation);

    /* JADX INFO: renamed from: tryReceive-PtdJZtk */
    Object mo2214tryReceivePtdJZtk();
}
