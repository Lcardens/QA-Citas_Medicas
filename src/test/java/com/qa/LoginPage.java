package com.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait espera;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void abrir() {
        driver.get("http://localhost:4200/login");
    }

    public void iniciarSesion(String email, String clave) {
        espera.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[name='email']")));
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys(clave);
        driver.findElement(By.xpath("//button[contains(@class, 'btn-primary') and contains(text(), 'Iniciar sesión')]")).click();
    }

    public boolean llegoAlInicio() {
        return espera.until(ExpectedConditions.urlContains("/dashboard/inicio"));
    }
}