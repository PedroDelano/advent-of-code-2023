// The Elf would first like to know which games would have been possible
// if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?
import java.io.*;
import java.util.HashMap;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Main {

  public static String trim(String st) {
    return st.replaceAll("\\s+", "");
  }

  public static int toInt(String st) {
    st = trim(st);
    return Integer.parseInt(st);
  }

  public static boolean isColor(String st) {
    st = trim(st);
    if (st.equals("red") || st.equals("blue") || st.equals("green")) {
      return true;
    }
    return false;
  }

  public static HashMap<String, Integer> getSet(String set) {
    HashMap<String, Integer> game = new HashMap<String, Integer>();
    game.put("red", 0);
    game.put("green", 0);
    game.put("blue", 0);
    String[] arrOfString = set.split(",", 0);
    for (String elem : arrOfString) {
      String color = elem.replaceAll("\\d+", "").replaceAll("\\s+", "");
      int numberOfBalls = toInt(elem.replaceAll("[a-zA-Z]", "").replaceAll("\\s+", ""));
      game.put(color, game.get(color) + numberOfBalls);
    }
    return game;
  }

  public static boolean isGameValid(String row) {
    row = row.replaceAll("Game (\\d*):", "");
    String[] sets = row.split(";");
    // for each game set, checks to see if it's valid
    // considering the number of colored balls
    for (String setString : sets) {
      HashMap<String, Integer> set = getSet(setString);
      if (!isSetValid(set)) {
        return false;
      }
    }
    return true;
  }

  public static int getGameId(String row) {
    String[] matches =
        Pattern.compile("Game (\\d*):")
            .matcher(row)
            .results()
            .map(MatchResult::group)
            .toArray(String[]::new);
    String gameId = matches[0];
    gameId = gameId.replaceAll("Game ", "");
    gameId = gameId.replaceAll(":", "");
    return toInt(gameId);
  }

  public static boolean isSetValid(HashMap<String, Integer> set) {
    if (set.get("red") > 12 || set.get("green") > 13 || set.get("blue") > 14) {
      return false;
    }
    return true;
  }

  public static void main(String[] args) throws Exception {
    File file = new File("day_2/input.txt");
    BufferedReader br = new BufferedReader(new FileReader(file));
    String row;
    int idSumOfValidGames = 0;
    while ((row = br.readLine()) != null) {
      if (isGameValid(row)) {
        idSumOfValidGames += getGameId(row);
      }
    }
    System.out.println("Soma dos IDs de jogos válidos: " + idSumOfValidGames);
  }
}
