package com.google.android.setupdesign.items;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import com.google.android.setupdesign.items.ItemHierarchy;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ItemGroup extends AbstractItemHierarchy implements ItemHierarchy.Observer {
    private final List children;
    private int count;
    private boolean dirty;
    private final SparseIntArray hierarchyStart;

    public ItemGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.children = new ArrayList();
        this.hierarchyStart = new SparseIntArray();
        this.count = 0;
        this.dirty = false;
    }

    private int getChildPosition(int i) {
        updateDataIfNeeded();
        if (i == -1) {
            return -1;
        }
        int size = this.children.size();
        int i2 = -1;
        while (i2 < 0 && i < size) {
            i2 = this.hierarchyStart.get(i, -1);
            i++;
        }
        return i2 < 0 ? getCount() : i2;
    }

    private int getChildPosition(ItemHierarchy itemHierarchy) {
        return getChildPosition(identityIndexOf(this.children, itemHierarchy));
    }

    private static int identityIndexOf(List list, Object obj) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i) == obj) {
                return i;
            }
        }
        return -1;
    }

    private void updateDataIfNeeded() {
        if (this.dirty) {
            this.count = 0;
            this.hierarchyStart.clear();
            for (int i = 0; i < this.children.size(); i++) {
                ItemHierarchy itemHierarchy = (ItemHierarchy) this.children.get(i);
                if (itemHierarchy.getCount() > 0) {
                    this.hierarchyStart.put(i, this.count);
                }
                this.count += itemHierarchy.getCount();
            }
            this.dirty = false;
        }
    }

    public void addChild(ItemHierarchy itemHierarchy) {
        this.dirty = true;
        this.children.add(itemHierarchy);
        itemHierarchy.registerObserver(this);
        int count = itemHierarchy.getCount();
        if (count > 0) {
            notifyItemRangeInserted(getChildPosition(itemHierarchy), count);
        }
    }

    @Override // com.google.android.setupdesign.items.ItemHierarchy
    public int getCount() {
        updateDataIfNeeded();
        return this.count;
    }

    @Override // com.google.android.setupdesign.items.ItemHierarchy.Observer
    public void onItemRangeChanged(ItemHierarchy itemHierarchy, int i, int i2) {
        int childPosition = getChildPosition(itemHierarchy);
        if (childPosition >= 0) {
            notifyItemRangeChanged(childPosition + i, i2);
            return;
        }
        Log.e("ItemGroup", "Unexpected child change " + itemHierarchy);
    }

    @Override // com.google.android.setupdesign.items.ItemHierarchy.Observer
    public void onItemRangeInserted(ItemHierarchy itemHierarchy, int i, int i2) {
        this.dirty = true;
        int childPosition = getChildPosition(itemHierarchy);
        if (childPosition >= 0) {
            notifyItemRangeInserted(childPosition + i, i2);
            return;
        }
        Log.e("ItemGroup", "Unexpected child insert " + itemHierarchy);
    }

    @Override // com.google.android.setupdesign.items.ItemHierarchy.Observer
    public void onItemRangeRemoved(ItemHierarchy itemHierarchy, int i, int i2) {
        this.dirty = true;
        int childPosition = getChildPosition(itemHierarchy);
        if (childPosition >= 0) {
            notifyItemRangeRemoved(childPosition + i, i2);
            return;
        }
        Log.e("ItemGroup", "Unexpected child remove " + itemHierarchy);
    }
}
