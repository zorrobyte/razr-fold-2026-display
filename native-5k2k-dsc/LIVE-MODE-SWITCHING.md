# Live (no‑replug) external mode switching + the bandwidth/lane reality

> How to change the external DisplayPort resolution/refresh **without physically unplugging the
> monitor**, why it works, exactly which modes are reachable on which cable, and where the real
> ceilings are (link bandwidth, DSC floor, DPU pixel clock, DP version). All numbers below were
> measured on‑device (blanc / SM8845, build `W3WBS36.36-48-5-1`), 2026‑07.

---

## TL;DR

- **Live no‑replug switch works.** Inject the mode into `edid_modes`, then cycle the QTI `hpd` node
  **0 → 1** *without* touching `skip_uevent`. The driver re‑probes, re‑runs **DSC negotiation**, and
  the framework re‑adds the display at the new timing — no physical replug.
- This **corrects the old gotcha** that said "cycling `hpd` wedges the display." It wedges only if you
  set `skip_uevent=1` (which suppresses the reconnect uevent) or never send the `1` after the `0`.
- **Which modes you can reach is a pure bandwidth question**, gated by the *current trained link*
  (lane count is not renegotiated by a sim‑HPD). On a **2‑lane** dock you can reach 5120×2160**@60**;
  **5120×2160@100 needs 4 lanes** (a direct USB‑C→DP cable) — no software trick beats this.

---

## 1. The working recipe

```sh
adb shell su -c '
  mount -t debugfs none /sys/kernel/debug 2>/dev/null
  echo "5120 2160 60 0" > /sys/kernel/debug/drm_dp/edid_modes   # W H R aspect
  echo 0 > /sys/kernel/debug/drm_dp/hpd                          # sim-HPD disconnect
  sleep 1
  echo 1 > /sys/kernel/debug/drm_dp/hpd                          # sim-HPD reconnect -> re-probe
'
# verify:
adb shell su -c 'cat /sys/kernel/debug/drm_dp/dp_debug | grep -E "resolution|num_lanes|bpp"'
```

Rules that make it reliable:

- **Do NOT set `skip_uevent=1`.** With it set, the reconnect uevent is suppressed and the Android
  framework never re‑adds the display → looks wedged (black, `status=disconnected`). Recover by
  `echo 0 > skip_uevent` then `echo 1 > hpd`.
- Always pair `0` **then** `1`. A lone `0` leaves the panel disconnected.
- Clear an override with `echo "0 0 0 0" > edid_modes` (then an `hpd` 0→1 to fall back to the
  EDID‑preferred mode).
- Proven live‑applied with no replug: 1920×1080@60, and **5120×2160@60 with DSC** (a DSC‑required mode
  — confirming sim‑HPD *does* run the DSC path, it is not a "dumb" power toggle).

### What does NOT work (and why)

| Attempt | Result | Reason |
|---|---|---|
| `cmd display set-user-preferred-display-mode W H R <id>` | records the pref, **never applies** | SurfaceFlinger refuses it for external displays: `W SurfaceFlinger: setDesiredMode: Attempted to set desired mode for external display …` (it even bounced the *cover* panel's refresh instead). Moto desktop displays carry `FLAG_MOTO_DESKTOP` / `FLAG_OWN_DISPLAY_GROUP` and own the modeset. |
| `cmd display disable-display <id>` / `enable-display <id>` | blanks + relights, **same mode** | dpms power toggle only. The DP link stays `connected` (`status=connected`, `drm_dp/connected=1`), so `edid_modes` is **not** re‑read. |
| `hpd` 0→1 **with** `skip_uevent=1` | driver re‑probes but display looks dead | reconnect uevent suppressed → framework doesn't re‑add the display. |

---

## 2. The bandwidth math — which modes fit which cable

DisplayPort link payload after 8b/10b (HBR3 = 8.1 Gbps/lane):

| Trained link | Payload |
|---|---|
| **2‑lane** HBR3 (typical dock) | **~12.96 Gbps** |
| **4‑lane** HBR3 (direct USB‑C→DP cable) | **~25.92 Gbps** |

The DP driver's **DSC compressed‑bpp floor is 18** (hard limit — measured):

```
[drm:dp_panel_get_supported_bpp] bpp 8 is below minimum supported bpp 18
[drm:dp_panel_get_supported_bpp] bpp 8 is below minimum supported bpp 24
[drm:dp_panel_get_supported_bpp] bpp 8 is not supported when dsc is enabled
[drm:_get_rc_table_index] unsupported DSC v1.2r0, bpc:2, bpp:8, fmt:0x0
```

So the *most* it will ever compress to is 18 bpp. Bandwidth needed = `W × H × Hz × bpp`:

| Mode | @18 bpp (DSC floor) | Fits 2‑lane (12.96)? | Fits 4‑lane (25.92)? |
|---|---|---|---|
| 5120×2160 **@60** | **~11.9 Gbps** | ✅ (barely — this is why the dock does @60) | ✅ |
| 5120×2160 **@100** | **~19.9 Gbps** | ❌ **impossible at any compression** | ✅ (this is why the direct cable does @100) |
| 5120×2160 **@120** | **~23.9 Gbps** | ❌ | ✅ *link‑wise* — but see §4 (DPU ceiling) |

**Conclusion:** the reason 5120@100 falls back to the 640×480 failsafe on a dock is not the driver, not
DSC quality, not my method — it's that **19.9 Gbps > 12.96 Gbps** even at the DSC floor. Only a 4‑lane
link carries it.

### Why the dock is 2 lanes and the cable is 4

USB‑C DisplayPort Alt Mode pin assignments:
- **Direct USB‑C→DP cable** → *Pin Assignment C*: **4 lanes to DP**, USB drops to USB 2.0.
- **Dock** → *Pin Assignment D*: **2 lanes to DP + 2 lanes to the dock's USB 3 hub**.

On most docks this is **physical wiring** — only two lanes are routed to the DP connector — so no
root/PD trick reclaims them. `sim‑hpd` reuses whatever lane count is already trained (it does not
renegotiate USB‑C alt‑mode), so a dock stays 2‑lane across a sim re‑probe. A **physical** replug
re‑runs alt‑mode negotiation, which is the only way a dock↔cable lane change happens.

---

## 3. Debugfs override behaviour (measured quirks)

- `max_pclk_khz` defaults to **675000** and **resets to 675000 on every re‑probe** (writing `1300000`
  does not stick). It is **not hard‑enforced** on the validated path anyway — 5120@60 runs at
  `pclock=704180KHz`, above the "cap". Treat it as advisory, not a real lever.
- `max_bpp` is the DSC target bpp. Setting it **below 18** (e.g. 8) makes every mode fail
  `mode_valid` → failsafe **640×480**. Leave it at 30.
- `lane_count` / `link_bw_code` writes did **not** override the trained link in testing — the sim
  re‑probe keeps the physically‑trained lane count.
- `dsc_feature_enable` / `fec_feature_enable` read `Y` (DSC + FEC active).

---

## 4. Where the real ceilings are (SoC: SM8845 / Snapdragon 8 Gen 5)

External‑display capabilities of the SoC (confirmed from spec sheets + inferred from the Qualcomm
mobile DP lineage — Qualcomm has not published a standalone SM8845 display datasheet):

| Capability | Value |
|---|---|
| DisplayPort version | **DP 1.4** over USB‑C Alt Mode |
| Max link rate | **HBR3 = 8.1 Gbps/lane** (25.92 Gbps payload @ 4 lanes). **No UHBR / DP 2.x.** |
| DSC | **DSC 1.2** (mandatory — advertised modes exceed raw HBR3) |
| DP lanes | up to **4** |
| USB4 / Thunderbolt | **No** (USB 3.1 Gen2 max) |
| Official external ceiling | **4K@120** or **8K@30**, 10‑bit HDR |

**Implications for 5K2K:**
- **Link bandwidth is not the wall** for 5120@100 — 4‑lane HBR3 + DSC carries it fine (measured on the
  direct cable). **UHBR is not required and not available.** A MacBook doing 5K@120 uses DP 2.1 UHBR /
  Thunderbolt (far higher per‑lane rate) — a different class of link this SoC does not have, but also
  doesn't need for @100.
- The next real wall for pushing past @100 is the **DPU/MDP pixel‑clock ceiling**, not the DP link:
  4K@120 ≈ 1.15 Gpix/s (advertised max), while 5120×2160@100 ≈ 1.24 and @120 ≈ 1.49 Gpix/s. Beyond
  ~@100 you're in the same **dual‑DSC / dual‑pipe‑merge** territory as the internal‑panel
  `max_dsc_count=4` patch, not a bandwidth problem.

Sources: [gadgetversus SM8845](https://gadgetversus.com/processor/qualcomm-sm8845-snapdragon-8-gen-5-specs/),
[Qualcomm Snapdragon 8 Gen 5](https://www.qualcomm.com/smartphones/products/8-series/snapdragon-8-gen-5-mobile-platform),
[DisplayPort HBR3/DSC (Wikipedia)](https://en.wikipedia.org/wiki/DisplayPort).

---

## 5. What this means for the app

- **Replace the "tap → unplug + replug" flow with the live `hpd` 0→1 re‑probe** for every mode that
  fits the current link. No physical replug needed — including **5120@100 when on the 4‑lane direct
  cable** (sim‑HPD keeps the trained 4 lanes and re‑runs DSC).
- **Gate by bandwidth, not by a hardcoded list:** compute `W×H×Hz×18` and compare to the current
  link payload (`num_lanes × 8.1e9 × 0.8`). Grey out / warn on modes that can't fit the *current*
  cable (e.g. 5120@100 on a 2‑lane dock) instead of letting them fail to 640×480.
- Detect lane count from `dp_debug` (`num_lanes`) and surface it ("2‑lane dock — 5K capped at 60 Hz;
  use a direct cable for 100 Hz").
- Keep a **physical‑replug fallback** only for the one case sim‑HPD can't do: forcing a **lane‑count
  change** (2→4), which requires real alt‑mode renegotiation.

---

## 6. HDCP (still open)

On this link HDCP 2.2 is stuck in a retry loop — `HDCP_VERSION_2P2: HDCP_STATE_AUTH_FAIL`, source
`caps: 2` (2.2 capable). Observed on the 2‑lane link and independent of resolution; looks like an
LG‑over‑DP‑alt interop / key‑provisioning issue rather than a bandwidth one. See the main README §9
HDCP note. Not yet resolved.
