package androidx.lifecycle.viewmodel.compose;

import androidx.compose.runtime.Composer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import kotlin.reflect.KClass;

/* JADX INFO: loaded from: classes.dex */
public abstract class ViewModelKt {
    public static final ViewModel get(ViewModelStoreOwner viewModelStoreOwner, KClass kClass, String str, ViewModelProvider.Factory factory, CreationExtras creationExtras) {
        return ViewModelKt__ViewModelKt.get(viewModelStoreOwner, kClass, str, factory, creationExtras);
    }

    public static final ViewModel viewModel(KClass kClass, ViewModelStoreOwner viewModelStoreOwner, String str, ViewModelProvider.Factory factory, CreationExtras creationExtras, Composer composer, int i, int i2) {
        return ViewModelKt__ViewModelKt.viewModel(kClass, viewModelStoreOwner, str, factory, creationExtras, composer, i, i2);
    }
}
