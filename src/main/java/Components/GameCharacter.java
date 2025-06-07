/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Базовый класс для всех игровых персонажей, содержащий основные характеристики
 * и методы управления состоянием персонажа
 * 
 * Хранит информацию о:
 * Уровне персонажа
 * Здоровье и максимальном здоровье
 * Наносимом уроне
 * Состоянии дебаффа
 * Графическом представлении
 * 
 * @author maria
 */
public class GameCharacter {
    /**
     * Текущий уровень персонажа. Влияет на силу атак и здоровье
     */
    public ImageIcon icon;
    
    private int level;
    private int health;
    private int maxHealth;
    private int damage;
    private int turnsWithDebuff;
    private final CharacterType name;

    /**
     * Создает нового персонажа с заданными параметрами
     * @param level начальный уровень
     * @param health начальное здоровье
     * @param damage базовый урон
     * @param name имя персонажа из перечисления CharacterName
     */
    public GameCharacter(int level, int health, int damage, CharacterType name) {
        this.level = level;
        this.health = health;
        this.damage = damage;
        this.maxHealth = health;
        this.name=name;
    }

    /**
     * Проверяет наличие активного дебаффа
     * @return true если дебафф активен, иначе false
     */
    public boolean isDebuffed() {
        return turnsWithDebuff > 0;
    }

    /**
     *
     */
    public void loseDebuffTurn() {
        if (turnsWithDebuff > 0) {
            turnsWithDebuff--;
        }
    }

    /**
     *
     * @param turns
     */
    public void setDebuffTurns(int turns) {
        turnsWithDebuff = turns;
    }

    /**
     *
     * @return
     */
    public int getDebuffTurns() {
        return turnsWithDebuff;
    }

    /**
     *
     * @param path
     */
    public void setPhoto(String path) {
        icon = new ImageIcon(path);
    }
    
    public void setPhoto(URL imageUrl) {
        if (imageUrl != null) {
            this.icon = new ImageIcon(imageUrl);
        } else {
            System.err.println("Image URL is null");
        }
    }

    /**
     *
     * @return
     */
    public ImageIcon getPhoto() {
        return icon;
    }

    /**
     *
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     *
     */
    public void addLevel() {
        this.level++;
    }

    /**
     *
     * @param health
     */
    public void addHealth(int health) {
        this.health += health;
    }

    /**
     *
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     *
     * @param damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     *
     * @param damage
     */
    public void addDamage(int damage) {
        this.damage += damage;
    }

    /**
     *
     * @param maxHealth
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     *
     * @param maxHealth
     */
    public void addMaxHealth(int maxHealth) {
        this.maxHealth += maxHealth;
    }

    /**
     *
     * @return
     */
    public int getLevel() {
        return this.level;
    }

    /**
     *
     * @return
     */
    public int getHealth() {
        return this.health;
    }

    /**
     *
     * @return
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     *
     * @return
     */
    public int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     *
     * @return
     */
    public CharacterType getName() {
        return name;
    }
    
    /**
     *
     * @return
     */
    public String getStringName(){
       return name.getString();
    }
}