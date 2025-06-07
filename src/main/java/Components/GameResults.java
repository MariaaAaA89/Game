/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

/**
 * Хранит результаты игровой сессии
 * @author maria
 */
public class GameResults {
    private String name;
    private int points;
    
    /**
     * Создает запись результата.
     * 
     * @param name имя игрока
     * @param points количество очков
     */
    public GameResults(String name, int points){
        this.name = name;
        this.points = points;
    }

    /**
     *
     * @return
     */
    public String getName(){
        return this.name;
    }
    
    /**
     *
     * @return
     */
    public int getPoints(){
        return this.points;
    }   
}

