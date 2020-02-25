import java.util.*;
import java.text.DecimalFormat;
import java.lang.Math;

public class Triangle
{
	double NormX;
	double NormY;
	double NormZ;
	double V1X;
	double V1Y;
	double V1Z;
	double V2X;
	double V2Y;
	double V2Z;
	double V3X;
	double V3Y;
	double V3Z;
	static double add0 = 0;
	static double add1 = 0.5;
	
	public Triangle(double normX, double normY, double normZ, double v1X, double v1Y, double v1Z, double v2X, double v2Y, double v2Z, double v3X, double v3Y, double v3Z)
	{
		NormX=normX;
		NormY=normY;
		NormZ=normZ;
		V1X=v1X;
		V1Y=v1Y;
		V1Z=v1Z;
		V2X=v2X;
		V2Y=v2Y;
		V2Z=v2Z;
		V3X=v3X;
		V3Y=v3Y;
		V3Z=v3Z;
	}
	
	/**
	 * Outputs a string that would fit in an ascii file
	 */
	public static String toString(ArrayList<Triangle> bunch)
	{
		//~ int bunLen=bunch.size();
		String str = "solid output\n";
		for(int i = 0; i<bunch.size(); i++)
		{
			//~ System.out.println((double)i/bunLen);
			str+="  facet normal " + Double.toString(bunch.get(i).NormX) + " " + Double.toString(bunch.get(i).NormY) + " " + Double.toString(bunch.get(i).NormZ) + "\n";
			str+="    outer loop\n";
			str+="      vertex " + Double.toString(bunch.get(i).V1X) + " " + Double.toString(bunch.get(i).V1Y) + " " + Double.toString(bunch.get(i).V1Z) + "\n";
			str+="      vertex " + Double.toString(bunch.get(i).V2X) + " " + Double.toString(bunch.get(i).V2Y) + " " + Double.toString(bunch.get(i).V2Z) + "\n";
			str+="      vertex " + Double.toString(bunch.get(i).V3X) + " " + Double.toString(bunch.get(i).V3Y) + " " + Double.toString(bunch.get(i).V3Z) + "\n";
			str+="    endloop\n";
			str+="  endfacet\n";
		}
		str+="endsolid";
		return str;
	}
	
	/**
	 * Distance Formula
	 */
	public static double distance(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		return Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2)+Math.pow((z2-z1),2));
	}
	
	public static double getAngle(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3)
	{
		double a=distance(x1,y1,z1,x2,y2,z2);
		double b=distance(x2,y2,z2,x3,y3,z3);
		double c=distance(x1,y1,z1,x3,y3,z3);
		//~ inverse cosine of a^2+b^2-c^2 / 2ab
		return (180*Math.acos((Math.pow(a,2)+Math.pow(b,2)-Math.pow(c,2))/(2*a*b)))/Math.PI;
	}
	
	public static void coolerModifier(ArrayList<Triangle> things, String binMes)
	{
		//~ int binMesLen = binMes.length();
		int count = 0;
		for(int i = 0; i<things.size(); i++)
		{
			if(count==binMes.length()) break;
			
			double normX = things.get(i).NormX;
			double v1X = things.get(i).V1X;
			double v2X = things.get(i).V2X;
			double v3X = things.get(i).V3X;
			
			double normY = things.get(i).NormY;
			double v1Y = things.get(i).V1Y;
			double v2Y = things.get(i).V2Y;
			double v3Y = things.get(i).V3Y;
			
			double normZ = things.get(i).NormZ;
			double v1Z = things.get(i).V1Z;
			double v2Z = things.get(i).V2Z;
			double v3Z = things.get(i).V3Z;
			
			double distanceOneToTwo = distance(v1X, v1Y, v1Z, v2X, v2Y, v2Z);
			// The encoding would be done in the alpha variables, encoding process would be different
			double alphaX = (v3X-v2X)/distanceOneToTwo; // Take x of 3rd vertex and subtract by x of 2nd vertex and divide by distance between 1st vertex and 2nd vertex and 3rd vertex. This number is resistant across scale and translation changes because these changes would change the numerator and denominator the same amount.
			double alphaY = (v3Y-v2Y)/distanceOneToTwo;
			double alphaZ = (v3Z-v2Z)/distanceOneToTwo;
			
			double fact = 10000;
			
			// Encodes the alpha variable
			alphaX*=fact;
			alphaX=Math.round(alphaX);
			if(binMes.charAt(count)=='0')  alphaX+=add0;
			else alphaX+=add1;
			alphaX/=fact;
			count++;
			if(count==binMes.length()) break;
			
			alphaY*=fact;
			alphaY=Math.round(alphaY);
			if(binMes.charAt(count)=='0')  alphaY+=add0;
			else alphaY+=add1;
			alphaY/=fact;
			count++;
			if(count==binMes.length()) break;
			
			alphaZ*=fact;
			alphaZ=Math.round(alphaZ);
			if(binMes.charAt(count)=='0')  alphaZ+=add0;
			else alphaZ+=add1;
			alphaZ/=fact;
			count++;
			
			
			
			// Undoes the operations to create alpha
			v3X = (alphaX*distanceOneToTwo) + v2X;
			v3Y = (alphaY*distanceOneToTwo) + v2Y;
			v3Z = (alphaZ*distanceOneToTwo) + v2Z;
			
			//Puts coordinates back into Triangle
			Triangle edit = new Triangle(normX, normY, normZ, v1X, v1Y, v1Z, v2X, v2Y, v2Z, v3X, v3Y, v3Z);
			things.set(i, edit);
			//~ System.out.println((double)i/binMesLen);
		}
	}
	
	public static String coolerReader(ArrayList<Triangle> things)
	{
		String str = "";
		for(int i = 0; i<things.size(); i++)
		{
			//~ System.out.println(i);
			double normX = things.get(i).NormX;
			double v1X = things.get(i).V1X;
			double v2X = things.get(i).V2X;
			double v3X = things.get(i).V3X;
			
			double normY = things.get(i).NormY;
			double v1Y = things.get(i).V1Y;
			double v2Y = things.get(i).V2Y;
			double v3Y = things.get(i).V3Y;
			
			double normZ = things.get(i).NormZ;
			double v1Z = things.get(i).V1Z;
			double v2Z = things.get(i).V2Z;
			double v3Z = things.get(i).V3Z;
			
			double distanceOneToTwo = distance(v1X, v1Y, v1Z, v2X, v2Y, v2Z);
			
			double alphaX = (v3X-v2X)/distanceOneToTwo;
			double alphaY = (v3Y-v2Y)/distanceOneToTwo;
			double alphaZ = (v3Z-v2Z)/distanceOneToTwo;
			//System.out.println(alphaX+","+alphaY+","+alphaZ);
			
			double mod1 = 1;
			double fact = 10000;
			
			char a = '0';
			char b = '1';
			
			if(((alphaX*fact)%mod1+mod1)%mod1<0.25)
			{
				str+=a;
			}
			else if(((alphaX*fact)%mod1+mod1)%mod1<.75)
			{
				str+=b;
			}
			else
			{
				str+=a;
			}
			
			if(((alphaY*fact)%mod1+mod1)%mod1<0.25)
			{
				str+=a;
			}
			else if(((alphaY*fact)%mod1+mod1)%mod1<.75)
			{
				str+=b;
			}
			else
			{
				str+=a;
			}
			
			
			if(((alphaZ*fact)%mod1+mod1)%mod1<0.25)
			{
				str+=a;
			}
			else if(((alphaZ*fact)%mod1+mod1)%mod1<0.75)
			{
				str+=b;
			}
			else
			{
				str+=a;
			}
		}
		return str;
	}
	
	
	
	
	public static void angleModifier(ArrayList<Triangle> things, String binMes)
	{
		int count = 0;
		for(int i = 0; i<things.size(); i++)
		{
			if(count==binMes.length()) break;
			
			double normX = things.get(i).NormX;
			double v1X = things.get(i).V1X;
			double v2X = things.get(i).V2X;
			double v3X = things.get(i).V3X;
			
			double normY = things.get(i).NormY;
			double v1Y = things.get(i).V1Y;
			double v2Y = things.get(i).V2Y;
			double v3Y = things.get(i).V3Y;
			
			double normZ = things.get(i).NormZ;
			double v1Z = things.get(i).V1Z;
			double v2Z = things.get(i).V2Z;
			double v3Z = things.get(i).V3Z;
			
			double distanceOneToTwo = distance(v1X, v1Y, v1Z, v2X, v2Y, v2Z);
			// The encoding would be done in the alpha variables, encoding process would be different
			double alphaX = (v3X-v2X)/distanceOneToTwo; // Take x of 3rd vertex and subtract by x of 2nd vertex and divide by distance between 1st vertex and 2nd vertex and 3rd vertex. This number is resistant across scale and translation changes because these changes would change the numerator and denominator the same amount.
			double alphaY = (v3Y-v2Y)/distanceOneToTwo;
			double alphaZ = (v3Z-v2Z)/distanceOneToTwo;
			
			double fact = 10000;
			
			// Encodes the alpha variable
			alphaX*=fact;
			alphaX=Math.round(alphaX);
			if(binMes.charAt(count)=='0')  alphaX+=add0;
			else alphaX+=add1;
			alphaX/=fact;
			count++;
			if(count==binMes.length()) break;
			
			alphaY*=fact;
			alphaY=Math.round(alphaY);
			if(binMes.charAt(count)=='0')  alphaY+=add0;
			else alphaY+=add1;
			alphaY/=fact;
			count++;
			if(count==binMes.length()) break;
			
			alphaZ*=fact;
			alphaZ=Math.round(alphaZ);
			if(binMes.charAt(count)=='0')  alphaZ+=add0;
			else alphaZ+=add1;
			alphaZ/=fact;
			count++;
			
			
			
			// Undoes the operations to create alpha
			v3X = (alphaX*distanceOneToTwo) + v2X;
			v3Y = (alphaY*distanceOneToTwo) + v2Y;
			v3Z = (alphaZ*distanceOneToTwo) + v2Z;
			
			//Puts coordinates back into Triangle
			Triangle edit = new Triangle(normX, normY, normZ, v1X, v1Y, v1Z, v2X, v2Y, v2Z, v3X, v3Y, v3Z);
			things.set(i, edit);
		}
	}
	
	public static String angleReader(ArrayList<Triangle> things)
	{
		String str = "";
		for(int i = 0; i<things.size(); i++)
		{
			//~ System.out.println(i);
			double normX = things.get(i).NormX;
			double v1X = things.get(i).V1X;
			double v2X = things.get(i).V2X;
			double v3X = things.get(i).V3X;
			
			double normY = things.get(i).NormY;
			double v1Y = things.get(i).V1Y;
			double v2Y = things.get(i).V2Y;
			double v3Y = things.get(i).V3Y;
			
			double normZ = things.get(i).NormZ;
			double v1Z = things.get(i).V1Z;
			double v2Z = things.get(i).V2Z;
			double v3Z = things.get(i).V3Z;
			
			double distanceOneToTwo = distance(v1X, v1Y, v1Z, v2X, v2Y, v2Z);
			
			double alphaX = (v3X-v2X)/distanceOneToTwo;
			double alphaY = (v3Y-v2Y)/distanceOneToTwo;
			double alphaZ = (v3Z-v2Z)/distanceOneToTwo;
			//System.out.println(alphaX+","+alphaY+","+alphaZ);
			
			double mod1 = 1;
			double fact = 10000;
			
			char a = '0';
			char b = '1';
			
			if(((alphaX*fact)%mod1+mod1)%mod1<0.25)
			{
				str+=a;
			}
			else if(((alphaX*fact)%mod1+mod1)%mod1<.75)
			{
				str+=b;
			}
			else
			{
				str+=a;
			}
			if(((alphaY*fact)%mod1+mod1)%mod1<0.25)
			{
				str+=a;
			}
			else if(((alphaY*fact)%mod1+mod1)%mod1<.75)
			{
				str+=b;
			}
			else
			{
				str+=a;
			}
			if(((alphaZ*fact)%mod1+mod1)%mod1<0.25)
			{
				str+=a;
			}
			else if(((alphaZ*fact)%mod1+mod1)%mod1<0.75)
			{
				str+=b;
			}
			else
			{
				str+=a;
			}
		}
		return str;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void modifier(ArrayList<Triangle> things, String binMes)
	{
		DecimalFormat df = new DecimalFormat("#.######");
		for(int i = 0; i<things.size(); i++)
		{
			//v1 and v2
			double len1 = distance(things.get(i).V2X, things.get(i).V2Y, things.get(i).V2Z, things.get(i).V1X, things.get(i).V1Y, things.get(i).V1Z);
			//v2 and v3
			double len2 = distance(things.get(i).V2X, things.get(i).V2Y, things.get(i).V2Z, things.get(i).V3X, things.get(i).V3Y, things.get(i).V3Z);
			//v1 and v3
			double len3 = distance(things.get(i).V3X, things.get(i).V3Y, things.get(i).V3Z, things.get(i).V1X, things.get(i).V1Y, things.get(i).V1Z);
			
			int count = 0;
			
			if (len1>len2 && len1>len3) //adjusts v3
			{
				double tempX=0;
				double tempY=0;
				double tempZ=0;
				if(binMes.charAt(count)=='0')
				{
					tempX = (Double.parseDouble(df.format(things.get(i).V3X))) + .0000002;
					tempY = (Double.parseDouble(df.format(things.get(i).V3X))) + .0000002;
					tempZ = (Double.parseDouble(df.format(things.get(i).V3X))) + .0000002;
				}
				else
				{
					tempX = ((int) things.get(i).V3X) + .0000001;
					tempY = ((int) things.get(i).V3Y) + .0000001;
					tempZ = ((int) things.get(i).V3Z) + .0000001;
				}
				Triangle tempTri = new Triangle(things.get(i).NormX, things.get(i).NormY, things.get(i).NormZ, things.get(i).V1X, things.get(i).V1Y,things.get(i).V1Z,things.get(i).V2X,things.get(i).V2Y,things.get(i).V2Z, tempX, tempY, tempZ);
				things.set(i, tempTri);
			}
			else if (len2>len1 && len2>len3) //adjusts v1
			{
				double tempX=0;
				double tempY=0;
				double tempZ=0;
				if(binMes.charAt(count)=='0')
				{
					tempX = (Double.parseDouble(df.format(things.get(i).V1X))) + .0000002;
					tempY = (Double.parseDouble(df.format(things.get(i).V1Y))) + .0000002;
					tempZ = (Double.parseDouble(df.format(things.get(i).V1Z))) + .0000002;
				}
				else
				{
					tempX = (Double.parseDouble(df.format(things.get(i).V1X))) + .0000001;
					tempY = (Double.parseDouble(df.format(things.get(i).V1Y))) + .0000001;
					tempZ = (Double.parseDouble(df.format(things.get(i).V1Z))) + .0000001;
				}
				Triangle tempTri = new Triangle(things.get(i).NormX, things.get(i).NormY, things.get(i).NormZ, tempX, tempY,tempZ,things.get(i).V2X,things.get(i).V2Y,things.get(i).V2Z, things.get(i).V3X,things.get(i).V3Y,things.get(i).V3Z);
				things.set(i, tempTri);
			}
			else if (len3>len1 && len3>len2) //adjusts v2
			{
				double tempX=0;
				double tempY=0;
				double tempZ=0;
				if(binMes.charAt(count)=='0')
				{
					tempX = (Double.parseDouble(df.format(things.get(i).V2X))) + .0000002;
					tempY = (Double.parseDouble(df.format(things.get(i).V2Y))) + .0000002;
					tempZ = (Double.parseDouble(df.format(things.get(i).V2Z))) + .0000002;
				}
				else
				{
					tempX = (Double.parseDouble(df.format(things.get(i).V2X))) + .0000001;
					tempY = (Double.parseDouble(df.format(things.get(i).V2Y))) + .0000001;
					tempZ = (Double.parseDouble(df.format(things.get(i).V2Z))) + .0000001;
				}
				Triangle tempTri = new Triangle(things.get(i).NormX, things.get(i).NormY, things.get(i).NormZ, things.get(i).V1X, things.get(i).V1Y,things.get(i).V1Z,tempX, tempY, tempZ, things.get(i).V3X,things.get(i).V3Y,things.get(i).V3Z);
				things.set(i, tempTri);
			}
			count++;
		}
	}
	
	public static String reader(ArrayList<Triangle> things)
	{
		String str = "";
		for(int i = 0; i<things.size(); i++)
		{
			//v1 and v2
			double len1 = Math.sqrt((Math.pow((things.get(i).V2X-things.get(i).V1X),2))+(Math.pow((things.get(i).V2Y-things.get(i).V1Y),2))+(Math.pow((things.get(i).V2Z-things.get(i).V1Z),2)));
			//v2 and v3
			double len2 = Math.sqrt((Math.pow((things.get(i).V2X-things.get(i).V3X),2))+(Math.pow((things.get(i).V2Y-things.get(i).V3Y),2))+(Math.pow((things.get(i).V2Z-things.get(i).V3Z),2)));
			//v1 and v3
			double len3 = Math.sqrt((Math.pow((things.get(i).V1X-things.get(i).V3X),2))+(Math.pow((things.get(i).V1Y-things.get(i).V3Y),2))+(Math.pow((things.get(i).V1Z-things.get(i).V3Z),2)));
			
			if (len1>len2 && len1>len3) //reads v3
			{
				double tempX = (things.get(i).V3X*10000000)%2;
				double tempY = (things.get(i).V3Y*10000000)%2;
				double tempZ = (things.get(i).V3Z*10000000)%2;
				if(tempX==0 && tempY==0 && tempZ==0)
				{
					str+="0";
				}
				else
				{
					str+="1";
				}
			}
			else if (len2>len1 && len2>len3) //reads v1
			{
				double tempX = (things.get(i).V3X*10000000)%2;
				double tempY = (things.get(i).V3Y*10000000)%2;
				double tempZ = (things.get(i).V3Z*10000000)%2;
				if (tempX==0 && tempY==0 && tempZ==0)
				{
					str+="0";
				}
				else
				{
					str+="1";
				}
			}
			else if (len3>len1 && len3>len2) //reads v2
			{
				double tempX = (things.get(i).V3X*10000000)%2;
				double tempY = (things.get(i).V3Y*10000000)%2;
				double tempZ = (things.get(i).V3Z*10000000)%2;
				if (tempX==0 && tempY==0 && tempZ==0)
				{
					str+="0";
				}
				else
				{
					str+="1";
				}
			}
		}
		return str;
	}
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args){}
}
