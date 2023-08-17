package mcgill.ca.rule;

import mcgill.ca.Category;
import mcgill.ca.fact.Fact;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractRule implements Rule
{
	protected String name;

	protected String description;

	protected String label;

	protected int priority;

	protected LinkedHashMap<Category, Set<String>> fragmentMap = new LinkedHashMap<>();

	public AbstractRule()
	{
	}

	public AbstractRule(final String name, final String description, final String label, final int priority)
	{
		this.name = name;
		this.description = description;
		this.label = label;
		this.priority = priority;
	}

	@Override public abstract boolean evaluate(Fact fact);

	@Override public abstract Map<Category, Set<String>> execute(Fact fact);

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(final String label)
	{
		this.label = label;
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(final int priority)
	{
		this.priority = priority;
	}

	@Override public String toString()
	{
		return name;
	}

	@Override public int compareTo(final Rule rule)
	{
		if (getPriority() < rule.getPriority())
		{
			return -1;
		}
		else if (getPriority() > rule.getPriority())
		{
			return 1;
		}
		else
		{
			return getName().compareTo(rule.getName());
		}
	}
}
