package s;

import android.util.Base64;
import java.util.List;

/* JADX INFO: renamed from: s.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0158a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final String f2790a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final String f2791b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final String f2792c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final List f2793d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final String f2794e;

    public C0158a(String str, String str2, String str3, List list) {
        this.f2790a = str;
        this.f2791b = str2;
        this.f2792c = str3;
        list.getClass();
        this.f2793d = list;
        this.f2794e = str + "-" + str2 + "-" + str3;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FontRequest {mProviderAuthority: " + this.f2790a + ", mProviderPackage: " + this.f2791b + ", mQuery: " + this.f2792c + ", mCertificates:");
        int i2 = 0;
        while (true) {
            List list = this.f2793d;
            if (i2 >= list.size()) {
                sb.append("}mCertificatesArray: 0");
                return sb.toString();
            }
            sb.append(" [");
            List list2 = (List) list.get(i2);
            for (int i3 = 0; i3 < list2.size(); i3++) {
                sb.append(" \"");
                sb.append(Base64.encodeToString((byte[]) list2.get(i3), 0));
                sb.append("\"");
            }
            sb.append(" ]");
            i2++;
        }
    }
}
