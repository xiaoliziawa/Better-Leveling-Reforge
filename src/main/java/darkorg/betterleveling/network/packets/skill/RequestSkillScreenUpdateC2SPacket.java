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

public class RequestSkillScreenUpdateC2SPacket {
    private final CompoundTag data;

    public RequestSkillScreenUpdateC2SPacket(Skill pSkill) {
        this.data = new CompoundTag();
        this.data.putString("Name", pSkill.getName());
    }

    public RequestSkillScreenUpdateC2SPacket(FriendlyByteBuf pBuf) {
        this.data = pBuf.readNbt();
    }

    public static void encode(RequestSkillScreenUpdateC2SPacket pPacket, FriendlyByteBuf pBuf) {
        pBuf.writeNbt(pPacket.data);
    }

    public static void handle(RequestSkillScreenUpdateC2SPacket pPacket, Supplier<NetworkEvent.Context> pContextSupplier) {
        NetworkEvent.Context context = pContextSupplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer != null) {
                serverPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(capability -> {
                    String name = pPacket.data.getString("Name");
                    NetworkHandler.sendToPlayer(new RefreshSkillScreenS2CPacket(Skills.getFrom(name)), serverPlayer);
                });
            }
        });
        context.setPacketHandled(true);
    }
}
