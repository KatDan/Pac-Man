package cz.cuni.mff.dancejova.game;

import cz.cuni.mff.dancejova.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

/**
 * The main class
 */
public class Game extends Canvas implements Runnable, KeyListener {

    final static int WIDTH = 640, HEIGHT = 480;
    final static String CHARACTERICONSOURCE = "/postavy.png";
    final static int MAINLABELHEIGHT = 100;
    final static int MAINLABELWIDTH = 300;
    final static int SCOREWIDTH = 150;
    final static int SCOREHEIGHT = Level.BLOCKSIZE;
    final static Color BACKGROUNDCOLOR = new Color(10,8,25);
    final static String TITLE = "Pac-Man";
    final static int OMNOMFREQUENCY = 5000;

    /**
     * Creates all the key components of the game
     */
    public Game(){
        Dimension dimension = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);

        //inicializacia hlavnych objektov
        addKeyListener(this);
        characters = new Images(CHARACTERICONSOURCE);
        maze = new Graph();
        player = new Player(WIDTH/2, WIDTH/2);
        level = new Level();

        //nastavenie hlavneho labelu
        main_label = new JLabel("PAUSED",JLabel.CENTER);
        main_label.setOpaque(true);
        main_label.setBounds((WIDTH - MAINLABELWIDTH)/2, (HEIGHT-MAINLABELHEIGHT)/2, MAINLABELWIDTH,MAINLABELHEIGHT);
        main_label.setForeground(Color.white);
        main_label.setBackground(Block.BLOCKCOLOR);

        //nastavenie skore
        score_label = new JLabel("Your score: 0", JLabel.CENTER);
        score_label.setOpaque(true);
        score_label.setBounds(0,0,SCOREWIDTH,SCOREHEIGHT);
        score_label.setForeground(Color.white);
        score_label.setBackground(Block.BLOCKCOLOR);
    }

    /**
     * The Game object for managing all the important methods
     */
    public static Game game;
    /**
     * The player object
     */
    public static Player player;
    /**
     * The level object
     */
    public static Level level;
    /**
     * The Graph object
     */
    public static Graph maze;

    /**
     * A label informing player about current state of the game shown in the middle of the screen
     */
    public static JLabel main_label;
    
    /**
     * A label showing the player's current score
     */
    public static JLabel score_label;

    /**
     * true if the game is paused, false otherwise
     */
    public static boolean isPaused = true;

    /**
     * true if the game is running, false otherwise
     */
    public static boolean isRunning = true;

    static Images characters;

    /**
     * Makes a player's and the ghosts' move
     */
    public void tick(){
        if(isRunning){
            player.tick();
            level.tick();
            score_label.setText("Your score: "+ player.score);
        }
    }

    /**
     * Renders Pac-Man's and the ghosts' move
     */
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(BACKGROUNDCOLOR);
        g.fillRect(0,0, WIDTH, HEIGHT);

        //update pozicii
        level.render(g);
        player.render(g);
        score_label.setText("Your score: "+ player.score);
        score_label.updateUI();

        g.dispose();
        bs.show();
    }

    int i = 0;

    /**
     * Manages the ticks and its rendering
     */
    public void run() {
        //vyrenderovanie zaciatku hry
        render();
        render();

        //nastavenie rychlosti animacie
        double timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        double delta = 0;
        double targetTick = 60.0;
        double ns = 1000000000 / targetTick;

        while (isRunning) {
            Game.game.requestFocus();
            i++;
            if(!isPaused) {
                main_label.setVisible(false);
                long now = System.nanoTime();
                if((now - lastTime)/ns > 10) lastTime = now - 1;
                delta += (now - lastTime) / ns;
                lastTime = now;
                while (delta >= 1) {
                    tick();
                    render();
                    delta--;
                }
                //otvaranie a zatvaranie pacmanovych ust
                if (i % OMNOMFREQUENCY == 0)
                    player.om_nom_nom_switch = !player.om_nom_nom_switch;

                if (System.currentTimeMillis() - timer >= 1000) {
                    timer += 1000;
                }
            }
            else render();
        }
    }

    /**
     * Sets the frame and its components
     * @param args
     */
    public static void main(String[] args){
        game = new Game();

        //inicializacia frameu a jeho sucasti
        JFrame frame = new JFrame();
        frame.setTitle(Game.TITLE);
        frame.add(score_label);
        frame.add(main_label);
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        main_label.setText("press space to start");
        main_label.setVisible(true);
        game.requestFocus();
        game.run();
    }

    /**
     * Updates the map to its initial state
     */
    public static void new_game(){
        //vygeneruje level odznova
        isPaused = true;
        isRunning = true;
        level = new Level();
        game.render();
        Player.score = 0;
        score_label.setText("Your score: "+ Player.score);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    /**
     * Manages inputs from the keyboard
     */
    public void keyPressed(KeyEvent e) {
        player.om_nom_nom_switch = true;
        switch (e.getKeyCode()){
            case KeyEvent.VK_RIGHT:
                player.direction = Player.Direction.right;
                break;
            case KeyEvent.VK_LEFT:
                player.direction = Player.Direction.left;
                break;
            case KeyEvent.VK_UP:
                player.direction = Player.Direction.up;
                break;
            case KeyEvent.VK_DOWN:
                player.direction = Player.Direction.down;
                break;
            case KeyEvent.VK_SPACE:
                isPaused = false;
                main_label.setVisible(false);
                if(!isRunning) System.exit(0);
                break;
            case KeyEvent.VK_P:
                isPaused = !isPaused;
                if(isPaused){
                    main_label.setText("PAUSED");
                }
                main_label.setVisible(!main_label.isVisible());
        }
    }

    @Override
    /**
     * Changes the player's direction to none
     */
    public void keyReleased(KeyEvent e) {
        player.direction = Player.Direction.none;
        player.om_nom_nom_switch = false;
    }
}
