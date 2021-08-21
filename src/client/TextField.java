package client;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * 菜单输入框
 * @author Administrator
 *
 */
public class TextField extends JTextField implements FocusListener
{
	private int state = 0; //1->修改 0->提示
	private String showText;
	
	public TextField(String text,int column)
	{
		super(text,column);
		showText = text;
		setForeground(Color.GRAY);
		addFocusListener(this);
	}

	public String getText()
	{
		if(this.state==1)
		{
			return super.getText();
		}
		return "";
	}

	@Override
	public void focusGained(FocusEvent e) 
	{
		if(state==0)
		{
			state = 1;
			this.setText("");
			setForeground(Color.BLACK);
		}
	}
	@Override
	public void focusLost(FocusEvent e) 
	{
		String temp = getText();
		if(temp.equals(""))
		{
			setForeground(Color.GRAY);
			this.setText(showText);
			state = 0;
		}
		else
		{
			this.setText(temp);
		}
	}
}