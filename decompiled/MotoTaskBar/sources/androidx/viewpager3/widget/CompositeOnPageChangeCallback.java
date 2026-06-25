package androidx.viewpager3.widget;

import androidx.viewpager3.widget.ViewPager3;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class CompositeOnPageChangeCallback extends ViewPager3.OnPageChangeCallback {
    private final List mCallbacks;

    CompositeOnPageChangeCallback(int i) {
        this.mCallbacks = new ArrayList(i);
    }

    private void throwCallbackListModifiedWhileInUse(ConcurrentModificationException concurrentModificationException) {
        throw new IllegalStateException("Adding and removing callbacks during dispatch to callbacks is not supported", concurrentModificationException);
    }

    void addOnPageChangeCallback(ViewPager3.OnPageChangeCallback onPageChangeCallback) {
        this.mCallbacks.add(onPageChangeCallback);
    }

    @Override // androidx.viewpager3.widget.ViewPager3.OnPageChangeCallback
    public void onPageScrollStateChanged(int i) {
        try {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ViewPager3.OnPageChangeCallback) it.next()).onPageScrollStateChanged(i);
            }
        } catch (ConcurrentModificationException e) {
            throwCallbackListModifiedWhileInUse(e);
        }
    }

    @Override // androidx.viewpager3.widget.ViewPager3.OnPageChangeCallback
    public void onPageScrolled(int i, float f, int i2) {
        try {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ViewPager3.OnPageChangeCallback) it.next()).onPageScrolled(i, f, i2);
            }
        } catch (ConcurrentModificationException e) {
            throwCallbackListModifiedWhileInUse(e);
        }
    }

    @Override // androidx.viewpager3.widget.ViewPager3.OnPageChangeCallback
    public void onPageSelected(int i) {
        try {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((ViewPager3.OnPageChangeCallback) it.next()).onPageSelected(i);
            }
        } catch (ConcurrentModificationException e) {
            throwCallbackListModifiedWhileInUse(e);
        }
    }

    void removeOnPageChangeCallback(ViewPager3.OnPageChangeCallback onPageChangeCallback) {
        this.mCallbacks.remove(onPageChangeCallback);
    }
}
