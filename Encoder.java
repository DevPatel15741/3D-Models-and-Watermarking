import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
import java.lang.Math;

public class Encoder2
{
	public static String toBin(String message)
	{
		byte[] bytes = message.getBytes();
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes)
		{
			int val = b;
			for (int i = 0; i < 8; i++)
			{
				binary.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}
		}
		String conversion = binary.toString();
		return conversion;
	}
		
	public static void main(String[] args) throws Exception
	{
		String message = "Copyright 2024-2030 Jimbo, Inc. All rights reserved.";
		String input=toBin(message);
		int count=0;
		
		Scanner scan=new Scanner(System.in);
		PrintWriter out = new PrintWriter (System.out);
		try
		{
			scan=new Scanner(new File("castle.stl"));
		}
		catch(Exception e){}
				
		ArrayList<Double> vertices = new ArrayList<Double>(10);
		while(scan.hasNext())
		{
			String line=scan.nextLine().trim();
			String[] chunks=line.split(" ");
			for(String chunk:chunks)
			{
				
				try
				{
					double y=Double.parseDouble(chunk);
					vertices.add(y);
				}
				catch(Exception e) {}
			}
		}
		
		ArrayList<Triangle> triangles = new ArrayList<Triangle>(100);
		
		double[] vertToPack = new double[12];
		for(int i=0; i<vertices.size(); i+=12)
		{
			int k = 0;
			for(int j=i; j<(i+12); j++)
			{
				vertToPack[k]=vertices.get(j);
				k++;
			}
			Triangle tri = new Triangle(vertToPack[0], vertToPack[1], vertToPack[2], vertToPack[3], vertToPack[4], vertToPack[5], vertToPack[6], vertToPack[7], vertToPack[8], vertToPack[9], vertToPack[10], vertToPack[11]);
			triangles.add(tri);
		}
		out = new PrintWriter ("output.stl");
		Triangle.coolerModifier(triangles, input);
		out.print(Triangle.toString(triangles));
		out.close();
	}
}
