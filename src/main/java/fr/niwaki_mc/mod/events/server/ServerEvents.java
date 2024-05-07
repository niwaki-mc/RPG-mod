package fr.niwaki_mc.mod.events.server;

import fr.niwaki_mc.commons.Commons;
import fr.niwaki_mc.commons.persistence.ClassesManager;
import fr.niwaki_mc.mod.commands.ClassesCommand;
import fr.niwaki_mc.mod.configs.ConfigHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

@Mod.EventBusSubscriber(modid = Commons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        ClassesCommand.register(event.getServer().getCommands().getDispatcher());
        ClassesManager.getInstance().start();
        ClassesManager.getInstance().insertDefaultClasses();
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        ClassesManager.getInstance().shutdown();
    }
}
