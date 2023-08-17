package mcgill.ca.ruleEngine;

public class RuleEngineParameters
{
	private boolean skipOnFirstAppliedRule;
	private boolean skipOnPredefinedRule;
	private boolean skipOnNamingConventionRule;
	private boolean skipOnGrammarPatternRule;

	public boolean isSkipOnPredefinedRule()
	{
		return skipOnPredefinedRule;
	}

	public void setSkipOnPredefinedRule(boolean skipOnPredefinedRule)
	{
		this.skipOnPredefinedRule = skipOnPredefinedRule;
	}

	public boolean isSkipOnFirstAppliedRule()
	{
		return skipOnFirstAppliedRule;
	}

	public void setSkipOnFirstAppliedRule(boolean skipOnFirstAppliedRule)
	{
		this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
	}

	public boolean isSkipOnNamingConventionRule()
	{
		return skipOnNamingConventionRule;
	}

	public void setSkipOnNamingConventionRule(boolean skipOnNamingConventionRule)
	{
		this.skipOnNamingConventionRule = skipOnNamingConventionRule;
	}

	public boolean isSkipOnGrammarPatternRule()
	{
		return skipOnGrammarPatternRule;
	}

	public void setSkipOnGrammarPatternRule(boolean skipOnGrammarPatternRule)
	{
		this.skipOnGrammarPatternRule = skipOnGrammarPatternRule;
	}

	public void setSkipOnFirstAppliedRule()
	{
		skipOnFirstAppliedRule = true;
	}

}
