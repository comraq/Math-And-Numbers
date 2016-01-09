package adam.mathandnumbers;

import java.util.ArrayList;
import java.util.List;

import adam.mathandnumbers.QuestionBank.QuestionType;
import adam.mathandnumbers.QuestionBank.QuestionOptions;

/**
 * Created by adam on 2016-01-09.
 */
public class Question {
  private QuestionType type;
  private List<QuestionOptions> options;

  public Question (QuestionType type) {
    this.type = type;
    options = new ArrayList<QuestionOptions>();
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
}
