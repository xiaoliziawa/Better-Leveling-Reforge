package darkorg.betterleveling.network.packets.skill;

import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.registry.Skills;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class IncreaseSkillC2SPacket {
    private final CompoundTag data;

    public IncreaseSkillC2SPacket(Skill pSkill) {
        this.data = new CompoundTag();
        this.data.putString("Name", pSkill.getName());
    }

    public IncreaseSkillC2SPacket(FriendlyByteBuf pBuf) {
        this.data = pBuf.readNbt();
    }

    public static void encode(IncreaseSkillC2SPacket pPacket, FriendlyByteBuf pBuf) {
        pBuf.writeNbt(pPacket.data);
    }

    public static void handle(IncreaseSkillC2SPacket pPacket, Supplier<NetworkEvent.Context> pContextSupplier) {
        NetworkEvent.Context context = pContextSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer != null) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    Skill skill = Skills.getFrom(pPacket.data.getString("Name"));
                    if (!serverPlayer.isCreative()) {
                        serverPlayer.giveExperiencePoints(-skill.getCurrentCost(capability.getLevel(serverPlayer, skill)));
                    }
                    capability.addLevel(serverPlayer, skill, 1);
                    NetworkHandler.sendToPlayer(new RefreshSkillScreenS2CPacket(skill), serverPlayer);
                });
            }
        });
        context.setPacketHandled(true);
    }
}