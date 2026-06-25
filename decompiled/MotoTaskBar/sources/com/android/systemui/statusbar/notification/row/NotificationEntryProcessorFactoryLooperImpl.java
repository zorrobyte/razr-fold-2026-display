package com.android.systemui.statusbar.notification.row;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: NotificationEntryProcessorFactoryLooperImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationEntryProcessorFactoryLooperImpl implements NotificationEntryProcessorFactory {
    private final Looper mMainLooper;

    /* JADX INFO: compiled from: NotificationEntryProcessorFactoryLooperImpl.kt */
    final class HandlerProcessor extends Handler implements Processor {
        public static final Companion Companion = new Companion(null);
        private final Consumer consumer;

        /* JADX INFO: compiled from: NotificationEntryProcessorFactoryLooperImpl.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public HandlerProcessor(Looper looper, Consumer consumer) {
            super(looper);
            looper.getClass();
            consumer.getClass();
            this.consumer = consumer;
        }

        @Override // com.android.systemui.statusbar.notification.row.Processor
        public void cancel(NotificationEntry notificationEntry) {
            notificationEntry.getClass();
            removeMessages(1, notificationEntry);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            message.getClass();
            if (message.what == 1) {
                Object obj = message.obj;
                obj.getClass();
                this.consumer.accept((NotificationEntry) obj);
                return;
            }
            throw new IllegalArgumentException("Unknown message type: " + message.what);
        }

        @Override // com.android.systemui.statusbar.notification.row.Processor
        public void request(NotificationEntry notificationEntry) {
            notificationEntry.getClass();
            if (hasMessages(1, notificationEntry)) {
                return;
            }
            sendMessage(Message.obtain(this, 1, notificationEntry));
        }
    }

    public NotificationEntryProcessorFactoryLooperImpl(Looper looper) {
        looper.getClass();
        this.mMainLooper = looper;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationEntryProcessorFactory
    public Processor create(Consumer consumer) {
        consumer.getClass();
        return new HandlerProcessor(this.mMainLooper, consumer);
    }
}
