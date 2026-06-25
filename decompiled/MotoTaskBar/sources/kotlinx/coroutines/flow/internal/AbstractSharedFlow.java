package kotlinx.coroutines.flow.internal;

import java.util.Arrays;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.StateFlow;

/* JADX INFO: compiled from: AbstractSharedFlow.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class AbstractSharedFlow {
    private SubscriptionCountStateFlow _subscriptionCount;
    private int nCollectors;
    private int nextIndex;
    private AbstractSharedFlowSlot[] slots;

    protected final AbstractSharedFlowSlot allocateSlot() {
        AbstractSharedFlowSlot abstractSharedFlowSlotCreateSlot;
        SubscriptionCountStateFlow subscriptionCountStateFlow;
        synchronized (this) {
            try {
                AbstractSharedFlowSlot[] abstractSharedFlowSlotArrCreateSlotArray = this.slots;
                if (abstractSharedFlowSlotArrCreateSlotArray == null) {
                    abstractSharedFlowSlotArrCreateSlotArray = createSlotArray(2);
                    this.slots = abstractSharedFlowSlotArrCreateSlotArray;
                } else if (this.nCollectors >= abstractSharedFlowSlotArrCreateSlotArray.length) {
                    Object[] objArrCopyOf = Arrays.copyOf(abstractSharedFlowSlotArrCreateSlotArray, abstractSharedFlowSlotArrCreateSlotArray.length * 2);
                    objArrCopyOf.getClass();
                    this.slots = (AbstractSharedFlowSlot[]) objArrCopyOf;
                    abstractSharedFlowSlotArrCreateSlotArray = (AbstractSharedFlowSlot[]) objArrCopyOf;
                }
                int i = this.nextIndex;
                do {
                    abstractSharedFlowSlotCreateSlot = abstractSharedFlowSlotArrCreateSlotArray[i];
                    if (abstractSharedFlowSlotCreateSlot == null) {
                        abstractSharedFlowSlotCreateSlot = createSlot();
                        abstractSharedFlowSlotArrCreateSlotArray[i] = abstractSharedFlowSlotCreateSlot;
                    }
                    i++;
                    if (i >= abstractSharedFlowSlotArrCreateSlotArray.length) {
                        i = 0;
                    }
                    abstractSharedFlowSlotCreateSlot.getClass();
                } while (!abstractSharedFlowSlotCreateSlot.allocateLocked(this));
                this.nextIndex = i;
                this.nCollectors++;
                subscriptionCountStateFlow = this._subscriptionCount;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (subscriptionCountStateFlow != null) {
            subscriptionCountStateFlow.increment(1);
        }
        return abstractSharedFlowSlotCreateSlot;
    }

    protected abstract AbstractSharedFlowSlot createSlot();

    protected abstract AbstractSharedFlowSlot[] createSlotArray(int i);

    protected final void freeSlot(AbstractSharedFlowSlot abstractSharedFlowSlot) {
        SubscriptionCountStateFlow subscriptionCountStateFlow;
        int i;
        Continuation[] continuationArrFreeLocked;
        abstractSharedFlowSlot.getClass();
        synchronized (this) {
            try {
                int i2 = this.nCollectors - 1;
                this.nCollectors = i2;
                subscriptionCountStateFlow = this._subscriptionCount;
                if (i2 == 0) {
                    this.nextIndex = 0;
                }
                continuationArrFreeLocked = abstractSharedFlowSlot.freeLocked(this);
            } catch (Throwable th) {
                throw th;
            }
        }
        for (Continuation continuation : continuationArrFreeLocked) {
            if (continuation != null) {
                Result.Companion companion = Result.Companion;
                continuation.resumeWith(Result.m2707constructorimpl(Unit.INSTANCE));
            }
        }
        if (subscriptionCountStateFlow != null) {
            subscriptionCountStateFlow.increment(-1);
        }
    }

    protected final int getNCollectors() {
        return this.nCollectors;
    }

    protected final AbstractSharedFlowSlot[] getSlots() {
        return this.slots;
    }

    public final StateFlow getSubscriptionCount() {
        SubscriptionCountStateFlow subscriptionCountStateFlow;
        synchronized (this) {
            subscriptionCountStateFlow = this._subscriptionCount;
            if (subscriptionCountStateFlow == null) {
                subscriptionCountStateFlow = new SubscriptionCountStateFlow(this.nCollectors);
                this._subscriptionCount = subscriptionCountStateFlow;
            }
        }
        return subscriptionCountStateFlow;
    }
}
