package skycat.mystical;

import io.wispforest.owo.config.annotation.*;

@SuppressWarnings("unused") // They are used by owo-config
@Modmenu(modId = "mystical")
@Config(name = "mysticalConfig", wrapperName = "MysticalConfig")
public class ConfigModel {
    @SectionHeader("General")
    public boolean devMode = false; // Not implemented

    @SectionHeader("Spells")
    @Nest public BigCreeperExplosionConfig bigCreeperExplosion = new BigCreeperExplosionConfig();
    @Nest public RandomTreeTypeConfig randomTreeType = new RandomTreeTypeConfig();
    @Nest public ZombieTypeChangeConfig zombieTypeChange = new ZombieTypeChangeConfig();
    @Nest public SkeletonTypeChangeConfig skeletonTypeChange = new SkeletonTypeChangeConfig();
    @Nest public EnderTypeChangeConfig enderTypeChange = new EnderTypeChangeConfig(); // TODO: Translate
    @Nest public CatVariantChangeConfig catVariantChange = new CatVariantChangeConfig();


    @SectionHeader("Logging") // Note: Logging as ERROR level does not always mean a critical error.
    public LogLevel failedToSetNightTimerLogLevel = LogLevel.WARN;
    public LogLevel timeOfDayAtStartupLogLevel = LogLevel.DEBUG;
    public LogLevel failedToLoadHavenManagerLogLevel = LogLevel.INFO;
    public LogLevel failedToSaveHavenManagerLogLevel = LogLevel.INFO;
    public LogLevel playerContributedLogLevel = LogLevel.OFF; // Not implemented
    public LogLevel failedToGetRandomBlockLogLevel = LogLevel.ERROR;
    public LogLevel failedToLoadSpellHandlerLogLevel = LogLevel.WARN;
    public LogLevel failedToSaveSpellHandlerLogLevel = LogLevel.ERROR;
    public LogLevel newSpellCommandLogLevel = LogLevel.INFO;

    public static class BigCreeperExplosionConfig {
        public boolean enabled = true; // Not implemented
        public double multiplier = 2.0;
        @PredicateConstraint("chancePredicate")
        public double chance = 100.0;
        public LogLevel logLevel = LogLevel.OFF;
        @PredicateConstraint("weightPredicate")
        public double weight = 1; // Not implemented

        public static boolean chancePredicate(double value) {
            return ConfigModel.chancePredicate(value);
        }

        public static boolean weightPredicate(double value) {
            return ConfigModel.weightPredicate(value);
        }
    }
    
    public static class RandomTreeTypeConfig {
        public boolean enabled = true; // Not implemented
        @PredicateConstraint("chancePredicate")
        public double chance = 100.0;
        // public boolean fromAcacia = true; // Not implemented // TODO: Config
        // public boolean fromAzalea = true; // Not implemented // TODO: Config
        // public boolean fromBirch = true; // Not implemented // TODO: Config
        // public boolean fromDarkOak = true; // Not implemented // TODO: Config
        // public boolean fromJungle = true; // Not implemented // TODO: Config
        // public boolean fromMangrove = true; // Not implemented // TODO: Config
        // public boolean fromOak = true; // Not implemented // TODO: Config
        // public boolean fromSpruce = true; // Not implemented // TODO: Config
        // public boolean toAcacia = true; // Not implemented // TODO: Config
        // public boolean toAzalea = true; // Not implemented // TODO: Config
        // public boolean toBirch = true; // Not implemented // TODO: Config
        // public boolean toDarkOak = true; // Not implemented // TODO: Config
        // public boolean toJungle = true; // Not implemented // TODO: Config
        // public boolean toMangrove = true; // Not implemented // TODO: Config
        // public boolean toOak = true; // Not implemented // TODO: Config
        // public boolean toSpruce = true; // Not implemented // TODO: Config
        public LogLevel logLevel = LogLevel.OFF; // Not implemented
        @PredicateConstraint("weightPredicate")
        public double weight = 1; // Not implemented
        public static boolean chancePredicate(double value) {
            return ConfigModel.chancePredicate(value);
        }

        public static boolean weightPredicate(double value) {
            return ConfigModel.weightPredicate(value);
        }
    }

    public static class LevitateConfig {
        public boolean enabled = true; // Not implemented
        @PredicateConstraint("chancePredicate")
        public double chance = 100.0; // Not implemented
        // TODO: block/event type options
        public LogLevel logLevel = LogLevel.OFF; // Not implemented
        @PredicateConstraint("weightPredicate")
        public double weight = 1; // Not implemented
        public static boolean chancePredicate(double value) {
            return ConfigModel.chancePredicate(value);
        }

        public static boolean weightPredicate(double value) {
            return ConfigModel.weightPredicate(value);
        }
    }

    public static class CatVariantChangeConfig {
        public boolean enabled = true;
        public LogLevel logLevel = LogLevel.OFF;
        @PredicateConstraint("weightPredicate")
        public double weight = 1;
        public double chance = 100.0; // Not implemented: logging, chance
        public static boolean chancePredicate(double value) {
            return ConfigModel.chancePredicate(value);
        }

        public static boolean weightPredicate(double value) {
            return ConfigModel.weightPredicate(value);
        }
    }

    public static class ZombieTypeChangeConfig {
        public boolean enabled = true; // Not implemented
        @PredicateConstraint("chancePredicate")
        public double chance = 25.0;
        public LogLevel logLevel = LogLevel.OFF; // Not implemented
        @PredicateConstraint("weightPredicate")
        public double weight = 1; // Not implemented
        public static boolean chancePredicate(double value) {
            return ConfigModel.chancePredicate(value);
        }

        public static boolean weightPredicate(double value) {
            return ConfigModel.weightPredicate(value);
        }
    }

    public static class SkeletonTypeChangeConfig {
        public boolean enabled = true; // Not implemented
        @PredicateConstraint("chancePredicate")
        public double chance = 25.0;
        public LogLevel logLevel = LogLevel.OFF; // Not implemented
        @PredicateConstraint("weightPredicate")
        public double weight = 1; // Not implemented
        public static boolean chancePredicate(double value) {
            return ConfigModel.chancePredicate(value);
        }

        public static boolean weightPredicate(double value) {
            return ConfigModel.weightPredicate(value);
        }
    }

    public static class EnderTypeChangeConfig {
        public boolean enabled = true; // Not implemented
        @PredicateConstraint("chancePredicate")
        public double chance = 25.0;
        public LogLevel logLevel = LogLevel.OFF; // Not implemented
        @PredicateConstraint("weightPredicate")
        public double weight = 1; // Not implemented
        public static boolean chancePredicate(double value) {
            return ConfigModel.chancePredicate(value);
        }

        public static boolean weightPredicate(double value) {
            return ConfigModel.weightPredicate(value);
        }
    }

    /**
     * Verify that the chance is valid.
     * Used instead of {@link RangeConstraint} because it doesn't make a slider.
     * @param value The chance value to check
     * @return value >= 0 && value <= 100
     */
    public static boolean chancePredicate(double value) {
        return value >= 0 && value <= 100;
    }

    /**
     * Verify that the given weight is valid.
     * Used instead of {@link RangeConstraint} because it doesn't make a slider.
     * @param value The weight to check
     * @return value >= 0
     */
    public static boolean weightPredicate(double value) {
        return value >= 0;
    }

}