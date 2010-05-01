
public class PrototypeWeight {
	protected double[] w;
	
	public PrototypeWeight(double[] w) {
		this.w = w;
	}
	
	public double[] getW() {
		return w;
	}
	
	public String toString() {
		String s = String.valueOf(w[0]);
		for(int i = 1; i < w.length; i++) {
			s += " " + String.valueOf(w[i]);
		}
		return s;
	}
}
