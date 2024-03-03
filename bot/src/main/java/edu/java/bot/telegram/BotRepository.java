package edu.java.bot.telegram;


import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BotRepository {
    private final Map<Long, List<URL>> memory;
    private final String userNotFoundMessage = "User not found!";
    private final String nullArgsMessage = "Chat ID or URL is null!";

    @Autowired
    public BotRepository(Map<Long, List<URL>> memory) {
        this.memory = memory;
    }


    public boolean trackLink(Long chatId, URL url) {
        if (chatId == null || url == null) {
            throw new IllegalArgumentException(nullArgsMessage);
        }

        var trackingUrls = new ArrayList<>(memory.get(chatId));
        if (trackingUrls.contains(url)) {
            return false;
        }
        trackingUrls.add(url);
        trackingUrls.sort(Comparator.comparing(URL::toString));
        memory.put(chatId, trackingUrls);
        return true;
    }


    public boolean untrackLink(Long chatId, URL url) {

        var trackingUrls = new ArrayList<>(memory.get(chatId));
        if (!trackingUrls.contains(url)) {
            return false;
        }
        trackingUrls.remove(url);
        memory.put(chatId, trackingUrls);
        return true;
    }


    public List<URL> listLink(Long chatId) {
        return memory.get(chatId);
    }

    public boolean addUser(Long chatId) {
        if (memory.containsKey(chatId)) {
            return false;
        }
        memory.put(chatId, new ArrayList<>());
        return true;
    }
}
