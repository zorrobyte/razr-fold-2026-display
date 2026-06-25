package androidx.media3.session;

import android.os.Bundle;
import androidx.media3.common.util.Util;

/* JADX INFO: loaded from: classes.dex */
public final class LibraryResult {
    public final long completionTimeMs;
    public final MediaLibraryService$LibraryParams params;
    public final int resultCode;
    public final SessionError sessionError;
    public final Object value;
    private final int valueType;
    private static final String FIELD_RESULT_CODE = Util.intToStringMaxRadix(0);
    private static final String FIELD_COMPLETION_TIME_MS = Util.intToStringMaxRadix(1);
    private static final String FIELD_PARAMS = Util.intToStringMaxRadix(2);
    private static final String FIELD_VALUE = Util.intToStringMaxRadix(3);
    private static final String FIELD_VALUE_TYPE = Util.intToStringMaxRadix(4);
    private static final String FIELD_SESSION_ERROR = Util.intToStringMaxRadix(5);

    private LibraryResult(int i, long j, MediaLibraryService$LibraryParams mediaLibraryService$LibraryParams, SessionError sessionError, Object obj, int i2) {
        this.resultCode = i;
        this.completionTimeMs = j;
        this.params = mediaLibraryService$LibraryParams;
        this.sessionError = sessionError;
        this.value = obj;
        this.valueType = i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static androidx.media3.session.LibraryResult fromBundle(android.os.Bundle r10, java.lang.Integer r11) {
        /*
            java.lang.String r0 = androidx.media3.session.LibraryResult.FIELD_RESULT_CODE
            r1 = 0
            int r3 = r10.getInt(r0, r1)
            java.lang.String r0 = androidx.media3.session.LibraryResult.FIELD_COMPLETION_TIME_MS
            long r4 = android.os.SystemClock.elapsedRealtime()
            long r4 = r10.getLong(r0, r4)
            java.lang.String r0 = androidx.media3.session.LibraryResult.FIELD_PARAMS
            android.os.Bundle r0 = r10.getBundle(r0)
            r2 = 0
            if (r0 != 0) goto L1c
            r6 = r2
            goto L21
        L1c:
            androidx.media3.session.MediaLibraryService$LibraryParams r0 = androidx.media3.session.MediaLibraryService$LibraryParams.fromBundle(r0)
            r6 = r0
        L21:
            java.lang.String r0 = androidx.media3.session.LibraryResult.FIELD_SESSION_ERROR
            android.os.Bundle r0 = r10.getBundle(r0)
            if (r0 == 0) goto L2f
            androidx.media3.session.SessionError r0 = androidx.media3.session.SessionError.fromBundle(r0)
        L2d:
            r7 = r0
            goto L3a
        L2f:
            if (r3 == 0) goto L39
            androidx.media3.session.SessionError r0 = new androidx.media3.session.SessionError
            java.lang.String r7 = "no error message provided"
            r0.<init>(r3, r7)
            goto L2d
        L39:
            r7 = r2
        L3a:
            java.lang.String r0 = androidx.media3.session.LibraryResult.FIELD_VALUE_TYPE
            int r9 = r10.getInt(r0)
            r0 = 1
            if (r9 == r0) goto L75
            r8 = 2
            if (r9 == r8) goto L77
            r8 = 3
            if (r9 == r8) goto L53
            r10 = 4
            if (r9 != r10) goto L4d
            goto L75
        L4d:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            r10.<init>()
            throw r10
        L53:
            if (r11 == 0) goto L5b
            int r11 = r11.intValue()
            if (r11 != r8) goto L5c
        L5b:
            r1 = r0
        L5c:
            androidx.media3.common.util.Assertions.checkState(r1)
            java.lang.String r11 = androidx.media3.session.LibraryResult.FIELD_VALUE
            android.os.IBinder r10 = androidx.core.app.BundleCompat.getBinder(r10, r11)
            if (r10 != 0) goto L68
            goto L75
        L68:
            androidx.media3.session.LibraryResult$$ExternalSyntheticLambda0 r11 = new androidx.media3.session.LibraryResult$$ExternalSyntheticLambda0
            r11.<init>()
            com.google.common.collect.ImmutableList r10 = androidx.media3.common.BundleListRetriever.getList(r10)
            com.google.common.collect.ImmutableList r2 = androidx.media3.common.util.BundleCollectionUtil.fromBundleList(r11, r10)
        L75:
            r8 = r2
            goto L91
        L77:
            if (r11 == 0) goto L7f
            int r11 = r11.intValue()
            if (r11 != r8) goto L80
        L7f:
            r1 = r0
        L80:
            androidx.media3.common.util.Assertions.checkState(r1)
            java.lang.String r11 = androidx.media3.session.LibraryResult.FIELD_VALUE
            android.os.Bundle r10 = r10.getBundle(r11)
            if (r10 != 0) goto L8c
            goto L75
        L8c:
            androidx.media3.common.MediaItem r2 = androidx.media3.common.MediaItem.fromBundle(r10)
            goto L75
        L91:
            androidx.media3.session.LibraryResult r2 = new androidx.media3.session.LibraryResult
            r2.<init>(r3, r4, r6, r7, r8, r9)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.session.LibraryResult.fromBundle(android.os.Bundle, java.lang.Integer):androidx.media3.session.LibraryResult");
    }

    public static LibraryResult fromUnknownBundle(Bundle bundle) {
        return fromBundle(bundle, null);
    }
}
