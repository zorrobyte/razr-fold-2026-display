package androidx.compose.ui.platform;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.collection.MutableObjectIntMap;
import androidx.collection.MutableScatterMap;
import androidx.collection.MutableScatterSet;
import androidx.collection.ObjectIntMapKt;
import androidx.collection.ScatterMapKt;
import androidx.collection.ScatterSetKt;
import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.focus.FocusInteropUtils_androidKt;
import androidx.compose.ui.focus.TwoDimensionalFocusSearchKt;
import androidx.compose.ui.graphics.RectHelper_androidKt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FocusFinderCompat.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FocusFinderCompat {
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private static final FocusFinderCompat$Companion$FocusFinderThreadLocal$1 FocusFinderThreadLocal = new ThreadLocal() { // from class: androidx.compose.ui.platform.FocusFinderCompat$Companion$FocusFinderThreadLocal$1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public FocusFinderCompat initialValue() {
            return new FocusFinderCompat();
        }
    };
    private final Rect cachedFocusedRect = new Rect();
    private final Rect bestCandidateRect = new Rect();
    private final Rect otherRect = new Rect();
    private final UserSpecifiedFocusComparator userSpecifiedFocusComparator = new UserSpecifiedFocusComparator(new UserSpecifiedFocusComparator.NextFocusGetter() { // from class: androidx.compose.ui.platform.FocusFinderCompat$$ExternalSyntheticLambda0
        @Override // androidx.compose.ui.platform.FocusFinderCompat.UserSpecifiedFocusComparator.NextFocusGetter
        public final View get(View view, View view2) {
            return FocusFinderCompat.userSpecifiedFocusComparator$lambda$0(this.f$0, view, view2);
        }
    });
    private final ArrayList tmpList = new ArrayList();

    /* JADX INFO: compiled from: FocusFinderCompat.android.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final FocusFinderCompat getInstance() {
            Object obj = FocusFinderCompat.FocusFinderThreadLocal.get();
            obj.getClass();
            return (FocusFinderCompat) obj;
        }
    }

    /* JADX INFO: compiled from: FocusFinderCompat.android.kt */
    final class UserSpecifiedFocusComparator implements Comparator {
        private final NextFocusGetter mNextFocusGetter;
        private View root;
        private final MutableScatterMap nextFoci = ScatterMapKt.mutableScatterMapOf();
        private final MutableScatterSet isConnectedTo = ScatterSetKt.mutableScatterSetOf();
        private final MutableScatterMap headsOfChains = ScatterMapKt.mutableScatterMapOf();
        private final MutableObjectIntMap originalOrdinal = ObjectIntMapKt.mutableObjectIntMapOf();

        /* JADX INFO: compiled from: FocusFinderCompat.android.kt */
        public interface NextFocusGetter {
            View get(View view, View view2);
        }

        public UserSpecifiedFocusComparator(NextFocusGetter nextFocusGetter) {
            this.mNextFocusGetter = nextFocusGetter;
        }

        @Override // java.util.Comparator
        public int compare(View view, View view2) {
            if (view == view2) {
                return 0;
            }
            if (view == null) {
                return -1;
            }
            if (view2 == null) {
                return 1;
            }
            View view3 = (View) this.headsOfChains.get(view);
            View view4 = (View) this.headsOfChains.get(view2);
            if (view3 == view4 && view3 != null) {
                if (view == view3) {
                    return -1;
                }
                return (view2 == view3 || this.nextFoci.get(view) == null) ? 1 : -1;
            }
            if (view3 != null) {
                view = view3;
            }
            if (view4 != null) {
                view2 = view4;
            }
            if (view3 == null && view4 == null) {
                return 0;
            }
            return this.originalOrdinal.get(view) < this.originalOrdinal.get(view2) ? -1 : 1;
        }

        public final void recycle() {
            this.root = null;
            this.headsOfChains.clear();
            this.isConnectedTo.clear();
            this.originalOrdinal.clear();
            this.nextFoci.clear();
        }

        public final void setFocusables(ArrayList arrayList, View view) {
            this.root = view;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.originalOrdinal.set((View) arrayList.get(i), i);
            }
            int size2 = arrayList.size() - 1;
            if (size2 >= 0) {
                while (true) {
                    int i2 = size2 - 1;
                    View view2 = (View) arrayList.get(size2);
                    View view3 = this.mNextFocusGetter.get(view, view2);
                    if (view3 != null && this.originalOrdinal.containsKey(view3)) {
                        this.nextFoci.set(view2, view3);
                        this.isConnectedTo.add(view3);
                    }
                    if (i2 < 0) {
                        break;
                    } else {
                        size2 = i2;
                    }
                }
            }
            int size3 = arrayList.size() - 1;
            if (size3 < 0) {
                return;
            }
            while (true) {
                int i3 = size3 - 1;
                View view4 = (View) arrayList.get(size3);
                if (((View) this.nextFoci.get(view4)) != null && !this.isConnectedTo.contains(view4)) {
                    setHeadOfChain(view4);
                }
                if (i3 < 0) {
                    return;
                } else {
                    size3 = i3;
                }
            }
        }

        public final void setHeadOfChain(View view) {
            View view2 = view;
            while (view != null) {
                View view3 = (View) this.headsOfChains.get(view);
                if (view3 != null) {
                    if (view3 == view2) {
                        return;
                    }
                    view = view2;
                    view2 = view3;
                }
                this.headsOfChains.set(view, view2);
                view = (View) this.nextFoci.get(view);
            }
        }
    }

    private final View findNextFocus(ViewGroup viewGroup, Rect rect, int i) {
        ViewGroup effectiveRoot = getEffectiveRoot(viewGroup, null);
        ArrayList arrayList = this.tmpList;
        try {
            arrayList.clear();
            FocusFinderCompat_androidKt.addFocusableViews(effectiveRoot, arrayList, i);
            if (arrayList.isEmpty()) {
                arrayList.clear();
                return null;
            }
            View viewFindNextFocus = findNextFocus(effectiveRoot, null, rect, i, arrayList);
            arrayList.clear();
            return viewFindNextFocus;
        } catch (Throwable th) {
            arrayList.clear();
            throw th;
        }
    }

    private final View findNextFocus(ViewGroup viewGroup, View view, Rect rect, int i, ArrayList arrayList) {
        Rect rect2 = this.cachedFocusedRect;
        if (view != null) {
            view.getFocusedRect(rect2);
            viewGroup.offsetDescendantRectToMyCoords(view, rect2);
        } else if (rect != null) {
            rect2.set(rect);
        } else if (i != 1) {
            if (i != 2) {
                if (i == 17 || i == 33) {
                    setFocusBottomRight(viewGroup, rect2);
                } else if (i == 66 || i == 130) {
                    setFocusTopLeft(viewGroup, rect2);
                }
            } else if (viewGroup.getLayoutDirection() == 1) {
                setFocusBottomRight(viewGroup, rect2);
            } else {
                setFocusTopLeft(viewGroup, rect2);
            }
        } else if (viewGroup.getLayoutDirection() == 1) {
            setFocusTopLeft(viewGroup, rect2);
        } else {
            setFocusBottomRight(viewGroup, rect2);
        }
        if (i == 1 || i == 2) {
            return findNextFocusInRelativeDirection(arrayList, viewGroup, view, i);
        }
        if (i == 17 || i == 33 || i == 66 || i == 130) {
            return findNextFocusInAbsoluteDirection(viewGroup, view, rect2, arrayList, i);
        }
        throw new IllegalArgumentException("Unknown direction: " + i);
    }

    private final View findNextFocusInAbsoluteDirection(ViewGroup viewGroup, View view, Rect rect, ArrayList arrayList, int i) {
        this.bestCandidateRect.set(rect);
        if (i == 17) {
            this.bestCandidateRect.offset(rect.width() + 1, 0);
        } else if (i == 33) {
            this.bestCandidateRect.offset(0, rect.height() + 1);
        } else if (i == 66) {
            this.bestCandidateRect.offset((-rect.width()) - 1, 0);
        } else if (i == 130) {
            this.bestCandidateRect.offset(0, (-rect.height()) - 1);
        }
        int size = arrayList.size();
        View view2 = null;
        for (int i2 = 0; i2 < size; i2++) {
            View view3 = (View) arrayList.get(i2);
            if (!Intrinsics.areEqual(view3, view) && !Intrinsics.areEqual(view3, viewGroup)) {
                view3.getFocusedRect(this.otherRect);
                viewGroup.offsetDescendantRectToMyCoords(view3, this.otherRect);
                androidx.compose.ui.geometry.Rect composeRect = RectHelper_androidKt.toComposeRect(this.otherRect);
                androidx.compose.ui.geometry.Rect composeRect2 = RectHelper_androidKt.toComposeRect(this.bestCandidateRect);
                androidx.compose.ui.geometry.Rect composeRect3 = RectHelper_androidKt.toComposeRect(rect);
                FocusDirection focusDirection = FocusInteropUtils_androidKt.toFocusDirection(i);
                if (TwoDimensionalFocusSearchKt.m742isBetterCandidateI7lrPNg(composeRect, composeRect2, composeRect3, focusDirection != null ? focusDirection.m690unboximpl() : FocusDirection.Companion.m695getNextdhqQ8s())) {
                    this.bestCandidateRect.set(this.otherRect);
                    view2 = view3;
                }
            }
        }
        return view2;
    }

    private final View findNextFocusInRelativeDirection(ArrayList arrayList, ViewGroup viewGroup, View view, int i) {
        ArrayList arrayList2;
        try {
            this.userSpecifiedFocusComparator.setFocusables(arrayList, viewGroup);
            Collections.sort(arrayList, this.userSpecifiedFocusComparator);
            this.userSpecifiedFocusComparator.recycle();
            int size = arrayList.size();
            View previousFocusable = null;
            if (size < 2) {
                return null;
            }
            if (i == 1) {
                arrayList2 = arrayList;
                previousFocusable = getPreviousFocusable(view, arrayList2, size);
            } else if (i == 2) {
                arrayList2 = arrayList;
                previousFocusable = getNextFocusable(view, arrayList2, size);
            } else if (i == 17 || i == 33 || i == 66 || i == 130) {
                arrayList2 = arrayList;
                previousFocusable = findNextFocusInAbsoluteDirection(viewGroup, view, this.cachedFocusedRect, arrayList2, i);
            } else {
                arrayList2 = arrayList;
            }
            return previousFocusable == null ? (View) arrayList2.get(size - 1) : previousFocusable;
        } catch (Throwable th) {
            this.userSpecifiedFocusComparator.recycle();
            throw th;
        }
    }

    private final View findNextUserSpecifiedFocus(ViewGroup viewGroup, View view, int i) {
        View viewFindUserSetNextFocus = FocusFinderCompat_androidKt.findUserSetNextFocus(view, viewGroup, i);
        boolean z = true;
        View viewFindUserSetNextFocus2 = viewFindUserSetNextFocus;
        while (viewFindUserSetNextFocus != null) {
            if (viewFindUserSetNextFocus.isFocusable() && viewFindUserSetNextFocus.getVisibility() == 0 && (!viewFindUserSetNextFocus.isInTouchMode() || viewFindUserSetNextFocus.isFocusableInTouchMode())) {
                return viewFindUserSetNextFocus;
            }
            viewFindUserSetNextFocus = FocusFinderCompat_androidKt.findUserSetNextFocus(viewFindUserSetNextFocus, viewGroup, i);
            boolean z2 = !z;
            if (!z) {
                viewFindUserSetNextFocus2 = viewFindUserSetNextFocus2 != null ? FocusFinderCompat_androidKt.findUserSetNextFocus(viewFindUserSetNextFocus2, viewGroup, i) : null;
                if (viewFindUserSetNextFocus2 == viewFindUserSetNextFocus) {
                    break;
                }
            }
            z = z2;
        }
        return null;
    }

    private final ViewGroup getEffectiveRoot(ViewGroup viewGroup, View view) {
        if (view != null && view != viewGroup) {
            ViewParent parent = view.getParent();
            ViewGroup viewGroup2 = null;
            while (true) {
                if (!(parent instanceof ViewGroup)) {
                    break;
                }
                if (parent == viewGroup) {
                    if (viewGroup2 == null) {
                        break;
                    }
                    return viewGroup2;
                }
                ViewGroup viewGroup3 = (ViewGroup) parent;
                if (viewGroup3.getTouchscreenBlocksFocus() && view.getContext().getPackageManager().hasSystemFeature("android.hardware.touchscreen")) {
                    viewGroup2 = viewGroup3;
                }
                parent = viewGroup3.getParent();
            }
        }
        return viewGroup;
    }

    private final View getNextFocusable(View view, ArrayList arrayList, int i) {
        int iLastIndexOf;
        int i2;
        if (i < 2) {
            return null;
        }
        return (view == null || (iLastIndexOf = arrayList.lastIndexOf(view)) < 0 || (i2 = iLastIndexOf + 1) >= i) ? (View) arrayList.get(0) : (View) arrayList.get(i2);
    }

    private final View getPreviousFocusable(View view, ArrayList arrayList, int i) {
        int iIndexOf;
        if (i < 2) {
            return null;
        }
        return (view == null || (iIndexOf = arrayList.indexOf(view)) <= 0) ? (View) arrayList.get(i - 1) : (View) arrayList.get(iIndexOf - 1);
    }

    private final boolean isValidId(int i) {
        return (i == 0 || i == -1) ? false : true;
    }

    private final void setFocusBottomRight(ViewGroup viewGroup, Rect rect) {
        int scrollY = viewGroup.getScrollY() + viewGroup.getHeight();
        int scrollX = viewGroup.getScrollX() + viewGroup.getWidth();
        rect.set(scrollX, scrollY, scrollX, scrollY);
    }

    private final void setFocusTopLeft(ViewGroup viewGroup, Rect rect) {
        int scrollY = viewGroup.getScrollY();
        int scrollX = viewGroup.getScrollX();
        rect.set(scrollX, scrollY, scrollX, scrollY);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final View userSpecifiedFocusComparator$lambda$0(FocusFinderCompat focusFinderCompat, View view, View view2) {
        if (focusFinderCompat.isValidId(view2.getNextFocusForwardId())) {
            return FocusFinderCompat_androidKt.findUserSetNextFocus(view2, view, 2);
        }
        return null;
    }

    public final View findNextFocus(ViewGroup viewGroup, View view, int i) {
        ViewGroup effectiveRoot = getEffectiveRoot(viewGroup, view);
        View viewFindNextUserSpecifiedFocus = findNextUserSpecifiedFocus(effectiveRoot, view, i);
        if (viewFindNextUserSpecifiedFocus != null) {
            return viewFindNextUserSpecifiedFocus;
        }
        ArrayList arrayList = this.tmpList;
        try {
            arrayList.clear();
            FocusFinderCompat_androidKt.addFocusableViews(effectiveRoot, arrayList, i);
            if (!arrayList.isEmpty()) {
                viewFindNextUserSpecifiedFocus = findNextFocus(effectiveRoot, view, null, i, arrayList);
            }
            arrayList.clear();
            return viewFindNextUserSpecifiedFocus;
        } catch (Throwable th) {
            arrayList.clear();
            throw th;
        }
    }

    public final View findNextFocusFromRect(ViewGroup viewGroup, Rect rect, int i) {
        this.cachedFocusedRect.set(rect);
        return findNextFocus(viewGroup, this.cachedFocusedRect, i);
    }
}
