package com.github.mangila.script.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.scripting.DefaultScriptVariableGenerator;
import org.springframework.integration.scripting.dsl.Scripts;
import org.springframework.messaging.MessageChannel;

import java.util.Objects;

@Configuration
public class ScriptConfig {

    @Bean
    MessageChannel outputChannel() {
        return new QueueChannel(256);
    }

    @Bean
    FileSystemResource groovyScriptResource() {
        return new FileSystemResource("groovy/Greeting.groovy");
    }

    @Bean
    IntegrationFlow groovyFlow(FileSystemResource groovyScriptResource, MessageChannel outputChannel) {
        return flow ->
                flow.transform(Scripts.processor(groovyScriptResource)
                                .lang("groovy")
                                .variableGenerator(new DefaultScriptVariableGenerator())
                                .refreshCheckDelay(0)
                        )
                        .channel(outputChannel);
    }

    @Bean
    MessagingTemplate template(IntegrationFlow groovyFlow) {
        var channel = Objects.requireNonNull(groovyFlow.getInputChannel());
        return new MessagingTemplate(channel);
    }
}
