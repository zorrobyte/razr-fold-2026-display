package androidx.media3.session;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Pair;
import androidx.media3.common.Player;
import androidx.media3.common.util.Util;
import androidx.media3.session.PlayerInfo;
import androidx.media3.session.legacy.MediaBrowserServiceCompat$BrowserRoot;
import androidx.media3.session.legacy.PlaybackStateCompat;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
abstract class MediaUtils {
    public static final MediaBrowserServiceCompat$BrowserRoot defaultBrowserRoot = new MediaBrowserServiceCompat$BrowserRoot("androidx.media3.session.MediaLibraryService", null);

    public static boolean areEqualError(PlaybackStateCompat playbackStateCompat, PlaybackStateCompat playbackStateCompat2) {
        boolean z = playbackStateCompat != null && playbackStateCompat.getState() == 7;
        boolean z2 = playbackStateCompat2 != null && playbackStateCompat2.getState() == 7;
        return (z && z2) ? ((PlaybackStateCompat) Util.castNonNull(playbackStateCompat)).getErrorCode() == ((PlaybackStateCompat) Util.castNonNull(playbackStateCompat2)).getErrorCode() && TextUtils.equals(((PlaybackStateCompat) Util.castNonNull(playbackStateCompat)).getErrorMessage(), ((PlaybackStateCompat) Util.castNonNull(playbackStateCompat2)).getErrorMessage()) : z == z2;
    }

    public static boolean areSessionPositionInfosInSamePeriodOrAd(SessionPositionInfo sessionPositionInfo, SessionPositionInfo sessionPositionInfo2) {
        Player.PositionInfo positionInfo = sessionPositionInfo.positionInfo;
        int i = positionInfo.mediaItemIndex;
        Player.PositionInfo positionInfo2 = sessionPositionInfo2.positionInfo;
        return i == positionInfo2.mediaItemIndex && positionInfo.periodIndex == positionInfo2.periodIndex && positionInfo.adGroupIndex == positionInfo2.adGroupIndex && positionInfo.adIndexInAdGroup == positionInfo2.adIndexInAdGroup;
    }

    public static int calculateBufferedPercentage(long j, long j2) {
        if (j == -9223372036854775807L || j2 == -9223372036854775807L) {
            return 0;
        }
        if (j2 == 0) {
            return 100;
        }
        return Util.constrainValue((int) ((j * 100) / j2), 0, 100);
    }

    public static long getUpdatedCurrentPositionMs(PlayerInfo playerInfo, long j, long j2, long j3) {
        boolean z = playerInfo.sessionPositionInfo.equals(SessionPositionInfo.DEFAULT) || j2 < playerInfo.sessionPositionInfo.eventTimeMs;
        if (playerInfo.isPlaying) {
            if (z || j == -9223372036854775807L) {
                if (j3 == -9223372036854775807L) {
                    j3 = SystemClock.elapsedRealtime() - playerInfo.sessionPositionInfo.eventTimeMs;
                }
                SessionPositionInfo sessionPositionInfo = playerInfo.sessionPositionInfo;
                long j4 = sessionPositionInfo.positionInfo.positionMs + ((long) (j3 * playerInfo.playbackParameters.speed));
                long j5 = sessionPositionInfo.durationMs;
                return j5 != -9223372036854775807L ? Math.min(j4, j5) : j4;
            }
        } else if (z || j == -9223372036854775807L) {
            return playerInfo.sessionPositionInfo.positionInfo.positionMs;
        }
        return j;
    }

    public static Player.Commands intersect(Player.Commands commands, Player.Commands commands2) {
        if (commands == null || commands2 == null) {
            return Player.Commands.EMPTY;
        }
        Player.Commands.Builder builder = new Player.Commands.Builder();
        for (int i = 0; i < commands.size(); i++) {
            if (commands2.contains(commands.get(i))) {
                builder.add(commands.get(i));
            }
        }
        return builder.build();
    }

    public static Pair mergePlayerInfo(PlayerInfo playerInfo, PlayerInfo.BundlingExclusions bundlingExclusions, PlayerInfo playerInfo2, PlayerInfo.BundlingExclusions bundlingExclusions2, Player.Commands commands) {
        PlayerInfo.BundlingExclusions bundlingExclusions3;
        if (bundlingExclusions2.isTimelineExcluded && commands.contains(17) && !bundlingExclusions.isTimelineExcluded) {
            playerInfo2 = playerInfo2.copyWithTimeline(playerInfo.timeline);
            bundlingExclusions3 = new PlayerInfo.BundlingExclusions(false, bundlingExclusions2.areCurrentTracksExcluded);
        } else {
            bundlingExclusions3 = bundlingExclusions2;
        }
        if (bundlingExclusions2.areCurrentTracksExcluded && commands.contains(30) && !bundlingExclusions.areCurrentTracksExcluded) {
            playerInfo2 = playerInfo2.copyWithCurrentTracks(playerInfo.currentTracks);
            bundlingExclusions3 = new PlayerInfo.BundlingExclusions(bundlingExclusions3.isTimelineExcluded, false);
        }
        return new Pair(playerInfo2, bundlingExclusions3);
    }

    public static List removeNullElements(List list) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj != null) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }
}
