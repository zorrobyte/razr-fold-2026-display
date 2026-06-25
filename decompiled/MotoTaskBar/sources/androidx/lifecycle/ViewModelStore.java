package androidx.lifecycle;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: ViewModelStore.kt */
/* JADX INFO: loaded from: classes.dex */
public class ViewModelStore {
    private final Map map = new LinkedHashMap();

    public final void clear() {
        Iterator it = this.map.values().iterator();
        while (it.hasNext()) {
            ((ViewModel) it.next()).clear$lifecycle_viewmodel_release();
        }
        this.map.clear();
    }

    public final ViewModel get(String str) {
        str.getClass();
        return (ViewModel) this.map.get(str);
    }

    public final Set keys() {
        return new HashSet(this.map.keySet());
    }

    public final void put(String str, ViewModel viewModel) {
        str.getClass();
        viewModel.getClass();
        ViewModel viewModel2 = (ViewModel) this.map.put(str, viewModel);
        if (viewModel2 != null) {
            viewModel2.clear$lifecycle_viewmodel_release();
        }
    }
}
