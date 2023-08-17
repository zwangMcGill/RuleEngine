package mcgill.ca;

import mcgill.ca.fact.Facts;
import mcgill.ca.rule.Rules;
import mcgill.ca.ruleEngine.DefaultRuleEngine;
import mcgill.ca.ruleFactory.AbstractRuleFactory;
import mcgill.ca.ruleFactory.CompositeRuleFactory;

import java.io.IOException;

public class RuleEngineFacade
{

	public static void main(String[] args) throws IOException
	{
		RuleEngineFacade ruleEngineFacade = new RuleEngineFacade();
		ruleEngineFacade.build();
	}

	public boolean build() throws IOException
	{
		DefaultRuleEngine engine = new DefaultRuleEngine();
		AbstractRuleFactory compositeRuleFactory = new CompositeRuleFactory();
		Facts facts = new Facts();
		Rules rules = compositeRuleFactory.CreateRules().get();

		return engine.runRules(rules, facts);
	}
}
