package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: ViewModelProviderImpl.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ViewModelProviderImpl_androidKt {
    public static final ViewModel createViewModel(ViewModelProvider.Factory factory, KClass kClass, CreationExtras creationExtras) {
        factory.getClass();
        kClass.getClass();
        creationExtras.getClass();
        try {
            try {
                return factory.create(kClass, creationExtras);
            } catch (AbstractMethodError unused) {
                return factory.create(JvmClassMappingKt.getJavaClass(kClass));
            }
        } catch (AbstractMethodError unused2) {
            return factory.create(JvmClassMappingKt.getJavaClass(kClass), creationExtras);
        }
    }
}
