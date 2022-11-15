package skycat.mystical.curses;

import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import skycat.mystical.LogLevel;
import skycat.mystical.Utils;

import java.util.ArrayList;
import java.util.Random;

public class CurseHandler implements EntitySleepEvents.StartSleeping, PlayerBlockBreakEvents.Before {
    ArrayList<Curse> activeCurses = new ArrayList<>();
    ArrayList<CurseConsequence> consequences = new ArrayList<>();
    ArrayList<CurseRemovalCondition> removalConditions = new ArrayList<>();
    Random random = new Random();

    public CurseHandler() {
        initializeConsequences();
        initializeRemovalConditions();
    }

    @Override
    public boolean beforeBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        for (Curse curse : cursesOfConsequence(PlayerBlockBreakEvents.Before.class)) {
            boolean cancel = !((PlayerBlockBreakEvents.Before)curse.consequence.callback).beforeBlockBreak(world, player, pos, state, blockEntity);
            if (cancel) {
                return false;
            }
        }
        return true;
    }

    public void doNighttimeEvents() {
        removeFulfilledCurses();
    }

    private void initializeConsequences() {
        // Pool of consequences goes here
    }

    private void initializeRemovalConditions() {
        // Pool of removal conditions goes here
    }

    @Override
    public void onStartSleeping(LivingEntity entity, BlockPos sleepingPos) {
        for (Curse curse : cursesOfConsequence(EntitySleepEvents.StartSleeping.class)) {
            ((EntitySleepEvents.StartSleeping)curse.consequence.callback).onStartSleeping(entity, sleepingPos);
        }
    }

    public <T> void onStatIncreased(Stat<T> stat, int amount) {

    }

    private void removeFulfilledCurses() {
        // CREDIT https://stackoverflow.com/a/1196612, then IntelliJ being like hey do this instead
        activeCurses.removeIf(curse -> curse.removalCondition.isFulfilled());
    }

    /**
     * Get active curses with consequences of type clazz
     * @return A new ArrayList
     * @param clazz The type of consequence
     * @param <T> Consequence type
     */
    public <T> ArrayList<Curse> cursesOfConsequence(Class<T> clazz) {
        ArrayList<Curse> matchingCurses = new ArrayList<>();
        for (Curse curse : activeCurses) {
            if (curse.consequence.callback.getClass().equals(clazz)) {
                matchingCurses.add(curse);
            }
        }
        return matchingCurses;
    }

    public <T> ArrayList<Curse> cursesOfConditions(Stat<T> stat) {
        ArrayList<Curse> matchingCurses = new ArrayList<>();
        for (Curse curse : activeCurses) {
            // TODO
        }
        return matchingCurses;
    }

    /**
     * Get a new curse, with consequence and removal condition randomly selected
     * @return A new curse
     */
    private Curse makeNewCurse() {
        if (removalConditions.size() == 0) { // Ideally, this should not be reachable without editing the pool manually.
            Utils.log("Tried to make a new curse, but the size of the removal condition pool was 0.", LogLevel.WARN);
            return null;
        }
        if (consequences.size() == 0) { // Ideally, this should not be reachable without editing the pool manually.
            Utils.log("Tried to make a new curse, but the size of the consequences pool was 0.", LogLevel.WARN);
            return null;
        }
        Utils.log("Making a new random curse.", LogLevel.DEBUG);
        return new Curse(
          consequences.get(random.nextInt(0, consequences.size())),
          removalConditions.get(random.nextInt(0, removalConditions.size()))
        );
    }

    /**
     * Get a new curse, targeting a difficulty (consequence difficulty * removal difficulty).
     * @param difficultyTarget
     * @return A new curse
     */
    private Curse makeNewCurse(int difficultyTarget) {
        return null; // TODO
    }
}
