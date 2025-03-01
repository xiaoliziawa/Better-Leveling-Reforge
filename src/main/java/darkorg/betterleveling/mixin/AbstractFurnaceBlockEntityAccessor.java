package darkorg.betterleveling.mixin;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface AbstractFurnaceBlockEntityAccessor {
    
    @Accessor("cookingProgress")
    int getProgress();
    
    @Accessor("cookingProgress")
    void setProgress(int value);
    
    @Accessor("cookingTotalTime")
    int getTotalTime();
    
    @Accessor("cookingTotalTime")
    void setTotalTime(int value);
    
    @Invoker("isLit")
    boolean callIsLit();
} 