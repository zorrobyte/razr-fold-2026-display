package com.android.systemui.statusbar.notification.collection.listbuilder;

import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NotifSection.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifSection {
    private final int bucket;
    private final NotifComparator comparator;
    private final NodeController headerController;
    private final int index;
    private final String label;
    private final NotifSectioner sectioner;

    public NotifSection(NotifSectioner notifSectioner, int i) {
        notifSectioner.getClass();
        this.sectioner = notifSectioner;
        this.index = i;
        int bucket = notifSectioner.getBucket();
        this.bucket = bucket;
        this.label = i + ":" + bucket + ":" + notifSectioner.getName();
        this.headerController = notifSectioner.getHeaderNodeController();
        this.comparator = notifSectioner.getComparator();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotifSection)) {
            return false;
        }
        NotifSection notifSection = (NotifSection) obj;
        return Intrinsics.areEqual(this.sectioner, notifSection.sectioner) && this.index == notifSection.index;
    }

    public final int getBucket() {
        return this.bucket;
    }

    public final NotifComparator getComparator() {
        return this.comparator;
    }

    public final NodeController getHeaderController() {
        return this.headerController;
    }

    public final int getIndex() {
        return this.index;
    }

    public final String getLabel() {
        return this.label;
    }

    public final NotifSectioner getSectioner() {
        return this.sectioner;
    }

    public int hashCode() {
        return (this.sectioner.hashCode() * 31) + Integer.hashCode(this.index);
    }

    public String toString() {
        return "NotifSection(sectioner=" + this.sectioner + ", index=" + this.index + ")";
    }
}
