package com.jg.bookstore.event;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Slf4j
@Component
public class Listener {

    @Autowired
    private Environment environment;

    @SneakyThrows
    @EventListener
    public void handleApplicationStartedEvent(final ApplicationStartedEvent event) {
        final String ip = InetAddress.getLocalHost().getHostAddress();
        log.info("**************************");
        log.info("");
        log.info("Swagger UI: http://{}:{}/swagger-ui.html", ip, environment.getProperty("local.server.port"));
        log.info("");
        log.info("**************************");
    }
}
