// There are lots of numbers and symbols you don't really understand,
// but apparently any number adjacent to a symbol, even diagonally,
// is a "part number" and should be included in your sum.
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Main {

  public static HashMap<String, Integer> getMatrixShape(BufferedReader br) throws Exception {
    // outputs a {"ncol": X, "nrow": Y} hashmap
    HashMap<String, Integer> matrixShape = new HashMap<String, Integer>();
    String row;
    Integer nrow = 0;
    Integer ncol = 0;
    while ((row = br.readLine()) != null) {
      nrow += 1;
      if (nrow == 1) {
        ncol = row.length();
      }
    }
    matrixShape.put("nrow", nrow);
    matrixShape.put("ncol", ncol);
    return matrixShape;
  }

  public static char[][] constructInputMatrix(String row, int rowIndex, char[][] inputMatrix) {
    // iterativelly constructs the input matrx
    for (int colIndex = 0; colIndex < row.length(); colIndex++) {
      inputMatrix[rowIndex][colIndex] = row.charAt(colIndex);
    }
    return inputMatrix;
  }

  public static boolean isDigit(char c) {
    String pattern = "[\\d]";
    return Pattern.matches(pattern, String.valueOf(c));
  }

  public static boolean isSymbol(char c) {
    return c != '.' && (c < '0' || c > '9');
  }

  public static boolean isAdjacent(
      ArrayList<Integer> numberIndex, char[][] stringMatrix, int rowIndex, int nrow, int ncol) {
    // check if the element on the left is a valid symbol
    if (numberIndex.get(0) > 0) {
      if (isSymbol(stringMatrix[rowIndex][numberIndex.get(0) - 1])) return true;
    }
    // check if the element on the right is a valid symbol
    if (numberIndex.get(numberIndex.size() - 1) < ncol - 1) {
      if (isSymbol(stringMatrix[rowIndex][numberIndex.get(numberIndex.size() - 1) + 1]))
        return true;
    }
    // check if there's any symbol in the up and down
    for (int index : numberIndex) {
      // checks up
      if (rowIndex > 0) {
        if (isSymbol(stringMatrix[rowIndex - 1][index])) return true; // checks up
        if (index + 1 < nrow - 1)
          if (isSymbol(stringMatrix[rowIndex - 1][index + 1])) return true; // check upper right
        if (index > 0)
          if (isSymbol(stringMatrix[rowIndex - 1][index - 1])) return true; // check upper left
      }
      // checks down
      if (rowIndex < nrow - 1) {
        if (isSymbol(stringMatrix[rowIndex + 1][index])) return true; // checks down
        if (index + 1 < nrow - 1)
          if (isSymbol(stringMatrix[rowIndex + 1][index + 1])) return true; // checks lower right
        if (index > 0)
          if (isSymbol(stringMatrix[rowIndex + 1][index - 1])) return true; // checks lower left
      }
    }
    return false;
  }

  public static void main(String[] args) throws Exception {
    String filePath = "day_3/input.txt";
    File file = new File(filePath);

    // Get the dimensions for the matrix
    BufferedReader br = new BufferedReader(new FileReader(file));
    HashMap<String, Integer> matrixShape = getMatrixShape(br);
    int nrow = matrixShape.get("nrow");
    int ncol = matrixShape.get("ncol");

    // empty matrix template
    char[][] stringMatrix = new char[nrow][ncol];

    // generate a matrix for the input
    br = new BufferedReader(new FileReader(file));
    String row;
    int rowIndex = 0;
    while ((row = br.readLine()) != null) {
      stringMatrix = constructInputMatrix(row, rowIndex, stringMatrix);
      rowIndex += 1;
    }

    int sumOfValidNumber = 0;

    // identify numbers and get the indexes
    for (int i = 0; i < nrow; i++) {
      for (int j = 0; j < ncol; j++) {
        if (!isDigit(stringMatrix[i][j])) continue;
        // if it finds a number will iterate until it reaches a dot again
        ArrayList<Integer> numberIndex = new ArrayList<>();
        for (int k = j; k < ncol; k++) {
          if (!isDigit(stringMatrix[i][j])) break;
          numberIndex.add(k);
          j++;
        }
        boolean isValid = isAdjacent(numberIndex, stringMatrix, i, nrow, ncol);
        if (isValid) {
          // join the numbers as a string and converts it into a Integer
          String validNumber = "";
          for (int elem : numberIndex) {
            validNumber += stringMatrix[i][elem];
          }
          sumOfValidNumber += Integer.parseInt(validNumber);
        }
      }
    }
    System.out.println("Sum of all valid numbers: " + sumOfValidNumber);
  }
}
