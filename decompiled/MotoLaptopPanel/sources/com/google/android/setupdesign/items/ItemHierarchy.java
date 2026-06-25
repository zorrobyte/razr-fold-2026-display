package com.google.android.setupdesign.items;

/* JADX INFO: loaded from: classes.dex */
public interface ItemHierarchy {

    public interface Observer {
        void onItemRangeChanged(ItemHierarchy itemHierarchy, int i, int i2);

        void onItemRangeInserted(ItemHierarchy itemHierarchy, int i, int i2);

        void onItemRangeRemoved(ItemHierarchy itemHierarchy, int i, int i2);
    }

    int getCount();

    void registerObserver(Observer observer);
}
