/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * Plea is a simple class to present the
 * 
 * request from client.
 *
 */

package auxiliary;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Plea implements Serializable
{
	/**Eclipse creates a serialVersionUID automatically.*/
	private static final long serialVersionUID = -2754171123053539496L;
	
	/**The type which is requesting to be transmitted.*/
	private ReplyType Type;
	
	/**Action that is taken.*/
	private String Action;
	
	/**Data in plea.*/
	private Map<String,Object> DataMap;
	
	public Plea()
	{
		DataMap=new HashMap<String,Object>();
	}

	public ReplyType getType() 
	{
		return Type;
	}

	public void setType(ReplyType type) 
	{
		Type = type;
	}

	public String getAction() 
	{
		return Action;
	}

	public void setAction(String action) 
	{
		Action = action;
	}

	public Map<String, Object> getDataMap() 
	{
		return DataMap;
	}

	public Object getData(String key)
	{
		return DataMap.get(key);
	}

	public void setData(String key,Object value)
	{
		DataMap.put(key,value);
	}
	
	public void removeData(String key)
	{
		DataMap.remove(key);
	}
	
	public void clearData()
	{
		DataMap.clear();
	}
}

/**Nothing Wrong.*/
