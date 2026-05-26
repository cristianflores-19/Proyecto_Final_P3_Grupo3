package com.p3.app.config;

import com.p3.engine.CollectionsTreeStrategy;
import com.p3.engine.CustomTreeStrategy;
import com.p3.engine.TreeAlgorithmStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TreeAlgorithmConfig {

    @Bean
    @ConditionalOnProperty(name = "app.tree-strategy", havingValue = "custom")
    public TreeAlgorithmStrategy customTreeStrategy() {
        return new CustomTreeStrategy();
    }

    @Bean
    @ConditionalOnProperty(name = "app.tree-strategy", havingValue = "collections")
    public TreeAlgorithmStrategy collectionsTreeStrategy() {
        return new CollectionsTreeStrategy();
    }
}