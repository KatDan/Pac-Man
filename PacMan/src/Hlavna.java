import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Hlavna extends Canvas implements Runnable, KeyListener {

    public Hlavna(){
        Dimension dimension = new Dimension(Hlavna.WIDTH,Hlavna.HEIGHT);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);


        addKeyListener(this);
        postavy = new ObrazkyPostav("/postavy.png");
        bludisko = new Graf();
        hrac = new Hrac(Hlavna.WIDTH/2, Hlavna.WIDTH/2);
        level = new Level();

        dolezity_label = new JLabel("PAUSED",JLabel.CENTER);
        dolezity_label.setOpaque(true);
        dolezity_label.setBounds(Hlavna.WIDTH/2 - 150, Hlavna.HEIGHT/2 -50, 300,100);
        dolezity_label.setForeground(Color.white);
        dolezity_label.setBackground(new Color(6,5,45));

    }

    public static Hrac hrac;
    public static Level level;
    public static ObrazkyPostav postavy;
    public static Graf bludisko;

    public static JPanel panel;
    public JLabel skore_label;
    public static JLabel dolezity_label;

    public Rectangle rect;
    //public static Graphics g = new Graphics();

    public static final int WIDTH = 640, HEIGHT = 480;
    public static final String TITLE = "Pac-Man";

    public static boolean isPaused = true;
    public static boolean isRunning = true;

    public void tick(){
       // System.out.println("ano");
        if(isRunning){
            hrac.tick();
            level.tick();
            level.skore_label.setText("Your score: "+hrac.skore);
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
        g.fillRect(0,0,Hlavna.WIDTH,Hlavna.HEIGHT);


        level.render(g);
        hrac.render(g);
        //g.setColor(Color.white);
        level.skore_label.setText("Your score: "+hrac.skore);

        g.dispose();
        bs.show();
    }

    public boolean threadSuspended = false;
    public int i = 0;


    public void run() {
        render();
        render();
        double fps = 0;
        double timer = System.currentTimeMillis();

        long lastTime = System.nanoTime();
        double delta = 0;
        double targetTick = 60.0;
        double ns = 1000000000 / targetTick;

        while (isRunning) {
            Hlavna.hlavna.requestFocus();
            i++;
            if(!isPaused) {
                dolezity_label.setVisible(false);
                //System.out.println("ide");
                long now = System.nanoTime();
                if((now - lastTime)/ns > 10) lastTime = now - 1;
                delta += (now - lastTime) / ns;
                lastTime = now;
                while (delta >= 1) {
                    tick();
                    render();
                    fps++;
                    delta--;
                }
                if (i % 5000 == 0)
                    hrac.spinac = !hrac.spinac;
                if (System.currentTimeMillis() - timer >= 1000) {
                    //System.out.println(fps);

                    fps = 0;
                    timer += 1000;
                }
            }
            else render();
        }
    }










    public static Hlavna hlavna;

    public static void main(String[] args){
        hlavna = new Hlavna();
        JFrame frame = new JFrame();
        frame.setTitle(Hlavna.TITLE);
        frame.add(level.skore_label);
        frame.add(dolezity_label);
        frame.add(hlavna);

        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //panel.setSize(50,50);
        dolezity_label.setText("press space to start");
        dolezity_label.setVisible(true);
        hlavna.requestFocus();
        //Hlavna.render();
        hlavna.run();
        //hlavna.isRunning = false;
    }

    public static void nova_hra(){
        isPaused = true;
        isRunning = true;
        level = new Level();
        hlavna.render();
        //level.dolezity_label.setVisible(false);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        hrac.spinac = true;
        switch (e.getKeyCode()){
            case KeyEvent.VK_RIGHT:
                hrac.smer = Hrac.Smer.right;
                break;
            case KeyEvent.VK_LEFT:
                hrac.smer = Hrac.Smer.left;
                break;
            case KeyEvent.VK_UP:
                hrac.smer = Hrac.Smer.up;
                break;
            case KeyEvent.VK_DOWN:
                hrac.smer = Hrac.Smer.down;
                break;
            case KeyEvent.VK_SPACE:
                isPaused = false;
                dolezity_label.setVisible(false);
                if(!isRunning) System.exit(0);

                break;
            case KeyEvent.VK_P:
                isPaused = !isPaused;
                if(isPaused){
                    dolezity_label.setText("PAUSED");
                }

                dolezity_label.setVisible(!dolezity_label.isVisible());

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        hrac.smer = Hrac.Smer.none;
        hrac.spinac = false;
    }
}
