package androidx.recyclerview.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class ChildHelper {
    final Callback mCallback;
    private View mViewInRemoveView;
    private int mRemoveStatus = 0;
    final Bucket mBucket = new Bucket();
    final List mHiddenViews = new ArrayList();

    class Bucket {
        long mData = 0;
        Bucket mNext;

        Bucket() {
        }

        private void ensureNext() {
            if (this.mNext == null) {
                this.mNext = new Bucket();
            }
        }

        void clear(int i) {
            if (i < 64) {
                this.mData &= ~(1 << i);
                return;
            }
            Bucket bucket = this.mNext;
            if (bucket != null) {
                bucket.clear(i - 64);
            }
        }

        int countOnesBefore(int i) {
            Bucket bucket = this.mNext;
            if (bucket == null) {
                if (i >= 64) {
                    return Long.bitCount(this.mData);
                }
                return Long.bitCount(((1 << i) - 1) & this.mData);
            }
            if (i >= 64) {
                return bucket.countOnesBefore(i - 64) + Long.bitCount(this.mData);
            }
            return Long.bitCount(((1 << i) - 1) & this.mData);
        }

        boolean get(int i) {
            if (i < 64) {
                return ((1 << i) & this.mData) != 0;
            }
            ensureNext();
            return this.mNext.get(i - 64);
        }

        void insert(int i, boolean z) {
            if (i >= 64) {
                ensureNext();
                this.mNext.insert(i - 64, z);
                return;
            }
            long j = this.mData;
            boolean z2 = (Long.MIN_VALUE & j) != 0;
            long j2 = (1 << i) - 1;
            this.mData = ((j & (~j2)) << 1) | (j & j2);
            if (z) {
                set(i);
            } else {
                clear(i);
            }
            if (z2 || this.mNext != null) {
                ensureNext();
                this.mNext.insert(0, z2);
            }
        }

        boolean remove(int i) {
            if (i >= 64) {
                ensureNext();
                return this.mNext.remove(i - 64);
            }
            long j = 1 << i;
            long j2 = this.mData;
            boolean z = (j2 & j) != 0;
            long j3 = j2 & (~j);
            this.mData = j3;
            long j4 = j - 1;
            this.mData = (j3 & j4) | Long.rotateRight((~j4) & j3, 1);
            Bucket bucket = this.mNext;
            if (bucket != null) {
                if (bucket.get(0)) {
                    set(63);
                }
                this.mNext.remove(0);
            }
            return z;
        }

        void reset() {
            this.mData = 0L;
            Bucket bucket = this.mNext;
            if (bucket != null) {
                bucket.reset();
            }
        }

        void set(int i) {
            if (i < 64) {
                this.mData |= 1 << i;
            } else {
                ensureNext();
                this.mNext.set(i - 64);
            }
        }

        public String toString() {
            if (this.mNext == null) {
                return Long.toBinaryString(this.mData);
            }
            return this.mNext.toString() + "xx" + Long.toBinaryString(this.mData);
        }
    }

    interface Callback {
        void addView(View view, int i);

        void attachViewToParent(View view, int i, ViewGroup.LayoutParams layoutParams);

        void detachViewFromParent(int i);

        View getChildAt(int i);

        int getChildCount();

        RecyclerView.ViewHolder getChildViewHolder(View view);

        int indexOfChild(View view);

        void onEnteredHiddenState(View view);

        void onLeftHiddenState(View view);

        void removeAllViews();

        void removeViewAt(int i);
    }

    ChildHelper(Callback callback) {
        this.mCallback = callback;
    }

    private int getOffset(int i) {
        if (i < 0) {
            return -1;
        }
        int childCount = this.mCallback.getChildCount();
        int i2 = i;
        while (i2 < childCount) {
            int iCountOnesBefore = i - (i2 - this.mBucket.countOnesBefore(i2));
            if (iCountOnesBefore == 0) {
                while (this.mBucket.get(i2)) {
                    i2++;
                }
                return i2;
            }
            i2 += iCountOnesBefore;
        }
        return -1;
    }

    private void hideViewInternal(View view) {
        this.mHiddenViews.add(view);
        this.mCallback.onEnteredHiddenState(view);
    }

    private boolean unhideViewInternal(View view) {
        if (!this.mHiddenViews.remove(view)) {
            return false;
        }
        this.mCallback.onLeftHiddenState(view);
        return true;
    }

    void addView(View view, int i, boolean z) {
        int childCount = i < 0 ? this.mCallback.getChildCount() : getOffset(i);
        this.mBucket.insert(childCount, z);
        if (z) {
            hideViewInternal(view);
        }
        this.mCallback.addView(view, childCount);
    }

    void addView(View view, boolean z) {
        addView(view, -1, z);
    }

    void attachViewToParent(View view, int i, ViewGroup.LayoutParams layoutParams, boolean z) {
        int childCount = i < 0 ? this.mCallback.getChildCount() : getOffset(i);
        this.mBucket.insert(childCount, z);
        if (z) {
            hideViewInternal(view);
        }
        this.mCallback.attachViewToParent(view, childCount, layoutParams);
    }

    void detachViewFromParent(int i) {
        int offset = getOffset(i);
        this.mBucket.remove(offset);
        this.mCallback.detachViewFromParent(offset);
    }

    View findHiddenNonRemovedView(int i) {
        int size = this.mHiddenViews.size();
        for (int i2 = 0; i2 < size; i2++) {
            View view = (View) this.mHiddenViews.get(i2);
            RecyclerView.ViewHolder childViewHolder = this.mCallback.getChildViewHolder(view);
            if (childViewHolder.getLayoutPosition() == i && !childViewHolder.isInvalid() && !childViewHolder.isRemoved()) {
                return view;
            }
        }
        return null;
    }

    View getChildAt(int i) {
        return this.mCallback.getChildAt(getOffset(i));
    }

    int getChildCount() {
        return this.mCallback.getChildCount() - this.mHiddenViews.size();
    }

    View getUnfilteredChildAt(int i) {
        return this.mCallback.getChildAt(i);
    }

    int getUnfilteredChildCount() {
        return this.mCallback.getChildCount();
    }

    void hide(View view) {
        int iIndexOfChild = this.mCallback.indexOfChild(view);
        if (iIndexOfChild >= 0) {
            this.mBucket.set(iIndexOfChild);
            hideViewInternal(view);
        } else {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        }
    }

    int indexOfChild(View view) {
        int iIndexOfChild = this.mCallback.indexOfChild(view);
        if (iIndexOfChild == -1 || this.mBucket.get(iIndexOfChild)) {
            return -1;
        }
        return iIndexOfChild - this.mBucket.countOnesBefore(iIndexOfChild);
    }

    boolean isHidden(View view) {
        return this.mHiddenViews.contains(view);
    }

    void removeAllViewsUnfiltered() {
        this.mBucket.reset();
        for (int size = this.mHiddenViews.size() - 1; size >= 0; size--) {
            this.mCallback.onLeftHiddenState((View) this.mHiddenViews.get(size));
            this.mHiddenViews.remove(size);
        }
        this.mCallback.removeAllViews();
    }

    void removeView(View view) {
        int i = this.mRemoveStatus;
        if (i == 1) {
            throw new IllegalStateException("Cannot call removeView(At) within removeView(At)");
        }
        if (i == 2) {
            throw new IllegalStateException("Cannot call removeView(At) within removeViewIfHidden");
        }
        try {
            this.mRemoveStatus = 1;
            this.mViewInRemoveView = view;
            int iIndexOfChild = this.mCallback.indexOfChild(view);
            if (iIndexOfChild >= 0) {
                if (this.mBucket.remove(iIndexOfChild)) {
                    unhideViewInternal(view);
                }
                this.mCallback.removeViewAt(iIndexOfChild);
            }
            this.mRemoveStatus = 0;
            this.mViewInRemoveView = null;
        } catch (Throwable th) {
            this.mRemoveStatus = 0;
            this.mViewInRemoveView = null;
            throw th;
        }
    }

    void removeViewAt(int i) {
        int i2 = this.mRemoveStatus;
        if (i2 == 1) {
            throw new IllegalStateException("Cannot call removeView(At) within removeView(At)");
        }
        if (i2 == 2) {
            throw new IllegalStateException("Cannot call removeView(At) within removeViewIfHidden");
        }
        try {
            int offset = getOffset(i);
            View childAt = this.mCallback.getChildAt(offset);
            if (childAt != null) {
                this.mRemoveStatus = 1;
                this.mViewInRemoveView = childAt;
                if (this.mBucket.remove(offset)) {
                    unhideViewInternal(childAt);
                }
                this.mCallback.removeViewAt(offset);
            }
            this.mRemoveStatus = 0;
            this.mViewInRemoveView = null;
        } catch (Throwable th) {
            this.mRemoveStatus = 0;
            this.mViewInRemoveView = null;
            throw th;
        }
    }

    boolean removeViewIfHidden(View view) {
        int i = this.mRemoveStatus;
        if (i == 1) {
            if (this.mViewInRemoveView == view) {
                return false;
            }
            throw new IllegalStateException("Cannot call removeViewIfHidden within removeView(At) for a different view");
        }
        if (i == 2) {
            throw new IllegalStateException("Cannot call removeViewIfHidden within removeViewIfHidden");
        }
        try {
            this.mRemoveStatus = 2;
            int iIndexOfChild = this.mCallback.indexOfChild(view);
            if (iIndexOfChild == -1) {
                unhideViewInternal(view);
                return true;
            }
            if (!this.mBucket.get(iIndexOfChild)) {
                return false;
            }
            this.mBucket.remove(iIndexOfChild);
            unhideViewInternal(view);
            this.mCallback.removeViewAt(iIndexOfChild);
            return true;
        } finally {
            this.mRemoveStatus = 0;
        }
    }

    public String toString() {
        return this.mBucket.toString() + ", hidden list:" + this.mHiddenViews.size();
    }

    void unhide(View view) {
        int iIndexOfChild = this.mCallback.indexOfChild(view);
        if (iIndexOfChild < 0) {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        }
        if (this.mBucket.get(iIndexOfChild)) {
            this.mBucket.clear(iIndexOfChild);
            unhideViewInternal(view);
        } else {
            throw new RuntimeException("trying to unhide a view that was not hidden" + view);
        }
    }
}
