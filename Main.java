import javax.swing.*;
import java.awt.*;

import java.util.*;
import java.util.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.*;

public class Main extends JPanel implements KeyListener {

    public static final int CELL_SIZE = 20;
    public static int row = 500 / CELL_SIZE;
    public static int column = 500 / CELL_SIZE;

    public static int width = 500;
    public static int height = 500;
    public static int score = 0;
    public static int highest_score = 0;
    public static String direction = "Right";
    public static boolean allowKeyPress = true;

    public static Snake snake = new Snake();
    public static Fruit fruit = new Fruit();
    public static Obstacle obstacle = new Obstacle();

    public static Main game = new Main();
    public static JFrame window = new JFrame("Snake Game");
    
    
    
    
    public Main(){
        //讀取最高分紀錄
        read_highest_score();
        //遊戲重置
        reset();
    }

    //讀取最高分紀錄
    public void read_highest_score(){
        try{
            File myObj = new File("score_record.txt");
            Scanner sc = new Scanner(myObj);
            if (sc.hasNextInt()) {
                highest_score = sc.nextInt();
            }
            sc.close();
        }catch(FileNotFoundException e){
            highest_score = 0;
            try{
                File myObj = new File("score_record.txt");
                if(myObj.createNewFile()){
                    System.out.println("File created：" + myObj.getName());
                }
                FileWriter fw = new FileWriter(myObj.getName());
                fw.write("" + 0);
            }catch(IOException err){
                System.out.println("An error occured");
                err.printStackTrace();
            }
        }
    }

    //遊戲重置
    private void reset(){
        score = 0;
        if(snake != null){
            snake.getSnakeBody().clear();
        }
        allowKeyPress = true;
        direction = "Right";
        snake = new Snake();
        fruit = new Fruit();
        obstacle = new Obstacle();
        sce=0;
        supertime = 0;
        setTimer();
    }

    private Timer t;
    public static int sce =0;
    public static boolean isrunning = true;
    private int speed = 110;
    private void setTimer(){
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                    repaint();
            }
        }, 0, speed);
    }////

    public static boolean issuper = false;
    private int supertime = 0;
    @Override
    public void paintComponent(Graphics g){
    if(isrunning){   //draw a black background, fruit, snake
        g.fillRect(0, 0, 500, 500);
        g.setColor(Color.GRAY);
        g.fillRect(500, 0,230, 500);
        g.setColor(Color.BLACK);
        g.fillRect(500, 0,3,500);

        g.setFont(new Font("Serif", Font.BOLD, 26));
        g.setColor(new Color(60,80,160));
        g.drawString("目前分數:"+score+"", 510,50);
        g.drawString("最高分數:"+highest_score+"", 510,130);
        g.drawString("時間:"+sce/10, 510,210);
        g.drawString("難度:\n"+(sce<500?(2-0.2*(sce/100)):(2-0.1*(sce/100)))+"秒一波", 510,290);
        g.setColor(new Color(250, 250, 70));
        g.drawString("剩餘無敵次數:"+(90-supertime)/30, 510,370);

        g.setFont(new Font("Serif", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        g.drawString("W/A/S/D:移動 ", 510,490);
        g.drawString("J:無敵狀態 ", 510,460);
        g.drawString("L:暫停/繼續", 510,430);
        obstacle.drawobstacle(g);//畫箱子

        //remove snake tail and put it in head（蛇的移動）
        int snakeX = snake.getSnakeBody().get(0).x;
        int snakeY = snake.getSnakeBody().get(0).y;
        
        if(direction.equals("Left")){
            snakeX -= CELL_SIZE;
        }else if(direction.equals("Up")){
            snakeY -= CELL_SIZE;
        }else if(direction.equals("Right")){
            snakeX += CELL_SIZE;
        }else{
            snakeY += CELL_SIZE;
        }
        
        //check if the snake eat the fruit5252152
        Node newHead = new Node(snakeX, snakeY);
        if(snake.getSnakeBody().get(0).x == fruit.getX() && snake.getSnakeBody().get(0).y == fruit.getY()){
            fruit.setNewLocation(snake);
            score++;
            if(score<10 || score%10!=0){
                snake.getSnakeBody().remove(snake.getSnakeBody().size()-1);
            }
        }else{
            snake.getSnakeBody().remove(snake.getSnakeBody().size()-1);
        }
        snake.getSnakeBody().add(0, newHead);
        
        

        //無敵次數跟時間計算
        if(issuper){
            supertime++;
            if(supertime==30||supertime==60||supertime==90){//3秒
                issuper = false;
            }
        }
        ArrayList<Node> snake_body = snake.getSnakeBody();
        Node head = snake_body.get(0);
        
        //判定是否撞到自己
        for(int i = 1;i < snake_body.size();i++){
            if(snake_body.get(i).x == head.x && snake_body.get(i).y == head.y && !issuper){
                allowKeyPress = false;
                t.cancel();
                t.purge();
    
                //結束遊戲使用彈跳視窗提示選項
                int response = JOptionPane.showOptionDialog(this, "Game Over. Your score was " + score + ". The highest score was " + highest_score + ".\nWould you like to start over？", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, JOptionPane.YES_NO_OPTION);
                //寫入分數;
                write_a_file(score);
                //選項執行
                switch(response){
                    case JOptionPane.CLOSED_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.NO_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.YES_OPTION:
                        reset();
                        return;
                }
            }
        }

        //判定是否撞到箱子
        for(int i = 0;i < snake_body.size();i++){
            for(int j=0;j<obstacle.Allobstacle.size();j++){
                if(obstacle.Allobstacle.get(j).x == snake_body.get(i).x && obstacle.Allobstacle.get(j).y == snake_body.get(i).y && !issuper){
                    allowKeyPress = false;
                    t.cancel();
                    t.purge();
        
                    //結束遊戲使用彈跳視窗提示選項
                    int response = JOptionPane.showOptionDialog(this, "Game Over. Your score was " + score + ". The highest score was " + highest_score + ".\nWould you like to start over？", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, JOptionPane.YES_NO_OPTION);
                    //寫入分數;
                    write_a_file(score);
                    //選項執行
                    switch(response){
                        case JOptionPane.CLOSED_OPTION:
                            System.exit(0);
                            break;
                        case JOptionPane.NO_OPTION:
                            System.exit(0);
                            break;
                        case JOptionPane.YES_OPTION:
                            reset();
                            return;     
                    }
                }  
            }
        }

        
        

        //設定箱子出現間隔時間
        sce++;
        if(sce>1000 && sce%5==0) {obstacle.AddBox();//100秒 隔0.5秒
        }else if(sce>900 && sce%6==0) {obstacle.AddBox();//90秒 隔0.6秒
        }else if(sce>800 && sce%7==0) {obstacle.AddBox();//80秒 隔0.7秒
        }else if(sce>700 && sce%8==0) {obstacle.AddBox();//70秒 隔0.8秒
        }else if(sce>600 && sce%9==0) {obstacle.AddBox();//60秒 隔0.9秒
        }else if(sce>500 && sce%10==0) {obstacle.AddBox();//50秒 隔1.0秒
        }else if(sce>400 && sce%12==0) {obstacle.AddBox();//40秒 隔1.2秒
        }else if(sce>300 && sce%14==0) {obstacle.AddBox();//30秒 隔1.4秒
        }else if(sce>200 && sce%16==0) {obstacle.AddBox();//20秒 隔1.6秒
        }else if(sce>100 && sce%18==0) {obstacle.AddBox();//10秒 隔1.8秒
        }else if(sce>0 && sce%20==0) {obstacle.AddBox();//0秒 隔2.0秒
        }
        fruit.drawFruit(g);//畫水果
        snake.drawSnake(g);//畫蛇
        
    }else{//暫停
        g.setColor(Color.YELLOW);
        g.fillRect(250,180,50,150);
        g.fillRect(330,180,50,150); 
    }
    }
    
    public static void main(String[] args){
        //視窗初始化
        JFrame window = new JFrame("Snake Game");
        JPanel panel = new JPanel();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(730, 537);
        panel.setBackground(Color.BLACK);
        
        ImageIcon icon= new ImageIcon("capoo.jpg");
        JButton button = new JButton(icon);
        button = new JButton(icon);
        button.setBounds(648, 413, 100, 100);
        
        
        //加入鍵盤監聽物件
        Main game = new Main();
        game.setLayout(null);
        game.add(button);
        Act act = new Act();
        button.addActionListener(act);

        window.add(game);
        window.addKeyListener(game);
        window.setFocusable(true);
        window.setVisible(true);     
    }
    
    static class Act implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(
            (JButton)e.getSource(), //或是(Component)e.getSource()
            "被發現了!!!"
            ,"遊戲介紹",
            JOptionPane.INFORMATION_MESSAGE
            );
    }
}

    @Override
    public void keyPressed(KeyEvent e){
        if(allowKeyPress){
            if(e.getKeyCode() == 65 && !direction.equals("Right")) direction = "Left";
            else if(e.getKeyCode() == 87 && !direction.equals("Down")) direction = "Up";
            else if(e.getKeyCode() == 68 && !direction.equals("Left")) direction = "Right";
            else if(e.getKeyCode() == 83 && !direction.equals("Up")) direction = "Down";
            else if(e.getKeyCode() == 76) isrunning = !isrunning;
            else if(e.getKeyCode() == 74 &&supertime<90) issuper = true;
        }
    }

    

    public void write_a_file(int score){
        try{
            FileWriter f = new FileWriter("score_record.txt");
            if(score > highest_score){
                f.write("" + score);
                highest_score = score;
            }else{
                f.write("" + highest_score);
            }
            f.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
//測試1



