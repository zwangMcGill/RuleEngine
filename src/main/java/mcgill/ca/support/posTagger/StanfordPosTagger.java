package mcgill.ca.support.posTagger;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.Tree;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.StringReader;
import java.util.*;
import java.util.regex.Pattern;

public class StanfordPosTagger
{
	private final String text;
	private final String CAMEL_CASE_REGEX = "([a-z]+[A-Z]+\\w+)+";

	private int count = 0;

	public StanfordPosTagger(String text)
	{
		this.text = this.preprocess(text);
	}

	public Set<Pair<String,Integer>> getPhraseTag()
	{
		LexicalizedParser lp = initializeLexicalizedParser();
		Tree parseTree = parse(lp);
		return getPhraseTag(parseTree);
	}

	private Set<Pair<String,Integer>> getPhraseTag(Tree parseTree)
	{
		Set<Pair<String,Integer>> tags = new LinkedHashSet<>();

		if (Objects.equals(parseTree.label().value(), "ROOT"))
		{
			parseTree = parseTree.skipRoot();
		}

		if (parseTree.label().value().equals("S"))
		{
			for (Tree subtree : parseTree.children())
			{
				count = 0;
				Pair<String,Integer> pair = new ImmutablePair<>(subtree.label().value(),getLeafNumber(subtree));
				tags.add(pair);
			}
		}
		else
		{
			Pair<String,Integer> pair = new ImmutablePair<>(parseTree.label().value(),getLeafNumber(parseTree));
			tags.add(pair);
		}

		return tags;
	}

	public List<String> getWordTag(Tree parseTree)
	{
		List<Label> labels = parseTree.preTerminalYield();
		assert labels.size() > 1;
		return labels.stream().map(Label::value).toList();
	}

	private LexicalizedParser initializeLexicalizedParser()
	{
		LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
		lp.setOptionFlags("-maxLength", "80", "-retainTmpSubcategories");
		return lp;
	}

	private Tree parse(LexicalizedParser lp)
	{
		TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
		Tokenizer<CoreLabel> tok = tokenizerFactory.getTokenizer(new StringReader(text));
		List<CoreLabel> rawWords = tok.tokenize();

		return lp.apply(rawWords);
	}

	private String preprocess(String text)
	{
		assert text != null && !text.isEmpty();

		String copy = text;

		if (Pattern.matches(CAMEL_CASE_REGEX, copy))
		{
			copy = String.join(" ", copy.split(CAMEL_CASE_REGEX));
		}

		if (copy.startsWith("test"))
		{
			copy = copy.replace("test", "");
		}
		else if (copy.endsWith("Test"))
		{
			copy = copy.replace("Test", "");
		}

		return copy;
	}

	private int getLeafNumber(Tree tree){
		if (tree.children().length == 0){
			count++;
			return count;
		}

		for (Tree subTree : tree.children()){
			getLeafNumber(subTree);
		}

		return count;
	}
}
