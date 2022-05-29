package lesson4.homework;

import java.util.Random;
import java.util.Scanner;

public class Exercise1 {

    static final Scanner scanner = new Scanner(System.in); // Вспомогательный класс для ввода данных
    static final Random random = new Random(); // Вспомогательный класс для генерации случайных чисел

    static final char DOT_HUMAN = 'Ӿ'; // Фишка игрока - человек
    static final char DOT_AI = 'Ө'; // Фишка игрока - компьютер
    static final char DOT_EMPTY  = '·'; // Пустое поле
    static char[][] field; // Двумерный массив, хранит текущее состояние игрового поля

    static int fieldSizeX; // Размер игрового поля по Х
    static int fieldSizeY; // Размер игрового поля по Y

    /**
     * Инициализация объектов игры
     */
    private static void initialize(){
        // Установка размера игрового поля
        fieldSizeX = 5;
        fieldSizeY = 5;
        field = new char[fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++){
                // Инициализация элементов массива DOT_EMPTY
                field[x][y] = DOT_EMPTY;

            }
        }
    }

    /**
     * Отрисовка игрового поля
     */
    static void printField(){

        System.out.print("+");
        for(int i = 0; i < fieldSizeX*2 + 1; i++){
            System.out.print( i % 2 == 0 ? " - " : i / 2 + 1 ); // Тернарная операция
        }
        System.out.println();

        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print( x + 1 + " | ");
            for (int y = 0; y < fieldSizeY; y++){
                System.out.print(field[x][y] + " | ");
            }
            System.out.println();
        }

        for(int i = 0; i <= fieldSizeX*2; i++) {
            System.out.print("__");
        }
        System.out.println();
    }
// char c = i % 2 == 0 ? '-' : (char) (i / 2 + 1); // Пример тернарной операции

    /**
     * Обработка хода игрока (человек)
     */
    static void humanTurn(){
        int x, y;

        do {
            System.out.println(" Введите координаты хода X и Y\n(от 1 до 5) через пробел >>> ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!(isCellValid(x, y) && isCellEmpty(x, y)));
            field[x][y] = DOT_HUMAN;
    }

    /**
     * Обработка хода игрока (компьютер)
     */
    static void aiTurn(){
        int x, y;
        do{
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        } while (!(isCellEmpty(x,y)));
            field[x][y] = DOT_AI;

    }

    /**
     * Проверка, ячейка является пустой (DOT_EMPTY)
     */
    static boolean isCellEmpty(int x, int y){
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка корректности хода (координаты хода не должны превышать размер игрового поля(массива))
     */
    static boolean isCellValid(int x, int y){
        return x >= 0 && x < fieldSizeX &&  y >=  0 && y < fieldSizeY;
    }

    static boolean checkWin(char c){
        for (int i = 0; i < 4; i++)
            if ((field[i][0] == c && field[i][1] == c && field[i][2] == c && field[i][3] == c) || (field[0][i] == c && field[1][i] == c && field[2][i] == c  && field[3][i] == c)) return true;
        if ((field[0][0] == c && field[1][1] == c && field[2][2] == c && field[3][3] == c) || (field[3][0] == c && field[2][1] == c && field[1][2] == c && field[0][1] == c)) return true;
        return false;
    }


    /**
     * Метод проверки состояния игры
     * @param dot - игровая фишка
     * @param s - победный слоган
     */
    static boolean gameChecks(char dot, String s) {
        if (checkWin(dot)){
            System.out.println(s);
            return true; // Завершение игры
        }
        if (checkDraw())
        {
            System.out.println("Ничья");
            return true; // Завершение игры
        }
        // Продолжаем игру
        return false;
    }

    /**
     * Проверка на ничью (если поле заполнено фишками игрока или компьютера)
     * @return
     */
    static boolean checkDraw(){
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field.length; y++){
                if(isCellEmpty(x,y)) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        while(true) {
            initialize();
            printField();
            while (true) {
                humanTurn(); // Обработка хода игрока (человек)
                printField();
                if (gameChecks(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn(); // Обработка хода игрока (компьютер)
                printField();
                if (gameChecks(DOT_AI, "Победил компьютер!!"))
                    break;
            }
            System.out.print("Повторить игру?: Y/N : ");
            if(!scanner.next().equalsIgnoreCase("Y"))
                break;
        }
    }
}