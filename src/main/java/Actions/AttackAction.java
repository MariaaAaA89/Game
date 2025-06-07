/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Actions;

import Components.GameCharacter;

/**
 * Действие атаки
 * Наносит урон противнику
 * @author maria
 */
public class AttackAction extends ActionType {
    private static final double DEBUFF_DAMAGE_MULTIPLIER = 1.25;
    private static final double BLOCK_DAMAGE_MULTIPLIER = 0.5;
    private static final double DEBUFF_BLOCK_DAMAGE_MULTIPLIER = 1.15;
    private static final double HEAL_DAMAGE_MULTIPLIER = 2.0;

    /**
     *
     * @return
     */
    @Override
    public String getType() {
        return "Hit";
    }

    /**
     * Реализует логику нанесения урона с учетом:
     * Состояния дебаффа
     * Действия противника
     * Случайных модификаторов
     * @param human
     * @param enemy
     * @param enemyActionType
     */
    @Override
    public void realization(GameCharacter human, GameCharacter enemy, String enemyActionType) {
        int damage = calculateDamage(human, enemy);
        switch (enemyActionType) {
            case "Hit":
                enemy.addHealth(-damage);
                break;
            case "Block":
                if (Math.random() < BLOCK_DAMAGE_MULTIPLIER) {
                    enemy.addHealth(-damage / 2);
                }
                break;
            case "Debuff":
                enemy.addHealth((int) (-damage * DEBUFF_BLOCK_DAMAGE_MULTIPLIER));
                break;
            case "Heal":
                enemy.addHealth((int) (-damage * HEAL_DAMAGE_MULTIPLIER));
                break;
            default:
                System.out.println("Unknown enemy action type: " + enemyActionType);
        }
    }

    private int calculateDamage(GameCharacter human, GameCharacter enemy) {
        int damage = human.getDamage();
        if (enemy.isDebuffed() && human.isDebuffed()) {
            damage = (int) (damage * DEBUFF_DAMAGE_MULTIPLIER / 2);
        } else if (enemy.isDebuffed() &&!human.isDebuffed()) {
            damage = (int) (damage * DEBUFF_DAMAGE_MULTIPLIER);
        } else if (!enemy.isDebuffed() && human.isDebuffed()) {
            damage = damage / 2;
        }
        return damage;
    }
}