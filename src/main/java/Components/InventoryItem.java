/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

/**
 * Класс для представления игровых предметов
 * @author maria
 */
public class InventoryItem {  
    private final String name;
    private int count;
    
    /**
     * Создает предмет с указанными параметрами
     * 
     * @param name название предмета
     * @param count начальное количество
     */
    public InventoryItem(String name, int count){
        this.name = name;
        this.count = count;
    }

    /**
     *
     * @param count
     */
    public void setCount(int count){
        this.count += count;
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
    public int getCount(){
        return this.count;
    }
}
