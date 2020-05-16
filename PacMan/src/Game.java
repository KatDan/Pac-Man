import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable, KeyListener {

    public Game(){
        Dimension dimension = new Dimension(Game.WIDTH, Game.HEIGHT);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);

        //inicializacia hlavnych objektov
        addKeyListener(this);
        characters = new Images("/postavy.png");
        maze = new Graph();
        player = new Player(Game.WIDTH/2, Game.WIDTH/2);
        level = new Level();

        //nastavenie hlavneho labelu
        main_label = new JLabel("PAUSED",JLabel.CENTER);
        main_label.setOpaque(true);
        main_label.setBounds(Game.WIDTH/2 - 150, Game.HEIGHT/2 -50, 300,100);
        main_label.setForeground(Color.white);
        main_label.setBackground(new Color(6,5,45));

        //nastavenie skore
        score_label = new JLabel("Your score: 0", JLabel.CENTER);
        score_label.setOpaque(true);
        score_label.setBounds(0,0,150,32);
        score_label.setForeground(Color.white);
        score_label.setBackground(new Color(6,5,45));

    }

    public static Game game;
    public static Player player;
    public static Level level;
    public static Images characters;
    public static Graph maze;

    public static JLabel main_label;
    public static JLabel score_label;

    public static final int WIDTH = 640, HEIGHT = 480;
    public static final String TITLE = "Pac-Man";

    public static boolean isPaused = true;
    public static boolean isRunning = true;

    public void tick(){
        if(isRunning){
            player.tick();
            level.tick();
            score_label.setText("Your score: "+ player.score);
        }
    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(new Color(10,8,25));
        g.fillRect(0,0, Game.WIDTH, Game.HEIGHT);

        //update pozicii
        level.render(g);
        player.render(g);
        score_label.setText("Your score: "+ player.score);
        score_label.updateUI();

        g.dispose();
        bs.show();
    }

    public int i = 0;

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
                if (i % 5000 == 0)
                    player.om_nom_nom_switch = !player.om_nom_nom_switch;

                if (System.currentTimeMillis() - timer >= 1000) {
                    timer += 1000;
                }
            }
            else render();
        }
    }

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
    public void keyReleased(KeyEvent e) {
        player.direction = Player.Direction.none;
        player.om_nom_nom_switch = false;
    }
}
