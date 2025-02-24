import java.util.*;
import java.awt.*;

public class Fruit {

    public static int x = 0;
    public static int y = 0;

    //水果的初始位置
    public Fruit(){
        this.x = (int) (Math.floor(Math.random()*Main.column)*Main.CELL_SIZE);
        this.y = (int) (Math.floor(Math.random()*Main.row)*Main.CELL_SIZE);
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    //畫出水果的初始位置
    public void drawFruit(Graphics g){
        g.setColor(Color.RED);
        g.fillOval(this.x, this.y, Main.CELL_SIZE, Main.CELL_SIZE);
    }

    //產生新的水果位置
    public void setNewLocation(Snake s){
        int new_x = (int) (Math.floor(Math.random()*Main.column)*Main.CELL_SIZE);
        int new_y = (int) (Math.floor(Math.random()*Main.column)*Main.CELL_SIZE);
        boolean overlapping;
        do{
            overlapping = check_overlap(new_x, new_y, s);
        }while(overlapping);

        this.x = new_x;
        this.y = new_y;
    }

    //檢查新的水果位置是否合法
    private boolean check_overlap(int x, int y, Snake s){
        ArrayList<Node> snake_body = s.getSnakeBody();

        for(int j = 0;j < s.getSnakeBody().size();j++){
            if(x == snake_body.get(j).x && y == snake_body.get(j).y) {
                return true;
            }
        }
        return false;
    }
}
