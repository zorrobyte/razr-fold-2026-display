package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupdesign.R$id;
import com.google.android.setupdesign.R$layout;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.strings.R$string;

/* JADX INFO: loaded from: classes.dex */
public class ExpandableSwitchItem extends SwitchItem implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private final AccessibilityDelegateCompat accessibilityDelegate;
    private boolean canExpanded;
    private CharSequence collapsedSummary;
    private CharSequence expandedSummary;
    private boolean isExpanded;
    private boolean isSwitchItem;

    public ExpandableSwitchItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isExpanded = false;
        this.canExpanded = true;
        this.isSwitchItem = true;
        this.accessibilityDelegate = new AccessibilityDelegateCompat() { // from class: com.google.android.setupdesign.items.ExpandableSwitchItem.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.addAction(ExpandableSwitchItem.this.isExpanded() ? AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE : AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND);
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i != 262144 && i != 524288) {
                    return super.performAccessibilityAction(view, i, bundle);
                }
                ExpandableSwitchItem.this.setExpanded(!r1.isExpanded());
                return true;
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudExpandableSwitchItem);
        this.collapsedSummary = typedArrayObtainStyledAttributes.getText(R$styleable.SudExpandableSwitchItem_sudCollapsedSummary);
        this.expandedSummary = typedArrayObtainStyledAttributes.getText(R$styleable.SudExpandableSwitchItem_sudExpandedSummary);
        if (PartnerConfigHelper.isGlifExpressiveEnabled(context)) {
            setLayoutResource(R$layout.sud_items_expandable_switch_expressive);
            setIconGravity(typedArrayObtainStyledAttributes.getInt(R$styleable.SudItem_sudIconGravity, 16));
        } else {
            setIconGravity(typedArrayObtainStyledAttributes.getInt(R$styleable.SudItem_sudIconGravity, 48));
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    private void updateShowMoreLinkText(View view) {
        TextView textView = (TextView) view.findViewById(R$id.sud_items_more_info);
        if (textView != null) {
            if (isExpanded()) {
                textView.setText(R$string.sud_less_info);
            } else {
                textView.setText(R$string.sud_more_info);
            }
        }
    }

    @Override // com.google.android.setupdesign.items.SwitchItem, com.google.android.setupdesign.items.Item
    protected int getDefaultLayoutResource() {
        return R$layout.sud_items_expandable_switch;
    }

    public boolean isExpanded() {
        return this.isExpanded;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (!PartnerConfigHelper.isGlifExpressiveEnabled(view.getContext())) {
            setExpanded(!isExpanded());
        } else if (view.getId() == R$id.sud_items_more_info) {
            setExpanded(!isExpanded());
            updateShowMoreLinkText(view);
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
