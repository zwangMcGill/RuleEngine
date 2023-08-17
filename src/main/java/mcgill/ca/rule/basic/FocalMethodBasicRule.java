package mcgill.ca.rule.basic;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import mcgill.ca.Category;
import mcgill.ca.fact.Fact;
import mcgill.ca.rule.AbstractRule;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.commons.text.similarity.LongestCommonSubsequence;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static mcgill.ca.Category.FOCAL_METHOD;

public class FocalMethodBasicRule extends AbstractRule
{
	private MethodDeclaration md;

	public FocalMethodBasicRule(String name, String description, String label, int priority)
	{
		super(name, description, label, priority);
	}

	public FocalMethodBasicRule()
	{
		setName("FocalMethodBasicRule");
	}

	public Map<Category, Set<String>> execute(Fact fact)
	{
		Set<MethodCallExpr> focalMethods = visit(fact);
		Set<String> focalMethodNames = focalMethods.stream().map(NodeWithSimpleName::getNameAsString)
				.collect(Collectors.toUnmodifiableSet());
		fragmentMap.put(FOCAL_METHOD, focalMethodNames);
		return fragmentMap;
	}

	@Override public boolean evaluate(Fact fact)
	{
		return !visit(fact).isEmpty();
	}

	private Set<MethodCallExpr> visit(Fact fact)
	{
		Set<MethodCallExpr> focalMethodSet = new LinkedHashSet<>();
		md = fact.getMd();
		md.accept(new MethodCallVisitor(), focalMethodSet);
		return focalMethodSet;
	}

	private boolean isFocal(String processedFocal, String processedTest)
	{
		return NC(processedFocal, processedTest) + NCC(processedFocal, processedTest) + Levenshtein(processedFocal,
				processedTest) + LCSB(processedFocal, processedTest) + LCBA(processedFocal, processedTest) >= 1.0;
	}

	private double NC(String processedFocal, String processedTest)
	{
		return processedTest.equals(processedFocal) ? 1 : 0;
	}

	private double NCC(String processedFocal, String processedTest)
	{
		return processedTest.contains(processedFocal) ? 1 : 0;
	}

	private double Levenshtein(String processedFocal, String processedTest)
	{
		double distance = new LevenshteinDistance().apply(processedTest, processedFocal);
		return 1.0 - (distance / Math.max(processedTest.length(), processedFocal.length()));
	}

	// Longest common sequence
	private double LCSB(String processedFocal, String processedTest)
	{
		double similarityScore = new LongestCommonSubsequence().apply(processedTest, processedFocal);
		return similarityScore / Math.max(processedTest.length(), processedFocal.length());
	}

	private double LCBA(String processedFocal, String processedTest)
	{
		List<MethodCallExpr> methodInvocations = getMethodInvocations(md);

		if (methodInvocations.size() == 0)
		{
			return 0;
		}

		for (int i = methodInvocations.size() - 1; i > 0; i--)
		{
			if (methodInvocations.get(i).getNameAsString().toLowerCase().contains("assert"))
				return methodInvocations.get(i - 1).getNameAsString().toLowerCase().equals(processedFocal) ? 1 : 0;
		}
		return 0;
	}

	private List<MethodCallExpr> getMethodInvocations(MethodDeclaration md)
	{
		return md.findAll(MethodCallExpr.class);
	}

	class MethodCallVisitor extends VoidVisitorAdapter<Set<MethodCallExpr>>
	{
		@Override public void visit(MethodCallExpr n, Set<MethodCallExpr> arg)
		{
			try
			{

				String focal = n.getNameAsString();

				if (isFocal(focal.toLowerCase(), md.getNameAsString().toLowerCase()))
					arg.add(n);

			}
			catch (UnsolvedSymbolException e)
			{
				System.err.println("Unsolved symbol in BaseFocalMethodCondition: " + e.getName());
			}

			super.visit(n, arg);
		}
	}
}
