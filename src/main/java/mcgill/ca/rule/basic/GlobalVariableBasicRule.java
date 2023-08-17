package mcgill.ca.rule.basic;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import mcgill.ca.Category;
import mcgill.ca.fact.Fact;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static mcgill.ca.Category.VARIABLE;

public class GlobalVariableBasicRule extends VariableBasicRule
{

	public GlobalVariableBasicRule(String name, String description, String label, int priority)
	{
		super(name, description, label, priority);
	}

	public GlobalVariableBasicRule()
	{
		setName("GlobalVariableBasicRule");
	}

	@Override public boolean evaluate(Fact fact)
	{
		CompilationUnit cu = fact.getCu();
		Optional<ClassOrInterfaceDeclaration> cd = cu.findFirst(ClassOrInterfaceDeclaration.class);

		if (cd.isPresent())
		{

			if (cd.get().getFields() == null)
				return false;

			for (FieldDeclaration fd : cd.get().getFields())
			{
				if (fd.getVariables() != null)
					return true;
			}
		}

		return false;
	}

	@Override public Map<Category, Set<String>> execute(Fact fact)
	{
		Set<String> globalVar = new LinkedHashSet<>();

		CompilationUnit cu = fact.getCu();
		Optional<ClassOrInterfaceDeclaration> cd = cu.findFirst(ClassOrInterfaceDeclaration.class);

		cd.ifPresent(classOrInterfaceDeclaration -> classOrInterfaceDeclaration.getFields().forEach(field -> {
			field.getVariables().forEach(variable -> {
				globalVar.add(variable.getNameAsString());
				globalVar.add(String.valueOf(variable.getType().isReferenceType()));
			});
		}));

		fragmentMap.put(VARIABLE, globalVar);
		return fragmentMap;
	}
}
