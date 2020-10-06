package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerRecordClient 
{
	private ObjectInputStream Object_IS;
	private ObjectOutputStream Object_OS;
	
	public ServerRecordClient(ObjectInputStream object_IS, ObjectOutputStream object_OS) 
	{
		super();
		Object_IS = object_IS;
		Object_OS = object_OS;
	}

	public ObjectInputStream getObject_IS() 
	{
		return Object_IS;
	}

	public ObjectOutputStream getObject_OS() 
	{
		return Object_OS;
	}
}

/**Nothing Wrong.*/