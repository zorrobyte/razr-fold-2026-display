package androidx.media3.common.text;

import android.os.Bundle;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;

/* JADX INFO: loaded from: classes.dex */
public final class VoiceSpan {
    private static final String FIELD_NAME = Util.intToStringMaxRadix(0);
    public final String name;

    public VoiceSpan(String str) {
        this.name = str;
    }

    public static VoiceSpan fromBundle(Bundle bundle) {
        return new VoiceSpan((String) Assertions.checkNotNull(bundle.getString(FIELD_NAME)));
    }
}
