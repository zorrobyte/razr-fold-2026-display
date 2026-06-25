package androidx.lifecycle;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import kotlin.Lazy;
import kotlin.jvm.functions.Function0;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: ViewModelLazy.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ViewModelLazy implements Lazy {
    private ViewModel cached;
    private final Function0 extrasProducer;
    private final Function0 factoryProducer;
    private final Function0 storeProducer;
    private final KClass viewModelClass;

    public ViewModelLazy(KClass kClass, Function0 function0, Function0 function02, Function0 function03) {
        kClass.getClass();
        function0.getClass();
        function02.getClass();
        function03.getClass();
        this.viewModelClass = kClass;
        this.storeProducer = function0;
        this.factoryProducer = function02;
        this.extrasProducer = function03;
    }

    @Override // kotlin.Lazy
    public ViewModel getValue() {
        ViewModel viewModel = this.cached;
        if (viewModel != null) {
            return viewModel;
        }
        ViewModel viewModel2 = ViewModelProvider.Companion.create((ViewModelStore) this.storeProducer.mo2224invoke(), (ViewModelProvider.Factory) this.factoryProducer.mo2224invoke(), (CreationExtras) this.extrasProducer.mo2224invoke()).get(this.viewModelClass);
        this.cached = viewModel2;
        return viewModel2;
    }

    @Override // kotlin.Lazy
    public boolean isInitialized() {
        return this.cached != null;
    }
}
