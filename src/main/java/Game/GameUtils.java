/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import Components.EnemyFactory;
import Components.Player;
import Components.InventoryItem;
import Components.GameCharacter;
import Components.GameResults;
import static Components.CharacterType.*;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * Вспомогательный класс для управления основными игровыми процессами
 * Обеспечивает взаимодействие между игровой логикой, данными и графическим интерфейсом
 * 
 * Основные функции:
 * Инициализация и управление списком противников
 * Работа с таблицей рекордов
 * Создание нового игрока
 * Обработка завершения игры
 * Взаимодействие с Excel для сохранения результатов
 * 
 * @see Fight
 * @see GameResults
 * @see Controller
 * 
 * @author maria
 */
public class GameUtils {
    /**
     * Экземпляр логики игры для управления адаптивной системой поведения и механиками
     */
    public GameLogic action = new GameLogic();
    /**
     * Контроллер боевой системы
     */
    public Fight fight = new Fight();
    /**
     * Список для хранения результатов игровых сессий
     */
    private final ArrayList<GameResults> gameResults = new ArrayList<>();
    /**
     * Массив противников, доступных в игре
     */
    private final GameCharacter[] enemies = new GameCharacter[5];
    
    /**
     * Фабрика для создания противников
     */
    EnemyFactory fabric = new EnemyFactory();

    /**
     * Инициализирует список противников, создавая экземпляры персонажей
     */
    public void setEnemies() {
        enemies[0] = fabric.create(BARAKA);
        enemies[1] = fabric.create(SUB_ZERO);
        enemies[2] = fabric.create(LIU_KANG);
        enemies[3] = fabric.create(SONYA_BLADE);
        enemies[4] = fabric.create(SHAO_KAHN);
    }

    /**
     *
     * @return
     */
    public GameCharacter[] getEnemies() {
        return this.enemies;
    }

    /**
     * Создает нового игрока с начальными параметрами
     * 
     * @param controller экземпляр посредника для представления
     * @param items начальный инвентарь игрока
     * @return новый экземпляр игрока
     */
    public Player newHuman(Controller controller, InventoryItem[] items) {
        Player player = new Player(0, 80, 16, You);
        controller.setHealthBar(player);
        controller.setPlayerMaxHealthBar(player);
        player.setItems(items);
        return player;
    }

    /**
     * Обрабатывает завершение игры и обновляет таблицу рекордов
     * 
     * @param player текущий игрок
     * @param text поле ввода имени игрока
     * @param table таблица для отображения результатов
     * @throws IOException при ошибках записи в файл
     */
    public void endGameTop(Player player, JTextField text, JTable table) throws IOException {
        gameResults.add(new GameResults(text.getText(), player.getPoints()));
        gameResults.sort(Comparator.comparing(GameResults::getPoints).reversed());
        writeToTable(table);
        writeToExcel();
    }

    /**
     * Записывает результаты в Excel-файл
     * 
     * @throws IOException при ошибках ввода/вывода
     * @see <a href="https://poi.apache.org/">Apache POI documentation</a>
     */
    public void writeToExcel() throws IOException {       
        File externalFile = new File("Results.xlsx");
    
        try (XSSFWorkbook book = new XSSFWorkbook()) {
            XSSFSheet sheet = book.createSheet("Результаты ТОП 10");
            XSSFRow r = sheet.createRow(0);
            r.createCell(0).setCellValue("№");
            r.createCell(1).setCellValue("Имя");
            r.createCell(2).setCellValue("Количество баллов");

            for (int i = 0; i < gameResults.size() && i < 10; i++) {
                XSSFRow r2 = sheet.createRow(i + 1);
                r2.createCell(0).setCellValue(i + 1);
                r2.createCell(1).setCellValue(gameResults.get(i).getName());
                r2.createCell(2).setCellValue(gameResults.get(i).getPoints());
            }
        
        try (FileOutputStream out = new FileOutputStream(externalFile)) {
            book.write(out);
        }
    }
    }

    /**
     * Возвращает список игровых результатов
     * 
     * @return неизменяемый список объектов GameResults
     */
    public ArrayList<GameResults> getResults() {
        return this.gameResults;
    }

    /**
     * Читает результаты из Excel-файла
     * 
     * @throws IOException при ошибках чтения файла
     */
    public void readFromExcel() throws IOException {
         // Путь к файлу во внешней директории
        File externalFile = new File("Results.xlsx");

        // Если файл существует вне JAR - читаем его
        if (externalFile.exists()) {
            try (XSSFWorkbook book = new XSSFWorkbook(externalFile)) {
                readDataFromWorkbook(book);
            }  catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(GameUtils.class.getName()).log(
                Level.SEVERE, 
                "Ошибка чтения внешнего файла Results.xlsx", 
                ex
            );
            }
        } 
        // Если нет - копируем из ресурсов и читаем
        else {
            try (InputStream is = getClass().getResourceAsStream("/Results.xlsx");
                 XSSFWorkbook book = new XSSFWorkbook(is)) {

                // Копируем файл из ресурсов в рабочую директорию
                try (FileOutputStream out = new FileOutputStream(externalFile)) {
                    book.write(out);
                }

                readDataFromWorkbook(book);
            }
         catch (IOException ex) {
        Logger.getLogger(GameUtils.class.getName()).log(
            Level.SEVERE, 
            "Ошибка копирования файла из ресурсов", 
            ex
            );
        }
        }
    }
    
    private void readDataFromWorkbook(XSSFWorkbook book) {
    XSSFSheet sh = book.getSheetAt(0);
    for (int i = 1; i <= sh.getLastRowNum(); i++) {
        XSSFRow row = sh.getRow(i);
        if (row != null) {
            String name = row.getCell(1).getStringCellValue();
            int points = (int) row.getCell(2).getNumericCellValue();
            gameResults.add(new GameResults(name, points));
        }
    }
}

    /**
     * Обновляет таблицу результатов в графическом интерфейсе
     * 
     * @param table целевая JTable для отображения данных
     */
    public void writeToTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < gameResults.size(); i++) {
            if (i < 10) {
                model.setValueAt(gameResults.get(i).getName(), i, 0);
                model.setValueAt(gameResults.get(i).getPoints(), i, 1);
            }
        }
    }
}
