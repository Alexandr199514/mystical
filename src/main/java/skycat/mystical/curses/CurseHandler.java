package skycat.mystical.curses;

import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class CurseHandler implements EntitySleepEvents.StartSleeping {
    public final ArrayList<Curse<?>> curses = new ArrayList<>();

    public CurseHandler() {
        // Initialize curses
        Curse<EntitySleepEvents.StartSleeping> curse = new Curse<>(
                EntitySleepEvents.START_SLEEPING,
                (entity, sleepingPos) -> {
                    entity.kill();
                },
                new CurseRemovalCondition<>(Stats.MINED, Blocks.STONE, 10, 0),
                1.0);
        curses.add(curse);
    }

    public void doNighttimeEvents() {
        removeFulfilledCurses();
    }

    @Override
    public void onStartSleeping(LivingEntity entity, BlockPos sleepingPos) {
        for (Curse<EntitySleepEvents.StartSleeping> curse : getCursesOfType(EntitySleepEvents.StartSleeping.class)) {
            if (curse.enabled) {
                curse.callback.onStartSleeping(entity, sleepingPos);
            } // TODO probably delete disabled curses
        }
    }

    public <T> ArrayList<Curse<T>> getCursesOfType(Class<T> clazz) {
        ArrayList<Curse<T>> matchingCurses = new ArrayList<>();
        return matchingCurses;
    }



    /**
     * Update curses and their removal conditions. Will not remove the effects of curses.
     */
    public void updateCurseFulfillment() {

    }

    public <T> void statIncreased(Stat<T> stat, int amount) {}

    public void removeFulfilledCurses() {
        for (Curse<?> curse : curses) {
            if (curse.removalCondition.isFulfilled()) {
                curse.disable();
            }
        }
    }

    // Get random curse
    // Get random curse, but weight chances based on difficulty
    // Get random curse, but weight chances to get close to a difficulty
}
