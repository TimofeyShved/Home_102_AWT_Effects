package com.AWT;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


class SurfacePuffEx extends JPanel implements ActionListener {

    // переменные
    private Timer timer;
    private int x = 1;
    private float alpha = 1;
    private final int DELAY = 15;
    private final int INITIAL_DELAY = 200;

    public SurfacePuffEx() {
            // конструктор
        initTimer();
    }

    private void initTimer() {
            // таймер
        timer = new Timer(DELAY, this); // создание таймера
        timer.setInitialDelay(INITIAL_DELAY); // постоянство
        timer.start(); // запуск
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();// создать нашу графику

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON); // рендеринг

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh); // установка рендоринга

        Font font = new Font("Dialog", Font.PLAIN, x); // шрифт
        g2d.setFont(font); // установка

        FontMetrics fm = g2d.getFontMetrics();
        String s = "ZetCode"; // слово
        Dimension size = getSize(); // размер

        int w = (int) size.getWidth(); // размер формы
        int h = (int) size.getHeight();

        int stringWidth = fm.stringWidth(s); // размер строки

        AlphaComposite ac = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(ac);

        g2d.drawString(s, (w - stringWidth) / 2, h / 2); // прорисовка

        g2d.dispose();
    }

    @Override
    public void paintComponent(Graphics g) { // прорисовка компонента

        super.paintComponent(g);
        doDrawing(g); // наша прорисовка
    }

    private void step() {// шаг

        x += 1;

        if (x > 40)
            alpha -= 0.01;

        if (alpha <= 0.01)
            timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) { // активность

        step(); // шаг
        repaint(); // перисовать
    }
}

public class PuffEx extends JFrame {

    public PuffEx() { // конструктор

        initUI();
    }

    private void initUI() { // инициализация

        add(new SurfacePuffEx());// добавить наш компонент

        setTitle("Puff");

        setSize(400, 300);// размеры
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // выход
    }

    public static void main(String[] args) { // главный класс

        EventQueue.invokeLater(new Runnable() { // поток
            @Override
            public void run() { // запуск

                PuffEx ex = new PuffEx();// наш класс
                ex.setVisible(true); // видимость
            }
        });
    }
}
