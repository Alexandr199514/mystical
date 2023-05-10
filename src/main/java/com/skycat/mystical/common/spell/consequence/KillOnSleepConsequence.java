package com.skycat.mystical.common.spell.consequence;

import com.skycat.mystical.Mystical;
import lombok.NonNull;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class KillOnSleepConsequence extends SpellConsequence implements EntitySleepEvents.StartSleeping {
    public static final ConsequenceFactory<KillOnSleepConsequence> FACTORY = new Factory();

    @Override
    public @NotNull ConsequenceFactory<KillOnSleepConsequence> getFactory() {
        return FACTORY;
    }

    protected KillOnSleepConsequence() {
        super(KillOnSleepConsequence.class, EntitySleepEvents.StartSleeping.class, 1000d);
    }

    @Override
    public void onStartSleeping(LivingEntity entity, BlockPos sleepingPos) {
        if (entity.isAlive() && !Mystical.HAVEN_MANAGER.isInHaven(entity)) {
            entity.kill();
        }
    }

    private static class Factory extends ConsequenceFactory<KillOnSleepConsequence> {
        private Factory() {
            super("killOnSleep", "Kill on sleep", "Bigger bedbugs", "Killed an entity for sleeping", KillOnSleepConsequence.class);
        }

        @Override
        public @NotNull KillOnSleepConsequence make(@NonNull Random random, double points) {
            return new KillOnSleepConsequence();
        }

        @Override
        public double getWeight() {
            return 0; // WARN debug
        }
    }
}