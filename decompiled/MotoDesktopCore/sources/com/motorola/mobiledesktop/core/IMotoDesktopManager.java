package com.motorola.mobiledesktop.core;

import X.C;
import X.F;
import X.I;
import X.InterfaceC0010c0;
import X.InterfaceC0016f0;
import X.InterfaceC0019h;
import X.InterfaceC0020h0;
import X.InterfaceC0023j;
import X.InterfaceC0028l0;
import X.InterfaceC0029m;
import X.InterfaceC0034o0;
import X.InterfaceC0035p;
import X.InterfaceC0039s;
import X.InterfaceC0041u;
import X.InterfaceC0044x;
import X.InterfaceC0046z;
import X.L;
import X.O;
import X.Q;
import X.T;
import X.Z;
import X.r0;
import X.u0;
import a0.b;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.companion.AssociationInfo;
import android.companion.AssociationRequest;
import android.companion.virtual.VirtualDeviceParams;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.hardware.display.VirtualDisplayConfig;
import android.net.Uri;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.p2p.WifiP2pConfig;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.UserHandle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.Surface;
import android.view.WindowManager;
import com.motorola.mobiledesktop.core.bean.MotoActivityInfo;
import com.motorola.mobiledesktop.core.bean.MotoDisplay;
import com.motorola.mobiledesktop.core.bean.MotoDisplayMode;
import com.motorola.mobiledesktop.core.bean.MotoRunningTaskInfo;
import com.motorola.mobiledesktop.core.bean.MotoRunningTaskInfoNew;
import com.motorola.mobiledesktop.core.bean.MotoWifiDisplay;
import com.motorola.mobiledesktop.core.bean.NearbyShareDevice;
import com.motorola.mobiledesktop.core.tethering.InterfaceConfigurationParcel;
import com.motorola.mobiledesktop.core.tethering.RouteInfoParcel;
import com.motorola.mobiledesktop.core.uinput.AbsSetup;
import com.motorola.mobiledesktop.core.uinput.IClientToken;
import com.motorola.mobiledesktop.core.uinput.InputEvent;
import com.motorola.mobiledesktop.core.uinput.InputSetup;
import j0.c;
import j0.e;
import j0.g;
import j0.i;
import j0.k;
import j0.m;
import j0.o;
import j0.q;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface IMotoDesktopManager extends IInterface {
    public static final String DESCRIPTOR = "com.motorola.mobiledesktop.core.IMotoDesktopManager";

    void addActivityListenerVDM(int i2, int i3, b bVar);

    void addDesktopIcon(String str, int i2, int i3, int i4, String str2, PendingIntent pendingIntent);

    void addInterfaceToLocalNetwork(String str, List<RouteInfoParcel> list);

    void addPrimaryClipChangedListener(IRdpPimaryClipChangedListener iRdpPimaryClipChangedListener);

    void addUniqueIdAssociation(String str, int i2);

    int[] aidlFeature();

    int aidlVersion();

    void bindDragDropService();

    boolean bindPlatformService(ComponentName componentName, O o2);

    Bundle callContentProvider(String str, String str2, String str3, Bundle bundle);

    void cancelAlarm(PendingIntent pendingIntent);

    boolean cancelBondProcess(BluetoothDevice bluetoothDevice);

    void cancelConnectWifiDisplay();

    void closeVirtualDevice(int i2, int i3);

    void collapseStatusBar();

    void connectWifiDisplay(MotoWifiDisplay motoWifiDisplay);

    boolean createAudioRecord(int i2, int i3, int i4, int i5, int i6);

    boolean createAudioTrackForDialCall(int i2, int i3, int i4);

    boolean createAudioTrackForIpCall(int i2, int i3, int i4);

    boolean createAudioTrackForMic(int i2, int i3, int i4);

    boolean createBond(BluetoothDevice bluetoothDevice, int i2);

    int createVirtualDevice(int i2, VirtualDeviceParams virtualDeviceParams);

    int createVirtualDisplay(InterfaceC0028l0 interfaceC0028l0, String str, int i2, int i3, int i4, Surface surface, int i5);

    int createVirtualDisplayForMultiUser(InterfaceC0034o0 interfaceC0034o0, String str, int i2, int i3, int i4, Surface surface, int i5);

    int createVirtualDisplayForMultiUserVDM(InterfaceC0034o0 interfaceC0034o0, int i2, int i3, String str, int i4, int i5, int i6, Surface surface, int i7);

    int createVirtualDisplayVDM(int i2, int i3, VirtualDisplayConfig virtualDisplayConfig, Rect rect, r0 r0Var);

    int createVirtualDisplayWithProjection(InterfaceC0034o0 interfaceC0034o0, String str, int i2, int i3, int i4, Rect rect, Surface surface, int i5);

    int createVirtualDisplayWithoutTask(r0 r0Var, String str, int i2, int i3, int i4, Rect rect, Surface surface, int i5);

    int createVirtualDisplayWithoutTaskVDM(r0 r0Var, int i2, int i3, String str, int i4, int i5, int i6, Rect rect, Surface surface, int i7);

    void deregisterOnPointerPositionChangedListener(Q q2);

    void deregisterOnPointerStateChangedListener(T t2);

    boolean desktopCommitText(int i2, CharSequence charSequence);

    void dhcpRedirect(boolean z2, String str, int i2);

    void disconnectBluetooth(String str);

    void disconnectWifiDisplay();

    boolean enableBle(boolean z2);

    void enableRdpcdrom(boolean z2);

    boolean enableWiFi(boolean z2);

    void forceStopPackage(String str);

    void forceStopPackageAsUser(String str, int i2);

    void forgetWifiDisplay(String str);

    Bundle getAppRestartConfigerBundle(int i2);

    int getAudioRecordState();

    String getBluetoothAddress();

    int getCallState();

    Rect getCliCutoutSize();

    MotoWifiDisplay getConnectedWifiDisplay();

    MotoWifiDisplay getConnectingWifiDisplay();

    IBinder getCoreProxyBinder(String str);

    MotoDisplayMode getCurrentDisplayMode(int i2);

    boolean getCurrentUserLargePointerSetting();

    int getCurrentUserPointerSize();

    int getDefaultDisplayDensity(int i2);

    float[] getDesiredDisplayModeSpecsRange(int i2);

    List<String> getDesktopRestartModeByPackages(List<String> list);

    List<MotoDisplayMode> getDesktopSupportedModes(int i2);

    int getDisplayUiMode(int i2);

    int getFieldValueByReflect(String str, String str2);

    int getFocusDisplayId();

    Rect getFocusedWindowRect(int i2);

    Intent getIntent(PendingIntent pendingIntent);

    float[] getLastMousePosition(int i2);

    Intent getLaunchIntentForPackage(String str, int i2);

    int getLeaProfileStatus(BluetoothDevice bluetoothDevice);

    MotoDisplay getMotoDisplay(int i2);

    MotoRunningTaskInfoNew getMotoRunningTaskInfo(ActivityManager.RunningTaskInfo runningTaskInfo);

    List<MotoRunningTaskInfo> getMotoRunningTaskInfos();

    List<MotoRunningTaskInfo> getMotoRunningTaskInfosByDisplayId(int i2);

    float getMotoSettingFloatWithType(int i2, String str, float f2);

    int getMotoSettingIntegerWithType(int i2, String str, int i3);

    List<AssociationInfo> getMyAssociations();

    PackageInfo getPackageInfoAsUser(String str, int i2, int i3);

    String getPhysicalDisplayId(int i2);

    int[] getPhysicalDisplayInfo();

    int getPointSpeed();

    ClipData getPrimaryClip();

    ClipDescription getPrimaryClipDescription();

    int getR4PcPointerSize();

    List<MotoRunningTaskInfoNew> getRecentTasksByDisplayId(int i2, int i3);

    List<MotoRunningTaskInfoNew> getRunningTaskInfos();

    List<MotoRunningTaskInfoNew> getRunningTaskInfosByDisplayId(int i2);

    List<ActivityManager.RunningTaskInfo> getRunningTasks();

    String getSerial();

    SoftApConfiguration getSoftApConfiguration();

    List<MotoDisplayMode> getSupportedModes(int i2);

    MotoRunningTaskInfoNew getTopTaskInfo(int i2);

    ActivityManager.RunningTaskInfo getTopVisbleTask(int i2);

    long getUsbCurrentFunctions();

    long getUsbWebcamFunction();

    int getVirtualDisplayID(IVirtualDisplayCallback iVirtualDisplayCallback, String str, int i2, int i3, int i4, Surface surface, int i5);

    int getVirtualDisplayIdV2(InterfaceC0028l0 interfaceC0028l0, String str, int i2, int i3, int i4, Surface surface, int i5);

    int getVirtualDisplayIdV2VDM(InterfaceC0028l0 interfaceC0028l0, int i2, int i3, String str, int i4, int i5, int i6, Surface surface, int i7);

    void goToPermissionManageSetting(String str);

    void goToSleepDisplayGroup(int i2, long j2, int i3, int i4);

    void grantRuntimePermission(String str, String str2);

    void grantUriPermission(Uri uri);

    void injectInputEvent(MotionEvent motionEvent, int i2);

    void interfaceAddAddress(String str, String str2, int i2);

    boolean isAppCloneUser(int i2);

    boolean isDeviceOwnerMode();

    boolean isDisplayRotatable(int i2);

    boolean isDpDisplayModeSupported();

    boolean isFwkSupportDragDrop();

    boolean isLeaFeatureSupported(BluetoothDevice bluetoothDevice);

    boolean isMethodExists(String str, String str2);

    boolean isReadyForDisplay(int i2);

    boolean isRestrictedDisplay(int i2);

    boolean isRoleHeld(String str);

    boolean isTetheringStarted();

    boolean isVDMSupported();

    boolean isWifiDisplayConnecting();

    boolean isWifiDisplaySwitchOn();

    void launchActivity(Intent intent);

    void launchActivityWithBundle(Intent intent, Bundle bundle);

    void launchActivityWithDisplayId(Intent intent, int i2);

    String[] listInterfaces();

    void listenCallStateChange(InterfaceC0039s interfaceC0039s, int i2);

    void lockDevice();

    void moveRootTaskToDisplay(int i2, int i3);

    void notifyAuthorized(int i2, boolean z2);

    WindowManager.LayoutParams obtainPreventFocusWindowLayoutParams();

    void onReadyForDrop(long j2, ClipData clipData, int i2, int i3, int i4);

    void putIntToMotorolaSettings(String str, int i2);

    void putIntToMotorolaSystemSettings(String str, int i2);

    void putMotoSettingFloatWithType(int i2, String str, float f2);

    void putMotoSettingIntegerWithType(int i2, String str, int i3);

    void putMotoSettingString(String str, String str2);

    void putMotoSettingStringWithType(int i2, String str, String str2);

    void putSystemSettingIntWithType(int i2, String str, int i3);

    void putSystemSettingString(String str, String str2);

    int[] queryDesktopIcon(String str, int i2);

    List<ResolveInfo> queryIntentActivitiesAsUser(Intent intent, int i2, int i3);

    List<ResolveInfo> queryIntentActivitiesByPaging(Intent intent, int i2, int i3, int i4, int i5);

    List<MotoActivityInfo> queryMotoActivityInfoList(Intent intent, int i2, int i3);

    List<MotoWifiDisplay> queryWifiDisplayDevice();

    int readAudioRecordData(byte[] bArr, int i2, int i3);

    boolean reconnectBluetoothProfile(List<BluetoothDevice> list);

    boolean reconnectLeaProfile(List<BluetoothDevice> list);

    void registerContentObserver(Uri uri, boolean z2, int i2, InterfaceC0041u interfaceC0041u);

    void registerDesktopInputMethodListener(InterfaceC0046z interfaceC0046z);

    void registerDragDropCallback(C c2);

    void registerOnAudioPbPkgNameListChangeListener(InterfaceC0023j interfaceC0023j);

    void registerOnPointerPositionChangedListener(int i2, Q q2);

    void registerOnPointerStateChangedListener(int i2, T t2);

    void registerOnRdpPointerStateChangedListener(IRdpPointerStateChangedListener iRdpPointerStateChangedListener);

    int registerReceiver(IntentFilter intentFilter, Z z2);

    void registerSoftApCallback(InterfaceC0010c0 interfaceC0010c0);

    void releaseAudioRecord();

    void releaseAudioTrackForDialCall();

    void releaseAudioTrackForIpCall();

    void releaseAudioTrackForMic();

    void releaseDisplay(int i2);

    void releaseVirtualDisplay(int i2);

    void releaseVirtualDisplayVDM(int i2, int i3, int i4);

    void removeActivityListenerVDM(int i2, int i3, b bVar);

    boolean removeBond(BluetoothDevice bluetoothDevice);

    void removeDesktopIcon(String str, int i2);

    void removePrimaryClipChangedListener(IRdpPimaryClipChangedListener iRdpPimaryClipChangedListener);

    void removeRoutesFromLocalNetwork(List<RouteInfoParcel> list);

    void removeTask(int i2);

    void removeUniqueIdAssociation(String str);

    void resetUsbPort(int i2);

    void resizeVirtualDisplay(int i2, int i3, int i4);

    void resizeVirtualDisplayByDisplayId(int i2, int i3, int i4, int i5);

    void revokeRuntimePermission(String str, String str2);

    void rotateDisplay(int i2, int i3);

    void sendKeyEvent(KeyEvent keyEvent, int i2);

    void sendKeySync(KeyEvent keyEvent, int i2);

    void sendPendingIntent(PendingIntent pendingIntent);

    void sendPointerSync(MotionEvent motionEvent, int i2);

    void sendUriToNearbyPc(NearbyShareDevice nearbyShareDevice, String str);

    void setActivityStateChangedCallback(InterfaceC0019h interfaceC0019h);

    void setAudioSettingChangeCallback(InterfaceC0029m interfaceC0029m);

    void setAudioSettingCheckState(boolean z2);

    void setAudioSettingFocus(int i2);

    void setAudioSettingVolumeByIndex(int i2, int i3);

    void setClientBluetoothDeviceAddress(String str);

    void setClientDeviceInfo(String str, int i2);

    void setCompatibilityMode(String str, boolean z2);

    void setConnectState(boolean z2, boolean z3, String str);

    void setCorePermissionDialogCallback(InterfaceC0044x interfaceC0044x);

    void setCustomPointerIcon(PointerIcon pointerIcon, int i2, boolean z2);

    void setCustomPointerIconForFullScreen(PointerIcon pointerIcon, int i2, boolean z2, int i3);

    void setCustomPointerIconForFullScreenV(PointerIcon pointerIcon, int i2, boolean z2, int i3, int i4);

    void setDesktopRestartModeByPackages(List<String> list, List<String> list2);

    void setDisplayImePolicy(int i2, int i3);

    void setExactAndAllowWhileIdle(int i2, long j2, PendingIntent pendingIntent);

    void setFontScale(int i2, float f2);

    void setForcedDisplayDensity(int i2, int i3);

    void setGoIntent(int i2);

    void setInterfaceConfig(String str, InterfaceConfigurationParcel interfaceConfigurationParcel);

    void setIpForwardingEnabled(boolean z2);

    void setLocationEnabled();

    void setMotoTaskStackListener(InterfaceC0020h0 interfaceC0020h0);

    void setNcmFunction(boolean z2, String str, String str2, F f2);

    void setPackageChangeCallback(L l2);

    boolean setPairingConfirmation(BluetoothDevice bluetoothDevice, boolean z2);

    void setPointSpeed(int i2);

    void setPointerIconVisible(boolean z2, int i2);

    void setPrimaryClip(ClipData clipData);

    void setProjectionRect(int i2, Rect rect);

    void setRdpAudioActivatedState(boolean z2);

    void setRdpAudioRecordingCallState(boolean z2);

    void setRdpAudioStartRecord();

    void setRdpPointerPosition(int i2, int i3);

    void setReadyForConnectedType(int i2, boolean z2);

    void setShowPointer(int i2, boolean z2);

    void setShowRdpPointer(boolean z2);

    boolean setSoftApConfiguration(SoftApConfiguration softApConfiguration);

    void setTaskStackListener(InterfaceC0020h0 interfaceC0020h0);

    void setTetheringFunction(boolean z2, boolean z3, String str, String str2, F f2);

    void setUsbFunctions(long j2);

    void setUsbFunctionsAuthenticated(long j2, boolean z2);

    void setUserPreferredDisplayMode(int i2, int i3, int i4, float f2);

    void setVirtualDisplaySupportedModes(List<MotoDisplayMode> list);

    void setVirtualDisplaySupportedModesVDM(int i2, int i3, int i4, List<MotoDisplayMode> list);

    void setVirtualDisplaySurface(Surface surface);

    void setVirtualDisplaySurfaceByDisplayId(int i2, Surface surface);

    void setWifiDisplayCallback(u0 u0Var);

    void setWifiDisplaySwitch(boolean z2);

    boolean setWifiEnabled(boolean z2);

    void startAccessory(String[] strArr);

    void startApp(Intent intent, int i2);

    void startAppAsUser(Intent intent, int i2, int i3);

    void startAppWithActivityOptions(Intent intent, Bundle bundle, int i2);

    void startAudioRecording();

    void startNearbyShareSysWatcher(I i2);

    void startPlatformService(Intent intent);

    void startSettingsUsbDetailsActivity();

    void startShortcut(String str, String str2, Rect rect, Bundle bundle, UserHandle userHandle);

    boolean startTetheredHotspot(SoftApConfiguration softApConfiguration);

    void startTethering(TetheringRequest tetheringRequest, InterfaceC0016f0 interfaceC0016f0);

    void startTetheringByOemNetd(boolean z2, boolean z3, String[] strArr);

    boolean startToAssociate(AssociationRequest associationRequest, InterfaceC0035p interfaceC0035p);

    void startToUnassociate(int i2);

    void startWifiDisplayScan();

    void stopAudioRecording();

    void stopHotspot();

    void stopNearbyShareSysWatcher();

    boolean stopSoftAp();

    void stopTethering();

    void stopTetheringWithType(int i2);

    void stopWifiDisplayScan();

    void tetherInterface(String str);

    boolean uinputAbsSetup(int i2, AbsSetup absSetup);

    boolean uinputCreate(int i2, InputSetup inputSetup);

    boolean uinputDeviceClose(int i2);

    int uinputDeviceOpen(IClientToken iClientToken);

    boolean uinputDoIoctl(int i2, int i3, int i4);

    boolean uinputDoStrIoctl(int i2, int i3, String str);

    boolean uinputEmit(int i2, InputEvent[] inputEventArr);

    boolean uinputEnable(int i2, int i3, char[] cArr);

    boolean unRegisterReceiver(int i2);

    void unbindDragDropService();

    void unbindPlatformService(ComponentName componentName);

    void unregisterContentObserver(InterfaceC0041u interfaceC0041u);

    void unregisterDesktopInputMethodListener(InterfaceC0046z interfaceC0046z);

    void unregisterOnAudioPbPkgNameListChangeListener(InterfaceC0023j interfaceC0023j);

    void unregisterSoftApCallback();

    void untetherInterface(String str);

    void wakeUpDisplayGroup(int i2, long j2, int i3, String str);

    int wifiAddNetwork(WifiConfiguration wifiConfiguration);

    void wifiDirectCancelConnect(c cVar);

    void wifiDirectConnect(WifiP2pConfig wifiP2pConfig, c cVar);

    void wifiDirectCreateGroup(WifiP2pConfig wifiP2pConfig, c cVar);

    void wifiDirectDeletePersistentGroup(String str, c cVar);

    void wifiDirectDiscoverPeers(c cVar);

    void wifiDirectRegisterListener(q qVar, g gVar, i iVar, o oVar, e eVar, k kVar, m mVar);

    void wifiDirectRemoveGroup(c cVar);

    void wifiDirectRequestConnectionInfo(e eVar);

    void wifiDirectRequestDeviceInfo(g gVar);

    void wifiDirectRequestDiscoveryState(i iVar);

    void wifiDirectRequestGroupInfo(k kVar);

    void wifiDirectRequestNetworkInfo(m mVar);

    void wifiDirectRequestPeers(o oVar);

    void wifiDirectRequestState(q qVar);

    void wifiDirectStopPeerDiscovery(c cVar);

    boolean wifiDisableNetwork(int i2);

    boolean wifiDisconnect();

    boolean wifiEnableNetwork(int i2, boolean z2);

    List<WifiConfiguration> wifiGetCallerConfiguredNetworks();

    boolean wifiReconnect();

    boolean wifiRemoveNetwork(int i2);

    int writeAudioTrackDataForDialCall(byte[] bArr, int i2, int i3);

    int writeAudioTrackDataForIpCall(byte[] bArr, int i2, int i3);

    int writeAudioTrackDataForMic(byte[] bArr, int i2, int i3);
}
