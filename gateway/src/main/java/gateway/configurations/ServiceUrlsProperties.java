package gateway.configurations;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "spring.services.urls")
public class ServiceUrlsProperties {

    private final String user;
    private final String feed;
    private final String messenger;
    private final String subs;

    public ServiceUrlsProperties(String user, String feed, String messenger, String subs) {
        this.user = user;
        this.feed = feed;
        this.messenger = messenger;
        this.subs = subs;
    }
}