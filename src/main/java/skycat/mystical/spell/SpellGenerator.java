package skycat.mystical.spell;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import skycat.mystical.Mystical;
import skycat.mystical.spell.consequence.*;
import skycat.mystical.spell.cure.CureFactory;
import skycat.mystical.spell.cure.SpellCure;
import skycat.mystical.spell.cure.StatBackedSpellCure;
import skycat.mystical.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

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
    @SuppressWarnings("rawtypes") private static final HashMap<String, ConsequenceFactory> shortNameToFactory = new HashMap<>();
    @SuppressWarnings("rawtypes") private static final ArrayList<CureFactory> cureFactories = new ArrayList<>();

    static {
        Collections.addAll(consequenceFactories,
                // KillOnSleepConsequence.FACTORY,
                LevitateConsequence.FACTORY,
                RandomTreeTypeConsequence.FACTORY,
                BigCreeperExplosionConsequence.FACTORY,
                FishingRodLaunchConsequence.FACTORY,
                CatVariantChangeConsequence.FACTORY,
                SheepColorChangeConsequence.FACTORY,
                ZombieTypeChangeConsequence.FACTORY,
                SkeletonTypeChangeConsequence.FACTORY,
                EnderTypeChangeConsequence.FACTORY
        );

        // For some reason, using "? extends SpellConsequence" gives a warning.
        for (ConsequenceFactory<?> factory : consequenceFactories) {
            getShortNameToFactory().put(factory.make(Mystical.RANDOM, 0).getShortName(), factory); // Not the greatest option for getting the names since we're making a new one, but oh well.
        }

        Collections.addAll(cureFactories,
                (random, points) -> (new StatBackedSpellCure(100.0, Stats.MINED.getOrCreateStat(Blocks.CACTUS), "text.mystical.spellCure.default")), // TODO: Translate
                (random, points) -> (new StatBackedSpellCure(1000, Stats.CUSTOM.getOrCreateStat(Stats.JUMP), "text.mystical.spellCure.default")), // TODO: Translate
                (random, points) -> (new StatBackedSpellCure(50, Stats.USED.getOrCreateStat(Items.SHEARS), "text.mystical.spellCure.default")), // TODO: Translate
                (random, points) -> (new StatBackedSpellCure(50000, Stats.CUSTOM.getOrCreateStat(Stats.SPRINT_ONE_CM), "text.mystical.spellCure.default")), // TODO: Translate
                (random, points) -> (new StatBackedSpellCure(640, Stats.CRAFTED.getOrCreateStat(Items.BARREL), "text.mystical.spellCure.default")), // TODO: Translate
                (random, points) -> (new StatBackedSpellCure(10, Stats.CUSTOM.getOrCreateStat(Stats.ANIMALS_BRED), "text.mystical.spellCure.default")) // TODO: Translate
        );
    }

    public static Spell get() {
        return new Spell(getConsequence(0), getCure(0));
    }

    public static Spell getWithConsequence(ConsequenceFactory<?> consequenceFactory) {
        return new Spell(consequenceFactory.make(Mystical.RANDOM, 0), getCure(0));
    }

    // TODO: Weight things
    public static SpellConsequence getConsequence(double points) {
        if (consequenceFactories.isEmpty()) {
            Utils.log(Utils.translateString("text.mystical.spellGenerator.emptyConsequenceList")); // TODO: Config
            return LevitateConsequence.FACTORY.make(Mystical.getRANDOM(), 0);
        }
        return Utils.chooseRandom(Mystical.getRANDOM(), consequenceFactories).make(Mystical.getRANDOM(), points);
    }

    public static SpellCure getCure(double points) {
        if (cureFactories.isEmpty()) {
            Utils.log(Utils.translateString("text.mystical.spellGenerator.emptyCureList")); // TODO: Config
            return new StatBackedSpellCure(10, Stats.MINED.getOrCreateStat(Blocks.CACTUS), "text.mystical.spellCure.default");
        }
        return Utils.chooseRandom(Mystical.getRANDOM(), cureFactories).make(Mystical.getRANDOM(), points);
    }

    private static Block getRandomBlock() { // TODO: Make checked (no unbreakables, configurable rarities, etc)
        Optional<RegistryEntry<Block>> blockEntry = Registry.BLOCK.getRandom(Mystical.getMC_RANDOM());
        Block block;
        if (blockEntry.isPresent()) {
            block = blockEntry.get().value();
            if (block.getHardness() == -1) { // Unbreakable block
                return getRandomBlock(); // Try again
            }
            return block;
        }
        // Labeled in translation as critical error. Ideally, this should not happen.
        Utils.log(Utils.translateString("text.mystical.spellGenerator.failedRandomBlock"), Mystical.CONFIG.failedToGetRandomBlockLogLevel());
        return Blocks.COMMAND_BLOCK;
    }

    public static HashMap<String, ConsequenceFactory> getShortNameToFactory() {
        return shortNameToFactory;
    }
}
