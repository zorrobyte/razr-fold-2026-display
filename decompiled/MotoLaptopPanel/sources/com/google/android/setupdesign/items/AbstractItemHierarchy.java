package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.items.ItemHierarchy;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractItemHierarchy implements ItemHierarchy {
    private int id;
    private final ArrayList observers;

    public AbstractItemHierarchy() {
        this.observers = new ArrayList();
        this.id = -1;
    }

    public AbstractItemHierarchy(Context context, AttributeSet attributeSet) {
        this.observers = new ArrayList();
        this.id = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudAbstractItem);
        this.id = typedArrayObtainStyledAttributes.getResourceId(R$styleable.SudAbstractItem_android_id, -1);
        typedArrayObtainStyledAttributes.recycle();
    }

    public void notifyItemRangeChanged(int i, int i2) {
        if (i < 0) {
            Log.w("AbstractItemHierarchy", "notifyItemRangeChanged: Invalid position=" + i);
            return;
        }
        if (i2 < 0) {
            Log.w("AbstractItemHierarchy", "notifyItemRangeChanged: Invalid itemCount=" + i2);
            return;
        }
        ArrayList arrayList = this.observers;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            ((ItemHierarchy.Observer) obj).onItemRangeChanged(this, i, i2);
        }
    }

    public void notifyItemRangeInserted(int i, int i2) {
        if (i < 0) {
            Log.w("AbstractItemHierarchy", "notifyItemRangeInserted: Invalid position=" + i);
            return;
        }
        if (i2 < 0) {
            Log.w("AbstractItemHierarchy", "notifyItemRangeInserted: Invalid itemCount=" + i2);
            return;
        }
        ArrayList arrayList = this.observers;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            ((ItemHierarchy.Observer) obj).onItemRangeInserted(this, i, i2);
        }
    }

    public void notifyItemRangeRemoved(int i, int i2) {
        if (i < 0) {
            Log.w("AbstractItemHierarchy", "notifyItemRangeInserted: Invalid position=" + i);
            return;
        }
        if (i2 < 0) {
            Log.w("AbstractItemHierarchy", "notifyItemRangeInserted: Invalid itemCount=" + i2);
            return;
        }
        ArrayList arrayList = this.observers;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            ((ItemHierarchy.Observer) obj).onItemRangeRemoved(this, i, i2);
        }
    }

    @Override // com.google.android.setupdesign.items.ItemHierarchy
    public void registerObserver(ItemHierarchy.Observer observer) {
        this.observers.add(observer);
    }
}
