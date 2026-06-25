package androidx.mediarouter.media;

import android.media.MediaRouter;
import android.util.Log;
import android.view.Display;
import androidx.mediarouter.media.MediaRouterJellybean;

/* JADX INFO: loaded from: classes.dex */
abstract class MediaRouterJellybeanMr1 {

    public interface Callback extends MediaRouterJellybean.Callback {
        void onRoutePresentationDisplayChanged(Object obj);
    }

    class CallbackProxy extends MediaRouterJellybean.CallbackProxy {
        CallbackProxy(Callback callback) {
            super(callback);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRoutePresentationDisplayChanged(android.media.MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            ((Callback) this.mCallback).onRoutePresentationDisplayChanged(routeInfo);
        }
    }

    public abstract class RouteInfo {
        public static Display getPresentationDisplay(Object obj) {
            try {
                return ((MediaRouter.RouteInfo) obj).getPresentationDisplay();
            } catch (NoSuchMethodError e) {
                Log.w("MediaRouterJellybeanMr1", "Cannot get presentation display for the route.", e);
                return null;
            }
        }

        public static boolean isEnabled(Object obj) {
            return ((MediaRouter.RouteInfo) obj).isEnabled();
        }
    }

    public static Object createCallback(Callback callback) {
        return new CallbackProxy(callback);
    }
}
