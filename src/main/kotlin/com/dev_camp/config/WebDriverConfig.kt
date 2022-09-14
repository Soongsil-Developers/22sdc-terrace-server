package com.dev_camp.config

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.logging.Level

@Configuration
class WebDriverConfig {
    @Bean
    fun webDriver(): WebDriver {
        val chromeDriverPath = "C:\\terrace_pj\\chromedriver.exe"
        System.setProperty("webdriver.chrome.driver",chromeDriverPath)
        val options = ChromeOptions()
            .addArguments("disable-gpu","--window-size=1920,1200")
            .setHeadless(true)
        val driver = ChromeDriver(options)
        System.out.print("webdriver run!!!!!!!!!!!!!!!!!!!!!") //싱글톤 확인 위함
        driver.setLogLevel(Level.WARNING)
        return driver
    }
}