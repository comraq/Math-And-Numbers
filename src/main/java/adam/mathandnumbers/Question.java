package adam.mathandnumbers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import adam.mathandnumbers.QuestionBank.QuestionType;
import adam.mathandnumbers.QuestionBank.QuestionOptions;

/**
 * Created by adam on 2016-01-09.
 */
public class Question {
  //TODO: Need to update numDigits based on the specified options;
  private int numOperands = 2;

  private QuestionType type;
  private List<QuestionOptions> options;
  private ArrayList<Integer> operands;
  private ArrayList<Integer> operandsDigits;

  public Question (QuestionType type) {
    this.type = type;
    options = new ArrayList<QuestionOptions>();
    operands = new ArrayList<Integer>();
    operandsDigits = new ArrayList<Integer>();
  }

  public Question genNextQuestion() {
    //TODO: Generate operands as restricted by the specified options;
    operandsDigits.clear();
    operands.clear();
    for (int i = 0; i < numOperands; ++i) {
      //TODO: Need to update numDigits based on the specified options;
      operandsDigits.add(4);
      operands.add(ThreadLocalRandom.current().nextInt(0, (int) Math.pow(10, operandsDigits.get(i))));
    }
    return this;
  }

  public QuestionType getType() { return type; }

  public boolean checkOptions(QuestionOptions option) {
    if (options.indexOf(option) == -1) {
      return false;
    } else {
      return true;
    }
  }

  public void includeOption(QuestionOptions option) { options.add(option); }

  public int getNumOperands() { return numOperands; }

  public ArrayList<Integer> getOperands() { return operands; }
}
