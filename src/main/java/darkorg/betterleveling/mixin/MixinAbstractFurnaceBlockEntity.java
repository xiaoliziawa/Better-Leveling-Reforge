package darkorg.betterleveling.mixin;

import darkorg.betterleveling.capability.BlockEntityCapabilityProvider;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.util.SkillUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class MixinAbstractFurnaceBlockEntity {

    @Shadow private int cookingProgress;
    @Shadow private int cookingTotalTime;

    @Inject(at = @At("HEAD"), method = "getTotalCookTime", cancellable = true)
    private static void getModifiedCookTime(Level pLevel, AbstractFurnaceBlockEntity pAbstractFurnaceBlockEntity, CallbackInfoReturnable<Integer> pCallbackInfoReturnable) {
        if (pLevel instanceof ServerLevel serverLevel) {
            pAbstractFurnaceBlockEntity.getCapability(BlockEntityCapabilityProvider.BLOCK_ENTITY_CAP).ifPresent(blockEntityCapability -> {
                if (blockEntityCapability.hasOwner() && blockEntityCapability.getOwner(serverLevel) instanceof ServerPlayer serverPlayer) {
                    serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(playerCapability -> {
                        Skill skill = Skills.COOKING_SPEED.get();
                        if (SkillUtil.hasUnlocked(playerCapability, serverPlayer, skill)) {
                            int currentLevel = playerCapability.getLevel(serverPlayer, skill);
                            if (currentLevel > 0) {
                                int originalCookTime = 200;

                                try {
                                    Field quickCheckField = AbstractFurnaceBlockEntity.class.getDeclaredField("quickCheck");
                                    quickCheckField.setAccessible(true);
                                    RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> quickCheck =
                                            (RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe>) quickCheckField.get(pAbstractFurnaceBlockEntity);

                                    originalCookTime = quickCheck.getRecipeFor(pAbstractFurnaceBlockEntity, pLevel)
                                            .map(AbstractCookingRecipe::getCookingTime)
                                            .orElse(200);
                                } catch (Exception e) {
                                }

                                double currentBonus = 1.0D - skill.getCurrentBonus(currentLevel);
                                int modifiedCookTime = Math.toIntExact(Math.round(originalCookTime * currentBonus));
                                pCallbackInfoReturnable.setReturnValue(Math.max(1, modifiedCookTime));
                            }
                        }
                    });
                }
            });
        }
    }

    @Inject(method = "serverTick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;isLit()Z",
            ordinal = 1, shift = At.Shift.AFTER))
    private static void onServerTick(Level level, BlockPos pos, BlockState state,
                                     AbstractFurnaceBlockEntity furnace, CallbackInfo ci) {
        if (!(level instanceof ServerLevel)) {
            return;
        }

        ServerLevel serverLevel = (ServerLevel) level;

        final AbstractFurnaceBlockEntityAccessor accessor = (AbstractFurnaceBlockEntityAccessor) furnace;

        if (!accessor.callIsLit()) {
            return;
        }

        double checkRange = 5.0;
        ServerPlayer nearestPlayer = null;
        double minDistance = Double.MAX_VALUE;

        for (ServerPlayer player : serverLevel.getServer().getPlayerList().getPlayers()) {
            if (player.level() == level) {
                double distSq = player.blockPosition().distSqr(pos);
                if (distSq <= checkRange * checkRange && distSq < minDistance) {
                    nearestPlayer = player;
                    minDistance = distSq;
                }
            }
        }

        if (nearestPlayer == null) {
            return;
        }

        final ServerPlayer player = nearestPlayer;

        player.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(cap -> {
            Skill cookingSpeedSkill = Skills.getFrom("cooking_speed");

            if (cookingSpeedSkill == null) {
                return;
            }

            int skillLevel = cap.getLevel(player, cookingSpeedSkill);

            if (skillLevel > 0) {
                double speedBonus = skillLevel * 0.10;

                if (Math.random() < speedBonus) {
                    int currentProgress = accessor.getProgress();
                    int totalTime = accessor.getTotalTime();

                    if (currentProgress < totalTime - 1) {
                    } else {
                        accessor.setProgress(totalTime - 1);
                    }
                }
            }
        });
    }
}