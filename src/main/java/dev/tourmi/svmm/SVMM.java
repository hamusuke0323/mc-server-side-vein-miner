package dev.tourmi.svmm;

import com.mojang.logging.LogUtils;
import dev.tourmi.svmm.commands.Commands;
import dev.tourmi.svmm.config.SVMMConfig;
import dev.tourmi.svmm.server.VeinMiner;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.core.net.Priority;
import org.slf4j.Logger;

@Mod(SVMM.MODID)
public class SVMM
{
    public static final String MODID = "svmm";
    private static final Logger LOGGER = LogUtils.getLogger();

    private VeinMiner veinMiner;

    public SVMM()
    {
        veinMiner = new VeinMiner();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SVMMConfig.SPEC, "svmm-config.toml");

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("Server-side Vein Mining successfully started");
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        Commands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onBlockBroken(BlockEvent.BreakEvent event) {
        veinMiner.onBlockBroken(event);
    }
}