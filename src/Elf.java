import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Elf extends Callliance{
    private final int AP=Constants.elfAP;
    private final int HP=Constants.elfHP;
    private final int MaxMove=Constants.elfMaxMove;
    private int currentHP=Constants.elfHP;
    private final int elfRangedAP= Constants.elfRangedAP;

    public Elf(String ID) {
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
