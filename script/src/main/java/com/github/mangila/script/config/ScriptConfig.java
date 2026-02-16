package com.github.mangila.script.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.scripting.DefaultScriptVariableGenerator;
import org.springframework.integration.scripting.dsl.Scripts;

import java.util.Objects;

@Configuration
public class ScriptConfig {

    private static final Logger log = LoggerFactory.getLogger(ScriptConfig.class);

    @Bean
    FileSystemResource groovyScriptResource() {
        return new FileSystemResource("groovy/Greeting.groovy");
    }

    @Bean
    IntegrationFlow groovyFlow(FileSystemResource groovyScriptResource) {
        return flow ->
                flow.transform(Scripts.processor(groovyScriptResource)
                                .lang("groovy")
                                .variableGenerator(new DefaultScriptVariableGenerator())
                                .refreshCheckDelay(0)
                        )
                        .handle(message -> log.info("{}", message.getPayload()));
    }

    @Bean
    MessagingTemplate template(IntegrationFlow groovyFlow) {
        var channel = Objects.requireNonNull(groovyFlow.getInputChannel());
        return new MessagingTemplate(channel);
    }
}
