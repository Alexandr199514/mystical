package skycat.mystical;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import skycat.mystical.server.MinecraftServerTimerAccess;

public class MysticalEventHandler implements ServerLifecycleEvents.ServerStarted {
    MinecraftServer server; // TODO: maybe shouldn't just be package-private
    MinecraftServerTimerAccess timerAccess;

    @Override
    public void onServerStarted(MinecraftServer server) {
        this.server = server;
        timerAccess = ((MinecraftServerTimerAccess) server);
        setNightTimer();
        Utils.log("Time of day is " + server.getOverworld().getTimeOfDay(), Settings.LoggingSettings.getTimeOfDayAtStartup());
    }

    public void doNighttimeEvents() {
        Utils.log("Doing nighttime stuff");
        try {
            setNightTimer();
        } catch (NullPointerException e) {
            Utils.log("Couldn't set timer for night. Reason: " + e.getMessage(), Settings.LoggingSettings.getNightTimerSetFailed());
            // TODO: Try again later?
        }
    }

    /**
     * Sets the nighttime timer to the next night
     * @return Number of ticks until night, or -1 on failure
     */
    public long setNightTimer() throws NullPointerException {
        // CREDIT: Daomephsta#0044 for help on fabric discord
        if (server == null) {
            throw new NullPointerException("server was null in setNightTimer.");
        }
        if (timerAccess == null) {
            throw new NullPointerException("timerAccess was null in setNightTimer.");
        }
        long timerLength;
        long currentTime = server.getOverworld().getTimeOfDay() % 24000;
        if (currentTime > 18000) { // If we've passed midnight
            timerLength = (24000 - currentTime) + 18000; // (time left in this day) + (time from morning til midnight) = time until tomorrow's midnight
        } else { // It's before midnight
             timerLength = 18000 - currentTime; // midnight - (current time) = time until midnight
        }

        if (timerLength == 0) { // So that we don't get repeating events if this is fired twice in a tick.
            timerLength = 18000;
        }
        timerAccess.mystical_setTimer(timerLength);
        return timerLength;
    }
}