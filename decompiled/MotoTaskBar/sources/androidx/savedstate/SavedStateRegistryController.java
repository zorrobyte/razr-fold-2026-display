package androidx.savedstate;

import android.os.Bundle;
import androidx.savedstate.internal.SavedStateRegistryImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SavedStateRegistryController.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SavedStateRegistryController {
    public static final Companion Companion = new Companion(null);
    private final SavedStateRegistryImpl impl;
    private final SavedStateRegistry savedStateRegistry;

    /* JADX INFO: compiled from: SavedStateRegistryController.android.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final SavedStateRegistryController create(final SavedStateRegistryOwner savedStateRegistryOwner) {
            savedStateRegistryOwner.getClass();
            return new SavedStateRegistryController(new SavedStateRegistryImpl(savedStateRegistryOwner, new Function0() { // from class: androidx.savedstate.SavedStateRegistryController$Companion$create$impl$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public /* bridge */ /* synthetic */ Object mo2224invoke() {
                    m1103invoke();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                public final void m1103invoke() {
                    savedStateRegistryOwner.getLifecycle().addObserver(new Recreator(savedStateRegistryOwner));
                }
            }), null);
        }
    }

    private SavedStateRegistryController(SavedStateRegistryImpl savedStateRegistryImpl) {
        this.impl = savedStateRegistryImpl;
        this.savedStateRegistry = new SavedStateRegistry(savedStateRegistryImpl);
    }

    public /* synthetic */ SavedStateRegistryController(SavedStateRegistryImpl savedStateRegistryImpl, DefaultConstructorMarker defaultConstructorMarker) {
        this(savedStateRegistryImpl);
    }

    public static final SavedStateRegistryController create(SavedStateRegistryOwner savedStateRegistryOwner) {
        return Companion.create(savedStateRegistryOwner);
    }

    public final SavedStateRegistry getSavedStateRegistry() {
        return this.savedStateRegistry;
    }

    public final void performAttach() {
        this.impl.performAttach();
    }

    public final void performRestore(Bundle bundle) {
        this.impl.performRestore$savedstate_release(bundle);
    }

    public final void performSave(Bundle bundle) {
        bundle.getClass();
        this.impl.performSave$savedstate_release(bundle);
    }
}
