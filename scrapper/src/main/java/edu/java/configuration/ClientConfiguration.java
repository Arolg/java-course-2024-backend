package edu.java.configuration;

import edu.java.configuration.client.GitHubClient;
import edu.java.configuration.client.StackoverflowClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    private final ApplicationConfig config;

    @Autowired
    public ClientConfiguration(ApplicationConfig config) {
        this.config = config;
    }

    @Bean
    public GitHubClient githubClientBean() {
        return new GitHubClient(config.api().githubBaseUrl());
    }

    @Bean
    public StackoverflowClient stackoverflowClientBean() {
        return new StackoverflowClient(config.api().stackoverflowBaseUrl());
    }
}
