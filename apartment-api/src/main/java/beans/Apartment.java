package beans;

public class Apartment {
	private String location;
	private int id;
	private int sqft;
	private int beds;
	private int baths;
	private int price;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSqft() {
		return sqft;
	}

	public void setSqft(int sqft) {
		this.sqft = sqft;
	}

	public int getBeds() {
		return beds;
	}

	public void setBeds(int beds) {
		this.beds = beds;
	}

	public int getBaths() {
		return baths;
	}

	public void setBaths(int baths) {
		this.baths = baths;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	// empty constructor
	public Apartment() {
	}

	// full constructor
	public Apartment(String location, int id, int sqft, int beds, int baths, int price) {
		super();
		this.location = location;
		this.id = id;
		this.sqft = sqft;
		this.beds = beds;
		this.baths = baths;
		this.price = price;
	}

	// constructor for apartments to be placed into the db
	public Apartment(String location, int sqft, int beds, int baths, int price) {
		super();
		this.location = location;
		this.sqft = sqft;
		this.beds = beds;
		this.baths = baths;
		this.price = price;
	}
	//constructor for immediate deletion
	public Apartment(int id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "Apartment [location=" + location + ", id=" + id + ", sqft=" + sqft + ", beds=" + beds + ", baths="
				+ baths + ", price=" + price + "]";
	};

}
