package edu.java.scrapper.configuration;

import edu.java.scrapper.configuration.client.GitHubClient;
import edu.java.scrapper.configuration.client.StackoverflowClient;
import org.springframework.beans.factory.annotation.Autowired;
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
