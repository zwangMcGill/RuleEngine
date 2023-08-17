package mcgill.ca.ruleEngine;

import mcgill.ca.fact.Fact;
import mcgill.ca.rule.Rule;
import mcgill.ca.rule.customize.GrammarPatternRule;
import mcgill.ca.rule.customize.NamingConventionRule;

public class DefaultRuleEngine extends AbstractRuleEngine
{
	public DefaultRuleEngine()
	{
		super();
		this.ruleEngineParameters.setSkipOnFirstAppliedRule(false);
		this.ruleEngineParameters.setSkipOnPredefinedRule(false);
		this.ruleEngineParameters.setSkipOnGrammarPatternRule(true);
		this.ruleEngineParameters.setSkipOnNamingConventionRule(true);
	}

	public DefaultRuleEngine(RuleEngineParameters ruleEngineParameters)
	{
		super(ruleEngineParameters);
	}

	/**
	 * check if the rule is eligible to run with user/default rule engine parameters
	 *
	 * @param rule
	 * @param fact
	 * @return
	 */
	protected boolean check(Rule rule, Fact fact)
	{

		if (ruleEngineParameters.isSkipOnGrammarPatternRule())
		{
			// if a grammar rule has been applied then skip the current grammar rule.
			if (!isFirstApplied(fact, rule, GrammarPatternRule.class))
				return false;
		}

		if (ruleEngineParameters.isSkipOnNamingConventionRule())
		{

			return isFirstApplied(fact, rule, NamingConventionRule.class);
		}

		return true;
	}

	/**
	 * check if the type of rule is first applied or not
	 *
	 * @param fact
	 * @param rule
	 * @param ruleClass
	 * @return
	 */
	private boolean isFirstApplied(Fact fact, Rule rule, Class<?> ruleClass)
	{
		return ruleClass != rule.getClass() || getExecutedRuleSet(fact.toString()).stream()
				.noneMatch(r -> r.getClass() == ruleClass);
	}
}
