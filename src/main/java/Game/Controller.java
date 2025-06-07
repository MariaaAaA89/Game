/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import Components.Player;
import Components.InventoryItem;
import Components.GameCharacter;
import Actions.ActionType;
import View.JFrames;

import javax.swing.*;

import static Components.CharacterType.You;

/**
 * Посредник для взаимодействия между игровой логикой и графическим интерфейсом
 * Реализует паттерн Медиатор (Посредник) для:
 * Обновления элементов интерфейса
 * Отображения диалоговых окон
 * Передачи данных между компонентами
 * @author maria
 */
public class Controller {
    private static final int DIALOG_WIDTH = 600;
    private static final int DIALOG_HEIGHT = 500;
    private static final String ACTION_FORMAT = "%s %s %s";
    private static final String DEBUFF_FORMAT = "%s is debuffed for %d turns";
    private JLabel enemyHealthLabel;
    private JLabel playerHealthLabel;
    private JLabel pointsValueLabel;
    private JLabel experienceValueLabel;
    private JLabel playerLevelLabel;
    private JLabel enemyLevelLabel;
    private JLabel playerDamageValueLabel;
    private JLabel endRoundLabel;
    private JLabel enemyDebuffLabel;
    private JLabel victoryLabel;
    private JLabel endGameWithoutLadderTitlleLabel;
    private JLabel playerActionLabel;
    private JLabel playerDebuffLabel;
    private JLabel enemyActionLabel;
    private JProgressBar playerHealthBar;
    private JProgressBar enemyHealthBar;
    private JDialog endFightDialog;
    private JDialog endGameDialog;
    private JDialog endGameWithoutLadderDialog;
    private JDialog cantUseItemDialog;
    private JFrame fightFrame;
    private JRadioButton firstItemButton;
    private JRadioButton secondItemButton;
    private JRadioButton thirdItemButton;
    
    public JFrames view;

    /**
     *
     * @param enemy
     * @param human
     * @param enemyAction
     * @param playerAction
     */
    public void setActionLabels(GameCharacter enemy, GameCharacter human, ActionType enemyAction, ActionType playerAction) {
        updateLabel(playerActionLabel, human.getStringName(), "uses", playerAction.getType());
        updateLabel(enemyActionLabel, enemy.getStringName(), "use", enemyAction.getType());
    }

    /**
     *
     * @param gameCharacter
     * @param isDebuffed
     */
    public void setDebuffLabel(GameCharacter gameCharacter, boolean isDebuffed) {
        String debuffText = isDebuffed ? String.format(DEBUFF_FORMAT, gameCharacter.getName(), gameCharacter.getDebuffTurns()) : "";
        updateDebuffLabel(gameCharacter, debuffText);
    }

    private void updateLabel(JLabel label, String playerName, String action, String actionType) {
        label.setText(String.format(ACTION_FORMAT, playerName, action, actionType));
    }

    private void updateDebuffLabel(GameCharacter gameCharacter, String debuffText) {
        if (gameCharacter.getName().equals(You)) {
            playerDebuffLabel.setText(debuffText);
        } else {
            enemyDebuffLabel.setText(debuffText);
        }
    }

    /**
     *
     * @param gameCharacter
     */
    public void setHealthBar(GameCharacter gameCharacter) {
        JProgressBar healthBar = gameCharacter.getName().equals(You) ? playerHealthBar : enemyHealthBar;
        healthBar.setValue(Math.max(gameCharacter.getHealth(), 0));
    }

    /**
     *
     * @param human
     */
    public void setPlayerMaxHealthBar(GameCharacter human) {
        playerHealthBar.setMaximum(human.getMaxHealth());
    }

    /**
     *
     * @param enemy
     */
    public void setEnemyMaxHealthBar(GameCharacter enemy) {
        enemyHealthBar.setMaximum(enemy.getMaxHealth());
    }

    /**
     *
     * @param human
     * @param items
     */
    public void revive(GameCharacter human, InventoryItem[] items) {
        playerHealthLabel.setText(human.getHealth() + "/" + human.getMaxHealth());
        thirdItemButton.setText(items[2].getName() + ", " + items[2].getCount() + " шт");
        playerActionLabel.setText("Вы воскресли");
    }

    /**
     *
     * @param text
     * @param isVictory
     */
    public void gameEnding(String text, boolean isVictory) {
        if (isVictory) {
            endGameDialog.setVisible(true);
            endGameDialog.setBounds(150, 150, DIALOG_WIDTH, DIALOG_HEIGHT);
            victoryLabel.setText(text);
        } else {
            endGameWithoutLadderDialog.setVisible(true);
            endGameWithoutLadderDialog.setBounds(150, 150, DIALOG_WIDTH, DIALOG_HEIGHT);
            endGameWithoutLadderTitlleLabel.setText(text);
        }
        fightFrame.dispose();
    }

    /**
     *
     */
    public void makeEndFightDialogVisible() {
        view.setPanelEnabled(view.getFightPanel(),false);
        endFightDialog.setVisible(true);
        endFightDialog.setBounds(300, 150, 700, 600);
    }

    /**
     *
     * @param text
     */
    public void setRoundEndText(String text) {
        endRoundLabel.setText(text);
    }

    /**
     *
     */
    public void openCantUseItemDialog() {
        cantUseItemDialog.setVisible(true);
        cantUseItemDialog.setBounds(300, 200, 400, 300);
    }

    /**
     *
     * @param items
     */
    public void setBagText(InventoryItem[] items) {
        firstItemButton.setText(items[0].getName() + ", " + items[0].getCount() + " шт");
        secondItemButton.setText(items[1].getName() + ", " + items[1].getCount() + " шт");
        thirdItemButton.setText(items[2].getName() + ", " + items[2].getCount() + " шт");
    }

    /**
     *
     * @param human
     * @param enemy
     */
    public void setRoundTexts(GameCharacter human, GameCharacter enemy) {
        updateHealthLabel(playerHealthLabel, human);
        updateHealthLabel(enemyHealthLabel, enemy);
    }

    private void updateHealthLabel(JLabel label, GameCharacter gameCharacter) {
        if (gameCharacter.getHealth() >= 0) {
            label.setText(gameCharacter.getHealth() + "/" + gameCharacter.getMaxHealth());
        } else {
            label.setText("0/" + gameCharacter.getMaxHealth());
        }
    }

    /**
     *
     * @param human
     * @param enemy
     * @param items
     */
    public void setNewRoundTexts(GameCharacter human, GameCharacter enemy, InventoryItem[] items) {
        playerActionLabel.setText("");
        enemyActionLabel.setText("");
        pointsValueLabel.setText(Integer.toString(((Player) human).getPoints()));
        experienceValueLabel.setText(((Player) human).getExperience() + "/" + ((Player) human).getNextExperience());
        playerLevelLabel.setText((human.getLevel()) + 1 + " level");
        enemyLevelLabel.setText(enemy.getLevel() + " level");
        playerHealthLabel.setText(human.getMaxHealth() + "/" + human.getMaxHealth());
        enemyHealthLabel.setText(enemy.getMaxHealth() + "/" + enemy.getMaxHealth());
        playerDamageValueLabel.setText(Integer.toString(human.getDamage()));
        setBagText(items);
    }

    /**
     *
     * @param enemyHealthLabel
     */
    public void setEnemyHealthLabel(JLabel enemyHealthLabel) {
        this.enemyHealthLabel = enemyHealthLabel;
    }

    /**
     *
     * @param playerHealthLabel
     */
    public void setPlayerHealthLabel(JLabel playerHealthLabel) {
        this.playerHealthLabel = playerHealthLabel;
    }

    /**
     *
     * @param pointsValueLabel
     */
    public void setPointsValueLabel(JLabel pointsValueLabel) {
        this.pointsValueLabel = pointsValueLabel;
    }

    /**
     *
     * @param experienceValueLabel
     */
    public void setExperienceValueLabel(JLabel experienceValueLabel) {
        this.experienceValueLabel = experienceValueLabel;
    }

    /**
     *
     * @param playerLevelLabel
     */
    public void setPlayerLevelLabel(JLabel playerLevelLabel) {
        this.playerLevelLabel = playerLevelLabel;
    }

    /**
     *
     * @param enemyLevelLabel
     */
    public void setEnemyLevelLabel(JLabel enemyLevelLabel) {
        this.enemyLevelLabel = enemyLevelLabel;
    }

    /**
     *
     * @param playerDamageValueLabel
     */
    public void setPlayerDamageValueLabel(JLabel playerDamageValueLabel) {
        this.playerDamageValueLabel = playerDamageValueLabel;
    }

    /**
     *
     * @param endRoundLabel
     */
    public void setEndRoundLabel(JLabel endRoundLabel) {
        this.endRoundLabel = endRoundLabel;
    }

    /**
     *
     * @param enemyDebuffLabel
     */
    public void setEnemyDebuffLabel(JLabel enemyDebuffLabel) {
        this.enemyDebuffLabel = enemyDebuffLabel;
    }

    /**
     *
     * @param victoryLabel
     */
    public void setVictoryLabel(JLabel victoryLabel) {
        this.victoryLabel = victoryLabel;
    }

    /**
     *
     * @param endGameWithoutLadderTitlleLabel
     */
    public void setEndGameWithoutLadderTitlleLabel(JLabel endGameWithoutLadderTitlleLabel) {
        this.endGameWithoutLadderTitlleLabel = endGameWithoutLadderTitlleLabel;
    }

    /**
     *
     * @param playerActionLabel
     */
    public void setPlayerActionLabel(JLabel playerActionLabel) {
        this.playerActionLabel = playerActionLabel;
    }

    /**
     *
     * @param playerDebuffLabel
     */
    public void setPlayerDebuffLabel(JLabel playerDebuffLabel) {
        this.playerDebuffLabel = playerDebuffLabel;
    }

    /**
     *
     * @param enemyActionLabel
     */
    public void setEnemyActionLabel(JLabel enemyActionLabel) {
        this.enemyActionLabel = enemyActionLabel;
    }

    /**
     *
     * @param playerHealthBar
     */
    public void setPlayerHealthBar(JProgressBar playerHealthBar) {
        this.playerHealthBar = playerHealthBar;
    }

    /**
     *
     * @param enemyHealthBar
     */
    public void setEnemyHealthBar(JProgressBar enemyHealthBar) {
        this.enemyHealthBar = enemyHealthBar;
    }

    /**
     *
     * @param endFightDialog
     */
    public void setEndFightDialog(JDialog endFightDialog) {
        this.endFightDialog = endFightDialog;
    }

    /**
     *
     * @param endGameDialog
     */
    public void setEndGameDialog(JDialog endGameDialog) {
        this.endGameDialog = endGameDialog;
    }

    /**
     *
     * @param endGameWithoutLadderDialog
     */
    public void setEndGameWithoutLadderDialog(JDialog endGameWithoutLadderDialog) {
        this.endGameWithoutLadderDialog = endGameWithoutLadderDialog;
    }

    /**
     *
     * @param cantUseItemDialog
     */
    public void setCantUseItemDialog(JDialog cantUseItemDialog) {
        this.cantUseItemDialog = cantUseItemDialog;
    }

    /**
     *
     * @param fightFrame
     */
    public void setFightFrame(JFrame fightFrame) {
        this.fightFrame = fightFrame;
    }

    /**
     *
     * @param firstItemButton
     */
    public void setFirstItemButton(JRadioButton firstItemButton) {
        this.firstItemButton = firstItemButton;
    }

    /**
     *
     * @param secondItemButton
     */
    public void setSecondItemButton(JRadioButton secondItemButton) {
        this.secondItemButton = secondItemButton;
    }

    /**
     *
     * @param thirdItemButton
     */
    public void setThirdItemButton(JRadioButton thirdItemButton) {
        this.thirdItemButton = thirdItemButton;
    }
}
