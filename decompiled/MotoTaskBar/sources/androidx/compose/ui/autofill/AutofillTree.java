package androidx.compose.ui.autofill;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Unit;

/* JADX INFO: compiled from: AutofillTree.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AutofillTree {
    private final Map children = new LinkedHashMap();

    public final Map getChildren() {
        return this.children;
    }

    public final Unit performAutofill(int i, String str) {
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(this.children.get(Integer.valueOf(i)));
        return null;
    }
}
