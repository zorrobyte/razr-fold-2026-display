package androidx.media3.session;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.media3.common.Player;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.ImmutableIntArray;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class CommandButton {
    public final CharSequence displayName;
    public final Bundle extras;
    public final int icon;
    public final int iconResId;
    public final Uri iconUri;
    public final boolean isEnabled;
    public final int playerCommand;
    public final SessionCommand sessionCommand;
    public final ImmutableIntArray slots;
    private static final String FIELD_SESSION_COMMAND = Util.intToStringMaxRadix(0);
    private static final String FIELD_PLAYER_COMMAND = Util.intToStringMaxRadix(1);
    private static final String FIELD_ICON_RES_ID = Util.intToStringMaxRadix(2);
    private static final String FIELD_DISPLAY_NAME = Util.intToStringMaxRadix(3);
    private static final String FIELD_EXTRAS = Util.intToStringMaxRadix(4);
    private static final String FIELD_ENABLED = Util.intToStringMaxRadix(5);
    private static final String FIELD_ICON_URI = Util.intToStringMaxRadix(6);
    private static final String FIELD_ICON = Util.intToStringMaxRadix(7);
    private static final String FIELD_SLOTS = Util.intToStringMaxRadix(8);

    public final class Builder {
        private final int icon;
        private int iconResId;
        private Uri iconUri;
        private SessionCommand sessionCommand;
        private ImmutableIntArray slots;
        private CharSequence displayName = "";
        private Bundle extras = Bundle.EMPTY;
        private int playerCommand = -1;
        private boolean enabled = true;

        Builder(int i, int i2) {
            this.icon = i;
            this.iconResId = i2;
        }

        public CommandButton build() {
            Assertions.checkState((this.sessionCommand == null) != (this.playerCommand == -1), "Exactly one of sessionCommand and playerCommand should be set");
            if (this.slots == null) {
                this.slots = ImmutableIntArray.of(CommandButton.getDefaultSlot(this.playerCommand, this.icon));
            }
            return new CommandButton(this.sessionCommand, this.playerCommand, this.icon, this.iconResId, this.iconUri, this.displayName, this.extras, this.enabled, this.slots);
        }

        public Builder setDisplayName(CharSequence charSequence) {
            this.displayName = charSequence;
            return this;
        }

        public Builder setEnabled(boolean z) {
            this.enabled = z;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.extras = new Bundle(bundle);
            return this;
        }

        public Builder setIconUri(Uri uri) {
            Assertions.checkArgument(Objects.equal(uri.getScheme(), "content") || Objects.equal(uri.getScheme(), "android.resource"), "Only content or resource Uris are supported for CommandButton");
            this.iconUri = uri;
            return this;
        }

        public Builder setPlayerCommand(int i) {
            Assertions.checkArgument(this.sessionCommand == null, "sessionCommand is already set. Only one of sessionCommand and playerCommand should be set.");
            this.playerCommand = i;
            return this;
        }

        public Builder setSessionCommand(SessionCommand sessionCommand) {
            Assertions.checkNotNull(sessionCommand, "sessionCommand should not be null.");
            Assertions.checkArgument(this.playerCommand == -1, "playerCommands is already set. Only one of sessionCommand and playerCommand should be set.");
            this.sessionCommand = sessionCommand;
            return this;
        }

        public Builder setSlots(int... iArr) {
            Assertions.checkArgument(iArr.length != 0);
            this.slots = ImmutableIntArray.copyOf(iArr);
            return this;
        }
    }

    private CommandButton(SessionCommand sessionCommand, int i, int i2, int i3, Uri uri, CharSequence charSequence, Bundle bundle, boolean z, ImmutableIntArray immutableIntArray) {
        this.sessionCommand = sessionCommand;
        this.playerCommand = i;
        this.icon = i2;
        this.iconResId = i3;
        this.iconUri = uri;
        this.displayName = charSequence;
        this.extras = new Bundle(bundle);
        this.isEnabled = z;
        this.slots = immutableIntArray;
    }

    static ImmutableList copyWithUnavailableButtonsDisabled(List list, SessionCommands sessionCommands, Player.Commands commands) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        for (int i = 0; i < list.size(); i++) {
            CommandButton commandButton = (CommandButton) list.get(i);
            if (isButtonCommandAvailable(commandButton, sessionCommands, commands)) {
                builder.add((Object) commandButton);
            } else {
                builder.add((Object) commandButton.copyWithIsEnabled(false));
            }
        }
        return builder.build();
    }

    public static CommandButton fromBundle(Bundle bundle, int i) {
        Bundle bundle2 = bundle.getBundle(FIELD_SESSION_COMMAND);
        SessionCommand sessionCommandFromBundle = bundle2 == null ? null : SessionCommand.fromBundle(bundle2);
        int i2 = bundle.getInt(FIELD_PLAYER_COMMAND, -1);
        int i3 = bundle.getInt(FIELD_ICON_RES_ID, 0);
        CharSequence charSequence = bundle.getCharSequence(FIELD_DISPLAY_NAME, "");
        Bundle bundle3 = bundle.getBundle(FIELD_EXTRAS);
        boolean z = i < 3 || bundle.getBoolean(FIELD_ENABLED, true);
        Uri uri = (Uri) bundle.getParcelable(FIELD_ICON_URI);
        int i4 = bundle.getInt(FIELD_ICON, 0);
        int[] intArray = bundle.getIntArray(FIELD_SLOTS);
        Builder builder = new Builder(i4, i3);
        if (sessionCommandFromBundle != null) {
            builder.setSessionCommand(sessionCommandFromBundle);
        }
        if (i2 != -1) {
            builder.setPlayerCommand(i2);
        }
        if (uri != null && (Objects.equal(uri.getScheme(), "content") || Objects.equal(uri.getScheme(), "android.resource"))) {
            builder.setIconUri(uri);
        }
        Builder displayName = builder.setDisplayName(charSequence);
        if (bundle3 == null) {
            bundle3 = Bundle.EMPTY;
        }
        Builder enabled = displayName.setExtras(bundle3).setEnabled(z);
        if (intArray == null) {
            intArray = new int[]{6};
        }
        return enabled.setSlots(intArray).build();
    }

    static ImmutableList getCustomLayoutFromMediaButtonPreferences(List list, boolean z, boolean z2) {
        SessionCommand sessionCommand;
        SessionCommand sessionCommand2;
        int i;
        if (list.isEmpty()) {
            return ImmutableList.of();
        }
        int i2 = -1;
        int i3 = -1;
        for (int i4 = 0; i4 < list.size(); i4++) {
            CommandButton commandButton = (CommandButton) list.get(i4);
            if (commandButton.isEnabled && (sessionCommand2 = commandButton.sessionCommand) != null && sessionCommand2.commandCode == 0) {
                int i5 = 0;
                while (true) {
                    if (i5 >= commandButton.slots.length() || (i = commandButton.slots.get(i5)) == 6) {
                        break;
                    }
                    if (z && i2 == -1 && i == 2) {
                        i2 = i4;
                        break;
                    }
                    if (z2 && i3 == -1 && i == 3) {
                        i3 = i4;
                        break;
                    }
                    i5++;
                }
            }
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        if (i2 != -1) {
            builder.add((Object) ((CommandButton) list.get(i2)).copyWithSlots(ImmutableIntArray.of(2)));
        }
        if (i3 != -1) {
            builder.add((Object) ((CommandButton) list.get(i3)).copyWithSlots(ImmutableIntArray.of(3)));
        }
        for (int i6 = 0; i6 < list.size(); i6++) {
            CommandButton commandButton2 = (CommandButton) list.get(i6);
            if (commandButton2.isEnabled && (sessionCommand = commandButton2.sessionCommand) != null && sessionCommand.commandCode == 0 && i6 != i2 && i6 != i3 && commandButton2.slots.contains(6)) {
                builder.add((Object) commandButton2.copyWithSlots(ImmutableIntArray.of(6)));
            }
        }
        return builder.build();
    }

    public static int getDefaultSlot(int i, int i2) {
        if (i == 1 || i2 == 57399 || i2 == 57396) {
            return 1;
        }
        if (i == 11 || i == 7 || i == 6 || i2 == 57413 || i2 == 57376 || i2 == 57410 || i2 == 57435 || i2 == 57433 || i2 == 1040473 || i2 == 57434) {
            return 2;
        }
        return (i == 12 || i == 9 || i == 8 || i2 == 57412 || i2 == 57375 || i2 == 63220 || i2 == 57432 || i2 == 57430 || i2 == 1040470 || i2 == 57431) ? 3 : 6;
    }

    static ImmutableList getMediaButtonPreferencesFromCustomLayout(List list, Player.Commands commands, Bundle bundle) {
        if (list.isEmpty()) {
            return ImmutableList.of();
        }
        boolean zContainsAny = commands.containsAny(7, 6);
        boolean zContainsAny2 = commands.containsAny(9, 8);
        boolean z = bundle.getBoolean("android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_PREVIOUS", false);
        boolean z2 = bundle.getBoolean("android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_NEXT", false);
        int i = (zContainsAny || z) ? -1 : 0;
        int i2 = (zContainsAny2 || z2) ? -1 : i == 0 ? 1 : 0;
        ImmutableList.Builder builder = ImmutableList.builder();
        for (int i3 = 0; i3 < list.size(); i3++) {
            CommandButton commandButton = (CommandButton) list.get(i3);
            if (i3 == i) {
                if (i2 == -1) {
                    builder.add((Object) commandButton.copyWithSlots(ImmutableIntArray.of(2, 6)));
                } else {
                    builder.add((Object) commandButton.copyWithSlots(ImmutableIntArray.of(2, 3, 6)));
                }
            } else if (i3 == i2) {
                builder.add((Object) commandButton.copyWithSlots(ImmutableIntArray.of(3, 6)));
            } else {
                builder.add((Object) commandButton.copyWithSlots(ImmutableIntArray.of(6)));
            }
        }
        return builder.build();
    }

    static boolean isButtonCommandAvailable(CommandButton commandButton, SessionCommands sessionCommands, Player.Commands commands) {
        SessionCommand sessionCommand = commandButton.sessionCommand;
        if (sessionCommand != null && sessionCommands.contains(sessionCommand)) {
            return true;
        }
        int i = commandButton.playerCommand;
        return i != -1 && commands.contains(i);
    }

    CommandButton copyWithIsEnabled(boolean z) {
        return this.isEnabled == z ? this : new CommandButton(this.sessionCommand, this.playerCommand, this.icon, this.iconResId, this.iconUri, this.displayName, new Bundle(this.extras), z, this.slots);
    }

    CommandButton copyWithSlots(ImmutableIntArray immutableIntArray) {
        return this.slots.equals(immutableIntArray) ? this : new CommandButton(this.sessionCommand, this.playerCommand, this.icon, this.iconResId, this.iconUri, this.displayName, new Bundle(this.extras), this.isEnabled, immutableIntArray);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CommandButton)) {
            return false;
        }
        CommandButton commandButton = (CommandButton) obj;
        return Objects.equal(this.sessionCommand, commandButton.sessionCommand) && this.playerCommand == commandButton.playerCommand && this.icon == commandButton.icon && this.iconResId == commandButton.iconResId && Objects.equal(this.iconUri, commandButton.iconUri) && TextUtils.equals(this.displayName, commandButton.displayName) && this.isEnabled == commandButton.isEnabled && this.slots.equals(commandButton.slots);
    }

    public int hashCode() {
        return Objects.hashCode(this.sessionCommand, Integer.valueOf(this.playerCommand), Integer.valueOf(this.icon), Integer.valueOf(this.iconResId), this.displayName, Boolean.valueOf(this.isEnabled), this.iconUri, this.slots);
    }
}
