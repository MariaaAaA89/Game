/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import Components.GameCharacter;
import java.net.URL;

import java.util.ArrayList;

/**
 *
 * @author maria
 */
/**
 * Класс для управления игровыми локациями и противниками
 * 
 * Основные функции:
 * Генерация случайных противников для локации
 * Управление прогрессом прохождения локаций
 * Сброс состояния локаций
 * 
 */
public class GameLevel {
    public static final int MAX_ENEMIES = 4;
    public static final int DEFAULT_ENEMY_INDEX = 4;
    
    private int currentLocation = 1;
    private int currentEnemyNumber = 0;
    private ArrayList<GameCharacter> currentEnemiesList = new ArrayList<>();
    private GameCharacter[] fullEnemiesList = null;
    private int locationSize;

    /**
     *
     * @param list
     */
    public void setFullEnemiesList(GameCharacter[] list) {
        fullEnemiesList = list;
    }

    /**
     *
     * @return
     */
    public ArrayList<GameCharacter> getCurrentEnemies() {
        return currentEnemiesList;
    }

    /**
     *
     * @param maxEnemies
     */
    public void setCurrentEnemies(int maxEnemies) {
        currentEnemiesList = new ArrayList<>();
        locationSize = 1 + (int) (Math.random() * maxEnemies);
        for (int i = 0; i < locationSize; i++) {
            GameCharacter enemy = getRandomEnemy();
            currentEnemiesList.add(enemy);
        }
    }   

    /**
     * Генерирует рандомного противника
     * @return экземпляр GameCharacter с настроенными параметрами
     */
    private GameCharacter getRandomEnemy() {
        int enemyIndex = (int) (Math.random() * MAX_ENEMIES);
        GameCharacter enemy = fullEnemiesList[enemyIndex];
        setEnemyPhoto(enemy);
        return enemy;
    }

    private void setEnemyPhoto(GameCharacter enemy) {
        try {
            // Формируем имя файла: lowercase без дефисов + .jpg
            String imageName = enemy.getStringName().toLowerCase().replace("-", "") + ".jpg";
            // Путь внутри JAR-файла или ресурсов
            String resourcePath = "/images/" + imageName;
            URL imgURL = getClass().getResource(resourcePath);
            
            if (imgURL != null) {
                enemy.setPhoto(imgURL); 
            } else {
                System.err.println("Image not found: " + resourcePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param isNextLocation
     * @param maxEnemies
     */
    public void resetLocation(boolean isNextLocation, int maxEnemies) {
        if (isNextLocation) {
            currentLocation += 1;
            currentEnemyNumber = 0;
            setCurrentEnemies(maxEnemies);
        } else {
            currentLocation = 1;
            currentEnemyNumber = 0;
            setCurrentEnemies(0);
        }
    }

    /**
     *
     * @return
     */
    public int getCurrentLocation() {
        return currentLocation;
    }

    /**
     *
     * @return
     */
    public int getCurrentEnemyNumber() {
        return currentEnemyNumber;
    }

     /**
     * Возвращает следующего противника для текущей локации.
     * 
     * @return экземпляр GameCharacter с настроенными параметрами
     * @see EnemyFabric
     */
    public GameCharacter getCurrentEnemy() {
        GameCharacter enemy;
        if (currentEnemyNumber != locationSize) {
            currentEnemyNumber += 1;
            return currentEnemiesList.get(currentEnemyNumber - 1);
        } else {
            currentEnemyNumber = 0;
            enemy = fullEnemiesList[DEFAULT_ENEMY_INDEX];
            setEnemyPhoto(enemy);
            return enemy;
        }
    }
}