package com.android.systemui.statusbar.notification.stack;

import java.util.function.Consumer;
import kotlin.Unit;

/* JADX INFO: compiled from: ScrollViewFields.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ScrollViewFields {
    private boolean isScrolledToTop = true;
    private Consumer stackHeightConsumer;

    public final Unit sendStackHeight(float f) {
        Consumer consumer = this.stackHeightConsumer;
        if (consumer == null) {
            return null;
        }
        consumer.accept(Float.valueOf(f));
        return Unit.INSTANCE;
    }
}
