package fr.niwaki_mc.mod.events.server;

import fr.niwaki_mc.commons.Commons;
import fr.niwaki_mc.commons.persistence.ClassesManager;
import fr.niwaki_mc.commons.persistence.HibernateUtils;
import fr.niwaki_mc.mod.NiwakiMod;
import fr.niwaki_mc.mod.commands.ClassesCommand;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Commons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        ClassesCommand.register(event.getServer().getCommands().getDispatcher());
        new ClassesManager().insertDefaultClasses();
        NiwakiMod.LOGGER.info("Server started");
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        HibernateUtils.getSessionFactory().close();
    }
}