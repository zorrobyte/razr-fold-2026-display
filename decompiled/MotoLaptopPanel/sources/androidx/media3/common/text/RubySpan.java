package androidx.media3.common.text;

import android.os.Bundle;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;

/* JADX INFO: loaded from: classes.dex */
public final class RubySpan {
    public final int position;
    public final String rubyText;
    private static final String FIELD_TEXT = Util.intToStringMaxRadix(0);
    private static final String FIELD_POSITION = Util.intToStringMaxRadix(1);

    public RubySpan(String str, int i) {
        this.rubyText = str;
        this.position = i;
    }

    public static RubySpan fromBundle(Bundle bundle) {
        return new RubySpan((String) Assertions.checkNotNull(bundle.getString(FIELD_TEXT)), bundle.getInt(FIELD_POSITION));
    }
}
