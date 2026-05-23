package com.p3.app.config;

import com.p3.engine.CollectionsTreeStrategy;
import com.p3.engine.TreeAlgorithmStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TreeAlgorithmConfig {

    @Bean
    public TreeAlgorithmStrategy treeAlgorithmStrategy() {
        return new CollectionsTreeStrategy();
    }
}
