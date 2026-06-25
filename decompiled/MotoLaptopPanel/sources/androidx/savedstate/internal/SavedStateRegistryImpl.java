package androidx.savedstate.internal;

import android.os.Bundle;
import androidx.core.os.BundleKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.savedstate.SavedStateReader;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import androidx.savedstate.SavedStateWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SavedStateRegistryImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SavedStateRegistryImpl {
    private static final Companion Companion = new Companion(null);
    private boolean attached;
    private boolean isAllowingSavingState;
    private boolean isRestored;
    private final Map keyToProviders;
    private final SynchronizedObject lock;
    private final Function0 onAttach;
    private final SavedStateRegistryOwner owner;
    private Bundle restoredState;

    /* JADX INFO: compiled from: SavedStateRegistryImpl.kt */
    final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SavedStateRegistryImpl(SavedStateRegistryOwner savedStateRegistryOwner, Function0 function0) {
        savedStateRegistryOwner.getClass();
        function0.getClass();
        this.owner = savedStateRegistryOwner;
        this.onAttach = function0;
        this.lock = new SynchronizedObject();
        this.keyToProviders = new LinkedHashMap();
        this.isAllowingSavingState = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void performAttach$lambda$11(SavedStateRegistryImpl savedStateRegistryImpl, LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        lifecycleOwner.getClass();
        event.getClass();
        if (event == Lifecycle.Event.ON_START) {
            savedStateRegistryImpl.isAllowingSavingState = true;
        } else if (event == Lifecycle.Event.ON_STOP) {
            savedStateRegistryImpl.isAllowingSavingState = false;
        }
    }

    public final Bundle consumeRestoredStateForKey(String str) {
        str.getClass();
        if (!this.isRestored) {
            throw new IllegalStateException("You can 'consumeRestoredStateForKey' only after the corresponding component has moved to the 'CREATED' state");
        }
        Bundle bundle = this.restoredState;
        if (bundle == null) {
            return null;
        }
        Bundle bundleM2035constructorimpl = SavedStateReader.m2035constructorimpl(bundle);
        Bundle bundleM2037getSavedStateimpl = SavedStateReader.m2036containsimpl(bundleM2035constructorimpl, str) ? SavedStateReader.m2037getSavedStateimpl(bundleM2035constructorimpl, str) : null;
        SavedStateWriter.m2047removeimpl(SavedStateWriter.m2043constructorimpl(bundle), str);
        if (SavedStateReader.m2040isEmptyimpl(SavedStateReader.m2035constructorimpl(bundle))) {
            this.restoredState = null;
        }
        return bundleM2037getSavedStateimpl;
    }

    public final SavedStateRegistry.SavedStateProvider getSavedStateProvider(String str) {
        SavedStateRegistry.SavedStateProvider savedStateProvider;
        str.getClass();
        synchronized (this.lock) {
            Iterator it = this.keyToProviders.entrySet().iterator();
            do {
                savedStateProvider = null;
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry entry = (Map.Entry) it.next();
                String str2 = (String) entry.getKey();
                SavedStateRegistry.SavedStateProvider savedStateProvider2 = (SavedStateRegistry.SavedStateProvider) entry.getValue();
                if (Intrinsics.areEqual(str2, str)) {
                    savedStateProvider = savedStateProvider2;
                }
            } while (savedStateProvider == null);
        }
        return savedStateProvider;
    }

    public final boolean isAllowingSavingState$savedstate_release() {
        return this.isAllowingSavingState;
    }

    public final void performAttach() {
        if (this.owner.getLifecycle().getCurrentState() != Lifecycle.State.INITIALIZED) {
            throw new IllegalStateException("Restarter must be created only during owner's initialization stage");
        }
        if (this.attached) {
            throw new IllegalStateException("SavedStateRegistry was already attached.");
        }
        this.onAttach.invoke();
        this.owner.getLifecycle().addObserver(new LifecycleEventObserver() { // from class: androidx.savedstate.internal.SavedStateRegistryImpl$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                SavedStateRegistryImpl.performAttach$lambda$11(this.f$0, lifecycleOwner, event);
            }
        });
        this.attached = true;
    }

    public final void performRestore$savedstate_release(Bundle bundle) {
        if (!this.attached) {
            performAttach();
        }
        if (this.owner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            throw new IllegalStateException(("performRestore cannot be called when owner is " + this.owner.getLifecycle().getCurrentState()).toString());
        }
        if (this.isRestored) {
            throw new IllegalStateException("SavedStateRegistry was already restored.");
        }
        Bundle bundleM2037getSavedStateimpl = null;
        if (bundle != null) {
            Bundle bundleM2035constructorimpl = SavedStateReader.m2035constructorimpl(bundle);
            if (SavedStateReader.m2036containsimpl(bundleM2035constructorimpl, "androidx.lifecycle.BundlableSavedStateRegistry.key")) {
                bundleM2037getSavedStateimpl = SavedStateReader.m2037getSavedStateimpl(bundleM2035constructorimpl, "androidx.lifecycle.BundlableSavedStateRegistry.key");
            }
        }
        this.restoredState = bundleM2037getSavedStateimpl;
        this.isRestored = true;
    }

    public final void performSave$savedstate_release(Bundle bundle) {
        Pair[] pairArr;
        bundle.getClass();
        Map mapEmptyMap = MapsKt.emptyMap();
        if (mapEmptyMap.isEmpty()) {
            pairArr = new Pair[0];
        } else {
            ArrayList arrayList = new ArrayList(mapEmptyMap.size());
            for (Map.Entry entry : mapEmptyMap.entrySet()) {
                arrayList.add(TuplesKt.to((String) entry.getKey(), entry.getValue()));
            }
            pairArr = (Pair[]) arrayList.toArray(new Pair[0]);
        }
        Bundle bundleBundleOf = BundleKt.bundleOf((Pair[]) Arrays.copyOf(pairArr, pairArr.length));
        Bundle bundleM2043constructorimpl = SavedStateWriter.m2043constructorimpl(bundleBundleOf);
        Bundle bundle2 = this.restoredState;
        if (bundle2 != null) {
            SavedStateWriter.m2044putAllimpl(bundleM2043constructorimpl, bundle2);
        }
        synchronized (this.lock) {
            try {
                for (Map.Entry entry2 : this.keyToProviders.entrySet()) {
                    SavedStateWriter.m2045putSavedStateimpl(bundleM2043constructorimpl, (String) entry2.getKey(), ((SavedStateRegistry.SavedStateProvider) entry2.getValue()).saveState());
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (SavedStateReader.m2040isEmptyimpl(SavedStateReader.m2035constructorimpl(bundleBundleOf))) {
            return;
        }
        SavedStateWriter.m2045putSavedStateimpl(SavedStateWriter.m2043constructorimpl(bundle), "androidx.lifecycle.BundlableSavedStateRegistry.key", bundleBundleOf);
    }

    public final void registerSavedStateProvider(String str, SavedStateRegistry.SavedStateProvider savedStateProvider) {
        str.getClass();
        savedStateProvider.getClass();
        synchronized (this.lock) {
            if (this.keyToProviders.containsKey(str)) {
                throw new IllegalArgumentException("SavedStateProvider with the given key is already registered");
            }
            this.keyToProviders.put(str, savedStateProvider);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void unregisterSavedStateProvider(String str) {
        str.getClass();
        synchronized (this.lock) {
        }
    }
}
