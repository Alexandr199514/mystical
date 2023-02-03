package skycat.mystical.spell.cure;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@FunctionalInterface
public interface CureFactory<T extends SpellCure> {
    /**
     * Make a new cure of class {@link T}
     * @param random The random to use to generate anything that should be randomized
     * @param points The point target to aim for.
     * @return A new {@link T}.
     */
    @NotNull T make(@NonNull Random random, double points);
}
