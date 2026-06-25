package androidx.activity.result.contract;

import android.content.Context;
import android.content.Intent;

/* JADX INFO: compiled from: ActivityResultContract.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityResultContract {

    /* JADX INFO: compiled from: ActivityResultContract.kt */
    public final class SynchronousResult {
        private final Object value;

        public SynchronousResult(Object obj) {
            this.value = obj;
        }

        public final Object getValue() {
            return this.value;
        }
    }

    public abstract Intent createIntent(Context context, Object obj);

    public SynchronousResult getSynchronousResult(Context context, Object obj) {
        context.getClass();
        return null;
    }

    public abstract Object parseResult(int i, Intent intent);
}
