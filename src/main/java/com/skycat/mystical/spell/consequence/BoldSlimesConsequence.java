package com.skycat.mystical.spell.consequence;

import com.mojang.serialization.MapCodec;
import com.skycat.mystical.Mystical;
import lombok.NonNull;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BoldSlimesConsequence extends SpellConsequence { // TODO: Tests
    public static final Factory FACTORY = new Factory();
    public BoldSlimesConsequence() {
        super(BoldSlimesConsequence.class, null, 50d);
    }

    @Override
    public @NonNull ConsequenceFactory<? extends SpellConsequence> getFactory() {
        return FACTORY;
    }

    public static class Factory extends ConsequenceFactory<BoldSlimesConsequence> {
        protected Factory() {
            super("boldSlimes",
                    "Slimes Spawn Everywhere",
                    "Slimes don't have social anxiety!",
                    "Allowing a slime to spawn somewhere",
                    BoldSlimesConsequence.class,
                    StatusEffectCategory.HARMFUL,
                    MapCodec.unit(BoldSlimesConsequence::new));
        }

        @Override
        public @NotNull BoldSlimesConsequence make(@NonNull Random random, double points) {
            return new BoldSlimesConsequence();
        }


        @Override
        public double getWeight() {
            return Mystical.CONFIG.boldSlimes.enabled() ? Mystical.CONFIG.boldSlimes.weight() : 0;
        }
    }
}
