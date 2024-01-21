import java.io.*;

// The newly-improved calibration document consists of lines of text;
// each line originally contained a specific calibration value that the Elves now need to recover.
// On each line, the calibration value can be found by combining the first digit and
// the last digit (in that order) to form a single two-digit number.
//
// Consider your entire calibration document. What is the sum of all of the calibration values?

public class Main {

  static int getNumberFromRow(String row) {
    // get the first and last digits from row
    String numberFromRow = "";
    for (int i = 0; i < row.length(); i++) {
      char c = row.charAt(i);
      if (Character.isDigit(c)) {
        numberFromRow += c;
      }
    }
    char firstDigit = numberFromRow.charAt(0);
    char lastDigit = numberFromRow.charAt(numberFromRow.length() - 1);
    String number = "" + firstDigit + lastDigit;
    return Integer.parseInt(number);
  }

  public static void main(String[] args) throws Exception {
    File file = new File("day_1/input.txt");
    BufferedReader br = new BufferedReader(new FileReader(file));
    String st;
    int sumOfAllCalibrationValues = 0;
    while ((st = br.readLine()) != null) {
      sumOfAllCalibrationValues += getNumberFromRow(st);
    }
    System.out.println(sumOfAllCalibrationValues);
  }
}
