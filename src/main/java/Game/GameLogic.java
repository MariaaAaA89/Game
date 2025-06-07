/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import Components.Player;
import Components.InventoryItem;
import Components.GameCharacter;
import Actions.ActionType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, содержащий основную игровую логику и адаптивную систему поведения противников
 * Реализует:
 * Адаптивный выбор действий противниками
 * Систему прокачки персонажа
 * Механику использования предметов
 * Генерацию наград за победу
 * @author maria
 */
public class GameLogic {
    private final int[] experienceForNextLevel = {40, 90, 180, 260, 410, 1000};
    private static final int HISTORY_SIZE = 3;

    /**
     * Выбирает действие для противника на основе анализа
     * поведения игрока
     * 
     * @param enemy текущий противник
     * @param actions
     * @param playerHistory история последних действий игрока
     * @return выбранное действие
     * @throws IllegalArgumentException если enemy == null
     */
    public ActionType chooseEnemyAction(GameCharacter enemy, List<ActionType> actions, List<String> playerHistory) {
        Map<String, Double> actionWeights = new HashMap<>();

        // Базовые вероятности для каждого типа врага
        switch (enemy.getName()) {
            case SUB_ZERO -> {
                actionWeights.put("Hit", 0.4);
                actionWeights.put("Block", 0.3);
                actionWeights.put("Debuff", 0.3);
            }
            case SHAO_KAHN -> {
                actionWeights.put("Hit", 0.6);
                actionWeights.put("Block", 0.4);
            }
            case LIU_KANG -> {
                actionWeights.put("Hit", 0.5);
                actionWeights.put("Counter", 0.5);
            }
            default -> {
                actionWeights.put("Hit", 0.7);
                actionWeights.put("Block", 0.3);
            }
        }

        // Адаптация на основе действий игрока
        adjustWeightsByPlayerActions(actionWeights, playerHistory);

        return selectWeightedAction(actions, actionWeights);
    }

    private void adjustWeightsByPlayerActions(Map<String, Double> weights, List<String> playerHistory) {
        long playerHits = playerHistory.stream().filter(a -> a.equals("Hit")).count();
        long playerBlocks = playerHistory.stream().filter(a -> a.equals("Block")).count();

        if (playerHits > HISTORY_SIZE / 2) {
            weights.put("Block", weights.getOrDefault("Block", 0.0) * 1.5);
            weights.put("Counter", weights.getOrDefault("Counter", 0.0) * 1.3);
        }

        if (playerBlocks > HISTORY_SIZE / 2) {
            weights.put("Debuff", weights.getOrDefault("Debuff", 0.0) * 1.4);
            weights.put("Hit", weights.get("Hit") * 0.8);
        }

        normalizeWeights(weights);
    }
    
    private void normalizeWeights(Map<String, Double> weights) {
        // Рассчитываем сумму всех весов
        double totalWeight = weights.values().stream()
            .mapToDouble(Double::doubleValue)
            .sum();

        // Нормализуем веса, если сумма не нулевая
        if (totalWeight > 0) {
            weights.replaceAll((action, weight) -> weight / totalWeight);
        } 
        // Дефолтные значения при нулевой сумме
        else {
            double defaultWeight = 1.0 / weights.size();
            weights.replaceAll((action, weight) -> defaultWeight);
        }
    }

    private ActionType selectWeightedAction(List<ActionType> actions, Map<String, Double> weights) {
        double total = weights.values().stream().mapToDouble(Double::doubleValue).sum();
        double random = Math.random() * total;

        double current = 0;
        for (ActionType action : actions) {
            current += weights.getOrDefault(action.getType(), 0.0);
            if (random <= current) {
                return action;
            }
        }
        return actions.get(actions.size() - 1);
    }

    /**
     *
     * @param player
     */
    public void addPoints(Player player) {
        player.setExperience(20 + 5 * player.getLevel());
        player.setPoints(20 + 5 * player.getLevel() + player.getHealth() / 4);
    }

    /**
     *
     * @param player
     * @return
     */
    public boolean checkExperience(Player player) {
        return player.getExperience() >= player.getNextExperience();
    }

    /**
     *
     * @param player
     * @param enemies
     */
    public void levelUp(Player player, GameCharacter[] enemies) {
        player.addLevel();
        int i = 0;
        while (player.getNextExperience() >= experienceForNextLevel[i]) {
            i = i + 1;
        }
        player.setNextExperience(experienceForNextLevel[i]);
        for (int j = 0; j < 5; j++) {
            newHealthEnemy(enemies[j], player);
        }
    }

    /**
     *
     * @param player
     */
    public void addPointsBoss(Player player) {
        player.setExperience(50);
        player.setPoints(65 + player.getHealth() / 2);
    }

    /**
     *
     * @param k1
     * @param k2
     * @param k3
     * @param items
     */
    public void addItems(int k1, int k2, int k3, InventoryItem[] items) {
        double i = Math.random();
        if (i < k1 * 0.01) {
            items[0].setCount(1);
        }
        if (i >= k1 * 0.01 & i < (k1 + k2) * 0.01) {
            items[1].setCount(1);
        }
        if (i >= (k1 + k2) * 0.01 & i < (k1 + k2 + k3) * 0.01) {
            items[2].setCount(1);
        }
    }

    /**
     *
     * @param player
     */
    public void addHealthToPlayer(Player player) {
        player.addMaxHealth(40 + 5 * player.getLevel());
    }

    /**
     *
     * @param player
     */
    public void addDamageToPlayer(Player player) {
        player.addDamage(5 + player.getLevel());
    }

    /**
     *
     * @param enemy
     * @param player
     */
    public void newHealthEnemy(GameCharacter enemy, Player player) {
        enemy.addMaxHealth(enemy.getMaxHealth() * (35 - 3 * player.getLevel()) / 100);
        enemy.addDamage(enemy.getDamage() * (20 + player.getLevel()) / 100);
        enemy.addLevel();
    }

    /**
     *
     * @param human
     * @param items
     * @param name
     * @param controller
     */
    public void useItem(GameCharacter human, InventoryItem[] items, String name, Controller controller) {
        switch (name) {
            case "First item" -> {
                if (items[0].getCount() > 0) {
                    human.addHealth((int) (human.getMaxHealth() * 0.25));
                    items[0].setCount(-1);
                } else {
                    controller.openCantUseItemDialog();
                }
            }
            case "Second item" -> {
                if (items[1].getCount() > 0) {
                    human.addHealth((int) (human.getMaxHealth() * 0.5));
                    items[1].setCount(-1);
                } else {
                    controller.openCantUseItemDialog();
                }
            }
            case "Third item" -> controller.openCantUseItemDialog();
        }
    }

    /**
     *
     * @param enemiesList
     */
    public void resetEnemies(GameCharacter[] enemiesList) {
        for (GameCharacter enemy : enemiesList) {
            enemy.setLevel(1);
            switch (enemy.getName()) {
                case SUB_ZERO -> {
                    enemy.setDamage(16);
                    enemy.setMaxHealth(60);
                }
                case SONYA_BLADE -> {
                    enemy.setDamage(16);
                    enemy.setMaxHealth(80);
                }
                case SHAO_KAHN -> {
                    enemy.setDamage(30);
                    enemy.setMaxHealth(100);
                }
                case LIU_KANG -> {
                    enemy.setDamage(20);
                    enemy.setMaxHealth(70);
                }
                case BARAKA -> {
                    enemy.setDamage(12);
                    enemy.setMaxHealth(100);
                }
            }
        }
    }
}

