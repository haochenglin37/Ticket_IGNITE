package com.example.ticketing.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IgniteConfig {

    @Bean(destroyMethod = "close")
    public Ignite ignite() {
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setIgniteInstanceName("ticketing-ignite");
        cfg.setPeerClassLoadingEnabled(true);
        return Ignition.start(cfg);
    }
}
