package androidx.compose.ui.text.platform;

import android.text.style.ClickableSpan;
import android.view.View;
import androidx.compose.ui.text.LinkAnnotation;

/* JADX INFO: compiled from: URLSpanCache.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class ComposeClickableSpan extends ClickableSpan {
    private final LinkAnnotation link;

    public ComposeClickableSpan(LinkAnnotation linkAnnotation) {
        this.link = linkAnnotation;
    }

    @Override // android.text.style.ClickableSpan
    public void onClick(View view) {
        this.link.getLinkInteractionListener();
    }
}
