package kotlinx.coroutines.channels;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.channels.SendChannel;

/* JADX INFO: compiled from: Produce.kt */
/* JADX INFO: loaded from: classes.dex */
final class ProducerCoroutine extends ChannelCoroutine implements ProducerScope {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ProducerCoroutine(CoroutineContext coroutineContext, Channel channel) {
        super(coroutineContext, channel, true, true);
        coroutineContext.getClass();
        channel.getClass();
    }

    @Override // kotlinx.coroutines.AbstractCoroutine, kotlinx.coroutines.JobSupport, kotlinx.coroutines.Job
    public boolean isActive() {
        return super.isActive();
    }

    @Override // kotlinx.coroutines.AbstractCoroutine
    protected void onCancelled(Throwable th, boolean z) {
        th.getClass();
        if (get_channel().close(th) || z) {
            return;
        }
        CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), th);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlinx.coroutines.AbstractCoroutine
    public void onCompleted(Unit unit) {
        unit.getClass();
        SendChannel.DefaultImpls.close$default(get_channel(), null, 1, null);
    }
}
