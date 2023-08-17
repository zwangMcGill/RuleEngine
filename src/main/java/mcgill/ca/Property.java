package mcgill.ca;

import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class Property
{
	private static Property property;

	private String YAML_PATH;
	private String SRC_PATH;
	private String TEST_PATH;
	private String SRC_DIR;

	public static Property getInstance()
	{
		if (property == null)
		{
			property = new Property();
		}
		return property;
	}

	public void loadProperties(String filePath) throws IOException
	{
		FileReader reader = new FileReader(filePath);
		Properties properties = new Properties();
		properties.load(reader);
		Enumeration<?> keys = properties.propertyNames();
		while (keys.hasMoreElements())
		{
			String key = (String) keys.nextElement();

			switch (key)
			{
			case "YAML_PATH":
				YAML_PATH = properties.getProperty(key);

			case "SRC_PATH":
				SRC_PATH = properties.getProperty(key);

			case "TEST_PATH":
				TEST_PATH = properties.getProperty(key);

			case "SRC_DIR":
				SRC_DIR = properties.getProperty(key);
			}
		}
		reader.close();
	}

	public String getYAML_PATH()
	{
		return YAML_PATH;
	}

	public void setYAML_PATH(String YAML_PATH)
	{
		this.YAML_PATH = YAML_PATH;
	}

	public String getSRC_PATH()
	{
		return SRC_PATH;
	}

	public void setSRC_PATH(String SRC_PATH)
	{
		this.SRC_PATH = SRC_PATH;
	}

	public String getTEST_PATH()
	{
		return TEST_PATH;
	}

	public void setTEST_PATH(String TEST_PATH)
	{
		this.TEST_PATH = TEST_PATH;
	}

	public String getSRC_DIR()
	{
		return SRC_DIR;
	}

	public void setSRC_DIR(String SRC_DIR)
	{
		this.SRC_DIR = SRC_DIR;
	}
}
