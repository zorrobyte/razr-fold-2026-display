package com.motorola.extendscreenshot;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.animation.Animation;
import android.view.animation.TranslateXAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.window.ScreenCapture;
import com.android.systemui.Dependency;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.motorola.extendscreenshot.ScreenshotController;
import com.motorola.extendscreenshot.ScreenshotSelectorView;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.TaskBarServiceCallBack;
import com.motorola.taskbar.TaskBarServiceProxy;
import com.motorola.taskbar.bar.MirrorPhonePanelController;
import com.motorola.taskbar.bar.TaskBarController;
import com.motorola.taskbar.util.TooltipPopupManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class ScreenshotController implements TaskBarServiceCallBack {
    private static final boolean DEBUG = !Build.IS_PRODUCTION_DEVICE;
    private Context mContext;
    private SparseArray mExtendScreenshotSessions = new SparseArray();
    private Handler mMainHandler;

    class ExtendScreenshotSession implements View.OnClickListener, View.OnHoverListener {
        private int mActiveBackgroundColor;
        private final BroadcastReceiver mBroadcastReceiver;
        private Context mContext;
        private Rect mCurrentFocusedWindowRect;
        private int mCurrentUserId;
        private TextView mCustomTooltext;
        private Display mDisplay;
        private int mDisplayId;
        private final DisplayManager mDisplayManager;
        private Bitmap mFullscreenBitmap;
        private ImageView mGlobalScreenshotBackground;
        private ImageButton mIBCurve;
        private ImageButton mIBFullscreen;
        private ImageButton mIBRect;
        private ImageButton mIBWindow;
        private Uri mImageUri;
        private int mLastActivedMode;
        View.OnLayoutChangeListener mLayoutChangeListener;
        private Handler mMainHandler;
        private List mSaveInBgTasks;
        private int[] mScreenshotButtonLocation;
        private ImageView mScreenshotPreview;
        private View mScreenshotPreviewRoot;
        private ScreenshotSelectorView mScreenshotSelectorView;
        private int mScreenshotType;
        private View mScreenshotWindow;
        private View mScreenshotWindowContainer;
        private View mSelectorToolsBar;
        private TextView mTextShared;
        private SparseArray mTipShownIds;
        private ArrayList mToolBtns;
        private TooltipPopupManager mTooltipPopupManager;
        private int mTooltipsOffset;
        private final WindowManager mWindowManager;

        /* JADX INFO: renamed from: com.motorola.extendscreenshot.ScreenshotController$ExtendScreenshotSession$10, reason: invalid class name */
        class AnonymousClass10 extends GlobalScreenshot$ActionsReadyListener {
            final /* synthetic */ Bitmap val$cropBitmap;

            AnonymousClass10(Bitmap bitmap) {
                this.val$cropBitmap = bitmap;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onActionsReady$0(Bitmap bitmap, GlobalScreenshot$SavedImageData globalScreenshot$SavedImageData) {
                ExtendScreenshotSession.this.showPreviewWindow(bitmap, globalScreenshot$SavedImageData);
            }

            @Override // com.motorola.extendscreenshot.GlobalScreenshot$ActionsReadyListener
            void onActionsReady(final GlobalScreenshot$SavedImageData globalScreenshot$SavedImageData) {
                Handler handler = ExtendScreenshotSession.this.mMainHandler;
                final Bitmap bitmap = this.val$cropBitmap;
                handler.post(new Runnable() { // from class: com.motorola.extendscreenshot.ScreenshotController$ExtendScreenshotSession$10$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onActionsReady$0(bitmap, globalScreenshot$SavedImageData);
                    }
                });
            }
        }

        /* JADX INFO: renamed from: com.motorola.extendscreenshot.ScreenshotController$ExtendScreenshotSession$11, reason: invalid class name */
        class AnonymousClass11 extends GlobalScreenshot$ActionsReadyListener {
            final /* synthetic */ Bitmap val$bitmap;

            AnonymousClass11(Bitmap bitmap) {
                this.val$bitmap = bitmap;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onActionsReady$0(Bitmap bitmap, GlobalScreenshot$SavedImageData globalScreenshot$SavedImageData) {
                ExtendScreenshotSession.this.showPreviewWindow(bitmap, globalScreenshot$SavedImageData);
            }

            @Override // com.motorola.extendscreenshot.GlobalScreenshot$ActionsReadyListener
            void onActionsReady(final GlobalScreenshot$SavedImageData globalScreenshot$SavedImageData) {
                Handler handler = ExtendScreenshotSession.this.mMainHandler;
                final Bitmap bitmap = this.val$bitmap;
                handler.post(new Runnable() { // from class: com.motorola.extendscreenshot.ScreenshotController$ExtendScreenshotSession$11$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onActionsReady$0(bitmap, globalScreenshot$SavedImageData);
                    }
                });
            }
        }

        /* JADX INFO: renamed from: com.motorola.extendscreenshot.ScreenshotController$ExtendScreenshotSession$9, reason: invalid class name */
        class AnonymousClass9 extends GlobalScreenshot$ActionsReadyListener {
            final /* synthetic */ Bitmap val$bitmap;

            AnonymousClass9(Bitmap bitmap) {
                this.val$bitmap = bitmap;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onActionsReady$0(Bitmap bitmap, GlobalScreenshot$SavedImageData globalScreenshot$SavedImageData) {
                ExtendScreenshotSession.this.showPreviewWindow(bitmap.copy(bitmap.getConfig(), false), globalScreenshot$SavedImageData);
            }

            @Override // com.motorola.extendscreenshot.GlobalScreenshot$ActionsReadyListener
            void onActionsReady(final GlobalScreenshot$SavedImageData globalScreenshot$SavedImageData) {
                Handler handler = ExtendScreenshotSession.this.mMainHandler;
                final Bitmap bitmap = this.val$bitmap;
                handler.post(new Runnable() { // from class: com.motorola.extendscreenshot.ScreenshotController$ExtendScreenshotSession$9$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onActionsReady$0(bitmap, globalScreenshot$SavedImageData);
                    }
                });
            }
        }

        public static /* synthetic */ void $r8$lambda$u5cHQ8OMsrEc5aCju7REf5WbayI(Uri uri) {
        }

        private ExtendScreenshotSession(Context context, int i) {
            this.mActiveBackgroundColor = -16776961;
            this.mToolBtns = new ArrayList();
            this.mLastActivedMode = 0;
            this.mSaveInBgTasks = new ArrayList();
            this.mTipShownIds = new SparseArray();
            this.mScreenshotButtonLocation = new int[2];
            this.mTooltipsOffset = 0;
            this.mCurrentUserId = 0;
            this.mMainHandler = new Handler(Looper.getMainLooper()) { // from class: com.motorola.extendscreenshot.ScreenshotController.ExtendScreenshotSession.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (message.what != 2) {
                        return;
                    }
                    ExtendScreenshotSession.this.dismissSavedPreviewScreen(true);
                }
            };
            this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.motorola.extendscreenshot.ScreenshotController.ExtendScreenshotSession.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent) {
                    ExtendScreenshotSession.this.dismissSavedPreviewScreen(false);
                }
            };
            this.mLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.motorola.extendscreenshot.ScreenshotController.ExtendScreenshotSession.6
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                    int measuredWidth = ((i4 - i2) / 2) - (ExtendScreenshotSession.this.mSelectorToolsBar.getMeasuredWidth() / 2);
                    int measuredHeight = 0;
                    if (ExtendScreenshotSession.this.mScreenshotType == 8) {
                        measuredWidth = ExtendScreenshotSession.this.mScreenshotButtonLocation[0] - (ExtendScreenshotSession.this.mSelectorToolsBar.getMeasuredWidth() / 2);
                        measuredHeight = ExtendScreenshotSession.this.mScreenshotButtonLocation[1] - ExtendScreenshotSession.this.mSelectorToolsBar.getMeasuredHeight();
                    }
                    ExtendScreenshotSession.this.updateToolsBarPosition(measuredWidth, measuredHeight);
                }
            };
            this.mDisplayId = i;
            DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
            this.mDisplayManager = displayManager;
            Display display = displayManager.getDisplay(i);
            this.mDisplay = display;
            Context contextCreateDisplayContext = context.createDisplayContext(display);
            this.mContext = contextCreateDisplayContext;
            this.mWindowManager = (WindowManager) contextCreateDisplayContext.getSystemService("window");
            this.mTooltipsOffset = context.getResources().getDimensionPixelOffset(R$dimen.screenshot_tooltips_margin);
            initReceiver();
            this.mCurrentUserId = getCurrentUserId();
        }

        private void cropAndSaveBitmap(Bitmap bitmap, Rect rect) {
            if (rect.left < 0) {
                rect.left = 0;
            }
            if (rect.top < 0) {
                rect.top = 0;
            }
            if (rect.right > bitmap.getWidth()) {
                rect.right = bitmap.getWidth();
            }
            if (rect.bottom > bitmap.getHeight()) {
                rect.bottom = bitmap.getHeight();
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height());
            if (bitmapCreateBitmap != bitmap) {
                bitmap.recycle();
            }
            saveScreenshotInWorkerThread(bitmapCreateBitmap, new AnonymousClass10(bitmapCreateBitmap));
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: dismissPreviewScreen, reason: merged with bridge method [inline-methods] */
        public void lambda$handlePreviewClicked$0() {
            if (ScreenshotController.DEBUG) {
                Log.d("ExtendScreenshotSession", "dismissPreviewScreen mScreenshotWindow = " + this.mScreenshotWindow);
            }
            View view = this.mScreenshotWindow;
            if (view != null) {
                this.mWindowManager.removeView(view);
            }
            this.mScreenshotWindow = null;
            Bitmap bitmap = this.mFullscreenBitmap;
            if (bitmap != null && !bitmap.isRecycled()) {
                this.mFullscreenBitmap.recycle();
            }
            this.mFullscreenBitmap = null;
            this.mImageUri = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dismissSavedPreviewScreen(boolean z) {
            if (!z) {
                lambda$handlePreviewClicked$0();
                return;
            }
            Animation translateXAnimation = new TranslateXAnimation(0.0f, isRtl() ? -(this.mScreenshotPreviewRoot.getRight() - this.mScreenshotPreviewRoot.getLeft()) : this.mScreenshotPreviewRoot.getLeft() + this.mScreenshotPreviewRoot.getWidth());
            translateXAnimation.setDuration(200L);
            translateXAnimation.setFillAfter(true);
            translateXAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.motorola.extendscreenshot.ScreenshotController.ExtendScreenshotSession.3
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    ExtendScreenshotSession.this.lambda$handlePreviewClicked$0();
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }
            });
            this.mScreenshotPreviewRoot.startAnimation(translateXAnimation);
        }

        private int getCurrentUserId() {
            try {
                return ActivityManager.getService().getCurrentUser().id;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private Bitmap getCurveBitmap(Bitmap bitmap, ScreenshotSelectorView screenshotSelectorView) {
            if (bitmap == null) {
                Log.d("ExtendScreenshotSession", "sourceBitmap is null");
                return null;
            }
            if (bitmap.isRecycled()) {
                Log.d("ExtendScreenshotSession", "sourceBitmap is isRecycled");
                return null;
            }
            ScreenshotSelectorView.GraphicPath graphicPath = screenshotSelectorView.getGraphicPath();
            if (graphicPath != null) {
                if (graphicPath.size() > 1) {
                    Rect rect = new Rect(graphicPath.getLeft(), graphicPath.getTop(), graphicPath.getRight(), graphicPath.getBottom());
                    if (rect.left < 0) {
                        rect.left = 0;
                    }
                    if (rect.right < 0) {
                        rect.right = 0;
                    }
                    if (rect.top < 0) {
                        rect.top = 0;
                    }
                    if (rect.bottom < 0) {
                        rect.bottom = 0;
                    }
                    int iAbs = Math.abs(rect.left - rect.right);
                    int iAbs2 = Math.abs(rect.top - rect.bottom);
                    if (iAbs > 0 && iAbs2 > 0) {
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        paint.setStyle(Paint.Style.FILL_AND_STROKE);
                        paint.setColor(-1);
                        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iAbs, iAbs2, Bitmap.Config.ARGB_8888);
                        graphicPath.getBounds(new Rect());
                        Canvas canvas = new Canvas(bitmapCreateBitmap);
                        canvas.translate(-rect.left, -rect.top);
                        Path path = new Path();
                        path.moveTo(((Integer) graphicPath.pathX.get(0)).intValue(), ((Integer) graphicPath.pathY.get(0)).intValue());
                        for (int i = 1; i < graphicPath.size(); i++) {
                            path.lineTo(((Integer) graphicPath.pathX.get(i)).intValue(), ((Integer) graphicPath.pathY.get(i)).intValue());
                        }
                        canvas.drawPath(path, paint);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(bitmap, (Rect) null, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), paint);
                        bitmap.recycle();
                        return bitmapCreateBitmap;
                    }
                }
            }
            return null;
        }

        private Bitmap getFocusedScreenBitmap() {
            int rotation = this.mDisplay.getRotation();
            Rect focusedWindowRect = this.mWindowManager.getFocusedWindowRect(this.mDisplayId);
            if (focusedWindowRect != null && !focusedWindowRect.isEmpty()) {
                return screenshot(focusedWindowRect, focusedWindowRect.width(), focusedWindowRect.height(), rotation);
            }
            Log.w("ExtendScreenshotSession", "getFocusedWindowRect failed.");
            return null;
        }

        private Bitmap getFullscreenBitmap() {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.mDisplay.getRealMetrics(displayMetrics);
            Rect rect = new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
            int rotation = this.mDisplay.getRotation();
            int iWidth = rect.width();
            int iHeight = rect.height();
            if (ScreenshotController.DEBUG) {
                Log.d("ExtendScreenshotSession", "getFullscreenBitmap, width:" + iWidth + " height:" + iHeight + " rotation:" + rotation + " mDisplay:" + this.mDisplay);
            }
            return screenshot(rect, iWidth, iHeight, rotation);
        }

        private WindowManager.LayoutParams getScreenshotWindowParams() {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, 2036, 394528, -3);
            layoutParams.privateFlags = 16;
            layoutParams.setTitle("ExtendScreenshot");
            layoutParams.layoutInDisplayCutoutMode = 3;
            layoutParams.setFitInsetsTypes(0);
            return layoutParams;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleActionUp(ScreenshotSelectorView screenshotSelectorView) {
            int selectorMode = screenshotSelectorView.getSelectorMode();
            if (selectorMode == 0) {
                handleRectSelectorView(screenshotSelectorView);
            } else if (selectorMode == 1) {
                handleCurveSelectorView(screenshotSelectorView);
            }
        }

        private void handleCurveSelectorView(ScreenshotSelectorView screenshotSelectorView) {
            Bitmap curveBitmap = getCurveBitmap(this.mFullscreenBitmap, screenshotSelectorView);
            if (curveBitmap != null) {
                saveScreenshotInWorkerThread(curveBitmap, new AnonymousClass11(curveBitmap));
            } else {
                Log.d("ExtendScreenshotSession", "handleCurveSelectorView bitmap is null.");
                lambda$handlePreviewClicked$0();
            }
        }

        private void handleDirectScreenshot(int i, boolean z) {
            Bitmap focusedScreenBitmap;
            Log.d("ExtendScreenshotSession", "handleDirectScreenshot type = " + i + "; fromTools = " + z);
            if (i == 1) {
                focusedScreenBitmap = z ? this.mFullscreenBitmap : getFullscreenBitmap();
            } else if (i != 2) {
                focusedScreenBitmap = null;
            } else {
                if (z) {
                    Rect rect = this.mCurrentFocusedWindowRect;
                    if (rect == null || rect.isEmpty()) {
                        Log.w("ExtendScreenshotSession", "handleDirectScreenshot get mCurrentFocusedWindowRect failed.");
                        return;
                    } else {
                        cropAndSaveBitmap(this.mFullscreenBitmap, this.mCurrentFocusedWindowRect);
                        return;
                    }
                }
                focusedScreenBitmap = getFocusedScreenBitmap();
            }
            if (focusedScreenBitmap == null) {
                Log.w("ExtendScreenshotSession", "handleDirectScreenshot getBitmap failed.");
            } else {
                saveScreenshotInWorkerThread(focusedScreenBitmap, new AnonymousClass9(focusedScreenBitmap));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handlePreviewClicked(View view, Uri uri) {
            Intent intent = new Intent("android.intent.action.EDIT");
            intent.setType("image/png");
            intent.setData(uri);
            intent.addFlags(1);
            intent.addFlags(2);
            intent.addFlags(268468224);
            ActivityManagerWrapper.getInstance().closeSystemWindows("screenshot");
            try {
                PendingIntent.getActivityAsUser(this.mContext, 0, intent, 67108864, null, UserHandle.CURRENT).send();
            } catch (PendingIntent.CanceledException e) {
                Log.e("ExtendScreenshotSession", "Pending intent canceled", e);
            }
            view.post(new Runnable() { // from class: com.motorola.extendscreenshot.ScreenshotController$ExtendScreenshotSession$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$handlePreviewClicked$0();
                }
            });
        }

        private void handleRectSelectorView(ScreenshotSelectorView screenshotSelectorView) {
            Rect selectionRect = screenshotSelectorView.getSelectionRect();
            if (selectionRect == null || selectionRect.width() < 10 || selectionRect.height() < 10) {
                lambda$handlePreviewClicked$0();
            } else {
                cropAndSaveBitmap(this.mFullscreenBitmap, selectionRect);
            }
        }

        private void handleScreenshotTool() {
            Bitmap fullscreenBitmap = getFullscreenBitmap();
            this.mFullscreenBitmap = fullscreenBitmap;
            if (fullscreenBitmap == null) {
                Log.w("ExtendScreenshotSession", "get screenshot failed.");
            } else if (fullscreenBitmap.getWidth() == 0 || this.mFullscreenBitmap.getHeight() == 0) {
                Log.w("ExtendScreenshotSession", "FullscreenBitmap size error");
            } else {
                Bitmap bitmap = this.mFullscreenBitmap;
                showScreenshotWindow(bitmap.copy(bitmap.getConfig(), false));
            }
        }

        private void handleSharedAction(Uri uri) {
            if (uri == null) {
                Log.w("ExtendScreenshotSession", "uri is null ");
                return;
            }
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/png");
            intent.putExtra("android.intent.extra.STREAM", uri);
            intent.setClipData(new ClipData(new ClipDescription("content", new String[]{"text/plain"}), new ClipData.Item(uri)));
            intent.addFlags(1);
            Intent intentAddFlags = Intent.createChooser(intent, null).addFlags(268468224);
            ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
            activityOptionsMakeBasic.setLaunchDisplayId(this.mDisplayId);
            Bundle bundle = activityOptionsMakeBasic.toBundle();
            intentAddFlags.putExtra("com.android.internal.app.ChooserActivity.EXTRA_PRIVATE_RETAIN_IN_ON_STOP", true);
            if (this.mContext.getUserId() != this.mCurrentUserId) {
                Intent intent2 = new Intent(this.mContext, (Class<?>) ActionProxyReceiver.class);
                intent2.setAction("ACTION.share_img");
                intent2.putExtra("intent", intentAddFlags);
                this.mContext.sendBroadcastAsUser(intent2, UserHandle.CURRENT);
            } else {
                this.mContext.startActivityAsUser(intentAddFlags, bundle, UserHandle.CURRENT);
            }
            lambda$handlePreviewClicked$0();
        }

        private void handleTooltips(MotionEvent motionEvent, View view, String str) {
            TextView textView;
            if (motionEvent.getAction() == 9) {
                if (this.mTooltipPopupManager == null || (textView = this.mCustomTooltext) == null) {
                    return;
                }
                textView.setText(str);
                this.mTipShownIds.put(view.getId(), Long.valueOf(this.mTooltipPopupManager.show(1, view, -this.mTooltipsOffset, false, this.mCustomTooltext, new ViewGroup.LayoutParams(-2, -2))));
                return;
            }
            if (motionEvent.getAction() == 10) {
                long jLongValue = ((Long) this.mTipShownIds.get(view.getId())).longValue();
                TooltipPopupManager tooltipPopupManager = this.mTooltipPopupManager;
                if (tooltipPopupManager == null || jLongValue == -1) {
                    return;
                }
                tooltipPopupManager.hide(jLongValue, false);
                this.mTipShownIds.put(view.getId(), -1L);
            }
        }

        private void initReceiver() {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter, 2);
        }

        private void initScreenshotLayout() {
            View viewInflate = LayoutInflater.from(this.mContext).inflate(R$layout.extend_global_screen_shot, (ViewGroup) null);
            this.mScreenshotWindow = viewInflate;
            View viewFindViewById = viewInflate.findViewById(R$id.screen_shot_container);
            this.mScreenshotWindowContainer = viewFindViewById;
            viewFindViewById.setVisibility(8);
            ImageView imageView = (ImageView) this.mScreenshotWindow.findViewById(R$id.global_screenshot_background);
            this.mGlobalScreenshotBackground = imageView;
            imageView.setVisibility(8);
            ScreenshotSelectorView screenshotSelectorView = (ScreenshotSelectorView) this.mScreenshotWindow.findViewById(R$id.global_screenshot_selector);
            this.mScreenshotSelectorView = screenshotSelectorView;
            screenshotSelectorView.setVisibility(8);
            View viewFindViewById2 = this.mScreenshotWindow.findViewById(R$id.screen_preview_root);
            this.mScreenshotPreviewRoot = viewFindViewById2;
            viewFindViewById2.setVisibility(8);
            ImageView imageView2 = (ImageView) this.mScreenshotWindow.findViewById(R$id.global_screenshot_preview);
            this.mScreenshotPreview = imageView2;
            imageView2.setVisibility(8);
            TextView textView = (TextView) this.mScreenshotPreviewRoot.findViewById(R$id.tv_action_shared);
            this.mTextShared = textView;
            textView.setOnClickListener(this);
            this.mCustomTooltext = (TextView) LayoutInflater.from(this.mContext).inflate(R$layout.screenshot_custom_tooltip, (ViewGroup) null);
            initSelectorToolsLayout();
        }

        private void initSelectorToolsLayout() {
            this.mSelectorToolsBar = this.mScreenshotWindow.findViewById(R$id.selector_tools_bar);
            this.mTooltipPopupManager = ((TaskBarController) Dependency.get(TaskBarController.class)).getTooltipPopupManager(this.mSelectorToolsBar);
            ImageButton imageButton = (ImageButton) this.mScreenshotWindow.findViewById(R$id.btn_rect);
            this.mIBRect = imageButton;
            imageButton.setOnHoverListener(this);
            this.mIBRect.setOnClickListener(this);
            ImageButton imageButton2 = (ImageButton) this.mScreenshotWindow.findViewById(R$id.btn_curve);
            this.mIBCurve = imageButton2;
            imageButton2.setOnHoverListener(this);
            this.mIBCurve.setOnClickListener(this);
            ImageButton imageButton3 = (ImageButton) this.mScreenshotWindow.findViewById(R$id.btn_window);
            this.mIBWindow = imageButton3;
            imageButton3.setOnHoverListener(this);
            this.mIBWindow.setOnClickListener(this);
            ImageButton imageButton4 = (ImageButton) this.mScreenshotWindow.findViewById(R$id.btn_full_screen);
            this.mIBFullscreen = imageButton4;
            imageButton4.setOnHoverListener(this);
            this.mIBFullscreen.setOnClickListener(this);
            this.mToolBtns.clear();
            this.mToolBtns.add(this.mIBRect);
            this.mToolBtns.add(this.mIBCurve);
            this.mToolBtns.add(this.mIBWindow);
            this.mToolBtns.add(this.mIBFullscreen);
        }

        private boolean isRtl() {
            return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$takeScreenshot$1(int i) {
            takeScreenshot(i, false);
        }

        private void saveScreenshotInWorkerThread(Bitmap bitmap, GlobalScreenshot$ActionsReadyListener globalScreenshot$ActionsReadyListener) {
            GlobalScreenshot$SaveImageInBackgroundData globalScreenshot$SaveImageInBackgroundData = new GlobalScreenshot$SaveImageInBackgroundData();
            globalScreenshot$SaveImageInBackgroundData.image = bitmap;
            globalScreenshot$SaveImageInBackgroundData.finisher = new Consumer() { // from class: com.motorola.extendscreenshot.ScreenshotController$ExtendScreenshotSession$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ScreenshotController.ExtendScreenshotSession.$r8$lambda$u5cHQ8OMsrEc5aCju7REf5WbayI((Uri) obj);
                }
            };
            globalScreenshot$SaveImageInBackgroundData.mActionsReadyListener = globalScreenshot$ActionsReadyListener;
            synchronized (this.mSaveInBgTasks) {
                try {
                    if (this.mSaveInBgTasks.size() >= 5) {
                        ((SaveImageInBackgroundTask) this.mSaveInBgTasks.remove(0)).setActionsReadyListener(new GlobalScreenshot$ActionsReadyListener(this) { // from class: com.motorola.extendscreenshot.ScreenshotController.ExtendScreenshotSession.12
                            @Override // com.motorola.extendscreenshot.GlobalScreenshot$ActionsReadyListener
                            void onActionsReady(GlobalScreenshot$SavedImageData globalScreenshot$SavedImageData) {
                            }
                        });
                    }
                    Context contextCreateContextAsUser = this.mContext;
                    int userId = contextCreateContextAsUser.getUserId();
                    int i = this.mCurrentUserId;
                    if (userId != i) {
                        contextCreateContextAsUser = this.mContext.createContextAsUser(UserHandle.of(i), 0);
                    }
                    SaveImageInBackgroundTask saveImageInBackgroundTask = new SaveImageInBackgroundTask(contextCreateContextAsUser, globalScreenshot$SaveImageInBackgroundData);
                    this.mSaveInBgTasks.add(saveImageInBackgroundTask);
                    saveImageInBackgroundTask.execute(new Void[0]);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        private Bitmap screenshot(Rect rect, int i, int i2, int i3) {
            ScreenCapture.ScreenshotHardwareBuffer buffer;
            int displayId = this.mDisplay.getDisplayId();
            ScreenCapture.CaptureArgs captureArgsBuild = new ScreenCapture.CaptureArgs.Builder().setSourceCrop(rect).build();
            ScreenCapture.SynchronousScreenCaptureListener synchronousScreenCaptureListenerCreateSyncCaptureListener = ScreenCapture.createSyncCaptureListener();
            try {
                WindowManagerGlobal.getWindowManagerService().captureDisplay(displayId, captureArgsBuild, synchronousScreenCaptureListenerCreateSyncCaptureListener);
                buffer = synchronousScreenCaptureListenerCreateSyncCaptureListener.getBuffer();
            } catch (RemoteException e) {
                e.printStackTrace();
                buffer = null;
            }
            Bitmap bitmapAsBitmap = buffer == null ? null : buffer.asBitmap();
            if (bitmapAsBitmap == null) {
                Log.w("ExtendScreenshotSession", "Failed to take screenshot display: " + displayId);
                showScreenshotFailedToast(R$string.screenshot_failed_to_capture_text);
                return null;
            }
            Bitmap bitmapCopy = bitmapAsBitmap.copy(Bitmap.Config.ARGB_8888, false);
            bitmapAsBitmap.recycle();
            if (bitmapCopy == null) {
                Log.w("ExtendScreenshotSession", "Failed to take screenshot display: " + displayId);
                showScreenshotFailedToast(R$string.screenshot_failed_to_capture_text);
            }
            return bitmapCopy;
        }

        private void sendScreenshotBroadcast() {
            Intent intent = new Intent("com.motorola.systemui.desk.ACTION.SCREENSHOT");
            intent.setPackage("com.motorola.mobiledesktop");
            intent.putExtra("display_id", this.mDisplayId);
            intent.putExtra("image_uri", this.mImageUri.toString());
            this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void showPreviewWindow(Bitmap bitmap, final GlobalScreenshot$SavedImageData globalScreenshot$SavedImageData) {
            boolean z;
            if (globalScreenshot$SavedImageData.uri == null) {
                showScreenshotFailedToast(R$string.screenshot_failed_to_save_text);
                lambda$handlePreviewClicked$0();
                return;
            }
            if (this.mScreenshotWindow == null) {
                initScreenshotLayout();
                z = true;
            } else {
                z = false;
            }
            this.mScreenshotWindowContainer.setVisibility(8);
            this.mScreenshotPreviewRoot.setVisibility(0);
            this.mScreenshotPreview.setImageBitmap(bitmap);
            this.mScreenshotPreview.setVisibility(0);
            this.mMainHandler.removeMessages(2);
            Handler handler = this.mMainHandler;
            handler.sendMessageDelayed(Message.obtain(handler, 2), 6000L);
            this.mScreenshotPreview.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.extendscreenshot.ScreenshotController.ExtendScreenshotSession.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ExtendScreenshotSession.this.mMainHandler.removeMessages(2);
                    ExtendScreenshotSession.this.handlePreviewClicked(view, globalScreenshot$SavedImageData.uri);
                }
            });
            this.mImageUri = globalScreenshot$SavedImageData.uri;
            WindowManager.LayoutParams screenshotWindowParams = getScreenshotWindowParams();
            screenshotWindowParams.width = this.mContext.getResources().getDimensionPixelSize(R$dimen.screenshot_preview_window_width);
            screenshotWindowParams.height = this.mContext.getResources().getDimensionPixelSize(R$dimen.screenshot_preview_window_height);
            if (isRtl()) {
                screenshotWindowParams.gravity = 83;
            } else {
                screenshotWindowParams.gravity = 85;
            }
            if (z) {
                this.mWindowManager.addView(this.mScreenshotWindow, screenshotWindowParams);
            } else {
                if (ScreenshotController.DEBUG) {
                    Log.d("ExtendScreenshotSession", "showPreviewWindow mScreenshotWindow = " + this.mScreenshotWindow);
                }
                this.mWindowManager.removeViewImmediate(this.mScreenshotWindow);
                this.mWindowManager.addView(this.mScreenshotWindow, screenshotWindowParams);
            }
            this.mScreenshotWindow.setOnTouchListener(new View.OnTouchListener() { // from class: com.motorola.extendscreenshot.ScreenshotController.ExtendScreenshotSession.8
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() != 4) {
                        return false;
                    }
                    ExtendScreenshotSession.this.mMainHandler.removeMessages(2);
                    ExtendScreenshotSession.this.lambda$handlePreviewClicked$0();
                    return true;
                }
            });
            sendScreenshotBroadcast();
        }

        private void showScreenshotFailedToast(int i) {
            Toast.makeText(this.mContext, i, 1).show();
        }

        private void showScreenshotWindow(Bitmap bitmap) {
            initScreenshotLayout();
            this.mScreenshotWindowContainer.setVisibility(0);
            this.mGlobalScreenshotBackground.setImageBitmap(bitmap);
            this.mGlobalScreenshotBackground.setVisibility(0);
            this.mScreenshotSelectorView.setVisibility(0);
            updateActivedToolButton(this.mLastActivedMode);
            this.mWindowManager.addView(this.mScreenshotWindow, getScreenshotWindowParams());
            this.mScreenshotWindow.setFocusableInTouchMode(true);
            this.mScreenshotWindow.requestFocus();
            this.mScreenshotWindow.setOnKeyListener(new View.OnKeyListener() { // from class: com.motorola.extendscreenshot.ScreenshotController.ExtendScreenshotSession.4
                @Override // android.view.View.OnKeyListener
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if (i != 111 || keyEvent.getAction() != 0) {
                        return false;
                    }
                    if (ExtendScreenshotSession.this.mScreenshotWindow == null) {
                        return true;
                    }
                    ExtendScreenshotSession.this.lambda$handlePreviewClicked$0();
                    return true;
                }
            });
            this.mScreenshotSelectorView.addSelectorViewStatusChangeListener(new ScreenshotSelectorView.SelectorViewStatusChangedListener() { // from class: com.motorola.extendscreenshot.ScreenshotController.ExtendScreenshotSession.5
                @Override // com.motorola.extendscreenshot.ScreenshotSelectorView.SelectorViewStatusChangedListener
                public void onTouchChanged(boolean z) {
                    Log.d("ExtendScreenshotSession", "onTouchChanged touching = " + z);
                    if (z) {
                        ExtendScreenshotSession.this.showSelectorTools(false);
                    } else {
                        ExtendScreenshotSession extendScreenshotSession = ExtendScreenshotSession.this;
                        extendScreenshotSession.handleActionUp(extendScreenshotSession.mScreenshotSelectorView);
                    }
                }
            });
            showSelectorTools(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void showSelectorTools(boolean z) {
            this.mSelectorToolsBar.setVisibility(z ? 0 : 8);
        }

        private void switchSelectorMode(int i) {
            this.mScreenshotSelectorView.setSelectMode(i);
            updateActivedToolButton(i);
            showSelectorTools(false);
            this.mLastActivedMode = i;
            if (i == 2) {
                takeScreenshot(2, true);
            } else {
                if (i != 3) {
                    return;
                }
                takeScreenshot(1, true);
            }
        }

        private void updateActivedToolButton(int i) {
            if (i == 0) {
                this.mIBRect.setActivated(true);
            } else if (i == 1) {
                this.mIBCurve.setActivated(true);
            } else if (i == 2) {
                this.mIBWindow.setActivated(true);
            } else if (i == 3) {
                this.mIBFullscreen.setActivated(true);
            }
            this.mSelectorToolsBar.postInvalidate();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateToolsBarPosition(int i, int i2) {
            if (this.mSelectorToolsBar != null) {
                if (ScreenshotController.DEBUG) {
                    Log.d("ExtendScreenshotSession", "updateToolsBarPosition x = " + i + "; y = " + i2);
                }
                View view = this.mSelectorToolsBar;
                view.layout(i, i2, view.getMeasuredWidth() + i, this.mSelectorToolsBar.getMeasuredHeight() + i2);
                showSelectorTools(true);
                this.mScreenshotWindow.removeOnLayoutChangeListener(this.mLayoutChangeListener);
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int id = view.getId();
            if (id == R$id.btn_rect) {
                switchSelectorMode(0);
                return;
            }
            if (id == R$id.btn_curve) {
                switchSelectorMode(1);
                return;
            }
            if (id == R$id.btn_window) {
                switchSelectorMode(2);
            } else if (id == R$id.btn_full_screen) {
                switchSelectorMode(3);
            } else if (id == R$id.tv_action_shared) {
                handleSharedAction(this.mImageUri);
            }
        }

        @Override // android.view.View.OnHoverListener
        public boolean onHover(View view, MotionEvent motionEvent) {
            int id = view.getId();
            Resources resources = this.mContext.getResources();
            if (id == R$id.btn_rect) {
                handleTooltips(motionEvent, this.mIBRect, resources.getString(R$string.tooltips_rect));
                return false;
            }
            if (id == R$id.btn_curve) {
                handleTooltips(motionEvent, this.mIBCurve, resources.getString(R$string.tooltips_curve));
                return false;
            }
            if (id == R$id.btn_window) {
                handleTooltips(motionEvent, this.mIBWindow, resources.getString(R$string.tooltips_window));
                return false;
            }
            if (id != R$id.btn_full_screen) {
                return false;
            }
            handleTooltips(motionEvent, this.mIBFullscreen, resources.getString(R$string.tooltips_fullscreen));
            return false;
        }

        public void takeScreenshot(final int i, View view) {
            if (view != null) {
                view.getLocationOnScreen(this.mScreenshotButtonLocation);
            }
            if (((MirrorPhonePanelController) Dependency.get(MirrorPhonePanelController.class)).requestHideInjectKeyWindow(this.mDisplayId)) {
                this.mMainHandler.postDelayed(new Runnable() { // from class: com.motorola.extendscreenshot.ScreenshotController$ExtendScreenshotSession$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$takeScreenshot$1(i);
                    }
                }, 150L);
            } else {
                takeScreenshot(i, false);
            }
        }

        public void takeScreenshot(int i, boolean z) {
            int i2;
            if (!z && this.mScreenshotWindow != null) {
                Log.i("ExtendScreenshotSession", "It's during screen shot.");
                return;
            }
            this.mCurrentUserId = getCurrentUserId();
            this.mScreenshotType = i;
            if (!z || this.mScreenshotWindow == null) {
                this.mCurrentFocusedWindowRect = this.mWindowManager.getFocusedWindowRect(this.mDisplayId);
            }
            if (ScreenshotController.DEBUG) {
                Log.d("ExtendScreenshotSession", "takeScreenshot mCurrentFocusedWindowRect = " + this.mCurrentFocusedWindowRect);
            }
            if (z || !((i2 = this.mScreenshotType) == 4 || i2 == 8 || i2 == 16)) {
                handleDirectScreenshot(this.mScreenshotType, z);
                return;
            }
            handleScreenshotTool();
            if (this.mScreenshotType == 16) {
                switchSelectorMode(0);
            }
        }

        public void unregisterReceiver() {
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        }
    }

    public ScreenshotController(Context context, TaskBarServiceProxy taskBarServiceProxy, Handler handler) {
        this.mContext = context;
        this.mMainHandler = handler;
        taskBarServiceProxy.addCallback((TaskBarServiceCallBack) this);
    }

    @Override // com.motorola.taskbar.TaskBarServiceCallBack
    public void onDisplayRemoved(int i) {
        ExtendScreenshotSession extendScreenshotSession;
        synchronized (this.mExtendScreenshotSessions) {
            try {
                if (this.mExtendScreenshotSessions.contains(i) && (extendScreenshotSession = (ExtendScreenshotSession) this.mExtendScreenshotSessions.get(i)) != null) {
                    extendScreenshotSession.unregisterReceiver();
                    this.mExtendScreenshotSessions.remove(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void takeScreenshot(int i, int i2) {
        if (i2 == 0) {
            return;
        }
        takeScreenshot(i, i2, null);
    }

    public void takeScreenshot(int i, int i2, View view) {
        ExtendScreenshotSession extendScreenshotSession;
        if (((DisplayManager) this.mContext.getSystemService("display")).getDisplay(i2) == null) {
            Log.e("ScreenshotController", "takeScreenshot error, display is null");
            return;
        }
        if (DEBUG) {
            Log.d("ScreenshotController", "takeScreenshot screenshotType = " + i + "; displayId = " + i2 + "; View = " + view);
        }
        synchronized (this.mExtendScreenshotSessions) {
            try {
                extendScreenshotSession = (ExtendScreenshotSession) this.mExtendScreenshotSessions.get(i2, null);
                if (extendScreenshotSession == null) {
                    extendScreenshotSession = new ExtendScreenshotSession(this.mContext, i2);
                    this.mExtendScreenshotSessions.put(i2, extendScreenshotSession);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        extendScreenshotSession.takeScreenshot(i, view);
    }
}
