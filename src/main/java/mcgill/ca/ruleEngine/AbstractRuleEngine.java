package mcgill.ca.ruleEngine;

import mcgill.ca.Category;
import mcgill.ca.fact.Fact;
import mcgill.ca.fact.Facts;
import mcgill.ca.rule.Rule;
import mcgill.ca.rule.Rules;

import java.util.*;

public abstract class AbstractRuleEngine implements RuleEngine
{
	protected final Map<String, Set<Rule>> executedRulesMap = new LinkedHashMap<>();
	protected final RuleEngineParameters ruleEngineParameters;

	public AbstractRuleEngine()
	{
		ruleEngineParameters = new RuleEngineParameters();
	}

	public AbstractRuleEngine(RuleEngineParameters ruleEngineParameters)
	{
		this.ruleEngineParameters = ruleEngineParameters;
	}

	@Override public boolean runRules(Rules rules, Facts facts)
	{
		assert facts != null;
		if (rules.isEmpty())
		{
			return false;
		}

		Set<Fact> set = facts.getFacts();

		assert !set.isEmpty() : "No Fact is Created";

		set.forEach(fact -> runRules(rules, fact));

		return true;
	}

	public final boolean runRules(Rules rules, Fact fact)
	{
		assert rules != null;

		if (rules.isEmpty())
		{
			return false;
		}

		HashMap<Category, Set<String>> ExecutedRes = new HashMap<>();

		for (Rule rule : rules)
		{
			if (!check(rule, fact))
			{
				System.out.println(rule.getName() + ": skipped");
				continue;
			}
			try
			{
				boolean toFire = rule.evaluate(fact);
				System.out.println(rule.getName() + ": " + toFire);
				if (toFire)
				{
					Map<Category, Set<String>> val = rule.execute(fact);
					ExecutedRes.putAll(val);
					getExecutedRuleSet(fact.toString()).add(rule);
				}
			}
			catch (Exception e)
			{
				e.getStackTrace();
			}
		}
		System.out.println("Execute Result: " + ExecutedRes);
		return true;
	}

	protected Set<Rule> getExecutedRuleSet(String factName)
	{
		executedRulesMap.computeIfAbsent(factName, k -> new LinkedHashSet<Rule>());

		return executedRulesMap.get(factName);
	}

	protected abstract boolean check(Rule rule, Fact fact);

}
