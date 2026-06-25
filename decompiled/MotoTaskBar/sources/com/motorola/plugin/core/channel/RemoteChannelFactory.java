package com.motorola.plugin.core.channel;

import com.motorola.plugin.core.channel.local.LocalChannelImpl;
import com.motorola.plugin.core.channel.remote.RemoteChannelImpl;
import com.motorola.plugin.core.channel.remote.SecurityRemoteChannelImpl;
import com.motorola.plugin.core.discovery.PluginType;
import com.motorola.plugin.core.utils.TraceTimeInvocationHandler;
import java.lang.reflect.Proxy;
import kotlin.NoWhenBranchMatchedException;

/* JADX INFO: compiled from: RemoteChannelFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface RemoteChannelFactory {
    public static final Factory Factory = Factory.$$INSTANCE;

    /* JADX INFO: compiled from: RemoteChannelFactory.kt */
    public final class Factory {
        static final /* synthetic */ Factory $$INSTANCE = new Factory();

        /* JADX INFO: compiled from: RemoteChannelFactory.kt */
        public final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[PluginType.values().length];
                iArr[PluginType.LOCAL.ordinal()] = 1;
                iArr[PluginType.INSTALL.ordinal()] = 2;
                iArr[PluginType.REMOTE.ordinal()] = 3;
                $EnumSwitchMapping$0 = iArr;
            }
        }

        private Factory() {
        }

        private final IRemoteChannelExtension createLocalChannel(ChannelParams channelParams) {
            return new LocalChannelImpl(channelParams);
        }

        private final IRemoteChannelExtension createSecurityRemoteChannel(ChannelParams channelParams) {
            Object objCast = IRemoteChannelExtension.class.cast(Proxy.newProxyInstance(RemoteChannelFactory.class.getClassLoader(), new Class[]{IRemoteChannelExtension.class}, new TraceTimeInvocationHandler(new SecurityRemoteChannelImpl(channelParams), IRemoteChannelExtension.class.getSimpleName())));
            objCast.getClass();
            return (IRemoteChannelExtension) objCast;
        }

        private final IRemoteChannelExtension createUnsafeRemoteChannel(ChannelParams channelParams) {
            return new RemoteChannelImpl(channelParams);
        }

        public final IRemoteChannelExtension createRemoteChannel(ChannelParams channelParams) {
            channelParams.getClass();
            int i = WhenMappings.$EnumSwitchMapping$0[channelParams.getPluginType().ordinal()];
            if (i == 1) {
                return createLocalChannel(channelParams);
            }
            if (i == 2) {
                return createSecurityRemoteChannel(channelParams);
            }
            if (i == 3) {
                return createUnsafeRemoteChannel(channelParams);
            }
            throw new NoWhenBranchMatchedException();
        }
    }
}
