package com.music.trivia;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import com.music.trivia.bot.DiscordBot;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try {

            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .ignoreIfMalformed()
                    .load();

            String token = dotenv.get("DISCORD_TOKEN");
            if (token == null || token.isEmpty()) {
                log.error("Discord token not found in environment variables");
                return;
            }

            log.info("Starting Music Trivia Bot...");
            new DiscordBot(token);

        } catch (Exception e) {
            log.error("Error starting bot", e);
        }
    }
}