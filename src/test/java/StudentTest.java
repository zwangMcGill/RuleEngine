import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StudentTest
{
	@Test public void testGetAge()
	{
		Student student = new Student("zed", 1, 17);
		int age = student.getAge();
		Assertions.assertTrue(age <= 18);
	}

	@Test public void testGetName()
	{
		Student student = new Student("Bob", 2, 20);
		String name = student.getName();
		Assertions.assertEquals("Bob", name);
	}

}
