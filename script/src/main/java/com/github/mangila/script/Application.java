package com.github.mangila.script;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.core.MessagingTemplate;

import java.awt.*;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final MessagingTemplate template;

    public Application(MessagingTemplate groovyFlow) {
        this.template = groovyFlow;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            template.convertAndSend(new Point(1, 2));
            Thread.sleep(5000);
        }
    }
}
