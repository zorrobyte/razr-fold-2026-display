package androidx.appcompat.widget;

import android.widget.TextView;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
final class AppCompatTextClassifierHelper {
    private TextView mTextView;

    AppCompatTextClassifierHelper(TextView textView) {
        this.mTextView = (TextView) Preconditions.checkNotNull(textView);
    }
}
