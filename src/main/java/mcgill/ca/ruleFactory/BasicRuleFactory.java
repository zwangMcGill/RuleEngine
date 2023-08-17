package mcgill.ca.ruleFactory;

import mcgill.ca.rule.Rules;
import mcgill.ca.rule.basic.*;

import java.util.Optional;

public class BasicRuleFactory extends AbstractRuleFactory
{

	/**
	 * Add all predefined basic rules
	 *
	 * @return
	 */
	@Override public Optional<Rules> CreateRules()
	{
		Rules rules = new Rules();
		rules.register(new FocalMethodBasicRule());
		rules.register(new StateBasicRule());
		rules.register(new GlobalVariableBasicRule());
		rules.register(new LocalVariableBasicRule());
		rules.register(new ParameterBasicRule());
		rules.register(new AssertThrowExceptionRule());
		rules.register(new TryCatchExceptionRule());
		rules.register(new ExpectedResultBasicRule());
		rules.register(new NamingConventionBasicRule());
		return Optional.of(rules);
	}
}
