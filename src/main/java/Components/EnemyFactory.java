/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import static Components.CharacterType.*;

/**
 * Фабрика для создания противников
 * @author maria
 */
public class EnemyFactory {
    /**
     * Создает нового противника заданного типа
     * 
     * @param characterName тип противника из CharacterName
     * @return новый экземпляр GameCharacter
     * @see CharacterType
     */
    public GameCharacter create(CharacterType characterName) {
        return switch (characterName){
            default -> null;
            case BARAKA -> new GameCharacter(1, 100, 12, BARAKA);
            case LIU_KANG -> new GameCharacter(1, 70, 20, LIU_KANG);
            case SONYA_BLADE -> new GameCharacter(1, 80, 16, SONYA_BLADE);
            case SUB_ZERO -> new GameCharacter(1, 60, 16, SUB_ZERO);
            case SHAO_KAHN -> new GameCharacter(3, 100, 30, SHAO_KAHN);
        };
    }
}
