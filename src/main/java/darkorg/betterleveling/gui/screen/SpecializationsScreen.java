package darkorg.betterleveling.gui.screen;

import com.google.common.collect.ImmutableList;
import darkorg.betterleveling.capability.PlayerCapabilityProvider;
import darkorg.betterleveling.gui.widget.button.SkillButton;
import darkorg.betterleveling.gui.widget.button.SpecializationButton;
import darkorg.betterleveling.impl.PlayerCapability;
import darkorg.betterleveling.impl.skill.Skill;
import darkorg.betterleveling.impl.specialization.Specialization;
import darkorg.betterleveling.key.KeyMappings;
import darkorg.betterleveling.network.NetworkHandler;
import darkorg.betterleveling.network.chat.ModComponents;
import darkorg.betterleveling.network.packets.specialization.RequestSpecializationsScreenC2SPacket;
import darkorg.betterleveling.network.packets.specialization.UnlockSpecializationC2SPacket;
import darkorg.betterleveling.registry.Skills;
import darkorg.betterleveling.registry.Specializations;
import darkorg.betterleveling.util.PlayerUtil;
import darkorg.betterleveling.util.RenderUtil;
import darkorg.betterleveling.util.SkillUtil;
import darkorg.betterleveling.util.SpecializationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ExtendedButton;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpecializationsScreen extends Screen {
    private static final ImmutableList<Specialization> SPECIALIZATIONS = Specializations.getAll();
    private final int imageWidth = 176;
    private final int imageHeight = 166;
    private int leftPos;
    private int topPos;
    private LocalPlayer localPlayer;
    private PlayerCapability capability;
    private Specialization specialization;
    private boolean isUnlocked;
    private boolean canUnlock;
    private ImmutableList<Specialization> unlockedSpecializations;
    private ImmutableList<Skill> skills;
    private int availableExperience;
    private Component availableXP;

    public SpecializationsScreen() {
        super(Component.empty());
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == KeyMappings.OPEN_GUI.getKey().getValue()) {
            this.onClose();
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        this.localPlayer = Minecraft.getInstance().player;

        if (this.localPlayer != null) {
            this.localPlayer.getCapability(PlayerCapabilityProvider.PLAYER_CAP).ifPresent(pCapability -> {
                this.capability = pCapability;
                this.specialization = this.capability.getSpecialization(this.localPlayer);
                this.unlockedSpecializations = SpecializationUtil.getUnlocked(this.capability, localPlayer);

                if (this.specialization == null) {
                    if (SpecializationUtil.hasUnlocked(this.capability, this.localPlayer)) {
                        this.specialization = this.unlockedSpecializations.get(0);
                        NetworkHandler.sendToServer(new RequestSpecializationsScreenC2SPacket(this.specialization));
                    } else {
                        Minecraft.getInstance().setScreen(new ChooseSpecializationScreen());
                    }
                } else {
                    this.skills = Skills.getAllFrom(this.specialization);
                    this.isUnlocked = this.capability.getUnlocked(this.localPlayer, this.specialization);
                    this.canUnlock = PlayerUtil.canUnlockSpecialization(this.localPlayer, this.specialization);

                    SpecializationButton specializationButton = new SpecializationButton(this.leftPos + 72, this.topPos + 16, this.specialization, this.isUnlocked, SPECIALIZATIONS, this::onValueChange, this::onSpecializationTooltip);
                    addRenderableWidget(specializationButton);

                    if (!this.isUnlocked) {
                        ExtendedButton unlockSpecButton = new ExtendedButton(this.leftPos + this.imageWidth / 2 - 37, this.topPos + 98, 74, 17, ModComponents.UNLOCK, this::onUnlock);
                        unlockSpecButton.active = this.canUnlock;
                        addRenderableWidget(unlockSpecButton);
                    }

                    this.skills.forEach(pSkill -> {
                        SkillButton skillButton = new SkillButton(this.leftPos - 28, this.topPos + 14, pSkill, this::onSkillTooltip);
                        addRenderableWidget(skillButton);
                    });

                    this.availableExperience = this.capability.getAvailableExperience(this.localPlayer);
                    this.availableXP = RenderUtil.getAvailableXP(this.availableExperience);
                }
            });
        }
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);

        pGuiGraphics.blit(RenderUtil.BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        if (this.isUnlocked) {
            pGuiGraphics.drawCenteredString(this.font, this.availableXP, this.leftPos + this.imageWidth / 2, this.topPos + 51, 16777215);
        } else {
            pGuiGraphics.drawCenteredString(this.font, ModComponents.SPEC_IS_LOCKED, this.leftPos + this.imageWidth / 2, this.topPos + 51, 16733525);
        }

        for (Renderable renderable : this.renderables) {
            renderable.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }

        // tooltip 渲染层bug fix
        for (Renderable renderable : this.renderables) {
            if (renderable instanceof SpecializationButton button && button.isHovered()) {
                this.onSpecializationTooltip(pGuiGraphics, this.font, pMouseX, pMouseY);
                break;
            } else if (renderable instanceof SkillButton button && button.isHovered()) {
                this.onSkillTooltip(button, pGuiGraphics, this.font, pMouseX, pMouseY);
                break;
            }
        }
    }

    private void onValueChange(Specialization pSpecialization) {
        NetworkHandler.sendToServer(new RequestSpecializationsScreenC2SPacket(pSpecialization));
    }

    private void onSpecializationTooltip(GuiGraphics pGuiGraphics, Font pFont, int pMouseX, int pMouseY) {
        List<Component> tooltipLines = SpecializationUtil.getTooltip(this.specialization, this.isUnlocked, this.canUnlock);
        
        // more tooltip fix
        int tooltipWidth = 0;
        for (Component line : tooltipLines) {
            int lineWidth = pFont.width(line);
            tooltipWidth = Math.max(tooltipWidth, lineWidth);
        }
        int tooltipHeight = tooltipLines.size() * (pFont.lineHeight + 2) - 2;
        
        int adjustedX = pMouseX;
        int adjustedY = pMouseY - tooltipHeight - 12;
        
        if (adjustedX + tooltipWidth + 12 > this.width) {
            adjustedX = this.width - tooltipWidth - 12;
        }
        
        if (adjustedY < 0) {
            adjustedY = pMouseY + 12;
        }
        
        pGuiGraphics.renderComponentTooltip(pFont, tooltipLines, adjustedX, adjustedY);
    }

    private void onUnlock(Button pButton) {
        Minecraft.getInstance().setScreen(new ConfirmScreen(this::onCallback, this.specialization.getTranslation(), ModComponents.CONFIRM_UNLOCK));
    }

    private void onCallback(boolean pConfirm) {
        if (pConfirm) {
            NetworkHandler.sendToServer(new UnlockSpecializationC2SPacket(this.specialization));
        }
        NetworkHandler.sendToServer(new RequestSpecializationsScreenC2SPacket(this.specialization));
    }

    private void onSkillTooltip(SkillButton pButton, GuiGraphics pGuiGraphics, Font pFont, int pMouseX, int pMouseY) {
        Skill skill = pButton.getSkill();
        int currentLevel = this.capability.getLevel(this.localPlayer, skill);
        boolean canIncrease = PlayerUtil.canIncreaseSkill(this.localPlayer, skill, skill.isMaxLevel(currentLevel), this.availableExperience, currentLevel);
        boolean hasUnlocked = SkillUtil.hasUnlocked(this.capability, this.localPlayer, skill);
        pGuiGraphics.renderComponentTooltip(pFont, SkillUtil.getTooltip(skill, hasUnlocked, currentLevel, canIncrease), pMouseX, pMouseY);
    }
}
