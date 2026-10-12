package com.qa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest {

    private WebDriver driver;

    @AfterEach
    void cerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void elAdminPuedeIniciarSesion() {
        driver = WebDriverFactory.crearChrome();

        LoginPage login = new LoginPage(driver);
        login.abrir();
        login.iniciarSesion(Credenciales.USUARIO_ADMIN, Credenciales.CLAVE_ADMIN);

        assertTrue(login.llegoAlInicio(), "El admin debería aterrizar en /dashboard/inicio");
    }
}