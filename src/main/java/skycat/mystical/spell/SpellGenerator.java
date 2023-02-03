package skycat.mystical.spell;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.stat.Stats;
import net.minecraft.util.registry.Registry;
import skycat.mystical.Mystical;
import skycat.mystical.spell.consequence.ConsequenceFactory;
import skycat.mystical.spell.consequence.KillOnSleepConsequence;
import skycat.mystical.spell.consequence.LevitateConsequence;
import skycat.mystical.spell.consequence.SpellConsequence;
import skycat.mystical.spell.cure.CureFactory;
import skycat.mystical.spell.cure.SpellCure;
import skycat.mystical.spell.cure.StatBackedSpellCure;
import skycat.mystical.util.Utils;

import java.util.ArrayList;

/*
Notes on randomization scheme:
Difficulty: A double rating how much trouble a spell will cause, taking into account both the cure and consequence.
    A negative rating indicates that the spell may be useful in a way
Split: A target value for |consequence difficulty - cure difficulty|.
    Lower values should result in a more balanced spell, while higher values return harder consequences paired with easier cures, or harder cures with easier consequences.
Wildness: A measure indicating how different the gameplay is due to the spell - more unique spells have higher wildness.
    This is separate from difficulty, though difficulty will likely be correlated.
 */
public class SpellGenerator { // TODO: For now, a lot of things that could be randomized are just hard-coded
    @SuppressWarnings("rawtypes") private static final ArrayList<ConsequenceFactory> consequenceFactories = new ArrayList<>();
    @SuppressWarnings("rawtypes") private static final ArrayList<CureFactory> cureFactories = new ArrayList<>();

    static {
        consequenceFactories.add(KillOnSleepConsequence.FACTORY);
        consequenceFactories.add(LevitateConsequence.FACTORY);


        cureFactories.add((random, points) -> (new StatBackedSpellCure(100.0, Stats.MINED.getOrCreateStat(Blocks.CACTUS))));
    }

    public static Spell get() {
        // WARN: Debug only
        return new Spell(getConsequence(0), getCure(0));
    }

    // TODO: Weight things
    public static SpellConsequence getConsequence(double points) {
        if (consequenceFactories.isEmpty()) {
            Utils.log("SpellGenerator found an empty consequence supplier list. Using default consequence (levitate, 0 pts).");
            return LevitateConsequence.FACTORY.make(Mystical.getRANDOM(), 0);
        }
        return Utils.chooseRandom(Mystical.getRANDOM(), consequenceFactories).make(Mystical.getRANDOM(), points);
    }

    public static SpellCure getCure(double points) {
        if (cureFactories.isEmpty()) {
            Utils.log("SpellGenerator found an empty cure list. Using default cure (mine 10 cactus).");
            return new StatBackedSpellCure(10, Stats.MINED.getOrCreateStat(Blocks.CACTUS));
        }
        return Utils.chooseRandom(Mystical.getRANDOM(), cureFactories).make(Mystical.getRANDOM(), points);
    }

    private static Block getRandomBlock() { // TODO: Make checked (no unbreakables, configurable rarities, etc)
        var randomBlock = Registry.BLOCK.getRandom(Mystical.getMC_RANDOM());
        if (randomBlock.isPresent()) {
            return randomBlock.get().value();
        }
        // TODO: Logging
        return Blocks.COMMAND_BLOCK;
    }
}
