import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class Client 
{
	public static void main(String[] args) 
	{
		int t = 0;
		int n = 0;
		double epsilon = 0.0;
		double[] x = {0};
		double[] y = {0};
		double[] p1 = {0};
		double[] p2 = {0};
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("adaboost-1.dat"));
			String[] parts = reader.readLine().split(" ");
			t = Integer.parseInt(parts[0]);
			n = Integer.parseInt(parts[1]);
			epsilon = Double.parseDouble(parts[2]);
			x = new double[n];
			y = new double[n];
			p1 = new double[n];
			p2 = new double[n];
			parts = reader.readLine().split(" ");
			for(int i = 0; i < n; ++i)
			{
				x[i] = Double.parseDouble(parts[i]);
 			}
			parts = reader.readLine().split(" ");
			for(int i = 0; i < n; ++i)
			{
				y[i] = Double.parseDouble(parts[i]);
 			}
			parts = reader.readLine().split(" ");
			for(int i = 0; i < n; ++i)
			{
				p1[i] = Double.parseDouble(parts[i]);
				p2[i] = p1[i];
				
 			}
			reader.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("Kaneria, Dhrupad Chandrakanth");
		//System.out.println("-----------------Part 1-----------------");
		//System.out.println("-----------------Binary AdaBoosting-----------------");
		Binary bi = new Binary(1.0);
		double[] z = new double[t];
		for(int i = 0; i < t; ++i)
		{
			System.out.println("Iteration " + (i+1));
			ArrayList<Integer> wrongClassified = new ArrayList<Integer>();
			ArrayList<Integer> wc = new ArrayList<Integer>();
			double[] q = new double[n];
			double[] pq = new double[n];
			double minIndex = 0.0;
			double mindex = 0;
 			double minError = Math.ceil(x[n-1]);
			String lesser = "<";
			for(int j = 0; j < n-1 ; ++j)
			{
				ArrayList<Integer> errorList11 = bi.calculate_error(y, 0, j, 1);
				ArrayList<Integer> errorList12 = bi.calculate_error(y, j+1, n-1, -1);
				wc.clear();
				wc.addAll(errorList11);
				wc.addAll(errorList12);
				double err = bi.find_error(wc, p1, n);
				if(err < minError)
				{
					minError = err;
					minIndex = bi.find_mid(x[j], x[j+1]);
					mindex = bi.find_mid(j, j+1);
					wrongClassified.clear();
					wrongClassified.addAll(wc);
				}
			}
			for(int j = 0; j < n-1 ; ++j)
			{
				ArrayList<Integer> errorList11 = bi.calculate_error(y, 0, j, -1);
				ArrayList<Integer> errorList12 = bi.calculate_error(y, j+1, n-1, 1);
				wc.clear();
				wc.addAll(errorList11);
				wc.addAll(errorList12);
				double err = bi.find_error(wc, p1, n);
				if(err < minError)
				{
					minError = err;
					minIndex = bi.find_mid(x[j], x[j+1]);
					mindex = bi.find_mid(j, j+1);
					lesser = ">";
					wrongClassified.clear();
					wrongClassified.addAll(wc);
				}
			}
			//System.out.println("Wrong Classified:" + wrongClassified);
			System.out.println("Classifier h = I(x " + lesser + " " + minIndex + ")");
			System.out.printf("Error = %.2f\n", minError);
			double weight = 0.5 * Math.log((1 - minError)/minError);
			System.out.printf("Alpha = %.2f\n", weight);
			q = bi.find_q(weight, wrongClassified, n);
			pq = bi.find_pq(p1, q, n);
			z[i] = bi.find_sum(pq, n);
			System.out.printf("Normalization Factor Z = %.2f\n", z[i]);
			System.out.print("Pi after normalization =");
			p1 = bi.find_new_p(pq, z[i], n);
			for(int w = 0; w < n; ++w)
			{
				System.out.printf(" %.2f", p1[w]);
				if(w != n-1)
				{
					System.out.print(",");
				}
			}
			System.out.println();
			bi.update_func(weight, i, lesser, minIndex);
			System.out.print("Boosted Classifier: f(x) = ");
			bi.display_func(i);
			System.out.println();
			ArrayList<Integer> errBoost = new ArrayList<Integer>();
			if(lesser.equalsIgnoreCase("<"))
			{
				errBoost.addAll(bi.calculate_error(y, 0, (int)(mindex-0.5), 1));
				errBoost.addAll(bi.calculate_error(y, (int)(mindex+0.5), n-1, -1));
			}
			else
			{
				errBoost.addAll(bi.calculate_error(y, 0, (int)(minIndex-0.5), -1));
				errBoost.addAll(bi.calculate_error(y, (int)(minIndex+0.5), n-1, 1));
			}
			System.out.printf("Boosted Classifier Error = %.2f\n", bi.find_prob(errBoost, p1));
			System.out.printf("Bound on Error = %.2f\n", bi.find_prod(z, i+1));
			System.out.println();
		}
		System.out.println();
		
		System.out.println("-----------------Part 2-----------------");
		System.out.println("-----------------Real AdaBoosting-----------------");
		System.out.println("Kaneria, Dhrupad Chandrakanth");
		Real re = new Real(1.0);
		double[] z2 = new double[t];
		for(int i = 0; i < t; ++i)
		{
			System.out.println("Iteration " + (i+1));
			double minIndex2 = 0.0;
			double mindex2 = 0.0;
			double minG = 0.0;
			double g = 0.0;
			double[] q2 = new double[n];
			double[] pq2 = new double[n];
			String lesser2 = "<";
			//ArrayList<Integer> wrongClassified2 = new ArrayList<Integer>();
			double prp = 0, prn = 0, pwp = 0, pwn = 0;
			double minprp = 0, minprn = 0, minpwp = 0, minpwn = 0;
			
			/*ArrayList<Integer> check1p = new ArrayList<Integer>();
			ArrayList<Integer> check2p = new ArrayList<Integer>();
			ArrayList<Integer> check1n = new ArrayList<Integer>();
			ArrayList<Integer> check2n = new ArrayList<Integer>();*/
			
			
			for(int j = 0; j < n-1 ; ++j)
			{
				
				/*check1p = re.find_no_error(y, 0, j, 1);
				check2p = re.find_no_error(y, j+1, n-1, 1);
				check1n = re.find_no_error(y, 0, j, -1);
				check2n = re.find_no_error(y, j+1, n-1, -1);
				//System.out.println("Half1p:" + check1p.size() + "  Half2p:" + check2p.size());
				//System.out.println("Half1n:" + check1n.size() + "  Half2n:" + check2n.size());
				if(check1.size() >= check2.size())
				{
					lesser2 = ">";
				}
				if(lesser2.equalsIgnoreCase("<"))
				{
					
				}
				check1p.clear();
				check1p.addAll(re.find_wrongClassify(y, 0, j, 1));
				check1p.addAll(re.find_wrongClassify(y, j+1, n-1, -1));
				System.out.println("Left Pos: " + check1p);
				
				check2p.clear();
				check2p.addAll(re.find_wrongClassify(y, 0, j, -1));
				check2p.addAll(re.find_wrongClassify(y, j+1, n-1, 1));
				System.out.println("Left neg: " + check2p);*/

				prp = re.find_prob(y, p2, 0, j, 1, 1);
				//prn = re.find_prob(y, p2, j+1, n-1, -1, -1);
				//pwp = re.find_prob(y, p2, j+1, n-1, -1, 1);
				prn = re.find_prob(y, p2, j+1, n-1, -1, -1);
				pwp = re.find_prob(y, p2, 0, n-1, -1, 1);
				pwn = re.find_prob(y, p2, 0, n-1, 1, -1);
				g = Math.sqrt(prp*prn) + Math.sqrt(pwp*pwn);
				//System.out.println("\nprp:" + prp + " prn:" + prn + " pwp:" + pwp + " pwn:" + pwn + " g:" + g);
				if(g > minG)
				{
					minIndex2 = re.find_mid(x[j], x[j+1]);
					mindex2 = re.find_mid(j, j+1);
					minG = g;
					minprp = prp;
					minprn = prn;
					minpwp = pwp;
					minpwn = pwn;
				}
				//System.out.println("g<: " + g);
			}
			/*for(int j = 0; j < n-1 ; ++j)
			{
				//prp = re.find_prob(y, p2, j+1, n-1, 1, 1);
				prp = re.find_prob(y, p2, 0, n-1, 1, 1);
				prn = re.find_prob(y, p2, 0, n-1, -1, -1);
				//pwp = re.find_prob(y, p2, j, n-1, -1, 1);
				pwp = re.find_prob(y, p2, 0, n-1, -1, 1);
				pwn = re.find_prob(y, p2, 0, n-1, 1, -1);
				g = Math.sqrt(prp*prn) + Math.sqrt(pwp*pwn);
				if(g > minG)
				{
					minIndex2 = re.find_mid(x[j], x[j+1]);
					mindex2 = re.find_mid(j, j+1);
					minG = g;
					lesser2 = ">";
					minprp = prp;
					minprn = prn;
					minpwp = pwp;
					minpwn = pwn;
				}
				//System.out.println("g>: " + g);
			}*/
			//System.out.println("prp:" + minprp + " prn:" + minprn + " pwp:" + minpwp + " pwn:" + minpwn);
			/*wrongClassified2.clear();
			if(lesser2.equalsIgnoreCase("<"))
			{
				wrongClassified2.addAll(re.find_wrongClassify(y, 0, (int)(minIndex2 - 0.5), 1));
				wrongClassified2.addAll(re.find_wrongClassify(y, (int)(minIndex2 + 0.5), n-1, -1));
			}
			else
			{
				wrongClassified2.addAll(re.find_wrongClassify(y, 0, (int)(minIndex2 - 0.5), -1));
				wrongClassified2.addAll(re.find_wrongClassify(y, (int)(minIndex2 + 0.5), n-1, 1));
			}*/
			//System.out.println("Wrong Classified: " + wrongClassified2);
			//epsilon = (1/(4*n));
			System.out.println("Classifier h = I(x " + lesser2 + " " + minIndex2 + ")");
			System.out.printf("G Error = %.2f\n", minG);
			double ctp = (0.5 * Math.log((minprp+epsilon)/(minprn+epsilon)));
			double ctn = (0.5 * Math.log((minpwp+epsilon)/(minpwn+epsilon)));
			System.out.printf("C_Plus = %.2f, C_Minus = %.2f", ctp, ctn);
			q2 = re.find_q(y, ctp, ctn, n);
			/*for(int w = 0; w < n; ++w)
			{
				System.out.printf("  %.6f", q2[w]);
			}System.out.println();*/
			pq2 = re.find_pq(y, q2, p2, n);
			z2[i] = re.find_sum(pq2, n);
			System.out.printf("\nNormalization Factor Z = %.2f\n", z2[i]);
			System.out.print("Pi after normalization =");
			p2 = re.find_new_p(pq2, z2[i], n);
			for(int w = 0; w < n; ++w)
			{
				System.out.printf(" %.2f", p2[w]);
				if(w != n-1)
				{
					System.out.print(",");
				}
			}
			re.update_func(minG, i, lesser2, minIndex2);
			//System.out.print("\nBoosted Classifier: f(x) = ");
			//re.display_func(i);
			
			
			System.out.print("\nf(x) =");
			for(int w = 0; w < n; ++w)
			{
				System.out.printf(" %.2f", re.find_value(re.f, y, w));
				if(w != n-1)
				{
					System.out.print(",");
				}
			}
			System.out.println();
			ArrayList<Integer> errBoost2 = new ArrayList<Integer>();
			if(lesser2.equalsIgnoreCase("<"))
			{
				errBoost2.addAll(re.calculate_error(y, 0, (int)(mindex2-0.5), 1));
				errBoost2.addAll(re.calculate_error(y, (int)(mindex2+0.5), n-1, -1));
			}
			else
			{
				errBoost2.addAll(re.calculate_error(y, 0, (int)(minIndex2-0.5), -1));
				errBoost2.addAll(re.calculate_error(y, (int)(minIndex2+0.5), n-1, 1));
			}
			System.out.printf("Boosted Classifier Error = %.2f\n", re.find_prob(errBoost2, p1));
			System.out.printf("Bound on Error = %.2f\n", re.find_prod(z2, i+1));

			System.out.println();
		}
	}
}
