package com.github.mangila.script.service;

import com.github.mangila.script.model.ScriptExecutionRequest;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ScriptService {

    private final MessageChannel outputChannel;
    private final MessagingTemplate template;

    public ScriptService(MessageChannel outputChannel,
                         MessagingTemplate template) {
        this.outputChannel = outputChannel;
        this.template = template;
    }

    public void enqueueScriptExecution(ScriptExecutionRequest event) {
        template.convertAndSend(event);
    }

    public Object pollScriptExecutionResult() {
        var payload =  template.receiveAndConvert(outputChannel, Duration.ofSeconds(10).toMillis());
        return payload;
    }
}
