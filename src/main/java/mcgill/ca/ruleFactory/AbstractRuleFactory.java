package mcgill.ca.ruleFactory;

import mcgill.ca.rule.Rules;

import java.util.Optional;

public abstract class AbstractRuleFactory
{

	public AbstractRuleFactory()
	{
	}

	public abstract Optional<Rules> CreateRules();
}
