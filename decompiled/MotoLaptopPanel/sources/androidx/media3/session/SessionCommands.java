package androidx.media3.session;

import android.os.Bundle;
import androidx.core.util.ObjectsCompat;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.Util;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class SessionCommands {
    public static final SessionCommands EMPTY = new Builder().build();
    private static final String FIELD_SESSION_COMMANDS = Util.intToStringMaxRadix(0);
    public final ImmutableSet commands;

    public final class Builder {
        private final Set commands = new HashSet();

        private void addCommandCodes(List list) {
            for (int i = 0; i < list.size(); i++) {
                add(new SessionCommand(((Integer) list.get(i)).intValue()));
            }
        }

        public Builder add(SessionCommand sessionCommand) {
            this.commands.add((SessionCommand) Assertions.checkNotNull(sessionCommand));
            return this;
        }

        Builder addAllSessionCommands() {
            addCommandCodes(SessionCommand.SESSION_COMMANDS);
            return this;
        }

        public SessionCommands build() {
            return new SessionCommands(this.commands);
        }

        public Builder remove(int i) {
            Assertions.checkArgument(i != 0);
            Iterator it = this.commands.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                SessionCommand sessionCommand = (SessionCommand) it.next();
                if (sessionCommand.commandCode == i) {
                    this.commands.remove(sessionCommand);
                    break;
                }
            }
            return this;
        }
    }

    private SessionCommands(Collection collection) {
        this.commands = ImmutableSet.copyOf(collection);
    }

    public static SessionCommands fromBundle(Bundle bundle) {
        ArrayList parcelableArrayList = bundle.getParcelableArrayList(FIELD_SESSION_COMMANDS);
        if (parcelableArrayList == null) {
            Log.w("SessionCommands", "Missing commands. Creating an empty SessionCommands");
            return EMPTY;
        }
        Builder builder = new Builder();
        for (int i = 0; i < parcelableArrayList.size(); i++) {
            builder.add(SessionCommand.fromBundle((Bundle) parcelableArrayList.get(i)));
        }
        return builder.build();
    }

    public boolean contains(SessionCommand sessionCommand) {
        return this.commands.contains(Assertions.checkNotNull(sessionCommand));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SessionCommands) {
            return this.commands.equals(((SessionCommands) obj).commands);
        }
        return false;
    }

    public int hashCode() {
        return ObjectsCompat.hash(this.commands);
    }
}
