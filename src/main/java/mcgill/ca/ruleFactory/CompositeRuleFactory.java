package mcgill.ca.ruleFactory;

import mcgill.ca.rule.Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompositeRuleFactory extends AbstractRuleFactory
{
	private final List<AbstractRuleFactory> ruleFactories = new ArrayList<>();

	public CompositeRuleFactory()
	{
		ruleFactories.add(new BasicRuleFactory());
		ruleFactories.add(new CustomizedRuleFactory());
	}

	@Override public Optional<Rules> CreateRules()
	{
		Rules rules = new Rules();

		for (AbstractRuleFactory ruleFactory : ruleFactories)
		{
			if (ruleFactory.CreateRules().isPresent())
			{
				rules.register(ruleFactory.CreateRules());
			}
		}
		return Optional.of(rules);
	}
}
