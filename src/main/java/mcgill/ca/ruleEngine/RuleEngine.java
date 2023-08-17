package mcgill.ca.ruleEngine;

import mcgill.ca.fact.Facts;
import mcgill.ca.rule.Rules;

public interface RuleEngine
{
	boolean runRules(Rules rules, Facts facts);
}
