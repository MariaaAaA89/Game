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
public class HealAction extends ActionType {

    /**
     *
     * @return
     */
    @Override
    public String getType() {
        return "Heal";
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
            case "Hit", "Heal", "Debuff" -> {
            }
            case "Block" -> {
                human.addHealth((human.getMaxHealth() - human.getHealth()) / 2);
            }
        }
    }
}