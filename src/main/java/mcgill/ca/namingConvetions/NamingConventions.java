package mcgill.ca.namingConvetions;

import java.util.Map;
import java.util.TreeMap;

public class NamingConventions
{
	private final Map<String, String> namingConvention = new TreeMap<>();

	public NamingConventions()
	{
		namingConvention.put("shouldThrowExceptionIfScenario", "shouldThrow(\\w+)If(\\w+)");
		namingConvention.put("shouldExpectedResultIfScenario", "should(\\w+)If(\\w+)");
		namingConvention.put("testShouldExpectedResult", "testShould(\\w+)");
		namingConvention.put("shouldExpectedResult", "should(\\w+)");
		namingConvention.put("testCanExpectedResult", "(test)?can(\\w+)");
		namingConvention.put("testReturnsExpectedResult", "(test)?Returns(\\w+)");
		namingConvention.put("testThrowExpectedResult", "(test)?Throw(\\w+)");
		namingConvention.put("testReturnsExpectedResultWhenScenario",
				"test(Throw|Returns|Throws|Return)(\\w+)When(\\w+)");
		namingConvention.put("ReturnsExpectedResultWhenScenario", "(throw|returns|throws|return)(\\w+)When(\\w+)");
		namingConvention.put("assertThrowsExpectedResult", "assertThrows(\\w+)");
		namingConvention.put("assertFocalMethod", "assert(\\w+)");
		namingConvention.put("FocalMethodWrappedInState", "(\\w+)WrappedIn(\\w+)");
		namingConvention.put("testExpectedResultThrownFromScenario", "test(\\w+)ThrownFrom(\\w+)");
		namingConvention.put("checkScenario", "check(\\w+)");
		namingConvention.put("whenScenarioThenExpectedResult", "when(\\w+)then(\\w+)");
		namingConvention.put("givenPreconditionWhenScenarioThenExpectedResult", "given(\\w+)When(\\w+)Then(\\w+)");
		namingConvention.put("givenPreconditionThenExpectedResult", "given(\\w+)Then(\\w+)");
		namingConvention.put("givenPreconditionReturnExpectedResult", "given(\\w+)Return(\\w+)");

	}

	public Map<String, String> getNamingConvention()
	{
		return namingConvention;
	}
}
