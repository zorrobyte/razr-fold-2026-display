package com.bumptech.glide.load.engine.bitmap_recycle;

/* JADX INFO: loaded from: classes.dex */
interface ArrayAdapterInterface {
    int getArrayLength(Object obj);

    int getElementSizeInBytes();

    String getTag();

    Object newArray(int i);
}
