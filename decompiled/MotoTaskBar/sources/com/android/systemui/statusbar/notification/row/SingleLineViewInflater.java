package com.android.systemui.statusbar.notification.row;

import android.R;
import android.app.Notification;
import android.app.Person;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.widget.MessagingMessage;
import com.android.internal.widget.PeopleHelper;
import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.ConversationAvatar;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.ConversationData;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.SingleLineViewModel;
import com.motorola.taskbar.R$layout;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SingleLineViewInflater.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SingleLineViewInflater {
    public static final SingleLineViewInflater INSTANCE = new SingleLineViewInflater();
    private static final PeopleHelper peopleHelper = new PeopleHelper();

    /* JADX INFO: compiled from: SingleLineViewInflater.kt */
    public final class ConversationTextData {
        private final CharSequence conversationText;
        private final CharSequence conversationTitle;
        private final CharSequence senderName;

        public ConversationTextData(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
            charSequence.getClass();
            this.conversationTitle = charSequence;
            this.conversationText = charSequence2;
            this.senderName = charSequence3;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConversationTextData)) {
                return false;
            }
            ConversationTextData conversationTextData = (ConversationTextData) obj;
            return Intrinsics.areEqual(this.conversationTitle, conversationTextData.conversationTitle) && Intrinsics.areEqual(this.conversationText, conversationTextData.conversationText) && Intrinsics.areEqual(this.senderName, conversationTextData.senderName);
        }

        public final CharSequence getConversationText() {
            return this.conversationText;
        }

        public final CharSequence getConversationTitle() {
            return this.conversationTitle;
        }

        public final CharSequence getSenderName() {
            return this.senderName;
        }

        public int hashCode() {
            int iHashCode = this.conversationTitle.hashCode() * 31;
            CharSequence charSequence = this.conversationText;
            int iHashCode2 = (iHashCode + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
            CharSequence charSequence2 = this.senderName;
            return iHashCode2 + (charSequence2 != null ? charSequence2.hashCode() : 0);
        }

        public String toString() {
            return "ConversationTextData(conversationTitle=" + ((Object) this.conversationTitle) + ", conversationText=" + ((Object) this.conversationText) + ", senderName=" + ((Object) this.senderName) + ")";
        }
    }

    private SingleLineViewInflater() {
    }

    private final CharSequence findBackUpConversationText(Notification.MessagingStyle.Message message, Context context) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        AsyncHybridViewInflation asyncHybridViewInflation = AsyncHybridViewInflation.INSTANCE;
        boolean zNotificationAsyncHybridViewInflation = Flags.notificationAsyncHybridViewInflation();
        if (!zNotificationAsyncHybridViewInflation) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "AsyncHybridViewInflation") + " to be enabled.");
        }
        if (zNotificationAsyncHybridViewInflation && isImageMessage(message)) {
            return context.getResources().getString(R.string.date_picker_mode);
        }
        return null;
    }

    private final CharSequence findBackUpConversationTitle(Notification.MessagingStyle messagingStyle, CharSequence charSequence, Context context) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        AsyncHybridViewInflation asyncHybridViewInflation = AsyncHybridViewInflation.INSTANCE;
        boolean zNotificationAsyncHybridViewInflation = Flags.notificationAsyncHybridViewInflation();
        if (!zNotificationAsyncHybridViewInflation) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "AsyncHybridViewInflation") + " to be enabled.");
        }
        if (!zNotificationAsyncHybridViewInflation) {
            return "";
        }
        if (messagingStyle.isGroupConversation()) {
            String string = context.getResources().getString(R.string.date_picker_next_month_button);
            string.getClass();
            return string;
        }
        if (charSequence != null) {
            return charSequence;
        }
        String string2 = context.getResources().getString(R.string.date_picker_prev_month_button);
        string2.getClass();
        return string2;
    }

    private final Icon getDefaultAvatar(Notification.Builder builder, CharSequence charSequence, PeopleHelper.NameToPrefixMap nameToPrefixMap) {
        String prefix;
        int smallIconColor = builder.getSmallIconColor(false);
        String str = "";
        if (charSequence == null || charSequence.length() == 0) {
            Icon iconCreateAvatarSymbol = peopleHelper.createAvatarSymbol("", "", smallIconColor);
            iconCreateAvatarSymbol.getClass();
            return iconCreateAvatarSymbol;
        }
        if (nameToPrefixMap != null && (prefix = nameToPrefixMap.getPrefix(charSequence)) != null) {
            str = prefix;
        }
        Icon iconCreateAvatarSymbol2 = peopleHelper.createAvatarSymbol(charSequence, str, smallIconColor);
        iconCreateAvatarSymbol2.getClass();
        return iconCreateAvatarSymbol2;
    }

    static /* synthetic */ Icon getDefaultAvatar$default(SingleLineViewInflater singleLineViewInflater, Notification.Builder builder, CharSequence charSequence, PeopleHelper.NameToPrefixMap nameToPrefixMap, int i, Object obj) {
        if ((i & 2) != 0) {
            nameToPrefixMap = null;
        }
        return singleLineViewInflater.getDefaultAvatar(builder, charSequence, nameToPrefixMap);
    }

    private final CharSequence getKeyOrName(Person person) {
        return person.getKey() == null ? person.getName() : person.getKey();
    }

    private final List groupMessages(List list, List list2) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        AsyncHybridViewInflation asyncHybridViewInflation = AsyncHybridViewInflation.INSTANCE;
        boolean zNotificationAsyncHybridViewInflation = Flags.notificationAsyncHybridViewInflation();
        if (!zNotificationAsyncHybridViewInflation) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "AsyncHybridViewInflation") + " to be enabled.");
        }
        if (!zNotificationAsyncHybridViewInflation) {
            return CollectionsKt.emptyList();
        }
        if (list.isEmpty() && list2.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        int size = list2.size();
        int size2 = list.size() + size;
        int i = 0;
        ArrayList arrayList2 = null;
        CharSequence charSequence = null;
        while (i < size2) {
            Notification.MessagingStyle.Message message = (Notification.MessagingStyle.Message) (i < size ? list2.get(i) : list.get(i - size));
            Person senderPerson = message.getSenderPerson();
            CharSequence keyOrName = senderPerson != null ? getKeyOrName(senderPerson) : null;
            if (arrayList2 == null || !Intrinsics.areEqual(keyOrName, charSequence)) {
                arrayList2 = new ArrayList();
                arrayList.add(arrayList2);
                charSequence = keyOrName;
            }
            if (arrayList2 != null) {
                arrayList2.add(message);
            }
            i++;
        }
        return arrayList;
    }

    public static final HybridNotificationView inflateSingleLineViewHolder(boolean z, int i, NotificationEntry notificationEntry, Context context, NotificationContentInflaterLogger notificationContentInflaterLogger) {
        notificationEntry.getClass();
        context.getClass();
        notificationContentInflaterLogger.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        AsyncHybridViewInflation asyncHybridViewInflation = AsyncHybridViewInflation.INSTANCE;
        boolean zNotificationAsyncHybridViewInflation = Flags.notificationAsyncHybridViewInflation();
        if (!zNotificationAsyncHybridViewInflation) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "AsyncHybridViewInflation") + " to be enabled.");
        }
        if (!zNotificationAsyncHybridViewInflation || (i & 16) == 0) {
            return null;
        }
        notificationContentInflaterLogger.logInflateSingleLine(notificationEntry, i, z);
        notificationContentInflaterLogger.logAsyncTaskProgress(notificationEntry, "inflating single-line content view");
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("NotificationContentInflater#inflateSingleLineView");
        }
        try {
            View viewInflate = LayoutInflater.from(context).inflate(z ? R$layout.hybrid_conversation_notification : R$layout.hybrid_notification, (ViewGroup) null);
            viewInflate.getClass();
            HybridNotificationView hybridNotificationView = (HybridNotificationView) viewInflate;
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            return hybridNotificationView;
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public static final SingleLineViewModel inflateSingleLineViewModel(Notification notification, Notification.MessagingStyle messagingStyle, Notification.Builder builder, Context context) {
        CharSequence conversationText;
        CharSequence conversationTitle;
        notification.getClass();
        builder.getClass();
        context.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        AsyncHybridViewInflation asyncHybridViewInflation = AsyncHybridViewInflation.INSTANCE;
        boolean zNotificationAsyncHybridViewInflation = Flags.notificationAsyncHybridViewInflation();
        if (!zNotificationAsyncHybridViewInflation) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "AsyncHybridViewInflation") + " to be enabled.");
        }
        CharSequence senderName = null;
        if (!zNotificationAsyncHybridViewInflation) {
            return new SingleLineViewModel(null, null, null);
        }
        peopleHelper.init(context);
        CharSequence charSequenceResolveTitle = HybridGroupManager.resolveTitle(notification);
        CharSequence charSequenceResolveText = HybridGroupManager.resolveText(notification);
        if (messagingStyle == null) {
            return new SingleLineViewModel(charSequenceResolveTitle, charSequenceResolveText, null);
        }
        boolean zIsGroupConversation = messagingStyle.isGroupConversation();
        SingleLineViewInflater singleLineViewInflater = INSTANCE;
        ConversationTextData conversationTextDataLoadConversationTextData = singleLineViewInflater.loadConversationTextData(messagingStyle, context);
        if (conversationTextDataLoadConversationTextData != null && (conversationTitle = conversationTextDataLoadConversationTextData.getConversationTitle()) != null && conversationTitle.length() > 0) {
            charSequenceResolveTitle = conversationTextDataLoadConversationTextData.getConversationTitle();
        }
        if (conversationTextDataLoadConversationTextData != null && (conversationText = conversationTextDataLoadConversationTextData.getConversationText()) != null && conversationText.length() > 0) {
            charSequenceResolveText = conversationTextDataLoadConversationTextData.getConversationText();
        }
        ConversationAvatar conversationAvatarLoadConversationAvatar = singleLineViewInflater.loadConversationAvatar(messagingStyle, builder, notification, zIsGroupConversation, context);
        if (zIsGroupConversation && conversationTextDataLoadConversationTextData != null) {
            senderName = conversationTextDataLoadConversationTextData.getSenderName();
        }
        return new SingleLineViewModel(charSequenceResolveTitle, charSequenceResolveText, new ConversationData(senderName, conversationAvatarLoadConversationAvatar));
    }

    private final boolean isImageMessage(Notification.MessagingStyle.Message message) {
        return MessagingMessage.hasImage(message);
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00c3 A[PHI: r2
      0x00c3: PHI (r2v22 android.graphics.drawable.Icon) = (r2v21 android.graphics.drawable.Icon), (r2v21 android.graphics.drawable.Icon), (r2v24 android.graphics.drawable.Icon) binds: [B:80:0x00c3, B:32:0x00a7, B:36:0x00b1] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final com.android.systemui.statusbar.notification.row.ui.viewmodel.ConversationAvatar loadConversationAvatar(android.app.Notification.MessagingStyle r17, android.app.Notification.Builder r18, android.app.Notification r19, boolean r20, android.content.Context r21) {
        /*
            Method dump skipped, instruction units count: 366
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.SingleLineViewInflater.loadConversationAvatar(android.app.Notification$MessagingStyle, android.app.Notification$Builder, android.app.Notification, boolean, android.content.Context):com.android.systemui.statusbar.notification.row.ui.viewmodel.ConversationAvatar");
    }

    private final ConversationTextData loadConversationTextData(Notification.MessagingStyle messagingStyle, Context context) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        AsyncHybridViewInflation asyncHybridViewInflation = AsyncHybridViewInflation.INSTANCE;
        boolean zNotificationAsyncHybridViewInflation = Flags.notificationAsyncHybridViewInflation();
        if (!zNotificationAsyncHybridViewInflation) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "AsyncHybridViewInflation") + " to be enabled.");
        }
        if (!zNotificationAsyncHybridViewInflation || messagingStyle.getMessages().isEmpty()) {
            return null;
        }
        List<Notification.MessagingStyle.Message> messages = messagingStyle.getMessages();
        List<Notification.MessagingStyle.Message> messages2 = messagingStyle.getMessages();
        messages2.getClass();
        Notification.MessagingStyle.Message message = messages.get(CollectionsKt.getLastIndex(messages2));
        CharSequence text = message.getText();
        if (text == null && isImageMessage(message)) {
            text = findBackUpConversationText(message, context);
        }
        Person senderPerson = message.getSenderPerson();
        CharSequence name = senderPerson != null ? senderPerson.getName() : null;
        String string = context.getResources().getString(R.string.date_picker_month_typeface, name != null ? name.toString() : null);
        string.getClass();
        CharSequence conversationTitle = messagingStyle.getConversationTitle();
        if (conversationTitle == null) {
            conversationTitle = findBackUpConversationTitle(messagingStyle, string, context);
        }
        return new ConversationTextData(conversationTitle, text, string);
    }
}
