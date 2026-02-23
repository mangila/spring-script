package com.github.mangila.script.controller;

import com.github.mangila.script.model.ScriptExecutionRequest;
import com.github.mangila.script.service.ScriptService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/script")
public class ScriptController {

    private final ScriptService service;

    public ScriptController(ScriptService service) {
        this.service = service;
    }

    @PostMapping
    public void enqueue(@RequestBody ScriptExecutionRequest payload) {
        service.enqueueScriptExecution(payload);
    }

    @GetMapping
    public Object poll() {
        return service.pollScriptExecutionResult();
    }
}
