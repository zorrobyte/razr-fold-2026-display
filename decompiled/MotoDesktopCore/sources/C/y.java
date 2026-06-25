package C;

import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class y {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public View f103b;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final HashMap f102a = new HashMap();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final ArrayList f104c = new ArrayList();

    public final boolean equals(Object obj) {
        if (!(obj instanceof y)) {
            return false;
        }
        y yVar = (y) obj;
        return this.f103b == yVar.f103b && this.f102a.equals(yVar.f102a);
    }

    public final int hashCode() {
        return this.f102a.hashCode() + (this.f103b.hashCode() * 31);
    }

    public final String toString() {
        String str = (("TransitionValues@" + Integer.toHexString(hashCode()) + ":\n") + "    view = " + this.f103b + "\n") + "    values:";
        HashMap map = this.f102a;
        for (String str2 : map.keySet()) {
            str = str + "    " + str2 + ": " + map.get(str2) + "\n";
        }
        return str;
    }
}
