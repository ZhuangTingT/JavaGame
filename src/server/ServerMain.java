package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import war.Config;

public class ServerMain 
{
	Logger logger = Logger.getLogger("ServerMain");
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
		ServerSocket server = new ServerSocket(Config.port);
		System.out.println("服务器启动成功");
		
		BufferedWriter toClients[] = new BufferedWriter [Config.playerNum];
		BufferedReader fromClients[] = new BufferedReader [Config.playerNum];
		Socket socket[] = new Socket [Config.playerNum];
		SendMsg thread[] = new SendMsg [Config.playerNum];
		
		int index = 0;
		while(index < Config.playerNum)
		{
			socket[index] = server.accept();
			System.out.println(socket[index].getRemoteSocketAddress() + ":已成功连接");
			
			toClients[index] = new BufferedWriter(new OutputStreamWriter(socket[index].getOutputStream()));
			fromClients[index] = new BufferedReader(new InputStreamReader(socket[index].getInputStream()));
			
			index++;
		}

		int obsLoc[][] = new int [Config.obsNum][2];
		createObs(obsLoc);
		
		ExecutorService pool = Executors.newFixedThreadPool(2);
		for(index = 0; index < Config.playerNum; index++)
		{
			thread[index] = new SendMsg(index, socket[index], toClients, fromClients, obsLoc);
			pool.submit(thread[index]);
		}
		
		pool.shutdown();
		while(pool.isTerminated() == false)
			Thread.sleep(1000);
		
		server.close();
	}
	
	static void createObs(int obsLoc[][])
	{
		Random rand = new Random();
		for(int i = 0; i < Config.obsNum; i++)
		{
			obsLoc[i] = new int [2];
			obsLoc[i][0] = rand.nextInt(8)+1;
			obsLoc[i][1] = rand.nextInt(8)+1;
		}
	}
}