package com.AWT;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class Surface extends JPanel implements ActionListener {

    private final Color colors[] = { // массив цветов
            Color.blue, Color.cyan, Color.green,
            Color.magenta, Color.orange, Color.pink,
            Color.red, Color.yellow, Color.lightGray, Color.white
    };

    // переменные
    private Ellipse2D.Float[] ellipses;  // массив кругов
    private double esize[];
    private float estroke[];
    private double maxSize = 0;
    private final int NUMBER_OF_ELLIPSES = 25;
    private final int DELAY = 30;
    private final int INITIAL_DELAY = 150;
    private Timer timer; // счётчик

    public Surface() {// конструктор
        // инициализация
        initSurface();
        initEllipses();
        initTimer();
    }

    private void initSurface() { // инициализация компонента

        setBackground(Color.black); // цвет заливки
        ellipses = new Ellipse2D.Float[NUMBER_OF_ELLIPSES]; // создаём круг
        esize = new double[ellipses.length]; // размеры
        estroke = new float[ellipses.length];
    }

    private void initEllipses() { // инициализация круга

        int w = 350; // значение по масштабу
        int h = 250;

        maxSize = w / 10; //максимальный размер круга

        for (int i = 0; i < ellipses.length; i++) { // цикл, по массиву

            ellipses[i] = new Ellipse2D.Float(); // создаем новый круг
            posRandEllipses(i, maxSize * Math.random(), w, h); // передаём наши данные в функцию posRandEllipses
        }
    }

    private void initTimer() { // инициализация таймера

        timer = new Timer(DELAY, this); // создание
        timer.setInitialDelay(INITIAL_DELAY); // постоянный
        timer.start(); // запуск
    }

    private void posRandEllipses(int i, double size, int w, int h) {
        esize[i] = size;// размер
        estroke[i] = 1.0f;
        double x = Math.random() * (w - (maxSize / 2));
        double y = Math.random() * (h - (maxSize / 2));
        ellipses[i].setFrame(x, y, size, size); // устанавливаем ему наши размеры
    }

    private void doStep(int w, int h) {

        for (int i = 0; i < ellipses.length; i++) { // цикл

            estroke[i] += 0.025f; // сдвиг
            esize[i]++; // размер

            if (esize[i] > maxSize) { // если размер меньше максимального
                // уменьшить до 1
                posRandEllipses(i, 1, w, h);
            } else {
                // а инчаче выполнить действие по установке нового размера
                ellipses[i].setFrame(ellipses[i].getX(), ellipses[i].getY(),
                        esize[i], esize[i]);
            }
        }
    }

    private void drawEllipses(Graphics2D g2d) {

        for (int i = 0; i < ellipses.length; i++) { // запуск цикла

            g2d.setColor(colors[i % colors.length]); // выбор цвета
            g2d.setStroke(new BasicStroke(estroke[i])); // место
            g2d.draw(ellipses[i]); // прорисовка
        }
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create(); // создать нашу графику

        RenderingHints rh
                = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON); // рендеринг

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh); // установка рендеринга

        Dimension size = getSize(); // получение размеров
        doStep(size.width, size.height); // вызов функции doStep
        drawEllipses(g2d); // прорисовка

        g2d.dispose(); // забыть позиции
    }

    @Override
    public void paintComponent(Graphics g) { // прорисовка компонента

        super.paintComponent(g);
        doDrawing(g); // наша функция рисования
    }

    @Override
    public void actionPerformed(ActionEvent e) { // активация
        repaint(); // перерисовать
    }
}

public class BubblesEx extends JFrame {

    public BubblesEx() { // конструктор
        // инициализация
        initUI();
    }

    private void initUI() {

        add(new Surface()); // добавление эл-та на форму

        setTitle("Bubbles");
        setSize(350, 250); // размеры
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // выход
    }

    public static void main(String[] args) { // главный класс

        EventQueue.invokeLater(new Runnable() { // поток
            @Override
            public void run() { // запуск

                BubblesEx ex = new BubblesEx(); // создание нашего класса
                ex.setVisible(true); // видимость
            }
        });
    }
}

