package mcgill.ca.fact;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import mcgill.ca.Property;
import mcgill.ca.Setup;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Facts implements Iterable<Fact>
{
	private final Set<Fact> facts = new LinkedHashSet<>();
	private final CompilationUnit cu;

	public Facts() throws IOException
	{
		Property property = Setup.loadProperty();
		this.cu = Setup.initializeCU(property);
		this.build();
	}

	public Facts(Property property) throws FileNotFoundException
	{
		this.cu = Setup.initializeCU(property);
		this.build();
	}

	public void build()
	{
		assert cu != null;
		List<MethodDeclaration> methods = findAllTestMethods();
		assert !methods.isEmpty() : "No Test Methods Found in the Test Suite!";
		methods.forEach(md -> facts.add(new Fact(md, cu)));
	}

	public List<MethodDeclaration> findAllTestMethods()
	{
		return cu.findAll(MethodDeclaration.class).stream().filter(e -> e.isAnnotationPresent(Test.class)).toList();

	}

	public Set<Fact> getFacts()
	{
		return facts;
	}

	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override public Iterator<Fact> iterator()
	{
		return facts.iterator();
	}
}
