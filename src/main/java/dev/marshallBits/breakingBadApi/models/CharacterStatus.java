package dev.marshallBits.breakingBadApi.models;

public enum CharacterStatus {
    ALIVE("Vivo"),
    DEAD("Muerto");

    // 🗒️ PROPIEDADES
    // creamos una propiedad privada para almacenar el nombre que se mostrará en la interfaz de usuario, "Vivo" o "Muerto". Si no incluimos esto, el nombre del enum se mostrará tal cual, por ejemplo "ALIVE" o "DEAD"
    private final String displayName;

    // 🏗️ CONSTRUCTOR
    // creamos un constructor privado para inicializar el nombre que se mostrará en la interfaz de usuario
    CharacterStatus(String displayName) {
        this.displayName = displayName;
    }

    // 🏷️ MÉTODOS
    // este getDisplayName() devuelve el nombre que se mostrará en la interfaz de usuario, en este caso "Vivo" o "Muerto"
    public String getDisplayName() {
        return displayName;
    }
}
