/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * Reply is a simple class to present the
 * 
 * reply from Server.
 *
 */
package auxiliary;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Reply implements Serializable
{
	private static final long serialVersionUID = 3442027433991318537L;

	/**Reply phase.*/
	private ReplyPhase Phase;

	/**Reply type.*/
	private ReplyType Type;

	private Map<String, Object> DataMap;

	private OutputStream OS;

	public Reply()
	{
		Phase=ReplyPhase.SUCCESS;
		DataMap=new HashMap<String,Object>();
	}

	public ReplyPhase getPhase() 
	{
		return Phase;
	}

	public void setPhase(ReplyPhase phase) 
	{
		Phase = phase;
	}

	public ReplyType getType() 
	{
		return Type;
	}

	public void setType(ReplyType type) 
	{
		Type = type;
	}

	public Map<String, Object> getDataMap() 
	{
		return DataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) 
	{
		DataMap = dataMap;
	}

	public OutputStream getOS() 
	{
		return OS;
	}

	public void setOS(OutputStream oS) 
	{
		OS = oS;
	}

	public void setData(String key,Object value)
	{
		DataMap.put(key,value);
	}

	public Object getData(String key)
	{
		return DataMap.get(key);
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
