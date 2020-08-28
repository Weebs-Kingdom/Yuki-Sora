package botApplication.discApplication.listeners;

import botApplication.discApplication.librarys.DiscApplicationServer;
import botApplication.discApplication.librarys.dungeon.queue.DungeonQueueHandler;
import botApplication.discApplication.librarys.poll.Poll;
import core.Engine;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveAllEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class DiscReactionListener extends ListenerAdapter {

    private final Engine engine;

    public DiscReactionListener(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (event.getUser().isBot()) {
            return;
        }
        DiscApplicationServer s = engine.getDiscEngine().getFilesHandler().getServerById(event.getGuild().getId());
        if (s != null) {
            if (!s.isSetupDone()) {
                return;
            }
        } else {
            return;
        }

        Poll p = getPoll(event.getMessageId(), event.getGuild().getId());
        if (p != null) {
            p.update(event.getReactionEmote().getName(), 1, event.getGuild(), event.getMember(), engine);
            return;
        }
        DungeonQueueHandler qh = getDungeonQueueHandler(s, event.getMessageId());
        if (qh != null) {
            event.getChannel().clearReactionsById(event.getMessageId()).complete();
            event.getChannel().addReactionById(qh.getMsgId(), qh.getEmoji()).queue();
            qh.click(event.getChannel(), engine, event.getGuild(), event.getMember());
            return;
        }

        if (!s.getCertificationMessageId().equals(event.getMessageId())) {
            return;
        }
        if (!exec(event.getReaction().retrieveUsers().complete(), event.getMember().getUser().getId()))
            return;

        switch (event.getReactionEmote().getName()) {
            //haken-Emoji
            case "✅":
                engine.getDiscEngine().getCertificationHandler().addMemberCertification(event.getMember(), event.getGuild());
                break;

            //X-Emoji
            case "❌":
                engine.getDiscEngine().getCertificationHandler().removeCertification(event.getMember(), event.getGuild());
                break;

            //Game-Emoji
            case "\uD83C\uDFAE":
                engine.getDiscEngine().getCertificationHandler().addTempGameCertification(event.getMember(), event.getGuild());
                break;
        }
    }

    @Override
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
        if (event.getUser().isBot()) {
            return;
        }
        DiscApplicationServer s = engine.getDiscEngine().getFilesHandler().getServerById(event.getGuild().getId());
        if (s != null) {
            if (!s.isSetupDone()) {
                return;
            }
        } else {
            return;
        }

        Poll p = getPoll(event.getMessageId(), event.getGuild().getId());
        if (p != null) {
            p.update(event.getReactionEmote().getName(), -1, event.getGuild(), event.getMember(), engine);
            return;
        }

        if (!s.getCertificationMessageId().equals(event.getMessageId())) {
            return;
        } else {
            switch (event.getReactionEmote().getName()) {
                //X-Emoji
                //haken-Emoji
                //Game-Emoji
                case "❌":
                case "✅":
                case "\uD83C\uDFAE":
                    engine.getDiscEngine().getCertificationHandler().removeCertification(event.getMember(), event.getGuild());
                    break;
            }
        }
    }

    @Override
    public void onGuildMessageReactionRemoveAll(GuildMessageReactionRemoveAllEvent event) {

    }

    private boolean exec(List<User> users, String userId) {
        int found = 0;
        for (User lUser : users) {
            if (lUser.getId().equals(userId))
                found++;
        }
        return found < 2;
    }

    private Poll getPoll(String msgId, String guildId) {
        for (Poll p : engine.getDiscEngine().getVoteCmd().getPolls()) {
            try {
                if (p.getGuildId().equals(guildId))
                    if (p.getMessageId().equals(msgId))
                        return p;
            } catch (Exception e) {
            }
        }
        return null;
    }

    private DungeonQueueHandler getDungeonQueueHandler(DiscApplicationServer server, String msgId) {
        if (server.getDungeonQueueHandler() == null)
            return null;
        if (server.getDungeonQueueHandler().getMsgId().equals(msgId)) {
            return server.getDungeonQueueHandler();
        }
        return null;
    }
}
