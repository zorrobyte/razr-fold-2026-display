package androidx.leanback.widget;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import androidx.collection.LruCache;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class ViewsStateBundle {
    private LruCache mChildStates;
    private int mSavePolicy = 0;
    private int mLimitNumber = 100;

    ViewsStateBundle() {
    }

    static String getSaveStatesKey(int i) {
        return Integer.toString(i);
    }

    private void saveViewUnchecked(View view, int i) {
        if (this.mChildStates != null) {
            String saveStatesKey = getSaveStatesKey(i);
            SparseArray<Parcelable> sparseArray = new SparseArray<>();
            view.saveHierarchyState(sparseArray);
            this.mChildStates.put(saveStatesKey, sparseArray);
        }
    }

    void clear() {
        LruCache lruCache = this.mChildStates;
        if (lruCache != null) {
            lruCache.evictAll();
        }
    }

    void loadFromBundle(Bundle bundle) {
        LruCache lruCache = this.mChildStates;
        if (lruCache == null || bundle == null) {
            return;
        }
        lruCache.evictAll();
        for (String str : bundle.keySet()) {
            this.mChildStates.put(str, bundle.getSparseParcelableArray(str));
        }
    }

    void loadView(View view, int i) {
        if (this.mChildStates != null) {
            SparseArray<Parcelable> sparseArray = (SparseArray) this.mChildStates.remove(getSaveStatesKey(i));
            if (sparseArray != null) {
                view.restoreHierarchyState(sparseArray);
            }
        }
    }

    void remove(int i) {
        LruCache lruCache = this.mChildStates;
        if (lruCache == null || lruCache.size() == 0) {
            return;
        }
        this.mChildStates.remove(getSaveStatesKey(i));
    }

    Bundle saveAsBundle() {
        LruCache lruCache = this.mChildStates;
        if (lruCache == null || lruCache.size() == 0) {
            return null;
        }
        Map mapSnapshot = this.mChildStates.snapshot();
        Bundle bundle = new Bundle();
        for (Map.Entry entry : mapSnapshot.entrySet()) {
            bundle.putSparseParcelableArray((String) entry.getKey(), (SparseArray) entry.getValue());
        }
        return bundle;
    }

    void saveOffscreenView(View view, int i) {
        int i2 = this.mSavePolicy;
        if (i2 == 1) {
            remove(i);
        } else if (i2 == 2 || i2 == 3) {
            saveViewUnchecked(view, i);
        }
    }

    Bundle saveOnScreenView(Bundle bundle, View view, int i) {
        if (this.mSavePolicy != 0) {
            String saveStatesKey = getSaveStatesKey(i);
            SparseArray<Parcelable> sparseArray = new SparseArray<>();
            view.saveHierarchyState(sparseArray);
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray(saveStatesKey, sparseArray);
        }
        return bundle;
    }
}
