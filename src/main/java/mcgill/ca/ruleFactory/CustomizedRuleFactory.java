package mcgill.ca.ruleFactory;

import mcgill.ca.Property;
import mcgill.ca.Setup;
import mcgill.ca.rule.Rules;
import mcgill.ca.support.reader.YAMLRuleReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * This class is used to create all the customized rules
 */
public class CustomizedRuleFactory extends AbstractRuleFactory
{
	YAMLRuleReader yamlRuleReader = new YAMLRuleReader();

	@Override public Optional<Rules> CreateRules()
	{
		try
		{
			Property property = Setup.loadProperty();
			return Optional.ofNullable(yamlRuleReader.read(new FileInputStream(property.getYAML_PATH())));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return Optional.empty();
	}
}
