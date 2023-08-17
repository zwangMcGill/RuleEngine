package mcgill.ca.rule.basic;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import mcgill.ca.Category;
import mcgill.ca.fact.Fact;
import mcgill.ca.rule.AbstractRule;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParameterBasicRule extends AbstractRule
{
	private final FocalMethodBasicRule focalMethodBasicRule = new FocalMethodBasicRule();

	public ParameterBasicRule(String name, String description, String label, int priority)
	{
		super(name, description, label, priority);
	}

	public ParameterBasicRule()
	{
		setName("ParameterBasicRule");
		setDescription("Predefined Parameter Rule to identify the parameters of the focal methods");
	}

	@Override public boolean evaluate(Fact fact)
	{
		return focalMethodBasicRule.evaluate(fact);
	}

	@Override public Map<Category, Set<String>> execute(Fact fact)
	{
		MethodDeclaration md = fact.getMd();
		List<MethodCallExpr> calls = fact.getMethodCalls();
		Set<String> parameters = new LinkedHashSet<>();
		Set<String> focalMethods = focalMethodBasicRule.execute(fact).get(Category.FOCAL_METHOD);

		assert !calls.isEmpty() : "No Method Call Found in " + md.getNameAsString();

		if (focalMethods.isEmpty())
			return fragmentMap;

		for (MethodCallExpr call : calls)
		{
			if (focalMethods.contains(call.getNameAsString()))
			{
				parameters.addAll(getAllParameters(call));
			}
		}

		fragmentMap.put(Category.PARAMETER, parameters);
		return fragmentMap;
	}

	private Set<String> getAllParameters(MethodCallExpr call)
	{
		Set<String> fragmentValue = new LinkedHashSet<>();
		assert call != null;
		for (int i = 0; i < call.getArguments().size(); i++)
		{
			Expression argument = call.getArguments().get(i);
			fragmentValue.add(call.resolve().getParam(i).getName());
		}
		return fragmentValue;
	}
}
