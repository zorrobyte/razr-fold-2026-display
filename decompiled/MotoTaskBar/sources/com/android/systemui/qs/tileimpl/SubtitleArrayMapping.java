package com.android.systemui.qs.tileimpl;

import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.res.R$array;
import java.util.HashMap;

/* JADX INFO: compiled from: SubtitleArrayMapping.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SubtitleArrayMapping {
    public static final SubtitleArrayMapping INSTANCE = new SubtitleArrayMapping();
    private static final HashMap subtitleIdsMap;

    static {
        HashMap map = new HashMap();
        subtitleIdsMap = map;
        map.put("internet", Integer.valueOf(R$array.tile_states_internet));
        map.put("wifi", Integer.valueOf(R$array.tile_states_wifi));
        map.put("cell", Integer.valueOf(R$array.tile_states_cell));
        map.put("battery", Integer.valueOf(R$array.tile_states_battery));
        map.put("dnd", Integer.valueOf(R$array.tile_states_dnd));
        map.put("flashlight", Integer.valueOf(R$array.tile_states_flashlight));
        map.put("rotation", Integer.valueOf(R$array.tile_states_rotation));
        map.put("bt", Integer.valueOf(R$array.tile_states_bt));
        map.put("airplane", Integer.valueOf(R$array.tile_states_airplane));
        map.put("location", Integer.valueOf(R$array.tile_states_location));
        map.put("hotspot", Integer.valueOf(R$array.tile_states_hotspot));
        map.put("inversion", Integer.valueOf(R$array.tile_states_inversion));
        map.put("saver", Integer.valueOf(R$array.tile_states_saver));
        map.put("dark", Integer.valueOf(R$array.tile_states_dark));
        map.put("work", Integer.valueOf(R$array.tile_states_work));
        map.put("cast", Integer.valueOf(R$array.tile_states_cast));
        map.put("night", Integer.valueOf(R$array.tile_states_night));
        map.put("screenrecord", Integer.valueOf(R$array.tile_states_screenrecord));
        map.put("record_issue", Integer.valueOf(R$array.tile_states_record_issue));
        map.put("reverse", Integer.valueOf(R$array.tile_states_reverse));
        map.put("reduce_brightness", Integer.valueOf(R$array.tile_states_reduce_brightness));
        map.put("cameratoggle", Integer.valueOf(R$array.tile_states_cameratoggle));
        map.put("mictoggle", Integer.valueOf(R$array.tile_states_mictoggle));
        map.put("controls", Integer.valueOf(R$array.tile_states_controls));
        map.put("wallet", Integer.valueOf(R$array.tile_states_wallet));
        map.put("qr_code_scanner", Integer.valueOf(R$array.tile_states_qr_code_scanner));
        map.put("alarm", Integer.valueOf(R$array.tile_states_alarm));
        map.put("onehanded", Integer.valueOf(R$array.tile_states_onehanded));
        map.put("color_correction", Integer.valueOf(R$array.tile_states_color_correction));
        map.put(BcSmartspaceDataPlugin.UI_SURFACE_DREAM, Integer.valueOf(R$array.tile_states_dream));
        map.put("font_scaling", Integer.valueOf(R$array.tile_states_font_scaling));
        map.put("hearing_devices", Integer.valueOf(R$array.tile_states_hearing_devices));
    }

    private SubtitleArrayMapping() {
    }

    public final int getSubtitleId(String str) {
        Integer num;
        if (str != null && (num = (Integer) subtitleIdsMap.get(str)) != null) {
            return num.intValue();
        }
        return R$array.tile_states_default;
    }
}
