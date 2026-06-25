package com.motorola.taskbar.recent;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.accessibility.AccessibilityManager;
import com.android.launcher3.Utilities;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.launcher3.icons.IconProvider;
import com.android.launcher3.util.Preconditions;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$integer;
import com.motorola.taskbar.util.MainThreadExecutor;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public class TaskIconCache {
    private final AccessibilityManager mAccessibilityManager;
    private final Handler mBackgroundHandler;
    private final Context mContext;
    private final TaskKeyLruCache mIconCache;
    private BaseIconFactory mIconFactory;
    private final IconProvider mIconProvider;
    private final SparseArray mDefaultIcons = new SparseArray();
    private MainThreadExecutor mMainThreadExecutor = new MainThreadExecutor();

    /* JADX INFO: renamed from: com.motorola.taskbar.recent.TaskIconCache$1, reason: invalid class name */
    class AnonymousClass1 extends IconLoadRequest {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Task val$task;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Handler handler, Task task, Consumer consumer) {
            super(handler);
            this.val$task = task;
            this.val$callback = consumer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(Task task, TaskCacheEntry taskCacheEntry, Consumer consumer) {
            task.icon = taskCacheEntry.icon;
            task.titleDescription = taskCacheEntry.contentDescription;
            consumer.accept(task);
            onEnd();
        }

        @Override // java.lang.Runnable
        public void run() {
            final TaskCacheEntry cacheEntry = TaskIconCache.this.getCacheEntry(this.val$task);
            if (isCanceled()) {
                return;
            }
            MainThreadExecutor mainThreadExecutor = TaskIconCache.this.mMainThreadExecutor;
            final Task task = this.val$task;
            final Consumer consumer = this.val$callback;
            mainThreadExecutor.execute(new Runnable() { // from class: com.motorola.taskbar.recent.TaskIconCache$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$run$0(task, cacheEntry, consumer);
                }
            });
        }
    }

    public abstract class IconLoadRequest extends CancellablHandlerRunnable {
        IconLoadRequest(Handler handler) {
            super(handler, null);
        }
    }

    class TaskCacheEntry {
        public String contentDescription;
        public Drawable icon;

        private TaskCacheEntry() {
            this.contentDescription = "";
        }
    }

    public TaskIconCache(Context context, Looper looper) {
        this.mContext = context;
        this.mBackgroundHandler = new Handler(looper);
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mIconCache = new TaskKeyLruCache(context.getResources().getInteger(R$integer.recentsIconCacheSize));
        this.mIconProvider = new IconProvider(context);
    }

    private String getBadgedContentDescription(ActivityInfo activityInfo, int i, ActivityManager.TaskDescription taskDescription) {
        PackageManager packageManager = this.mContext.getPackageManager();
        String strTrim = taskDescription == null ? null : Utilities.trim(taskDescription.getLabel());
        if (TextUtils.isEmpty(strTrim)) {
            strTrim = Utilities.trim(activityInfo.loadLabel(packageManager));
        }
        String strTrim2 = Utilities.trim(activityInfo.applicationInfo.loadLabel(packageManager));
        String string = i != UserHandle.myUserId() ? packageManager.getUserBadgedLabel(strTrim2, UserHandle.of(i)).toString() : strTrim2;
        if (strTrim2.equals(strTrim)) {
            return string;
        }
        return string + " " + strTrim;
    }

    private BitmapInfo getBitmapInfo(Drawable drawable, int i, int i2, boolean z) {
        BaseIconFactory iconFactory = getIconFactory();
        try {
            iconFactory.setWrapperBackgroundColor(i2);
            BitmapInfo bitmapInfoCreateBadgedIconBitmap = iconFactory.createBadgedIconBitmap(drawable, new BaseIconFactory.IconOptions().setUser(UserHandle.of(i)));
            iconFactory.close();
            return bitmapInfoCreateBadgedIconBitmap;
        } catch (Throwable th) {
            if (iconFactory != null) {
                try {
                    iconFactory.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public TaskCacheEntry getCacheEntry(Task task) {
        TaskCacheEntry taskCacheEntry = (TaskCacheEntry) this.mIconCache.getAndInvalidateIfModified(task.key);
        if (taskCacheEntry != null) {
            return taskCacheEntry;
        }
        ActivityManager.TaskDescription taskDescription = task.taskDescription;
        Task.TaskKey taskKey = task.key;
        ActivityInfo activityInfo = null;
        TaskCacheEntry taskCacheEntry2 = new TaskCacheEntry();
        Bitmap icon = getIcon(taskDescription, taskKey.userId);
        if (icon != null) {
            taskCacheEntry2.icon = new FastBitmapDrawable(getBitmapInfo(new BitmapDrawable(this.mContext.getResources(), icon), taskKey.userId, taskDescription.getPrimaryColor(), false));
        } else {
            activityInfo = PackageManagerWrapper.getInstance().getActivityInfo(taskKey.getComponent(), taskKey.userId);
            if (activityInfo != null) {
                taskCacheEntry2.icon = getBitmapInfo(this.mIconProvider.getIcon(activityInfo), taskKey.userId, taskDescription.getPrimaryColor(), activityInfo.applicationInfo.isInstantApp()).newIcon(this.mContext);
            } else {
                taskCacheEntry2.icon = getDefaultIcon(taskKey.userId);
            }
        }
        if (activityInfo == null) {
            activityInfo = PackageManagerWrapper.getInstance().getActivityInfo(taskKey.getComponent(), taskKey.userId);
        }
        if (activityInfo != null) {
            taskCacheEntry2.contentDescription = getBadgedContentDescription(activityInfo, task.key.userId, task.taskDescription);
        }
        this.mIconCache.put(task.key, taskCacheEntry2);
        return taskCacheEntry2;
    }

    private Drawable getDefaultIcon(int i) {
        FastBitmapDrawable fastBitmapDrawableNewIcon;
        synchronized (this.mDefaultIcons) {
            BitmapInfo bitmapInfo = (BitmapInfo) this.mDefaultIcons.get(i);
            if (bitmapInfo == null) {
                BaseIconFactory iconFactory = getIconFactory();
                try {
                    BitmapInfo bitmapInfoMakeDefaultIcon = iconFactory.makeDefaultIcon(this.mIconProvider);
                    iconFactory.close();
                    this.mDefaultIcons.put(i, bitmapInfoMakeDefaultIcon);
                    bitmapInfo = bitmapInfoMakeDefaultIcon;
                } finally {
                }
            }
            fastBitmapDrawableNewIcon = bitmapInfo.newIcon(this.mContext);
        }
        return fastBitmapDrawableNewIcon;
    }

    private Bitmap getIcon(ActivityManager.TaskDescription taskDescription, int i) {
        return taskDescription.getInMemoryIcon() != null ? taskDescription.getInMemoryIcon() : ActivityManager.TaskDescription.loadTaskDescriptionIcon(taskDescription.getIconFilename(), i);
    }

    private BaseIconFactory getIconFactory() {
        if (this.mIconFactory == null) {
            Context context = this.mContext;
            this.mIconFactory = new BaseIconFactory(context, 640, context.getResources().getDimensionPixelSize(R$dimen.taskbar_icon_size));
        }
        return this.mIconFactory;
    }

    void onTaskRemoved(Task.TaskKey taskKey) {
        this.mIconCache.remove(taskKey);
    }

    public IconLoadRequest updateIconInBackground(Task task, Consumer consumer) {
        Preconditions.assertUIThread();
        if (task.icon != null) {
            consumer.accept(task);
            return null;
        }
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.mBackgroundHandler, task, consumer);
        Utilities.postAsyncCallback(this.mBackgroundHandler, anonymousClass1);
        return anonymousClass1;
    }
}
