package mcgill.ca.condition;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import mcgill.ca.fact.Fact;

import java.util.List;
import java.util.Optional;

/**
 * Check if the value is within the specified range
 *
 * @param <T>
 */
public class RangeCheckCondition<T extends Comparable> implements Condition
{
	private final String object;
	private final int position;
	private final T lowBoundInclusive;
	private final T hiBoundInclusive;

	public RangeCheckCondition(String object, int position, T lowBoundInclusive, T hiBoundInclusive)
	{
		this.object = object;
		this.position = position;
		this.lowBoundInclusive = lowBoundInclusive;
		this.hiBoundInclusive = hiBoundInclusive;
	}

	@Override public boolean evaluate(Fact fact)
	{
		MethodDeclaration md = fact.getMd();
		List<VariableDeclarator> variables = md.findAll(VariableDeclarator.class);

		Optional<VariableDeclarator> declaredVariable = variables.stream()
				.filter(variable -> variable.getNameAsString().equals(object)).findFirst();

		if (declaredVariable.isPresent())
		{
			Optional<Expression> expression = declaredVariable.get().getInitializer();

			if (expression.isPresent())
			{
				if (expression.get().isObjectCreationExpr())
				{
					ObjectCreationExpr objectCreationExpr = expression.get().asObjectCreationExpr();
					Expression argument = objectCreationExpr.getArgument(position);
					return check(argument);
				}
			}
		}
		return false;
	}

	public boolean check(Expression argument)
	{
		if (argument.isIntegerLiteralExpr())
		{
			return argument.asIntegerLiteralExpr().asInt() <= (int) hiBoundInclusive
					&& argument.asIntegerLiteralExpr().asInt() >= (int) lowBoundInclusive;
		}

		if (argument.isDoubleLiteralExpr())
		{
			return argument.asDoubleLiteralExpr().asDouble() <= (double) hiBoundInclusive
					&& argument.asDoubleLiteralExpr().asDouble() >= (double) lowBoundInclusive;
		}

		if (argument.isLongLiteralExpr())
		{
			return argument.asLongLiteralExpr().asLong() <= (long) hiBoundInclusive
					&& argument.asLongLiteralExpr().asLong() >= (long) lowBoundInclusive;
		}

		return false;
	}

}
