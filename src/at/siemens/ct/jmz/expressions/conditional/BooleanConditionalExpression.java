package at.siemens.ct.jmz.expressions.conditional;

import at.siemens.ct.jmz.expressions.Expression;
import at.siemens.ct.jmz.expressions.bool.BooleanExpression;

public class BooleanConditionalExpression extends ConditionalExpression<Boolean> {

  public BooleanConditionalExpression(BooleanExpression condition, Expression<Boolean> thenBranch,
      Expression<Boolean> elseBranch) {
    super(condition, thenBranch, elseBranch);
  }

}