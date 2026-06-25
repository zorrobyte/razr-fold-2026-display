package androidx.compose.ui.text.platform;

import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.LinkAnnotation;
import androidx.compose.ui.text.UrlAnnotation;
import java.util.WeakHashMap;

/* JADX INFO: compiled from: URLSpanCache.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class URLSpanCache {
    private final WeakHashMap spansByAnnotation = new WeakHashMap();
    private final WeakHashMap urlSpansByAnnotation = new WeakHashMap();
    private final WeakHashMap linkSpansWithListenerByAnnotation = new WeakHashMap();

    public final ClickableSpan toClickableSpan(AnnotatedString.Range range) {
        WeakHashMap weakHashMap = this.linkSpansWithListenerByAnnotation;
        Object composeClickableSpan = weakHashMap.get(range);
        if (composeClickableSpan == null) {
            composeClickableSpan = new ComposeClickableSpan((LinkAnnotation) range.getItem());
            weakHashMap.put(range, composeClickableSpan);
        }
        return (ClickableSpan) composeClickableSpan;
    }

    public final URLSpan toURLSpan(AnnotatedString.Range range) {
        WeakHashMap weakHashMap = this.urlSpansByAnnotation;
        Object uRLSpan = weakHashMap.get(range);
        if (uRLSpan == null) {
            uRLSpan = new URLSpan(((LinkAnnotation.Url) range.getItem()).getUrl());
            weakHashMap.put(range, uRLSpan);
        }
        return (URLSpan) uRLSpan;
    }

    public final URLSpan toURLSpan(UrlAnnotation urlAnnotation) {
        WeakHashMap weakHashMap = this.spansByAnnotation;
        Object uRLSpan = weakHashMap.get(urlAnnotation);
        if (uRLSpan == null) {
            uRLSpan = new URLSpan(urlAnnotation.getUrl());
            weakHashMap.put(urlAnnotation, uRLSpan);
        }
        return (URLSpan) uRLSpan;
    }
}
