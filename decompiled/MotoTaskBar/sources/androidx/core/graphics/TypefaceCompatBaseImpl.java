package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
abstract class TypefaceCompatBaseImpl {
    private ConcurrentHashMap mFontFamilies = new ConcurrentHashMap();

    TypefaceCompatBaseImpl() {
    }

    public abstract Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, int i);

    public abstract Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fontInfoArr, int i);

    public abstract Typeface createFromFontInfoWithFallback(Context context, CancellationSignal cancellationSignal, List list, int i);

    public abstract Typeface createFromResourcesFontFile(Context context, Resources resources, int i, String str, int i2);
}
