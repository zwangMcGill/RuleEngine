package mcgill.ca.action;

import mcgill.ca.Category;
import mcgill.ca.fact.Fact;

import java.util.Map;
import java.util.Set;

public interface Action
{
	Map<Category, Set<String>> execute(Fact fact);
}
