package mcgill.ca;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Setup
{
	public static Property loadProperty() throws IOException
	{
		Property property = Property.getInstance();
		property.loadProperties("D:\\Research\\RuleEngine\\src\\main\\java\\mcgill\\ca\\config.properties");
		return property;
	}

	/**
	 * Initialize CU with type solver
	 *
	 * @return CompilationUnit
	 * @throws FileNotFoundException
	 */
	public static CompilationUnit initializeCU(Property property) throws FileNotFoundException
	{
		CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
		JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
		// add the basic reflection type solver
		combinedTypeSolver.add(new ReflectionTypeSolver());
		// add the src type solver
		combinedTypeSolver.add(new JavaParserTypeSolver(new File(property.getSRC_DIR())));

		StaticJavaParser.getParserConfiguration().setSymbolResolver(symbolSolver);
		return StaticJavaParser.parse(new File(property.getTEST_PATH()));
	}
}
