package fr.niwaki_mc.mod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.niwaki_mc.commons.models.Classes;
import fr.niwaki_mc.commons.persistence.ClassesManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ClassesCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("classes")
                .executes(ClassesCommand::execute));
    }

    private static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ClassesManager manager = new ClassesManager();
        List<Classes> classesList = manager.getAllClasses();

        for (Classes classes : classesList) {
            context.getSource().sendSystemMessage(Component.literal(classes.getName()));
        }

        return 1;
    }
}
