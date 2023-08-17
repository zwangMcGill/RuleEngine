package mcgill.ca.rule.basic;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import mcgill.ca.Category;
import mcgill.ca.fact.Fact;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static mcgill.ca.Category.VARIABLE;

public class LocalVariableBasicRule extends VariableBasicRule
{
	public LocalVariableBasicRule(String name, String description, String label, int priority)
	{
		super(name, description, label, priority);
	}

	public LocalVariableBasicRule()
	{
		setName("LocalVariableBasicRule");
	}

	@Override public boolean evaluate(Fact fact)
	{
		MethodDeclaration md = fact.getMd();
		return md.findAll(VariableDeclarator.class) != null;
	}

	public Map<Category, Set<String>> execute(Fact fact)
	{
		MethodDeclaration md = fact.getMd();
		Set<String> localVar = new LinkedHashSet<>();
		for (VariableDeclarator variableDeclarator : md.findAll(VariableDeclarator.class))
		{
			localVar.add(variableDeclarator.getNameAsString());
		}
		fragmentMap.put(VARIABLE, localVar);
		return fragmentMap;
	}
}
