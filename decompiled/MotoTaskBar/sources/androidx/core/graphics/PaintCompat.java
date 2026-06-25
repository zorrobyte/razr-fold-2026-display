package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.Paint;

/* JADX INFO: loaded from: classes.dex */
public abstract class PaintCompat {
    private static final ThreadLocal sRectThreadLocal = new ThreadLocal();

    abstract class Api23Impl {
        static boolean hasGlyph(Paint paint, String str) {
            return paint.hasGlyph(str);
        }
    }

    abstract class Api29Impl {
        static void setBlendMode(Paint paint, Object obj) {
            paint.setBlendMode((BlendMode) obj);
        }
    }

    public static boolean hasGlyph(Paint paint, String str) {
        return Api23Impl.hasGlyph(paint, str);
    }

    public static boolean setBlendMode(Paint paint, BlendModeCompat blendModeCompat) {
        Api29Impl.setBlendMode(paint, blendModeCompat != null ? BlendModeUtils$Api29Impl.obtainBlendModeFromCompat(blendModeCompat) : null);
        return true;
    }
}
