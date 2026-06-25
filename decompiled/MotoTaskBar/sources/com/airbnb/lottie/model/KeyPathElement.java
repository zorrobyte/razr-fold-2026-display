package com.airbnb.lottie.model;

import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface KeyPathElement {
    void addValueCallback(Object obj, LottieValueCallback lottieValueCallback);

    void resolveKeyPath(KeyPath keyPath, int i, List list, KeyPath keyPath2);
}
