package androidx.constraintlayout.widget;

/* JADX INFO: loaded from: classes.dex */
public class Barrier extends ConstraintHelper {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f1357c;

    public int getType() {
        return this.f1357c;
    }

    public void setAllowsGoneWidget(boolean z2) {
        throw null;
    }

    public void setType(int i2) {
        this.f1357c = i2;
        getResources().getConfiguration().getLayoutDirection();
        throw null;
    }
}
