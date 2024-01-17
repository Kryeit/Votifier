package com.kryeit.votifier.utils;

import com.kryeit.votifier.MinecraftServerSupplier;
import net.minecraft.server.command.ServerCommandSource;

public class Utils {

    public static void executeCommandAsServer(String command) {
        // Create a command source that represents the server
        ServerCommandSource source = MinecraftServerSupplier.getServer().getCommandSource();

        // Execute the command
        MinecraftServerSupplier.getServer().getCommandManager().executeWithPrefix(source, command);
    }
}
