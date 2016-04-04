package at.siemens.ct.jmz.expressions.array;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import at.siemens.ct.jmz.elements.IntArrayConstant;
import at.siemens.ct.jmz.elements.IntSet;
import at.siemens.ct.jmz.elements.PseudoOptionalIntSet;
import at.siemens.ct.jmz.expressions.comprehension.ListComprehension;
import at.siemens.ct.jmz.expressions.set.IntSetExpression;

/**
 * TODO: Overlaps with {@link ListComprehension} and with {@link IntArrayConstant}. Wanted: a beautiful design
 * 
 * @author z003ft4a (Richard Taupe)
 *
 */
public class IntExplicitList implements IntArrayExpression {

  public static final String DEFAULT_NULL = "<>";
  public static final char LEFT_BRACKET = '[';
  public static final char RIGHT_BRACKET = ']';

  private List<? extends IntSetExpression> range;
  private IntSet type;
  private Collection<Integer> values;
  private String nullElement = DEFAULT_NULL;

  public IntExplicitList(List<? extends IntSetExpression> range, Collection<Integer> values) {
    this(range, IntSet.deriveRange(values), values);
  }

  public IntExplicitList(List<? extends IntSetExpression> range, IntSet type,
      Collection<Integer> values) {
    this.range = range;
    this.type = type;
    this.values = values;
    initNullElement();
  }

  private void initNullElement() {
    if (type instanceof PseudoOptionalIntSet) {
      nullElement = ((PseudoOptionalIntSet) type).getNullElement().use();
    }
  }

  @Override
  public List<? extends IntSetExpression> getRange() {
    return Collections.unmodifiableList(range);
  }

  @Override
  public String use() {
    return valuesToString();
  }

  private String valuesToString() {
    Function<? super Integer, ? extends String> intOrNull =
        i -> (i == null ? nullElement : i.toString());
    return LEFT_BRACKET + values.stream().map(intOrNull).collect(Collectors.joining(", "))
        + RIGHT_BRACKET;
  }
}
