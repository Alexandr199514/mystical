package com.skycat.mystical.server.mixin;

import com.skycat.mystical.Mystical;
import com.skycat.mystical.common.spell.consequence.NoFuseConsequence;
import net.minecraft.entity.TntEntity;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TntEntity.class)
public abstract class TntEntityMixin implements EntityLike {

    @ModifyArg(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/entity/LivingEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/TntEntity;setFuse(I)V"))
    private int modifyFuse(int fuse) {
        if (!Mystical.HAVEN_MANAGER.isInHaven(getBlockPos()) && Mystical.SPELL_HANDLER.isConsequenceActive(NoFuseConsequence.class)) {
            return 1;
        }
        return fuse;
    }
}
