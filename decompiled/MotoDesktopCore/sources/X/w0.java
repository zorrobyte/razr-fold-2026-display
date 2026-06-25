package X;

import androidx.fragment.app.AbstractComponentCallbacksC0098j;
import com.motorola.mobiledesktop.core.uinput.EventType;

/* JADX INFO: loaded from: classes.dex */
public abstract /* synthetic */ class w0 {
    public static String a(String str, AbstractComponentCallbacksC0098j abstractComponentCallbacksC0098j, String str2) {
        return str + abstractComponentCallbacksC0098j + str2;
    }

    public static void b(int i2, String str, String str2) {
        v0.a(str2, str + i2);
    }

    public static /* synthetic */ void c(Object obj) {
        if (obj != null) {
            throw new ClassCastException();
        }
    }

    public static /* synthetic */ String d(int i2) {
        switch (i2) {
            case 1:
                return "NONE";
            case 2:
                return "LEFT";
            case 3:
                return "TOP";
            case EventType.EVENT_MSC /* 4 */:
                return "RIGHT";
            case EventType.EVENT_SW /* 5 */:
                return "BOTTOM";
            case 6:
                return "BASELINE";
            case 7:
                return "CENTER";
            case 8:
                return "CENTER_X";
            case 9:
                return "CENTER_Y";
            default:
                throw null;
        }
    }
}
