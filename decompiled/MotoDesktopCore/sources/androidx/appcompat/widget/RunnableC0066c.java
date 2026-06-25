package androidx.appcompat.widget;

/* JADX INFO: renamed from: androidx.appcompat.widget.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class RunnableC0066c implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1204a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ActionBarOverlayLayout f1205b;

    public /* synthetic */ RunnableC0066c(ActionBarOverlayLayout actionBarOverlayLayout, int i2) {
        this.f1204a = i2;
        this.f1205b = actionBarOverlayLayout;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f1204a) {
            case 0:
                ActionBarOverlayLayout actionBarOverlayLayout = this.f1205b;
                actionBarOverlayLayout.b();
                actionBarOverlayLayout.f912w = actionBarOverlayLayout.f894d.animate().translationY(0.0f).setListener(actionBarOverlayLayout.f913x);
                break;
            default:
                ActionBarOverlayLayout actionBarOverlayLayout2 = this.f1205b;
                actionBarOverlayLayout2.b();
                actionBarOverlayLayout2.f912w = actionBarOverlayLayout2.f894d.animate().translationY(-actionBarOverlayLayout2.f894d.getHeight()).setListener(actionBarOverlayLayout2.f913x);
                break;
        }
    }
}
