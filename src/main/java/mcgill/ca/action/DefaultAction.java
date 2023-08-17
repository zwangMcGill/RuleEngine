package mcgill.ca.action;

import mcgill.ca.Category;
import mcgill.ca.fact.Fact;

import java.util.*;

public class DefaultAction implements Action
{
	private final Category fragment;
	private final String description;

	public DefaultAction(String fragment, String description)
	{
		this.fragment = Category.fromString(fragment).orElseThrow();
		this.description = description;
	}

	@Override public Map<Category, Set<String>> execute(Fact fact)
	{

		assert fragment != null : "No Valid Fragment";

		Map<Category, Set<String>> map = new TreeMap<>();
		map.put(fragment, new LinkedHashSet<>(Collections.singleton(description)));
		return map;
	}
}
