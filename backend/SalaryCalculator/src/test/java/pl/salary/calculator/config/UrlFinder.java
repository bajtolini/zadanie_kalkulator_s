package pl.salary.calculator.config;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log
@Component
public class UrlFinder {

    private String serverPort;

    public UrlFinder(@Value("${server.port}") String serverPort) {
        this.serverPort = serverPort;
    }

    public String findApplicationUrl() {
        String url = "http://localhost:" + serverPort;
        log.info("Application URL '" + url + "' found");
        return url;
    }
}