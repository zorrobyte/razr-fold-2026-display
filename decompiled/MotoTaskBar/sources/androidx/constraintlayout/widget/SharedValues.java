package androidx.constraintlayout.widget;

import android.util.SparseIntArray;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public class SharedValues {
    private SparseIntArray mValues = new SparseIntArray();
    private HashMap mValuesListeners = new HashMap();

    public interface SharedValuesListener {
    }

    public void addListener(int i, SharedValuesListener sharedValuesListener) {
        HashSet hashSet = (HashSet) this.mValuesListeners.get(Integer.valueOf(i));
        if (hashSet == null) {
            hashSet = new HashSet();
            this.mValuesListeners.put(Integer.valueOf(i), hashSet);
        }
        hashSet.add(new WeakReference(sharedValuesListener));
    }
}
