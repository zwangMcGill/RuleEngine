package mcgill.ca.rule.basic;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import mcgill.ca.Category;
import mcgill.ca.fact.Fact;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static mcgill.ca.Category.EXCEPTION;

public class AssertThrowExceptionRule extends ExceptionBasicRule
{
	public AssertThrowExceptionRule(String name, String description, String label, int priority)
	{
		super(name, description, label, priority);
	}

	public AssertThrowExceptionRule()
	{

		setName("AssertThrowExceptionRule");
		setDescription("Catch the Exception thrown using AssertThrow() from JUnit5");
	}

	@Override public boolean evaluate(Fact fact)
	{
		MethodDeclaration md = fact.getMd();
		return !md.findAll(MethodDeclaration.class).stream().filter(e -> e.getNameAsString().contains("assertThrow"))
				.toList().isEmpty();
	}

	@Override public Map<Category, Set<String>> execute(Fact fact)
	{
		MethodDeclaration md = fact.getMd();
		Set<String> exceptions = new LinkedHashSet<>();
		md.accept(new MethodCallVisitor(), exceptions);
		fragmentMap.put(EXCEPTION, exceptions);
		return fragmentMap;
	}

	static class MethodCallVisitor extends VoidVisitorAdapter<Set<String>>
	{
		@Override public void visit(MethodCallExpr n, Set<String> arg)
		{
			// Found a method call
			if (Objects.equals(n.getNameAsString(), "assertThrows"))
			{
				String exception = n.getArgument(0).toString();
				arg.add(exception.replace(".class", ""));
			}

			super.visit(n, arg);
		}
	}
}
