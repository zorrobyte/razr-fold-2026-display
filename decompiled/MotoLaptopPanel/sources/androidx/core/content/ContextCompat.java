package androidx.core.content;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Process;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.ObjectsCompat;

/* JADX INFO: loaded from: classes.dex */
public abstract class ContextCompat {
    private static final Object sSync = new Object();

    abstract class Api21Impl {
        static Drawable getDrawable(Context context, int i) {
            return context.getDrawable(i);
        }
    }

    abstract class Api23Impl {
        static int getColor(Context context, int i) {
            return context.getColor(i);
        }

        static Object getSystemService(Context context, Class cls) {
            return context.getSystemService(cls);
        }
    }

    public static int checkSelfPermission(Context context, String str) {
        ObjectsCompat.requireNonNull(str, "permission must be non-null");
        return context.checkPermission(str, Process.myPid(), Process.myUid());
    }

    public static int getColor(Context context, int i) {
        return Api23Impl.getColor(context, i);
    }

    public static ColorStateList getColorStateList(Context context, int i) {
        return ResourcesCompat.getColorStateList(context.getResources(), i, context.getTheme());
    }

    public static Drawable getDrawable(Context context, int i) {
        return Api21Impl.getDrawable(context, i);
    }

    public static Object getSystemService(Context context, Class cls) {
        return Api23Impl.getSystemService(context, cls);
    }
}
