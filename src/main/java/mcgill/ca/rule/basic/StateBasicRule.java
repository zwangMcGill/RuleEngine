package mcgill.ca.rule.basic;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import mcgill.ca.Category;
import mcgill.ca.fact.Fact;
import mcgill.ca.rule.AbstractRule;

import java.util.*;

import static mcgill.ca.Category.STATE;

public class StateBasicRule extends AbstractRule
{
	private final FocalMethodBasicRule focalMethodBasicRule = new FocalMethodBasicRule();

	public StateBasicRule()
	{
		setName("StateBasicRule");
		setDescription("Predefined State Rule to identify the state of the parameters of the focal methods");
	}

	@Override public boolean evaluate(Fact fact)
	{
		return focalMethodBasicRule.evaluate(fact);
	}

	@Override public Map<Category, Set<String>> execute(Fact fact)
	{
		MethodDeclaration md = fact.getMd();
		List<MethodCallExpr> calls = fact.getMethodCalls();
		Set<String> states = new LinkedHashSet<>();
		Set<String> focalMethods = focalMethodBasicRule.execute(fact).get(Category.FOCAL_METHOD);

		assert !calls.isEmpty() : "No Method Call Found in " + md.getNameAsString();

		for (MethodCallExpr call : calls)
		{
			if (focalMethods.contains(call.getNameAsString()))
			{
				states.addAll(getMethodState(md, call));
			}
		}

		fragmentMap.put(STATE, states);
		return fragmentMap;
	}

	public Set<String> getMethodState(MethodDeclaration md, MethodCallExpr mc)
	{
		Set<String> set = new LinkedHashSet<>();
		for (int i = 0; i < mc.getArguments().size(); i++)
		{
			Expression argument = mc.getArguments().get(i);
			set.addAll(checkLiteralExprType(md, argument));

			if (argument.isNameExpr())
			{

				String variableName = ((NameExpr) mc.getArguments().get(i)).getNameAsString();

				List<VariableDeclarator> variableValue = md.findAll(VariableDeclarator.class,
						vd -> vd.getNameAsString().equals(variableName));

				// Print the value of the variable, if found
				if (variableValue.size() != 0)
				{
					List<AssignExpr> assignExpr = md.findAll(AssignExpr.class).stream()
							.filter(a -> a.getTarget().equals(variableName)).toList();

					if (assignExpr.isEmpty())
					{
						Optional<Expression> expression = variableValue.get(0).getInitializer();
						expression.ifPresent(value -> set.addAll(checkLiteralExprType(md, value)));
					}
					else
					{
						Expression expression = assignExpr.get(assignExpr.size() - 1).getValue();
						set.addAll(checkLiteralExprType(md, expression));
					}
				}
			}
		}
		return set;
	}

	private Set<String> checkLiteralExprType(MethodDeclaration md, Expression argument)
	{
		Set<String> set = new LinkedHashSet<>();

		if (argument.isStringLiteralExpr())
		{
			handleStringLiteralExpr(argument, set);
		}
		else if (argument.isCharLiteralExpr())
		{
			handleCharLiteralExpr(argument, set);
		}
		else if (argument.isIntegerLiteralExpr())
		{
			handleIntegerLiteralExpr(argument, set);
		}
		else if (argument.isDoubleLiteralExpr())
		{
			handleDoubleLiteralExpr(argument, set);
		}
		else if (argument.isLongLiteralExpr())
		{
			handleFloatLiteralExpr(argument, set);
		}
		else if (argument.isBooleanLiteralExpr())
		{
			handleBooleanLiteralExpr(argument, set);
		}
		else if (argument.isObjectCreationExpr())
		{
			handleObjectCreationExpr(md, argument, set);
		}
		else if (argument.isArrayInitializerExpr())
		{
			handleArrayInitializerExpr(argument, set);
		}
		else if (argument.isArrayCreationExpr())
		{
			handleArrayCreationExpr(argument, set);
		}
		else if (argument.isNullLiteralExpr())
		{
			set.add("null");
		}
		else if (argument.isMethodCallExpr())
		{
			System.out.println("Unable to recognize so far.");
		}

		return set;
	}

	private void handleStringLiteralExpr(Expression argument, Set<String> set)
	{
		String value = argument.asStringLiteralExpr().asString();
		if (value == null)
		{
			set.add("nullString");
		}
		else
		{
			set.add("nonNullString");
		}
	}

	private void handleCharLiteralExpr(Expression argument, Set<String> set)
	{
		char value = argument.asCharLiteralExpr().asChar();
		if (value == '\0')
		{
			set.add("nullChar");
		}
		else
		{
			set.add("nonNullChar");
		}
	}

	private void handleIntegerLiteralExpr(Expression argument, Set<String> set)
	{
		int value = argument.asIntegerLiteralExpr().asInt();
		if (value == 0)
		{
			set.add("zero");
			set.add("0");
		}
		else if (value > 0)
		{
			set.add("positiveInteger");
		}
		else
		{
			set.add("negativeInteger");
		}
	}

	private void handleDoubleLiteralExpr(Expression argument, Set<String> set)
	{
		double value = argument.asDoubleLiteralExpr().asDouble();
		if (value == 0.0)
		{
			set.add("0_0");
		}
		else if (value > 0.0)
		{
			set.add("positiveDouble");
		}
		else
		{
			set.add("negativeDouble");
		}
	}

	private void handleFloatLiteralExpr(Expression argument, Set<String> set)
	{
		long value = argument.asLongLiteralExpr().asLong();
		if (value == 0L)
		{
			set.add("0L");
		}
		else if (value > 0L)
		{
			set.add("positiveLong");
		}
		else
		{
			set.add("negativeLong");
		}
	}

	private void handleBooleanLiteralExpr(Expression argument, Set<String> set)
	{
		boolean value = argument.asBooleanLiteralExpr().getValue();
		if (value)
		{
			set.add("true");
		}
		else
		{
			set.add("false");
		}
	}

	private void handleObjectCreationExpr(MethodDeclaration md, Expression argument, Set<String> set)
	{
		String expr = argument.toString();

		if (expr.contains("List"))
		{
			int size = md.findAll(MethodCallExpr.class).stream().filter(e -> e.getNameAsString().equals("add")).toList()
					.size();
			if (size == 0)
			{
				set.add("EmptyList");
			}
			else
			{
				set.add("NonEmptyList");
			}
		}
		else if (expr.contains("Map"))
		{
			int size = md.findAll(MethodCallExpr.class).stream().filter(e -> e.getNameAsString().equals("put")).toList()
					.size();
			if (size == 0)
			{
				set.add("EmptyMap");
			}
			else
			{
				set.add("NonEmptyMap");
			}
		}
		else if (expr.contains("Queue"))
		{
			int size = md.findAll(MethodCallExpr.class).stream().filter(e -> e.getNameAsString().equals("push"))
					.toList().size();
			if (size == 0)
			{
				set.add("EmptyQueue");
			}
			else
			{
				set.add("NonEmptyQueue");
			}
		}
	}

	private void handleArrayInitializerExpr(Expression argument, Set<String> set)
	{
		ArrayInitializerExpr initializerExpr = argument.asArrayInitializerExpr();
		String[] values = initializerExpr.getValues().stream().map(Node::toString).toArray(String[]::new);
		if (values.length == 0)
		{
			set.add("EmptyArray");
		}
		else
		{
			set.add("ArraySizeIs" + values.length);
		}
	}

	private void handleArrayCreationExpr(Expression argument, Set<String> set)
	{
		String arrayCreationExpr = argument.toString();
		int startIndex = arrayCreationExpr.indexOf('[') + 1;
		int endIndex = arrayCreationExpr.indexOf(']');
		String sizeStr = arrayCreationExpr.substring(startIndex, endIndex);
		set.add("ArraySizeIs" + sizeStr);
	}

}
