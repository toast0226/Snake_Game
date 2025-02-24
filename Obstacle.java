import java.util.*;
import java.awt.*;

public class Obstacle{
    ArrayList<Box> Allobstacle;
    Color myColor = new Color(0);

    public Obstacle(){
        Allobstacle = new ArrayList<>();
        int i=0;
        for(i=0;i<=1;i++){//上下
            int a = (int) (Math.floor(Math.random()*Main.column)*Main.CELL_SIZE);
            if(a==Fruit.x) {
                --i;
                continue;
            }
            Allobstacle.add(new Box(a,Main.width*i));
        }
        for(i=0;i<=1;i++){//左右
            int a = (int) (Math.floor(Math.random()*Main.row)*Main.CELL_SIZE);
            if(a==Fruit.y) {
                --i;
                continue;
            }
            Allobstacle.add(new Box(Main.height*i,a));
        }
    }
        
    public void AddBox(){
        int i=0;
        for(i=0;i<=1;i++){//上下
            int a = (int) (Math.floor(Math.random()*Main.column)*Main.CELL_SIZE);
            if(a==Fruit.x) {
                --i;
                continue;
            }else{
                Allobstacle.add(new Box(a,Main.width*i));
            }
        }
        for(i=0;i<=1;i++){//左右
            int a = (int) (Math.floor(Math.random()*Main.row)*Main.CELL_SIZE);
            if(a==Fruit.y) {
                --i;
                continue;
            }else{
                Allobstacle.add(new Box(Main.height*i,a));
            }
           
        }
    }
    
    public void drawobstacle(Graphics g){
        int i=0;
        if(Main.sce%25==0){
            int red = (int) (Math.random() * 256);
            int green = (int) (Math.random() * 256);
            int blue = (int) (Math.random() * 256);
            myColor =new Color(red,green,blue);
        }
        for(i=0;i<Allobstacle.size();i++){
            if(i%4==0){//上
                g.setColor(myColor);
                Allobstacle.get(i).y = Allobstacle.get(i).y + Main.CELL_SIZE;
                if(Allobstacle.get(i).y == 500){
                    for(int j=3;j>=0;j--){
                        Allobstacle.remove(i);
                    }
                    i=-1;
                    continue;
                }
                g.fillRect(Allobstacle.get(i).x,Allobstacle.get(i).y,Main.CELL_SIZE,Main.CELL_SIZE);
            }else if(i%4==1){//下
                g.setColor(myColor);
                Allobstacle.get(i).y = Allobstacle.get(i).y - Main.CELL_SIZE;
                g.fillRect(Allobstacle.get(i).x,Allobstacle.get(i).y,Main.CELL_SIZE,Main.CELL_SIZE);
            }else if(i%4==2){//左
                g.setColor(myColor);
                Allobstacle.get(i).x = Allobstacle.get(i).x + Main.CELL_SIZE;
                g.fillRect(Allobstacle.get(i).x,Allobstacle.get(i).y,Main.CELL_SIZE,Main.CELL_SIZE);
            }else if(i%4==3){//右
                g.setColor(myColor);
                Allobstacle.get(i).x = Allobstacle.get(i).x - Main.CELL_SIZE;
                g.fillRect(Allobstacle.get(i).x,Allobstacle.get(i).y,Main.CELL_SIZE,Main.CELL_SIZE);
            }
        }
    }
}