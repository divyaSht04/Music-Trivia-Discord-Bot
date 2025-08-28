package com.music.trivia.command;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommandManager extends ListenerAdapter {
    private final Map<String, Command> commands;

    public CommandManager() {
        this.commands = new HashMap<>();
        registerCommands();
    }

    private void registerCommands() {
        commands.put("trivia", new TriviaCommand());
        commands.put("help", new HelpCommand());
        log.info("Registered {} commands", commands.size());
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw();
        if (!message.startsWith("!")) return;

        String[] parts = message.substring(1).split(" ");
        String commandName = parts[0].toLowerCase();

        Command command = commands.get(commandName);
        if (command != null) {
            try {
                command.execute(event, parts);
            } catch (Exception e) {
                log.error("Error executing command: {}", commandName, e);
                event.getChannel().sendMessage("❌ An error occurred while executing the command.").queue();
            }
        }
    }
}
