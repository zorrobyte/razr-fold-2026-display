package com.android.systemui.qs.dagger;

import android.view.View;
import com.android.systemui.qs.QSPanel;
import com.motorola.taskbar.R$id;

/* JADX INFO: compiled from: QSScopeModule.kt */
/* JADX INFO: loaded from: classes.dex */
public interface QSScopeModule {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: QSScopeModule.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final QSPanel provideQSPanel(View view) {
            view.getClass();
            View viewRequireViewById = view.requireViewById(R$id.quick_settings_panel);
            viewRequireViewById.getClass();
            return (QSPanel) viewRequireViewById;
        }
    }

    static QSPanel provideQSPanel(View view) {
        return Companion.provideQSPanel(view);
    }
}
