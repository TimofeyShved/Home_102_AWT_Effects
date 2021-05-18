package com.AWT;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class SurfaceStarDemo extends JPanel implements ActionListener {

    // точки

    private final int points[][] = {
            {0, 85}, {75, 75}, {100, 10}, {125, 75},
            {200, 85}, {150, 125}, {160, 190}, {100, 150},
            {40, 190}, {50, 125}, {0, 85}
    };

    // переменные
    private Timer timer;
    private double angle = 0;
    private double scale = 1;
    private double delta = 0.01;

    private final int DELAY = 10;

    public SurfaceStarDemo() { // конструктор

        initTimer();
    }

    private void initTimer() { // инициализация таймера

        timer = new Timer(DELAY, this); // создание таймера
        timer.start(); // запуск
    }

    private void doDrawing(Graphics g) { // наща прорисовка

        int h = getHeight(); // размеры формы
        int w = getWidth();

        Graphics2D g2d = (Graphics2D) g.create(); // создание графики

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON); // рендеринг

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.translate(w / 2, h / 2); // перемешение в центр
        GeneralPath star = new GeneralPath(); // звезда
        star.moveTo(points[0][0], points[0][1]); // начальные точки

        for (int k = 1; k < points.length; k++) { // цикл

            star.lineTo(points[k][0], points[k][1]); // нарисовать звезду
        }

        g2d.rotate(angle); // поворот
        g2d.scale(scale, scale); // изменение размера
        g2d.fill(star); // прорисовка звезды на форме

        g2d.dispose(); // забыть позицию
    }

    @Override
    public void paintComponent(Graphics g) { // прорисовка компонента

        super.paintComponent(g);
        doDrawing(g); // наша прорисовка
    }

    private void step() { // шаг

        if (scale < 0.01) { // если размер больше или меньше указанного, то отнимать

            delta = -delta;
        } else if (scale > 0.99) {

            delta = -delta;
        }

        scale += delta;
        angle += 0.01;
    }

    @Override
    public void actionPerformed(ActionEvent e) { // активность

        step(); // шаг
        repaint(); // перисовать
    }
}

public class StarDemoEx extends JFrame {

    public StarDemoEx() { // конструктор

        initUI();
    }

    private void initUI() { // инициализация

        add(new SurfaceStarDemo()); // добавить наш компонент

        setTitle("Star");
        setSize(420, 250); // размеры
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // выход
    }

    public static void main(String[] args) { // главный класс

        EventQueue.invokeLater(new Runnable() { // поток
            @Override
            public void run() { // запуск

                StarDemoEx ex = new StarDemoEx(); // наш класс
                ex.setVisible(true); // видимость
            }
        });
    }
}
