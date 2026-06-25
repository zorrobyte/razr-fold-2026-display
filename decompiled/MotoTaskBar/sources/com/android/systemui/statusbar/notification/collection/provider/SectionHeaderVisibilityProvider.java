package com.android.systemui.statusbar.notification.collection.provider;

import android.content.Context;
import com.android.systemui.res.R$bool;

/* JADX INFO: compiled from: SectionHeaderVisibilityProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SectionHeaderVisibilityProvider {
    private final boolean neverShowSectionHeaders;
    private boolean sectionHeadersVisible;

    public SectionHeaderVisibilityProvider(Context context) {
        context.getClass();
        this.neverShowSectionHeaders = context.getResources().getBoolean(R$bool.config_notification_never_show_section_headers);
        this.sectionHeadersVisible = true;
    }

    public final boolean getSectionHeadersVisible() {
        return this.sectionHeadersVisible;
    }
}
