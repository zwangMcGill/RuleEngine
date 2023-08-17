import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;

public class StanfordPOSTaggerTest
{
	@Test void givenSampleText_whenTokenize_thenExpectedTokensReturned()
	{
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		String text = "The german shepard display an act of kindness";
		Annotation document = new Annotation(text);
		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
		StringBuilder tokens = new StringBuilder();

		for (CoreMap sentence : sentences)
		{
			for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class))
			{
				String word = token.get(CoreAnnotations.TextAnnotation.class);
				tokens.append(word).append(" ");
			}
		}
		Assertions.assertEquals("The german shepard display an act of kindness", tokens.toString().trim());
	}

}
