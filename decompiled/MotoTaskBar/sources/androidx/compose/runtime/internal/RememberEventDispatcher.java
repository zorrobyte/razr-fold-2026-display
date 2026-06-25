package androidx.compose.runtime.internal;

import androidx.collection.MutableIntList;
import androidx.collection.MutableScatterMap;
import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSetKt;
import androidx.compose.runtime.ComposeNodeLifecycleCallback;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RememberManager;
import androidx.compose.runtime.RememberObserver;
import androidx.compose.runtime.RememberObserverHolder;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.tooling.CompositionErrorContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: RememberEventDispatcher.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RememberEventDispatcher implements RememberManager {
    private final Set abandoning;
    private final MutableIntList afters;
    private MutableVector currentRememberingList;
    private final MutableVector leaving;
    private ArrayList nestedRemembersLists;
    private MutableScatterMap pausedPlaceholders;
    private final List pending;
    private final MutableIntList priorities;
    private MutableScatterSet releasing;
    private final MutableVector remembering;
    private final MutableVector sideEffects;
    private final CompositionErrorContext traceContext;

    public RememberEventDispatcher(Set set, CompositionErrorContext compositionErrorContext) {
        this.abandoning = set;
        this.traceContext = compositionErrorContext;
        MutableVector mutableVector = new MutableVector(new RememberObserverHolder[16], 0);
        this.remembering = mutableVector;
        this.currentRememberingList = mutableVector;
        this.leaving = new MutableVector(new Object[16], 0);
        this.sideEffects = new MutableVector(new Function0[16], 0);
        this.pending = new ArrayList();
        this.priorities = new MutableIntList(0, 1, null);
        this.afters = new MutableIntList(0, 1, null);
    }

    private final void dispatchRememberList(MutableVector mutableVector) {
        Object[] objArr = mutableVector.content;
        int size = mutableVector.getSize();
        for (int i = 0; i < size; i++) {
            RememberObserverHolder rememberObserverHolder = (RememberObserverHolder) objArr[i];
            RememberObserver wrapped = rememberObserverHolder.getWrapped();
            this.abandoning.remove(wrapped);
            try {
                wrapped.onRemembered();
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                CompositionErrorContext compositionErrorContext = this.traceContext;
                if (compositionErrorContext != null) {
                    compositionErrorContext.attachComposeStackTrace(th, rememberObserverHolder);
                }
                throw th;
            }
        }
    }

    private final void processPendingLeaving(int i) {
        if (this.pending.isEmpty()) {
            return;
        }
        int i2 = 0;
        int i3 = 0;
        List listMutableListOf = null;
        MutableIntList mutableIntList = null;
        MutableIntList mutableIntList2 = null;
        while (true) {
            MutableIntList mutableIntList3 = this.afters;
            if (i3 >= mutableIntList3._size) {
                break;
            }
            if (i <= mutableIntList3.get(i3)) {
                Object objRemove = this.pending.remove(i3);
                int iRemoveAt = this.afters.removeAt(i3);
                int iRemoveAt2 = this.priorities.removeAt(i3);
                if (listMutableListOf == null) {
                    listMutableListOf = CollectionsKt.mutableListOf(objRemove);
                    mutableIntList2 = new MutableIntList(0, 1, null);
                    mutableIntList2.add(iRemoveAt);
                    mutableIntList = new MutableIntList(0, 1, null);
                    mutableIntList.add(iRemoveAt2);
                } else {
                    mutableIntList.getClass();
                    mutableIntList2.getClass();
                    listMutableListOf.add(objRemove);
                    mutableIntList2.add(iRemoveAt);
                    mutableIntList.add(iRemoveAt2);
                }
            } else {
                i3++;
            }
        }
        if (listMutableListOf != null) {
            mutableIntList.getClass();
            mutableIntList2.getClass();
            int size = listMutableListOf.size() - 1;
            while (i2 < size) {
                int i4 = i2 + 1;
                int size2 = listMutableListOf.size();
                for (int i5 = i4; i5 < size2; i5++) {
                    int i6 = mutableIntList2.get(i2);
                    int i7 = mutableIntList2.get(i5);
                    if (i6 < i7 || (i7 == i6 && mutableIntList.get(i2) < mutableIntList.get(i5))) {
                        RememberEventDispatcherKt.swap(listMutableListOf, i2, i5);
                        RememberEventDispatcherKt.swap(mutableIntList, i2, i5);
                        RememberEventDispatcherKt.swap(mutableIntList2, i2, i5);
                    }
                }
                i2 = i4;
            }
            MutableVector mutableVector = this.leaving;
            mutableVector.addAll(mutableVector.getSize(), listMutableListOf);
        }
    }

    private final void recordLeaving(Object obj, int i, int i2, int i3) {
        processPendingLeaving(i);
        if (i3 < 0 || i3 >= i) {
            this.leaving.add(obj);
            return;
        }
        this.pending.add(obj);
        this.priorities.add(i2);
        this.afters.add(i3);
    }

    public final void dispatchAbandons() {
        if (this.abandoning.isEmpty()) {
            return;
        }
        Object objBeginSection = Trace.INSTANCE.beginSection("Compose:abandons");
        try {
            Iterator it = this.abandoning.iterator();
            while (it.hasNext()) {
                RememberObserver rememberObserver = (RememberObserver) it.next();
                it.remove();
                rememberObserver.onAbandoned();
            }
            Unit unit = Unit.INSTANCE;
            Trace.INSTANCE.endSection(objBeginSection);
        } catch (Throwable th) {
            Trace.INSTANCE.endSection(objBeginSection);
            throw th;
        }
    }

    public final void dispatchRememberObservers() {
        Object objBeginSection;
        processPendingLeaving(Integer.MIN_VALUE);
        if (this.leaving.getSize() != 0) {
            objBeginSection = Trace.INSTANCE.beginSection("Compose:onForgotten");
            try {
                MutableScatterSet mutableScatterSet = this.releasing;
                for (int size = this.leaving.getSize() - 1; -1 < size; size--) {
                    Object obj = this.leaving.content[size];
                    try {
                        if (obj instanceof RememberObserverHolder) {
                            RememberObserver wrapped = ((RememberObserverHolder) obj).getWrapped();
                            this.abandoning.remove(wrapped);
                            wrapped.onForgotten();
                        }
                        if (obj instanceof ComposeNodeLifecycleCallback) {
                            if (mutableScatterSet == null || !mutableScatterSet.contains(obj)) {
                                ((ComposeNodeLifecycleCallback) obj).onDeactivate();
                            } else {
                                ((ComposeNodeLifecycleCallback) obj).onRelease();
                            }
                        }
                        Unit unit = Unit.INSTANCE;
                    } catch (Throwable th) {
                        CompositionErrorContext compositionErrorContext = this.traceContext;
                        if (compositionErrorContext != null) {
                            compositionErrorContext.attachComposeStackTrace(th, obj);
                        }
                        throw th;
                    }
                }
                Unit unit2 = Unit.INSTANCE;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        if (this.remembering.getSize() != 0) {
            Trace trace = Trace.INSTANCE;
            objBeginSection = trace.beginSection("Compose:onRemembered");
            try {
                dispatchRememberList(this.remembering);
                Unit unit3 = Unit.INSTANCE;
                trace.endSection(objBeginSection);
            } finally {
                Trace.INSTANCE.endSection(objBeginSection);
            }
        }
    }

    public final void dispatchSideEffects() {
        if (this.sideEffects.getSize() != 0) {
            Object objBeginSection = Trace.INSTANCE.beginSection("Compose:sideeffects");
            try {
                MutableVector mutableVector = this.sideEffects;
                Object[] objArr = mutableVector.content;
                int size = mutableVector.getSize();
                for (int i = 0; i < size; i++) {
                    ((Function0) objArr[i]).mo2224invoke();
                }
                this.sideEffects.clear();
                Unit unit = Unit.INSTANCE;
                Trace.INSTANCE.endSection(objBeginSection);
            } catch (Throwable th) {
                Trace.INSTANCE.endSection(objBeginSection);
                throw th;
            }
        }
    }

    @Override // androidx.compose.runtime.RememberManager
    public void endResumingScope(RecomposeScopeImpl recomposeScopeImpl) {
        MutableScatterMap mutableScatterMap = this.pausedPlaceholders;
        if (mutableScatterMap != null) {
        }
    }

    @Override // androidx.compose.runtime.RememberManager
    public void forgetting(RememberObserverHolder rememberObserverHolder, int i, int i2, int i3) {
        recordLeaving(rememberObserverHolder, i, i2, i3);
    }

    @Override // androidx.compose.runtime.RememberManager
    public void releasing(ComposeNodeLifecycleCallback composeNodeLifecycleCallback, int i, int i2, int i3) {
        MutableScatterSet mutableScatterSetMutableScatterSetOf = this.releasing;
        if (mutableScatterSetMutableScatterSetOf == null) {
            mutableScatterSetMutableScatterSetOf = ScatterSetKt.mutableScatterSetOf();
            this.releasing = mutableScatterSetMutableScatterSetOf;
        }
        mutableScatterSetMutableScatterSetOf.plusAssign(composeNodeLifecycleCallback);
        recordLeaving(composeNodeLifecycleCallback, i, i2, i3);
    }

    @Override // androidx.compose.runtime.RememberManager
    public void remembering(RememberObserverHolder rememberObserverHolder) {
        this.currentRememberingList.add(rememberObserverHolder);
    }

    @Override // androidx.compose.runtime.RememberManager
    public void startResumingScope(RecomposeScopeImpl recomposeScopeImpl) {
        MutableScatterMap mutableScatterMap = this.pausedPlaceholders;
        if (mutableScatterMap != null) {
        }
    }
}
