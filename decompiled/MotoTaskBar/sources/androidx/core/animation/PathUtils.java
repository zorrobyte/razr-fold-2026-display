package androidx.core.animation;

import android.graphics.Path;

/* JADX INFO: loaded from: classes.dex */
abstract class PathUtils {

    abstract class Api26Impl {
        static float[] approximate(Path path, float f) {
            return path.approximate(f);
        }
    }

    static float[] createKeyFrameData(Path path, float f) {
        return Api26Impl.approximate(path, f);
    }
}
