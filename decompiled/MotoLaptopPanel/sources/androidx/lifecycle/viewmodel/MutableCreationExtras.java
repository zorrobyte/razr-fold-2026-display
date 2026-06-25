package androidx.lifecycle.viewmodel;

import androidx.lifecycle.viewmodel.CreationExtras;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: CreationExtras.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MutableCreationExtras extends CreationExtras {
    /* JADX WARN: Multi-variable type inference failed */
    public MutableCreationExtras() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MutableCreationExtras(CreationExtras creationExtras) {
        this(creationExtras.getExtras$lifecycle_viewmodel_release());
        creationExtras.getClass();
    }

    public /* synthetic */ MutableCreationExtras(CreationExtras creationExtras, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CreationExtras.Empty.INSTANCE : creationExtras);
    }

    public MutableCreationExtras(Map map) {
        map.getClass();
        getExtras$lifecycle_viewmodel_release().putAll(map);
    }

    @Override // androidx.lifecycle.viewmodel.CreationExtras
    public Object get(CreationExtras.Key key) {
        key.getClass();
        return getExtras$lifecycle_viewmodel_release().get(key);
    }

    public final void set(CreationExtras.Key key, Object obj) {
        key.getClass();
        getExtras$lifecycle_viewmodel_release().put(key, obj);
    }
}
