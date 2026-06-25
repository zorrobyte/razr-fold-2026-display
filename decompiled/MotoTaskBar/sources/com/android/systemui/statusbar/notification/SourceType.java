package com.android.systemui.statusbar.notification;

/* JADX INFO: compiled from: Roundable.kt */
/* JADX INFO: loaded from: classes.dex */
public interface SourceType {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: Roundable.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final SourceType from(final String str) {
            str.getClass();
            return new SourceType() { // from class: com.android.systemui.statusbar.notification.SourceType$Companion$from$1
                public String toString() {
                    return str;
                }
            };
        }
    }

    static SourceType from(String str) {
        return Companion.from(str);
    }
}
