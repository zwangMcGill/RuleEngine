package mcgill.ca.rule.customize;

import mcgill.ca.Category;
import mcgill.ca.fact.Fact;
import mcgill.ca.rule.AbstractRule;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NamingConventionRule extends AbstractRule
{

	private final String convention;
	private final String REGEX;

	public NamingConventionRule(String name, String description, String label, int priority, String convention,
			String REGEX)
	{
		this.name = name;
		this.description = description;
		this.label = "NamingConventionRule";
		this.priority = priority;
		this.convention = convention;
		this.REGEX = REGEX;
	}

	@Override public boolean evaluate(Fact fact)
	{
		Pattern pattern = Pattern.compile(REGEX);
		Matcher matcher = pattern.matcher(fact.getMd().getNameAsString());
		Matcher templateMatcher = pattern.matcher(convention);
		return matcher.find() && templateMatcher.find();
	}

	@Override public Map<Category, Set<String>> execute(Fact fact)
	{
		Pattern pattern = Pattern.compile(REGEX);
		Matcher matcher = pattern.matcher(fact.getMd().getNameAsString());
		Matcher templateMatcher = pattern.matcher(convention);

		// method name matches the regex completely.
		if (matcher.groupCount() == 1)
		{
			Optional<Category> category = Category.fromString(convention.replace("test", ""));

			if (category.isPresent())
			{
				fragmentMap.computeIfAbsent(category.get(), k -> new TreeSet<>());
				fragmentMap.get(category.get()).add(fact.toString().replace("test", ""));
			}
			return fragmentMap;
		}

		for (int i = 0; i < matcher.groupCount(); i++)
		{
			if (!matcher.group(i + 1).equals(templateMatcher.group(i + 1)))
			{
				Optional<Category> category = Category.fromString(templateMatcher.group(i + 1));
				if (category.isPresent())
				{
					fragmentMap.computeIfAbsent(category.get(), k -> new TreeSet<>());
					fragmentMap.get(templateMatcher.group(i + 1)).add(matcher.group(i + 1));
				}
			}
		}
		return fragmentMap;
	}
}
