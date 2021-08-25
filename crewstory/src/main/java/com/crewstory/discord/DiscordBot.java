package com.crewstory.discord;

import com.crewstory.discord.database.UserRepository;
import com.crewstory.discord.model.User;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;


public class DiscordBot extends ListenerAdapter
{
    private static final Logger LOG = LoggerFactory.getLogger(DiscordBot.class);
    private static String token;
    private static String channelId;
    private static String logChannelId;
    private static UserRepository userRepository;

    private static void readProperty() {
        try {
            InputStream in = DiscordBot.class.getClassLoader().getResourceAsStream("config.properties");
            Properties prop = new Properties();
            if(in == null)
                LOG.error("Unable to load config.properties config file.");
            prop.load(in);
            token = prop.getProperty("discord.bot.token");
            channelId = prop.getProperty("discord.bot.channel.id.announcements");
            logChannelId = prop.getProperty("discord.bot.channel.id.log");

        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public static void main( String[] args ) {
        readProperty();
        try {
            JDA jda = JDABuilder.createDefault(token).build();
            jda.addEventListener(new DiscordBot());
            jda.awaitReady();
            jda.setAutoReconnect(true);
            jda.getPresence().setActivity(Activity.watching("Syncing"));
            userRepository = new UserRepository();

        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        Objects.requireNonNull(event.getJDA().getTextChannelById(logChannelId))
                .sendMessage("Bot Status: ONLINE").queue();
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if(event.getChannel().getId().equals(channelId)) {
            String fileUrls = String.join(";", event.getMessage().getAttachments().stream().map(m -> m.getUrl()).collect(Collectors.toList()));
            String title = "";
            if(event.getMessage().getContentRaw().split("\n").length > 1) {
                title = event.getMessage().getContentRaw().split("\n")[1];
            }
            String content = event.getMessage().getContentRaw();
            LocalDateTime date = LocalDateTime.now();
            String author = event.getAuthor().getName();

            userRepository.create(new User(0L, fileUrls, title, content, date, author));
        }
    }
}
