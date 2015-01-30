import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
public class Client 
{
	public static void main(String[] args) 
	{
		int m = 0;
		int kfold = 0;
		int t = 0;
		ArrayList<Integer> test = new ArrayList<Integer>();
		HashMap<Integer, tuple> numbers = new HashMap<Integer, tuple>();
		HashMap<Integer, ArrayList<Integer>> permutations = new HashMap<Integer, ArrayList<Integer>>();
		Assignment1 asgnmt1 = new Assignment1();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("first_file.txt"));
			String line = null;
			line = reader.readLine();
			String[] parts = line.split(" ");
			kfold = Integer.parseInt(parts[0]);
			m = Integer.parseInt(parts[1]);
			t = Integer.parseInt(parts[2]);
			for(int i = 0; i < t; ++i)
			{
				parts = reader.readLine().split(" ");
				ArrayList<Integer> arr = new ArrayList<Integer>();
				for(int j = 0; j < m; ++j)
				{
					arr.add(j, Integer.parseInt(parts[j]));
				}
				permutations.put(i, arr);
			}
			//System.out.println(permutations);
			reader.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		numbers = asgnmt1.readFile();
		
		int[][] points = null;
		int row = 0;
		int col = 0;
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("second_file.txt"));
			String line = reader.readLine();
			String[] parts = line.split(" ");
			row = Integer.parseInt(parts[0]);
			col = Integer.parseInt(parts[1]);
			points = new int[row][col];
			for(int i = 0; i < row; ++i)
			{
				parts = reader.readLine().split(" ");
				for(int j = 0; j < col; ++j)
				{
					if(parts[j].equals("+"))
					{
						points[i][j] = 1;
					}
					else if(parts[j].equals("-"))
					{
						points[i][j] = 0;
					}
					else if(parts[j].equals("."))
					{
						points[i][j] = -1;
					}
				}
			}
			reader.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		int twrong = 0;
		double err = 0.0;
		double[] er = new double[t];
		System.out.println("Kaneria Dhrupad Chandrakanth");
		for(int knn = 1; knn < 6; ++knn)
		{
			System.out.print("K=" + knn + " ");
			
			/*for(int i = 0; i < row; ++i)
			{
				for(int j = 0; j < col; ++j)
				{
					if(points[i][j] == -1)
					{
						int val = asgnmt1.classify(numbers, new tuple(j, i, -1), knn);
						if(val == 1)
						{
							System.out.print("+ ");
						}
						else if(val == 0)
						{
							System.out.print("- ");
						}
						else
						{
							System.out.print(". ");
						}
						
					}
					else if(points[i][j] == 0)
					{
						System.out.print("- ");
					}
					else if(points[i][j] == 1)
					{
						System.out.print("+ ");
					}
				}
				System.out.println();
			}*/
			
			err = 0.0;
			for(int i = 0; i < t; ++i)
			{
				int count = 0;
				twrong = 0;
				ArrayList<Integer> arr = new ArrayList<Integer>();
				arr = permutations.get(i);
				//System.out.println("Permutation: "+arr);
				
				int n = m/kfold;
				for(int p = 1; p < kfold; ++p)
				{
					for(int j = 0; j < n; ++j)
					{
						test.add(arr.get(count));
						count++;
					}
					twrong = asgnmt1.find_distance(numbers, test, m, knn, twrong);
					test.clear();
				}
				for(int j = count; j < m; ++j)
				{
					test.add(arr.get(j));
				}
				twrong = asgnmt1.find_distance(numbers, test, m, knn, twrong);
				test.clear();
				er[i] = (double)twrong/m;
				err += er[i];
				//System.out.println("Total wrong: " + twrong);
				//System.out.println("E: " + er[i]);
			}
			//System.out.printf("E: %.4f \n", err);
			System.out.printf("e=%.4f ", err/t);
			System.out.printf("sigma=%.3f\n", asgnmt1.find_sigma(err/t, er, t));
			
			for(int i = 0; i < row; ++i)
			{
				for(int j = 0; j < col; ++j)
				{
					if(points[i][j] == -1)
					{
						int val = asgnmt1.classify(numbers, new tuple(j, i, -1), knn);
						if(val == 1)
						{
							System.out.print("+ ");
						}
						else if(val == 0)
						{
							System.out.print("- ");
						}
						else
						{
							System.out.print(". ");
						}
						
					}
					else if(points[i][j] == 0)
					{
						System.out.print("- ");
					}
					else if(points[i][j] == 1)
					{
						System.out.print("+ ");
					}
				}
				System.out.println();
			}
			System.out.println();
		}
	}
}
