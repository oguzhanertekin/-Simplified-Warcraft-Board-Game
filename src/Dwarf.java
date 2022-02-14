import java.util.ArrayList;

public class Dwarf extends Callliance{
    private final int AP=Constants.dwarfAP;
    private final int HP=Constants.dwarfHP;
    private final int MaxMove=Constants.dwarfMaxMove;
    private int currentHP=Constants.dwarfHP;

    public Dwarf(String ID) {
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
