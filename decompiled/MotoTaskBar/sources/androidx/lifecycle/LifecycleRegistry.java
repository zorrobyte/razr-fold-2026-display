package androidx.lifecycle;

import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.Lifecycle;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: compiled from: LifecycleRegistry.jvm.kt */
/* JADX INFO: loaded from: classes.dex */
public class LifecycleRegistry extends Lifecycle {
    public static final Companion Companion = new Companion(null);
    private final MutableStateFlow _currentStateFlow;
    private int addingObserverCounter;
    private final boolean enforceMainThread;
    private boolean handlingEvent;
    private final WeakReference lifecycleOwner;
    private boolean newEventOccurred;
    private FastSafeIterableMap observerMap;
    private ArrayList parentStates;
    private Lifecycle.State state;

    /* JADX INFO: compiled from: LifecycleRegistry.jvm.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Lifecycle.State min$lifecycle_runtime_release(Lifecycle.State state, Lifecycle.State state2) {
            state.getClass();
            return (state2 == null || state2.compareTo(state) >= 0) ? state : state2;
        }
    }

    /* JADX INFO: compiled from: LifecycleRegistry.jvm.kt */
    public final class ObserverWithState {
        private LifecycleEventObserver lifecycleObserver;
        private Lifecycle.State state;

        public ObserverWithState(LifecycleObserver lifecycleObserver, Lifecycle.State state) {
            state.getClass();
            lifecycleObserver.getClass();
            this.lifecycleObserver = Lifecycling.lifecycleEventObserver(lifecycleObserver);
            this.state = state;
        }

        public final void dispatchEvent(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            event.getClass();
            Lifecycle.State targetState = event.getTargetState();
            this.state = LifecycleRegistry.Companion.min$lifecycle_runtime_release(this.state, targetState);
            LifecycleEventObserver lifecycleEventObserver = this.lifecycleObserver;
            lifecycleOwner.getClass();
            lifecycleEventObserver.onStateChanged(lifecycleOwner, event);
            this.state = targetState;
        }

        public final Lifecycle.State getState() {
            return this.state;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LifecycleRegistry(LifecycleOwner lifecycleOwner) {
        this(lifecycleOwner, true);
        lifecycleOwner.getClass();
    }

    private LifecycleRegistry(LifecycleOwner lifecycleOwner, boolean z) {
        this.enforceMainThread = z;
        this.observerMap = new FastSafeIterableMap();
        Lifecycle.State state = Lifecycle.State.INITIALIZED;
        this.state = state;
        this.parentStates = new ArrayList();
        this.lifecycleOwner = new WeakReference(lifecycleOwner);
        this._currentStateFlow = StateFlowKt.MutableStateFlow(state);
    }

    private final void backwardPass(LifecycleOwner lifecycleOwner) {
        Iterator itDescendingIterator = this.observerMap.descendingIterator();
        itDescendingIterator.getClass();
        while (itDescendingIterator.hasNext() && !this.newEventOccurred) {
            Map.Entry entry = (Map.Entry) itDescendingIterator.next();
            entry.getClass();
            LifecycleObserver lifecycleObserver = (LifecycleObserver) entry.getKey();
            ObserverWithState observerWithState = (ObserverWithState) entry.getValue();
            while (observerWithState.getState().compareTo(this.state) > 0 && !this.newEventOccurred && this.observerMap.contains(lifecycleObserver)) {
                Lifecycle.Event eventDownFrom = Lifecycle.Event.Companion.downFrom(observerWithState.getState());
                if (eventDownFrom == null) {
                    throw new IllegalStateException("no event down from " + observerWithState.getState());
                }
                pushParentState(eventDownFrom.getTargetState());
                observerWithState.dispatchEvent(lifecycleOwner, eventDownFrom);
                popParentState();
            }
        }
    }

    private final Lifecycle.State calculateTargetState(LifecycleObserver lifecycleObserver) {
        ObserverWithState observerWithState;
        Map.Entry entryCeil = this.observerMap.ceil(lifecycleObserver);
        Lifecycle.State state = null;
        Lifecycle.State state2 = (entryCeil == null || (observerWithState = (ObserverWithState) entryCeil.getValue()) == null) ? null : observerWithState.getState();
        if (!this.parentStates.isEmpty()) {
            state = (Lifecycle.State) this.parentStates.get(r0.size() - 1);
        }
        Companion companion = Companion;
        return companion.min$lifecycle_runtime_release(companion.min$lifecycle_runtime_release(this.state, state2), state);
    }

    private final void enforceMainThreadIfNeeded(String str) {
        if (!this.enforceMainThread || LifecycleRegistry_androidKt.isMainThread()) {
            return;
        }
        throw new IllegalStateException(("Method " + str + " must be called on the main thread").toString());
    }

    private final void forwardPass(LifecycleOwner lifecycleOwner) {
        SafeIterableMap.IteratorWithAdditions iteratorWithAdditions = this.observerMap.iteratorWithAdditions();
        iteratorWithAdditions.getClass();
        while (iteratorWithAdditions.hasNext() && !this.newEventOccurred) {
            Map.Entry entry = (Map.Entry) iteratorWithAdditions.next();
            LifecycleObserver lifecycleObserver = (LifecycleObserver) entry.getKey();
            ObserverWithState observerWithState = (ObserverWithState) entry.getValue();
            while (observerWithState.getState().compareTo(this.state) < 0 && !this.newEventOccurred && this.observerMap.contains(lifecycleObserver)) {
                pushParentState(observerWithState.getState());
                Lifecycle.Event eventUpFrom = Lifecycle.Event.Companion.upFrom(observerWithState.getState());
                if (eventUpFrom == null) {
                    throw new IllegalStateException("no event up from " + observerWithState.getState());
                }
                observerWithState.dispatchEvent(lifecycleOwner, eventUpFrom);
                popParentState();
            }
        }
    }

    private final boolean isSynced() {
        if (this.observerMap.size() == 0) {
            return true;
        }
        Map.Entry entryEldest = this.observerMap.eldest();
        entryEldest.getClass();
        Lifecycle.State state = ((ObserverWithState) entryEldest.getValue()).getState();
        Map.Entry entryNewest = this.observerMap.newest();
        entryNewest.getClass();
        Lifecycle.State state2 = ((ObserverWithState) entryNewest.getValue()).getState();
        return state == state2 && this.state == state2;
    }

    private final void moveToState(Lifecycle.State state) {
        if (this.state == state) {
            return;
        }
        LifecycleRegistryKt.checkLifecycleStateTransition((LifecycleOwner) this.lifecycleOwner.get(), this.state, state);
        this.state = state;
        if (this.handlingEvent || this.addingObserverCounter != 0) {
            this.newEventOccurred = true;
            return;
        }
        this.handlingEvent = true;
        sync();
        this.handlingEvent = false;
        if (this.state == Lifecycle.State.DESTROYED) {
            this.observerMap = new FastSafeIterableMap();
        }
    }

    private final void popParentState() {
        this.parentStates.remove(r1.size() - 1);
    }

    private final void pushParentState(Lifecycle.State state) {
        this.parentStates.add(state);
    }

    private final void sync() {
        LifecycleOwner lifecycleOwner = (LifecycleOwner) this.lifecycleOwner.get();
        if (lifecycleOwner == null) {
            throw new IllegalStateException("LifecycleOwner of this LifecycleRegistry is already garbage collected. It is too late to change lifecycle state.");
        }
        while (!isSynced()) {
            this.newEventOccurred = false;
            Lifecycle.State state = this.state;
            Map.Entry entryEldest = this.observerMap.eldest();
            entryEldest.getClass();
            if (state.compareTo(((ObserverWithState) entryEldest.getValue()).getState()) < 0) {
                backwardPass(lifecycleOwner);
            }
            Map.Entry entryNewest = this.observerMap.newest();
            if (!this.newEventOccurred && entryNewest != null && this.state.compareTo(((ObserverWithState) entryNewest.getValue()).getState()) > 0) {
                forwardPass(lifecycleOwner);
            }
        }
        this.newEventOccurred = false;
        this._currentStateFlow.setValue(getCurrentState());
    }

    @Override // androidx.lifecycle.Lifecycle
    public void addObserver(LifecycleObserver lifecycleObserver) {
        LifecycleOwner lifecycleOwner;
        lifecycleObserver.getClass();
        enforceMainThreadIfNeeded("addObserver");
        Lifecycle.State state = this.state;
        Lifecycle.State state2 = Lifecycle.State.DESTROYED;
        if (state != state2) {
            state2 = Lifecycle.State.INITIALIZED;
        }
        ObserverWithState observerWithState = new ObserverWithState(lifecycleObserver, state2);
        if (((ObserverWithState) this.observerMap.putIfAbsent(lifecycleObserver, observerWithState)) == null && (lifecycleOwner = (LifecycleOwner) this.lifecycleOwner.get()) != null) {
            boolean z = this.addingObserverCounter != 0 || this.handlingEvent;
            Lifecycle.State stateCalculateTargetState = calculateTargetState(lifecycleObserver);
            this.addingObserverCounter++;
            while (observerWithState.getState().compareTo(stateCalculateTargetState) < 0 && this.observerMap.contains(lifecycleObserver)) {
                pushParentState(observerWithState.getState());
                Lifecycle.Event eventUpFrom = Lifecycle.Event.Companion.upFrom(observerWithState.getState());
                if (eventUpFrom == null) {
                    throw new IllegalStateException("no event up from " + observerWithState.getState());
                }
                observerWithState.dispatchEvent(lifecycleOwner, eventUpFrom);
                popParentState();
                stateCalculateTargetState = calculateTargetState(lifecycleObserver);
            }
            if (!z) {
                sync();
            }
            this.addingObserverCounter--;
        }
    }

    @Override // androidx.lifecycle.Lifecycle
    public Lifecycle.State getCurrentState() {
        return this.state;
    }

    public void handleLifecycleEvent(Lifecycle.Event event) {
        event.getClass();
        enforceMainThreadIfNeeded("handleLifecycleEvent");
        moveToState(event.getTargetState());
    }

    @Override // androidx.lifecycle.Lifecycle
    public void removeObserver(LifecycleObserver lifecycleObserver) {
        lifecycleObserver.getClass();
        enforceMainThreadIfNeeded("removeObserver");
        this.observerMap.remove(lifecycleObserver);
    }

    public void setCurrentState(Lifecycle.State state) {
        state.getClass();
        enforceMainThreadIfNeeded("setCurrentState");
        moveToState(state);
    }
}
