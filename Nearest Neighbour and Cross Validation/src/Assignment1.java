import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.*;

public class Assignment1 
{
	HashMap<Integer, tuple> readFile() 
	{
		int count = 0;
		HashMap<Integer, tuple> numbers = new HashMap<Integer, tuple>();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("second_file.txt"));
			String line = reader.readLine();
			String[] parts = line.split(" ");
			int row = Integer.parseInt(parts[0]);
			int col = Integer.parseInt(parts[1]);
			for(int i = 0; i < row; ++i)
			{
				parts = reader.readLine().split(" ");
				for(int j = 0; j < col; ++j)
				{
					if(parts[j].equals("+"))
					{
						tuple t = new tuple(j, i, 1);
						numbers.put(count, t);
						count++;
					}
					else if(parts[j].equals("-"))
					{
						tuple t = new tuple(j, i, 0);
						numbers.put(count, t);
						count++;
					}
				}
			}
			reader.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return numbers;
	}
	
	int find_distance(HashMap<Integer, tuple> numbers, ArrayList<Integer> test, int m, int knn, int twrong)
	{
		ArrayList<Integer> train = new ArrayList<Integer>();
		for(int j = 0; j < m; ++j)
		{
			train.add(j);
		}
		for(int i = 0; i < test.size(); ++i)
		{
			for(int j = 0; j < train.size(); j++)
			{
	            if(train.get(j).equals(test.get(i)))
	            {
	                train.remove(j);
	            }
	        }
		}
		//System.out.println("\nTrain: " + train);
		//System.out.println("Test: " + test);
		
		return find_neighbors(numbers, train, test, knn, twrong);
	}
	
	int find_neighbors(HashMap<Integer, tuple> numbers, ArrayList<Integer> train, ArrayList<Integer> test, int knn, int twrong) 
	{
		ArrayList<Integer> sdmap = new ArrayList<Integer>();
		int pos = 0, neg = 0;
		for(int i = 0; i < test.size(); ++i)
		{
			
			tuple one = numbers.get(test.get(i));
			HashMap<Integer, Double> dmap = new HashMap<Integer, Double>();
			for(int j = 0; j < train.size(); ++j)
			{
				tuple two = numbers.get(train.get(j));
				dmap.put(train.get(j), distance(one.x1, one.x2, two.x1, two.x2));
			}
			sdmap = sortByValue(dmap);
			//System.out.println("dmap:"+dmap);
			//System.out.println("sorted:"+sdmap);
			
			//System.out.println("Tuple: x1=" + one.x1 + " x2=" + one.x2+ " y=" + one.y);
			List<Integer> leastk;
			if(knn > sdmap.size())
			{
				leastk = sdmap.subList(0, sdmap.size());
			}
			else
			{
				leastk = sdmap.subList(0, knn);
			}
			pos = 0; neg = 0;
			for(int j = 0; j < leastk.size(); ++j)
			{
				//System.out.println("Comparing: x1=" + numbers.get(leastk.get(j)).x1 + " x2=" + numbers.get(leastk.get(j)).x2+ " y=" + numbers.get(leastk.get(j)).y);
				/*if(one.y != numbers.get(leastk.get(j)).y)
				{
					wrong++;
				}
			}*/
				if(numbers.get(leastk.get(j)).y == 0)
				{
					neg++;
				}
				else if(numbers.get(leastk.get(j)).y == 1)
				{
					pos++;
				}
			}
			if(pos == neg)
			{
				if(one.y == 1)
				{
					twrong++;
				}
			}
			else
			{
				if(one.y == 1 && neg > pos)
				{
					twrong++;
				}
				if(one.y == 0 && neg < pos)
				{
					twrong++;
				}
			}/*
			if(wrong > (leastk.size()/2))
			{
				twrong++;
			}
			else if(wrong == (leastk.size()/2))
			{
				//If the number of positive and negative are the same, classify it as negative
			}*/
		}
		return twrong;
	}
	
	double find_sigma(double e, double[] er, int t) 
	{
		double v = 0.0;
		for(int i = 0; i < t; ++i)
		{
			v = v + Math.pow((e - er[i]), 2);
		}
		return Math.sqrt(v/(t-1));
	}

	double distance(int x1, int y1, int x2, int y2)
	{
		return (Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	ArrayList<Integer> sortByValue(final HashMap m) 
	{
		ArrayList<Integer> keys = new ArrayList<Integer>();
        keys.addAll(m.keySet());
        Collections.sort(keys, new Comparator() 
        {
            public int compare(Object o1, Object o2) 
            {
                Object v1 = m.get(o1);
                Object v2 = m.get(o2);
                if (v1 == null) 
                {
                    return (v2 == null) ? 0 : 1;
                }
                else if (v1 instanceof Comparable) 
                {
                    return ((Comparable) v1).compareTo(v2);
                }
                else 
                {
                    return 0;
                }
            }
        });
        return keys;
	}

	 int classify(HashMap<Integer, tuple> numbers, tuple t, int knn) 
	{
		//System.out.println("Classifying: " + t.x1 + " " + t.x2);
		HashMap<Integer, Double> dmap = new HashMap<Integer, Double>();
		for(int j = 0; j < numbers.size(); ++j)
		{
			tuple one = numbers.get(j);
			dmap.put(j, distance(t.x1, t.x2, one.x1, one.x2));
		}
		ArrayList<Integer> sdmap = sortByValue(dmap);
		
		List<Integer> leastk;
		if(knn > sdmap.size())
		{
			leastk = sdmap.subList(0, sdmap.size());
		}
		else
		{
			leastk = sdmap.subList(0, knn);
		}
		//System.out.print("knn: " + knn + "  leastk:" + leastk);
		//System.out.println();
		int pos = 0; int neg = 0;
		for(int j = 0; j < leastk.size(); ++j)
		{
			if(numbers.get(leastk.get(j)).y == 0)
			{
				neg++;
			}
			else if(numbers.get(leastk.get(j)).y == 1)
			{
				pos++;
			}	
		}
		//System.out.print("Neg: " + neg + "Pos: " + pos + " ");
		if(neg >= pos)
		{
			return 0;
		}
		else if(pos > neg)
		{
			return 1;
		}
		return -1;
	}
}
