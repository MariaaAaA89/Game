/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

/**
 * Перечисление возможных имен персонажей 
 * @author maria
 */
public enum CharacterType {
    /** Игрок */ 
    You("You") ,
    BARAKA ("Baraka"),
    LIU_KANG ("Liu-Kang"),
    SONYA_BLADE ("Sonya-Blade"),
    SUB_ZERO ("Sub-Zero"),
    /** Босс */ 
    SHAO_KAHN ("Shao-Kahn");

    private String name;

    CharacterType(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getString() {
        return name;
    }
}
