package mcgill.ca.rule;

import mcgill.ca.Category;
import mcgill.ca.fact.Fact;

import java.util.Map;
import java.util.Set;

public interface Rule extends Comparable<Rule>
{

	String DEFAULT_NAME = "rule";

	String DEFAULT_DESCRIPTION = "description";

	String DEFAULT_LABEL = "BasicRule";

	int DEFAULT_PRIORITY = Integer.MAX_VALUE - 1;

	default String getName()
	{
		return DEFAULT_NAME;
	}

	default String getDescription()
	{
		return DEFAULT_DESCRIPTION;
	}

	default int getPriority()
	{
		return DEFAULT_PRIORITY;
	}

	boolean evaluate(Fact fact);

	Map<Category, Set<String>> execute(Fact fact);

	int compareTo(Rule rule);
}
