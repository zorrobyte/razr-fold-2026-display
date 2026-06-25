package androidx.lifecycle;

import android.view.View;
import androidx.lifecycle.viewmodel.R$id;

/* JADX INFO: compiled from: ViewTreeViewModelStoreOwner.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ViewTreeViewModelStoreOwner {
    public static final void set(View view, ViewModelStoreOwner viewModelStoreOwner) {
        view.getClass();
        view.setTag(R$id.view_tree_view_model_store_owner, viewModelStoreOwner);
    }
}
