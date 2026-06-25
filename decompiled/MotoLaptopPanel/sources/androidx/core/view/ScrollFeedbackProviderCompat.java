package androidx.core.view;

import android.view.ScrollFeedbackProvider;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class ScrollFeedbackProviderCompat {
    private final ScrollFeedbackProviderImpl mImpl;

    class ScrollFeedbackProviderApi35Impl implements ScrollFeedbackProviderImpl {
        private final ScrollFeedbackProvider mProvider;

        ScrollFeedbackProviderApi35Impl(View view) {
            this.mProvider = ScrollFeedbackProvider.createProvider(view);
        }

        @Override // androidx.core.view.ScrollFeedbackProviderCompat.ScrollFeedbackProviderImpl
        public void onScrollLimit(int i, int i2, int i3, boolean z) {
            this.mProvider.onScrollLimit(i, i2, i3, z);
        }

        @Override // androidx.core.view.ScrollFeedbackProviderCompat.ScrollFeedbackProviderImpl
        public void onScrollProgress(int i, int i2, int i3, int i4) {
            this.mProvider.onScrollProgress(i, i2, i3, i4);
        }
    }

    interface ScrollFeedbackProviderImpl {
        void onScrollLimit(int i, int i2, int i3, boolean z);

        void onScrollProgress(int i, int i2, int i3, int i4);
    }

    private ScrollFeedbackProviderCompat(View view) {
        this.mImpl = new ScrollFeedbackProviderApi35Impl(view);
    }

    public static ScrollFeedbackProviderCompat createProvider(View view) {
        return new ScrollFeedbackProviderCompat(view);
    }

    public void onScrollLimit(int i, int i2, int i3, boolean z) {
        this.mImpl.onScrollLimit(i, i2, i3, z);
    }

    public void onScrollProgress(int i, int i2, int i3, int i4) {
        this.mImpl.onScrollProgress(i, i2, i3, i4);
    }
}
