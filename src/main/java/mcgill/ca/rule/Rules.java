package mcgill.ca.rule;

import java.util.*;

public class Rules implements Iterable<Rule>
{
	private final Set<Rule> rules;

	public Rules()
	{
		this.rules = new LinkedHashSet<>();
	}

	public void register(Rule... rules)
	{
		Objects.requireNonNull(rules);
		for (Rule rule : rules)
		{
			Objects.requireNonNull(rule);
			this.rules.add(rule);
		}
	}

	public void register(Optional<Rules> rules)
	{
		Objects.requireNonNull(rules);

		if (rules.isPresent())
		{
			for (Rule rule : rules.get())
			{
				Objects.requireNonNull(rule);
				this.rules.add(rule);
			}
		}
	}

	/**
	 * Unregister one or more rules.
	 *
	 * @param rules to unregister, must not be null
	 */
	public void unregister(Rule... rules)
	{
		Objects.requireNonNull(rules);
		for (Rule rule : rules)
		{
			Objects.requireNonNull(rule);
			this.rules.remove(rule);
		}
	}

	/**
	 * Unregister a rule by name.
	 *
	 * @param ruleName name of the rule to unregister, must not be null
	 */
	public void unregister(final String ruleName)
	{
		Objects.requireNonNull(ruleName);
		Rule rule = findRuleByName(ruleName);
		if (rule != null)
		{
			unregister(rule);
		}
	}

	public boolean isEmpty()
	{
		return rules.isEmpty();
	}

	private Rule findRuleByName(String ruleName)
	{
		return rules.stream().filter(rule -> rule.getName().equalsIgnoreCase(ruleName)).findFirst().orElse(null);
	}

	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override public Iterator<Rule> iterator()
	{
		return rules.iterator();
	}

	@Override public String toString()
	{
		return "Rules{" + "rules=" + rules + '}';
	}
}
