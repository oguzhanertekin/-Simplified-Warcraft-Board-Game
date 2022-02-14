import javax.swing.plaf.basic.BasicMenuUI;
import java.util.ArrayList;

public class Ork extends Zorde{
    private final int AP=Constants.orkAP;
    private final int HP=Constants.orkHP;
    private final int MaxMove=Constants.orkMaxMove;
    private final int orkHealPoints = Constants.orkHealPoints;
    private int currentHP=Constants.orkHP;


    public Ork(String ID) {
        super(ID);
    }
    @Override
    public void setCurrentHP(int currentHP) {
        if(currentHP>this.getHP())
            this.currentHP = this.getHP();
        else if(currentHP<=0){
            this.currentHP=0;
        }
        else
            this.currentHP=currentHP;
    }

    @Override
    public int getAP() {
        return AP;
    }

    @Override
    public int getHP() {
        return HP;
    }

    @Override
    public int getMaxMove() {
        return MaxMove;
    }

    public int getOrkHealPoints() {
        return orkHealPoints;
    }

    @Override
    public int getCurrentHP() {
        return currentHP;
    }

    public void heal(ArrayList<Character> friendList){
        int ownHP= this.getCurrentHP()+this.getOrkHealPoints();
        this.setCurrentHP(ownHP);
        if(!friendList.isEmpty()){
            for(Character character:friendList){
                int HP=character.getCurrentHP()+this.getOrkHealPoints();
                character.setCurrentHP(HP);
            }
        }

    }

    @Override
    public void attack(ArrayList<Character> aroundCharacter) {
        super.attack(aroundCharacter);
    }
}
