import java.util.ArrayList;

public class Character {
    private String ID;
    private int AP;
    private int HP;
    private int currentHP;
    private int MaxMove;
    private int[] position= new int[]{0,0};

    public Character(String ID){
        this.ID=ID;
    }

    public String getID() {
        return ID;
    }

    public int getAP() {
        return AP;
    }

    public int getHP() {
        return HP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getMaxMove() {
        return MaxMove;
    }

    public void setPosition(int x,int y) {
        this.position[0] = x;
        this.position[1]=y;
    }
    public int[] getPosition() {
        return position;
    }

    public int getX(){
        return position[0];
    }
    public int getY(){
        return position[1];
    }

    public void attack(ArrayList<Character> aroundCharacter){
        if(!aroundCharacter.isEmpty()){
            for(Character enemy: aroundCharacter){
                int HP= enemy.getCurrentHP()-this.getAP();
                enemy.setCurrentHP(HP);
            }
        }
    }

    public String toString(){
        return this.getID();
    }
}