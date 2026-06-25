package androidx.recyclerview.widget;

/* JADX INFO: loaded from: classes.dex */
public final class E {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f1725a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f1726b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public boolean f1727c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f1728d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f1729e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f1730f;

    public final int a() {
        if (this.f1728d) {
            return this.f1725a - this.f1726b;
        }
        return 0;
    }

    public final String toString() {
        return "State{mTargetPosition=-1, mData=null, mItemCount=0, mIsMeasuring=false, mPreviousLayoutItemCount=" + this.f1725a + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.f1726b + ", mStructureChanged=" + this.f1727c + ", mInPreLayout=" + this.f1728d + ", mRunSimpleAnimations=" + this.f1729e + ", mRunPredictiveAnimations=" + this.f1730f + '}';
    }
}
