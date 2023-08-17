package mcgill.ca.rule.basic;

import mcgill.ca.Category;
import mcgill.ca.fact.Fact;
import mcgill.ca.namingConvetions.NamingConventions;
import mcgill.ca.rule.AbstractRule;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NamingConventionBasicRule extends AbstractRule
{
	private final NamingConventions namingConventions = new NamingConventions();

	public NamingConventionBasicRule(String name, String description, String label, int priority)
	{
		super(name, description, label, priority);
	}

	public NamingConventionBasicRule()
	{
		setName("NamingConventionBasicRule");
		setDescription(
				"Predefined Naming Convention Rule to identify the naming conventions containing special keyword(s)");
	}

	@Override public boolean evaluate(Fact fact)
	{
		for (Map.Entry<String, String> entry : namingConventions.getNamingConvention().entrySet())
		{
			Pattern pattern = Pattern.compile(entry.getValue());
			Matcher valueMatcher = pattern.matcher(name);
			Matcher keyMatcher = pattern.matcher(entry.getKey());
			if (valueMatcher.find() && keyMatcher.find())
			{
				return true;
			}
		}
		return false;
	}

	public Map<Category, Set<String>> execute(Fact fact)
	{

		for (Map.Entry<String, String> entry : namingConventions.getNamingConvention().entrySet())
		{
			Pattern pattern = Pattern.compile(entry.getValue());
			Matcher valueMatcher = pattern.matcher(name);
			Matcher keyMatcher = pattern.matcher(entry.getKey());

			for (int i = 0; i < keyMatcher.groupCount(); i++)
			{
				Optional<Category> category = Category.fromString(keyMatcher.group(i + 1));

				if (category.isPresent())
				{
					fragmentMap.computeIfAbsent(category.get(), k -> new HashSet<>());
					fragmentMap.get(keyMatcher.group(i + 1)).add(valueMatcher.group(i + 1));
				}
			}
		}
		return fragmentMap;
	}

}
