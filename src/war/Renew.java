package war;

public class Renew implements Runnable
{
	Follower f = null;
	String map[][];
	boolean checkMatrix[][];

	public Renew(Follower f, String map[][], boolean checkMatrix[][])
	{
		this.f = f;
		this.map = map;
		this.checkMatrix = checkMatrix;
	}

	@Override
	public void run() 
	{
		try 
		{
			Thread.sleep(5000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		f.setXY(f.x0, f.y0);
		f.hp = f.hp0;
		map[f.x][f.y] = f.name;
		checkMatrix[f.x][f.y] = false;
	}
}