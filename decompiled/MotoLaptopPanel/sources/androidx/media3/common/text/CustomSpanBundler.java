package androidx.media3.common.text;

import android.os.Bundle;
import android.text.Spannable;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;

/* JADX INFO: loaded from: classes.dex */
abstract class CustomSpanBundler {
    private static final String FIELD_START_INDEX = Util.intToStringMaxRadix(0);
    private static final String FIELD_END_INDEX = Util.intToStringMaxRadix(1);
    private static final String FIELD_FLAGS = Util.intToStringMaxRadix(2);
    private static final String FIELD_TYPE = Util.intToStringMaxRadix(3);
    private static final String FIELD_PARAMS = Util.intToStringMaxRadix(4);

    public static void unbundleAndApplyCustomSpan(Bundle bundle, Spannable spannable) {
        int i = bundle.getInt(FIELD_START_INDEX);
        int i2 = bundle.getInt(FIELD_END_INDEX);
        int i3 = bundle.getInt(FIELD_FLAGS);
        int i4 = bundle.getInt(FIELD_TYPE, -1);
        Bundle bundle2 = bundle.getBundle(FIELD_PARAMS);
        if (i4 == 1) {
            spannable.setSpan(RubySpan.fromBundle((Bundle) Assertions.checkNotNull(bundle2)), i, i2, i3);
            return;
        }
        if (i4 == 2) {
            spannable.setSpan(TextEmphasisSpan.fromBundle((Bundle) Assertions.checkNotNull(bundle2)), i, i2, i3);
        } else if (i4 == 3) {
            spannable.setSpan(new HorizontalTextInVerticalContextSpan(), i, i2, i3);
        } else {
            if (i4 != 4) {
                return;
            }
            spannable.setSpan(VoiceSpan.fromBundle((Bundle) Assertions.checkNotNull(bundle2)), i, i2, i3);
        }
    }
}
