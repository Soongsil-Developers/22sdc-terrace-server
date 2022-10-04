package com.dev_camp.config

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope
import java.util.logging.Level

@Component
class WebDriverConfig(){
    @Bean(destroyMethod = "quit")
    @RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun webDriver(): WebDriver {
        val chromeDriverPath = "C:\\terrace_pj\\chromedriver.exe"
        System.setProperty("webdriver.chrome.driver", chromeDriverPath)

        val options = ChromeOptions()
            .addArguments("headless")
            .addArguments("disable-gpu")
            .addArguments("window-size=1920x1080")
            .addArguments("--blink-setting=imagesEnable=false")
            .addArguments("--disable-popup-blocking")
            .addArguments("--disable-application-cache")

        val driver = ChromeDriver(options)
        driver.setLogLevel(Level.WARNING)

        return driver
    }
}