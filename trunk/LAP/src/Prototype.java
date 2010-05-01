
public class Prototype {
	private double[] v;
	protected int cls;
	
	public Prototype(double[] v,int cls) {
		this.v = v;
		this.cls = cls;
	}

	public double[] getV() {
		return v;
	}
	
	public int getCls() {
		return cls;
	}
	
	public String toString() {
		String s = String.valueOf(v[0]);
		for(int i = 1; i < v.length; i++) {
			s += " " + String.valueOf(v[i]);
		}
		return s;
	}
}
