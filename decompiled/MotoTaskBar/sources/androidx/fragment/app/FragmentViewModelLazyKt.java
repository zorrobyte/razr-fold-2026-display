package androidx.fragment.app;

import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import kotlin.Lazy;
import kotlin.jvm.functions.Function0;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: FragmentViewModelLazy.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentViewModelLazyKt {
    public static final Lazy createViewModelLazy(final Fragment fragment, KClass kClass, Function0 function0, Function0 function02, Function0 function03) {
        if (function03 == null) {
            function03 = new Function0() { // from class: androidx.fragment.app.FragmentViewModelLazyKt$createViewModelLazy$factoryPromise$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final ViewModelProvider.Factory mo2224invoke() {
                    return fragment.getDefaultViewModelProviderFactory();
                }
            };
        }
        return new ViewModelLazy(kClass, function0, function03, function02);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: viewModels$lambda-1, reason: not valid java name */
    public static final ViewModelStoreOwner m1066viewModels$lambda1(Lazy lazy) {
        return (ViewModelStoreOwner) lazy.getValue();
    }
}
