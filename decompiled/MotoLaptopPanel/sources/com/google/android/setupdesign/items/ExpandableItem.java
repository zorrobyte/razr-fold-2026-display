package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.setupdesign.R$drawable;
import com.google.android.setupdesign.R$id;
import com.google.android.setupdesign.R$layout;
import com.google.android.setupdesign.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class ExpandableItem extends Item implements View.OnClickListener {
    private final AccessibilityDelegateCompat accessibilityDelegate;
    private boolean canExpanded;
    private View expandedContent;
    private int expandedLayoutRes;
    private boolean isExpanded;

    public ExpandableItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isExpanded = false;
        this.canExpanded = true;
        this.expandedLayoutRes = 0;
        this.expandedContent = null;
        this.accessibilityDelegate = new AccessibilityDelegateCompat() { // from class: com.google.android.setupdesign.items.ExpandableItem.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.addAction(ExpandableItem.this.isExpanded() ? AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE : AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND);
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i != 262144 && i != 524288) {
                    return super.performAccessibilityAction(view, i, bundle);
                }
                ExpandableItem.this.setExpanded(!r1.isExpanded());
                return true;
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudExpandableItem);
        this.expandedLayoutRes = typedArrayObtainStyledAttributes.getResourceId(R$styleable.SudExpandableItem_sudExpandedContent, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void updateExpandButtonImage(View view) {
        ImageView imageView = (ImageView) view.findViewById(R$id.sud_items_expand_button);
        if (imageView != null) {
            if (isExpanded()) {
                imageView.setImageResource(R$drawable.sud_items_collapse_button_icon);
            } else {
                imageView.setImageResource(R$drawable.sud_items_expand_button_icon);
            }
        }
    }

    @Override // com.google.android.setupdesign.items.Item
    protected int getDefaultLayoutResource() {
        return R$layout.sud_items_expandable;
    }

    public boolean isExpanded() {
        return this.isExpanded;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R$id.sud_items_expand_button && this.canExpanded) {
            setExpanded(!isExpanded());
            updateExpandButtonImage(view);
        }
    }

    public void setExpanded(boolean z) {
        if (this.isExpanded == z) {
            return;
        }
        this.isExpanded = z;
        notifyItemChanged();
    }
}
