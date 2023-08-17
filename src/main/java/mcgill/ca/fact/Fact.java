package mcgill.ca.fact;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.util.List;

public class Fact
{
	private final MethodDeclaration md;
	private final CompilationUnit cu;

	public Fact(MethodDeclaration md, CompilationUnit cu)
	{
		this.md = md;
		this.cu = cu;
	}

	public CompilationUnit getCu()
	{
		return cu.clone();
	}

	public MethodDeclaration getMd()
	{
		return md.clone();
	}

	public List<MethodCallExpr> getMethodCalls()
	{
		return md.findAll(MethodCallExpr.class);
	}

	public String toString()
	{
		return md.getNameAsString();
	}
}
