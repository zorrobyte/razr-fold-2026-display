package com.android.systemui.dagger;

import android.content.Context;
import android.content.pm.ShortcutManager;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.ActivityIntentHelper_Factory;
import com.android.systemui.ActivityStarterDelegate_Factory;
import com.android.systemui.Dependency;
import com.android.systemui.Dependency_MembersInjector;
import com.android.systemui.SystemUIFactory;
import com.android.systemui.SystemUIFactory_ContextHolder_ProvideContextFactory;
import com.android.systemui.UiOffloadThread_Factory;
import com.android.systemui.broadcast.BroadcastSender_Factory;
import com.android.systemui.common.ui.ConfigurationState_Factory;
import com.android.systemui.displays.ActivityStarterProxy_Factory;
import com.android.systemui.dump.DumpManager_Factory;
import com.android.systemui.dump.LogBufferEulogizer_Factory;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease_Factory;
import com.android.systemui.flags.SystemPropertiesHelper_Factory;
import com.android.systemui.graphics.ImageLoader;
import com.android.systemui.graphics.ImageLoader_Factory;
import com.android.systemui.haptics.qs.QSLongPressEffect_Factory;
import com.android.systemui.log.LogBufferFactory_Factory;
import com.android.systemui.log.dagger.LogModule_ProvideMediaBrowserBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideMediaCarouselControllerBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideMediaViewLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideNotifInflationLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideNotifInteractionLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideNotificationHeadsUpLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideNotificationInterruptLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideNotificationRemoteInputLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideNotificationRenderLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideNotificationsLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideShadeLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideWakeLockLogFactory;
import com.android.systemui.media.controls.data.repository.MediaDataRepository_Factory;
import com.android.systemui.media.controls.data.repository.MediaFilterRepository_Factory;
import com.android.systemui.media.controls.domain.MediaDomainModule_Companion_ProvidesMediaDataManagerFactory;
import com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataFilterImpl_Factory;
import com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl_Factory;
import com.android.systemui.media.controls.domain.pipeline.MediaDataCombineLatest_Factory;
import com.android.systemui.media.controls.domain.pipeline.MediaDataFilterImpl_Factory;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor_Factory;
import com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager_Factory;
import com.android.systemui.media.controls.domain.pipeline.MediaSessionBasedFilter_Factory;
import com.android.systemui.media.controls.domain.pipeline.MediaTimeoutListener_Factory;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor_Factory;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor_Factory;
import com.android.systemui.media.controls.domain.pipeline.interactor.factory.MediaControlInteractorFactory_Impl;
import com.android.systemui.media.controls.domain.resume.MediaBrowserFactory_Factory;
import com.android.systemui.media.controls.domain.resume.MediaResumeListener_Factory;
import com.android.systemui.media.controls.domain.resume.ResumeMediaBrowserFactory_Factory;
import com.android.systemui.media.controls.domain.resume.ResumeMediaBrowserLogger_Factory;
import com.android.systemui.media.controls.ui.controller.MediaCarouselControllerLogger_Factory;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController_Factory;
import com.android.systemui.media.controls.ui.controller.MediaControlPanel_Factory;
import com.android.systemui.media.controls.ui.controller.MediaHierarchyManager_Factory;
import com.android.systemui.media.controls.ui.controller.MediaHostStatesManager_Factory;
import com.android.systemui.media.controls.ui.controller.MediaViewController_Factory;
import com.android.systemui.media.controls.ui.controller.MediaViewLogger_Factory;
import com.android.systemui.media.controls.ui.view.MediaHost_MediaHostStateHolder_Factory;
import com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel_Factory;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel_Factory;
import com.android.systemui.media.controls.util.MediaControllerFactory_Factory;
import com.android.systemui.media.controls.util.MediaFeatureFlag_Factory;
import com.android.systemui.media.controls.util.MediaFlags_Factory;
import com.android.systemui.media.controls.util.MediaUiEventLogger_Factory;
import com.android.systemui.media.dagger.MediaModule_ProvidesQSMediaHostFactory;
import com.android.systemui.qs.QSPanelController;
import com.android.systemui.qs.QSPanelController_Factory;
import com.android.systemui.qs.QSTileHost_Factory;
import com.android.systemui.qs.dagger.QSSceneComponent;
import com.android.systemui.qs.dagger.QSSceneModule_ProvidesQSUsingMediaPlayerFactory;
import com.android.systemui.qs.dagger.QSScopeModule_ProvideQSPanelFactory;
import com.android.systemui.qs.tileimpl.QSFactoryImpl_Factory;
import com.android.systemui.qs.tiles.AirplaneModeTile_Factory;
import com.android.systemui.qs.tiles.BluetoothTile_Factory;
import com.android.systemui.qs.tiles.CellularTile_Factory;
import com.android.systemui.qs.tiles.DndTile_Factory;
import com.android.systemui.qs.tiles.HotspotTile_Factory;
import com.android.systemui.qs.tiles.ScreenRecordTile_Factory;
import com.android.systemui.qs.tiles.ScreenshotTile_Factory;
import com.android.systemui.qs.tiles.WifiTile_Factory;
import com.android.systemui.qs.tiles.controller.AirplaneQSTileController_Factory;
import com.android.systemui.qs.tiles.controller.BluetoothQSTileController_Factory;
import com.android.systemui.qs.tiles.controller.CellQSTileController_Factory;
import com.android.systemui.qs.tiles.controller.DndQSTileController_Factory;
import com.android.systemui.qs.tiles.controller.HotspotQSTileController_Factory;
import com.android.systemui.qs.tiles.controller.ScreenRecordQSTileController_Factory;
import com.android.systemui.qs.tiles.controller.WifiQSTileController_Factory;
import com.android.systemui.settings.MultiUserUtilsModule_ProvideUserTrackerFactory;
import com.android.systemui.statusbar.NotificationClickNotifier_Factory;
import com.android.systemui.statusbar.NotificationInteractionTracker_Factory;
import com.android.systemui.statusbar.NotificationListener_Factory;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl_Factory;
import com.android.systemui.statusbar.NotificationRemoteInputManager_Factory;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.RemoteInputNotificationRebuilder_Factory;
import com.android.systemui.statusbar.data.repository.RemoteInputRepositoryImpl_Factory;
import com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor_Factory;
import com.android.systemui.statusbar.notification.AnimatedImageNotificationManager_Factory;
import com.android.systemui.statusbar.notification.AssistantFeedbackController_Factory;
import com.android.systemui.statusbar.notification.ColorUpdateLogger_Factory;
import com.android.systemui.statusbar.notification.ConversationNotificationManager_Factory;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor_Factory;
import com.android.systemui.statusbar.notification.DynamicPrivacyController_Factory;
import com.android.systemui.statusbar.notification.NotifPipelineFlags_Factory;
import com.android.systemui.statusbar.notification.NotificationClickerLogger_Factory;
import com.android.systemui.statusbar.notification.NotificationClicker_Builder_Factory;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager_Factory;
import com.android.systemui.statusbar.notification.RemoteInputControllerLogger_Factory;
import com.android.systemui.statusbar.notification.collection.DesktopUnreadNotificationMonitor_Factory;
import com.android.systemui.statusbar.notification.collection.NotifCollection_Factory;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl_Factory;
import com.android.systemui.statusbar.notification.collection.NotifInflaterLogger_Factory;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl_Factory;
import com.android.systemui.statusbar.notification.collection.NotifPipelineChoreographerImpl_Factory;
import com.android.systemui.statusbar.notification.collection.NotifPipeline_Factory;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder_Factory;
import com.android.systemui.statusbar.notification.collection.TargetSdkResolver_Factory;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger_Factory;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.ColorizedFgsCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.DataStoreCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.DeviceProvisionedCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.DismissibilityCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.GroupCountCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.HideLocallyDismissedNotifsCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.HideNotifsForOtherUsersCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.MediaCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinatorsImpl_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.NotificationStatsLoggerCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.RowAlertTimeCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.RowAppearanceCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinatorLogger_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.StackCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsModule_NotifCoordinatorsFactory;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsSubcomponent;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl_Factory;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider_Factory;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl_Factory;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderLogger_Factory;
import com.android.systemui.statusbar.notification.collection.inflation.OnUserInteractionCallbackImpl_Factory;
import com.android.systemui.statusbar.notification.collection.init.NotifPipelineInitializer_Factory;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger_Factory;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger_Factory;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider_Factory;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider_Factory;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProviderImpl_Factory;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl_Factory;
import com.android.systemui.statusbar.notification.collection.provider.SectionHeaderVisibilityProvider_Factory;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider_Factory;
import com.android.systemui.statusbar.notification.collection.provider.VisibilityLocationProviderDelegator_Factory;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider_Factory;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl_Factory;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl_Factory;
import com.android.systemui.statusbar.notification.collection.render.MediaContainerController_Factory;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.NodeSpecBuilderLogger_Factory;
import com.android.systemui.statusbar.notification.collection.render.NotifViewBarn_Factory;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager_Factory;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl_Factory;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger_Factory;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManagerFactory_Impl;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManager_Factory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesAlertingHeaderControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesAlertingHeaderNodeControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesAlertingHeaderSubcomponentFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesIncomingHeaderControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesIncomingHeaderNodeControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesIncomingHeaderSubcomponentFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesPeopleHeaderControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesPeopleHeaderNodeControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesSilentHeaderControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesSilentHeaderNodeControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesSilentHeaderSubcomponentFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationStatsLoggerModule_Companion_ProvideLegacyLoggerOptionalFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationStatsLoggerModule_Companion_ProvideRowStatsLoggerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationStatsLoggerModule_Companion_ProvideStatsLoggerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationStatsLoggerModule_Companion_ProvideViewModelFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationsModule_ProvideListContainerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationsModule_ProvideNotificationPanelLoggerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationsModule_ProvideNotificationsControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationsModule_ProvideVisualInterruptionDecisionProviderFactory;
import com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationListRepository_Factory;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor_Factory;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor_Factory;
import com.android.systemui.statusbar.notification.domain.interactor.RenderNotificationListInteractor_Factory;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor_Factory;
import com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModelModule_ProvideOptionalFactory;
import com.android.systemui.statusbar.notification.icon.IconBuilder_Factory;
import com.android.systemui.statusbar.notification.icon.IconManager_Factory;
import com.android.systemui.statusbar.notification.init.NotificationsControllerImpl_Factory;
import com.android.systemui.statusbar.notification.init.NotificationsControllerStub_Factory;
import com.android.systemui.statusbar.notification.interruption.AvalancheProvider_Factory;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger_Factory;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder_Factory;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl_Factory;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger_Factory;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl_Factory;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionLogger_Factory;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProviderImpl_Factory;
import com.android.systemui.statusbar.notification.logging.NotificationLogger_ExpansionStateLogger_Factory;
import com.android.systemui.statusbar.notification.people.NotificationPersonExtractorPluginBoundary_Factory;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl_Factory;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationViewController_Factory;
import com.android.systemui.statusbar.notification.row.BigPictureIconManager;
import com.android.systemui.statusbar.notification.row.BigPictureLayoutInflaterFactory_Factory;
import com.android.systemui.statusbar.notification.row.BigPictureStatsManager;
import com.android.systemui.statusbar.notification.row.BigPictureStatsManager_Factory;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController_Factory;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController_Factory;
import com.android.systemui.statusbar.notification.row.ExpandableOutlineViewController_Factory;
import com.android.systemui.statusbar.notification.row.ExpandableViewController_Factory;
import com.android.systemui.statusbar.notification.row.HeadsUpStyleProviderImpl_Factory;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineInitializer_Factory;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger_Factory;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline_Factory;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager_Factory;
import com.android.systemui.statusbar.notification.row.NotifLayoutInflaterFactory_Factory;
import com.android.systemui.statusbar.notification.row.NotifLayoutInflaterFactory_Provider_Impl;
import com.android.systemui.statusbar.notification.row.NotifRemoteViewCacheImpl_Factory;
import com.android.systemui.statusbar.notification.row.NotifRemoteViewsFactoryContainerImpl_Factory;
import com.android.systemui.statusbar.notification.row.NotificationContentInflaterLogger_Factory;
import com.android.systemui.statusbar.notification.row.NotificationContentInflater_Factory;
import com.android.systemui.statusbar.notification.row.NotificationEntryProcessorFactoryLooperImpl_Factory;
import com.android.systemui.statusbar.notification.row.NotificationOptimizedLinearLayoutFactory_Factory;
import com.android.systemui.statusbar.notification.row.NotificationRowLogger_Factory;
import com.android.systemui.statusbar.notification.row.NotificationSettingsController_Factory;
import com.android.systemui.statusbar.notification.row.NotificationViewFlipperFactory_Factory;
import com.android.systemui.statusbar.notification.row.PrecomputedTextViewFactory_Factory;
import com.android.systemui.statusbar.notification.row.RowContentBindStageLogger_Factory;
import com.android.systemui.statusbar.notification.row.RowContentBindStage_Factory;
import com.android.systemui.statusbar.notification.row.RowInflaterTaskLogger_Factory;
import com.android.systemui.statusbar.notification.row.RowInflaterTask_Factory;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideAppNameFactory;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideNotificationKeyFactory;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.NotificationViewFlipperViewModel_Factory;
import com.android.systemui.statusbar.notification.stack.AmbientState_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainerLogger_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationSectionsManager_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper_Builder_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationTargetsHelper_Factory;
import com.android.systemui.statusbar.notification.stack.StackStateLogger_Factory;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackInteractor_Factory;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl_Factory;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder_Factory;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel_Factory;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel_Factory;
import com.android.systemui.statusbar.notification.ui.viewbinder.HeadsUpNotificationViewBinder_Factory;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone_Factory;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil_Factory;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter_Factory;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter_Factory;
import com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback_Factory;
import com.android.systemui.statusbar.policy.AvalancheController_Factory;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl_Factory;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl_Factory;
import com.android.systemui.statusbar.policy.RemoteInputUriController_Factory;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.statusbar.policy.RemoteInputViewController;
import com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl_Factory;
import com.android.systemui.statusbar.policy.SmartActionInflaterImpl_Factory;
import com.android.systemui.statusbar.policy.SmartReplyConstants_Factory;
import com.android.systemui.statusbar.policy.SmartReplyInflaterImpl_Factory;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl_Factory;
import com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor_Factory;
import com.android.systemui.util.DeviceConfigProxy_Factory;
import com.android.systemui.util.concurrency.ConcurrencyModule_ProvideBackgroundDelayableExecutorFactory;
import com.android.systemui.util.concurrency.ConcurrencyModule_ProvideBackgroundExecutorFactory;
import com.android.systemui.util.concurrency.ConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory;
import com.android.systemui.util.concurrency.ConcurrencyModule_ProvideBgHandlerFactory;
import com.android.systemui.util.concurrency.ConcurrencyModule_ProvideBgLooperFactory;
import com.android.systemui.util.concurrency.ConcurrencyModule_ProvideHandlerFactory;
import com.android.systemui.util.concurrency.ConcurrencyModule_ProvideMainDelayableExecutorFactory;
import com.android.systemui.util.concurrency.ConcurrencyModule_ProvideMainExecutorFactory;
import com.android.systemui.util.concurrency.ConcurrencyModule_ProvideMainHandlerFactory;
import com.android.systemui.util.concurrency.ConcurrencyModule_ProvideMainLooperFactory;
import com.android.systemui.util.concurrency.ConcurrencyModule_ProvideNotifInflationExecutorFactory;
import com.android.systemui.util.concurrency.ConcurrencyModule_ProvideNotifInflationLooperFactory;
import com.android.systemui.util.concurrency.ConcurrencyModule_ProvideUiBackgroundExecutorFactory;
import com.android.systemui.util.concurrency.ThreadFactoryImpl_Factory;
import com.android.systemui.util.kotlin.GlobalCoroutinesModule;
import com.android.systemui.util.kotlin.GlobalCoroutinesModule_ApplicationScopeFactory;
import com.android.systemui.util.kotlin.GlobalCoroutinesModule_MainCoroutineContextFactory;
import com.android.systemui.util.kotlin.GlobalCoroutinesModule_MainDispatcherFactory;
import com.android.systemui.util.kotlin.GlobalCoroutinesModule_TracingCoroutineContextFactory;
import com.android.systemui.util.kotlin.JavaAdapter_Factory;
import com.android.systemui.util.kotlin.SysUICoroutinesModule;
import com.android.systemui.util.kotlin.SysUICoroutinesModule_BgCoroutineContextFactory;
import com.android.systemui.util.kotlin.SysUICoroutinesModule_BgDispatcherFactory;
import com.android.systemui.util.settings.GlobalSettingsImpl_Factory;
import com.android.systemui.util.settings.SecureSettingsImpl_Factory;
import com.android.systemui.util.settings.SystemSettingsImpl_Factory;
import com.android.systemui.util.time.SystemClockImpl_Factory;
import com.android.systemui.util.wakelock.WakeLockLogger_Factory;
import com.android.systemui.util.wakelock.WakeLock_Builder_Factory;
import com.motorola.extendscreenshot.ScreenshotController_Factory;
import com.motorola.glass.GlassesMonitor_Factory;
import com.motorola.systemui.desktop.overwrites.statusbar.notification.DesktopHeadsUpController_Factory;
import com.motorola.taskbar.MotoFeature_Factory;
import com.motorola.taskbar.TaskBarChildUserServerService;
import com.motorola.taskbar.TaskBarChildUserServerService_MembersInjector;
import com.motorola.taskbar.TaskBarService;
import com.motorola.taskbar.TaskBarServiceProxy;
import com.motorola.taskbar.TaskBarServiceProxy_Factory;
import com.motorola.taskbar.TaskBarService_MembersInjector;
import com.motorola.taskbar.WifiStatusMonitor_Factory;
import com.motorola.taskbar.bar.ExternalDisplayModeManager_Factory;
import com.motorola.taskbar.bar.ExternalDisplayTutorialManager_Factory;
import com.motorola.taskbar.bar.MirrorPhonePanelController_Factory;
import com.motorola.taskbar.bar.QSNotificationPanelController_Factory;
import com.motorola.taskbar.bar.TaskBarController_Factory;
import com.motorola.taskbar.media.DesktopFocusAudioOutputMonitor_Factory;
import com.motorola.taskbar.model.DisplayConfigurationController_Factory;
import com.motorola.taskbar.model.DisplayWindowManager_Factory;
import com.motorola.taskbar.model.HardwareDisplayControllerImpl_Factory;
import com.motorola.taskbar.model.NavIconController_Factory;
import com.motorola.taskbar.model.SurfaceBlurController;
import com.motorola.taskbar.qsnotification.QsConfigurationControllerImpl_Factory;
import com.motorola.taskbar.qsnotification.QsNotificationCentralSurfaces;
import com.motorola.taskbar.qsnotification.QsNotificationCentralSurfaces_Factory;
import com.motorola.taskbar.qsnotification.QsNotificationComponentStarter_Factory;
import com.motorola.taskbar.qsnotification.QsNotificationDependency;
import com.motorola.taskbar.qsnotification.QsNotificationDependency_MembersInjector;
import com.motorola.taskbar.qsnotification.QsNotificationTooltipPopupManager_Factory;
import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent;
import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent_NotificationExtraModule_ProvideNotificationMediaManagerFactory;
import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent_NotificationExtraModule_ProvideSmartReplyControllerFactory;
import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent_NotificationExtraModule_ProviderLayoutInflaterFactory;
import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent_QsNotificationBaseModule_ProvideContextFactory;
import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent_QsNotificationBaseModule_ProvideDisplayIdFactory;
import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent_QsNotificationBaseModule_ProvideQsConfigurationControllerFactory;
import com.motorola.taskbar.recent.RecentsActivity;
import com.motorola.taskbar.recent.RecentsActivity_MembersInjector;
import com.motorola.taskbar.recent.RecentsController;
import com.motorola.taskbar.recent.RecentsController_Factory;
import com.motorola.taskbar.recent.TaskController_Factory;
import com.motorola.taskbar.recent.TaskSyncreticController_Factory;
import com.motorola.taskbar.shortcut.ShortcutKeyDispatcher_Factory;
import com.motorola.taskbar.shortcut.record.RecordManager_Factory;
import com.motorola.trackpad.ReadyForProxy_Factory;
import com.motorola.trackpad.TrackpadGestureHandler_Factory;
import dagger.internal.DelegateFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.InstanceFactory;
import dagger.internal.MapProviderFactory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: loaded from: classes.dex */
public abstract class DaggerSystemUIRootComponent {

    public final class Builder {
        private SystemUIFactory.ContextHolder contextHolder;
        private DependencyProvider dependencyProvider;
        private FrameworkServicesModule frameworkServicesModule;
        private GlobalCoroutinesModule globalCoroutinesModule;
        private SharedLibraryModule sharedLibraryModule;
        private SysUICoroutinesModule sysUICoroutinesModule;

        private Builder() {
        }

        public SystemUIRootComponent build() {
            if (this.frameworkServicesModule == null) {
                this.frameworkServicesModule = new FrameworkServicesModule();
            }
            if (this.globalCoroutinesModule == null) {
                this.globalCoroutinesModule = new GlobalCoroutinesModule();
            }
            if (this.dependencyProvider == null) {
                this.dependencyProvider = new DependencyProvider();
            }
            if (this.sharedLibraryModule == null) {
                this.sharedLibraryModule = new SharedLibraryModule();
            }
            Preconditions.checkBuilderRequirement(this.contextHolder, SystemUIFactory.ContextHolder.class);
            if (this.sysUICoroutinesModule == null) {
                this.sysUICoroutinesModule = new SysUICoroutinesModule();
            }
            return new SystemUIRootComponentImpl(this.frameworkServicesModule, this.globalCoroutinesModule, this.dependencyProvider, this.sharedLibraryModule, this.contextHolder, this.sysUICoroutinesModule);
        }

        public Builder contextHolder(SystemUIFactory.ContextHolder contextHolder) {
            contextHolder.getClass();
            this.contextHolder = contextHolder;
            return this;
        }

        public Builder dependencyProvider(DependencyProvider dependencyProvider) {
            dependencyProvider.getClass();
            this.dependencyProvider = dependencyProvider;
            return this;
        }
    }

    final class CoordinatorsSubcomponentFactory implements CoordinatorsSubcomponent.Factory {
        private final QsNotificationComponentImpl qsNotificationComponentImpl;
        private final SystemUIRootComponentImpl systemUIRootComponentImpl;

        private CoordinatorsSubcomponentFactory(SystemUIRootComponentImpl systemUIRootComponentImpl, QsNotificationComponentImpl qsNotificationComponentImpl) {
            this.systemUIRootComponentImpl = systemUIRootComponentImpl;
            this.qsNotificationComponentImpl = qsNotificationComponentImpl;
        }

        @Override // com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsSubcomponent.Factory
        public CoordinatorsSubcomponent create() {
            return new CoordinatorsSubcomponentImpl(this.systemUIRootComponentImpl, this.qsNotificationComponentImpl);
        }
    }

    final class CoordinatorsSubcomponentImpl implements CoordinatorsSubcomponent {
        private Provider colorizedFgsCoordinatorProvider;
        private Provider conversationCoordinatorProvider;
        private final CoordinatorsSubcomponentImpl coordinatorsSubcomponentImpl;
        private Provider dataStoreCoordinatorProvider;
        private Provider deviceProvisionedCoordinatorProvider;
        private Provider dismissibilityCoordinatorProvider;
        private Provider groupCountCoordinatorProvider;
        private Provider groupWhenCoordinatorProvider;
        private Provider headsUpCoordinatorLoggerProvider;
        private Provider headsUpCoordinatorProvider;
        private Provider hideLocallyDismissedNotifsCoordinatorProvider;
        private Provider hideNotifsForOtherUsersCoordinatorProvider;
        private Provider mediaCoordinatorProvider;
        private Provider notifCoordinatorsImplProvider;
        private Provider notificationStatsLoggerCoordinatorProvider;
        private Provider preparationCoordinatorLoggerProvider;
        private Provider preparationCoordinatorProvider;
        private final QsNotificationComponentImpl qsNotificationComponentImpl;
        private Provider rankingCoordinatorProvider;
        private Provider remoteInputCoordinatorProvider;
        private Provider renderNotificationListInteractorProvider;
        private Provider rowAlertTimeCoordinatorProvider;
        private Provider rowAppearanceCoordinatorProvider;
        private Provider sensitiveContentCoordinatorImplProvider;
        private Provider stackCoordinatorProvider;
        private final SystemUIRootComponentImpl systemUIRootComponentImpl;
        private Provider viewConfigCoordinatorProvider;

        private CoordinatorsSubcomponentImpl(SystemUIRootComponentImpl systemUIRootComponentImpl, QsNotificationComponentImpl qsNotificationComponentImpl) {
            this.coordinatorsSubcomponentImpl = this;
            this.systemUIRootComponentImpl = systemUIRootComponentImpl;
            this.qsNotificationComponentImpl = qsNotificationComponentImpl;
            initialize();
        }

        private void initialize() {
            this.dataStoreCoordinatorProvider = DoubleCheck.provider((Provider) DataStoreCoordinator_Factory.create(this.qsNotificationComponentImpl.notifLiveDataStoreImplProvider));
            this.hideLocallyDismissedNotifsCoordinatorProvider = DoubleCheck.provider((Provider) HideLocallyDismissedNotifsCoordinator_Factory.create());
            this.hideNotifsForOtherUsersCoordinatorProvider = DoubleCheck.provider((Provider) HideNotifsForOtherUsersCoordinator_Factory.create(this.qsNotificationComponentImpl.notificationLockscreenUserManagerImplProvider));
            this.rankingCoordinatorProvider = DoubleCheck.provider((Provider) RankingCoordinator_Factory.create(this.qsNotificationComponentImpl.highPriorityProvider, this.qsNotificationComponentImpl.providesAlertingHeaderNodeControllerProvider, this.qsNotificationComponentImpl.providesSilentHeaderControllerProvider, this.qsNotificationComponentImpl.providesSilentHeaderNodeControllerProvider));
            this.colorizedFgsCoordinatorProvider = DoubleCheck.provider((Provider) ColorizedFgsCoordinator_Factory.create());
            this.deviceProvisionedCoordinatorProvider = DoubleCheck.provider((Provider) DeviceProvisionedCoordinator_Factory.create(this.systemUIRootComponentImpl.bindDeviceProvisionedControllerProvider, this.systemUIRootComponentImpl.provideIPackageManagerProvider));
            HeadsUpCoordinatorLogger_Factory headsUpCoordinatorLogger_FactoryCreate = HeadsUpCoordinatorLogger_Factory.create(this.systemUIRootComponentImpl.provideNotificationHeadsUpLogBufferProvider);
            this.headsUpCoordinatorLoggerProvider = headsUpCoordinatorLogger_FactoryCreate;
            this.headsUpCoordinatorProvider = DoubleCheck.provider((Provider) HeadsUpCoordinator_Factory.create(headsUpCoordinatorLogger_FactoryCreate, this.systemUIRootComponentImpl.bindSystemClockProvider, this.qsNotificationComponentImpl.desktopHeadsUpControllerProvider, this.qsNotificationComponentImpl.headsUpViewBinderProvider, this.qsNotificationComponentImpl.provideVisualInterruptionDecisionProvider, this.qsNotificationComponentImpl.notificationRemoteInputManagerProvider, this.qsNotificationComponentImpl.launchFullScreenIntentProvider, NotifPipelineFlags_Factory.create(), this.qsNotificationComponentImpl.providesIncomingHeaderNodeControllerProvider, this.systemUIRootComponentImpl.provideMainDelayableExecutorProvider));
            this.conversationCoordinatorProvider = DoubleCheck.provider((Provider) ConversationCoordinator_Factory.create(this.qsNotificationComponentImpl.peopleNotificationIdentifierImplProvider, this.qsNotificationComponentImpl.iconManagerProvider, this.qsNotificationComponentImpl.highPriorityProvider, this.qsNotificationComponentImpl.providesPeopleHeaderNodeControllerProvider));
            this.groupCountCoordinatorProvider = DoubleCheck.provider((Provider) GroupCountCoordinator_Factory.create());
            this.groupWhenCoordinatorProvider = DoubleCheck.provider((Provider) GroupWhenCoordinator_Factory.create(this.systemUIRootComponentImpl.provideMainDelayableExecutorProvider, this.systemUIRootComponentImpl.bindSystemClockProvider));
            this.mediaCoordinatorProvider = DoubleCheck.provider((Provider) MediaCoordinator_Factory.create(this.qsNotificationComponentImpl.mediaFeatureFlagProvider, this.systemUIRootComponentImpl.provideIStatusBarServiceProvider, this.qsNotificationComponentImpl.iconManagerProvider));
            PreparationCoordinatorLogger_Factory preparationCoordinatorLogger_FactoryCreate = PreparationCoordinatorLogger_Factory.create(this.systemUIRootComponentImpl.provideNotificationsLogBufferProvider);
            this.preparationCoordinatorLoggerProvider = preparationCoordinatorLogger_FactoryCreate;
            this.preparationCoordinatorProvider = DoubleCheck.provider((Provider) PreparationCoordinator_Factory.create(preparationCoordinatorLogger_FactoryCreate, this.qsNotificationComponentImpl.notifInflaterImplProvider, this.qsNotificationComponentImpl.notifInflationErrorManagerProvider, this.qsNotificationComponentImpl.notifViewBarnProvider, this.qsNotificationComponentImpl.notifUiAdjustmentProvider, this.systemUIRootComponentImpl.provideIStatusBarServiceProvider, this.qsNotificationComponentImpl.bindEventManagerImplProvider));
            this.remoteInputCoordinatorProvider = DoubleCheck.provider((Provider) RemoteInputCoordinator_Factory.create(this.systemUIRootComponentImpl.dumpManagerProvider, this.qsNotificationComponentImpl.remoteInputNotificationRebuilderProvider, this.qsNotificationComponentImpl.notificationRemoteInputManagerProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, this.qsNotificationComponentImpl.provideSmartReplyControllerProvider));
            this.rowAlertTimeCoordinatorProvider = DoubleCheck.provider((Provider) RowAlertTimeCoordinator_Factory.create());
            this.rowAppearanceCoordinatorProvider = DoubleCheck.provider((Provider) RowAppearanceCoordinator_Factory.create(this.qsNotificationComponentImpl.provideContextProvider, this.systemUIRootComponentImpl.assistantFeedbackControllerProvider, this.qsNotificationComponentImpl.sectionStyleProvider));
            this.renderNotificationListInteractorProvider = RenderNotificationListInteractor_Factory.create(this.qsNotificationComponentImpl.activeNotificationListRepositoryProvider, this.qsNotificationComponentImpl.sectionStyleProvider);
            this.stackCoordinatorProvider = DoubleCheck.provider((Provider) StackCoordinator_Factory.create(this.qsNotificationComponentImpl.groupExpansionManagerImplProvider, this.renderNotificationListInteractorProvider, this.qsNotificationComponentImpl.activeNotificationsInteractorProvider, this.systemUIRootComponentImpl.sensitiveNotificationProtectionControllerImplProvider));
            this.viewConfigCoordinatorProvider = DoubleCheck.provider((Provider) ViewConfigCoordinator_Factory.create(this.qsNotificationComponentImpl.provideQsConfigurationControllerProvider, this.qsNotificationComponentImpl.notificationLockscreenUserManagerImplProvider, this.systemUIRootComponentImpl.colorUpdateLoggerProvider));
            this.sensitiveContentCoordinatorImplProvider = DoubleCheck.provider((Provider) SensitiveContentCoordinatorImpl_Factory.create(this.systemUIRootComponentImpl.dynamicPrivacyControllerProvider, this.qsNotificationComponentImpl.notificationLockscreenUserManagerImplProvider, this.systemUIRootComponentImpl.selectedUserInteractorProvider, this.systemUIRootComponentImpl.sensitiveNotificationProtectionControllerImplProvider));
            this.dismissibilityCoordinatorProvider = DoubleCheck.provider((Provider) DismissibilityCoordinator_Factory.create(this.qsNotificationComponentImpl.notificationDismissibilityProviderImplProvider));
            this.notificationStatsLoggerCoordinatorProvider = DoubleCheck.provider((Provider) NotificationStatsLoggerCoordinator_Factory.create(this.qsNotificationComponentImpl.provideStatsLoggerProvider));
            this.notifCoordinatorsImplProvider = DoubleCheck.provider((Provider) NotifCoordinatorsImpl_Factory.create(this.qsNotificationComponentImpl.sectionStyleProvider, this.dataStoreCoordinatorProvider, this.hideLocallyDismissedNotifsCoordinatorProvider, this.hideNotifsForOtherUsersCoordinatorProvider, this.rankingCoordinatorProvider, this.colorizedFgsCoordinatorProvider, this.deviceProvisionedCoordinatorProvider, this.headsUpCoordinatorProvider, this.conversationCoordinatorProvider, this.groupCountCoordinatorProvider, this.groupWhenCoordinatorProvider, this.mediaCoordinatorProvider, this.preparationCoordinatorProvider, this.remoteInputCoordinatorProvider, this.rowAlertTimeCoordinatorProvider, this.rowAppearanceCoordinatorProvider, this.stackCoordinatorProvider, this.qsNotificationComponentImpl.shadeEventCoordinatorProvider, this.viewConfigCoordinatorProvider, this.qsNotificationComponentImpl.visualStabilityCoordinatorProvider, this.sensitiveContentCoordinatorImplProvider, this.dismissibilityCoordinatorProvider, this.notificationStatsLoggerCoordinatorProvider));
        }

        @Override // com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsSubcomponent
        public NotifCoordinators getNotifCoordinators() {
            return (NotifCoordinators) this.notifCoordinatorsImplProvider.get();
        }
    }

    final class D_DependencyInjectorImpl implements Dependency.DependencyInjector {
        private final D_DependencyInjectorImpl d_DependencyInjectorImpl;
        private final SystemUIRootComponentImpl systemUIRootComponentImpl;

        private D_DependencyInjectorImpl(SystemUIRootComponentImpl systemUIRootComponentImpl) {
            this.d_DependencyInjectorImpl = this;
            this.systemUIRootComponentImpl = systemUIRootComponentImpl;
        }

        private Dependency injectDependency(Dependency dependency) {
            Dependency_MembersInjector.injectMBgLooper(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.provideBgLooperProvider));
            Dependency_MembersInjector.injectMBgHandler(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.provideBgHandlerProvider));
            Dependency_MembersInjector.injectMMainLooper(dependency, DoubleCheck.lazy((Provider) ConcurrencyModule_ProvideMainLooperFactory.create()));
            Dependency_MembersInjector.injectMMainHandler(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.provideMainHandlerProvider));
            Dependency_MembersInjector.injectMTimeTickHandler(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.provideTimeTickHandlerProvider));
            Dependency_MembersInjector.injectMAlarmManager(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.provideAlarmManagerProvider));
            Dependency_MembersInjector.injectMTaskBarController(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.taskBarControllerProvider));
            Dependency_MembersInjector.injectMTaskController(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.taskControllerProvider));
            Dependency_MembersInjector.injectMTaskSyncreticController(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.taskSyncreticControllerProvider));
            Dependency_MembersInjector.injectMDeviceProvisionedController(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.bindDeviceProvisionedControllerProvider));
            Dependency_MembersInjector.injectMTaskBarServiceProxy(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.taskBarServiceProxyProvider));
            Dependency_MembersInjector.injectMWifiStatusMonitor(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.wifiStatusMonitorProvider));
            Dependency_MembersInjector.injectMMotoFeature(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.motoFeatureProvider));
            Dependency_MembersInjector.injectMActivityStarter(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.activityStarterDelegateProvider));
            Dependency_MembersInjector.injectMBroadcastDispatcher(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.providesBroadcastDispatcherProvider));
            Dependency_MembersInjector.injectMQSNotificationPanelController(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.qSNotificationPanelControllerProvider));
            Dependency_MembersInjector.injectMShortcutKeyDispatcher(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.shortcutKeyDispatcherProvider));
            Dependency_MembersInjector.injectMMirrorPhonePanelController(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.mirrorPhonePanelControllerProvider));
            Dependency_MembersInjector.injectMRecentsController(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.recentsControllerProvider));
            Dependency_MembersInjector.injectMScreenshotController(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.screenshotControllerProvider));
            Dependency_MembersInjector.injectMIWindowManager(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.provideIWindowManagerProvider));
            Dependency_MembersInjector.injectMIStatusBarService(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.provideIStatusBarServiceProvider));
            Dependency_MembersInjector.injectMDisplayMetrics(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.provideDisplayMetricsProvider));
            Dependency_MembersInjector.injectMINotificationManager(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.provideINotificationManagerProvider));
            Dependency_MembersInjector.injectMWallpaperManager(dependency, DoubleCheck.lazy((Provider) FrameworkServicesModule_ProvideIWallPaperManagerFactory.create()));
            Dependency_MembersInjector.injectMConfigurationController(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.provideConfigurationControllerProvider));
            Dependency_MembersInjector.injectMExternalDisplayModeManager(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.externalDisplayModeManagerProvider));
            Dependency_MembersInjector.injectMExternalDisplayTutorialManager(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.externalDisplayTutorialManagerProvider));
            Dependency_MembersInjector.injectMHardwareDisplayController(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.hardwareDisplayControllerImplProvider));
            Dependency_MembersInjector.injectMDisplayWindowManager(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.displayWindowManagerProvider));
            Dependency_MembersInjector.injectMNavIconController(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.navIconControllerProvider));
            Dependency_MembersInjector.injectMRecordManager(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.recordManagerProvider));
            Dependency_MembersInjector.injectMGlassesMonitor(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.glassesMonitorProvider));
            Dependency_MembersInjector.injectMReadyForProxy(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.readyForProxyProvider));
            Dependency_MembersInjector.injectMTrackpadGestureHandle(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.trackpadGestureHandlerProvider));
            Dependency_MembersInjector.injectMFeatureFlagsLazy(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.featureFlagsClassicReleaseProvider));
            Dependency_MembersInjector.injectMQsNotificationComponentStarter(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.qsNotificationComponentStarterProvider));
            Dependency_MembersInjector.injectMUiOffloadThread(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.uiOffloadThreadProvider));
            Dependency_MembersInjector.injectMUiEventLogger(dependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.provideUiEventLoggerProvider));
            return dependency;
        }

        @Override // com.android.systemui.Dependency.DependencyInjector
        public void createSystemUI(Dependency dependency) {
            injectDependency(dependency);
        }
    }

    final class ExpandableNotificationRowComponentBuilder implements ExpandableNotificationRowComponent.Builder {
        private ExpandableNotificationRow expandableNotificationRow;
        private NotificationEntry notificationEntry;
        private ExpandableNotificationRow.OnExpandClickListener onExpandClickListener;
        private final QsNotificationComponentImpl qsNotificationComponentImpl;
        private final SystemUIRootComponentImpl systemUIRootComponentImpl;

        private ExpandableNotificationRowComponentBuilder(SystemUIRootComponentImpl systemUIRootComponentImpl, QsNotificationComponentImpl qsNotificationComponentImpl) {
            this.systemUIRootComponentImpl = systemUIRootComponentImpl;
            this.qsNotificationComponentImpl = qsNotificationComponentImpl;
        }

        @Override // com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent.Builder
        public ExpandableNotificationRowComponent build() {
            Preconditions.checkBuilderRequirement(this.expandableNotificationRow, ExpandableNotificationRow.class);
            Preconditions.checkBuilderRequirement(this.notificationEntry, NotificationEntry.class);
            Preconditions.checkBuilderRequirement(this.onExpandClickListener, ExpandableNotificationRow.OnExpandClickListener.class);
            return new ExpandableNotificationRowComponentImpl(this.systemUIRootComponentImpl, this.qsNotificationComponentImpl, this.expandableNotificationRow, this.notificationEntry, this.onExpandClickListener);
        }

        @Override // com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent.Builder
        public ExpandableNotificationRowComponentBuilder expandableNotificationRow(ExpandableNotificationRow expandableNotificationRow) {
            expandableNotificationRow.getClass();
            this.expandableNotificationRow = expandableNotificationRow;
            return this;
        }

        @Override // com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent.Builder
        public ExpandableNotificationRowComponentBuilder notificationEntry(NotificationEntry notificationEntry) {
            notificationEntry.getClass();
            this.notificationEntry = notificationEntry;
            return this;
        }

        @Override // com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent.Builder
        public ExpandableNotificationRowComponentBuilder onExpandClickListener(ExpandableNotificationRow.OnExpandClickListener onExpandClickListener) {
            onExpandClickListener.getClass();
            this.onExpandClickListener = onExpandClickListener;
            return this;
        }
    }

    final class ExpandableNotificationRowComponentImpl implements ExpandableNotificationRowComponent {
        private Provider activatableNotificationViewControllerProvider;
        private final ExpandableNotificationRowComponentImpl expandableNotificationRowComponentImpl;
        private Provider expandableNotificationRowControllerProvider;
        private Provider expandableNotificationRowDragControllerProvider;
        private Provider expandableNotificationRowProvider;
        private Provider expandableOutlineViewControllerProvider;
        private Provider expandableViewControllerProvider;
        private Provider notificationChildrenContainerLoggerProvider;
        private final NotificationEntry notificationEntry;
        private Provider notificationEntryProvider;
        private Provider notificationRowLoggerProvider;
        private Provider onExpandClickListenerProvider;
        private Provider provideAppNameProvider;
        private Provider provideNotificationKeyProvider;
        private Provider provideStatusBarNotificationProvider;
        private final QsNotificationComponentImpl qsNotificationComponentImpl;
        private Provider remoteInputViewSubcomponentFactoryProvider;
        private final SystemUIRootComponentImpl systemUIRootComponentImpl;

        private ExpandableNotificationRowComponentImpl(SystemUIRootComponentImpl systemUIRootComponentImpl, QsNotificationComponentImpl qsNotificationComponentImpl, ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry, ExpandableNotificationRow.OnExpandClickListener onExpandClickListener) {
            this.expandableNotificationRowComponentImpl = this;
            this.systemUIRootComponentImpl = systemUIRootComponentImpl;
            this.qsNotificationComponentImpl = qsNotificationComponentImpl;
            this.notificationEntry = notificationEntry;
            initialize(expandableNotificationRow, notificationEntry, onExpandClickListener);
        }

        private void initialize(ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry, ExpandableNotificationRow.OnExpandClickListener onExpandClickListener) {
            Factory factoryCreate = InstanceFactory.create(expandableNotificationRow);
            this.expandableNotificationRowProvider = factoryCreate;
            ExpandableViewController_Factory expandableViewController_FactoryCreate = ExpandableViewController_Factory.create(factoryCreate);
            this.expandableViewControllerProvider = expandableViewController_FactoryCreate;
            ExpandableOutlineViewController_Factory expandableOutlineViewController_FactoryCreate = ExpandableOutlineViewController_Factory.create(this.expandableNotificationRowProvider, expandableViewController_FactoryCreate);
            this.expandableOutlineViewControllerProvider = expandableOutlineViewController_FactoryCreate;
            this.activatableNotificationViewControllerProvider = ActivatableNotificationViewController_Factory.create(this.expandableNotificationRowProvider, expandableOutlineViewController_FactoryCreate, this.systemUIRootComponentImpl.provideAccessibilityManagerProvider);
            this.remoteInputViewSubcomponentFactoryProvider = new Provider() { // from class: com.android.systemui.dagger.DaggerSystemUIRootComponent.ExpandableNotificationRowComponentImpl.1
                @Override // javax.inject.Provider
                public RemoteInputViewSubcomponent.Factory get() {
                    return new RemoteInputViewSubcomponentFactory(ExpandableNotificationRowComponentImpl.this.systemUIRootComponentImpl, ExpandableNotificationRowComponentImpl.this.qsNotificationComponentImpl, ExpandableNotificationRowComponentImpl.this.expandableNotificationRowComponentImpl);
                }
            };
            this.notificationRowLoggerProvider = NotificationRowLogger_Factory.create(this.systemUIRootComponentImpl.provideNotificationsLogBufferProvider, this.systemUIRootComponentImpl.provideNotificationRenderLogBufferProvider);
            this.notificationChildrenContainerLoggerProvider = NotificationChildrenContainerLogger_Factory.create(this.systemUIRootComponentImpl.provideNotificationRenderLogBufferProvider);
            Factory factoryCreate2 = InstanceFactory.create(notificationEntry);
            this.notificationEntryProvider = factoryCreate2;
            this.provideStatusBarNotificationProvider = ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory.create(factoryCreate2);
            this.provideAppNameProvider = ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideAppNameFactory.create(this.qsNotificationComponentImpl.provideContextProvider, this.provideStatusBarNotificationProvider);
            this.provideNotificationKeyProvider = ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideNotificationKeyFactory.create(this.provideStatusBarNotificationProvider);
            this.onExpandClickListenerProvider = InstanceFactory.create(onExpandClickListener);
            this.expandableNotificationRowDragControllerProvider = ExpandableNotificationRowDragController_Factory.create(this.qsNotificationComponentImpl.provideContextProvider, this.qsNotificationComponentImpl.desktopHeadsUpControllerProvider, this.qsNotificationComponentImpl.provideNotificationPanelLoggerProvider);
            this.expandableNotificationRowControllerProvider = DoubleCheck.provider((Provider) ExpandableNotificationRowController_Factory.create(this.expandableNotificationRowProvider, this.activatableNotificationViewControllerProvider, this.remoteInputViewSubcomponentFactoryProvider, this.systemUIRootComponentImpl.colorUpdateLoggerProvider, this.notificationRowLoggerProvider, this.notificationChildrenContainerLoggerProvider, this.qsNotificationComponentImpl.provideListContainerProvider, this.systemUIRootComponentImpl.smartReplyConstantsProvider, this.qsNotificationComponentImpl.provideSmartReplyControllerProvider, this.systemUIRootComponentImpl.bindSystemClockProvider, this.provideAppNameProvider, this.provideNotificationKeyProvider, this.systemUIRootComponentImpl.groupMembershipManagerImplProvider, this.qsNotificationComponentImpl.groupExpansionManagerImplProvider, this.qsNotificationComponentImpl.rowContentBindStageProvider, this.qsNotificationComponentImpl.provideRowStatsLoggerProvider, this.qsNotificationComponentImpl.desktopHeadsUpControllerProvider, this.onExpandClickListenerProvider, this.systemUIRootComponentImpl.provideAllowNotificationLongPressProvider, this.qsNotificationComponentImpl.onUserInteractionCallbackImplProvider, this.systemUIRootComponentImpl.featureFlagsClassicReleaseProvider, this.qsNotificationComponentImpl.peopleNotificationIdentifierImplProvider, this.systemUIRootComponentImpl.notificationSettingsControllerProvider, this.expandableNotificationRowDragControllerProvider, this.qsNotificationComponentImpl.notificationDismissibilityProviderImplProvider, this.systemUIRootComponentImpl.provideIStatusBarServiceProvider));
        }

        @Override // com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent
        public BigPictureIconManager getBigPictureIconManager() {
            return new BigPictureIconManager((Context) this.qsNotificationComponentImpl.provideContextProvider.get(), (ImageLoader) this.systemUIRootComponentImpl.imageLoaderProvider.get(), (BigPictureStatsManager) this.systemUIRootComponentImpl.bigPictureStatsManagerProvider.get(), (CoroutineScope) this.systemUIRootComponentImpl.applicationScopeProvider.get(), (CoroutineDispatcher) this.systemUIRootComponentImpl.mainDispatcherProvider.get(), (CoroutineDispatcher) this.systemUIRootComponentImpl.bgDispatcherProvider.get());
        }

        @Override // com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent
        public ExpandableNotificationRowController getExpandableNotificationRowController() {
            return (ExpandableNotificationRowController) this.expandableNotificationRowControllerProvider.get();
        }
    }

    final class QND_DependencyInjectorImpl implements QsNotificationDependency.DependencyInjector {
        private Provider notificationSectionsManagerProvider;
        private final QND_DependencyInjectorImpl qND_DependencyInjectorImpl;
        private final QsNotificationComponentImpl qsNotificationComponentImpl;
        private final SystemUIRootComponentImpl systemUIRootComponentImpl;

        private QND_DependencyInjectorImpl(SystemUIRootComponentImpl systemUIRootComponentImpl, QsNotificationComponentImpl qsNotificationComponentImpl) {
            this.qND_DependencyInjectorImpl = this;
            this.systemUIRootComponentImpl = systemUIRootComponentImpl;
            this.qsNotificationComponentImpl = qsNotificationComponentImpl;
            initialize();
        }

        private void initialize() {
            this.notificationSectionsManagerProvider = NotificationSectionsManager_Factory.create(this.qsNotificationComponentImpl.provideQsConfigurationControllerProvider, this.systemUIRootComponentImpl.notificationSectionsFeatureManagerProvider, this.qsNotificationComponentImpl.mediaContainerControllerProvider, this.qsNotificationComponentImpl.notificationRoundnessManagerProvider, this.qsNotificationComponentImpl.providesIncomingHeaderControllerProvider, this.qsNotificationComponentImpl.providesPeopleHeaderControllerProvider, this.qsNotificationComponentImpl.providesAlertingHeaderControllerProvider, this.qsNotificationComponentImpl.providesSilentHeaderControllerProvider);
        }

        private QsNotificationDependency injectQsNotificationDependency2(QsNotificationDependency qsNotificationDependency) {
            QsNotificationDependency_MembersInjector.injectMSmartReplyController(qsNotificationDependency, DoubleCheck.lazy(this.qsNotificationComponentImpl.provideSmartReplyControllerProvider));
            QsNotificationDependency_MembersInjector.injectMNotificationRemoteInputManager(qsNotificationDependency, DoubleCheck.lazy(this.qsNotificationComponentImpl.notificationRemoteInputManagerProvider));
            QsNotificationDependency_MembersInjector.injectMNotificationSectionsManager(qsNotificationDependency, DoubleCheck.lazy(this.notificationSectionsManagerProvider));
            QsNotificationDependency_MembersInjector.injectMAmbientState(qsNotificationDependency, DoubleCheck.lazy(this.qsNotificationComponentImpl.ambientStateProvider));
            QsNotificationDependency_MembersInjector.injectMGroupMembershipManager(qsNotificationDependency, DoubleCheck.lazy(this.systemUIRootComponentImpl.groupMembershipManagerImplProvider));
            QsNotificationDependency_MembersInjector.injectMGroupExpansionManager(qsNotificationDependency, DoubleCheck.lazy(this.qsNotificationComponentImpl.groupExpansionManagerImplProvider));
            QsNotificationDependency_MembersInjector.injectMQsNotificationTooltipPopupManager(qsNotificationDependency, DoubleCheck.lazy(this.qsNotificationComponentImpl.qsNotificationTooltipPopupManagerProvider));
            QsNotificationDependency_MembersInjector.injectMActivityStarter(qsNotificationDependency, DoubleCheck.lazy(this.qsNotificationComponentImpl.activityStarterProxyProvider));
            return qsNotificationDependency;
        }

        @Override // com.motorola.taskbar.qsnotification.QsNotificationDependency.DependencyInjector
        public void injectQsNotificationDependency(QsNotificationDependency qsNotificationDependency) {
            injectQsNotificationDependency2(qsNotificationDependency);
        }
    }

    final class QSSceneComponentFactory implements QSSceneComponent.Factory {
        private final QsNotificationComponentImpl qsNotificationComponentImpl;
        private final SystemUIRootComponentImpl systemUIRootComponentImpl;

        private QSSceneComponentFactory(SystemUIRootComponentImpl systemUIRootComponentImpl, QsNotificationComponentImpl qsNotificationComponentImpl) {
            this.systemUIRootComponentImpl = systemUIRootComponentImpl;
            this.qsNotificationComponentImpl = qsNotificationComponentImpl;
        }

        @Override // com.android.systemui.qs.dagger.QSSceneComponent.Factory
        public QSSceneComponent create(View view) {
            view.getClass();
            return new QSSceneComponentImpl(this.systemUIRootComponentImpl, this.qsNotificationComponentImpl, view);
        }
    }

    final class QSSceneComponentImpl implements QSSceneComponent {
        private Provider provideQSPanelProvider;
        private Provider qSPanelControllerProvider;
        private final QSSceneComponentImpl qSSceneComponentImpl;
        private final QsNotificationComponentImpl qsNotificationComponentImpl;
        private final View rootView;
        private Provider rootViewProvider;
        private final SystemUIRootComponentImpl systemUIRootComponentImpl;

        private QSSceneComponentImpl(SystemUIRootComponentImpl systemUIRootComponentImpl, QsNotificationComponentImpl qsNotificationComponentImpl, View view) {
            this.qSSceneComponentImpl = this;
            this.systemUIRootComponentImpl = systemUIRootComponentImpl;
            this.qsNotificationComponentImpl = qsNotificationComponentImpl;
            this.rootView = view;
            initialize(view);
        }

        private void initialize(View view) {
            Factory factoryCreate = InstanceFactory.create(view);
            this.rootViewProvider = factoryCreate;
            QSScopeModule_ProvideQSPanelFactory qSScopeModule_ProvideQSPanelFactoryCreate = QSScopeModule_ProvideQSPanelFactory.create(factoryCreate);
            this.provideQSPanelProvider = qSScopeModule_ProvideQSPanelFactoryCreate;
            this.qSPanelControllerProvider = DoubleCheck.provider((Provider) QSPanelController_Factory.create(qSScopeModule_ProvideQSPanelFactoryCreate, this.qsNotificationComponentImpl.qSTileHostProvider, QSSceneModule_ProvidesQSUsingMediaPlayerFactory.create(), this.qsNotificationComponentImpl.providesQSMediaHostProvider, this.systemUIRootComponentImpl.dumpManagerProvider, QSLongPressEffect_Factory.create()));
        }

        @Override // com.android.systemui.qs.dagger.QSComponent
        public QSPanelController getQSPanelController() {
            return (QSPanelController) this.qSPanelControllerProvider.get();
        }
    }

    final class QsNotificationComponentBuilder implements QsNotificationComponent.Builder {
        private Integer setDisplayId;
        private final SystemUIRootComponentImpl systemUIRootComponentImpl;

        private QsNotificationComponentBuilder(SystemUIRootComponentImpl systemUIRootComponentImpl) {
            this.systemUIRootComponentImpl = systemUIRootComponentImpl;
        }

        @Override // com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent.Builder
        public QsNotificationComponent build() {
            Preconditions.checkBuilderRequirement(this.setDisplayId, Integer.class);
            return new QsNotificationComponentImpl(this.systemUIRootComponentImpl, this.setDisplayId);
        }

        @Override // com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent.Builder
        public QsNotificationComponentBuilder setDisplayId(int i) {
            this.setDisplayId = Integer.valueOf(i);
            return this;
        }
    }

    final class QsNotificationComponentImpl implements QsNotificationComponent {
        private Provider activeNotificationListRepositoryProvider;
        private Provider activeNotificationsInteractorProvider;
        private Provider activityStarterProxyProvider;
        private Provider airplaneModeTileProvider;
        private Provider ambientStateProvider;
        private Provider animatedImageNotificationManagerProvider;
        private Provider avalancheControllerProvider;
        private Provider avalancheProvider;
        private Provider bindEventManagerImplProvider;
        private Provider bluetoothTileProvider;
        private Provider builderProvider;
        private Provider builderProvider2;
        private Provider cellularTileProvider;
        private Provider configurationStateProvider;
        private Provider conversationNotificationManagerProvider;
        private Provider conversationNotificationProcessorProvider;
        private Provider coordinatorsSubcomponentFactoryProvider;
        private Provider desktopFocusAudioOutputMonitorProvider;
        private Provider desktopHeadsUpControllerProvider;
        private Provider desktopUnreadNotificationMonitorProvider;
        private Provider dndTileProvider;
        private Provider expandableNotificationRowComponentBuilderProvider;
        private Provider expansionStateLoggerProvider;
        private Provider groupCoalescerLoggerProvider;
        private Provider groupCoalescerProvider;
        private Provider groupExpansionManagerImplProvider;
        private Provider headsUpManagerPhoneProvider;
        private Provider headsUpNotificationInteractorProvider;
        private Provider headsUpNotificationViewBinderProvider;
        private Provider headsUpViewBinderLoggerProvider;
        private Provider headsUpViewBinderProvider;
        private Provider highPriorityProvider;
        private Provider hotspotTileProvider;
        private Provider iconBuilderProvider;
        private Provider iconManagerProvider;
        private Provider keyguardDismissUtilProvider;
        private Provider keyguardNotificationVisibilityProviderImplProvider;
        private Provider launchFullScreenIntentProvider;
        private Provider legacyMediaDataFilterImplProvider;
        private Provider legacyMediaDataManagerImplProvider;
        private Provider mapOfStringAndProviderOfQSTileImplOfProvider;
        private Provider mediaCarouselControllerProvider;
        private Provider mediaCarouselInteractorProvider;
        private Provider mediaCarouselViewModelProvider;
        private Provider mediaContainerControllerProvider;
        private Provider mediaControlInteractorFactoryProvider;
        private MediaControlInteractor_Factory mediaControlInteractorProvider;
        private Provider mediaControlPanelProvider;
        private Provider mediaControllerFactoryProvider;
        private Provider mediaDataFilterImplProvider;
        private Provider mediaDataProcessorProvider;
        private Provider mediaDataRepositoryProvider;
        private Provider mediaDeviceManagerProvider;
        private Provider mediaFeatureFlagProvider;
        private Provider mediaFilterRepositoryProvider;
        private Provider mediaHierarchyManagerProvider;
        private Provider mediaHostStatesManagerProvider;
        private Provider mediaSessionBasedFilterProvider;
        private Provider mediaTimeoutListenerProvider;
        private Provider mediaViewControllerProvider;
        private Provider nodeSpecBuilderLoggerProvider;
        private Provider notifBindPipelineInitializerProvider;
        private Provider notifBindPipelineLoggerProvider;
        private Provider notifBindPipelineProvider;
        private Provider notifCollectionLoggerProvider;
        private Provider notifCollectionProvider;
        private Provider notifCoordinatorsProvider;
        private Provider notifInflaterImplProvider;
        private Provider notifInflaterLoggerProvider;
        private Provider notifInflationErrorManagerProvider;
        private NotifLayoutInflaterFactory_Factory notifLayoutInflaterFactoryProvider;
        private Provider notifLiveDataStoreImplProvider;
        private Provider notifPipelineChoreographerImplProvider;
        private Provider notifPipelineInitializerProvider;
        private Provider notifPipelineProvider;
        private Provider notifRemoteViewCacheImplProvider;
        private Provider notifRemoteViewsFactoryContainerImplProvider;
        private Provider notifUiAdjustmentProvider;
        private Provider notifViewBarnProvider;
        private Provider notificationClickNotifierProvider;
        private Provider notificationClickerLoggerProvider;
        private Provider notificationContentInflaterLoggerProvider;
        private Provider notificationContentInflaterProvider;
        private Provider notificationDismissibilityProviderImplProvider;
        private Provider notificationEntryProcessorFactoryLooperImplProvider;
        private Provider notificationInteractionTrackerProvider;
        private Provider notificationInterruptLoggerProvider;
        private Provider notificationInterruptStateProviderImplProvider;
        private Provider notificationListViewBinderProvider;
        private Provider notificationListViewModelProvider;
        private Provider notificationLockscreenUserManagerImplProvider;
        private Provider notificationLoggerViewModelProvider;
        private Provider notificationPersonExtractorPluginBoundaryProvider;
        private Provider notificationRemoteInputManagerProvider;
        private Provider notificationRoundnessManagerProvider;
        private Provider notificationRowBinderImplProvider;
        private Provider notificationRowBinderLoggerProvider;
        private Provider notificationSectionsManagerProvider;
        private Provider notificationStackInteractorProvider;
        private Provider notificationStackScrollLayoutControllerProvider;
        private Provider notificationStackScrollLoggerProvider;
        private Provider notificationStackSizeCalculatorProvider;
        private Provider notificationStatsLoggerImplProvider;
        private Provider notificationTargetsHelperProvider;
        private Provider notificationViewFlipperFactoryProvider;
        private Provider notificationViewFlipperViewModelProvider;
        private Provider notificationVisibilityProviderImplProvider;
        private Provider notificationsControllerImplProvider;
        private Provider notificationsControllerStubProvider;
        private Provider onUserInteractionCallbackImplProvider;
        private Provider peopleNotificationIdentifierImplProvider;
        private Provider provideContextProvider;
        private Provider provideDisplayIdProvider;
        private Provider provideHeadsUpStyleManagerProvider;
        private Provider provideLegacyLoggerOptionalProvider;
        private Provider provideListContainerProvider;
        private Provider provideNotifRemoteViewCacheProvider;
        private Provider provideNotifRemoteViewsFactoryContainerProvider;
        private Provider provideNotificationMediaManagerProvider;
        private Provider provideNotificationPanelLoggerProvider;
        private Provider provideNotificationsControllerProvider;
        private Provider provideOptionalProvider;
        private Provider provideQsConfigurationControllerProvider;
        private Provider provideRowStatsLoggerProvider;
        private Provider provideSmartReplyControllerProvider;
        private Provider provideStatsLoggerProvider;
        private Provider provideViewModelProvider;
        private Provider provideVisualInterruptionDecisionProvider;
        private Provider providerLayoutInflaterProvider;
        private Provider providerProvider;
        private Provider providesAlertingHeaderControllerProvider;
        private Provider providesAlertingHeaderNodeControllerProvider;
        private Provider providesAlertingHeaderSubcomponentProvider;
        private Provider providesIncomingHeaderControllerProvider;
        private Provider providesIncomingHeaderNodeControllerProvider;
        private Provider providesIncomingHeaderSubcomponentProvider;
        private Provider providesMediaDataManagerProvider;
        private Provider providesPeopleHeaderControllerProvider;
        private Provider providesPeopleHeaderNodeControllerProvider;
        private Provider providesPeopleHeaderSubcomponentProvider;
        private Provider providesQSMediaHostProvider;
        private Provider providesSilentHeaderControllerProvider;
        private Provider providesSilentHeaderNodeControllerProvider;
        private Provider providesSilentHeaderSubcomponentProvider;
        private Provider qSFactoryImplProvider;
        private Provider qSSceneComponentFactoryProvider;
        private Provider qSTileHostProvider;
        private Provider qsConfigurationControllerImplProvider;
        private Provider qsNotificationCentralSurfacesProvider;
        private final QsNotificationComponentImpl qsNotificationComponentImpl;
        private Provider qsNotificationTooltipPopupManagerProvider;
        private Provider remoteInputInteractorProvider;
        private Provider remoteInputNotificationRebuilderProvider;
        private Provider remoteInputRepositoryImplProvider;
        private Provider remoteInputUriControllerProvider;
        private Provider renderStageManagerProvider;
        private Provider rowContentBindStageLoggerProvider;
        private Provider rowContentBindStageProvider;
        private Provider rowInflaterTaskLoggerProvider;
        private Provider rowInflaterTaskProvider;
        private Provider screenRecordTileProvider;
        private Provider screenshotTileProvider;
        private Provider sectionHeaderControllerSubcomponentBuilderProvider;
        private Provider sectionHeaderVisibilityProvider;
        private Provider sectionStyleProvider;
        private Provider seekBarViewModelProvider;
        private Provider seenNotificationsInteractorProvider;
        private Provider setDisplayIdProvider;
        private Provider shadeEventCoordinatorLoggerProvider;
        private Provider shadeEventCoordinatorProvider;
        private Provider shadeListBuilderLoggerProvider;
        private Provider shadeListBuilderProvider;
        private Provider shadeViewDifferLoggerProvider;
        private Provider shadeViewManagerFactoryProvider;
        private ShadeViewManager_Factory shadeViewManagerProvider;
        private Provider smartActionInflaterImplProvider;
        private Provider smartReplyInflaterImplProvider;
        private Provider smartReplyStateInflaterImplProvider;
        private Provider stackStateLoggerProvider;
        private Provider statusBarNotificationActivityStarterProvider;
        private Provider statusBarNotificationPresenterProvider;
        private Provider statusBarRemoteInputCallbackProvider;
        private final SystemUIRootComponentImpl systemUIRootComponentImpl;
        private Provider visualInterruptionDecisionLoggerProvider;
        private Provider visualInterruptionDecisionProviderImplProvider;
        private Provider visualStabilityCoordinatorProvider;
        private Provider visualStabilityProvider;
        private Provider wifiTileProvider;

        private QsNotificationComponentImpl(SystemUIRootComponentImpl systemUIRootComponentImpl, Integer num) {
            this.qsNotificationComponentImpl = this;
            this.systemUIRootComponentImpl = systemUIRootComponentImpl;
            initialize(num);
            initialize2(num);
            initialize3(num);
            initialize4(num);
            initialize5(num);
            initialize6(num);
            initialize7(num);
            initialize8(num);
        }

        private void initialize(Integer num) {
            Factory factoryCreate = InstanceFactory.create(num);
            this.setDisplayIdProvider = factoryCreate;
            this.provideDisplayIdProvider = QsNotificationComponent_QsNotificationBaseModule_ProvideDisplayIdFactory.create(factoryCreate);
            this.provideContextProvider = DoubleCheck.provider((Provider) QsNotificationComponent_QsNotificationBaseModule_ProvideContextFactory.create(this.systemUIRootComponentImpl.provideContextProvider, this.provideDisplayIdProvider));
            this.notifCollectionLoggerProvider = NotifCollectionLogger_Factory.create(this.systemUIRootComponentImpl.provideNotificationsLogBufferProvider);
            this.notificationDismissibilityProviderImplProvider = DoubleCheck.provider((Provider) NotificationDismissibilityProviderImpl_Factory.create(this.systemUIRootComponentImpl.dumpManagerProvider));
            this.notifCollectionProvider = DoubleCheck.provider((Provider) NotifCollection_Factory.create(this.systemUIRootComponentImpl.provideIStatusBarServiceProvider, this.systemUIRootComponentImpl.bindSystemClockProvider, NotifPipelineFlags_Factory.create(), this.notifCollectionLoggerProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, this.systemUIRootComponentImpl.provideBackgroundExecutorProvider, this.systemUIRootComponentImpl.logBufferEulogizerProvider, this.systemUIRootComponentImpl.dumpManagerProvider, this.notificationDismissibilityProviderImplProvider));
            this.notifPipelineChoreographerImplProvider = DoubleCheck.provider((Provider) NotifPipelineChoreographerImpl_Factory.create(this.systemUIRootComponentImpl.providesChoreographerProvider, this.systemUIRootComponentImpl.provideMainDelayableExecutorProvider));
            Provider provider = DoubleCheck.provider((Provider) NotificationClickNotifier_Factory.create(this.systemUIRootComponentImpl.provideIStatusBarServiceProvider, this.systemUIRootComponentImpl.provideMainExecutorProvider, this.systemUIRootComponentImpl.provideUiBackgroundExecutorProvider));
            this.notificationClickNotifierProvider = provider;
            this.notificationInteractionTrackerProvider = DoubleCheck.provider((Provider) NotificationInteractionTracker_Factory.create(provider));
            this.shadeListBuilderLoggerProvider = ShadeListBuilderLogger_Factory.create(NotifPipelineFlags_Factory.create(), this.systemUIRootComponentImpl.provideNotificationsLogBufferProvider);
            this.shadeListBuilderProvider = DoubleCheck.provider((Provider) ShadeListBuilder_Factory.create(this.systemUIRootComponentImpl.dumpManagerProvider, this.notifPipelineChoreographerImplProvider, NotifPipelineFlags_Factory.create(), this.notificationInteractionTrackerProvider, this.shadeListBuilderLoggerProvider, this.systemUIRootComponentImpl.bindSystemClockProvider));
            Provider provider2 = DoubleCheck.provider((Provider) RenderStageManager_Factory.create());
            this.renderStageManagerProvider = provider2;
            this.notifPipelineProvider = DoubleCheck.provider((Provider) NotifPipeline_Factory.create(this.notifCollectionProvider, this.shadeListBuilderProvider, provider2));
            this.notifLiveDataStoreImplProvider = DoubleCheck.provider((Provider) NotifLiveDataStoreImpl_Factory.create(this.systemUIRootComponentImpl.provideMainExecutorProvider));
            this.groupCoalescerLoggerProvider = GroupCoalescerLogger_Factory.create(this.systemUIRootComponentImpl.provideNotificationsLogBufferProvider);
            this.groupCoalescerProvider = GroupCoalescer_Factory.create(this.systemUIRootComponentImpl.provideMainDelayableExecutorProvider, this.systemUIRootComponentImpl.bindSystemClockProvider, this.groupCoalescerLoggerProvider);
            Provider provider3 = new Provider() { // from class: com.android.systemui.dagger.DaggerSystemUIRootComponent.QsNotificationComponentImpl.1
                @Override // javax.inject.Provider
                public CoordinatorsSubcomponent.Factory get() {
                    return new CoordinatorsSubcomponentFactory(QsNotificationComponentImpl.this.systemUIRootComponentImpl, QsNotificationComponentImpl.this.qsNotificationComponentImpl);
                }
            };
            this.coordinatorsSubcomponentFactoryProvider = provider3;
            this.notifCoordinatorsProvider = DoubleCheck.provider((Provider) CoordinatorsModule_NotifCoordinatorsFactory.create(provider3));
            this.notifInflationErrorManagerProvider = DoubleCheck.provider((Provider) NotifInflationErrorManager_Factory.create());
            NotifInflaterLogger_Factory notifInflaterLogger_FactoryCreate = NotifInflaterLogger_Factory.create(this.systemUIRootComponentImpl.provideNotifInflationLogBufferProvider);
            this.notifInflaterLoggerProvider = notifInflaterLogger_FactoryCreate;
            this.notifInflaterImplProvider = DoubleCheck.provider((Provider) NotifInflaterImpl_Factory.create(this.notifInflationErrorManagerProvider, notifInflaterLogger_FactoryCreate));
            Provider provider4 = DoubleCheck.provider((Provider) QsNotificationComponent_NotificationExtraModule_ProviderLayoutInflaterFactory.create(this.provideContextProvider));
            this.providerLayoutInflaterProvider = provider4;
            this.mediaContainerControllerProvider = DoubleCheck.provider((Provider) MediaContainerController_Factory.create(provider4));
            this.sectionHeaderVisibilityProvider = DoubleCheck.provider((Provider) SectionHeaderVisibilityProvider_Factory.create(this.provideContextProvider));
            this.nodeSpecBuilderLoggerProvider = NodeSpecBuilderLogger_Factory.create(NotifPipelineFlags_Factory.create(), this.systemUIRootComponentImpl.provideNotificationsLogBufferProvider);
        }

        private void initialize2(Integer num) {
            this.shadeViewDifferLoggerProvider = ShadeViewDifferLogger_Factory.create(this.systemUIRootComponentImpl.provideNotificationsLogBufferProvider);
            this.notifViewBarnProvider = DoubleCheck.provider((Provider) NotifViewBarn_Factory.create());
            ShadeViewManager_Factory shadeViewManager_FactoryCreate = ShadeViewManager_Factory.create(this.provideContextProvider, this.mediaContainerControllerProvider, this.systemUIRootComponentImpl.notificationSectionsFeatureManagerProvider, this.sectionHeaderVisibilityProvider, this.nodeSpecBuilderLoggerProvider, this.shadeViewDifferLoggerProvider, this.notifViewBarnProvider);
            this.shadeViewManagerProvider = shadeViewManager_FactoryCreate;
            this.shadeViewManagerFactoryProvider = ShadeViewManagerFactory_Impl.createFactoryProvider(shadeViewManager_FactoryCreate);
            this.notifPipelineInitializerProvider = DoubleCheck.provider((Provider) NotifPipelineInitializer_Factory.create(this.notifPipelineProvider, this.groupCoalescerProvider, this.notifCollectionProvider, this.shadeListBuilderProvider, this.renderStageManagerProvider, this.notifCoordinatorsProvider, this.notifInflaterImplProvider, this.systemUIRootComponentImpl.dumpManagerProvider, this.shadeViewManagerFactoryProvider));
            this.notifBindPipelineLoggerProvider = NotifBindPipelineLogger_Factory.create(this.systemUIRootComponentImpl.provideNotificationsLogBufferProvider);
            NotificationEntryProcessorFactoryLooperImpl_Factory notificationEntryProcessorFactoryLooperImpl_FactoryCreate = NotificationEntryProcessorFactoryLooperImpl_Factory.create(ConcurrencyModule_ProvideMainLooperFactory.create());
            this.notificationEntryProcessorFactoryLooperImplProvider = notificationEntryProcessorFactoryLooperImpl_FactoryCreate;
            this.notifBindPipelineProvider = DoubleCheck.provider((Provider) NotifBindPipeline_Factory.create(this.notifPipelineProvider, this.notifBindPipelineLoggerProvider, notificationEntryProcessorFactoryLooperImpl_FactoryCreate));
            NotifRemoteViewCacheImpl_Factory notifRemoteViewCacheImpl_FactoryCreate = NotifRemoteViewCacheImpl_Factory.create(this.notifPipelineProvider);
            this.notifRemoteViewCacheImplProvider = notifRemoteViewCacheImpl_FactoryCreate;
            this.provideNotifRemoteViewCacheProvider = DoubleCheck.provider((Provider) notifRemoteViewCacheImpl_FactoryCreate);
            Provider provider = DoubleCheck.provider((Provider) ActiveNotificationListRepository_Factory.create());
            this.activeNotificationListRepositoryProvider = provider;
            ActiveNotificationsInteractor_Factory activeNotificationsInteractor_FactoryCreate = ActiveNotificationsInteractor_Factory.create(provider, this.systemUIRootComponentImpl.bgDispatcherProvider);
            this.activeNotificationsInteractorProvider = activeNotificationsInteractor_FactoryCreate;
            this.notificationVisibilityProviderImplProvider = DoubleCheck.provider((Provider) NotificationVisibilityProviderImpl_Factory.create(activeNotificationsInteractor_FactoryCreate, this.notifLiveDataStoreImplProvider, this.notifPipelineProvider));
            this.notificationLockscreenUserManagerImplProvider = DoubleCheck.provider((Provider) NotificationLockscreenUserManagerImpl_Factory.create(this.provideContextProvider, this.systemUIRootComponentImpl.providesBroadcastDispatcherProvider, this.systemUIRootComponentImpl.provideDevicePolicyManagerProvider, this.systemUIRootComponentImpl.provideUserManagerProvider, this.systemUIRootComponentImpl.provideUserTrackerProvider, this.notificationVisibilityProviderImplProvider, this.notifPipelineProvider, this.notificationClickNotifierProvider, this.systemUIRootComponentImpl.provideKeyguardManagerProvider, this.systemUIRootComponentImpl.provideMainExecutorProvider, this.systemUIRootComponentImpl.provideBackgroundExecutorProvider, this.systemUIRootComponentImpl.bindDeviceProvisionedControllerProvider, this.systemUIRootComponentImpl.secureSettingsImplProvider, this.systemUIRootComponentImpl.dumpManagerProvider, this.systemUIRootComponentImpl.provideLockPatternUtilsProvider, this.systemUIRootComponentImpl.featureFlagsClassicReleaseProvider));
            this.provideSmartReplyControllerProvider = DoubleCheck.provider((Provider) QsNotificationComponent_NotificationExtraModule_ProvideSmartReplyControllerFactory.create(this.systemUIRootComponentImpl.dumpManagerProvider, this.notificationVisibilityProviderImplProvider, this.systemUIRootComponentImpl.provideIStatusBarServiceProvider, this.notificationClickNotifierProvider));
            this.remoteInputUriControllerProvider = DoubleCheck.provider((Provider) RemoteInputUriController_Factory.create(this.systemUIRootComponentImpl.provideIStatusBarServiceProvider));
            this.notificationRemoteInputManagerProvider = DoubleCheck.provider((Provider) NotificationRemoteInputManager_Factory.create(this.provideContextProvider, NotifPipelineFlags_Factory.create(), this.notificationLockscreenUserManagerImplProvider, this.provideSmartReplyControllerProvider, this.notificationVisibilityProviderImplProvider, this.remoteInputUriControllerProvider, this.systemUIRootComponentImpl.remoteInputControllerLoggerProvider, this.notificationClickNotifierProvider, this.systemUIRootComponentImpl.javaAdapterProvider));
            Provider provider2 = DoubleCheck.provider((Provider) BindEventManagerImpl_Factory.create());
            this.bindEventManagerImplProvider = provider2;
            this.conversationNotificationManagerProvider = DoubleCheck.provider((Provider) ConversationNotificationManager_Factory.create(provider2, this.provideContextProvider, this.notifPipelineProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider));
            this.conversationNotificationProcessorProvider = ConversationNotificationProcessor_Factory.create(this.systemUIRootComponentImpl.provideLauncherAppsProvider, this.conversationNotificationManagerProvider);
            this.mediaFeatureFlagProvider = MediaFeatureFlag_Factory.create(this.systemUIRootComponentImpl.provideContextProvider);
            Provider provider3 = DoubleCheck.provider((Provider) ActivityStarterProxy_Factory.create(this.provideDisplayIdProvider, this.systemUIRootComponentImpl.activityStarterDelegateProvider));
            this.activityStarterProxyProvider = provider3;
            this.keyguardDismissUtilProvider = DoubleCheck.provider((Provider) KeyguardDismissUtil_Factory.create(provider3));
            this.smartReplyInflaterImplProvider = SmartReplyInflaterImpl_Factory.create(this.systemUIRootComponentImpl.smartReplyConstantsProvider, this.keyguardDismissUtilProvider, this.notificationRemoteInputManagerProvider, this.provideSmartReplyControllerProvider, this.provideContextProvider);
            this.desktopHeadsUpControllerProvider = DoubleCheck.provider((Provider) DesktopHeadsUpController_Factory.create(this.provideDisplayIdProvider, this.provideContextProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, this.systemUIRootComponentImpl.bindDeviceProvisionedControllerProvider, this.notifInflaterImplProvider, this.systemUIRootComponentImpl.provideConfigurationControllerProvider, this.systemUIRootComponentImpl.provideUserTrackerProvider, this.notifBindPipelineProvider));
        }

        private void initialize3(Integer num) {
            this.smartActionInflaterImplProvider = SmartActionInflaterImpl_Factory.create(this.systemUIRootComponentImpl.smartReplyConstantsProvider, this.activityStarterProxyProvider, this.provideSmartReplyControllerProvider, this.desktopHeadsUpControllerProvider);
            this.smartReplyStateInflaterImplProvider = SmartReplyStateInflaterImpl_Factory.create(this.systemUIRootComponentImpl.smartReplyConstantsProvider, this.systemUIRootComponentImpl.provideActivityManagerWrapperProvider, this.systemUIRootComponentImpl.providePackageManagerWrapperProvider, this.systemUIRootComponentImpl.provideDevicePolicyManagerWrapperProvider, this.smartReplyInflaterImplProvider, this.smartActionInflaterImplProvider);
            this.notificationStackInteractorProvider = DoubleCheck.provider((Provider) NotificationStackInteractor_Factory.create());
            Provider provider = DoubleCheck.provider((Provider) NotificationViewFlipperViewModel_Factory.create(this.systemUIRootComponentImpl.dumpManagerProvider, this.notificationStackInteractorProvider));
            this.notificationViewFlipperViewModelProvider = provider;
            this.notificationViewFlipperFactoryProvider = NotificationViewFlipperFactory_Factory.create(provider);
            NotifRemoteViewsFactoryContainerImpl_Factory notifRemoteViewsFactoryContainerImpl_FactoryCreate = NotifRemoteViewsFactoryContainerImpl_Factory.create(this.systemUIRootComponentImpl.featureFlagsClassicReleaseProvider, PrecomputedTextViewFactory_Factory.create(), BigPictureLayoutInflaterFactory_Factory.create(), NotificationOptimizedLinearLayoutFactory_Factory.create(), this.notificationViewFlipperFactoryProvider);
            this.notifRemoteViewsFactoryContainerImplProvider = notifRemoteViewsFactoryContainerImpl_FactoryCreate;
            Provider provider2 = DoubleCheck.provider((Provider) notifRemoteViewsFactoryContainerImpl_FactoryCreate);
            this.provideNotifRemoteViewsFactoryContainerProvider = provider2;
            NotifLayoutInflaterFactory_Factory notifLayoutInflaterFactory_FactoryCreate = NotifLayoutInflaterFactory_Factory.create(provider2);
            this.notifLayoutInflaterFactoryProvider = notifLayoutInflaterFactory_FactoryCreate;
            this.providerProvider = NotifLayoutInflaterFactory_Provider_Impl.createFactoryProvider(notifLayoutInflaterFactory_FactoryCreate);
            this.provideHeadsUpStyleManagerProvider = DoubleCheck.provider((Provider) HeadsUpStyleProviderImpl_Factory.create());
            this.notificationContentInflaterLoggerProvider = NotificationContentInflaterLogger_Factory.create(this.systemUIRootComponentImpl.provideNotifInflationLogBufferProvider);
            this.notificationContentInflaterProvider = DoubleCheck.provider((Provider) NotificationContentInflater_Factory.create(this.provideNotifRemoteViewCacheProvider, this.notificationRemoteInputManagerProvider, this.conversationNotificationProcessorProvider, this.mediaFeatureFlagProvider, this.systemUIRootComponentImpl.provideNotifInflationExecutorProvider, this.smartReplyStateInflaterImplProvider, this.providerProvider, this.provideHeadsUpStyleManagerProvider, this.notificationContentInflaterLoggerProvider));
            RowContentBindStageLogger_Factory rowContentBindStageLogger_FactoryCreate = RowContentBindStageLogger_Factory.create(this.systemUIRootComponentImpl.provideNotifInflationLogBufferProvider);
            this.rowContentBindStageLoggerProvider = rowContentBindStageLogger_FactoryCreate;
            Provider provider3 = DoubleCheck.provider((Provider) RowContentBindStage_Factory.create(this.notificationContentInflaterProvider, this.notifInflationErrorManagerProvider, rowContentBindStageLogger_FactoryCreate));
            this.rowContentBindStageProvider = provider3;
            this.notifBindPipelineInitializerProvider = NotifBindPipelineInitializer_Factory.create(this.notifBindPipelineProvider, provider3);
            this.expansionStateLoggerProvider = NotificationLogger_ExpansionStateLogger_Factory.create(this.systemUIRootComponentImpl.provideUiBackgroundExecutorProvider);
            this.provideNotificationPanelLoggerProvider = DoubleCheck.provider((Provider) NotificationsModule_ProvideNotificationPanelLoggerFactory.create());
            this.provideLegacyLoggerOptionalProvider = DoubleCheck.provider((Provider) NotificationStatsLoggerModule_Companion_ProvideLegacyLoggerOptionalFactory.create(this.systemUIRootComponentImpl.notificationListenerProvider, this.systemUIRootComponentImpl.provideUiBackgroundExecutorProvider, this.notifLiveDataStoreImplProvider, this.notificationVisibilityProviderImplProvider, this.notifPipelineProvider, this.systemUIRootComponentImpl.javaAdapterProvider, this.expansionStateLoggerProvider, this.provideNotificationPanelLoggerProvider));
            this.rowInflaterTaskLoggerProvider = RowInflaterTaskLogger_Factory.create(this.systemUIRootComponentImpl.provideNotifInflationLogBufferProvider);
            this.rowInflaterTaskProvider = RowInflaterTask_Factory.create(this.systemUIRootComponentImpl.bindSystemClockProvider, this.rowInflaterTaskLoggerProvider);
            this.expandableNotificationRowComponentBuilderProvider = new Provider() { // from class: com.android.systemui.dagger.DaggerSystemUIRootComponent.QsNotificationComponentImpl.2
                @Override // javax.inject.Provider
                public ExpandableNotificationRowComponent.Builder get() {
                    return new ExpandableNotificationRowComponentBuilder(QsNotificationComponentImpl.this.systemUIRootComponentImpl, QsNotificationComponentImpl.this.qsNotificationComponentImpl);
                }
            };
            this.iconBuilderProvider = IconBuilder_Factory.create(this.provideContextProvider);
            this.iconManagerProvider = DoubleCheck.provider((Provider) IconManager_Factory.create(this.notifPipelineProvider, this.systemUIRootComponentImpl.provideLauncherAppsProvider, this.iconBuilderProvider, this.systemUIRootComponentImpl.applicationScopeProvider, this.systemUIRootComponentImpl.bgCoroutineContextProvider, this.systemUIRootComponentImpl.mainCoroutineContextProvider));
            this.notificationRowBinderLoggerProvider = NotificationRowBinderLogger_Factory.create(this.systemUIRootComponentImpl.provideNotifInflationLogBufferProvider);
            this.notificationRowBinderImplProvider = DoubleCheck.provider((Provider) NotificationRowBinderImpl_Factory.create(this.provideContextProvider, this.systemUIRootComponentImpl.provideNotificationMessagingUtilProvider, this.notificationRemoteInputManagerProvider, this.notificationLockscreenUserManagerImplProvider, this.notifBindPipelineProvider, this.rowContentBindStageProvider, this.rowInflaterTaskProvider, this.expandableNotificationRowComponentBuilderProvider, this.iconManagerProvider, this.notificationRowBinderLoggerProvider, this.systemUIRootComponentImpl.featureFlagsClassicReleaseProvider));
        }

        private void initialize4(Integer num) {
            MediaControllerFactory_Factory mediaControllerFactory_FactoryCreate = MediaControllerFactory_Factory.create(this.systemUIRootComponentImpl.provideContextProvider);
            this.mediaControllerFactoryProvider = mediaControllerFactory_FactoryCreate;
            this.mediaTimeoutListenerProvider = DoubleCheck.provider((Provider) MediaTimeoutListener_Factory.create(mediaControllerFactory_FactoryCreate, this.systemUIRootComponentImpl.provideMainDelayableExecutorProvider, this.systemUIRootComponentImpl.bindSystemClockProvider, this.systemUIRootComponentImpl.mediaFlagsProvider));
            this.mediaSessionBasedFilterProvider = MediaSessionBasedFilter_Factory.create(this.systemUIRootComponentImpl.provideContextProvider, this.systemUIRootComponentImpl.provideMediaSessionManagerProvider, this.systemUIRootComponentImpl.provideMainExecutorProvider, this.systemUIRootComponentImpl.provideBackgroundExecutorProvider);
            this.desktopFocusAudioOutputMonitorProvider = DesktopFocusAudioOutputMonitor_Factory.create(this.provideContextProvider, this.systemUIRootComponentImpl.provideUserTrackerProvider);
            this.mediaDeviceManagerProvider = MediaDeviceManager_Factory.create(this.systemUIRootComponentImpl.provideContextProvider, this.systemUIRootComponentImpl.provideMainExecutorProvider, this.systemUIRootComponentImpl.provideBackgroundExecutorProvider, this.desktopFocusAudioOutputMonitorProvider);
            this.legacyMediaDataFilterImplProvider = DoubleCheck.provider((Provider) LegacyMediaDataFilterImpl_Factory.create(this.provideContextProvider, this.systemUIRootComponentImpl.provideUserTrackerProvider, this.systemUIRootComponentImpl.broadcastSenderProvider, this.notificationLockscreenUserManagerImplProvider, this.systemUIRootComponentImpl.provideMainExecutorProvider, this.systemUIRootComponentImpl.bindSystemClockProvider, this.systemUIRootComponentImpl.mediaUiEventLoggerProvider, this.systemUIRootComponentImpl.mediaFlagsProvider));
            this.legacyMediaDataManagerImplProvider = DoubleCheck.provider((Provider) LegacyMediaDataManagerImpl_Factory.create(this.provideContextProvider, ThreadFactoryImpl_Factory.create(), this.systemUIRootComponentImpl.provideMainExecutorProvider, this.systemUIRootComponentImpl.provideMainDelayableExecutorProvider, this.mediaControllerFactoryProvider, this.systemUIRootComponentImpl.dumpManagerProvider, this.systemUIRootComponentImpl.providesBroadcastDispatcherProvider, this.mediaTimeoutListenerProvider, this.systemUIRootComponentImpl.mediaResumeListenerProvider, this.mediaSessionBasedFilterProvider, this.mediaDeviceManagerProvider, MediaDataCombineLatest_Factory.create(), this.legacyMediaDataFilterImplProvider, this.activityStarterProxyProvider, this.systemUIRootComponentImpl.bindSystemClockProvider, this.systemUIRootComponentImpl.mediaFlagsProvider, this.systemUIRootComponentImpl.mediaUiEventLoggerProvider));
            this.mediaDataRepositoryProvider = DoubleCheck.provider((Provider) MediaDataRepository_Factory.create(this.systemUIRootComponentImpl.mediaFlagsProvider, this.systemUIRootComponentImpl.dumpManagerProvider));
            this.mediaDataProcessorProvider = DoubleCheck.provider((Provider) MediaDataProcessor_Factory.create(this.provideContextProvider, this.systemUIRootComponentImpl.applicationScopeProvider, this.systemUIRootComponentImpl.bgDispatcherProvider, ThreadFactoryImpl_Factory.create(), this.systemUIRootComponentImpl.provideMainExecutorProvider, this.systemUIRootComponentImpl.provideMainDelayableExecutorProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, this.mediaControllerFactoryProvider, this.systemUIRootComponentImpl.dumpManagerProvider, this.systemUIRootComponentImpl.providesBroadcastDispatcherProvider, this.activityStarterProxyProvider, this.systemUIRootComponentImpl.bindSystemClockProvider, this.systemUIRootComponentImpl.mediaFlagsProvider, this.systemUIRootComponentImpl.mediaUiEventLoggerProvider, this.mediaDataRepositoryProvider));
            Provider provider = DoubleCheck.provider((Provider) QsConfigurationControllerImpl_Factory.create(this.provideContextProvider));
            this.qsConfigurationControllerImplProvider = provider;
            this.provideQsConfigurationControllerProvider = QsNotificationComponent_QsNotificationBaseModule_ProvideQsConfigurationControllerFactory.create(provider);
            this.mediaFilterRepositoryProvider = DoubleCheck.provider((Provider) MediaFilterRepository_Factory.create(this.systemUIRootComponentImpl.provideContextProvider, this.systemUIRootComponentImpl.bindSystemClockProvider, this.provideQsConfigurationControllerProvider));
            this.mediaDataFilterImplProvider = MediaDataFilterImpl_Factory.create(this.systemUIRootComponentImpl.provideContextProvider, this.systemUIRootComponentImpl.provideUserTrackerProvider, this.systemUIRootComponentImpl.broadcastSenderProvider, this.notificationLockscreenUserManagerImplProvider, this.systemUIRootComponentImpl.provideMainExecutorProvider, this.systemUIRootComponentImpl.bindSystemClockProvider, this.systemUIRootComponentImpl.mediaFlagsProvider, this.mediaFilterRepositoryProvider);
            Provider provider2 = DoubleCheck.provider((Provider) MediaCarouselInteractor_Factory.create(this.systemUIRootComponentImpl.applicationScopeProvider, this.mediaDataRepositoryProvider, this.mediaDataProcessorProvider, this.mediaTimeoutListenerProvider, this.systemUIRootComponentImpl.mediaResumeListenerProvider, this.mediaSessionBasedFilterProvider, this.mediaDeviceManagerProvider, MediaDataCombineLatest_Factory.create(), this.mediaDataFilterImplProvider, this.mediaFilterRepositoryProvider, this.systemUIRootComponentImpl.mediaFlagsProvider));
            this.mediaCarouselInteractorProvider = provider2;
            Provider provider3 = DoubleCheck.provider((Provider) MediaDomainModule_Companion_ProvidesMediaDataManagerFactory.create(this.legacyMediaDataManagerImplProvider, provider2, this.systemUIRootComponentImpl.mediaFlagsProvider));
            this.providesMediaDataManagerProvider = provider3;
            this.provideNotificationMediaManagerProvider = DoubleCheck.provider((Provider) QsNotificationComponent_NotificationExtraModule_ProvideNotificationMediaManagerFactory.create(this.provideContextProvider, this.notificationVisibilityProviderImplProvider, this.notifPipelineProvider, this.notifCollectionProvider, provider3, this.systemUIRootComponentImpl.dumpManagerProvider));
            this.headsUpViewBinderLoggerProvider = HeadsUpViewBinderLogger_Factory.create(this.systemUIRootComponentImpl.provideNotificationHeadsUpLogBufferProvider);
            this.headsUpViewBinderProvider = DoubleCheck.provider((Provider) HeadsUpViewBinder_Factory.create(this.systemUIRootComponentImpl.provideNotificationMessagingUtilProvider, this.rowContentBindStageProvider, this.headsUpViewBinderLoggerProvider));
            NotificationClickerLogger_Factory notificationClickerLogger_FactoryCreate = NotificationClickerLogger_Factory.create(this.systemUIRootComponentImpl.provideNotifInteractionLogBufferProvider);
            this.notificationClickerLoggerProvider = notificationClickerLogger_FactoryCreate;
            this.builderProvider = NotificationClicker_Builder_Factory.create(notificationClickerLogger_FactoryCreate);
            this.animatedImageNotificationManagerProvider = DoubleCheck.provider((Provider) AnimatedImageNotificationManager_Factory.create(this.notifPipelineProvider, this.bindEventManagerImplProvider, this.desktopHeadsUpControllerProvider));
            Provider provider4 = this.systemUIRootComponentImpl.notificationListenerProvider;
            Provider provider5 = this.notifPipelineProvider;
            this.notificationsControllerImplProvider = DoubleCheck.provider((Provider) NotificationsControllerImpl_Factory.create(provider4, provider5, provider5, this.notifLiveDataStoreImplProvider, this.systemUIRootComponentImpl.targetSdkResolverProvider, this.notifPipelineInitializerProvider, this.notifBindPipelineInitializerProvider, this.provideLegacyLoggerOptionalProvider, this.notificationRowBinderImplProvider, this.provideNotificationMediaManagerProvider, this.headsUpViewBinderProvider, this.builderProvider, this.animatedImageNotificationManagerProvider));
            NotificationsControllerStub_Factory notificationsControllerStub_FactoryCreate = NotificationsControllerStub_Factory.create(this.systemUIRootComponentImpl.notificationListenerProvider);
            this.notificationsControllerStubProvider = notificationsControllerStub_FactoryCreate;
            this.provideNotificationsControllerProvider = DoubleCheck.provider((Provider) NotificationsModule_ProvideNotificationsControllerFactory.create(this.provideContextProvider, this.notificationsControllerImplProvider, notificationsControllerStub_FactoryCreate));
            this.desktopUnreadNotificationMonitorProvider = DoubleCheck.provider((Provider) DesktopUnreadNotificationMonitor_Factory.create(this.shadeListBuilderProvider, this.systemUIRootComponentImpl.notificationListenerProvider, this.systemUIRootComponentImpl.provideBgHandlerProvider));
        }

        private void initialize5(Integer num) {
            this.notificationRoundnessManagerProvider = DoubleCheck.provider((Provider) NotificationRoundnessManager_Factory.create(this.systemUIRootComponentImpl.dumpManagerProvider));
            this.builderProvider2 = NotificationSwipeHelper_Builder_Factory.create(this.systemUIRootComponentImpl.provideResourcesProvider, this.systemUIRootComponentImpl.provideViewConfigurationProvider, this.systemUIRootComponentImpl.dumpManagerProvider, this.systemUIRootComponentImpl.featureFlagsClassicReleaseProvider, this.notificationRoundnessManagerProvider);
            this.groupExpansionManagerImplProvider = DoubleCheck.provider((Provider) GroupExpansionManagerImpl_Factory.create(this.systemUIRootComponentImpl.dumpManagerProvider, this.systemUIRootComponentImpl.groupMembershipManagerImplProvider));
            Provider provider = new Provider() { // from class: com.android.systemui.dagger.DaggerSystemUIRootComponent.QsNotificationComponentImpl.3
                @Override // javax.inject.Provider
                public SectionHeaderControllerSubcomponent.Builder get() {
                    return new SectionHeaderControllerSubcomponentBuilder(QsNotificationComponentImpl.this.systemUIRootComponentImpl, QsNotificationComponentImpl.this.qsNotificationComponentImpl);
                }
            };
            this.sectionHeaderControllerSubcomponentBuilderProvider = provider;
            Provider provider2 = DoubleCheck.provider((Provider) NotificationSectionHeadersModule_ProvidesSilentHeaderSubcomponentFactory.create(provider));
            this.providesSilentHeaderSubcomponentProvider = provider2;
            this.providesSilentHeaderControllerProvider = NotificationSectionHeadersModule_ProvidesSilentHeaderControllerFactory.create(provider2);
            this.seenNotificationsInteractorProvider = DoubleCheck.provider((Provider) SeenNotificationsInteractor_Factory.create(this.activeNotificationListRepositoryProvider));
            this.configurationStateProvider = ConfigurationState_Factory.create(this.systemUIRootComponentImpl.provideConfigurationControllerProvider, this.systemUIRootComponentImpl.provideContextProvider, this.providerLayoutInflaterProvider);
            this.provideOptionalProvider = DoubleCheck.provider((Provider) FooterViewModelModule_ProvideOptionalFactory.create(this.activeNotificationsInteractorProvider, this.seenNotificationsInteractorProvider));
            NotificationLoggerViewModel_Factory notificationLoggerViewModel_FactoryCreate = NotificationLoggerViewModel_Factory.create(this.activeNotificationsInteractorProvider);
            this.notificationLoggerViewModelProvider = notificationLoggerViewModel_FactoryCreate;
            this.provideViewModelProvider = NotificationStatsLoggerModule_Companion_ProvideViewModelFactory.create(notificationLoggerViewModel_FactoryCreate);
            this.visualStabilityProvider = DoubleCheck.provider((Provider) VisualStabilityProvider_Factory.create());
            this.avalancheControllerProvider = DoubleCheck.provider((Provider) AvalancheController_Factory.create(this.systemUIRootComponentImpl.dumpManagerProvider));
            Provider provider3 = DoubleCheck.provider((Provider) HeadsUpManagerPhone_Factory.create(this.provideContextProvider, this.systemUIRootComponentImpl.groupMembershipManagerImplProvider, this.visualStabilityProvider, this.provideQsConfigurationControllerProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, this.systemUIRootComponentImpl.globalSettingsImplProvider, this.systemUIRootComponentImpl.bindSystemClockProvider, this.systemUIRootComponentImpl.provideMainDelayableExecutorProvider, this.systemUIRootComponentImpl.provideUiEventLoggerProvider, this.systemUIRootComponentImpl.javaAdapterProvider, this.avalancheControllerProvider));
            this.headsUpManagerPhoneProvider = provider3;
            this.headsUpNotificationInteractorProvider = HeadsUpNotificationInteractor_Factory.create(provider3);
            Provider provider4 = DoubleCheck.provider((Provider) RemoteInputRepositoryImpl_Factory.create(this.notificationRemoteInputManagerProvider));
            this.remoteInputRepositoryImplProvider = provider4;
            Provider provider5 = DoubleCheck.provider((Provider) RemoteInputInteractor_Factory.create(provider4));
            this.remoteInputInteractorProvider = provider5;
            NotificationListViewModel_Factory notificationListViewModel_FactoryCreate = NotificationListViewModel_Factory.create(this.provideOptionalProvider, this.provideViewModelProvider, this.activeNotificationsInteractorProvider, this.notificationStackInteractorProvider, this.headsUpNotificationInteractorProvider, provider5, this.seenNotificationsInteractorProvider, this.systemUIRootComponentImpl.bgDispatcherProvider, this.systemUIRootComponentImpl.dumpManagerProvider);
            this.notificationListViewModelProvider = notificationListViewModel_FactoryCreate;
            this.headsUpNotificationViewBinderProvider = HeadsUpNotificationViewBinder_Factory.create(notificationListViewModel_FactoryCreate);
            Provider provider6 = DoubleCheck.provider((Provider) NotificationStatsLoggerImpl_Factory.create());
            this.notificationStatsLoggerImplProvider = provider6;
            this.provideStatsLoggerProvider = NotificationStatsLoggerModule_Companion_ProvideStatsLoggerFactory.create(provider6);
            Provider provider7 = DoubleCheck.provider((Provider) VisualStabilityCoordinator_Factory.create(this.systemUIRootComponentImpl.provideBackgroundDelayableExecutorProvider, this.systemUIRootComponentImpl.dumpManagerProvider, this.desktopHeadsUpControllerProvider, this.systemUIRootComponentImpl.javaAdapterProvider, this.systemUIRootComponentImpl.visibilityLocationProviderDelegatorProvider, this.visualStabilityProvider));
            this.visualStabilityCoordinatorProvider = provider7;
            this.onUserInteractionCallbackImplProvider = DoubleCheck.provider((Provider) OnUserInteractionCallbackImpl_Factory.create(this.notificationVisibilityProviderImplProvider, this.notifCollectionProvider, this.desktopHeadsUpControllerProvider, provider7));
            this.notificationStackScrollLayoutControllerProvider = new DelegateFactory();
            this.statusBarRemoteInputCallbackProvider = DoubleCheck.provider((Provider) StatusBarRemoteInputCallback_Factory.create(this.provideContextProvider, this.groupExpansionManagerImplProvider, this.notificationLockscreenUserManagerImplProvider, this.activityStarterProxyProvider, this.systemUIRootComponentImpl.provideMainExecutorProvider));
        }

        private void initialize6(Integer num) {
            this.statusBarNotificationPresenterProvider = DoubleCheck.provider((Provider) StatusBarNotificationPresenter_Factory.create(this.provideContextProvider, this.desktopHeadsUpControllerProvider, this.activityStarterProxyProvider, this.notificationStackScrollLayoutControllerProvider, this.notificationLockscreenUserManagerImplProvider, this.provideNotificationMediaManagerProvider, this.notificationRemoteInputManagerProvider, this.statusBarRemoteInputCallbackProvider));
            this.launchFullScreenIntentProvider = DoubleCheck.provider((Provider) LaunchFullScreenIntentProvider_Factory.create());
            this.statusBarNotificationActivityStarterProvider = DoubleCheck.provider((Provider) StatusBarNotificationActivityStarter_Factory.create(this.provideContextProvider, this.provideDisplayIdProvider, ConcurrencyModule_ProvideHandlerFactory.create(), this.systemUIRootComponentImpl.provideBackgroundExecutorProvider, this.notificationVisibilityProviderImplProvider, this.desktopHeadsUpControllerProvider, this.activityStarterProxyProvider, this.notificationClickNotifierProvider, this.systemUIRootComponentImpl.provideIDreamManagerProvider, this.notificationRemoteInputManagerProvider, this.notificationLockscreenUserManagerImplProvider, this.systemUIRootComponentImpl.activityIntentHelperProvider, this.onUserInteractionCallbackImplProvider, this.statusBarNotificationPresenterProvider, this.launchFullScreenIntentProvider, this.systemUIRootComponentImpl.provideUserTrackerProvider, this.systemUIRootComponentImpl.qSNotificationPanelControllerProvider));
            this.notificationListViewBinderProvider = NotificationListViewBinder_Factory.create(this.systemUIRootComponentImpl.bgDispatcherProvider, this.configurationStateProvider, this.headsUpNotificationViewBinderProvider, this.provideStatsLoggerProvider, this.statusBarNotificationActivityStarterProvider, this.providesSilentHeaderControllerProvider, this.notificationListViewModelProvider);
            this.stackStateLoggerProvider = StackStateLogger_Factory.create(this.systemUIRootComponentImpl.provideNotificationHeadsUpLogBufferProvider, this.systemUIRootComponentImpl.provideNotificationRenderLogBufferProvider);
            this.notificationStackScrollLoggerProvider = NotificationStackScrollLogger_Factory.create(this.systemUIRootComponentImpl.provideNotificationHeadsUpLogBufferProvider, this.systemUIRootComponentImpl.provideNotificationRenderLogBufferProvider, this.systemUIRootComponentImpl.provideShadeLogBufferProvider);
            this.notificationStackSizeCalculatorProvider = DoubleCheck.provider((Provider) NotificationStackSizeCalculator_Factory.create(this.providesMediaDataManagerProvider, this.systemUIRootComponentImpl.provideResourcesProvider));
            this.notificationTargetsHelperProvider = DoubleCheck.provider((Provider) NotificationTargetsHelper_Factory.create(this.systemUIRootComponentImpl.featureFlagsClassicReleaseProvider));
            DelegateFactory.setDelegate(this.notificationStackScrollLayoutControllerProvider, DoubleCheck.provider((Provider) NotificationStackScrollLayoutController_Factory.create(this.systemUIRootComponentImpl.provideAllowNotificationLongPressProvider, this.provideNotificationsControllerProvider, this.notificationVisibilityProviderImplProvider, this.desktopHeadsUpControllerProvider, this.notificationRoundnessManagerProvider, this.systemUIRootComponentImpl.bindDeviceProvisionedControllerProvider, this.systemUIRootComponentImpl.dynamicPrivacyControllerProvider, this.provideQsConfigurationControllerProvider, this.notificationLockscreenUserManagerImplProvider, this.systemUIRootComponentImpl.colorUpdateLoggerProvider, this.systemUIRootComponentImpl.dumpManagerProvider, this.builderProvider2, this.groupExpansionManagerImplProvider, this.providesSilentHeaderControllerProvider, this.notifPipelineProvider, this.notifCollectionProvider, this.systemUIRootComponentImpl.provideUiEventLoggerProvider, this.notificationRemoteInputManagerProvider, this.systemUIRootComponentImpl.visibilityLocationProviderDelegatorProvider, this.seenNotificationsInteractorProvider, this.notificationListViewBinderProvider, this.systemUIRootComponentImpl.provideInteractionJankMonitorProvider, this.stackStateLoggerProvider, this.notificationStackScrollLoggerProvider, this.notificationStackSizeCalculatorProvider, this.notificationTargetsHelperProvider, this.systemUIRootComponentImpl.secureSettingsImplProvider, this.notificationDismissibilityProviderImplProvider, this.activityStarterProxyProvider, this.systemUIRootComponentImpl.sensitiveNotificationProtectionControllerImplProvider)));
            this.notificationInterruptLoggerProvider = NotificationInterruptLogger_Factory.create(this.systemUIRootComponentImpl.provideNotificationInterruptLogBufferProvider);
            Provider provider = DoubleCheck.provider((Provider) NotificationPersonExtractorPluginBoundary_Factory.create(this.systemUIRootComponentImpl.extensionControllerImplProvider));
            this.notificationPersonExtractorPluginBoundaryProvider = provider;
            Provider provider2 = DoubleCheck.provider((Provider) PeopleNotificationIdentifierImpl_Factory.create(provider, this.systemUIRootComponentImpl.groupMembershipManagerImplProvider));
            this.peopleNotificationIdentifierImplProvider = provider2;
            this.highPriorityProvider = DoubleCheck.provider((Provider) HighPriorityProvider_Factory.create(provider2, this.systemUIRootComponentImpl.groupMembershipManagerImplProvider));
            this.keyguardNotificationVisibilityProviderImplProvider = DoubleCheck.provider((Provider) KeyguardNotificationVisibilityProviderImpl_Factory.create(this.systemUIRootComponentImpl.provideMainHandlerProvider, this.notificationLockscreenUserManagerImplProvider, this.highPriorityProvider, this.systemUIRootComponentImpl.provideUserTrackerProvider, this.systemUIRootComponentImpl.secureSettingsImplProvider, this.systemUIRootComponentImpl.globalSettingsImplProvider));
            this.notificationInterruptStateProviderImplProvider = DoubleCheck.provider((Provider) NotificationInterruptStateProviderImpl_Factory.create(this.systemUIRootComponentImpl.providePowerManagerProvider, this.systemUIRootComponentImpl.provideAmbientDisplayConfigurationProvider, this.desktopHeadsUpControllerProvider, this.notificationInterruptLoggerProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, NotifPipelineFlags_Factory.create(), this.keyguardNotificationVisibilityProviderImplProvider, this.systemUIRootComponentImpl.provideUiEventLoggerProvider, this.systemUIRootComponentImpl.provideUserTrackerProvider, this.systemUIRootComponentImpl.bindDeviceProvisionedControllerProvider, this.systemUIRootComponentImpl.bindSystemClockProvider, this.systemUIRootComponentImpl.globalSettingsImplProvider));
            this.visualInterruptionDecisionLoggerProvider = VisualInterruptionDecisionLogger_Factory.create(this.systemUIRootComponentImpl.provideNotificationInterruptLogBufferProvider);
            this.avalancheProvider = DoubleCheck.provider((Provider) AvalancheProvider_Factory.create(this.systemUIRootComponentImpl.providesBroadcastDispatcherProvider, this.visualInterruptionDecisionLoggerProvider));
            VisualInterruptionDecisionProviderImpl_Factory visualInterruptionDecisionProviderImpl_FactoryCreate = VisualInterruptionDecisionProviderImpl_Factory.create(this.systemUIRootComponentImpl.provideAmbientDisplayConfigurationProvider, this.systemUIRootComponentImpl.bindDeviceProvisionedControllerProvider, this.systemUIRootComponentImpl.globalSettingsImplProvider, this.desktopHeadsUpControllerProvider, this.keyguardNotificationVisibilityProviderImplProvider, this.visualInterruptionDecisionLoggerProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, this.provideDisplayIdProvider, this.systemUIRootComponentImpl.providePowerManagerProvider, this.systemUIRootComponentImpl.bindSystemClockProvider, this.systemUIRootComponentImpl.provideUiEventLoggerProvider, this.systemUIRootComponentImpl.provideUserTrackerProvider, this.avalancheProvider, this.systemUIRootComponentImpl.systemSettingsImplProvider);
            this.visualInterruptionDecisionProviderImplProvider = visualInterruptionDecisionProviderImpl_FactoryCreate;
            this.provideVisualInterruptionDecisionProvider = DoubleCheck.provider((Provider) NotificationsModule_ProvideVisualInterruptionDecisionProviderFactory.create(this.notificationInterruptStateProviderImplProvider, visualInterruptionDecisionProviderImpl_FactoryCreate));
            Provider provider3 = new Provider() { // from class: com.android.systemui.dagger.DaggerSystemUIRootComponent.QsNotificationComponentImpl.4
                @Override // javax.inject.Provider
                public QSSceneComponent.Factory get() {
                    return new QSSceneComponentFactory(QsNotificationComponentImpl.this.systemUIRootComponentImpl, QsNotificationComponentImpl.this.qsNotificationComponentImpl);
                }
            };
            this.qSSceneComponentFactoryProvider = provider3;
            this.qsNotificationCentralSurfacesProvider = DoubleCheck.provider((Provider) QsNotificationCentralSurfaces_Factory.create(this.provideDisplayIdProvider, this.provideContextProvider, this.provideNotificationsControllerProvider, this.notificationRemoteInputManagerProvider, this.desktopUnreadNotificationMonitorProvider, this.notificationStackScrollLayoutControllerProvider, this.statusBarNotificationPresenterProvider, this.statusBarNotificationActivityStarterProvider, this.provideQsConfigurationControllerProvider, this.provideVisualInterruptionDecisionProvider, provider3, this.desktopHeadsUpControllerProvider, this.systemUIRootComponentImpl.qSNotificationPanelControllerProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, this.systemUIRootComponentImpl.provideMainDelayableExecutorProvider));
            this.sectionStyleProvider = DoubleCheck.provider((Provider) SectionStyleProvider_Factory.create(this.highPriorityProvider));
            Provider provider4 = DoubleCheck.provider((Provider) NotificationSectionHeadersModule_ProvidesAlertingHeaderSubcomponentFactory.create(this.sectionHeaderControllerSubcomponentBuilderProvider));
            this.providesAlertingHeaderSubcomponentProvider = provider4;
            this.providesAlertingHeaderNodeControllerProvider = NotificationSectionHeadersModule_ProvidesAlertingHeaderNodeControllerFactory.create(provider4);
            this.providesSilentHeaderNodeControllerProvider = NotificationSectionHeadersModule_ProvidesSilentHeaderNodeControllerFactory.create(this.providesSilentHeaderSubcomponentProvider);
        }

        private void initialize7(Integer num) {
            Provider provider = DoubleCheck.provider((Provider) NotificationSectionHeadersModule_ProvidesIncomingHeaderSubcomponentFactory.create(this.sectionHeaderControllerSubcomponentBuilderProvider));
            this.providesIncomingHeaderSubcomponentProvider = provider;
            this.providesIncomingHeaderNodeControllerProvider = NotificationSectionHeadersModule_ProvidesIncomingHeaderNodeControllerFactory.create(provider);
            Provider provider2 = DoubleCheck.provider((Provider) NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory.create(this.sectionHeaderControllerSubcomponentBuilderProvider));
            this.providesPeopleHeaderSubcomponentProvider = provider2;
            this.providesPeopleHeaderNodeControllerProvider = NotificationSectionHeadersModule_ProvidesPeopleHeaderNodeControllerFactory.create(provider2);
            this.notifUiAdjustmentProvider = DoubleCheck.provider((Provider) NotifUiAdjustmentProvider_Factory.create(this.systemUIRootComponentImpl.provideMainHandlerProvider, this.systemUIRootComponentImpl.secureSettingsImplProvider, this.notificationLockscreenUserManagerImplProvider, this.systemUIRootComponentImpl.sensitiveNotificationProtectionControllerImplProvider, this.sectionStyleProvider, this.systemUIRootComponentImpl.provideUserTrackerProvider, this.systemUIRootComponentImpl.groupMembershipManagerImplProvider));
            this.remoteInputNotificationRebuilderProvider = DoubleCheck.provider((Provider) RemoteInputNotificationRebuilder_Factory.create(this.provideContextProvider));
            this.shadeEventCoordinatorLoggerProvider = ShadeEventCoordinatorLogger_Factory.create(this.systemUIRootComponentImpl.provideNotificationsLogBufferProvider);
            this.shadeEventCoordinatorProvider = DoubleCheck.provider((Provider) ShadeEventCoordinator_Factory.create(this.systemUIRootComponentImpl.provideMainExecutorProvider, this.shadeEventCoordinatorLoggerProvider));
            this.provideListContainerProvider = DoubleCheck.provider((Provider) NotificationsModule_ProvideListContainerFactory.create(this.notificationStackScrollLayoutControllerProvider));
            this.provideRowStatsLoggerProvider = NotificationStatsLoggerModule_Companion_ProvideRowStatsLoggerFactory.create(this.notificationStatsLoggerImplProvider, this.provideLegacyLoggerOptionalProvider);
            DelegateFactory delegateFactory = new DelegateFactory();
            this.qSTileHostProvider = delegateFactory;
            this.dndTileProvider = DndTile_Factory.create(delegateFactory, this.systemUIRootComponentImpl.provideBgLooperProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, this.activityStarterProxyProvider, this.systemUIRootComponentImpl.dndQSTileControllerProvider);
            this.bluetoothTileProvider = BluetoothTile_Factory.create(this.qSTileHostProvider, this.systemUIRootComponentImpl.provideBgLooperProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, this.activityStarterProxyProvider, this.systemUIRootComponentImpl.bluetoothQSTileControllerProvider);
            this.wifiTileProvider = WifiTile_Factory.create(this.qSTileHostProvider, this.systemUIRootComponentImpl.provideBgLooperProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, this.activityStarterProxyProvider, this.systemUIRootComponentImpl.wifiQSTileControllerProvider);
            this.cellularTileProvider = CellularTile_Factory.create(this.qSTileHostProvider, this.systemUIRootComponentImpl.provideBgLooperProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, this.activityStarterProxyProvider, this.systemUIRootComponentImpl.cellQSTileControllerProvider);
            this.hotspotTileProvider = HotspotTile_Factory.create(this.qSTileHostProvider, this.systemUIRootComponentImpl.provideBgLooperProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, this.activityStarterProxyProvider, this.systemUIRootComponentImpl.hotspotQSTileControllerProvider);
            this.airplaneModeTileProvider = AirplaneModeTile_Factory.create(this.qSTileHostProvider, this.systemUIRootComponentImpl.provideBgLooperProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, this.activityStarterProxyProvider, this.systemUIRootComponentImpl.airplaneQSTileControllerProvider);
            this.screenshotTileProvider = ScreenshotTile_Factory.create(this.qSTileHostProvider, this.systemUIRootComponentImpl.provideBgLooperProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, this.systemUIRootComponentImpl.screenshotControllerProvider, this.activityStarterProxyProvider);
            this.screenRecordTileProvider = ScreenRecordTile_Factory.create(this.qSTileHostProvider, this.systemUIRootComponentImpl.provideBgLooperProvider, this.systemUIRootComponentImpl.provideMainHandlerProvider, this.activityStarterProxyProvider, this.systemUIRootComponentImpl.screenRecordQSTileControllerProvider);
            MapProviderFactory mapProviderFactoryBuild = MapProviderFactory.builder(8).put((Object) "dnd", this.dndTileProvider).put((Object) "bt", this.bluetoothTileProvider).put((Object) "wifi", this.wifiTileProvider).put((Object) "cell", this.cellularTileProvider).put((Object) "hotspot", this.hotspotTileProvider).put((Object) "airplane", this.airplaneModeTileProvider).put((Object) "screenshot", this.screenshotTileProvider).put((Object) "screenrecord", this.screenRecordTileProvider).build();
            this.mapOfStringAndProviderOfQSTileImplOfProvider = mapProviderFactoryBuild;
            Provider provider3 = DoubleCheck.provider((Provider) QSFactoryImpl_Factory.create(mapProviderFactoryBuild));
            this.qSFactoryImplProvider = provider3;
            DelegateFactory.setDelegate(this.qSTileHostProvider, DoubleCheck.provider((Provider) QSTileHost_Factory.create(this.provideContextProvider, provider3, this.systemUIRootComponentImpl.provideMainExecutorProvider, this.systemUIRootComponentImpl.provideUserTrackerProvider)));
            this.mediaHostStatesManagerProvider = DoubleCheck.provider((Provider) MediaHostStatesManager_Factory.create());
            this.seekBarViewModelProvider = SeekBarViewModel_Factory.create(this.systemUIRootComponentImpl.provideBackgroundRepeatableExecutorProvider);
            this.mediaViewControllerProvider = MediaViewController_Factory.create(this.provideContextProvider, this.provideQsConfigurationControllerProvider, this.mediaHostStatesManagerProvider, this.systemUIRootComponentImpl.mediaViewLoggerProvider, this.seekBarViewModelProvider, this.systemUIRootComponentImpl.provideMainDelayableExecutorProvider, this.systemUIRootComponentImpl.mediaFlagsProvider, this.systemUIRootComponentImpl.globalSettingsImplProvider);
        }

        private void initialize8(Integer num) {
            this.mediaCarouselControllerProvider = new DelegateFactory();
            this.mediaControlPanelProvider = MediaControlPanel_Factory.create(this.provideContextProvider, this.systemUIRootComponentImpl.provideBackgroundExecutorProvider, this.systemUIRootComponentImpl.provideMainDelayableExecutorProvider, this.activityStarterProxyProvider, this.systemUIRootComponentImpl.broadcastSenderProvider, this.mediaViewControllerProvider, this.seekBarViewModelProvider, this.providesMediaDataManagerProvider, this.mediaCarouselControllerProvider, this.systemUIRootComponentImpl.bindSystemClockProvider, this.systemUIRootComponentImpl.mediaUiEventLoggerProvider, this.systemUIRootComponentImpl.activityIntentHelperProvider, this.notificationLockscreenUserManagerImplProvider, this.systemUIRootComponentImpl.globalSettingsImplProvider, this.systemUIRootComponentImpl.mediaFlagsProvider);
            MediaControlInteractor_Factory mediaControlInteractor_FactoryCreate = MediaControlInteractor_Factory.create(this.systemUIRootComponentImpl.provideContextProvider, this.mediaFilterRepositoryProvider, this.mediaDataProcessorProvider, this.activityStarterProxyProvider, this.systemUIRootComponentImpl.activityIntentHelperProvider, this.notificationLockscreenUserManagerImplProvider);
            this.mediaControlInteractorProvider = mediaControlInteractor_FactoryCreate;
            this.mediaControlInteractorFactoryProvider = MediaControlInteractorFactory_Impl.createFactoryProvider(mediaControlInteractor_FactoryCreate);
            this.mediaCarouselViewModelProvider = DoubleCheck.provider((Provider) MediaCarouselViewModel_Factory.create(this.systemUIRootComponentImpl.applicationScopeProvider, this.systemUIRootComponentImpl.provideContextProvider, this.systemUIRootComponentImpl.bgDispatcherProvider, this.systemUIRootComponentImpl.provideBackgroundExecutorProvider, this.visualStabilityProvider, this.mediaCarouselInteractorProvider, this.mediaControlInteractorFactoryProvider, this.systemUIRootComponentImpl.mediaUiEventLoggerProvider, this.systemUIRootComponentImpl.mediaFlagsProvider));
            DelegateFactory.setDelegate(this.mediaCarouselControllerProvider, DoubleCheck.provider((Provider) MediaCarouselController_Factory.create(this.provideContextProvider, this.mediaControlPanelProvider, this.visualStabilityProvider, this.mediaHostStatesManagerProvider, this.activityStarterProxyProvider, this.systemUIRootComponentImpl.bindSystemClockProvider, this.systemUIRootComponentImpl.mainDispatcherProvider, this.systemUIRootComponentImpl.provideMainDelayableExecutorProvider, this.systemUIRootComponentImpl.provideBackgroundExecutorProvider, this.systemUIRootComponentImpl.bgDispatcherProvider, this.providesMediaDataManagerProvider, this.provideQsConfigurationControllerProvider, this.systemUIRootComponentImpl.dumpManagerProvider, this.systemUIRootComponentImpl.mediaUiEventLoggerProvider, this.systemUIRootComponentImpl.mediaCarouselControllerLoggerProvider, this.systemUIRootComponentImpl.mediaFlagsProvider, this.systemUIRootComponentImpl.globalSettingsImplProvider, this.mediaCarouselViewModelProvider, this.mediaViewControllerProvider)));
            this.mediaHierarchyManagerProvider = DoubleCheck.provider((Provider) MediaHierarchyManager_Factory.create(this.provideContextProvider, this.mediaCarouselControllerProvider, this.providesMediaDataManagerProvider));
            this.providesQSMediaHostProvider = DoubleCheck.provider((Provider) MediaModule_ProvidesQSMediaHostFactory.create(MediaHost_MediaHostStateHolder_Factory.create(), this.mediaHierarchyManagerProvider, this.providesMediaDataManagerProvider, this.mediaHostStatesManagerProvider, this.mediaCarouselControllerProvider));
            this.providesIncomingHeaderControllerProvider = NotificationSectionHeadersModule_ProvidesIncomingHeaderControllerFactory.create(this.providesIncomingHeaderSubcomponentProvider);
            this.providesPeopleHeaderControllerProvider = NotificationSectionHeadersModule_ProvidesPeopleHeaderControllerFactory.create(this.providesPeopleHeaderSubcomponentProvider);
            this.providesAlertingHeaderControllerProvider = NotificationSectionHeadersModule_ProvidesAlertingHeaderControllerFactory.create(this.providesAlertingHeaderSubcomponentProvider);
            this.notificationSectionsManagerProvider = NotificationSectionsManager_Factory.create(this.provideQsConfigurationControllerProvider, this.systemUIRootComponentImpl.notificationSectionsFeatureManagerProvider, this.mediaContainerControllerProvider, this.notificationRoundnessManagerProvider, this.providesIncomingHeaderControllerProvider, this.providesPeopleHeaderControllerProvider, this.providesAlertingHeaderControllerProvider, this.providesSilentHeaderControllerProvider);
            this.ambientStateProvider = DoubleCheck.provider((Provider) AmbientState_Factory.create(this.provideContextProvider, this.systemUIRootComponentImpl.dumpManagerProvider, this.notificationSectionsManagerProvider, this.avalancheControllerProvider));
            this.qsNotificationTooltipPopupManagerProvider = DoubleCheck.provider((Provider) QsNotificationTooltipPopupManager_Factory.create(this.provideContextProvider));
        }

        @Override // com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent
        public QsNotificationDependency.DependencyInjector createDependency() {
            return new QND_DependencyInjectorImpl(this.systemUIRootComponentImpl, this.qsNotificationComponentImpl);
        }

        @Override // com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent
        public QsNotificationCentralSurfaces getQsNotificationCentralSurfaces() {
            return (QsNotificationCentralSurfaces) this.qsNotificationCentralSurfacesProvider.get();
        }
    }

    final class RemoteInputViewSubcomponentFactory implements RemoteInputViewSubcomponent.Factory {
        private final ExpandableNotificationRowComponentImpl expandableNotificationRowComponentImpl;
        private final QsNotificationComponentImpl qsNotificationComponentImpl;
        private final SystemUIRootComponentImpl systemUIRootComponentImpl;

        private RemoteInputViewSubcomponentFactory(SystemUIRootComponentImpl systemUIRootComponentImpl, QsNotificationComponentImpl qsNotificationComponentImpl, ExpandableNotificationRowComponentImpl expandableNotificationRowComponentImpl) {
            this.systemUIRootComponentImpl = systemUIRootComponentImpl;
            this.qsNotificationComponentImpl = qsNotificationComponentImpl;
            this.expandableNotificationRowComponentImpl = expandableNotificationRowComponentImpl;
        }

        @Override // com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent.Factory
        public RemoteInputViewSubcomponent create(RemoteInputView remoteInputView, RemoteInputController remoteInputController) {
            remoteInputView.getClass();
            remoteInputController.getClass();
            return new RemoteInputViewSubcomponentImpl(this.systemUIRootComponentImpl, this.qsNotificationComponentImpl, this.expandableNotificationRowComponentImpl, remoteInputView, remoteInputController);
        }
    }

    final class RemoteInputViewSubcomponentImpl implements RemoteInputViewSubcomponent {
        private final ExpandableNotificationRowComponentImpl expandableNotificationRowComponentImpl;
        private final QsNotificationComponentImpl qsNotificationComponentImpl;
        private final RemoteInputController remoteInputController;
        private final RemoteInputViewSubcomponentImpl remoteInputViewSubcomponentImpl;
        private final SystemUIRootComponentImpl systemUIRootComponentImpl;
        private final RemoteInputView view;

        private RemoteInputViewSubcomponentImpl(SystemUIRootComponentImpl systemUIRootComponentImpl, QsNotificationComponentImpl qsNotificationComponentImpl, ExpandableNotificationRowComponentImpl expandableNotificationRowComponentImpl, RemoteInputView remoteInputView, RemoteInputController remoteInputController) {
            this.remoteInputViewSubcomponentImpl = this;
            this.systemUIRootComponentImpl = systemUIRootComponentImpl;
            this.qsNotificationComponentImpl = qsNotificationComponentImpl;
            this.expandableNotificationRowComponentImpl = expandableNotificationRowComponentImpl;
            this.view = remoteInputView;
            this.remoteInputController = remoteInputController;
        }

        private RemoteInputViewControllerImpl remoteInputViewControllerImpl() {
            return new RemoteInputViewControllerImpl(this.view, this.expandableNotificationRowComponentImpl.notificationEntry, this.remoteInputController, (ShortcutManager) this.systemUIRootComponentImpl.provideShortcutManagerProvider.get(), (UiEventLogger) this.systemUIRootComponentImpl.provideUiEventLoggerProvider.get(), (FeatureFlags) this.systemUIRootComponentImpl.featureFlagsClassicReleaseProvider.get());
        }

        @Override // com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent
        public RemoteInputViewController getController() {
            return remoteInputViewControllerImpl();
        }
    }

    final class SectionHeaderControllerSubcomponentBuilder implements SectionHeaderControllerSubcomponent.Builder {
        private String clickIntentAction;
        private Integer headerText;
        private String nodeLabel;
        private final QsNotificationComponentImpl qsNotificationComponentImpl;
        private final SystemUIRootComponentImpl systemUIRootComponentImpl;

        private SectionHeaderControllerSubcomponentBuilder(SystemUIRootComponentImpl systemUIRootComponentImpl, QsNotificationComponentImpl qsNotificationComponentImpl) {
            this.systemUIRootComponentImpl = systemUIRootComponentImpl;
            this.qsNotificationComponentImpl = qsNotificationComponentImpl;
        }

        @Override // com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent.Builder
        public SectionHeaderControllerSubcomponent build() {
            Preconditions.checkBuilderRequirement(this.nodeLabel, String.class);
            Preconditions.checkBuilderRequirement(this.headerText, Integer.class);
            Preconditions.checkBuilderRequirement(this.clickIntentAction, String.class);
            return new SectionHeaderControllerSubcomponentImpl(this.systemUIRootComponentImpl, this.qsNotificationComponentImpl, this.nodeLabel, this.headerText, this.clickIntentAction);
        }

        @Override // com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent.Builder
        public SectionHeaderControllerSubcomponentBuilder clickIntentAction(String str) {
            str.getClass();
            this.clickIntentAction = str;
            return this;
        }

        @Override // com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent.Builder
        public SectionHeaderControllerSubcomponentBuilder headerText(int i) {
            this.headerText = Integer.valueOf(i);
            return this;
        }

        @Override // com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent.Builder
        public SectionHeaderControllerSubcomponentBuilder nodeLabel(String str) {
            str.getClass();
            this.nodeLabel = str;
            return this;
        }
    }

    final class SectionHeaderControllerSubcomponentImpl implements SectionHeaderControllerSubcomponent {
        private Provider clickIntentActionProvider;
        private Provider headerTextProvider;
        private Provider nodeLabelProvider;
        private final QsNotificationComponentImpl qsNotificationComponentImpl;
        private final SectionHeaderControllerSubcomponentImpl sectionHeaderControllerSubcomponentImpl;
        private Provider sectionHeaderNodeControllerImplProvider;
        private final SystemUIRootComponentImpl systemUIRootComponentImpl;

        private SectionHeaderControllerSubcomponentImpl(SystemUIRootComponentImpl systemUIRootComponentImpl, QsNotificationComponentImpl qsNotificationComponentImpl, String str, Integer num, String str2) {
            this.sectionHeaderControllerSubcomponentImpl = this;
            this.systemUIRootComponentImpl = systemUIRootComponentImpl;
            this.qsNotificationComponentImpl = qsNotificationComponentImpl;
            initialize(str, num, str2);
        }

        private void initialize(String str, Integer num, String str2) {
            this.nodeLabelProvider = InstanceFactory.create(str);
            this.headerTextProvider = InstanceFactory.create(num);
            this.clickIntentActionProvider = InstanceFactory.create(str2);
            this.sectionHeaderNodeControllerImplProvider = DoubleCheck.provider((Provider) SectionHeaderNodeControllerImpl_Factory.create(this.nodeLabelProvider, this.qsNotificationComponentImpl.providerLayoutInflaterProvider, this.headerTextProvider, this.qsNotificationComponentImpl.activityStarterProxyProvider, this.clickIntentActionProvider));
        }

        @Override // com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent
        public SectionHeaderController getHeaderController() {
            return (SectionHeaderController) this.sectionHeaderNodeControllerImplProvider.get();
        }

        @Override // com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent
        public NodeController getNodeController() {
            return (NodeController) this.sectionHeaderNodeControllerImplProvider.get();
        }
    }

    final class SystemUIRootComponentImpl implements SystemUIRootComponent {
        private Provider activityIntentHelperProvider;
        private Provider activityStarterDelegateProvider;
        private Provider airplaneQSTileControllerProvider;
        private Provider applicationScopeProvider;
        private Provider assistantFeedbackControllerProvider;
        private Provider bgCoroutineContextProvider;
        private Provider bgDispatcherProvider;
        private Provider bigPictureStatsManagerProvider;
        private Provider bindDeviceProvisionedControllerProvider;
        private Provider bindSystemClockProvider;
        private Provider bluetoothQSTileControllerProvider;
        private Provider broadcastSenderProvider;
        private Provider builderProvider;
        private Provider cellQSTileControllerProvider;
        private Provider colorUpdateLoggerProvider;
        private Provider deviceProvisionedControllerImplProvider;
        private Provider displayConfigurationControllerProvider;
        private Provider displayWindowManagerProvider;
        private Provider dndQSTileControllerProvider;
        private Provider dumpManagerProvider;
        private Provider dynamicPrivacyControllerProvider;
        private Provider extensionControllerImplProvider;
        private Provider externalDisplayModeManagerProvider;
        private Provider externalDisplayTutorialManagerProvider;
        private Provider featureFlagsClassicReleaseProvider;
        private Provider glassesMonitorProvider;
        private Provider globalSettingsImplProvider;
        private Provider groupMembershipManagerImplProvider;
        private Provider hardwareDisplayControllerImplProvider;
        private Provider hotspotQSTileControllerProvider;
        private Provider imageLoaderProvider;
        private Provider javaAdapterProvider;
        private Provider logBufferEulogizerProvider;
        private Provider logBufferFactoryProvider;
        private Provider mainCoroutineContextProvider;
        private Provider mainDispatcherProvider;
        private Provider mediaBrowserFactoryProvider;
        private Provider mediaCarouselControllerLoggerProvider;
        private Provider mediaFlagsProvider;
        private Provider mediaResumeListenerProvider;
        private Provider mediaUiEventLoggerProvider;
        private Provider mediaViewLoggerProvider;
        private Provider mirrorPhonePanelControllerProvider;
        private Provider motoFeatureProvider;
        private Provider navIconControllerProvider;
        private Provider notificationListenerProvider;
        private Provider notificationSectionsFeatureManagerProvider;
        private Provider notificationSettingsControllerProvider;
        private Provider provideAccessibilityManagerProvider;
        private Provider provideActivityManagerWrapperProvider;
        private Provider provideAlarmManagerProvider;
        private Provider provideAllowNotificationLongPressProvider;
        private Provider provideAmbientDisplayConfigurationProvider;
        private Provider provideBackgroundDelayableExecutorProvider;
        private Provider provideBackgroundExecutorProvider;
        private Provider provideBackgroundRepeatableExecutorProvider;
        private Provider provideBgHandlerProvider;
        private Provider provideBgLooperProvider;
        private Provider provideConfigurationControllerProvider;
        private Provider provideContentResolverProvider;
        private Provider provideContextProvider;
        private Provider provideDevicePolicyManagerProvider;
        private Provider provideDevicePolicyManagerWrapperProvider;
        private Provider provideDisplayManagerProvider;
        private Provider provideDisplayMetricsProvider;
        private Provider provideIActivityManagerProvider;
        private Provider provideIDreamManagerProvider;
        private Provider provideINotificationManagerProvider;
        private Provider provideIPackageManagerProvider;
        private Provider provideIStatusBarServiceProvider;
        private Provider provideIWindowManagerProvider;
        private Provider provideInteractionJankMonitorProvider;
        private Provider provideKeyguardManagerProvider;
        private Provider provideLatencyTrackerProvider;
        private Provider provideLauncherAppsProvider;
        private Provider provideLockPatternUtilsProvider;
        private Provider provideMainDelayableExecutorProvider;
        private Provider provideMainExecutorProvider;
        private Provider provideMainHandlerProvider;
        private Provider provideMediaBrowserBufferProvider;
        private Provider provideMediaCarouselControllerBufferProvider;
        private Provider provideMediaProjectionManagerProvider;
        private Provider provideMediaSessionManagerProvider;
        private Provider provideMediaViewLogBufferProvider;
        private Provider provideNotifInflationExecutorProvider;
        private Provider provideNotifInflationLogBufferProvider;
        private Provider provideNotifInflationLooperProvider;
        private Provider provideNotifInteractionLogBufferProvider;
        private Provider provideNotificationHeadsUpLogBufferProvider;
        private Provider provideNotificationInterruptLogBufferProvider;
        private Provider provideNotificationManagerProvider;
        private Provider provideNotificationMessagingUtilProvider;
        private Provider provideNotificationRemoteInputLogBufferProvider;
        private Provider provideNotificationRenderLogBufferProvider;
        private Provider provideNotificationsLogBufferProvider;
        private Provider providePackageManagerProvider;
        private Provider providePackageManagerWrapperProvider;
        private Provider providePowerManagerProvider;
        private Provider provideResourcesProvider;
        private Provider provideShadeLogBufferProvider;
        private Provider provideShortcutManagerProvider;
        private Provider provideTelephonyManagerProvider;
        private Provider provideTimeTickHandlerProvider;
        private Provider provideUiBackgroundExecutorProvider;
        private Provider provideUiEventLoggerProvider;
        private Provider provideUserManagerProvider;
        private Provider provideUserTrackerProvider;
        private Provider provideViewConfigurationProvider;
        private Provider provideWakeLockLogProvider;
        private Provider provideWindowManagerProvider;
        private Provider providesBroadcastDispatcherProvider;
        private Provider providesChoreographerProvider;
        private Provider qSNotificationPanelControllerProvider;
        private Provider qsNotificationComponentBuilderProvider;
        private Provider qsNotificationComponentStarterProvider;
        private Provider readyForProxyProvider;
        private Provider recentsControllerProvider;
        private Provider recordManagerProvider;
        private Provider remoteInputControllerLoggerProvider;
        private Provider resumeMediaBrowserFactoryProvider;
        private Provider resumeMediaBrowserLoggerProvider;
        private Provider screenRecordQSTileControllerProvider;
        private Provider screenshotControllerProvider;
        private Provider secureSettingsImplProvider;
        private Provider selectedUserInteractorProvider;
        private Provider sensitiveNotificationProtectionControllerImplProvider;
        private Provider shortcutKeyDispatcherProvider;
        private Provider smartReplyConstantsProvider;
        private Provider systemPropertiesHelperProvider;
        private Provider systemSettingsImplProvider;
        private final SystemUIRootComponentImpl systemUIRootComponentImpl;
        private Provider targetSdkResolverProvider;
        private Provider taskBarControllerProvider;
        private Provider taskBarServiceProxyProvider;
        private Provider taskControllerProvider;
        private Provider taskSyncreticControllerProvider;
        private Provider tracingCoroutineContextProvider;
        private Provider trackpadGestureHandlerProvider;
        private Provider uiOffloadThreadProvider;
        private Provider visibilityLocationProviderDelegatorProvider;
        private Provider wakeLockLoggerProvider;
        private Provider wifiQSTileControllerProvider;
        private Provider wifiStatusMonitorProvider;

        private SystemUIRootComponentImpl(FrameworkServicesModule frameworkServicesModule, GlobalCoroutinesModule globalCoroutinesModule, DependencyProvider dependencyProvider, SharedLibraryModule sharedLibraryModule, SystemUIFactory.ContextHolder contextHolder, SysUICoroutinesModule sysUICoroutinesModule) {
            this.systemUIRootComponentImpl = this;
            initialize(frameworkServicesModule, globalCoroutinesModule, dependencyProvider, sharedLibraryModule, contextHolder, sysUICoroutinesModule);
            initialize2(frameworkServicesModule, globalCoroutinesModule, dependencyProvider, sharedLibraryModule, contextHolder, sysUICoroutinesModule);
            initialize3(frameworkServicesModule, globalCoroutinesModule, dependencyProvider, sharedLibraryModule, contextHolder, sysUICoroutinesModule);
            initialize4(frameworkServicesModule, globalCoroutinesModule, dependencyProvider, sharedLibraryModule, contextHolder, sysUICoroutinesModule);
            initialize5(frameworkServicesModule, globalCoroutinesModule, dependencyProvider, sharedLibraryModule, contextHolder, sysUICoroutinesModule);
            initialize6(frameworkServicesModule, globalCoroutinesModule, dependencyProvider, sharedLibraryModule, contextHolder, sysUICoroutinesModule);
        }

        private Handler backgroundHandler() {
            return ConcurrencyModule_ProvideBgHandlerFactory.provideBgHandler((Looper) this.provideBgLooperProvider.get());
        }

        private void initialize(FrameworkServicesModule frameworkServicesModule, GlobalCoroutinesModule globalCoroutinesModule, DependencyProvider dependencyProvider, SharedLibraryModule sharedLibraryModule, SystemUIFactory.ContextHolder contextHolder, SysUICoroutinesModule sysUICoroutinesModule) {
            this.provideContextProvider = SystemUIFactory_ContextHolder_ProvideContextFactory.create(contextHolder);
            this.provideMainHandlerProvider = ConcurrencyModule_ProvideMainHandlerFactory.create(ConcurrencyModule_ProvideMainLooperFactory.create());
            Provider provider = DoubleCheck.provider((Provider) ConcurrencyModule_ProvideBgLooperFactory.create());
            this.provideBgLooperProvider = provider;
            ConcurrencyModule_ProvideBgHandlerFactory concurrencyModule_ProvideBgHandlerFactoryCreate = ConcurrencyModule_ProvideBgHandlerFactory.create(provider);
            this.provideBgHandlerProvider = concurrencyModule_ProvideBgHandlerFactoryCreate;
            this.taskBarServiceProxyProvider = DoubleCheck.provider((Provider) TaskBarServiceProxy_Factory.create(this.provideContextProvider, this.provideMainHandlerProvider, concurrencyModule_ProvideBgHandlerFactoryCreate));
            this.provideDisplayManagerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideDisplayManagerFactory.create(this.provideContextProvider));
            this.provideNotificationManagerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideNotificationManagerFactory.create(this.provideContextProvider));
            this.bindSystemClockProvider = DoubleCheck.provider((Provider) SystemClockImpl_Factory.create());
            ConcurrencyModule_ProvideMainExecutorFactory concurrencyModule_ProvideMainExecutorFactoryCreate = ConcurrencyModule_ProvideMainExecutorFactory.create(this.provideContextProvider);
            this.provideMainExecutorProvider = concurrencyModule_ProvideMainExecutorFactoryCreate;
            this.notificationListenerProvider = DoubleCheck.provider((Provider) NotificationListener_Factory.create(this.provideContextProvider, this.provideNotificationManagerProvider, this.bindSystemClockProvider, concurrencyModule_ProvideMainExecutorFactoryCreate));
            Provider provider2 = new Provider() { // from class: com.android.systemui.dagger.DaggerSystemUIRootComponent.SystemUIRootComponentImpl.1
                @Override // javax.inject.Provider
                public QsNotificationComponent.Builder get() {
                    return new QsNotificationComponentBuilder(SystemUIRootComponentImpl.this.systemUIRootComponentImpl);
                }
            };
            this.qsNotificationComponentBuilderProvider = provider2;
            Provider provider3 = DoubleCheck.provider((Provider) QsNotificationComponentStarter_Factory.create(this.provideContextProvider, this.provideDisplayManagerProvider, this.notificationListenerProvider, provider2));
            this.qsNotificationComponentStarterProvider = provider3;
            this.qSNotificationPanelControllerProvider = DoubleCheck.provider((Provider) QSNotificationPanelController_Factory.create(this.provideContextProvider, this.provideMainHandlerProvider, provider3));
            this.provideContentResolverProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideContentResolverFactory.create(this.provideContextProvider));
            this.provideUserManagerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideUserManagerFactory.create(this.provideContextProvider));
            this.provideIActivityManagerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideIActivityManagerFactory.create());
            this.dumpManagerProvider = DoubleCheck.provider((Provider) DumpManager_Factory.create());
            Provider provider4 = DoubleCheck.provider((Provider) GlobalCoroutinesModule_TracingCoroutineContextFactory.create(globalCoroutinesModule));
            this.tracingCoroutineContextProvider = provider4;
            Provider provider5 = DoubleCheck.provider((Provider) GlobalCoroutinesModule_MainCoroutineContextFactory.create(globalCoroutinesModule, provider4));
            this.mainCoroutineContextProvider = provider5;
            this.applicationScopeProvider = DoubleCheck.provider((Provider) GlobalCoroutinesModule_ApplicationScopeFactory.create(globalCoroutinesModule, provider5));
            Provider provider6 = DoubleCheck.provider((Provider) SysUICoroutinesModule_BgDispatcherFactory.create(sysUICoroutinesModule));
            this.bgDispatcherProvider = provider6;
            Provider provider7 = DoubleCheck.provider((Provider) MultiUserUtilsModule_ProvideUserTrackerFactory.create(this.provideContextProvider, this.provideUserManagerProvider, this.provideIActivityManagerProvider, this.dumpManagerProvider, this.applicationScopeProvider, provider6, this.provideBgHandlerProvider));
            this.provideUserTrackerProvider = provider7;
            this.secureSettingsImplProvider = SecureSettingsImpl_Factory.create(this.provideContentResolverProvider, provider7);
            GlobalSettingsImpl_Factory globalSettingsImpl_FactoryCreate = GlobalSettingsImpl_Factory.create(this.provideContentResolverProvider);
            this.globalSettingsImplProvider = globalSettingsImpl_FactoryCreate;
            this.deviceProvisionedControllerImplProvider = DoubleCheck.provider((Provider) DeviceProvisionedControllerImpl_Factory.create(this.secureSettingsImplProvider, globalSettingsImpl_FactoryCreate, this.provideUserTrackerProvider, this.dumpManagerProvider, this.provideBgHandlerProvider, this.provideMainExecutorProvider));
        }

        private void initialize2(FrameworkServicesModule frameworkServicesModule, GlobalCoroutinesModule globalCoroutinesModule, DependencyProvider dependencyProvider, SharedLibraryModule sharedLibraryModule, SystemUIFactory.ContextHolder contextHolder, SysUICoroutinesModule sysUICoroutinesModule) {
            this.bindDeviceProvisionedControllerProvider = DoubleCheck.provider((Provider) ReferenceSystemUIModule_BindDeviceProvisionedControllerFactory.create(this.deviceProvisionedControllerImplProvider));
            Provider provider = DoubleCheck.provider((Provider) MotoFeature_Factory.create(this.provideContextProvider));
            this.motoFeatureProvider = provider;
            Provider provider2 = DoubleCheck.provider((Provider) HardwareDisplayControllerImpl_Factory.create(this.provideContextProvider, this.provideMainHandlerProvider, this.provideBgHandlerProvider, this.taskBarServiceProxyProvider, provider));
            this.hardwareDisplayControllerImplProvider = provider2;
            this.externalDisplayTutorialManagerProvider = DoubleCheck.provider((Provider) ExternalDisplayTutorialManager_Factory.create(this.provideContextProvider, this.provideMainHandlerProvider, this.provideBgHandlerProvider, provider2, this.bindDeviceProvisionedControllerProvider));
            Provider provider3 = DoubleCheck.provider((Provider) ConcurrencyModule_ProvideBackgroundExecutorFactory.create(this.provideBgLooperProvider));
            this.provideBackgroundExecutorProvider = provider3;
            Provider provider4 = DoubleCheck.provider((Provider) DependencyProvider_ProvidesBroadcastDispatcherFactory.create(dependencyProvider, this.provideContextProvider, this.provideBgLooperProvider, provider3));
            this.providesBroadcastDispatcherProvider = provider4;
            Provider provider5 = DoubleCheck.provider((Provider) NavIconController_Factory.create(this.provideContextProvider, this.provideMainHandlerProvider, this.hardwareDisplayControllerImplProvider, this.taskBarServiceProxyProvider, provider4));
            this.navIconControllerProvider = provider5;
            this.taskBarControllerProvider = DoubleCheck.provider((Provider) TaskBarController_Factory.create(this.provideContextProvider, this.provideMainHandlerProvider, this.taskBarServiceProxyProvider, this.qSNotificationPanelControllerProvider, this.bindDeviceProvisionedControllerProvider, this.externalDisplayTutorialManagerProvider, this.qsNotificationComponentStarterProvider, provider5));
            Provider provider6 = DoubleCheck.provider((Provider) ActivityStarterDelegate_Factory.create(this.provideContextProvider, ConcurrencyModule_ProvideMainLooperFactory.create()));
            this.activityStarterDelegateProvider = provider6;
            this.recentsControllerProvider = DoubleCheck.provider((Provider) RecentsController_Factory.create(this.provideContextProvider, provider6));
            this.provideIStatusBarServiceProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideIStatusBarServiceFactory.create());
            Provider provider7 = DoubleCheck.provider((Provider) LogBufferFactory_Factory.create());
            this.logBufferFactoryProvider = provider7;
            this.provideNotificationsLogBufferProvider = DoubleCheck.provider((Provider) LogModule_ProvideNotificationsLogBufferFactory.create(provider7, NotifPipelineFlags_Factory.create()));
            this.logBufferEulogizerProvider = DoubleCheck.provider((Provider) LogBufferEulogizer_Factory.create());
            this.providesChoreographerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvidesChoreographerFactory.create(frameworkServicesModule));
            this.provideMainDelayableExecutorProvider = DoubleCheck.provider((Provider) ConcurrencyModule_ProvideMainDelayableExecutorFactory.create(ConcurrencyModule_ProvideMainLooperFactory.create()));
            this.provideUiBackgroundExecutorProvider = DoubleCheck.provider((Provider) ConcurrencyModule_ProvideUiBackgroundExecutorFactory.create());
            this.targetSdkResolverProvider = DoubleCheck.provider((Provider) TargetSdkResolver_Factory.create(this.provideContextProvider));
            this.provideNotifInflationLogBufferProvider = DoubleCheck.provider((Provider) LogModule_ProvideNotifInflationLogBufferFactory.create(this.logBufferFactoryProvider));
            this.notificationSectionsFeatureManagerProvider = DoubleCheck.provider((Provider) NotificationSectionsFeatureManager_Factory.create(DeviceConfigProxy_Factory.create(), this.provideContextProvider));
            this.provideDevicePolicyManagerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideDevicePolicyManagerFactory.create(this.provideContextProvider));
            this.provideKeyguardManagerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideKeyguardManagerFactory.create(this.provideContextProvider));
            this.provideLockPatternUtilsProvider = DependencyProvider_ProvideLockPatternUtilsFactory.create(dependencyProvider, this.provideContextProvider);
            this.provideResourcesProvider = FrameworkServicesModule_ProvideResourcesFactory.create(this.provideContextProvider);
            this.systemPropertiesHelperProvider = DoubleCheck.provider((Provider) SystemPropertiesHelper_Factory.create());
        }

        private void initialize3(FrameworkServicesModule frameworkServicesModule, GlobalCoroutinesModule globalCoroutinesModule, DependencyProvider dependencyProvider, SharedLibraryModule sharedLibraryModule, SystemUIFactory.ContextHolder contextHolder, SysUICoroutinesModule sysUICoroutinesModule) {
            this.featureFlagsClassicReleaseProvider = DoubleCheck.provider((Provider) FeatureFlagsClassicRelease_Factory.create(this.provideResourcesProvider, this.systemPropertiesHelperProvider));
            Provider provider = DoubleCheck.provider((Provider) LogModule_ProvideNotificationRemoteInputLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideNotificationRemoteInputLogBufferProvider = provider;
            this.remoteInputControllerLoggerProvider = DoubleCheck.provider((Provider) RemoteInputControllerLogger_Factory.create(provider));
            this.javaAdapterProvider = DoubleCheck.provider((Provider) JavaAdapter_Factory.create(this.applicationScopeProvider));
            this.provideLauncherAppsProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideLauncherAppsFactory.create(this.provideContextProvider));
            Provider provider2 = DoubleCheck.provider((Provider) ConcurrencyModule_ProvideNotifInflationLooperFactory.create(this.provideBgLooperProvider));
            this.provideNotifInflationLooperProvider = provider2;
            this.provideNotifInflationExecutorProvider = DoubleCheck.provider((Provider) ConcurrencyModule_ProvideNotifInflationExecutorFactory.create(provider2));
            this.smartReplyConstantsProvider = DoubleCheck.provider((Provider) SmartReplyConstants_Factory.create(this.provideMainExecutorProvider, this.provideContextProvider, DeviceConfigProxy_Factory.create()));
            this.provideActivityManagerWrapperProvider = DoubleCheck.provider((Provider) SharedLibraryModule_ProvideActivityManagerWrapperFactory.create(sharedLibraryModule));
            this.providePackageManagerWrapperProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvidePackageManagerWrapperFactory.create());
            this.provideDevicePolicyManagerWrapperProvider = DoubleCheck.provider((Provider) SharedLibraryModule_ProvideDevicePolicyManagerWrapperFactory.create(sharedLibraryModule));
            this.provideConfigurationControllerProvider = DoubleCheck.provider((Provider) DependencyProvider_ProvideConfigurationControllerFactory.create(dependencyProvider, this.provideContextProvider));
            this.provideNotificationMessagingUtilProvider = DependencyProvider_ProvideNotificationMessagingUtilFactory.create(dependencyProvider, this.provideContextProvider);
            this.bgCoroutineContextProvider = DoubleCheck.provider((Provider) SysUICoroutinesModule_BgCoroutineContextFactory.create(sysUICoroutinesModule, this.tracingCoroutineContextProvider, this.bgDispatcherProvider));
            this.mediaFlagsProvider = DoubleCheck.provider((Provider) MediaFlags_Factory.create(this.featureFlagsClassicReleaseProvider));
            this.mediaBrowserFactoryProvider = MediaBrowserFactory_Factory.create(this.provideContextProvider);
            Provider provider3 = DoubleCheck.provider((Provider) LogModule_ProvideMediaBrowserBufferFactory.create(this.logBufferFactoryProvider));
            this.provideMediaBrowserBufferProvider = provider3;
            Provider provider4 = DoubleCheck.provider((Provider) ResumeMediaBrowserLogger_Factory.create(provider3));
            this.resumeMediaBrowserLoggerProvider = provider4;
            ResumeMediaBrowserFactory_Factory resumeMediaBrowserFactory_FactoryCreate = ResumeMediaBrowserFactory_Factory.create(this.provideContextProvider, this.mediaBrowserFactoryProvider, provider4);
            this.resumeMediaBrowserFactoryProvider = resumeMediaBrowserFactory_FactoryCreate;
            this.mediaResumeListenerProvider = DoubleCheck.provider((Provider) MediaResumeListener_Factory.create(this.provideContextProvider, this.providesBroadcastDispatcherProvider, this.provideUserTrackerProvider, this.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, resumeMediaBrowserFactory_FactoryCreate, this.dumpManagerProvider, this.bindSystemClockProvider, this.mediaFlagsProvider));
            this.provideMediaSessionManagerProvider = FrameworkServicesModule_ProvideMediaSessionManagerFactory.create(this.provideContextProvider);
            Provider provider5 = DoubleCheck.provider((Provider) LogModule_ProvideWakeLockLogFactory.create(this.logBufferFactoryProvider));
            this.provideWakeLockLogProvider = provider5;
            WakeLockLogger_Factory wakeLockLogger_FactoryCreate = WakeLockLogger_Factory.create(provider5);
            this.wakeLockLoggerProvider = wakeLockLogger_FactoryCreate;
            WakeLock_Builder_Factory wakeLock_Builder_FactoryCreate = WakeLock_Builder_Factory.create(this.provideContextProvider, wakeLockLogger_FactoryCreate);
            this.builderProvider = wakeLock_Builder_FactoryCreate;
            this.broadcastSenderProvider = DoubleCheck.provider((Provider) BroadcastSender_Factory.create(this.provideContextProvider, wakeLock_Builder_FactoryCreate, this.provideBackgroundExecutorProvider));
        }

        private void initialize4(FrameworkServicesModule frameworkServicesModule, GlobalCoroutinesModule globalCoroutinesModule, DependencyProvider dependencyProvider, SharedLibraryModule sharedLibraryModule, SystemUIFactory.ContextHolder contextHolder, SysUICoroutinesModule sysUICoroutinesModule) {
            Provider provider = DoubleCheck.provider((Provider) SystemUIRootComponent_SystemUIRootComponentExtraModule_ProvideUiEventLoggerFactory.create());
            this.provideUiEventLoggerProvider = provider;
            this.mediaUiEventLoggerProvider = DoubleCheck.provider((Provider) MediaUiEventLogger_Factory.create(provider));
            this.provideNotificationHeadsUpLogBufferProvider = DoubleCheck.provider((Provider) LogModule_ProvideNotificationHeadsUpLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideNotifInteractionLogBufferProvider = DoubleCheck.provider((Provider) LogModule_ProvideNotifInteractionLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideAllowNotificationLongPressProvider = DoubleCheck.provider((Provider) DependencyProvider_ProvideAllowNotificationLongPressFactory.create());
            this.dynamicPrivacyControllerProvider = DoubleCheck.provider((Provider) DynamicPrivacyController_Factory.create());
            this.colorUpdateLoggerProvider = DoubleCheck.provider((Provider) ColorUpdateLogger_Factory.create(this.dumpManagerProvider));
            this.provideViewConfigurationProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideViewConfigurationFactory.create(this.provideContextProvider));
            this.groupMembershipManagerImplProvider = DoubleCheck.provider((Provider) GroupMembershipManagerImpl_Factory.create());
            this.visibilityLocationProviderDelegatorProvider = DoubleCheck.provider((Provider) VisibilityLocationProviderDelegator_Factory.create());
            this.provideIDreamManagerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideIDreamManagerFactory.create());
            this.activityIntentHelperProvider = DoubleCheck.provider((Provider) ActivityIntentHelper_Factory.create(this.provideContextProvider));
            this.provideBackgroundDelayableExecutorProvider = DoubleCheck.provider((Provider) ConcurrencyModule_ProvideBackgroundDelayableExecutorFactory.create(this.provideBgLooperProvider));
            this.provideInteractionJankMonitorProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideInteractionJankMonitorFactory.create());
            this.provideNotificationRenderLogBufferProvider = DoubleCheck.provider((Provider) LogModule_ProvideNotificationRenderLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideShadeLogBufferProvider = DoubleCheck.provider((Provider) LogModule_ProvideShadeLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideMediaProjectionManagerProvider = FrameworkServicesModule_ProvideMediaProjectionManagerFactory.create(this.provideContextProvider);
            this.providePackageManagerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvidePackageManagerFactory.create(this.provideContextProvider));
            Provider provider2 = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideTelephonyManagerFactory.create(this.provideContextProvider));
            this.provideTelephonyManagerProvider = provider2;
            this.sensitiveNotificationProtectionControllerImplProvider = DoubleCheck.provider((Provider) SensitiveNotificationProtectionControllerImpl_Factory.create(this.provideContextProvider, this.globalSettingsImplProvider, this.provideMediaProjectionManagerProvider, this.provideIActivityManagerProvider, this.providePackageManagerProvider, provider2, this.provideMainHandlerProvider, this.provideBackgroundExecutorProvider));
            this.providePowerManagerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvidePowerManagerFactory.create(this.provideContextProvider));
            this.provideAmbientDisplayConfigurationProvider = FrameworkServicesModule_ProvideAmbientDisplayConfigurationFactory.create(frameworkServicesModule, this.provideContextProvider);
            this.provideNotificationInterruptLogBufferProvider = DoubleCheck.provider((Provider) LogModule_ProvideNotificationInterruptLogBufferFactory.create(this.logBufferFactoryProvider));
            this.extensionControllerImplProvider = DoubleCheck.provider((Provider) ExtensionControllerImpl_Factory.create(this.provideContextProvider, this.provideConfigurationControllerProvider));
            this.systemSettingsImplProvider = SystemSettingsImpl_Factory.create(this.provideContentResolverProvider, this.provideUserTrackerProvider);
        }

        private void initialize5(FrameworkServicesModule frameworkServicesModule, GlobalCoroutinesModule globalCoroutinesModule, DependencyProvider dependencyProvider, SharedLibraryModule sharedLibraryModule, SystemUIFactory.ContextHolder contextHolder, SysUICoroutinesModule sysUICoroutinesModule) {
            this.provideIPackageManagerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideIPackageManagerFactory.create());
            this.assistantFeedbackControllerProvider = DoubleCheck.provider((Provider) AssistantFeedbackController_Factory.create(this.provideMainHandlerProvider, this.provideContextProvider, DeviceConfigProxy_Factory.create()));
            this.selectedUserInteractorProvider = DoubleCheck.provider((Provider) SelectedUserInteractor_Factory.create());
            this.provideAccessibilityManagerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideAccessibilityManagerFactory.create(this.provideContextProvider));
            this.notificationSettingsControllerProvider = DoubleCheck.provider((Provider) NotificationSettingsController_Factory.create(this.provideUserTrackerProvider, this.provideMainHandlerProvider, this.provideBgHandlerProvider, this.secureSettingsImplProvider, this.dumpManagerProvider));
            this.imageLoaderProvider = DoubleCheck.provider((Provider) ImageLoader_Factory.create(this.provideContextProvider, this.bgDispatcherProvider));
            this.provideLatencyTrackerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideLatencyTrackerFactory.create(this.provideContextProvider));
            Provider provider = DoubleCheck.provider((Provider) GlobalCoroutinesModule_MainDispatcherFactory.create(globalCoroutinesModule));
            this.mainDispatcherProvider = provider;
            this.bigPictureStatsManagerProvider = DoubleCheck.provider((Provider) BigPictureStatsManager_Factory.create(this.provideLatencyTrackerProvider, provider, this.dumpManagerProvider));
            this.provideShortcutManagerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideShortcutManagerFactory.create(this.provideContextProvider));
            this.dndQSTileControllerProvider = DoubleCheck.provider((Provider) DndQSTileController_Factory.create(this.taskBarServiceProxyProvider));
            this.bluetoothQSTileControllerProvider = DoubleCheck.provider((Provider) BluetoothQSTileController_Factory.create(this.taskBarServiceProxyProvider));
            Provider provider2 = DoubleCheck.provider((Provider) WifiStatusMonitor_Factory.create(this.provideContextProvider));
            this.wifiStatusMonitorProvider = provider2;
            this.wifiQSTileControllerProvider = DoubleCheck.provider((Provider) WifiQSTileController_Factory.create(this.taskBarServiceProxyProvider, provider2));
            this.cellQSTileControllerProvider = DoubleCheck.provider((Provider) CellQSTileController_Factory.create(this.taskBarServiceProxyProvider));
            this.hotspotQSTileControllerProvider = DoubleCheck.provider((Provider) HotspotQSTileController_Factory.create(this.taskBarServiceProxyProvider));
            this.airplaneQSTileControllerProvider = DoubleCheck.provider((Provider) AirplaneQSTileController_Factory.create(this.taskBarServiceProxyProvider));
            this.screenshotControllerProvider = DoubleCheck.provider((Provider) ScreenshotController_Factory.create(this.provideContextProvider, this.taskBarServiceProxyProvider, this.provideMainHandlerProvider));
            this.screenRecordQSTileControllerProvider = DoubleCheck.provider((Provider) ScreenRecordQSTileController_Factory.create(this.taskBarServiceProxyProvider));
            Provider provider3 = DoubleCheck.provider((Provider) LogModule_ProvideMediaViewLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideMediaViewLogBufferProvider = provider3;
            this.mediaViewLoggerProvider = DoubleCheck.provider((Provider) MediaViewLogger_Factory.create(provider3));
            this.provideBackgroundRepeatableExecutorProvider = DoubleCheck.provider((Provider) ConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory.create(this.provideBackgroundDelayableExecutorProvider));
            Provider provider4 = DoubleCheck.provider((Provider) LogModule_ProvideMediaCarouselControllerBufferFactory.create(this.logBufferFactoryProvider));
            this.provideMediaCarouselControllerBufferProvider = provider4;
            this.mediaCarouselControllerLoggerProvider = DoubleCheck.provider((Provider) MediaCarouselControllerLogger_Factory.create(provider4));
            this.provideTimeTickHandlerProvider = DoubleCheck.provider((Provider) DependencyProvider_ProvideTimeTickHandlerFactory.create(dependencyProvider));
        }

        private void initialize6(FrameworkServicesModule frameworkServicesModule, GlobalCoroutinesModule globalCoroutinesModule, DependencyProvider dependencyProvider, SharedLibraryModule sharedLibraryModule, SystemUIFactory.ContextHolder contextHolder, SysUICoroutinesModule sysUICoroutinesModule) {
            this.provideAlarmManagerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideAlarmManagerFactory.create(this.provideContextProvider));
            Provider provider = DoubleCheck.provider((Provider) TaskController_Factory.create(this.provideContextProvider, ConcurrencyModule_ProvideMainLooperFactory.create(), this.provideBgLooperProvider));
            this.taskControllerProvider = provider;
            this.taskSyncreticControllerProvider = DoubleCheck.provider((Provider) TaskSyncreticController_Factory.create(this.provideContextProvider, provider, ConcurrencyModule_ProvideMainLooperFactory.create(), this.provideBgLooperProvider, this.motoFeatureProvider));
            Provider provider2 = DoubleCheck.provider((Provider) RecordManager_Factory.create(this.provideContextProvider));
            this.recordManagerProvider = provider2;
            this.shortcutKeyDispatcherProvider = DoubleCheck.provider((Provider) ShortcutKeyDispatcher_Factory.create(this.provideContextProvider, this.qSNotificationPanelControllerProvider, this.taskBarControllerProvider, this.recentsControllerProvider, this.bindDeviceProvisionedControllerProvider, this.provideKeyguardManagerProvider, provider2));
            Provider provider3 = DoubleCheck.provider((Provider) DisplayConfigurationController_Factory.create(this.provideContextProvider, this.provideMainHandlerProvider));
            this.displayConfigurationControllerProvider = provider3;
            this.mirrorPhonePanelControllerProvider = DoubleCheck.provider((Provider) MirrorPhonePanelController_Factory.create(this.provideContextProvider, this.provideMainHandlerProvider, this.provideConfigurationControllerProvider, provider3));
            this.provideIWindowManagerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideIWindowManagerFactory.create());
            Provider provider4 = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideWindowManagerFactory.create(this.provideContextProvider));
            this.provideWindowManagerProvider = provider4;
            this.provideDisplayMetricsProvider = DoubleCheck.provider((Provider) DependencyProvider_ProvideDisplayMetricsFactory.create(dependencyProvider, this.provideContextProvider, provider4));
            this.provideINotificationManagerProvider = DoubleCheck.provider((Provider) FrameworkServicesModule_ProvideINotificationManagerFactory.create(frameworkServicesModule));
            this.externalDisplayModeManagerProvider = DoubleCheck.provider((Provider) ExternalDisplayModeManager_Factory.create(this.provideContextProvider));
            this.displayWindowManagerProvider = DoubleCheck.provider((Provider) DisplayWindowManager_Factory.create(this.provideContextProvider, this.provideBgHandlerProvider));
            this.glassesMonitorProvider = DoubleCheck.provider((Provider) GlassesMonitor_Factory.create(this.provideContextProvider, this.taskBarServiceProxyProvider, this.motoFeatureProvider));
            this.trackpadGestureHandlerProvider = DoubleCheck.provider((Provider) TrackpadGestureHandler_Factory.create(this.provideContextProvider, this.motoFeatureProvider));
            this.readyForProxyProvider = DoubleCheck.provider((Provider) ReadyForProxy_Factory.create(this.provideContextProvider, ConcurrencyModule_ProvideHandlerFactory.create(), this.motoFeatureProvider, this.taskBarServiceProxyProvider, this.hardwareDisplayControllerImplProvider, this.trackpadGestureHandlerProvider, this.taskBarControllerProvider));
            this.uiOffloadThreadProvider = DoubleCheck.provider((Provider) UiOffloadThread_Factory.create());
        }

        private RecentsActivity injectRecentsActivity(RecentsActivity recentsActivity) {
            RecentsActivity_MembersInjector.injectRecentsController(recentsActivity, (RecentsController) this.recentsControllerProvider.get());
            RecentsActivity_MembersInjector.injectSurfaceBlurController(recentsActivity, new SurfaceBlurController());
            return recentsActivity;
        }

        private TaskBarChildUserServerService injectTaskBarChildUserServerService(TaskBarChildUserServerService taskBarChildUserServerService) {
            TaskBarChildUserServerService_MembersInjector.injectMTaskBarServiceProxy(taskBarChildUserServerService, (TaskBarServiceProxy) this.taskBarServiceProxyProvider.get());
            TaskBarChildUserServerService_MembersInjector.injectMDeviceProvisionedController(taskBarChildUserServerService, (DeviceProvisionedController) this.bindDeviceProvisionedControllerProvider.get());
            TaskBarChildUserServerService_MembersInjector.injectMBgHandler(taskBarChildUserServerService, backgroundHandler());
            return taskBarChildUserServerService;
        }

        private TaskBarService injectTaskBarService(TaskBarService taskBarService) {
            TaskBarService_MembersInjector.injectMTaskBarServiceProxy(taskBarService, (TaskBarServiceProxy) this.taskBarServiceProxyProvider.get());
            return taskBarService;
        }

        @Override // com.android.systemui.dagger.SystemUIRootComponent
        public Dependency.DependencyInjector createDependency() {
            return new D_DependencyInjectorImpl(this.systemUIRootComponentImpl);
        }

        @Override // com.android.systemui.dagger.SystemUIRootComponent
        public void inject(TaskBarChildUserServerService taskBarChildUserServerService) {
            injectTaskBarChildUserServerService(taskBarChildUserServerService);
        }

        @Override // com.android.systemui.dagger.SystemUIRootComponent
        public void inject(TaskBarService taskBarService) {
            injectTaskBarService(taskBarService);
        }

        @Override // com.android.systemui.dagger.SystemUIRootComponent
        public void inject(RecentsActivity recentsActivity) {
            injectRecentsActivity(recentsActivity);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
