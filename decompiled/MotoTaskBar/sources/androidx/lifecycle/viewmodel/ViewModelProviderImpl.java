package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.internal.SynchronizedObject;
import androidx.lifecycle.viewmodel.internal.ViewModelProviders;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: ViewModelProviderImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ViewModelProviderImpl {
    private final CreationExtras defaultExtras;
    private final ViewModelProvider.Factory factory;
    private final SynchronizedObject lock;
    private final ViewModelStore store;

    public ViewModelProviderImpl(ViewModelStore viewModelStore, ViewModelProvider.Factory factory, CreationExtras creationExtras) {
        viewModelStore.getClass();
        factory.getClass();
        creationExtras.getClass();
        this.store = viewModelStore;
        this.factory = factory;
        this.defaultExtras = creationExtras;
        this.lock = new SynchronizedObject();
    }

    public static /* synthetic */ ViewModel getViewModel$lifecycle_viewmodel_release$default(ViewModelProviderImpl viewModelProviderImpl, KClass kClass, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = ViewModelProviders.INSTANCE.getDefaultKey$lifecycle_viewmodel_release(kClass);
        }
        return viewModelProviderImpl.getViewModel$lifecycle_viewmodel_release(kClass, str);
    }

    public final ViewModel getViewModel$lifecycle_viewmodel_release(KClass kClass, String str) {
        ViewModel viewModelCreateViewModel;
        kClass.getClass();
        str.getClass();
        synchronized (this.lock) {
            try {
                viewModelCreateViewModel = this.store.get(str);
                if (kClass.isInstance(viewModelCreateViewModel)) {
                    if (this.factory instanceof ViewModelProvider.OnRequeryFactory) {
                        ViewModelProvider.OnRequeryFactory onRequeryFactory = (ViewModelProvider.OnRequeryFactory) this.factory;
                        viewModelCreateViewModel.getClass();
                        onRequeryFactory.onRequery(viewModelCreateViewModel);
                    }
                    viewModelCreateViewModel.getClass();
                } else {
                    MutableCreationExtras mutableCreationExtras = new MutableCreationExtras(this.defaultExtras);
                    mutableCreationExtras.set(ViewModelProvider.VIEW_MODEL_KEY, str);
                    viewModelCreateViewModel = ViewModelProviderImpl_androidKt.createViewModel(this.factory, kClass, mutableCreationExtras);
                    this.store.put(str, viewModelCreateViewModel);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return viewModelCreateViewModel;
    }
}
