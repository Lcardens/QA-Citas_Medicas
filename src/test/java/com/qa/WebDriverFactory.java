package com.qa;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {

    public static WebDriver crearChrome() {
        ChromeOptions opciones = new ChromeOptions();
        opciones.addArguments("--start-maximized");
        return new ChromeDriver(opciones);
    }
}