package darkorg.betterleveling.data.client;

import darkorg.betterleveling.BetterLeveling;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ChineseLanguageProvider extends LanguageProvider {
    
    public ChineseLanguageProvider(PackOutput output) {
        super(output, BetterLeveling.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        // GUI 文本
        add("betterleveling..command.failure.capability", "命令异常：未找到能力");
        add("betterleveling..command.failure.skill", "命令异常：未找到技能");
        add("betterleveling..command.failure.spec", "命令异常：未找到专精");
        add("betterleveling..invalid_level", "无效等级");
        add("betterleveling.gui.additional_info", "附加信息：");
        add("betterleveling.gui.available", "可用：");
        add("betterleveling.gui.bullet", "• ");
        add("betterleveling.gui.cannot_access", "你无法使用这个机器。");
        add("betterleveling.gui.cannot_decrease", "无法降低等级");
        add("betterleveling.gui.cannot_increase", "无法提升等级");
        add("betterleveling.gui.choose.cannot_unlock", "尚无法解锁专精。所需等级：");
        add("betterleveling.gui.choose.confirm", "你确定要选择这个专精作为你的首选吗？");
        add("betterleveling.gui.choose_spec_title", "选择你的专精");
        add("betterleveling.gui.cost", "花费：");
        add("betterleveling.gui.current_bonus", "加成：");
        add("betterleveling.gui.current_cost", "当前");
        add("betterleveling.gui.decrease", "降低");
        add("betterleveling.gui.decrease.confirm", "你确定要降低这个技能的等级吗？");
        add("betterleveling.gui.hold_shift", "按住SHIFT查看附加信息");
        add("betterleveling.gui.increase", "提升");
        add("betterleveling.gui.increase.confirm", "你确定要提升这个技能的等级吗？");
        add("betterleveling.gui.level", "等级：");
        add("betterleveling.gui.levels", "等级");
        add("betterleveling.gui.locked", "已锁定");
        add("betterleveling.gui.max_level", "最高等级");
        add("betterleveling.gui.not_enough_xp", "你没有足够的经验值！");
        add("betterleveling.gui.not_owned", "机器被其他人绑定！");
        add("betterleveling.gui.per_level", "每级");
        add("betterleveling.gui.prerequisites", "前置条件：");
        add("betterleveling.gui.register", "机器绑定成功");
        add("betterleveling.gui.select", "选择");
        add("betterleveling.gui.spec", "专精");
        add("betterleveling.gui.spec_is_locked", "此专精已锁定");
        add("betterleveling.gui.unlock", "解锁");
        add("betterleveling.gui.unlock.confirm", "你确定要解锁这个专精吗？");
        add("betterleveling.gui.unlock_cost", "解锁花费：");
        add("betterleveling.gui.unregister", "机器解绑成功");
        add("betterleveling.gui.xp", "经验值");
        
        // 技能
        add("betterleveling.skill.arrow_speed", "箭矢速度");
        add("betterleveling.skill.arrow_speed.description.0", "提高箭矢速度");
        add("betterleveling.skill.arrow_speed.description.1", "+");
        add("betterleveling.skill.arrow_speed.description.2", "% ");
        add("betterleveling.skill.arrow_speed.description.3", "速度");
        
        add("betterleveling.skill.cooking_speed", "烹饪速度");
        add("betterleveling.skill.cooking_speed.description.0", "减少烹饪总时间");
        add("betterleveling.skill.cooking_speed.description.1", "-");
        add("betterleveling.skill.cooking_speed.description.2", "% ");
        add("betterleveling.skill.cooking_speed.description.3", "总时间");
        
        add("betterleveling.skill.crit_strike", "暴击");
        add("betterleveling.skill.crit_strike.description.0", "提高暴击几率");
        add("betterleveling.skill.crit_strike.description.1", "+");
        add("betterleveling.skill.crit_strike.description.2", "% ");
        add("betterleveling.skill.crit_strike.description.3", "几率");
        
        add("betterleveling.skill.green_thumb", "绿色拇指");
        add("betterleveling.skill.green_thumb.description.0", "有几率促进周围作物生长");
        add("betterleveling.skill.green_thumb.description.1", "+");
        add("betterleveling.skill.green_thumb.description.2", "% ");
        add("betterleveling.skill.green_thumb.description.3", "几率");
        
        add("betterleveling.skill.harvest_proficiency", "收获熟练度");
        add("betterleveling.skill.harvest_proficiency.description.0", "有几率增加作物掉落");
        add("betterleveling.skill.harvest_proficiency.description.1", "+");
        add("betterleveling.skill.harvest_proficiency.description.2", "% ");
        add("betterleveling.skill.harvest_proficiency.description.3", "几率");
        
        add("betterleveling.skill.iron_skin", "钢铁之肤");
        add("betterleveling.skill.iron_skin.description.0", "减少受到的伤害");
        add("betterleveling.skill.iron_skin.description.1", "-");
        add("betterleveling.skill.iron_skin.description.2", "% ");
        add("betterleveling.skill.iron_skin.description.3", "伤害");
        
        add("betterleveling.skill.meat_gathering", "肉类采集");
        add("betterleveling.skill.meat_gathering.description.0", "有几率增加肉类掉落");
        add("betterleveling.skill.meat_gathering.description.1", "+");
        add("betterleveling.skill.meat_gathering.description.2", "% ");
        add("betterleveling.skill.meat_gathering.description.3", "几率");
        
        add("betterleveling.skill.prospecting", "勘探");
        add("betterleveling.skill.prospecting.description.0", "有几率增加矿石掉落");
        add("betterleveling.skill.prospecting.description.1", "+");
        add("betterleveling.skill.prospecting.description.2", "% ");
        add("betterleveling.skill.prospecting.description.3", "几率");
        
        add("betterleveling.skill.quick_draw", "快速拉弓");
        add("betterleveling.skill.quick_draw.description.0", "减少弓箭充能总时间");
        add("betterleveling.skill.quick_draw.description.1", "-");
        add("betterleveling.skill.quick_draw.description.2", "% ");
        add("betterleveling.skill.quick_draw.description.3", "总时间");
        
        add("betterleveling.skill.skinning", "剥皮");
        add("betterleveling.skill.skinning.description.0", "有几率增加皮革掉落");
        add("betterleveling.skill.skinning.description.1", "+");
        add("betterleveling.skill.skinning.description.2", "% ");
        add("betterleveling.skill.skinning.description.3", "几率");
        
        add("betterleveling.skill.sneak_speed", "潜行速度");
        add("betterleveling.skill.sneak_speed.description.0", "提高潜行时的速度");
        add("betterleveling.skill.sneak_speed.description.1", "+");
        add("betterleveling.skill.sneak_speed.description.2", "% ");
        add("betterleveling.skill.sneak_speed.description.3", "速度");
        
        add("betterleveling.skill.soft_landing", "软着陆");
        add("betterleveling.skill.soft_landing.description.0", "减少摔落伤害");
        add("betterleveling.skill.soft_landing.description.1", "-");
        add("betterleveling.skill.soft_landing.description.2", "% ");
        add("betterleveling.skill.soft_landing.description.3", "伤害");
        
        add("betterleveling.skill.sprint_speed", "冲刺速度");
        add("betterleveling.skill.sprint_speed.description.0", "提高冲刺时的速度");
        add("betterleveling.skill.sprint_speed.description.1", "+");
        add("betterleveling.skill.sprint_speed.description.2", "% ");
        add("betterleveling.skill.sprint_speed.description.3", "速度");
        
        add("betterleveling.skill.stonecutting", "采石");
        add("betterleveling.skill.stonecutting.description.0", "提高石头挖掘速度");
        add("betterleveling.skill.stonecutting.description.1", "+");
        add("betterleveling.skill.stonecutting.description.2", "% ");
        add("betterleveling.skill.stonecutting.description.3", "速度");
        
        add("betterleveling.skill.strength", "力量");
        add("betterleveling.skill.strength.description.0", "提高输出伤害");
        add("betterleveling.skill.strength.description.1", "+");
        add("betterleveling.skill.strength.description.2", "% ");
        add("betterleveling.skill.strength.description.3", "伤害");
        
        add("betterleveling.skill.swim_speed", "游泳速度");
        add("betterleveling.skill.swim_speed.description.0", "提高游泳速度");
        add("betterleveling.skill.swim_speed.description.1", "+");
        add("betterleveling.skill.swim_speed.description.2", "% ");
        add("betterleveling.skill.swim_speed.description.3", "速度");
        
        add("betterleveling.skill.treasure_hunting", "寻宝");
        add("betterleveling.skill.treasure_hunting.description.0", "有几率从泥土中挖出宝藏");
        add("betterleveling.skill.treasure_hunting.description.1", "+");
        add("betterleveling.skill.treasure_hunting.description.2", "% ");
        add("betterleveling.skill.treasure_hunting.description.3", "几率");
        
        add("betterleveling.skill.woodcutting", "伐木");
        add("betterleveling.skill.woodcutting.description.0", "提高砍伐木材速度");
        add("betterleveling.skill.woodcutting.description.1", "+");
        add("betterleveling.skill.woodcutting.description.2", "% ");
        add("betterleveling.skill.woodcutting.description.3", "速度");
        
        // 专精
        add("betterleveling.specialization.combat", "战斗");
        add("betterleveling.specialization.combat.description.0", "领域的守护者");
        add("betterleveling.specialization.combat.description.1", "击杀获得额外经验");
        
        add("betterleveling.specialization.crafting", "制作");
        add("betterleveling.specialization.crafting.description.0", "制作和收集资源");
        add("betterleveling.specialization.crafting.description.1", "制作时获得额外经验");
        
        add("betterleveling.specialization.mining", "采矿");
        add("betterleveling.specialization.mining.description.0", "分支挖矿是懦夫的行为");
        add("betterleveling.specialization.mining.description.1", "开采矿石获得额外经验");
        
        // 物品和方块
        add("block.betterleveling.raw_debris_block", "粗古遗物块");
        add("item.betterleveling.raw_debris", "粗古遗物");
        
        // 按键绑定
        add("key.betterleveling.open_gui", "打开界面");
        add("key.category.betterleveling", "Better Leveling");
    }
} 