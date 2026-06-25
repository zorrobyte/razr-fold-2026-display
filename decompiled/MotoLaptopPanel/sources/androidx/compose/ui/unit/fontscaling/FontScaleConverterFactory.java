package androidx.compose.ui.unit.fontscaling;

import androidx.collection.SparseArrayCompat;
import androidx.compose.ui.unit.InlineClassHelperKt;
import kotlin.Unit;

/* JADX INFO: compiled from: FontScaleConverterFactory.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FontScaleConverterFactory {
    public static final int $stable;
    private static final float[] CommonFontSizes;
    public static final FontScaleConverterFactory INSTANCE;
    private static final Object[] LookupTablesWriteLock;
    private static volatile SparseArrayCompat sLookupTables;

    static {
        FontScaleConverterFactory fontScaleConverterFactory = new FontScaleConverterFactory();
        INSTANCE = fontScaleConverterFactory;
        CommonFontSizes = new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f};
        sLookupTables = new SparseArrayCompat(0, 1, null);
        Object[] objArr = new Object[0];
        LookupTablesWriteLock = objArr;
        synchronized (objArr) {
            fontScaleConverterFactory.putInto(sLookupTables, 1.15f, new FontScaleConverterTable(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{9.2f, 11.5f, 13.8f, 16.4f, 19.8f, 21.8f, 25.2f, 30.0f, 100.0f}));
            fontScaleConverterFactory.putInto(sLookupTables, 1.3f, new FontScaleConverterTable(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{10.4f, 13.0f, 15.6f, 18.8f, 21.6f, 23.6f, 26.4f, 30.0f, 100.0f}));
            fontScaleConverterFactory.putInto(sLookupTables, 1.5f, new FontScaleConverterTable(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{12.0f, 15.0f, 18.0f, 22.0f, 24.0f, 26.0f, 28.0f, 30.0f, 100.0f}));
            fontScaleConverterFactory.putInto(sLookupTables, 1.8f, new FontScaleConverterTable(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{14.4f, 18.0f, 21.6f, 24.4f, 27.6f, 30.8f, 32.8f, 34.8f, 100.0f}));
            fontScaleConverterFactory.putInto(sLookupTables, 2.0f, new FontScaleConverterTable(new float[]{8.0f, 10.0f, 12.0f, 14.0f, 18.0f, 20.0f, 24.0f, 30.0f, 100.0f}, new float[]{16.0f, 20.0f, 24.0f, 26.0f, 30.0f, 34.0f, 36.0f, 38.0f, 100.0f}));
            Unit unit = Unit.INSTANCE;
        }
        if (!(fontScaleConverterFactory.getScaleFromKey(sLookupTables.keyAt(0)) - 0.01f > 1.03f)) {
            InlineClassHelperKt.throwIllegalStateException("You should only apply non-linear scaling to font scales > 1");
        }
        $stable = 8;
    }

    private FontScaleConverterFactory() {
    }

    private final FontScaleConverter createInterpolatedTableBetween(FontScaleConverter fontScaleConverter, FontScaleConverter fontScaleConverter2, float f) {
        float[] fArr = CommonFontSizes;
        float[] fArr2 = new float[fArr.length];
        int length = fArr.length;
        for (int i = 0; i < length; i++) {
            float f2 = CommonFontSizes[i];
            fArr2[i] = MathUtils.INSTANCE.lerp(fontScaleConverter.convertSpToDp(f2), fontScaleConverter2.convertSpToDp(f2), f);
        }
        return new FontScaleConverterTable(CommonFontSizes, fArr2);
    }

    private final FontScaleConverter get(float f) {
        return (FontScaleConverter) sLookupTables.get(getKey(f));
    }

    private final int getKey(float f) {
        return (int) (f * 100.0f);
    }

    private final float getScaleFromKey(int i) {
        return i / 100.0f;
    }

    private final void put(float f, FontScaleConverter fontScaleConverter) {
        synchronized (LookupTablesWriteLock) {
            SparseArrayCompat sparseArrayCompatM18clone = sLookupTables.m18clone();
            INSTANCE.putInto(sparseArrayCompatM18clone, f, fontScaleConverter);
            sLookupTables = sparseArrayCompatM18clone;
            Unit unit = Unit.INSTANCE;
        }
    }

    private final void putInto(SparseArrayCompat sparseArrayCompat, float f, FontScaleConverter fontScaleConverter) {
        sparseArrayCompat.put(getKey(f), fontScaleConverter);
    }

    public final FontScaleConverter forScale(float f) {
        FontScaleConverter fontScaleConverterTable;
        if (!isNonLinearFontScalingActive(f)) {
            return null;
        }
        FontScaleConverter fontScaleConverter = INSTANCE.get(f);
        if (fontScaleConverter != null) {
            return fontScaleConverter;
        }
        int iIndexOfKey = sLookupTables.indexOfKey(getKey(f));
        if (iIndexOfKey >= 0) {
            return (FontScaleConverter) sLookupTables.valueAt(iIndexOfKey);
        }
        int i = -(iIndexOfKey + 1);
        int i2 = i - 1;
        float scaleFromKey = 1.0f;
        if (i >= sLookupTables.size()) {
            FontScaleConverterTable fontScaleConverterTable2 = new FontScaleConverterTable(new float[]{1.0f}, new float[]{f});
            put(f, fontScaleConverterTable2);
            return fontScaleConverterTable2;
        }
        if (i2 < 0) {
            float[] fArr = CommonFontSizes;
            fontScaleConverterTable = new FontScaleConverterTable(fArr, fArr);
        } else {
            scaleFromKey = getScaleFromKey(sLookupTables.keyAt(i2));
            fontScaleConverterTable = (FontScaleConverter) sLookupTables.valueAt(i2);
        }
        FontScaleConverter fontScaleConverterCreateInterpolatedTableBetween = createInterpolatedTableBetween(fontScaleConverterTable, (FontScaleConverter) sLookupTables.valueAt(i), MathUtils.INSTANCE.constrainedMap(0.0f, 1.0f, scaleFromKey, getScaleFromKey(sLookupTables.keyAt(i)), f));
        put(f, fontScaleConverterCreateInterpolatedTableBetween);
        return fontScaleConverterCreateInterpolatedTableBetween;
    }

    public final boolean isNonLinearFontScalingActive(float f) {
        return f >= 1.03f;
    }
}
