package mcgill.ca.rule.customize;

import mcgill.ca.Category;
import mcgill.ca.action.DefaultAction;
import mcgill.ca.condition.RangeCheckCondition;
import mcgill.ca.fact.Fact;
import mcgill.ca.rule.AbstractRule;

import java.util.Map;
import java.util.Set;

public class RangeCheckRule<T extends Comparable> extends AbstractRule
{

	private final RangeCheckCondition<T> condition;
	private final DefaultAction action;

	public RangeCheckRule(RangeCheckCondition<T> condition, DefaultAction action)
	{
		this.label = "RangeCheckRule";
		this.condition = condition;
		this.action = action;
	}

	public RangeCheckRule(String name, String description, String label, int priority, RangeCheckCondition<T> condition,
			DefaultAction action)
	{
		this.name = name;
		this.priority = priority;
		this.label = label;
		this.description = description;
		this.condition = condition;
		this.action = action;
	}

	public boolean evaluate(Fact fact)
	{
		return condition.evaluate(fact);
	}

	public Map<Category, Set<String>> execute(Fact fact)
	{
		return action.execute(fact);
	}
}
