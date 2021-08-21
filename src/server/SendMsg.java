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
			// ��������֪�ͻ����±�
			toClients[index].write(index);
			toClients[index].flush();
			// ���ϰ���λ�ô��͸��ͻ���
			for(int i = 0; i < Config.obsNum; i++)
			{
				toClients[index].write(obsLoc[i][0]);
				toClients[index].flush();
				toClients[index].write(obsLoc[i][1]);
				toClients[index].flush();
			}
			
			// ��������ÿͻ��˵Ľ�ɫѡ��
			String selfChoice = fromClients[index].readLine();
			// ��������֪�ͻ��˶��ֵĽ�ɫѡ��
			toClients[1-index].write(selfChoice+"\n");
			toClients[1-index].flush();
			
			// ��ʼ��Ϸ
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
				toClients[1-index].write("���" + socket.getInetAddress() + "������");
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
