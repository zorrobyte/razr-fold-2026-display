package androidx.compose.ui.platform;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: FocusFinderCompat.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class FocusFinderCompat_androidKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void addFocusableViews(View view, ArrayList arrayList, int i) {
        view.addFocusables(arrayList, i, view.isInTouchMode() ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final View findUserSetNextFocus(final View view, final View view2, int i) {
        int nextFocusForwardId;
        if (i == 1) {
            if (view.getId() == -1) {
                return null;
            }
            return findViewByPredicateInsideOut(view2, view, new Function1() { // from class: androidx.compose.ui.platform.FocusFinderCompat_androidKt.findUserSetNextFocus.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(View view3) {
                    return Boolean.valueOf(FocusFinderCompat_androidKt.findViewInsideOutShouldExist(view2, view3, view3.getNextFocusForwardId()) == view);
                }
            });
        }
        if (i == 2 && (nextFocusForwardId = view.getNextFocusForwardId()) != -1) {
            return findViewInsideOutShouldExist(view2, view, nextFocusForwardId);
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x001d, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final android.view.View findViewByPredicateInsideOut(android.view.View r4, android.view.View r5, kotlin.jvm.functions.Function1 r6) {
        /*
            r0 = 0
            r1 = r0
        L2:
            android.view.View r1 = findViewByPredicateTraversal(r5, r6, r1)
            if (r1 != 0) goto L1d
            if (r5 != r4) goto Lb
            goto L1d
        Lb:
            android.view.ViewParent r1 = r5.getParent()
            if (r1 == 0) goto L1c
            boolean r2 = r1 instanceof android.view.View
            if (r2 != 0) goto L16
            goto L1c
        L16:
            android.view.View r1 = (android.view.View) r1
            r3 = r1
            r1 = r5
            r5 = r3
            goto L2
        L1c:
            return r0
        L1d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.FocusFinderCompat_androidKt.findViewByPredicateInsideOut(android.view.View, android.view.View, kotlin.jvm.functions.Function1):android.view.View");
    }

    private static final View findViewByPredicateTraversal(View view, Function1 function1, View view2) {
        View viewFindViewByPredicateTraversal;
        if (((Boolean) function1.invoke(view)).booleanValue()) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt != view2 && (viewFindViewByPredicateTraversal = findViewByPredicateTraversal(childAt, function1, view2)) != null) {
                return viewFindViewByPredicateTraversal;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final View findViewInsideOutShouldExist(View view, View view2, final int i) {
        return findViewByPredicateInsideOut(view, view2, new Function1() { // from class: androidx.compose.ui.platform.FocusFinderCompat_androidKt.findViewInsideOutShouldExist.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(View view3) {
                return Boolean.valueOf(view3.getId() == i);
            }
        });
    }
}
