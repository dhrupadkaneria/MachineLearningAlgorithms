import java.util.ArrayList;
public class Real 
{
	ArrayList<Double> f = new ArrayList<Double>();
	ArrayList<String> less = new ArrayList<String>();
	ArrayList<Double> value = new ArrayList<Double>();
	
	public Real(double d) 
	{
		f.add(d);
	}
	double find_value(ArrayList<Double> f2, double[] y, int i) 
	{
		double d2 = 0.0;
		for(int k = 0 ; k < f.size(); ++k)
		{
			d2 = d2 + f.get(k)*y[i];
		}
		return d2;
	}
	double find_prob(double[] y, double[] p, int start, int stop, int expected, int actual)
	{
		int e = 0;
		double d = 0.0, prob = 0.0;
		for(int i = start; i <= stop; ++i)
		{
			d = find_value(f, y, i);
			if(d < 0)
			{
				e = -1;
			}
			else if(d > 0)
			{
				e = 1;
			}
			if(expected == e && actual == y[i])
			{
				prob = prob + p[i];
			}
		}
		return prob;
	}
	double find_mid(double d, double e) 
	{
		return (d+e)/2;
	}
	ArrayList<Integer> find_wrongClassify(double[] y, int start, int stop, int exp) 
	{
		double d = 0.0;
		ArrayList<Integer> wrong = new ArrayList<Integer>();
		for(int i = start; i <= stop; ++i)
		{
			d = find_value(f, y, i);
			if(exp == 1 && d < 0)
			{
				wrong.add(i);
			}
			if(exp == -1 && d > 0)
			{
				wrong.add(i);
			}
		}
		return wrong;
	}
	double[] find_q(double[] y, double ctp, double ctn, int n) 
	{
		double[] q = new double[n];
		double d = 0.0;
		for(int i = 0; i < n; ++i)
		{
			d = find_value(f, y, i);
			if(d < 0)
			{
				q[i] = ctn;
			}
			else if(d > 0)
			{
				q[i] = ctp;
			}
		}
		return q;
	}
	double[] find_pq(double[] y, double[] q, double[] p, int n) 
	{
		double[] new_pq = new double[n];
		for(int i = 0; i < n; ++i)
		{
			new_pq[i] = p[i]* Math.pow(Math.E, -1 * y[i] * q[i]);
		}
		return new_pq;
	}
	double find_sum(double[] pq, int n) 
	{
		double sum = 0.0;
		for(int i = 0; i < n; ++i)
		{
			sum = sum + pq[i];
		}
		return sum;
	}
	double find_prod(double[] p, int n) 
	{
		double prod = 1.0;
		for(int i = 0; i < n; ++i)
		{
			prod = prod * p[i];
		}
		return prod;
	}
	double[] find_new_p(double[] pq, double z, int n) 
	{
		double[] new_p = new double[n];
		for(int i = 0; i < n; ++i)
		{
			new_p[i] = pq[i]/z;
		}
		return new_p;
	}
	void display_func(int iterations) 
	{
		for(int i = 0; i <= iterations; ++i)
		{
			if(i != 0)
			{
				System.out.print("  +  ");
			}
			System.out.printf("%.6f", f.get(i));
			System.out.print(" I(x" + less.get(i) + "" + value.get(i) + ")");
		}
	}
	void update_func(double minG, int iteration, String lesser2, double val) 
	{
		if(iteration == 0)
		{
			f.clear();
		}
		f.add(minG);
		less.add(lesser2);
		value.add(val);
	}
	ArrayList<Integer> calculate_error(double[] y, int start, int stop, int j) 
	{
		ArrayList<Integer> error = new ArrayList<Integer>();
		double d = 0.0;
		for(int i = start; i <= stop; ++i)
		{
			d = find_value(f, y, i);
			if((j == 1 && d < 0) || (j == -1 && d > 0))
			{
				error.add(i);
			}
		}
		return error;
	}
	double find_prob(ArrayList<Integer> errBoost, double[] p1) 
	{
		double sum = 0.0;
		for(int i = 0; i < errBoost.size(); ++i)
		{
			sum = sum + p1[errBoost.get(i)];
		}
		return sum;
	}
	public double find_prob2(double[] y, double[] p2, int start, int stop, int expected, int check) 
	{
		new ArrayList<Integer>();
		double d = 0.0, sum = 0.0;
		System.out.println();
		for(int i = start; i <= stop; ++i)
		{
			d = find_value(f, y, i);
			System.out.printf("i:%d ", i);
			switch(check)
			{
			case 0:
				if(d > 0 && y[i] == 1)
				{
					sum = sum + p2[i];
					System.out.printf("case0:%d ", i);
				}
				break;
			case 1:
				if(d < 0 && y[i] == -1)
				{
					sum = sum + p2[i];
					System.out.printf("case1:%d ", i);
				}
				break;
			case 2:
				if(d < 0 && y[i] == 1)
				{
					sum = sum + p2[i];
					System.out.printf("case2:%d ", i);
				}
				break;
			case 3:
				if(d > 0 && y[i] == -1)
				{
					sum = sum + p2[i];
					System.out.printf("case3:%d ", i);
				}
				break;
			}
		}
		return sum;
	}
	public ArrayList<Integer> find_no_error(double[] y, int start, int stop, int j) 
	{
		ArrayList<Integer> check = new ArrayList<Integer>();
		double d = 0.0;
		for(int i = start; i <= stop; ++i)
		{
			d = find_value(f, y, i);
			if(j == 1)
			{
				if(d > 0 && y[i] == 1)
				{
					check.add(i);
				}
			}
			if(j == -1)
			{
				if(d < 0 && y[i] == -1)
				{
					check.add(i);
				}
			}
		}
		return check;
	}
}
