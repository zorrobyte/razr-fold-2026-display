package androidx.core.viewtree;

import android.view.View;
import android.view.ViewParent;

/* JADX INFO: compiled from: ViewTree.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ViewTree {
    public static final ViewParent getParentOrViewTreeDisjointParent(View view) {
        view.getClass();
        ViewParent parent = view.getParent();
        if (parent != null) {
            return parent;
        }
        Object tag = view.getTag(R$id.view_tree_disjoint_parent);
        if (tag instanceof ViewParent) {
            return (ViewParent) tag;
        }
        return null;
    }
}
