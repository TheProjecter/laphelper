
public class GaussPrototype extends GaussWeight {
	
	private int cls;
	
	public GaussPrototype(double x, double y, int cls) {
		super(x, y);
		this.cls = cls;
	}

	public int getCls() {
		return cls;
	}
	
	public String toString() {
		return String.valueOf(x) + " " + String.valueOf(y) + " " + String.valueOf(cls);
	}
}
