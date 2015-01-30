import java.util.ArrayList;
public class Binary 
{
	ArrayList<Double> f = new ArrayList<Double>();
	ArrayList<String> less = new ArrayList<String>();
	ArrayList<Double> value = new ArrayList<Double>();
	
	public Binary(double d) 
	{
		f.add(d);
	}
	double find_value(double[] y, int i) 
	{
		double d2 = 0.0;
		for(int k = 0 ; k < f.size(); ++k)
		{
			d2 = d2 + f.get(k)*y[i];
		}
		return d2;
	}
	ArrayList<Integer> calculate_error(double[] y, int start, int stop, int j) 
	{
		ArrayList<Integer> error = new ArrayList<Integer>();
		double d = 0.0;
		for(int i = start; i <= stop; ++i)
		{
			d = find_value(y, i);
			if((j == 1 && d < 0) || (j == -1 && d > 0))
			{
				error.add(i);
			}
		}
		return error;
	}
	double find_mid(double d, double e) 
	{
		return (d+e)/2;
	}
	double find_error(ArrayList<Integer> wrongClassified, double[] p, int n) 
	{
		double err = 0.0;
		for(int i = 0; i < wrongClassified.size(); ++i)
		{
			err = err + p[wrongClassified.get(i)];
		}
		return err;
	}
	double[] find_q(double weight, ArrayList<Integer> wrongClassified, int n) 
	{
		double[] q = new double[n];
		for(int i = 0; i < n; ++i)
		{
			if(wrongClassified.contains(i))
			{
				q[i] = Math.pow(Math.E, weight);
			}
			else
			{
				q[i] = Math.pow(Math.E, -weight);
			}
		}
		return q;
	}
	double[] find_pq(double[] p, double[] q, int n) 
	{
		double[] pq = new double[n];
		for(int i = 0; i < n; ++i)
		{
			pq[i] = p[i] * q[i];
		}
		return pq;
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
	void update_func(double weight, int iteration, String lesser, double val) 
	{
		if(iteration == 0)
		{
			f.clear();
		}
		f.add(weight);
		less.add(lesser);
		value.add(val);
	}
	void display_func(int iterations) 
	{
		for(int i = 0; i <= iterations; ++i)
		{
			if(i != 0)
			{
				System.out.print("  +  ");
			}
			System.out.printf("%.2f", f.get(i));
			System.out.print(" * I(x" + less.get(i) + "" + value.get(i) + ")");
		}
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
}
