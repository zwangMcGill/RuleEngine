package mcgill.ca.support.reader;

import mcgill.ca.action.DefaultAction;
import mcgill.ca.condition.RangeCheckCondition;
import mcgill.ca.rule.Rule;
import mcgill.ca.rule.Rules;
import mcgill.ca.rule.customize.GrammarPatternRule;
import mcgill.ca.rule.customize.NamingConventionRule;
import mcgill.ca.rule.customize.RangeCheckRule;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Read and create Rules from YAML file
 */
public class YAMLRuleReader
{
	private final Yaml yaml;

	public YAMLRuleReader()
	{
		this.yaml = new Yaml();
	}

	public Rules read(FileInputStream path)
	{
		Rules rules = new Rules();
		// need to define a static global path for yaml file
		Iterable<Map<String, Object>> ruleList = loadRules(path);

		for (Map<String, Object> rule : ruleList)
		{
			rules.register(createRules(rule));
		}

		return rules;
	}

	private Iterable<Map<String, Object>> loadRules(InputStream inputStream)
	{
		List<Map<String, Object>> rulesList = new ArrayList<>();
		Iterable<Object> rules = yaml.loadAll(inputStream);

		rules.forEach(rule -> {
			rulesList.add((Map<String, Object>) rule);
		});

		return rulesList;
	}

	private Rule createRules(Map<String, Object> map)
	{
		String label = (String) map.get("label");
		if (label == null || label.isEmpty())
			throw new IllegalArgumentException("Label is not specified");

		switch (label)
		{
		case "NamingConventionRule":
			String name = (String) map.get("name");
			String description = (String) map.get("description");
			int priority = (int) map.get("priority");
			String convention = (String) map.get("convention");
			String regex = (String) map.get("REGEX");
			return new NamingConventionRule(name, description, label, priority, convention, regex);

		case "GrammarPatternRule":
			name = (String) map.get("name");
			description = (String) map.get("description");
			label = (String) map.get("label");
			priority = (int) map.get("priority");
			convention = (String) map.get("convention");
			String pattern = (String) map.get("pattern");
			return new GrammarPatternRule(name, description, label, priority, convention, pattern);

		case "RangeCheckRule":
			name = (String) map.get("name");
			description = (String) map.get("description");
			label = (String) map.get("label");
			priority = (int) map.get("priority");
			RangeCheckCondition rangeCheckCondition = new RangeCheckCondition((String) map.get("object"),
					(int) map.get("position"), (Comparable) map.get("lowBoundInclusive"),
					(Comparable) map.get("hiBoundInclusive"));
			DefaultAction defaultAction = new DefaultAction((String) map.get("fragment"), (String) map.get("val"));

			return new RangeCheckRule<Comparable>(name, description, label, priority, rangeCheckCondition,
					defaultAction);

		case "EdgeCheckRule":
			break;

		case "SpecialCheckRule":
			break;
		}

		return null;
	}
}
