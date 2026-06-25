package androidx.media3.session;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.DeviceInfo;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.Player;
import androidx.media3.common.Rating;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.Util;
import androidx.media3.session.CommandButton;
import androidx.media3.session.SessionCommands;
import androidx.media3.session.legacy.AudioAttributesCompat;
import androidx.media3.session.legacy.MediaBrowserServiceCompat$BrowserRoot;
import androidx.media3.session.legacy.MediaControllerCompat;
import androidx.media3.session.legacy.MediaDescriptionCompat;
import androidx.media3.session.legacy.MediaMetadataCompat;
import androidx.media3.session.legacy.MediaSessionCompat;
import androidx.media3.session.legacy.PlaybackStateCompat;
import androidx.media3.session.legacy.RatingCompat;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
abstract class LegacyConversions {
    public static final MediaBrowserServiceCompat$BrowserRoot defaultBrowserRoot = new MediaBrowserServiceCompat$BrowserRoot("androidx.media3.session.MediaLibraryService", null);
    public static final ImmutableSet KNOWN_METADATA_COMPAT_KEYS = ImmutableSet.of((Object) "android.media.metadata.TITLE", (Object) "android.media.metadata.ARTIST", (Object) "android.media.metadata.DURATION", (Object) "android.media.metadata.ALBUM", (Object) "android.media.metadata.AUTHOR", (Object) "android.media.metadata.WRITER", (Object[]) new String[]{"android.media.metadata.COMPOSER", "android.media.metadata.COMPILATION", "android.media.metadata.DATE", "android.media.metadata.YEAR", "android.media.metadata.GENRE", "android.media.metadata.TRACK_NUMBER", "android.media.metadata.NUM_TRACKS", "android.media.metadata.DISC_NUMBER", "android.media.metadata.ALBUM_ARTIST", "android.media.metadata.ART", "android.media.metadata.ART_URI", "android.media.metadata.ALBUM_ART", "android.media.metadata.ALBUM_ART_URI", "android.media.metadata.USER_RATING", "android.media.metadata.RATING", "android.media.metadata.DISPLAY_TITLE", "android.media.metadata.DISPLAY_SUBTITLE", "android.media.metadata.DISPLAY_DESCRIPTION", "android.media.metadata.DISPLAY_ICON", "android.media.metadata.DISPLAY_ICON_URI", "android.media.metadata.MEDIA_ID", "android.media.metadata.MEDIA_URI", "android.media.metadata.BT_FOLDER_TYPE", "android.media.metadata.ADVERTISEMENT", "android.media.metadata.DOWNLOAD_STATUS", "androidx.media3.session.EXTRAS_KEY_MEDIA_TYPE_COMPAT"});

    public class ConversionException extends Exception {
        private ConversionException(String str) {
            super(str);
        }
    }

    public static AudioAttributes convertToAudioAttributes(AudioAttributesCompat audioAttributesCompat) {
        return audioAttributesCompat == null ? AudioAttributes.DEFAULT : new AudioAttributes.Builder().setContentType(audioAttributesCompat.getContentType()).setFlags(audioAttributesCompat.getFlags()).setUsage(audioAttributesCompat.getUsage()).build();
    }

    public static AudioAttributes convertToAudioAttributes(MediaControllerCompat.PlaybackInfo playbackInfo) {
        return playbackInfo == null ? AudioAttributes.DEFAULT : convertToAudioAttributes(playbackInfo.getAudioAttributes());
    }

    public static int convertToBufferedPercentage(PlaybackStateCompat playbackStateCompat, MediaMetadataCompat mediaMetadataCompat, long j) {
        return MediaUtils.calculateBufferedPercentage(convertToBufferedPositionMs(playbackStateCompat, mediaMetadataCompat, j), convertToDurationMs(mediaMetadataCompat));
    }

    public static long convertToBufferedPositionMs(PlaybackStateCompat playbackStateCompat, MediaMetadataCompat mediaMetadataCompat, long j) {
        long bufferedPosition = playbackStateCompat == null ? 0L : playbackStateCompat.getBufferedPosition();
        long jConvertToCurrentPositionMs = convertToCurrentPositionMs(playbackStateCompat, mediaMetadataCompat, j);
        long jConvertToDurationMs = convertToDurationMs(mediaMetadataCompat);
        return jConvertToDurationMs == -9223372036854775807L ? Math.max(jConvertToCurrentPositionMs, bufferedPosition) : Util.constrainValue(bufferedPosition, jConvertToCurrentPositionMs, jConvertToDurationMs);
    }

    private static byte[] convertToByteArray(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static long convertToCurrentPositionMs(PlaybackStateCompat playbackStateCompat, MediaMetadataCompat mediaMetadataCompat, long j) {
        if (playbackStateCompat == null) {
            return 0L;
        }
        long currentPosition = playbackStateCompat.getState() == 3 ? getCurrentPosition(playbackStateCompat, j) : playbackStateCompat.getPosition();
        long jConvertToDurationMs = convertToDurationMs(mediaMetadataCompat);
        return jConvertToDurationMs == -9223372036854775807L ? Math.max(0L, currentPosition) : Util.constrainValue(currentPosition, 0L, jConvertToDurationMs);
    }

    public static DeviceInfo convertToDeviceInfo(MediaControllerCompat.PlaybackInfo playbackInfo, String str) {
        if (playbackInfo == null) {
            return DeviceInfo.UNKNOWN;
        }
        return new DeviceInfo.Builder(playbackInfo.getPlaybackType() == 2 ? 1 : 0).setMaxVolume(playbackInfo.getMaxVolume()).setRoutingControllerId(str).build();
    }

    public static int convertToDeviceVolume(MediaControllerCompat.PlaybackInfo playbackInfo) {
        if (playbackInfo == null) {
            return 0;
        }
        return playbackInfo.getCurrentVolume();
    }

    public static long convertToDurationMs(MediaMetadataCompat mediaMetadataCompat) {
        if (mediaMetadataCompat == null || !mediaMetadataCompat.containsKey("android.media.metadata.DURATION")) {
            return -9223372036854775807L;
        }
        long j = mediaMetadataCompat.getLong("android.media.metadata.DURATION");
        if (j <= 0) {
            return -9223372036854775807L;
        }
        return j;
    }

    private static int convertToFolderType(long j) {
        if (j == 0) {
            return 0;
        }
        if (j == 1) {
            return 1;
        }
        if (j == 2) {
            return 2;
        }
        if (j == 3) {
            return 3;
        }
        if (j == 4) {
            return 4;
        }
        if (j == 5) {
            return 5;
        }
        return j == 6 ? 6 : 0;
    }

    public static boolean convertToIsDeviceMuted(MediaControllerCompat.PlaybackInfo playbackInfo) {
        return playbackInfo != null && playbackInfo.getCurrentVolume() == 0;
    }

    public static boolean convertToIsPlaying(PlaybackStateCompat playbackStateCompat) {
        return playbackStateCompat != null && playbackStateCompat.getState() == 3;
    }

    public static boolean convertToIsPlayingAd(MediaMetadataCompat mediaMetadataCompat) {
        return (mediaMetadataCompat == null || mediaMetadataCompat.getLong("android.media.metadata.ADVERTISEMENT") == 0) ? false : true;
    }

    public static ImmutableList convertToMediaButtonPreferences(PlaybackStateCompat playbackStateCompat, Player.Commands commands, Bundle bundle) {
        List<PlaybackStateCompat.CustomAction> customActions;
        if (playbackStateCompat != null && (customActions = playbackStateCompat.getCustomActions()) != null) {
            ImmutableList.Builder builder = new ImmutableList.Builder();
            for (PlaybackStateCompat.CustomAction customAction : customActions) {
                String action = customAction.getAction();
                Bundle extras = customAction.getExtras();
                CommandButton.Builder builder2 = new CommandButton.Builder(extras != null ? extras.getInt("androidx.media3.session.EXTRAS_KEY_COMMAND_BUTTON_ICON_COMPAT", 0) : 0, customAction.getIcon());
                if (extras == null) {
                    extras = Bundle.EMPTY;
                }
                builder.add((Object) builder2.setSessionCommand(new SessionCommand(action, extras)).setDisplayName(customAction.getName()).setEnabled(true).build());
            }
            return CommandButton.getMediaButtonPreferencesFromCustomLayout(builder.build(), commands, bundle);
        }
        return ImmutableList.of();
    }

    public static MediaItem convertToMediaItem(MediaDescriptionCompat mediaDescriptionCompat) {
        Assertions.checkNotNull(mediaDescriptionCompat);
        return convertToMediaItem(mediaDescriptionCompat, false, true);
    }

    private static MediaItem convertToMediaItem(MediaDescriptionCompat mediaDescriptionCompat, boolean z, boolean z2) {
        String mediaId = mediaDescriptionCompat.getMediaId();
        MediaItem.Builder builder = new MediaItem.Builder();
        if (mediaId == null) {
            mediaId = "";
        }
        return builder.setMediaId(mediaId).setRequestMetadata(new MediaItem.RequestMetadata.Builder().setMediaUri(mediaDescriptionCompat.getMediaUri()).build()).setMediaMetadata(convertToMediaMetadata(mediaDescriptionCompat, 0, z, z2)).build();
    }

    public static MediaItem convertToMediaItem(MediaMetadataCompat mediaMetadataCompat, int i) {
        return convertToMediaItem(mediaMetadataCompat.getString("android.media.metadata.MEDIA_ID"), mediaMetadataCompat, i);
    }

    public static MediaItem convertToMediaItem(MediaSessionCompat.QueueItem queueItem) {
        return convertToMediaItem(queueItem.getDescription());
    }

    public static MediaItem convertToMediaItem(String str, MediaMetadataCompat mediaMetadataCompat, int i) {
        MediaItem.Builder builder = new MediaItem.Builder();
        if (str != null) {
            builder.setMediaId(str);
        }
        String string = mediaMetadataCompat.getString("android.media.metadata.MEDIA_URI");
        if (string != null) {
            builder.setRequestMetadata(new MediaItem.RequestMetadata.Builder().setMediaUri(Uri.parse(string)).build());
        }
        builder.setMediaMetadata(convertToMediaMetadata(mediaMetadataCompat, i));
        return builder.build();
    }

    public static MediaMetadata convertToMediaMetadata(MediaDescriptionCompat mediaDescriptionCompat, int i) {
        return convertToMediaMetadata(mediaDescriptionCompat, i, false, true);
    }

    private static MediaMetadata convertToMediaMetadata(MediaDescriptionCompat mediaDescriptionCompat, int i, boolean z, boolean z2) {
        byte[] bArrConvertToByteArray;
        if (mediaDescriptionCompat == null) {
            return MediaMetadata.EMPTY;
        }
        MediaMetadata.Builder builder = new MediaMetadata.Builder();
        builder.setSubtitle(mediaDescriptionCompat.getSubtitle()).setDescription(mediaDescriptionCompat.getDescription()).setArtworkUri(mediaDescriptionCompat.getIconUri()).setUserRating(convertToRating(RatingCompat.newUnratedRating(i)));
        Bitmap iconBitmap = mediaDescriptionCompat.getIconBitmap();
        if (iconBitmap != null) {
            try {
                bArrConvertToByteArray = convertToByteArray(iconBitmap);
            } catch (IOException e) {
                Log.w("LegacyConversions", "Failed to convert iconBitmap to artworkData", e);
                bArrConvertToByteArray = null;
            }
            builder.setArtworkData(bArrConvertToByteArray, 3);
        }
        Bundle extras = mediaDescriptionCompat.getExtras();
        Bundle bundle = extras != null ? new Bundle(extras) : null;
        if (bundle != null && bundle.containsKey("android.media.extra.BT_FOLDER_TYPE")) {
            builder.setFolderType(Integer.valueOf(convertToFolderType(bundle.getLong("android.media.extra.BT_FOLDER_TYPE"))));
            bundle.remove("android.media.extra.BT_FOLDER_TYPE");
        }
        builder.setIsBrowsable(Boolean.valueOf(z));
        if (bundle != null && bundle.containsKey("androidx.media3.session.EXTRAS_KEY_MEDIA_TYPE_COMPAT")) {
            builder.setMediaType(Integer.valueOf((int) bundle.getLong("androidx.media3.session.EXTRAS_KEY_MEDIA_TYPE_COMPAT")));
            bundle.remove("androidx.media3.session.EXTRAS_KEY_MEDIA_TYPE_COMPAT");
        }
        if (bundle != null && bundle.containsKey("androidx.media.utils.extras.CUSTOM_BROWSER_ACTION_ID_LIST")) {
            builder.setSupportedCommands(ImmutableList.copyOf((Collection) Assertions.checkNotNull(bundle.getStringArrayList("androidx.media.utils.extras.CUSTOM_BROWSER_ACTION_ID_LIST"))));
        }
        if (bundle == null || !bundle.containsKey("androidx.media3.mediadescriptioncompat.title")) {
            builder.setTitle(mediaDescriptionCompat.getTitle());
        } else {
            builder.setTitle(bundle.getCharSequence("androidx.media3.mediadescriptioncompat.title"));
            builder.setDisplayTitle(mediaDescriptionCompat.getTitle());
            bundle.remove("androidx.media3.mediadescriptioncompat.title");
        }
        if (bundle != null && !bundle.isEmpty()) {
            builder.setExtras(bundle);
        }
        builder.setIsPlayable(Boolean.valueOf(z2));
        return builder.build();
    }

    public static MediaMetadata convertToMediaMetadata(MediaMetadataCompat mediaMetadataCompat, int i) {
        if (mediaMetadataCompat == null) {
            return MediaMetadata.EMPTY;
        }
        MediaMetadata.Builder builder = new MediaMetadata.Builder();
        CharSequence text = mediaMetadataCompat.getText("android.media.metadata.TITLE");
        CharSequence text2 = mediaMetadataCompat.getText("android.media.metadata.DISPLAY_TITLE");
        MediaMetadata.Builder title = builder.setTitle(text != null ? text : text2);
        if (text == null) {
            text2 = null;
        }
        title.setDisplayTitle(text2).setSubtitle(mediaMetadataCompat.getText("android.media.metadata.DISPLAY_SUBTITLE")).setDescription(mediaMetadataCompat.getText("android.media.metadata.DISPLAY_DESCRIPTION")).setArtist(mediaMetadataCompat.getText("android.media.metadata.ARTIST")).setAlbumTitle(mediaMetadataCompat.getText("android.media.metadata.ALBUM")).setAlbumArtist(mediaMetadataCompat.getText("android.media.metadata.ALBUM_ARTIST")).setOverallRating(convertToRating(mediaMetadataCompat.getRating("android.media.metadata.RATING")));
        if (mediaMetadataCompat.containsKey("android.media.metadata.DURATION")) {
            long j = mediaMetadataCompat.getLong("android.media.metadata.DURATION");
            if (j >= 0) {
                builder.setDurationMs(Long.valueOf(j));
            }
        }
        Rating ratingConvertToRating = convertToRating(mediaMetadataCompat.getRating("android.media.metadata.USER_RATING"));
        if (ratingConvertToRating != null) {
            builder.setUserRating(ratingConvertToRating);
        } else {
            builder.setUserRating(convertToRating(RatingCompat.newUnratedRating(i)));
        }
        if (mediaMetadataCompat.containsKey("android.media.metadata.YEAR")) {
            builder.setRecordingYear(Integer.valueOf((int) mediaMetadataCompat.getLong("android.media.metadata.YEAR")));
        }
        String firstString = getFirstString(mediaMetadataCompat, "android.media.metadata.DISPLAY_ICON_URI", "android.media.metadata.ALBUM_ART_URI", "android.media.metadata.ART_URI");
        if (firstString != null) {
            builder.setArtworkUri(Uri.parse(firstString));
        }
        Bitmap firstBitmap = getFirstBitmap(mediaMetadataCompat, "android.media.metadata.DISPLAY_ICON", "android.media.metadata.ALBUM_ART", "android.media.metadata.ART");
        if (firstBitmap != null) {
            try {
                builder.setArtworkData(convertToByteArray(firstBitmap), 3);
            } catch (IOException e) {
                Log.w("LegacyConversions", "Failed to convert artworkBitmap to artworkData", e);
            }
        }
        boolean zContainsKey = mediaMetadataCompat.containsKey("android.media.metadata.BT_FOLDER_TYPE");
        builder.setIsBrowsable(Boolean.valueOf(zContainsKey));
        if (zContainsKey) {
            builder.setFolderType(Integer.valueOf(convertToFolderType(mediaMetadataCompat.getLong("android.media.metadata.BT_FOLDER_TYPE"))));
        }
        if (mediaMetadataCompat.containsKey("androidx.media3.session.EXTRAS_KEY_MEDIA_TYPE_COMPAT")) {
            builder.setMediaType(Integer.valueOf((int) mediaMetadataCompat.getLong("androidx.media3.session.EXTRAS_KEY_MEDIA_TYPE_COMPAT")));
        }
        builder.setIsPlayable(Boolean.TRUE);
        Bundle bundle = mediaMetadataCompat.getBundle();
        UnmodifiableIterator it = KNOWN_METADATA_COMPAT_KEYS.iterator();
        while (it.hasNext()) {
            bundle.remove((String) it.next());
        }
        if (!bundle.isEmpty()) {
            builder.setExtras(bundle);
        }
        return builder.build();
    }

    public static MediaMetadata convertToMediaMetadata(CharSequence charSequence) {
        return charSequence == null ? MediaMetadata.EMPTY : new MediaMetadata.Builder().setTitle(charSequence).build();
    }

    public static boolean convertToPlayWhenReady(PlaybackStateCompat playbackStateCompat) {
        if (playbackStateCompat == null) {
            return false;
        }
        switch (playbackStateCompat.getState()) {
        }
        return false;
    }

    public static PlaybackException convertToPlaybackException(PlaybackStateCompat playbackStateCompat) {
        if (playbackStateCompat == null || playbackStateCompat.getState() != 7) {
            return null;
        }
        CharSequence errorMessage = playbackStateCompat.getErrorMessage();
        Bundle extras = playbackStateCompat.getExtras();
        String string = errorMessage != null ? errorMessage.toString() : null;
        int iConvertToPlaybackExceptionErrorCode = convertToPlaybackExceptionErrorCode(playbackStateCompat.getErrorCode());
        if (extras == null) {
            extras = Bundle.EMPTY;
        }
        return new PlaybackException(string, null, iConvertToPlaybackExceptionErrorCode, extras);
    }

    private static int convertToPlaybackExceptionErrorCode(int i) {
        int iConvertToSessionErrorCode = convertToSessionErrorCode(i);
        if (iConvertToSessionErrorCode == -5) {
            return 2000;
        }
        if (iConvertToSessionErrorCode != -1) {
            return iConvertToSessionErrorCode;
        }
        return 1000;
    }

    public static PlaybackParameters convertToPlaybackParameters(PlaybackStateCompat playbackStateCompat) {
        return playbackStateCompat == null ? PlaybackParameters.DEFAULT : new PlaybackParameters(playbackStateCompat.getPlaybackSpeed());
    }

    public static int convertToPlaybackState(PlaybackStateCompat playbackStateCompat, MediaMetadataCompat mediaMetadataCompat, long j) {
        if (playbackStateCompat == null) {
            return 1;
        }
        switch (playbackStateCompat.getState()) {
            case 0:
            case 1:
            case 7:
            case 8:
                return 1;
            case 2:
                long jConvertToDurationMs = convertToDurationMs(mediaMetadataCompat);
                return (jConvertToDurationMs != -9223372036854775807L && convertToCurrentPositionMs(playbackStateCompat, mediaMetadataCompat, j) >= jConvertToDurationMs) ? 4 : 3;
            case 3:
                return 3;
            case 4:
            case 5:
            case 6:
            case 9:
            case 10:
            case 11:
                return 2;
            default:
                throw new ConversionException("Invalid state of PlaybackStateCompat: " + playbackStateCompat.getState());
        }
    }

    public static Player.Commands convertToPlayerCommands(PlaybackStateCompat playbackStateCompat, int i, long j, boolean z) {
        Player.Commands.Builder builder = new Player.Commands.Builder();
        long actions = playbackStateCompat == null ? 0L : playbackStateCompat.getActions();
        if ((hasAction(actions, 4L) && hasAction(actions, 2L)) || hasAction(actions, 512L)) {
            builder.add(1);
        }
        if (hasAction(actions, 16384L)) {
            builder.add(2);
        }
        if ((hasAction(actions, 32768L) && hasAction(actions, 1024L)) || ((hasAction(actions, 65536L) && hasAction(actions, 2048L)) || (hasAction(actions, 131072L) && hasAction(actions, 8192L)))) {
            builder.addAll(31, 2);
        }
        if (hasAction(actions, 8L)) {
            builder.add(11);
        }
        if (hasAction(actions, 64L)) {
            builder.add(12);
        }
        if (hasAction(actions, 256L)) {
            builder.addAll(5, 4);
        }
        if (hasAction(actions, 32L)) {
            builder.addAll(9, 8);
        }
        if (hasAction(actions, 16L)) {
            builder.addAll(7, 6);
        }
        if (hasAction(actions, 4194304L)) {
            builder.add(13);
        }
        if (hasAction(actions, 1L)) {
            builder.add(3);
        }
        if (i == 1) {
            builder.addAll(26, 34);
        } else if (i == 2) {
            builder.addAll(26, 34, 25, 33);
        }
        builder.addAll(23, 17, 18, 16, 21, 32);
        if ((j & 4) != 0) {
            builder.add(20);
            if (hasAction(actions, 4096L)) {
                builder.add(10);
            }
        }
        if (z) {
            if (hasAction(actions, 262144L)) {
                builder.add(15);
            }
            if (hasAction(actions, 2097152L)) {
                builder.add(14);
            }
        }
        return builder.build();
    }

    public static Rating convertToRating(RatingCompat ratingCompat) {
        if (ratingCompat == null) {
            return null;
        }
        switch (ratingCompat.getRatingStyle()) {
            case 1:
                if (!ratingCompat.isRated()) {
                }
                break;
            case 2:
                if (!ratingCompat.isRated()) {
                }
                break;
            case 3:
                if (!ratingCompat.isRated()) {
                }
                break;
            case 4:
                if (!ratingCompat.isRated()) {
                }
                break;
            case 5:
                if (!ratingCompat.isRated()) {
                }
                break;
            case 6:
                if (!ratingCompat.isRated()) {
                }
                break;
        }
        return null;
    }

    public static int convertToRepeatMode(int i) {
        if (i == -1 || i == 0) {
            return 0;
        }
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2 && i != 3) {
                Log.w("LegacyConversions", "Unrecognized PlaybackStateCompat.RepeatMode: " + i + " was converted to `Player.REPEAT_MODE_OFF`");
                return 0;
            }
        }
        return i2;
    }

    public static SessionCommands convertToSessionCommands(PlaybackStateCompat playbackStateCompat, boolean z) {
        List<PlaybackStateCompat.CustomAction> customActions;
        SessionCommands.Builder builder = new SessionCommands.Builder();
        builder.addAllSessionCommands();
        if (!z) {
            builder.remove(40010);
        }
        if (playbackStateCompat != null && (customActions = playbackStateCompat.getCustomActions()) != null) {
            for (PlaybackStateCompat.CustomAction customAction : customActions) {
                String action = customAction.getAction();
                Bundle extras = customAction.getExtras();
                if (extras == null) {
                    extras = Bundle.EMPTY;
                }
                builder.add(new SessionCommand(action, extras));
            }
        }
        return builder.build();
    }

    static SessionError convertToSessionError(int i, int i2, CharSequence charSequence, Bundle bundle, Context context) {
        if (i == 7 || i2 == 0) {
            return null;
        }
        int iConvertToSessionErrorCode = convertToSessionErrorCode(i2);
        String string = charSequence != null ? charSequence.toString() : getSessionErrorMessage(iConvertToSessionErrorCode, context);
        if (bundle == null) {
            bundle = Bundle.EMPTY;
        }
        return new SessionError(iConvertToSessionErrorCode, string, bundle);
    }

    public static SessionError convertToSessionError(PlaybackStateCompat playbackStateCompat, Context context) {
        if (playbackStateCompat == null) {
            return null;
        }
        return convertToSessionError(playbackStateCompat.getState(), playbackStateCompat.getErrorCode(), playbackStateCompat.getErrorMessage(), playbackStateCompat.getExtras(), context);
    }

    private static int convertToSessionErrorCode(int i) {
        switch (i) {
            case 1:
                return -2;
            case 2:
                return -6;
            case 3:
                return -102;
            case 4:
                return -103;
            case 5:
                return -104;
            case 6:
                return -105;
            case 7:
                return -106;
            case 8:
                return -110;
            case 9:
                return -107;
            case 10:
                return 1;
            case 11:
                return -109;
            default:
                return -1;
        }
    }

    public static boolean convertToShuffleModeEnabled(int i) {
        if (i == -1 || i == 0) {
            return false;
        }
        if (i == 1 || i == 2) {
            return true;
        }
        throw new IllegalArgumentException("Unrecognized ShuffleMode: " + i);
    }

    public static long convertToTotalBufferedDurationMs(PlaybackStateCompat playbackStateCompat, MediaMetadataCompat mediaMetadataCompat, long j) {
        return convertToBufferedPositionMs(playbackStateCompat, mediaMetadataCompat, j) - convertToCurrentPositionMs(playbackStateCompat, mediaMetadataCompat, j);
    }

    private static long getCurrentPosition(PlaybackStateCompat playbackStateCompat, long j) {
        return playbackStateCompat.getCurrentPosition(j == -9223372036854775807L ? null : Long.valueOf(j));
    }

    private static Bitmap getFirstBitmap(MediaMetadataCompat mediaMetadataCompat, String... strArr) {
        for (String str : strArr) {
            if (mediaMetadataCompat.containsKey(str)) {
                return mediaMetadataCompat.getBitmap(str);
            }
        }
        return null;
    }

    private static String getFirstString(MediaMetadataCompat mediaMetadataCompat, String... strArr) {
        for (String str : strArr) {
            if (mediaMetadataCompat.containsKey(str)) {
                return mediaMetadataCompat.getString(str);
            }
        }
        return null;
    }

    public static Object getFutureResult(Future future, long j) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        boolean z = false;
        long j2 = j;
        while (true) {
            try {
                try {
                    return future.get(j2, TimeUnit.MILLISECONDS);
                } catch (InterruptedException unused) {
                    z = true;
                    long jElapsedRealtime2 = SystemClock.elapsedRealtime() - jElapsedRealtime;
                    if (jElapsedRealtime2 >= j) {
                        throw new TimeoutException();
                    }
                    j2 = j - jElapsedRealtime2;
                }
            } finally {
                if (z) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private static String getSessionErrorMessage(int i, Context context) {
        if (i == -100) {
            return context.getString(R$string.error_message_disconnected);
        }
        if (i == 1) {
            return context.getString(R$string.error_message_info_cancelled);
        }
        if (i == -6) {
            return context.getString(R$string.error_message_not_supported);
        }
        if (i == -5) {
            return context.getString(R$string.error_message_io);
        }
        if (i == -4) {
            return context.getString(R$string.error_message_permission_denied);
        }
        if (i == -3) {
            return context.getString(R$string.error_message_bad_value);
        }
        if (i == -2) {
            return context.getString(R$string.error_message_invalid_state);
        }
        switch (i) {
            case -110:
                return context.getString(R$string.error_message_content_already_playing);
            case -109:
                return context.getString(R$string.error_message_end_of_playlist);
            case -108:
                return context.getString(R$string.error_message_setup_required);
            case -107:
                return context.getString(R$string.error_message_skip_limit_reached);
            case -106:
                return context.getString(R$string.error_message_not_available_in_region);
            case -105:
                return context.getString(R$string.error_message_parental_control_restricted);
            case -104:
                return context.getString(R$string.error_message_concurrent_stream_limit);
            case -103:
                return context.getString(R$string.error_message_premium_account_required);
            case -102:
                return context.getString(R$string.error_message_authentication_expired);
            default:
                return context.getString(R$string.error_message_fallback);
        }
    }

    private static boolean hasAction(long j, long j2) {
        return (j & j2) != 0;
    }
}
