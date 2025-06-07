/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Actions;

import Components.GameCharacter;

/**
 * Абстрактный класс для представления игровых действий
 * Все конкретные действия должны наследовать этот класс
 * @author maria
 */
public abstract class ActionType {
    /**
     * Возвращает тип действия
     * 
     * @return строковый идентификатор действия
     */
    public abstract String getType();

    /**
     *
     * @param gameCharacter
     * @param enemy
     * @param enemyActionType
     */
    public abstract void realization(GameCharacter gameCharacter, GameCharacter enemy, String enemyActionType);
}
