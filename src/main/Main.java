package main;

import input.KeyBoard;
import system.CreateWorld;
import system.GameConducting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class Main extends JFrame implements Runnable{
    private Thread loop;
    private boolean running;
    private Canvas canvas;
    private byte cfpd, fps;
    private int caps, aps, runTime;
    private CreateWorld world;
    private GameConducting conducting;
    private KeyBoard keyBoard;

    private long preStart;
    private long timer;

    public Main(){
        initComponent();
        this.setSize(Constantes.WIDTH, Constantes.HEIGHT);
        this.setLocationRelativeTo(null);

        closingCommand();

        canvas.requestFocus();
    }

    private void initComponent(){
        preStart = System.nanoTime();
        timer = System.nanoTime();

        canvas = new Canvas();
        canvas.setBackground(Color.BLACK);
        this.getContentPane().add(canvas, BorderLayout.CENTER);

        keyBoard = new KeyBoard();
        canvas.addKeyListener(keyBoard);

        running = false;

        fps = 0;
        aps = 0;
        cfpd = 0;
        caps = 0;
        runTime = 0;
        conducting = new GameConducting();
        world = new CreateWorld(conducting);
        world.createObjects();

        this.setResizable(true);
        this.setVisible(true);
        this.setTitle("AstroShip");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    public void start(){
        if (running) return;
        running = true;
        loop = new Thread(this, "main loop");
        loop.start();
    }

    private void stop(){
        try {
            running = false;
            loop.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void closingCommand(){
        this.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             *
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                stop();
            }
        });
    }

    private void update(int dt){
        keyBoard.refresh();
        conducting.update(dt);
        if(KeyBoard.exits){
            stop();
        }
    }

    private void draw(){
        BufferStrategy bs = canvas.getBufferStrategy();
        if(bs == null){
            canvas.createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0,0, Constantes.WIDTH, Constantes.HEIGHT);
        g.setColor(Color.WHITE);
        //start draw whatever------------------------------------------------------------------------------------------
        g.drawString("FPS: "+fps+", APS: "+ aps +", Run time: "+formatRunTime(), 10, 15);
        conducting.render(g);
        //end draw-----------------------------------------------------------------------------------------------------
        g.dispose();
        bs.show();
        cfpd++;
    }

    private String formatRunTime(){
        String time = "0";
        if(runTime > 0 && runTime < 60) time = runTime+" s";
        else if(runTime/60 > 0 && runTime/60 < 60){
            String min = String.valueOf(runTime/60);
            String s = String.valueOf(runTime-runTime/60*60);
            time = min+":"+s+" min";
        }
        return time;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        int seconds = 0;
        double delta = 0;
        //long start;
        while (running){
            long start = System.nanoTime();
            delta += (start - preStart) / Constantes.TIME;
            preStart = start;
            while (delta >= 1){
                update(seconds);
                draw();
                delta--;
            }
            caps++;
            if(System.nanoTime() - timer > Constantes.NANOTIME){
                timer += Constantes.NANOTIME;
                seconds++;
                runTime = seconds;
                fps = cfpd;
                aps = caps;
                cfpd = 0;
                caps = 0;
            }
        }
    }

    public static void main(String[] args) {
        new Main().start();
    }
}