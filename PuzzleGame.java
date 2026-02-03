// Task 1: Import the utility package
import java.util.*;
import java.io.*;
public class PuzzleGame {
    // Task 1: Declare the variables
   private static List<String> wordList=new ArrayList<>();
   private static List<String> sequenceList=new ArrayList<>();
   private static Random rand=new Random();

    // Task 1, 2: Define the PuzzleGame constructor
    public PuzzleGame(){
        if (wordList.isEmpty()){
            loadWords("words.txt");
        }
        if (sequenceList.isEmpty()){
            loadSequences("sequences.txt");
        }
    
    }
    // Task 2: Load the words
private static void loadWords(String filename){
   
    try (BufferedReader br=new BufferedReader(new FileReader(filename))){
   String line;
   while((line=br.readLine())!=null){
    if(!line.trim().isEmpty()) wordList.add(line.trim());
   }
    }   
    catch (IOException e) {
        // TODO: handle exception
        System.out.println("Error loading words" +e.getMessage());
    }

}
    // Task 2: Load the sequences
    private static void loadSequences(String filename){
        String line;
        try(BufferedReader br=new BufferedReader(new FileReader(filename))) {
            while((line=br.readLine())!= null){
                if(!line.trim().isEmpty()){
                      sequenceList.add(line.trim());
                }
            }
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println("Error loading sequences" + e.getMessage());
        }
    }
  

    // Task 4: Define the Puzzle class
    // Represents one puzzle instance for web mode
    public static class Puzzle{
        public String puzzleId;
        public String displayText;
        public String correctAnswer;

     }


    // Task 5: Scramble the word
   private String scrambleWord (String in){
    char[] ch=in.toCharArray();
    List<Character> charl=new ArrayList<>();
    for(char c:ch){
       charl.add(c);
    }
    Collections.shuffle(charl);
    StringBuilder sb=new StringBuilder();
    for(char c:charl){
        sb.append(c);
    }
    return sb.toString();
   }


    // Task 6: Generate one word puzzle
    // Generate one word puzzle
    public Puzzle generateWordPuzzle(){
        Puzzle p=new Puzzle();
        String word=wordList.get(rand.nextInt(wordList.size()));
        String scrambled=scrambleWord(word);
        p.puzzleId=UUID.randomUUID().toString();
        p.displayText=scrambled;
        p.correctAnswer=word;
        return p;
    }


    // Task 7: verify the answer with puzzle's correct answer
          public boolean checkAnswer(String word,String ans){
 
            return ans.trim().equalsIgnoreCase(word);
          }


    // Task 8: Generate one math puzzle
    // Generate one math puzzle
public Puzzle generateMathPuzzle(){
 Puzzle p=new Puzzle();
 String seq=sequenceList.get(rand.nextInt(sequenceList.size()));
 String[] element=seq.split(",");
 int hideindex=rand.nextInt(element.length);
 String ans=element[hideindex];
 StringBuilder sb=new StringBuilder();
 for(int i=0;i<element.length;i++){
      if(i==hideindex) {
        sb.append("_");
      }else{
        sb.append(element[i]);
      } 
      if(i<element.length-1)
      sb.append(",");
 }
 p.puzzleId=UUID.randomUUID().toString();
 p.displayText=sb.toString();
 p.correctAnswer=ans;
    return p;
}


    // Task 3, 4, 6, 7, 9, 10: Define the CLI menu
    public void startCLI(){
        Scanner sc=new Scanner(System.in);
         int lives=3;
        while(lives>0){
            System.out.println("\nLives remaining: " + lives);
            System.out.println("=== Puzzle Game Menu ===");
            System.out.println("1.Word Scramble Puzzle");
            System.out.println("2.Math Pattern Puzzle");
            System.out.println("3.Exit");
            System.out.print("Select an option: ");
            String choice=sc.nextLine();
            boolean result=false;
            Puzzle p= null;
            switch(choice){
                case "1":
                p = generateWordPuzzle();
                System.out.println("Unscramble the word:");
                System.out.println(p.displayText);
                System.out.print("Your answer: ");
                String answer = sc.nextLine(); 
                result=checkAnswer(p.correctAnswer,answer);
             
                break;

            case "2":
            p= generateMathPuzzle();
                System.out.print("Guess the missing element in the sequence");
                System.out.println(p.displayText);
                System.out.print("Your answer: ");
                String answer1 = sc.nextLine(); 
                result=checkAnswer(p.correctAnswer,answer1);
                break;

            case "3":
                System.out.println("Exiting game. Goodbye!");
                return;  // Exit the game

            default:    // Handle invalid menu input
                System.out.println("Invalid choice.");
                continue; 

            }
            if(result){
                System.out.println("correct answer");

            }else{
                System.out.println("wromg answer,the answer is" + p.correctAnswer);
                lives--;
            }

        }
        System.out.println("Game over. You ran out of lives.");

     }


    // Task 1, Task 3: Start the game
    public static void main(String[] args) {
   PuzzleGame game=new PuzzleGame();
   game.startCLI();
        
    }

}