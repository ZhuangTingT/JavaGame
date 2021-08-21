package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import war.Config;

public class ClientMain
{
	static Socket socket;
	static Logger logger = Logger.getLogger("clientMain");
	
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		try
		{
			socket = new Socket(Config.serverName, Config.port);
			System.out.println("客户端连接成功");
			EnterGUI enterGui = new EnterGUI(socket);
		}
		catch (Exception e)
		{
			logger.info(e.getMessage());
		} 
	}
}
