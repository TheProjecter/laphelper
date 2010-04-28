
public class GaussWeight implements Prototype {
	protected double x, y;
	
	public GaussWeight(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public String toString() {
		return " " + String.valueOf(x) + " " + String.valueOf(y);
	}
}
