/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

/**
 * Класс игрока, расширяющий базовый функционал GameCharacter
 * 
 * Добавляет систему:
 * Очков опыта
 * Инвентаря
 * Прогресса прокачки
 * 
 * @see GameCharacter
 * 
 * @author maria
 */
public class Player extends GameCharacter {

    private int points;
    private int experience;
    private int nextExperience;
    private InventoryItem[] items;

    /**
     * Инициализирует нового игрока
     * 
     * @param level начальный уровень
     * @param health начальное здоровье
     * @param damage базовый урон
     * @param name имя персонажа 
     */
    public Player(int level, int health, int damage, CharacterType name) {
        super(level, health, damage, name);
        this.points = 0;
        this.experience = 0;
        this.nextExperience = 40;
    }
    
    /**
     *
     * @param items
     */
    public void setItems(InventoryItem[] items){
        this.items = items;
    }

    /**
     *
     * @return
     */
    public InventoryItem[] getItems(){
        return this.items;
    }

    /**
     *
     * @return
     */
    public int getPoints() {
        return this.points;
    }

    /**
     *
     * @return
     */
    public int getExperience() {
        return this.experience;
    }

    /**
     *
     * @return
     */
    public int getNextExperience() {
        return this.nextExperience;
    }
    
    /**
     *
     */
    public void resetPoints() {
        this.points = 0;
    }
    
    /**
     *
     * @param points
     */
    public void setPoints(int points) {
        this.points += points;
    }

    /**
     *
     */
    public void resetExperience() {
        this.experience = 0;
    }

    /**
     *
     * @param experience
     */
    public void setExperience(int experience) {
        this.experience += experience;
    }

    /**
     *
     * @param nextExperience
     */
    public void setNextExperience(int nextExperience) {
        this.nextExperience = nextExperience;
    }
}

