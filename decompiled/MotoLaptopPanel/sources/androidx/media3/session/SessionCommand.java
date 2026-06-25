package androidx.media3.session;

import android.os.Bundle;
import android.text.TextUtils;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

/* JADX INFO: loaded from: classes.dex */
public final class SessionCommand {
    public final int commandCode;
    public final String customAction;
    public final Bundle customExtras;
    static final ImmutableList SESSION_COMMANDS = ImmutableList.of((Object) 40010);
    static final ImmutableList LIBRARY_COMMANDS = ImmutableList.of((Object) 50000, (Object) 50001, (Object) 50002, (Object) 50003, (Object) 50004, (Object) 50005, (Object) 50006);
    private static final String FIELD_COMMAND_CODE = Util.intToStringMaxRadix(0);
    private static final String FIELD_CUSTOM_ACTION = Util.intToStringMaxRadix(1);
    private static final String FIELD_CUSTOM_EXTRAS = Util.intToStringMaxRadix(2);

    public SessionCommand(int i) {
        Assertions.checkArgument(i != 0, "commandCode shouldn't be COMMAND_CODE_CUSTOM");
        this.commandCode = i;
        this.customAction = "";
        this.customExtras = Bundle.EMPTY;
    }

    public SessionCommand(String str, Bundle bundle) {
        this.commandCode = 0;
        this.customAction = (String) Assertions.checkNotNull(str);
        this.customExtras = new Bundle((Bundle) Assertions.checkNotNull(bundle));
    }

    public static SessionCommand fromBundle(Bundle bundle) {
        int i = bundle.getInt(FIELD_COMMAND_CODE, 0);
        if (i != 0) {
            return new SessionCommand(i);
        }
        String str = (String) Assertions.checkNotNull(bundle.getString(FIELD_CUSTOM_ACTION));
        Bundle bundle2 = bundle.getBundle(FIELD_CUSTOM_EXTRAS);
        if (bundle2 == null) {
            bundle2 = Bundle.EMPTY;
        }
        return new SessionCommand(str, bundle2);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SessionCommand)) {
            return false;
        }
        SessionCommand sessionCommand = (SessionCommand) obj;
        return this.commandCode == sessionCommand.commandCode && TextUtils.equals(this.customAction, sessionCommand.customAction);
    }

    public int hashCode() {
        return Objects.hashCode(this.customAction, Integer.valueOf(this.commandCode));
    }
}
