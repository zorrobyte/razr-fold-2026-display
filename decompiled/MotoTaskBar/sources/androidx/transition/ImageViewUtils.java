package androidx.transition;

import android.graphics.Matrix;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
abstract class ImageViewUtils {

    abstract class Api29Impl {
        static void animateTransform(ImageView imageView, Matrix matrix) {
            imageView.animateTransform(matrix);
        }
    }

    static void animateTransform(ImageView imageView, Matrix matrix) {
        Api29Impl.animateTransform(imageView, matrix);
    }
}
