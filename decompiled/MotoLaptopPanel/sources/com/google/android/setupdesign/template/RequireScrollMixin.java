package com.google.android.setupdesign.template;

import android.os.Handler;
import android.os.Looper;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.template.Mixin;

/* JADX INFO: loaded from: classes.dex */
public class RequireScrollMixin implements Mixin {
    private ScrollHandlingDelegate delegate;
    private final TemplateLayout templateLayout;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean requiringScrollToBottom = false;
    private boolean everScrolledToBottom = false;

    public interface OnRequireScrollStateChangedListener {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public interface ScrollHandlingDelegate {
    }

    /* JADX INFO: renamed from: -$$Nest$fgetlistener, reason: not valid java name */
    static /* bridge */ /* synthetic */ OnRequireScrollStateChangedListener m2132$$Nest$fgetlistener(RequireScrollMixin requireScrollMixin) {
        requireScrollMixin.getClass();
        return null;
    }

    public RequireScrollMixin(TemplateLayout templateLayout) {
        this.templateLayout = templateLayout;
    }

    private void postScrollStateChange(final boolean z) {
        this.handler.post(new Runnable() { // from class: com.google.android.setupdesign.template.RequireScrollMixin.6
            @Override // java.lang.Runnable
            public void run() {
                RequireScrollMixin.m2132$$Nest$fgetlistener(RequireScrollMixin.this);
            }
        });
    }

    void notifyScrollabilityChange(boolean z) {
        if (z == this.requiringScrollToBottom) {
            return;
        }
        if (!z) {
            postScrollStateChange(false);
            this.requiringScrollToBottom = false;
            this.everScrolledToBottom = true;
        } else {
            if (this.everScrolledToBottom) {
                return;
            }
            postScrollStateChange(true);
            this.requiringScrollToBottom = true;
        }
    }

    public void setScrollHandlingDelegate(ScrollHandlingDelegate scrollHandlingDelegate) {
        this.delegate = scrollHandlingDelegate;
    }
}
