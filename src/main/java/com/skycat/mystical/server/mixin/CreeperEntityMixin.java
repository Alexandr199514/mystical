package com.skycat.mystical.server.mixin;

import com.skycat.mystical.Mystical;
import com.skycat.mystical.common.spell.consequence.BigCreeperExplosionConsequence;
import com.skycat.mystical.common.spell.consequence.NoFuseConsequence;
import com.skycat.mystical.common.util.Utils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin {
    @Shadow
    private int explosionRadius;

    @Shadow private int fuseTime;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void CreeperEntity(EntityType entityType, World world, CallbackInfo ci) { // TODO: allow for working with havens
        if (Mystical.SPELL_HANDLER.isConsequenceActive(BigCreeperExplosionConsequence.class) && Utils.percentChance(Mystical.CONFIG.bigCreeperExplosion.chance())) {
            explosionRadius = (int) (explosionRadius * Mystical.CONFIG.bigCreeperExplosion.multiplier()); // WARN This method seems to lag out the game for some reason
            Utils.log(Utils.translateString("text.mystical.consequence.bigCreeperExplosion.fired"), Mystical.CONFIG.bigCreeperExplosion.logLevel());
        }
    }

    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/mob/CreeperEntity;fuseTime:I", opcode = Opcodes.GETFIELD))
    private int changeFuseTime(CreeperEntity instance) {
        if (!Mystical.HAVEN_MANAGER.isInHaven(instance) && Mystical.SPELL_HANDLER.isConsequenceActive(NoFuseConsequence.class)) {
            return 1;
        }
        return fuseTime;
    }

}
