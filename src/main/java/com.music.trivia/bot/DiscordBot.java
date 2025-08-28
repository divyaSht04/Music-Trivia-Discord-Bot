package com.music.trivia.bot;

import com.music.trivia.command.CommandManager;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

@Slf4j
public class DiscordBot extends ListenerAdapter {
    private final JDA jda;
    private final CommandManager commandManager;

    public DiscordBot(String token) throws LoginException {
        JDABuilder builder = JDABuilder.createDefault(token);

        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_VOICE_STATES);
        builder.enableCache(CacheFlag.VOICE_STATE);

        builder.setActivity(Activity.listening("!trivia help"));
        builder.addEventListeners(this);

        this.commandManager = new CommandManager();
        builder.addEventListeners(commandManager);

        this.jda = builder.build();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        log.info("Music Trivia Bot is ready!");
        log.info("Bot name: {}", jda.getSelfUser().getName());
        log.info("Guilds: {}", jda.getGuilds().size());
    }

}