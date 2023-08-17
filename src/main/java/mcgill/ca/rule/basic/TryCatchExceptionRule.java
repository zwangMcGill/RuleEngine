package mcgill.ca.rule.basic;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import mcgill.ca.Category;
import mcgill.ca.fact.Fact;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static mcgill.ca.Category.EXCEPTION;

public class TryCatchExceptionRule extends ExceptionBasicRule
{
	public TryCatchExceptionRule(String name, String description, String label, int priority)
	{
		super(name, description, label, priority);
	}

	public TryCatchExceptionRule()
	{
		setName("TryCatchExceptionRule");
		setDescription("Catch Exception throw by try-catch mechanism");
	}

	@Override public boolean evaluate(Fact fact)
	{
		MethodDeclaration md = fact.getMd();
		return !md.findAll(TryStmt.class).isEmpty();
	}

	@Override public Map<Category, Set<String>> execute(Fact fact)
	{
		MethodDeclaration md = fact.getMd();
		Set<String> exceptions = new LinkedHashSet<>();
		md.accept(new TryStatementVisitor(), exceptions);
		fragmentMap.put(EXCEPTION, exceptions);
		return fragmentMap;
	}

	// try-catch
	static class TryStatementVisitor extends VoidVisitorAdapter<Set<String>>
	{

		public boolean hasExceptionCatchClause(TryStmt stmt)
		{
			boolean hasNoCatch = stmt.getCatchClauses().size() == 0;

			boolean hasException = stmt.getCatchClauses().stream().anyMatch(
					e -> e.getParameter().stream().anyMatch(statement -> statement.toString().contains("Exception")));

			return hasException && !hasNoCatch;
		}

		public void visit(TryStmt stmt, Set<String> arg)
		{
			super.visit(stmt, arg);

			if (hasExceptionCatchClause(stmt))
			{
				List<String> exception = stmt.getCatchClauses().stream().map(e -> e.getParameter().toString())
						.filter(e -> e.contains("Exception")).toList();
				arg.add(exception.get(0).split(" ")[0]);

			}
			else
			{
				System.out.println("No Catch Exception Clause existed");
			}
		}
	}
}
