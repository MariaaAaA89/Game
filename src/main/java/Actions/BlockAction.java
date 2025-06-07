/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Actions;

import Components.GameCharacter;

/**
 *
 * @author maria
 */
public class BlockAction extends ActionType {

    /**
     *
     * @return
     */
    @Override
    public String getType() {
        return "Block";
    }

    /**
     *
     * @param human
     * @param enemy
     * @param enemyActionType
     */
    @Override
    public void realization(GameCharacter human, GameCharacter enemy, String enemyActionType) {
    }
}