package com.music.trivia.command;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Slf4j
public class HelpCommand implements Command {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        String helpMessage = """
                **🎵 Music Trivia Bot Help**
                
                **Commands:**
                `!trivia start` - Start a new music trivia game
                `!trivia stop` - Stop the current game
                `!trivia help` - Show this help message
                
                **How to Play:**
                1. Use `!trivia start` to begin
                2. Listen to the music clip
                3. Guess the song title or artist
                4. Earn points for correct answers!
                """;
        event.getChannel().sendMessage(helpMessage).queue();
        log.info("Help command executed by user: {}", event.getAuthor().getName());
    }
}
