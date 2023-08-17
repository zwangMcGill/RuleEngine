package mcgill.ca.rule.basic;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import mcgill.ca.Category;
import mcgill.ca.fact.Fact;
import mcgill.ca.rule.AbstractRule;

import java.util.*;
import java.util.stream.Collectors;

import static mcgill.ca.Category.EXPECTED_RESULT;

/**
 * Basic rule for identifying EXPECTED_RESULT based on JUnit Assert Statements types
 */
public class ExpectedResultBasicRule extends AbstractRule
{
	public ExpectedResultBasicRule(String name, String description, String label, int priority)
	{
		super(name, description, label, priority);
	}

	public ExpectedResultBasicRule()
	{
		setName("ExpectedResultBasicRule");
	}

	@Override public boolean evaluate(Fact fact)
	{
		return !visit(fact).isEmpty();
	}

	@Override public Map<Category, Set<String>> execute(Fact fact)
	{
		Set<String> expectedRes = new LinkedHashSet<>();
		Set<MethodCallExpr> assertCalls = visit(fact);

		for (MethodCallExpr assertCall : assertCalls)
		{
			String assertName = assertCall.getNameAsString();
			if (getAssertionType(assertName).isPresent())
			{
				expectedRes.add(getAssertionType(assertName).get());
			}
		}

		fragmentMap.put(EXPECTED_RESULT, expectedRes);
		return fragmentMap;
	}

	private Optional<String> getAssertionType(String methodName)
	{

		Map<String, String> assertMap = new HashMap<>();
		assertMap.put("assertTrue", "ReturnTrue");
		assertMap.put("assertFalse", "ReturnFalse");
		assertMap.put("assertArrayEquals", "ReturnArrayEquals");
		assertMap.put("assertNotEquals", "ReturnNotEquals");
		assertMap.put("assertEquals", "ReturnEquals");
		assertMap.put("assertNotNull", "ReturnNotNull");
		assertMap.put("assertAll", "ReturnNoExceptions");
		assertMap.put("assertNotSame", "ReturnNotSame");
		assertMap.put("assertNull", "ReturnNull");
		assertMap.put("assertSame", "ReturnSame");
		assertMap.put("assertThrows", "ThrowsException");
		assertMap.put("assertTimeout", "ReturnTimeout");
		assertMap.put("fail", "Fail");

		return Optional.ofNullable(assertMap.get(methodName));

	}

	private Set<MethodCallExpr> visit(Fact fact)
	{
		MethodDeclaration md = fact.getMd();
		return md.findAll(MethodCallExpr.class).stream().filter(e -> e.getNameAsString().contains("assert"))
				.collect(Collectors.toSet());
	}
}
