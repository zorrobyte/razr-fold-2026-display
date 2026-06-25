package com.android.systemui.util;

import android.view.ViewGroup;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: ConvenienceExtensions.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ConvenienceExtensionsKt {
    public static final Sequence getChildren(ViewGroup viewGroup) {
        viewGroup.getClass();
        return SequencesKt.sequence(new ConvenienceExtensionsKt$children$1(viewGroup, null));
    }
}
