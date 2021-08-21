package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import war.Config;

public class SendMsg extends Thread
{
	Logger logger = Logger.getLogger("SendMsg");
	int index = -1;
	Socket socket;
	BufferedWriter toClients[];
	BufferedReader fromClients[];
	int obsLoc[][];
	int enemyOp;

	public SendMsg(int index, Socket socket, BufferedWriter toClients[], BufferedReader fromClients[], int obsLoc[][]) 
	{
		this.index = index;
		this.socket = socket;
		this.toClients = toClients;
		this.fromClients = fromClients;
		this.obsLoc = obsLoc;
	}

	public void run() 
	{
		try 
		{
			// 服务器告知客户端下标
			toClients[index].write(index);
			toClients[index].flush();
			// 将障碍物位置传送给客户端
			for(int i = 0; i < Config.obsNum; i++)
			{
				toClients[index].write(obsLoc[i][0]);
				toClients[index].flush();
				toClients[index].write(obsLoc[i][1]);
				toClients[index].flush();
			}
			
			// 服务器获得客户端的角色选择
			String selfChoice = fromClients[index].readLine();
			// 服务器告知客户端对手的角色选择
			toClients[1-index].write(selfChoice+"\n");
			toClients[1-index].flush();
			
			// 开始游戏
			while(true)
			{
				enemyOp = fromClients[index].read();
				toClients[1-index].write(enemyOp);
				toClients[1-index].flush();
			}
		}
		catch(SocketException se)
		{
			logger.info(se.getMessage());
		}
		catch (IOException e) 
		{
			logger.info(e.getMessage());
		}
		finally 
		{
			try
			{
				toClients[1-index].write("玩家" + socket.getInetAddress() + "已下线");
				toClients[1-index].flush();
				socket.close();
			}
			catch (IOException e) 
			{
				logger.info(e.getMessage());
			}
		}
		
	}
}
