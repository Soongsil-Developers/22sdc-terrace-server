package com.dev_camp.config

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.web.context.annotation.RequestScope
import java.util.logging.Level

@Configuration
class WebDriverConfig {
    @Bean
    @RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun webDriver(): WebDriver {
        val chromeDriverPath = "C:/Users/Public"
        System.setProperty("webdriver.chrome.driver", chromeDriverPath)
        val options = ChromeOptions()
            .addArguments("--headless")
            .setHeadless(true)
        val driver = ChromeDriver(options)
        driver.setLogLevel(Level.WARNING)
        return driver
    }
}