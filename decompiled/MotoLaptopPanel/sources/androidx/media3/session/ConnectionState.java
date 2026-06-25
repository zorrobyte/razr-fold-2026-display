package androidx.media3.session;

import android.app.PendingIntent;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.IBinder;
import androidx.core.app.BundleCompat;
import androidx.media3.common.Player;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.BundleCollectionUtil;
import androidx.media3.common.util.Util;
import androidx.media3.session.IMediaSession;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
class ConnectionState {
    public final ImmutableList commandButtonsForMediaItems;
    public final ImmutableList customLayout;
    public final int libraryVersion;
    public final ImmutableList mediaButtonPreferences;
    public final MediaSession.Token platformToken;
    public final Player.Commands playerCommandsFromPlayer;
    public final Player.Commands playerCommandsFromSession;
    public final PlayerInfo playerInfo;
    public final PendingIntent sessionActivity;
    public final IMediaSession sessionBinder;
    public final SessionCommands sessionCommands;
    public final Bundle sessionExtras;
    public final int sessionInterfaceVersion;
    public final Bundle tokenExtras;
    private static final String FIELD_LIBRARY_VERSION = Util.intToStringMaxRadix(0);
    private static final String FIELD_SESSION_BINDER = Util.intToStringMaxRadix(1);
    private static final String FIELD_SESSION_ACTIVITY = Util.intToStringMaxRadix(2);
    private static final String FIELD_CUSTOM_LAYOUT = Util.intToStringMaxRadix(9);
    private static final String FIELD_MEDIA_BUTTON_PREFERENCES = Util.intToStringMaxRadix(14);
    private static final String FIELD_COMMAND_BUTTONS_FOR_MEDIA_ITEMS = Util.intToStringMaxRadix(13);
    private static final String FIELD_SESSION_COMMANDS = Util.intToStringMaxRadix(3);
    private static final String FIELD_PLAYER_COMMANDS_FROM_SESSION = Util.intToStringMaxRadix(4);
    private static final String FIELD_PLAYER_COMMANDS_FROM_PLAYER = Util.intToStringMaxRadix(5);
    private static final String FIELD_TOKEN_EXTRAS = Util.intToStringMaxRadix(6);
    private static final String FIELD_SESSION_EXTRAS = Util.intToStringMaxRadix(11);
    private static final String FIELD_PLAYER_INFO = Util.intToStringMaxRadix(7);
    private static final String FIELD_SESSION_INTERFACE_VERSION = Util.intToStringMaxRadix(8);
    private static final String FIELD_IN_PROCESS_BINDER = Util.intToStringMaxRadix(10);
    private static final String FIELD_PLATFORM_TOKEN = Util.intToStringMaxRadix(12);

    public ConnectionState(int i, int i2, IMediaSession iMediaSession, PendingIntent pendingIntent, ImmutableList immutableList, ImmutableList immutableList2, ImmutableList immutableList3, SessionCommands sessionCommands, Player.Commands commands, Player.Commands commands2, Bundle bundle, Bundle bundle2, PlayerInfo playerInfo, MediaSession.Token token) {
        this.libraryVersion = i;
        this.sessionInterfaceVersion = i2;
        this.sessionBinder = iMediaSession;
        this.sessionActivity = pendingIntent;
        this.customLayout = immutableList;
        this.mediaButtonPreferences = immutableList2;
        this.commandButtonsForMediaItems = immutableList3;
        this.sessionCommands = sessionCommands;
        this.playerCommandsFromSession = commands;
        this.playerCommandsFromPlayer = commands2;
        this.tokenExtras = bundle;
        this.sessionExtras = bundle2;
        this.playerInfo = playerInfo;
        this.platformToken = token;
    }

    public static ConnectionState fromBundle(Bundle bundle) {
        bundle.getBinder(FIELD_IN_PROCESS_BINDER);
        int i = bundle.getInt(FIELD_LIBRARY_VERSION, 0);
        final int i2 = bundle.getInt(FIELD_SESSION_INTERFACE_VERSION, 0);
        IBinder iBinder = (IBinder) Assertions.checkNotNull(BundleCompat.getBinder(bundle, FIELD_SESSION_BINDER));
        PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable(FIELD_SESSION_ACTIVITY);
        ArrayList parcelableArrayList = bundle.getParcelableArrayList(FIELD_CUSTOM_LAYOUT);
        ImmutableList immutableListFromBundleList = parcelableArrayList != null ? BundleCollectionUtil.fromBundleList(new Function() { // from class: androidx.media3.session.ConnectionState$$ExternalSyntheticLambda0
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return CommandButton.fromBundle((Bundle) obj, i2);
            }
        }, parcelableArrayList) : ImmutableList.of();
        ArrayList parcelableArrayList2 = bundle.getParcelableArrayList(FIELD_MEDIA_BUTTON_PREFERENCES);
        ImmutableList immutableListFromBundleList2 = parcelableArrayList2 != null ? BundleCollectionUtil.fromBundleList(new Function() { // from class: androidx.media3.session.ConnectionState$$ExternalSyntheticLambda1
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return CommandButton.fromBundle((Bundle) obj, i2);
            }
        }, parcelableArrayList2) : ImmutableList.of();
        ArrayList parcelableArrayList3 = bundle.getParcelableArrayList(FIELD_COMMAND_BUTTONS_FOR_MEDIA_ITEMS);
        ImmutableList immutableListFromBundleList3 = parcelableArrayList3 != null ? BundleCollectionUtil.fromBundleList(new Function() { // from class: androidx.media3.session.ConnectionState$$ExternalSyntheticLambda2
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return CommandButton.fromBundle((Bundle) obj, i2);
            }
        }, parcelableArrayList3) : ImmutableList.of();
        Bundle bundle2 = bundle.getBundle(FIELD_SESSION_COMMANDS);
        SessionCommands sessionCommandsFromBundle = bundle2 == null ? SessionCommands.EMPTY : SessionCommands.fromBundle(bundle2);
        Bundle bundle3 = bundle.getBundle(FIELD_PLAYER_COMMANDS_FROM_PLAYER);
        Player.Commands commandsFromBundle = bundle3 == null ? Player.Commands.EMPTY : Player.Commands.fromBundle(bundle3);
        Bundle bundle4 = bundle.getBundle(FIELD_PLAYER_COMMANDS_FROM_SESSION);
        Player.Commands commandsFromBundle2 = bundle4 == null ? Player.Commands.EMPTY : Player.Commands.fromBundle(bundle4);
        Bundle bundle5 = bundle.getBundle(FIELD_TOKEN_EXTRAS);
        Bundle bundle6 = bundle.getBundle(FIELD_SESSION_EXTRAS);
        Bundle bundle7 = bundle.getBundle(FIELD_PLAYER_INFO);
        PlayerInfo playerInfoFromBundle = bundle7 == null ? PlayerInfo.DEFAULT : PlayerInfo.fromBundle(bundle7, i2);
        MediaSession.Token token = (MediaSession.Token) bundle.getParcelable(FIELD_PLATFORM_TOKEN);
        Bundle bundle8 = bundle6;
        IMediaSession iMediaSessionAsInterface = IMediaSession.Stub.asInterface(iBinder);
        if (bundle5 == null) {
            bundle5 = Bundle.EMPTY;
        }
        Bundle bundle9 = bundle5;
        if (bundle8 == null) {
            bundle8 = Bundle.EMPTY;
        }
        return new ConnectionState(i, i2, iMediaSessionAsInterface, pendingIntent, immutableListFromBundleList, immutableListFromBundleList2, immutableListFromBundleList3, sessionCommandsFromBundle, commandsFromBundle2, commandsFromBundle, bundle9, bundle8, playerInfoFromBundle, token);
    }
}
