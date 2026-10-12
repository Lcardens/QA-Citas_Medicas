package com.qa;

public class Credenciales {

    public static final String USUARIO_ADMIN = getenv("USUARIO_ADMIN");
    public static final String CLAVE_ADMIN = getenv("CLAVE_ADMIN");
    public static final String USUARIO_MEDICO = getenv("USUARIO_MEDICO");
    public static final String CLAVE_MEDICO = getenv("CLAVE_MEDICO");
    public static final String USUARIO_PACIENTE = getenv("USUARIO_PACIENTE");
    public static final String CLAVE_PACIENTE = getenv("CLAVE_PACIENTE");

    private static String getenv(String nombre) {
        String valor = System.getenv(nombre);
        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException("Falta la variable de entorno: " + nombre);
        }
        return valor;
    }
}