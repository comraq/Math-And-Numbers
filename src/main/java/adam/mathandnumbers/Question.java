package adam.mathandnumbers;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import adam.mathandnumbers.QuestionBank.QuestionType;
import adam.mathandnumbers.QuestionBank.QuestionOptions;

import static adam.mathandnumbers.QuestionBank.QuestionType.*;

/**
 * Created by adam on 2016-01-09.
 */
public class Question {
  //TODO: Need to update numDigits based on the specified options;
  private int numOperands;
  private int numDigits;

  private QuestionType type;
  private Set<QuestionOptions> options;
  private ArrayList<Integer> operands;

  public Question (QuestionType type) {
    this.type = type;
    options = new HashSet<>();
    operands = new ArrayList<>();
  }

  public Question genNextQuestion() {
    operands.clear();
    switch(type) {
      case ADDITION:
        genAdditionQuestion();
        break;
      case SUBTRACTION:
        genSubtractionQuestion();
        break;
      case MULTIPLICATION:
        genMultiplicationQuestion();
        break;
      case DIVISION:
        genDivisionQuestion();
        break;

      default:
        //Do nothing!
    }
    return this;
  }

  private void genAdditionQuestion() {
    int total = (int) Math.pow(10, numDigits);
    //TODO: Generate operands as restricted by the specified options;
    for (int i = 0; i < numOperands; ++i) {
      operands.add(ThreadLocalRandom.current().nextInt(0, total));
      total -= operands.get(i);
    }
  }

  private void genSubtractionQuestion() {
    operands.add(ThreadLocalRandom.current().nextInt(0, (int) Math.pow(10, numDigits)));
    int remain = operands.get(0);
    //TODO: Generate operands as restricted by the specified options;
    for (int i = 1; i < numOperands; ++i) {
      operands.add(0, ThreadLocalRandom.current().nextInt(0, remain));
      remain -= operands.get(0);
    }
  }

  private void genMultiplicationQuestion() {
    int total = (int) Math.pow(10, numDigits);
    //TODO: Generate operands as restricted by the specified options;
    for (int i = 0; i < numOperands; ++i) {
      operands.add(ThreadLocalRandom.current().nextInt(0, total));
      if (operands.get(i) != 0)
        total /= operands.get(i);
    }
  }

  private void genDivisionQuestion() {
    operands.add(ThreadLocalRandom.current().nextInt(1, (int) Math.pow(10, numDigits)));
    int remain = operands.get(0);
    //TODO: Generate operands as restricted by the specified options;
    for (int i = 1; i < numOperands; ++i) {
      operands.add(0, ThreadLocalRandom.current().nextInt(1, remain));
      remain /= operands.get(0);
      if (remain == 1)
        remain = 2;
    }
  }

  public QuestionType getType() { return type; }

  public boolean checkOptions(QuestionOptions option) {
    if (options.contains(option)) {
      return true;
    } else {
      return false;
    }
  }

  public void setQuestionType(QuestionType type) { this.type = type; }

  public void setNumDigits(int maxDigits) { this.numDigits = maxDigits; }

  public void setNumOperands(int maxOperands) { this.numOperands = maxOperands; }

  public void setOperands(ArrayList<Integer> operands) { this.operands = operands; }

  public void includeOption(QuestionOptions option) { options.add(option); }

  public int getNumOperands() { return numOperands; }

  public int getNumDigits() { return numDigits; }

  public ArrayList<Integer> getOperands() { return operands; }
}
