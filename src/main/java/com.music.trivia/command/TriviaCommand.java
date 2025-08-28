package com.music.trivia.command;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Slf4j
public class TriviaCommand implements Command {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if (args.length < 2) {
            sendHelp(event);
            return;
        }

        String subCommand = args[1].toLowerCase();
        log.info("Executing trivia subcommand: {}", subCommand);

        switch (subCommand) {
            case "start":
                startGame(event);
                break;
            case "stop":
                stopGame(event);
                break;
            case "help":
            default:
                sendHelp(event);
                break;
        }
    }

    private void startGame(MessageReceivedEvent event) {
        event.getChannel().sendMessage("🎵 Music Trivia starting soon! (Work in progress)").queue();
        log.info("Game start requested by user: {}", event.getAuthor().getName());
        // Game logic will be implemented in later steps
    }

    private void stopGame(MessageReceivedEvent event) {
        event.getChannel().sendMessage("⏹️ Music Trivia stopped.").queue();
        log.info("Game stop requested by user: {}", event.getAuthor().getName());
    }

    private void sendHelp(MessageReceivedEvent event) {
        String helpMessage = """
                **🎵 Music Trivia Commands:**
                `!trivia start` - Start a new trivia game
                `!trivia stop` - Stop the current game
                `!trivia help` - Show this help message
                """;
        event.getChannel().sendMessage(helpMessage).queue();
    }
}
