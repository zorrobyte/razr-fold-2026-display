package androidx.compose.material3.tokens;

import androidx.compose.material3.internal.DefaultPlatformTextStyle_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.LineHeightStyle;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TypographyTokens.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TypographyTokensKt {
    private static final LineHeightStyle DefaultLineHeightStyle;
    private static final TextStyle DefaultTextStyle;

    static {
        LineHeightStyle lineHeightStyle = new LineHeightStyle(LineHeightStyle.Alignment.Companion.m1776getCenterPIaL0Z0(), LineHeightStyle.Trim.Companion.m1797getNoneEVpEnUU(), (DefaultConstructorMarker) null);
        DefaultLineHeightStyle = lineHeightStyle;
        DefaultTextStyle = TextStyle.m1602copyp1EtxEg$default(TextStyle.Companion.getDefault(), 0L, 0L, null, null, null, null, null, 0L, null, null, null, 0L, null, null, null, 0, 0, 0L, null, DefaultPlatformTextStyle_androidKt.defaultPlatformTextStyle(), lineHeightStyle, 0, 0, null, 15204351, null);
    }

    public static final TextStyle getDefaultTextStyle() {
        return DefaultTextStyle;
    }
}
