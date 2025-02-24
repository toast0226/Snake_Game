import java.util.*;
import java.awt.*;

public class Snake {

    private ArrayList<Node> snakeBody;

    //蛇的初始位置
    public Snake(){
        snakeBody = new ArrayList<>();
        snakeBody.add(new Node(20,240));
    }

    public ArrayList<Node> getSnakeBody(){
        return snakeBody;
    }

    //畫出蛇的初始位置
    public void drawSnake(Graphics g){
        for(int i = 0;i<snakeBody.size();i++){
            if(i==0){
                if(Main.issuper){
                    g.setColor(Color.YELLOW);
                }else{
                    g.setColor(Color.GREEN);
                }
            }else{
                g.setColor(Color.gray);
            }
    
            Node n = snakeBody.get(i);
            if(n.x >= Main.width) n.x = 0;
            if(n.x < 0) n.x = Main.width - Main.CELL_SIZE;
            if(n.y >= Main.height) n.y = 0;
            if(n.y < 0) n.y = Main.height - Main.CELL_SIZE;
            
            g.fillOval(n.x, n.y, Main.CELL_SIZE, Main.CELL_SIZE);
        }
    }
}