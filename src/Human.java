import java.util.ArrayList;

public class Human extends Callliance{
    private final int AP=Constants.humanAP;
    private final int HP=Constants.humanHP;
    private final int MaxMove=Constants.humanMaxMove;
    private int currentHP=Constants.humanHP;

    public Human(String ID) {
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

    @Override
    public int getCurrentHP() {
        return currentHP;
    }

    @Override
    public void attack(ArrayList<Character> aroundCharacter) {
        super.attack(aroundCharacter);
    }
}
