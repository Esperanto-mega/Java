package server;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ServerOnlineUserUI extends AbstractTableModel
{
	private static final long serialVersionUID = -3577801384082059991L;
	
	private String[] Title = {"Account","Nickname","Sex"};
	
	private List<String[]> UserLines = new ArrayList<String[]>();

	@Override
	public int getColumnCount() 
	{
		// TODO 自动生成的方法存根
		return Title.length;
	}
	
	public String getColumnName(int index)
	{
		return Title[index];
	}

	@Override
	public int getRowCount() 
	{
		// TODO 自动生成的方法存根
		return UserLines.size();
	}

	public String getValueAt(int row, int column)
	{
		// TODO 自动生成的方法存根
		return (UserLines.get(row))[column];
	}
	
	public void add(String[] value)
	{
		int size = UserLines.size();
		UserLines.add(value);
		fireTableRowsInserted(size, size);
	}
	
	public void remove (long id)
	{
		int index=-1;
		for (int i=0;i<=UserLines.size();++i)
		{
			if(String.valueOf(id).equals(getValueAt(i, 0)))
			{
				index=i;
				break;
			}
		}
		UserLines.remove(index);
		fireTableRowsDeleted(2, 3);
	}
}

/**Nothing Wrong.*/
