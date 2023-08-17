package mcgill.ca.condition;

import mcgill.ca.fact.Fact;

public interface Condition
{
	boolean evaluate(Fact fact);
}
