package twins.additionalClasses;

public class Location {
	private double lat;
	private double lng;
	
	
	public Location() {
		
	}
	public Location(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}
	public double getX() {
		return lat;
	}
	public void setX(double x) {
		this.lat = x;
	}
	public double getY() {
		return lng;
	}
	public void setY(double y) {
		this.lng = y;
	}
	@Override
	public String toString() {
		return "Location [lat=" + lat + ", lng=" + lng + "]";
	}
}
