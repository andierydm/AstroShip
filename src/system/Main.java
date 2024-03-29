package system;

import object.GameObject;
import object.GameObjectType;
import object.Ship;
import system.collision.CollisionShape;
import system.input.AnotherKeyboardInput;
import system.input.KeyboardInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class Main extends JFrame implements Runnable {
    private byte cfpd, fps;
    private int caps, aps, runTime;
    private long preStart;
    private long timer;
    private Thread gamethred;
    private volatile boolean running = false;
    private final Canvas canvas = new Canvas();
    private final GameConducting conducting = new GameConducting();
    private final GameWorld world = new GameWorld(conducting);
    private final KeyboardInput keyboardInput = new KeyboardInput();
    private final String GAME_THREAD_IDENTIFIER = "GameThread";
    private Image ship = null;
    private SystemTimer time;
    private CameraSystem cs;

    public Main() {
        initResources();
        initComponent();
        canvas.requestFocus();

    }

    private void initListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeGame();
            }
        });

        canvas.addKeyListener(keyboardInput);

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                AnotherKeyboardInput.getInstance().keyPressed(e);

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    closeGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                AnotherKeyboardInput.getInstance().keyReleased(e);
            }
        });
    }

    private void closeGame() {
        if (JOptionPane.showConfirmDialog(
                null,
                "Do you want to close the game?",
                "Close Game",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION
        ) {
            stopGameThread();
            System.exit(0);
        }
    }

    private void createLayout() {
        final GroupLayout gl = new GroupLayout(getContentPane());
        getContentPane().setLayout(gl);

        gl.setHorizontalGroup(gl.createParallelGroup().addComponent(canvas));
        gl.setVerticalGroup(gl.createSequentialGroup().addComponent(canvas));
    }

    private void initComponent() {
        preStart = System.nanoTime();
        timer = System.nanoTime();

        if (GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadlessInstance()) {
            System.err.println("Could not init game in a headless mode");
            System.exit(-1);
        }

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int windowWidth = (int) (screenSize.width * 0.80);
        final int windowHeight = (int) (screenSize.height * 0.80);

        canvas.setMinimumSize(new Dimension(windowWidth, windowHeight));
        canvas.setPreferredSize(new Dimension(windowWidth, windowHeight));
        canvas.setMaximumSize(new Dimension(windowWidth, windowHeight));

        Constants.WIDTH = windowWidth;
        Constants.HEIGHT = windowHeight;

        cs = new CameraSystem(new Point2D.Float(0f,0f));

        time = new SystemTimer();

        initListeners();
        createLayout();

        pack();

        canvas.createBufferStrategy(2);
        startGameThread();

        canvas.setBackground(Color.BLACK);

        fps = 0;
        aps = 0;
        cfpd = 0;
        caps = 0;
        runTime = 0;
        world.createObjects();

        setTitle("AstroShip - %dx%d".formatted(windowWidth, windowHeight));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    private void initResources() {
        ResourceManager.loadResource()
                .loadImage("ship.png", "ship", information -> ResourceManager.resizeImage(information.getImage(), 100))
                .finish();

        if (!ResourceManager.loadImages()) {
            JOptionPane.showConfirmDialog(null, "Could not localize one of the resources");
            System.exit(-1);
        }
    }

    public void startGameThread() {
        if (running) return;

        System.out.println("Game is starting...");

        gamethred = new Thread(this, GAME_THREAD_IDENTIFIER);
        gamethred.start();
    }

    private synchronized void stopGameThread() {
        if (!running) return;

        System.out.println("Game is being stopping...");

        try {
            running = false;
            gamethred.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Game stopped!");
    }

    private void update(int dt) {
        AnotherKeyboardInput.getInstance().poll();
        keyboardInput.refresh();
        conducting.update(dt);
        List<GameObject> lo = conducting.getList();
        lo.forEach(o -> {
           if(o.getGameObjectType() == GameObjectType.Player){
                cs.update(dt, (Ship) o);
           }
        });
    }

    private void render() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g.clearRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
        g.setColor(Color.WHITE);
        //inicio de la camara-------------------------------------------------------------------------------------------
        g2d.translate(cs.getPosition().getX(), cs.getPosition().getY());

        CollisionShape colS = CollisionShape.createBoxShape(25, 25);
        colS.moveShape(0, 150);

        Polygon p = colS.toPolygon();

        g.drawPolygon(p);
        //start draw whatever------------------------------------------------------------------------------------------
        g.drawString("FPS: " + fps + ", APS: " + aps + ", Run time: " + formatRunTime(), 10, 15);
        conducting.render(g);
        //fin de la camara
        g2d.translate(-cs.getPosition().getX(), -cs.getPosition().getY());
        //end draw-----------------------------------------------------------------------------------------------------
        g.dispose();
        bs.show();
        cfpd++;
        //time.plusF();
        if(AnotherKeyboardInput.getInstance().keyDown(KeyEvent.VK_R)){
            if (JOptionPane.showConfirmDialog(
                    null,
                    "Do you want to reset the game?",
                    "Reset Game",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION
            ) {
                new Main().startGameThread();
            }

        }
    }

    private void render(Graphics graphics, double time) {
        //Cleaning the canvas
        graphics.setColor(Color.BLACK);
        graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //Drawing FPS and another information
        graphics.setColor(Color.WHITE);
        graphics.drawString("FPS: %d, APS: %d, Running Time: %d".formatted(fps, aps, ((int) time)), 10, 15);
        conducting.render(graphics);
    }

    private String formatRunTime() {
        String time = "0";
        if (runTime > 0 && runTime < 60) time = runTime + " s";
        else if (runTime / 60 > 0 && runTime / 60 < 60) {
            String min = String.valueOf(runTime / 60);
            String s = String.valueOf(runTime - runTime / 60 * 60);
            time = min + ":" + s + " min";
        }
        return time;
    }

    private void runGameLoop() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();

        if (bufferStrategy == null) {
            stopGameThread();
            return;
        }

        double t = 0.0;
        double dt = 1 / 60.0;

        double currentTime = System.currentTimeMillis() / 1000.0;

        while (running) {
            Graphics graphics = null;

            try {
                graphics = bufferStrategy.getDrawGraphics();

                double newTime = System.currentTimeMillis() / 1000.0;
                double frameTime = newTime - currentTime;
                currentTime = newTime;

                while (frameTime > 0.0) {
                    double deltaTime = Math.min(frameTime, dt);
                    update((int) deltaTime);
                    frameTime -= deltaTime;
                    t += deltaTime;
                }

                render(graphics, t);

                bufferStrategy.show();
            } finally {
                if (graphics != null) {
                    graphics.dispose();
                }
            }
        }
    }

    private void runMainGameLoop() {
        int seconds = 0;
        double delta = 0;

        while (running) {
            long start = System.nanoTime();
            delta += (start - preStart) / Constants.TIME;
            preStart = start;
            while (delta >= 1) {
                update(seconds);
                world.createEnemy(seconds);
                render();
                delta--;
            }
            caps++;
            if (System.nanoTime() - timer > Constants.NANOTIME) {
                timer += Constants.NANOTIME;
                seconds++;
                runTime = seconds;
                fps = cfpd;
                aps = caps;
                cfpd = 0;
                caps = 0;
            }
            //time.plusA();
            //time.systemShowTime(timer);
        }
    }

    @Override
    public void run() {
        System.out.println("Game started successfully!");

        running = true;

        runMainGameLoop();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Main::new);
    }
}