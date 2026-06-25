package com.android.settingslib.media;

import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.util.Log;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class ManagerInfoMediaManager extends InfoMediaManager {
    private static final boolean DEBUG = Log.isLoggable("ManagerInfoMediaManager", 3);

    final class RouterManagerCallback implements MediaRouter2Manager.Callback {
        public void onPreferredFeaturesChanged(String str, List list) {
            throw null;
        }

        public void onRequestFailed(int i) {
            throw null;
        }

        public void onRouteListingPreferenceUpdated(String str, RouteListingPreference routeListingPreference) {
            throw null;
        }

        public void onRoutesUpdated() {
            throw null;
        }

        public void onSessionReleased(RoutingSessionInfo routingSessionInfo) {
            throw null;
        }

        public void onSessionUpdated(RoutingSessionInfo routingSessionInfo) {
            throw null;
        }

        public void onTransferFailed(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
        }

        public void onTransferred(RoutingSessionInfo routingSessionInfo, RoutingSessionInfo routingSessionInfo2) {
            if (ManagerInfoMediaManager.DEBUG) {
                Log.d("ManagerInfoMediaManager", "onTransferred() oldSession : " + ((Object) routingSessionInfo.getName()) + ", newSession : " + ((Object) routingSessionInfo2.getName()));
            }
            throw null;
        }
    }
}
