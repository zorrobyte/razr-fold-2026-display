package androidx.mediarouter.media;

import android.content.IntentFilter;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class MediaRouteDescriptor {
    final Bundle mBundle;
    List mControlFilters;
    List mGroupMemberIds;

    public final class Builder {
        private final Bundle mBundle;
        private ArrayList mControlFilters;
        private ArrayList mGroupMemberIds;

        public Builder(MediaRouteDescriptor mediaRouteDescriptor) {
            if (mediaRouteDescriptor == null) {
                throw new IllegalArgumentException("descriptor must not be null");
            }
            this.mBundle = new Bundle(mediaRouteDescriptor.mBundle);
            if (!mediaRouteDescriptor.getGroupMemberIds().isEmpty()) {
                this.mGroupMemberIds = new ArrayList(mediaRouteDescriptor.getGroupMemberIds());
            }
            if (mediaRouteDescriptor.getControlFilters().isEmpty()) {
                return;
            }
            this.mControlFilters = new ArrayList(mediaRouteDescriptor.mControlFilters);
        }

        public Builder(String str, String str2) {
            this.mBundle = new Bundle();
            setId(str);
            setName(str2);
        }

        public Builder addControlFilter(IntentFilter intentFilter) {
            if (intentFilter == null) {
                throw new IllegalArgumentException("filter must not be null");
            }
            if (this.mControlFilters == null) {
                this.mControlFilters = new ArrayList();
            }
            if (!this.mControlFilters.contains(intentFilter)) {
                this.mControlFilters.add(intentFilter);
            }
            return this;
        }

        public Builder addControlFilters(Collection collection) {
            if (collection == null) {
                throw new IllegalArgumentException("filters must not be null");
            }
            if (!collection.isEmpty()) {
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    IntentFilter intentFilter = (IntentFilter) it.next();
                    if (intentFilter != null) {
                        addControlFilter(intentFilter);
                    }
                }
            }
            return this;
        }

        public Builder addGroupMemberId(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("groupMemberId must not be empty");
            }
            if (this.mGroupMemberIds == null) {
                this.mGroupMemberIds = new ArrayList();
            }
            if (!this.mGroupMemberIds.contains(str)) {
                this.mGroupMemberIds.add(str);
            }
            return this;
        }

        public Builder addGroupMemberIds(Collection collection) {
            if (collection == null) {
                throw new IllegalArgumentException("groupMemberIds must not be null");
            }
            if (!collection.isEmpty()) {
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    addGroupMemberId((String) it.next());
                }
            }
            return this;
        }

        public MediaRouteDescriptor build() {
            ArrayList<? extends Parcelable> arrayList = this.mControlFilters;
            if (arrayList != null) {
                this.mBundle.putParcelableArrayList("controlFilters", arrayList);
            }
            ArrayList<String> arrayList2 = this.mGroupMemberIds;
            if (arrayList2 != null) {
                this.mBundle.putStringArrayList("groupMemberIds", arrayList2);
            }
            return new MediaRouteDescriptor(this.mBundle);
        }

        public Builder clearControlFilters() {
            ArrayList arrayList = this.mControlFilters;
            if (arrayList != null) {
                arrayList.clear();
            }
            return this;
        }

        public Builder clearGroupMemberIds() {
            ArrayList arrayList = this.mGroupMemberIds;
            if (arrayList != null) {
                arrayList.clear();
            }
            return this;
        }

        public Builder setCanDisconnect(boolean z) {
            this.mBundle.putBoolean("canDisconnect", z);
            return this;
        }

        public Builder setConnectionState(int i) {
            this.mBundle.putInt("connectionState", i);
            return this;
        }

        public Builder setDescription(String str) {
            this.mBundle.putString("status", str);
            return this;
        }

        public Builder setDeviceType(int i) {
            this.mBundle.putInt("deviceType", i);
            return this;
        }

        public Builder setEnabled(boolean z) {
            this.mBundle.putBoolean("enabled", z);
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            if (bundle == null) {
                this.mBundle.putBundle("extras", null);
                return this;
            }
            this.mBundle.putBundle("extras", new Bundle(bundle));
            return this;
        }

        public Builder setIconUri(Uri uri) {
            if (uri == null) {
                throw new IllegalArgumentException("iconUri must not be null");
            }
            this.mBundle.putString("iconUri", uri.toString());
            return this;
        }

        public Builder setId(String str) {
            if (str == null) {
                throw new NullPointerException("id must not be null");
            }
            this.mBundle.putString("id", str);
            return this;
        }

        public Builder setName(String str) {
            if (str == null) {
                throw new NullPointerException("name must not be null");
            }
            this.mBundle.putString("name", str);
            return this;
        }

        public Builder setPlaybackStream(int i) {
            this.mBundle.putInt("playbackStream", i);
            return this;
        }

        public Builder setPlaybackType(int i) {
            this.mBundle.putInt("playbackType", i);
            return this;
        }

        public Builder setPresentationDisplayId(int i) {
            this.mBundle.putInt("presentationDisplayId", i);
            return this;
        }

        public Builder setVolume(int i) {
            this.mBundle.putInt("volume", i);
            return this;
        }

        public Builder setVolumeHandling(int i) {
            this.mBundle.putInt("volumeHandling", i);
            return this;
        }

        public Builder setVolumeMax(int i) {
            this.mBundle.putInt("volumeMax", i);
            return this;
        }
    }

    MediaRouteDescriptor(Bundle bundle) {
        this.mBundle = bundle;
    }

    public static MediaRouteDescriptor fromBundle(Bundle bundle) {
        if (bundle != null) {
            return new MediaRouteDescriptor(bundle);
        }
        return null;
    }

    public boolean canDisconnectAndKeepPlaying() {
        return this.mBundle.getBoolean("canDisconnect", false);
    }

    void ensureControlFilters() {
        if (this.mControlFilters == null) {
            ArrayList parcelableArrayList = this.mBundle.getParcelableArrayList("controlFilters");
            this.mControlFilters = parcelableArrayList;
            if (parcelableArrayList == null) {
                this.mControlFilters = Collections.EMPTY_LIST;
            }
        }
    }

    void ensureGroupMemberIds() {
        if (this.mGroupMemberIds == null) {
            ArrayList<String> stringArrayList = this.mBundle.getStringArrayList("groupMemberIds");
            this.mGroupMemberIds = stringArrayList;
            if (stringArrayList == null) {
                this.mGroupMemberIds = Collections.EMPTY_LIST;
            }
        }
    }

    public int getConnectionState() {
        return this.mBundle.getInt("connectionState", 0);
    }

    public List getControlFilters() {
        ensureControlFilters();
        return this.mControlFilters;
    }

    public String getDescription() {
        return this.mBundle.getString("status");
    }

    public int getDeviceType() {
        return this.mBundle.getInt("deviceType");
    }

    public Bundle getExtras() {
        return this.mBundle.getBundle("extras");
    }

    public List getGroupMemberIds() {
        ensureGroupMemberIds();
        return this.mGroupMemberIds;
    }

    public Uri getIconUri() {
        String string = this.mBundle.getString("iconUri");
        if (string == null) {
            return null;
        }
        return Uri.parse(string);
    }

    public String getId() {
        return this.mBundle.getString("id");
    }

    public int getMaxClientVersion() {
        return this.mBundle.getInt("maxClientVersion", Integer.MAX_VALUE);
    }

    public int getMinClientVersion() {
        return this.mBundle.getInt("minClientVersion", 1);
    }

    public String getName() {
        return this.mBundle.getString("name");
    }

    public int getPlaybackStream() {
        return this.mBundle.getInt("playbackStream", -1);
    }

    public int getPlaybackType() {
        return this.mBundle.getInt("playbackType", 1);
    }

    public int getPresentationDisplayId() {
        return this.mBundle.getInt("presentationDisplayId", -1);
    }

    public IntentSender getSettingsActivity() {
        return (IntentSender) this.mBundle.getParcelable("settingsIntent");
    }

    public int getVolume() {
        return this.mBundle.getInt("volume");
    }

    public int getVolumeHandling() {
        return this.mBundle.getInt("volumeHandling", 0);
    }

    public int getVolumeMax() {
        return this.mBundle.getInt("volumeMax");
    }

    public boolean isEnabled() {
        return this.mBundle.getBoolean("enabled", true);
    }

    public boolean isValid() {
        ensureControlFilters();
        return (TextUtils.isEmpty(getId()) || TextUtils.isEmpty(getName()) || this.mControlFilters.contains(null)) ? false : true;
    }

    public String toString() {
        return "MediaRouteDescriptor{ id=" + getId() + ", groupMemberIds=" + getGroupMemberIds() + ", name=" + getName() + ", description=" + getDescription() + ", iconUri=" + getIconUri() + ", isEnabled=" + isEnabled() + ", connectionState=" + getConnectionState() + ", controlFilters=" + Arrays.toString(getControlFilters().toArray()) + ", playbackType=" + getPlaybackType() + ", playbackStream=" + getPlaybackStream() + ", deviceType=" + getDeviceType() + ", volume=" + getVolume() + ", volumeMax=" + getVolumeMax() + ", volumeHandling=" + getVolumeHandling() + ", presentationDisplayId=" + getPresentationDisplayId() + ", extras=" + getExtras() + ", isValid=" + isValid() + ", minClientVersion=" + getMinClientVersion() + ", maxClientVersion=" + getMaxClientVersion() + " }";
    }
}
