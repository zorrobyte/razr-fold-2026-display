package androidx.activity;

import android.view.View;

/* JADX INFO: compiled from: ViewTreeFullyLoadedReporterOwner.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ViewTreeFullyDrawnReporterOwner {
    public static final void set(View view, FullyDrawnReporterOwner fullyDrawnReporterOwner) {
        view.getClass();
        fullyDrawnReporterOwner.getClass();
        view.setTag(R$id.report_drawn, fullyDrawnReporterOwner);
    }
}
