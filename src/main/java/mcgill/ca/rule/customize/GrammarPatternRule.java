package mcgill.ca.rule.customize;

import mcgill.ca.Category;
import mcgill.ca.fact.Fact;
import mcgill.ca.rule.AbstractRule;
import mcgill.ca.support.posTagger.StanfordPosTagger;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Grammar Pattern Rule takes a template-like sequence of POS tags, and a sequence of categories. The goal is to link category with
 */
public class GrammarPatternRule extends AbstractRule
{
	private final String convention;
	private final String pattern;

	public GrammarPatternRule(String name, String description, String label, int priority, String convention,
			String pattern)
	{
		this.name = name;
		this.description = description;
		this.label = "GrammarPatternRule";
		this.priority = priority;
		this.convention = convention;
		this.pattern = pattern;
	}

	@Override public boolean evaluate(Fact fact)
	{
		assert pattern != null && convention != null : "Grammar Rule is not completed";
		assert pattern.split(" ").length == convention.split(
				" ").length : "Please fix the format of provided convention/pattern";

		StanfordPosTagger posTagger = new StanfordPosTagger(fact.toString());
		Set<Pair<String, Integer>> phraseTags = posTagger.getPhraseTag();
		String producedPattern = phraseTags.stream().map(Pair::getKey).collect(Collectors.joining(" "));

		return producedPattern.equals(pattern);
	}

	@Override public Map<Category, Set<String>> execute(Fact fact)
	{
		int index = 0;
		StanfordPosTagger posTagger = new StanfordPosTagger(fact.toString());
		Set<Pair<String, Integer>> phraseTags = posTagger.getPhraseTag();

		String[] fragments = convention.split(" ");
		Pair<String, Integer>[] phrasesArray = phraseTags.toArray(new Pair[0]);
		String methodName = fact.toString();

		for (int i = 0; i < phraseTags.size(); i++)
		{
			Optional<Category> category = Category.fromString(fragments[i]);
			if (category.isPresent())
			{
				fragmentMap.computeIfAbsent(category.get(), k -> new TreeSet<>());
				fragmentMap.get(category.get()).add(methodName.substring(index, index + phrasesArray[i].getValue()));
			}
		}

		return fragmentMap;
	}

}
