# DeX has been locked to 60Hz external for years. I got a Razr Fold's desktop mode running an external monitor at 100Hz (and 5K2K@60) — here's the comparison, and where the *real* hardware ceiling is

Posting this for the DeX crowd as a **comparison data point**, because the #1 complaint here forever has been: **DeX outputs 60Hz to external monitors, period — no high refresh, no matter your display.**

I just went deep on a different Android desktop mode — Motorola's, on a rooted Razr Fold 2026 (SD 8 Gen 5) — driving an LG 45GX950A (5K2K ultrawide). And it does things DeX won't. Sharing the numbers and the technical *why*, so you know exactly what's a Samsung software choice vs. an actual hardware wall.

---

## What the Razr's desktop mode actually did (vs DeX's 60Hz lock)

| | DeX (typical) | Razr Fold desktop mode |
|---|---|---|
| External refresh | **60Hz, locked** | **3440×1440 @ 100Hz**, plus 1080p@120, 2560×1080@120/240 |
| Max resolution | up to 4K (flagships) | **5120×2160 @ 60** / 4K@60 / 3440×1440@100 |
| Frame pacing | 60 | adaptive — boosts to panel max on interaction |

So a phone desktop mode running an external monitor at **100Hz** is clearly possible on current Android/Snapdragon hardware. **DeX's 60Hz cap is a Samsung software decision, not a silicon limit.** That's the comparison point — if you've been told "phones can't do high-refresh DeX," here's a phone doing it.

(Caveat for honesty: the Razr needed root + a framework patch to lift Android's resolution cap — see below — but the *refresh-rate* side, the 100Hz/120Hz external modes, came from Moto's desktop mode itself, not from any hack. Samsung simply chose to lock DeX at 60.)

## Two things I learned that explain a LOT of phone-desktop behavior

**1. Android hides an external-resolution cap.** There's an AOSP flag (`enable_mode_limit_for_external_display`) that drops any external mode bigger than your phone's *own screen's* pixel count. It's why stock Pixels cap external around 1440p. Samsung tunes DeX past it (hence 4K), but it's the baseline mechanism — and on my Razr I had to patch the compiled framework (root) to get 4K/5K2K at all.

**2. The "60Hz feeling" on a high-refresh panel is adaptive frame-pacing.** Even with a 100Hz external *mode* set, Android renders content at 60 when idle and **boosts to the panel max only when you interact** (mouse/scroll). TestUFO will read 60 unless you're moving the cursor mid-test. This part *is* the same on DeX — so even if Samsung unlocked the mode, the desktop would still idle at 60 to save power and boost on use. Worth knowing before you blame the cable.

## The real hardware ceiling (this part hits DeX too)

Here's where even the Razr stops, and it's universal to phones: **USB-C DisplayPort version.** It's negotiated at the USB-C Alt-Mode layer by the phone, and **phones top out at DisplayPort 1.4** (recent Samsung flagships included — that's HBR3 + DSC, which is how DeX does 4K@120-with-DSC over a good cable). **No phone does DisplayPort 2.1.**

I proved it brutally: same monitor, **same exact cable and input** — plugged into a Mac it ran **5120×2160 @ 165Hz** (DP 2.1); the phone on that identical cable maxed at 5K2K@60 and the DisplayPort AUX channel showed the monitor handing the *phone* DP 1.2 but the *Mac* DP 2.1. It's the source's USB-C controller firmware. Nothing software-side — not root, not a custom kernel — changes it.

**So for any DeX setup:** your phone is a DP-1.4 source at best. 4K@120 w/ DSC is the realistic top end on good hardware; the truly heavy stuff (5K2K@120, 8K) needs a laptop/PC with a real DP-2.1/Thunderbolt port.

## The most actionable DeX tip in here: ditch the dock for resolution

One USB-C port, 4 lanes. A dock/hub usually runs DisplayPort in **2-lane mode** (keeping 2 lanes for USB) — **half your DP bandwidth.** Measured it directly: through a Thunderbolt dock the Razr did 3440×1440@60; on a **direct USB-C→DP cable** the *same phone + monitor* jumped to 5K2K@60 / 4K@60 / 3440×1440@100. For DeX, if you want max resolution/refresh, **use a direct USB-C→DisplayPort cable, not a hub.** The hub costs you display bandwidth.

---

## Bottom line for DeX users
- **A phone desktop mode running external at 100Hz is real** (Moto does it). DeX's 60Hz lock is **Samsung's software choice, not hardware** — good ammo for feature requests.
- **Even unlocked, "60Hz feel" persists at idle** (adaptive pacing, AOSP). It boosts on interaction.
- **Direct cable > dock** for bandwidth (docks halve DisplayPort).
- **Phones max at DisplayPort 1.4.** 4K@120/DSC is the realistic ceiling; nothing phone-side reaches DP-2.1 modes.

If anyone from the DeX side knows whether newer One UI builds have *ever* shipped >60Hz external, I'd love to hear it — from the hardware side there's no reason it couldn't.
