import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
import java.lang.Math;

public class Decoder
{
	public static String toAsc(String string)
	{
		StringBuilder sb = new StringBuilder();
		char[] chars = string.replaceAll("\\s", "").toCharArray();
		int [] mapping = {1,2,4,8,16,32,64,128};

		for (int j = 0; j < chars.length; j+=8)
		{
			int idx = 0;
			int sum = 0;
			for (int i = 7; i>= 0; i--)
			{
				try
				{
					if (chars[i+j] == '1')
					{
						sum += 1 << idx;
					}
				}
				catch (Exception e){}
				idx++;
			}
			sb.append(Character.toChars(sum));	
		}		
		return sb.toString();
	}
	
	public static void main (String[] args) throws FileNotFoundException
	{
		Scanner scan=new Scanner(System.in);
		try
		{
			//~ scan=new Scanner(new File("output.stl"));
			scan=new Scanner(new File("transformed_castle.stl"));
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
		
		String binMes = Triangle.coolerReader(triangles);
		String asc = toAsc(binMes);
		System.out.println(asc);
		
		File file = new File ("message.txt");
		try
		{
			PrintWriter out = new PrintWriter(file);
			out.println(binMes);
			out.println(asc);
			out.close();       
		}
		catch (FileNotFoundException ex)  {}
	}
}

