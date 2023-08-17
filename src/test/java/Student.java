public class Student
{
	String Name;
	int id;
	int age;

	public Student(String name, int id, int age)
	{
		Name = name;
		this.id = id;
		this.age = age;
	}

	public String getName()
	{
		return Name;
	}

	public void setName(String name)
	{
		Name = name;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

}
