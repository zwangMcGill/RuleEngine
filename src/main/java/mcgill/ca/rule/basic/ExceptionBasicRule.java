package mcgill.ca.rule.basic;

import mcgill.ca.Category;
import mcgill.ca.fact.Fact;
import mcgill.ca.rule.AbstractRule;

import java.util.Map;
import java.util.Set;

public class ExceptionBasicRule extends AbstractRule
{
	public ExceptionBasicRule(String name, String description, String label, int priority)
	{
		super(name, description, label, priority);
	}

	public ExceptionBasicRule()
	{
		super();
	}

	@Override public boolean evaluate(Fact fact)
	{
		return false;
	}

	@Override public Map<Category, Set<String>> execute(Fact fact)
	{
		return fragmentMap;
	}
}
