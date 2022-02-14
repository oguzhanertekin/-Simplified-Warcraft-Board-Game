import java.io.*;
import java.util.*;

public class Main {
    public static String[][] board;  // initialize board
    public static String copyboard;
    public static int size; // board size
    public static TreeMap<String,Character> characters = new TreeMap<String,Character>(); // Map; ID is the key
                                                                                          // Character Object is the value.
                                                                                          // POLYMORPHISM

    public static List<String> readFile(String fileName) throws IOException { // this method read any file and return
                                                                        // that list line by line in List
        String line;
        File file= new File(fileName);
        FileReader fReader= new FileReader(file);
        BufferedReader bReader= new BufferedReader(fReader);
        List<String> list= new ArrayList();
        while ((line = bReader.readLine()) != null) {
            list.add(line); }
        return list;
    }
    public static void createBoard(String initialFile) throws IOException {  // this method create board with characters
        String table= (String) readFile(initialFile).get(1);
        size= Integer.parseInt(table.split("x")[0]);   // define the size of board
        board= new String[size][size];  // creating board

        for(String[] line:board){   // fill board with spaces
            Arrays.fill(line,"  ");
        }

        for(String line: readFile(initialFile)){
            if(line.startsWith("ELF")){
               String ID=line.split(" ")[1];
               Elf elf=new Elf(ID);
               placeChar(elf,line);
            }
            else if(line.startsWith("HUMAN")){
                String ID=line.split(" ")[1];
                Human human= new Human(ID);
                placeChar(human,line);
            }
            else if(line.startsWith("DWARF")){
                String ID=line.split(" ")[1];
                Dwarf dwarf= new Dwarf(ID);
                placeChar(dwarf,line);
            }

            else if(line.startsWith("GOBLIN")){
                String ID=line.split(" ")[1];
                Goblin goblin= new Goblin(ID);
                placeChar(goblin,line);
            }

            else if(line.startsWith("TROLL")){
                String ID=line.split(" ")[1];
                Troll troll= new Troll(ID);
                placeChar(troll,line);
            }

            else if(line.startsWith("ORK")){
                String ID=line.split(" ")[1];
                Ork ork= new Ork(ID);
                placeChar(ork,line);
            }

        }
    }
    public static String showBoard() throws IOException {  // this method returns current table in output file

        int stars= (size+1);
        int i=0;
        String bound="";
        while(i<stars){
            bound+="**";
            i++;}

        String bound2=bound;

        // creating bounds of board
        bound=bound+"\n";
        for(String[] raw: board){
            bound+="*";
            for(String line: raw){
                bound+=line;
            }
            bound+="*\n";
        }
        bound+=bound2+"\n\n";

        for(Map.Entry<String,Character> entry:characters.entrySet()){ // POLYMORPHISM
            Character character= (Character) entry.getValue();
            bound+=String.format("%s\t%d\t(%d)",character.getID(),character.getCurrentHP(),character.getHP());
            bound+="\n";
        }
        bound+="\n";
        return bound;
    }
    public static boolean isBound(Character character,int x,int y){ // this method check whether  position coordiantes are bound (**) or not.

        if(!(x>=0 && x<size) || !(y>=0 && y<size)){
            return true; }
        else
            return false;
    }
    public static boolean canMove(Character character,String command){  // this method checks whether character can move or not ; in terms of character's maxMove value.

        int moveCount= Integer.parseInt(String.valueOf(command.split(";").length));
        if(character.getMaxMove()*2!=moveCount)
            return false;
        else
            return true;
    }
    public static void placeChar(Character character,String line){  // this method places character into the table by character's position
                                                                    // POLYMORPHISM
        int x= Integer.parseInt(line.split(" ")[2]);
        int y= Integer.parseInt(line.split(" ")[3]);
        character.setPosition(x,y);
        characters.put(character.getID(),character);
        board[y][x]=character.toString();

    }
    public static int occupied(Character character,int x,int y){      // this method checks the next position and
                                                                    // returns 1 --> if square occupied by friend
                                                                    // returns 0 --> if square not occupied
                                                                    // returns -1 --> if square occupied by enemy
                                                                    // POLYMORPHISM
        if(character instanceof Zorde){
            String square=board[y][x];
            if(square.equals("  "))
                return 0;
            else if(!(square.equals("  ")) && characters.get(square) instanceof Callliance)
                return -1;
            else
                return 1;
        }

        else if(character instanceof Callliance){
            String square=board[y][x];
            if(square.equals("  "))
                return 0;
            else if(!(square.equals("  ")) && characters.get(square) instanceof Zorde)
                return -1;
            else
                return 1;
        }
        return 0;

    }
    public static ArrayList<int[]> moveList(String command){ // this method reads command and convert numbers to integer type.
                                                                    // Also return a List that contains binary steps.   ex: [1,0],[-1,1],[1,1]....
        ArrayList<int[]> steps = new ArrayList<>();
        int key;
        for(key=0; key<(command.split(";").length);key++){
            int[] step= new int[2];
            step[0]= Integer.parseInt(command.split(";")[key]);
            step[1]=Integer.parseInt(command.split(";")[key+1]);
            steps.add(step);
            key++;
        }
        return steps;
    }
    public static HashMap<String,ArrayList<Character>> checkAround(Character character,int range){  // this method check character's neighboring squares and determines friends and enemies.
        HashMap<String,ArrayList<Character>> around= new HashMap<>();
        ArrayList<Character> friends= new ArrayList<>();
        ArrayList<Character> enemies= new ArrayList<>();

        for(int x=-range;x<=range;x++){
            for(int y=-range;y<=range;y++){
                if(x==0 && y==0){
                    continue;
                }
                int x1=character.getX()+x;
                int y1=character.getY()+y;
                if(!(isBound(character,x1,y1))){
                    if(occupied(character,x1,y1)==1){
                        friends.add(characters.get(board[y1][x1]));
                    }
                    else if (occupied(character,x1,y1)==-1){
                        enemies.add(characters.get(board[y1][x1]));
                    }
                }
                x1=0;
                y1=0;
            }
        }
        around.put("FRIENDS",friends);
        around.put("ENEMIES",enemies);
        return around;
    }
    public static void fightToDeath(Character attacker,Character deffender){  // when a character tried to move to square that occupied by enemy. this method is called.
        int deffenderHP=deffender.getCurrentHP()- attacker.getAP();
        deffender.setCurrentHP(deffenderHP);
        if(attacker.getCurrentHP()>deffender.getCurrentHP()){
            int x=deffender.getX();
            int y=deffender.getY();
            int attackerHP= attacker.getCurrentHP()- deffender.getCurrentHP();
            attacker.setCurrentHP(attackerHP);
            delete(deffender);
            changeBoard(attacker,x,y);

        }
        else if(attacker.getCurrentHP()<deffender.getCurrentHP()){
            deffenderHP=deffender.getCurrentHP()-attacker.getCurrentHP();
            deffender.setCurrentHP(deffenderHP);
            delete(attacker);
        }
        else if(attacker.getCurrentHP()==deffender.getCurrentHP()){
            delete(attacker);
            delete(deffender);
        }
    }
    public static void delete(Character character){  // this method deletes character from board and character list
        board[character.getY()][character.getX()]="  ";
        characters.remove(character.getID(),characters.get(character.getID()));
    }
    public static void checkDeath(){  // this methods check whether any character is dead or not. If there is any dead character, it deletes that character

        Set<String> keys=characters.keySet();
        TreeSet<String> sortedKeys = new TreeSet<String>(keys);
        if(!sortedKeys.isEmpty()){
            for (String key:sortedKeys) {
                if (characters.get(key).getCurrentHP() <= 0) {
                    delete(characters.get(key)); }
            }
        }
    }
    public static void changeBoard(Character character,int x,int y){ //changes board's current appearence
        if(!isBound(character,x,y)){
            board[character.getY()][character.getX()]="  ";
            character.setPosition(x,y);
            board[y][x]=character.getID();
        }

    }
    public static String copyBoard(String orgBoard){  // this methods copies board
        String newBoard= orgBoard;
        return newBoard; }
    public static boolean isDifferent(String board1,String board2){ // this method checks whether board was changed or not
        if(board1.equals(board2)){
            return false; }
        else{
            return true; }
    }
    public static void commands(String commandFile,String output) throws IOException {  // this methods read commands file and operates them
        File outputFile = new File(output);   /* Creating outputFile */
        FileWriter fWriter = new FileWriter(outputFile, false);
        BufferedWriter bWriter = new BufferedWriter(fWriter);

        List<String> list= readFile(commandFile);
        bWriter.write(showBoard());
        for(String line:list){
            copyboard=copyBoard(showBoard());
            String ID= line.split(" ")[0];
            String command= line.split(" ")[1];
            Character character=(Character) characters.get(ID);
            try{
                if (canMove(character,command)){
                    int key=0;
                    for(int[] position:moveList(command)){
                        key++;
                        int currentX= character.getX();
                        int currentY= character.getY();
                        int x = position[0];
                        int y = position[1];
                        try {
                            if(!isBound(character,currentX+x,currentY+y)){
                                if(character instanceof Ork){
                                    Ork ork =(Ork) character;  // POLYMOROHISM
                                    ork.heal(checkAround(character,1).get("FRIENDS"));
                                }

                                if(occupied(character,currentX+x,currentY+y)==1){
                                    break;
                                }
                                else if(occupied(character,currentX+x,currentY+y)==0){
                                    changeBoard(character,currentX+x,currentY+y);
                                    if(character instanceof Elf || character instanceof Dwarf || character instanceof Goblin){
                                        if(character instanceof Elf){ // POLYMOROHISM
                                            if(character.getMaxMove()==key){
                                                character.attack(checkAround(character,2).get("ENEMIES"));
                                                checkDeath();
                                            }
                                            else {
                                                character.attack(checkAround(character,1).get("ENEMIES"));
                                                checkDeath();
                                            }
                                        }
                                        else {
                                            character.attack(checkAround(character,1).get("ENEMIES"));
                                            checkDeath();
                                        }
                                    }
                                    else if (character instanceof Ork || character instanceof Human || character instanceof Troll){  // POLYMOROHISM
                                        if(character.getMaxMove()==key){
                                            character.attack(checkAround(character,1).get("ENEMIES"));
                                            checkDeath();
                                        }
                                    }

                                }
                                else if ((occupied(character,currentX+x,currentY+y))==-1){
                                    Character deffender= (Character) characters.get(board[currentY+y][currentX+x]);
                                    fightToDeath(character,deffender);
                                    checkDeath();
                                    break;
                                }
                            }
                            else {
                                if(isDifferent(showBoard(),copyboard)){
                                    bWriter.write(showBoard());
                                }
                                throw new BoundaryCheckException();
                            }

                        }
                        catch (BoundaryCheckException e) {
                            bWriter.write("Error : Game board boundaries are exceeded. Input line ignored.\n\n");
                            copyboard=copyBoard(showBoard());
                            break;
                        }
                    }
                }
                else {
                    throw new MoveCountException();
                }
            }
            catch (MoveCountException e){
                bWriter.write("Error : Move sequence contains wrong number of move steps. Input line ignored.\n\n");
                continue;
            }
            checkDeath();

            if(isDifferent(showBoard(),copyboard)){
                bWriter.write(showBoard());
                copyboard=copyBoard(showBoard());
            }
            copyboard=copyBoard(showBoard());
            if(characters.size()==1){
                for(Map.Entry<String,Character> entry: characters.entrySet()){
                    if(entry.getValue() instanceof Zorde){
                        bWriter.write("\nGame Finished\nZorde Wins");
                    }
                    else if(entry.getValue() instanceof Callliance){
                        bWriter.write("\nGame Finished\nCalliance Wins");
                    }break;
                }
                break;
            }
        }bWriter.close();
    }

    public static void main(String[] args) throws IOException{
        String initialsFile=args[0];
        String commandsFile=args[1];
        String outputFile=args[2];

        createBoard(initialsFile);
        commands(commandsFile,outputFile);
    }
}
