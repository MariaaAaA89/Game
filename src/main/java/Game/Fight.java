/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import Components.Player;
import Components.GameCharacter;
import Components.GameResults;
import Actions.*;
import static Components.CharacterType.SHAO_KAHN;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Контроллер боевой механики, управляющий взаимодействием между игроком
 * и противником в рамках одного раунда
 * Основные функции:
 * Обработка действий игрока и противника
 * Отслеживание истории действий игрока
 * Проверка условий победы/поражения
 * Взаимодействие с Представлением через Контроллер
 * @author maria
 */
public class Fight {
    Controller controller;
    Player player;
    GameCharacter enemy;

    public GameLevel location = new GameLevel();
    public ArrayList<ActionType> actionsList = new ArrayList<>() {
        {
            add(new AttackAction());
            add(new BlockAction());
            add(new DebuffEffect());
            add(new HealAction());
        }
    };
    private final Deque<String> playerActionHistory = new ArrayDeque<>();
    private static final int HISTORY_SIZE = 3;

    /**
     *
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     *
     * @param player
     */
    public void setHuman(Player player) {
        this.player = player;
    }

    /**
     *
     * @param enemy
     */
    public void setEnemy(GameCharacter enemy) {
        this.enemy = enemy;
    }

    /**
     *
     * @return
     */
    public Player getHuman() {
        return this.player;
    }

    /**
     *
     * @return
     */
    public GameCharacter getEnemy() {
        return this.enemy;
    }

    /**
     *
     * @param enemyAction
     * @param playerAction
     */
    public void playerMove(ActionType enemyAction, ActionType playerAction) {
        controller.setActionLabels(enemy, player, enemyAction, playerAction);
        playerAction.realization(player, enemy, enemyAction.getType());
    }

    /**
     *
     * @param enemyAction
     * @param playerAction
     */
    public void enemyMove(ActionType enemyAction, ActionType playerAction) {
        controller.setActionLabels(player, enemy, enemyAction, playerAction);
        playerAction.realization(enemy, player, enemyAction.getType());
    }

    /**
     *
     */
    public void checkDebuff() {
        if (!enemy.isDebuffed()) {
            controller.setDebuffLabel(enemy, false);
        }
        if (enemy.isDebuffed()) {
            controller.setDebuffLabel(enemy, true);
            enemy.loseDebuffTurn();
        }
        if (!player.isDebuffed()) {
            controller.setDebuffLabel(player, false);
        }
        if (player.isDebuffed()) {
            controller.setDebuffLabel(enemy, true);
            player.loseDebuffTurn();
        }

    }

    /**
     * Обрабатывает один раунд боя.
     * 
     * @param a номер действия игрока (0 - блок, 1 - атака, 2 - дебафф)
     * @param gameResults список для сохранения результатов игры
     * @param locationsNumber общее количество локаций
     * @param enemiesList массив всех возможных противников
     */
    public void hit(int a, ArrayList<GameResults> gameResults, int locationsNumber, GameCharacter[] enemiesList) {
        GameLogic action = new GameLogic();
        // Получаем историю действий игрока
        List<String> history = getPlayerActionHistory();

        // Передаем историю в метод выбора действия
        ActionType enemyAction = action.chooseEnemyAction(enemy, new ArrayList<>(actionsList), history);

        // Логируем действие игрока
        logPlayerAction(actionsList.get(a).getType());
        switch (a) {
            case 0 -> {
                playerMove(enemyAction,
                        actionsList.get(1));
                if (enemy.getHealth() > 0) {
                    enemyMove(actionsList.get(1), enemyAction);
                }
            }
            case 1 -> {
                playerMove(enemyAction, actionsList.get(0));
                if (enemy.getHealth() > 0) {
                    enemyMove(actionsList.get(0),
                            enemyAction);
                }
            }
            case 2 -> {
                playerMove(enemyAction, actionsList.get(2));
                if (enemy.getHealth() > 0) {
                    enemyMove(actionsList.get(2),
                            enemyAction);
                }
            }
        }
        controller.setRoundTexts(player, enemy);
        checkDebuff();
        controller.setHealthBar(player);
        controller.setHealthBar(enemy);
        checkDeath(gameResults, locationsNumber, enemiesList);
    }

    /**
     *
     * @param gameResults
     * @param locationsNumber
     * @param enemiesList
     */
    public void checkDeath(ArrayList<GameResults> gameResults, int locationsNumber, GameCharacter[] enemiesList) {
        if (player.getHealth() <= 0 & player.getItems()[2].getCount() > 0) {
            player.setHealth((int) (player.getMaxHealth() * 0.05));
            player.getItems()[2].setCount(-1);
            controller.setHealthBar(player);
            controller.revive(player, player.getItems());
        }
        if (player.getHealth() <= 0 | enemy.getHealth() <= 0) {
            if (location.getCurrentLocation() == locationsNumber & SHAO_KAHN.equals(enemy.getName())) {
                location.resetLocation(false, 1);
                endFinalRound(gameResults, enemiesList);
            } else {
                endRound(enemiesList);
            }
        }
    }

    /**
     *
     * @param enemiesList
     */
    public void endRound(GameCharacter[] enemiesList) {
        GameLogic action = new GameLogic();
        controller.makeEndFightDialogVisible();
        if (player.getHealth() > 0) {
            controller.setRoundEndText("You win");
            if (enemy.getName().equals(SHAO_KAHN)) {
                action.addItems(38, 23, 8, player.getItems());
                action.addPointsBoss(player);
                location.resetLocation(true, player.getLevel());
            } else {
                action.addItems(25, 15, 5, player.getItems());
                action.addPoints(player);
            }
        } else {
            reset(enemiesList);
            controller.setRoundEndText(enemy.getStringName() + " win");
        }
    }

    /**
     *
     * @param enemiesList
     */
    public void reset(GameCharacter[] enemiesList) {
        GameLogic action = new GameLogic();
        player.setDamage(16);
        player.setHealth(80);
        player.setMaxHealth(80);
        action.resetEnemies(enemiesList);
        player.setLevel(0);
        player.resetPoints();
        player.resetExperience();
        player.setNextExperience(40);
        location.setFullEnemiesList(enemiesList);
        location.resetLocation(false, player.getLevel());
    }

    /**
     *
     * @param gameResults
     * @param enemiesList
     */
    public void endFinalRound(ArrayList<GameResults> gameResults, GameCharacter[] enemiesList) {
        GameLogic action = new GameLogic();
        action.resetEnemies(enemiesList);
        String text = "Победа не на вашей стороне";
        if (player.getHealth() > 0) {
            action.addPoints(player);
            text = "Победа на вашей стороне";
        }
        boolean top = false;
        if (gameResults == null) {
            top = true;
        } else {
            int a = 0;
            for (GameResults results : gameResults) {
                if (player.getPoints() < results.getPoints()) {
                    a++;
                }
            }
            if (a < 10) {
                top = true;
            }
        }
        controller.gameEnding(text, top);
    }

    /**
     *
     */
    public void newRound() {
        controller.setPlayerMaxHealthBar(player);
        controller.setEnemyMaxHealthBar(enemy);
        player.setHealth(player.getMaxHealth());
        enemy.setHealth(enemy.getMaxHealth());
        controller.setHealthBar(player);
        controller.setHealthBar(enemy);
    }
    
    /**
     *
     * @param action
     */
    public void logPlayerAction(String action) {
        if (playerActionHistory.size() >= HISTORY_SIZE) {
            playerActionHistory.removeFirst();
        }
        playerActionHistory.addLast(action);
    }
    
    /**
     *
     * @return
     */
    public List<String> getPlayerActionHistory() {
        return new ArrayList<>(playerActionHistory);
    }
}