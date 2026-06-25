package h;

import java.util.LinkedHashMap;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class f {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final LinkedHashMap f2588a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f2589b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f2590c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f2591d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f2592e;

    public f(int i2) {
        if (i2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.f2590c = i2;
        this.f2588a = new LinkedHashMap(0, 0.75f, true);
    }

    public final Object a(Object obj) {
        if (obj == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            try {
                Object obj2 = this.f2588a.get(obj);
                if (obj2 != null) {
                    this.f2591d++;
                    return obj2;
                }
                this.f2592e++;
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0084, code lost:
    
        throw new java.lang.IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object b(java.lang.Object r3, java.lang.Object r4) {
        /*
            r2 = this;
            if (r3 == 0) goto L89
            if (r4 == 0) goto L89
            monitor-enter(r2)
            int r0 = r2.f2589b     // Catch: java.lang.Throwable -> L1a
            int r0 = r0 + 1
            r2.f2589b = r0     // Catch: java.lang.Throwable -> L1a
            java.util.LinkedHashMap r0 = r2.f2588a     // Catch: java.lang.Throwable -> L1a
            java.lang.Object r3 = r0.put(r3, r4)     // Catch: java.lang.Throwable -> L1a
            if (r3 == 0) goto L1c
            int r4 = r2.f2589b     // Catch: java.lang.Throwable -> L1a
            int r4 = r4 + (-1)
            r2.f2589b = r4     // Catch: java.lang.Throwable -> L1a
            goto L1c
        L1a:
            r3 = move-exception
            goto L87
        L1c:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L1a
            int r4 = r2.f2590c
        L1f:
            monitor-enter(r2)
            int r0 = r2.f2589b     // Catch: java.lang.Throwable -> L31
            if (r0 < 0) goto L66
            java.util.LinkedHashMap r0 = r2.f2588a     // Catch: java.lang.Throwable -> L31
            boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> L31
            if (r0 == 0) goto L33
            int r0 = r2.f2589b     // Catch: java.lang.Throwable -> L31
            if (r0 != 0) goto L66
            goto L33
        L31:
            r3 = move-exception
            goto L85
        L33:
            int r0 = r2.f2589b     // Catch: java.lang.Throwable -> L31
            if (r0 <= r4) goto L64
            java.util.LinkedHashMap r0 = r2.f2588a     // Catch: java.lang.Throwable -> L31
            boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> L31
            if (r0 == 0) goto L40
            goto L64
        L40:
            java.util.LinkedHashMap r0 = r2.f2588a     // Catch: java.lang.Throwable -> L31
            java.util.Set r0 = r0.entrySet()     // Catch: java.lang.Throwable -> L31
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L31
            java.lang.Object r0 = r0.next()     // Catch: java.lang.Throwable -> L31
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch: java.lang.Throwable -> L31
            java.lang.Object r1 = r0.getKey()     // Catch: java.lang.Throwable -> L31
            r0.getValue()     // Catch: java.lang.Throwable -> L31
            java.util.LinkedHashMap r0 = r2.f2588a     // Catch: java.lang.Throwable -> L31
            r0.remove(r1)     // Catch: java.lang.Throwable -> L31
            int r0 = r2.f2589b     // Catch: java.lang.Throwable -> L31
            int r0 = r0 + (-1)
            r2.f2589b = r0     // Catch: java.lang.Throwable -> L31
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L31
            goto L1f
        L64:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L31
            return r3
        L66:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L31
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L31
            r4.<init>()     // Catch: java.lang.Throwable -> L31
            java.lang.Class r0 = r2.getClass()     // Catch: java.lang.Throwable -> L31
            java.lang.String r0 = r0.getName()     // Catch: java.lang.Throwable -> L31
            r4.append(r0)     // Catch: java.lang.Throwable -> L31
            java.lang.String r0 = ".sizeOf() is reporting inconsistent results!"
            r4.append(r0)     // Catch: java.lang.Throwable -> L31
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L31
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L31
            throw r3     // Catch: java.lang.Throwable -> L31
        L85:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L31
            throw r3
        L87:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L1a
            throw r3
        L89:
            java.lang.NullPointerException r2 = new java.lang.NullPointerException
            java.lang.String r3 = "key == null || value == null"
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: h.f.b(java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public final synchronized String toString() {
        int i2;
        try {
            int i3 = this.f2591d;
            int i4 = this.f2592e + i3;
            i2 = i4 != 0 ? (i3 * 100) / i4 : 0;
            Locale locale = Locale.US;
        } catch (Throwable th) {
            throw th;
        }
        return "LruCache[maxSize=" + this.f2590c + ",hits=" + this.f2591d + ",misses=" + this.f2592e + ",hitRate=" + i2 + "%]";
    }
}
