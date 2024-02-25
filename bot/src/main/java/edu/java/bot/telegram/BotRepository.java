package edu.java.bot.telegram;


import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class BotRepository {
    private final Map<Long, List<URL>> memory = new HashMap<>();
    private final String userNotFoundMessage = "User not found!";
    private final String nullArgsMessage = "Chat ID or URL is null!";


    public boolean trackLink(Long chatId, URL url) {
        List<URL> links = memory.getOrDefault(chatId, new ArrayList<>());

        links.add(url);

        memory.put(chatId, links);

        if (chatId == null || url == null) {
            throw new IllegalArgumentException(nullArgsMessage);
        }
        if (!memory.containsKey(chatId)) {
            throw new IllegalArgumentException(userNotFoundMessage);
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
        return memory.getOrDefault(chatId, new ArrayList<>());
    }

}
