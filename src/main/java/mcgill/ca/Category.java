package mcgill.ca;

import java.util.Optional;

/**
 * Define enum class for semantic fragments
 */
public enum Category
{
	FOCAL_METHOD("FocalMethod"), FOCAL_CLASS("FocalClass"), SCENARIO("Scenario"), STATE("State"), VARIABLE(
		"Variable"), PARAMETER("Parameter"), EXPECTED_RESULT("ExpectedResult"), EXCEPTION("Exception");

	private final String fragment;

	Category(String fragment)
	{
		this.fragment = fragment;
	}

	public static Optional<Category> fromString(String fragment)
	{

		for (Category f : Category.values())
		{
			if (f.fragment.equals(fragment))
			{
				return Optional.of(f);
			}
		}
		return Optional.empty();
	}
}
