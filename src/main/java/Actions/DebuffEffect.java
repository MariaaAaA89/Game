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
public class DebuffEffect extends ActionType {

    /**
     *
     * @return
     */
    @Override
    public String getType() {
        return "Debuff";
    }

    /**
     *
     * @param human
     * @param enemy
     * @param enemyActionType
     */
    @Override
    public void realization(GameCharacter human, GameCharacter enemy, String enemyActionType) {
        switch (enemyActionType) {
            case "Hit", "Debuff", "Heal" -> {
            }
            case "Block" -> {
                if (Math.random()<0.75){
                    enemy.setDebuffTurns(enemy.getLevel());
                }
            }
        }
    }
}

