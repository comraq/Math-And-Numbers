package adam.mathandnumbers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
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

  private final QuestionType type;
  private Set<QuestionOptions> options;
  private ArrayList<Integer> operands;
  private int answer;

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
    updateAnswer();
    return this;
  }

  private void genAdditionQuestion() {
    int remain = (int) Math.pow(10, numDigits);
    int acc = 0;
    for (int i = 0; i < numOperands; ++i) {
      operands.add(0, getAddOperand(remain, acc));
      remain -= operands.get(0);
      acc += operands.get(0);
    }
  }

  private int getAddOperand(int remain, int acc) {
    if (remain < 1)
      return 0;

    if (options.contains(QuestionOptions.CARRY))
      return ThreadLocalRandom.current().nextInt(0, remain);

    int result = 0;
    for (int i = 1; i <= numDigits; ++i) {
      int digit = (acc % ((int) Math.pow(10, i))) / ((int) Math.pow(10, i - 1));
      int nextDigit = ThreadLocalRandom.current().nextInt(0, 10 - digit);
      result += nextDigit * (int) Math.pow(10, i - 1);
    }
    return result;
  }

  private void genSubtractionQuestion() {
    operands.add(ThreadLocalRandom.current().nextInt(0, (int) Math.pow(10, numDigits)));
    int remain = operands.get(0);
    for (int i = 1; i < numOperands; ++i) {
      operands.add(0, getSubOperand(remain));
      remain -= operands.get(0);
    }
  }

  private int getSubOperand(int remain) {
    if (remain < 1)
      return 0;

    if (options.contains(QuestionOptions.CARRY))
      return ThreadLocalRandom.current().nextInt(0, remain);

    int result = 0;
    for (int i = 1; i <= numDigits; ++i) {
      int digit = (remain % ((int) Math.pow(10, i))) / ((int) Math.pow(10, i - 1));
      int nextDigit = (digit == 0)? 0 : ThreadLocalRandom.current().nextInt(0, digit);
      result += nextDigit * (int) Math.pow(10, i - 1);
    }
    return result;
  }

  private void genMultiplicationQuestion() {
    int total = (int) Math.pow(10, numDigits);
    //TODO: Generate operands as restricted by the specified options;
    for (int i = 0; i < numOperands; ++i) {
      operands.add(0, ThreadLocalRandom.current().nextInt(0, total));
      if (operands.get(0) != 0)
        total /= operands.get(i);

      total = (total < 10)? 10 : total;
    }
  }

  private void genDivisionQuestion() {
    operands.add(ThreadLocalRandom.current().nextInt(1, (int) Math.pow(10, numDigits)));
    int remain = operands.get(0);
    for (int i = 1; i < numOperands; ++i) {
      if (remain < 2)
        remain = 2;
      operands.add(0, ThreadLocalRandom.current().nextInt(1, remain));
      remain /= operands.get(0);
    }
    adjustForRemainder();
  }

  //Check whether the remainder option is set and adjust the operands accordingly
  private void adjustForRemainder() {
    if (options.contains(QuestionOptions.REMAINDER))
      return;

    int dividend = operands.get(1), divisor = operands.get(0);
    int remainder = dividend % divisor;
    boolean increase = new Random().nextBoolean();
    if (increase && (divisor - remainder + dividend) < (int)Math.pow(10, numDigits))
      operands.set(1, divisor - remainder + dividend);
    else
      operands.set(1, dividend - remainder);
  }

  public QuestionType getType() { return type; }

  public boolean checkOptions(QuestionOptions option) {
    if (options.contains(option)) {
      return true;
    } else {
      return false;
    }
  }

  public void setNumDigits(int maxDigits) { this.numDigits = maxDigits; }

  public void setNumOperands(int maxOperands) { this.numOperands = maxOperands; }

  public void setOperands(ArrayList<Integer> operands) {
    this.operands = operands;
    updateAnswer();
  }

  private void updateAnswer() {
    switch(type) {
      case ADDITION:
        answer = 0;
        for (Integer operand: operands)
          answer += operand;
        break;
      case SUBTRACTION:
        answer = operands.get(operands.size() - 1);
        for (int i = 0; i < operands.size() - 1; ++i)
          answer -= operands.get(i);
        break;
      case MULTIPLICATION:
        answer = 1;
        for (Integer operand: operands)
          answer *= operand;
        break;
      case DIVISION:
        answer = operands.get(operands.size() - 1);
        for (int i = 0; i < operands.size() - 1; ++i)
          answer /= operands.get(i);
        break;
      default:
        //Do nothing!
    }
  }

  public int getAnswer() { return answer; }

  public void includeOption(QuestionOptions option) { options.add(option); }

  public int getNumOperands() { return numOperands; }

  public int getNumDigits() { return numDigits; }

  public ArrayList<Integer> getOperands() { return operands; }
}
