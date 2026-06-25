package com.motorola.laptoppanel.ui.theme;

import androidx.compose.material3.Typography;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.font.SystemFontFamily;
import androidx.compose.ui.unit.TextUnitKt;

/* JADX INFO: compiled from: type.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TypeKt {
    private static final Typography Typography;

    static {
        long neutral100 = ColorKt.getNeutral100();
        FontFamily.Companion companion = FontFamily.Companion;
        SystemFontFamily systemFontFamily = companion.getDefault();
        FontWeight.Companion companion2 = FontWeight.Companion;
        FontWeight bold = companion2.getBold();
        TextStyle textStyle = new TextStyle(neutral100, TextUnitKt.getSp(24), bold, null, null, systemFontFamily, null, TextUnitKt.getSp(0), null, null, null, 0L, null, null, null, 0, 0, TextUnitKt.getSp(32), null, null, null, 0, 0, null, 16645976, null);
        long neutral1002 = ColorKt.getNeutral100();
        SystemFontFamily systemFontFamily2 = companion.getDefault();
        FontWeight medium = companion2.getMedium();
        TextStyle textStyle2 = new TextStyle(neutral1002, TextUnitKt.getSp(16), medium, null, null, systemFontFamily2, null, TextUnitKt.getSp(0.15d), null, null, null, 0L, null, null, null, 0, 0, TextUnitKt.getSp(22), null, null, null, 0, 0, null, 16645976, null);
        long neutral1003 = ColorKt.getNeutral100();
        SystemFontFamily systemFontFamily3 = companion.getDefault();
        FontWeight normal = companion2.getNormal();
        Typography = new Typography(null, null, null, null, null, textStyle, null, textStyle2, null, null, null, new TextStyle(neutral1003, TextUnitKt.getSp(12), normal, null, null, systemFontFamily3, null, TextUnitKt.getSp(0), null, null, null, 0L, null, null, null, 0, 0, TextUnitKt.getSp(16), null, null, null, 0, 0, null, 16645976, null), null, null, null, 30559, null);
    }

    public static final Typography getTypography() {
        return Typography;
    }
}
