package com.ambrosezen.weblearnenglish.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

public class ConfigEnv implements ApplicationContextInitializer<ConfigurableApplicationContext> {
  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    Dotenv dotenv = Dotenv.load();

    System.setProperty("EMAIL_USERNAME", dotenv.get("EMAIL_USERNAME"));
    System.setProperty("EMAIL_PASSWORD", dotenv.get("EMAIL_PASSWORD"));
  }
}
