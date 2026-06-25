package androidx.leanback.widget;

/* JADX INFO: loaded from: classes.dex */
public abstract class ItemAlignmentFacet {

    public abstract class ItemAlignmentDef {
        private boolean mAlignToBaseline;
        int mViewId = -1;
        int mFocusViewId = -1;
        int mOffset = 0;
        float mOffsetPercent = 50.0f;
        boolean mOffsetWithPadding = false;

        public boolean isAlignedToTextViewBaseLine() {
            return this.mAlignToBaseline;
        }
    }
}
