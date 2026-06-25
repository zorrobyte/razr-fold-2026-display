package kotlinx.coroutines.channels;

import kotlinx.atomicfu.AtomicArray;
import kotlinx.atomicfu.AtomicFU_commonKt;
import kotlinx.coroutines.internal.Segment;

/* JADX INFO: compiled from: BufferedChannel.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ChannelSegment extends Segment {
    private final BufferedChannel _channel;
    private final AtomicArray data;

    public ChannelSegment(long j, ChannelSegment channelSegment, BufferedChannel bufferedChannel, int i) {
        super(j, channelSegment, i);
        this._channel = bufferedChannel;
        this.data = AtomicFU_commonKt.atomicArrayOfNulls(BufferedChannelKt.SEGMENT_SIZE * 2);
    }

    private final void setElementLazy(int i, Object obj) {
        this.data.get(i * 2).lazySet(obj);
    }

    public final boolean casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(int i, Object obj, Object obj2) {
        return this.data.get((i * 2) + 1).compareAndSet(obj, obj2);
    }

    public final void cleanElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(int i) {
        setElementLazy(i, null);
    }

    public final Object getAndSetState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(int i, Object obj) {
        return this.data.get((i * 2) + 1).getAndSet(obj);
    }

    public final BufferedChannel getChannel() {
        BufferedChannel bufferedChannel = this._channel;
        bufferedChannel.getClass();
        return bufferedChannel;
    }

    public final Object getElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(int i) {
        return this.data.get(i * 2).getValue();
    }

    @Override // kotlinx.coroutines.internal.Segment
    public int getNumberOfSlots() {
        return BufferedChannelKt.SEGMENT_SIZE;
    }

    public final Object getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(int i) {
        return this.data.get((i * 2) + 1).getValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0065, code lost:
    
        cleanElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0068, code lost:
    
        if (r0 == false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x006a, code lost:
    
        r3 = getChannel().onUndeliveredElement;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0070, code lost:
    
        if (r3 == null) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0072, code lost:
    
        kotlinx.coroutines.internal.OnUndeliveredElementKt.callUndeliveredElement(r3, r5, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0075, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:?, code lost:
    
        return;
     */
    @Override // kotlinx.coroutines.internal.Segment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onCancellation(int r4, java.lang.Throwable r5, kotlin.coroutines.CoroutineContext r6) {
        /*
            r3 = this;
            r6.getClass()
            int r5 = kotlinx.coroutines.channels.BufferedChannelKt.SEGMENT_SIZE
            if (r4 < r5) goto L9
            r0 = 1
            goto La
        L9:
            r0 = 0
        La:
            if (r0 == 0) goto Ld
            int r4 = r4 - r5
        Ld:
            java.lang.Object r5 = r3.getElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r4)
        L11:
            java.lang.Object r1 = r3.getState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r4)
            boolean r2 = r1 instanceof kotlinx.coroutines.Waiter
            if (r2 != 0) goto L76
            boolean r2 = r1 instanceof kotlinx.coroutines.channels.WaiterEB
            if (r2 == 0) goto L1e
            goto L76
        L1e:
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.BufferedChannelKt.access$getINTERRUPTED_SEND$p()
            if (r1 == r2) goto L65
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.BufferedChannelKt.access$getINTERRUPTED_RCV$p()
            if (r1 != r2) goto L2b
            goto L65
        L2b:
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.BufferedChannelKt.access$getRESUMING_BY_EB$p()
            if (r1 == r2) goto L11
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.BufferedChannelKt.access$getRESUMING_BY_RCV$p()
            if (r1 != r2) goto L38
            goto L11
        L38:
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.BufferedChannelKt.access$getDONE_RCV$p()
            if (r1 == r3) goto L9c
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.BufferedChannelKt.BUFFERED
            if (r1 != r3) goto L43
            goto L9c
        L43:
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.channels.BufferedChannelKt.getCHANNEL_CLOSED()
            if (r1 != r3) goto L4a
            goto L9c
        L4a:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "unexpected state: "
            r4.append(r5)
            r4.append(r1)
            java.lang.String r4 = r4.toString()
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        L65:
            r3.cleanElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r4)
            if (r0 == 0) goto L9c
            kotlinx.coroutines.channels.BufferedChannel r3 = r3.getChannel()
            kotlin.jvm.functions.Function1 r3 = r3.onUndeliveredElement
            if (r3 == 0) goto L9c
            kotlinx.coroutines.internal.OnUndeliveredElementKt.callUndeliveredElement(r3, r5, r6)
            return
        L76:
            if (r0 == 0) goto L7d
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.BufferedChannelKt.access$getINTERRUPTED_SEND$p()
            goto L81
        L7d:
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.channels.BufferedChannelKt.access$getINTERRUPTED_RCV$p()
        L81:
            boolean r1 = r3.casState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r4, r1, r2)
            if (r1 == 0) goto L11
            r3.cleanElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r4)
            r1 = r0 ^ 1
            r3.onCancelledRequest(r4, r1)
            if (r0 == 0) goto L9c
            kotlinx.coroutines.channels.BufferedChannel r3 = r3.getChannel()
            kotlin.jvm.functions.Function1 r3 = r3.onUndeliveredElement
            if (r3 == 0) goto L9c
            kotlinx.coroutines.internal.OnUndeliveredElementKt.callUndeliveredElement(r3, r5, r6)
        L9c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelSegment.onCancellation(int, java.lang.Throwable, kotlin.coroutines.CoroutineContext):void");
    }

    public final void onCancelledRequest(int i, boolean z) {
        if (z) {
            getChannel().waitExpandBufferCompletion$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host((this.id * ((long) BufferedChannelKt.SEGMENT_SIZE)) + ((long) i));
        }
        onSlotCleaned();
    }

    public final Object retrieveElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(int i) {
        Object element$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = getElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
        cleanElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(i);
        return element$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
    }

    public final void setState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(int i, Object obj) {
        this.data.get((i * 2) + 1).setValue(obj);
    }

    public final void storeElement$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(int i, Object obj) {
        setElementLazy(i, obj);
    }
}
