package bdtfinal;

public class PopulationDTO {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String countryName;
	private String population2022;
	private String population2020;
	private String population2015;
	private String population1970;
	private String area;
	private String density;
	private String growthRate;
	private String worldPopulationPercentage;
	
	public String getPopulation2020() {
		return population2020;
	}
	public void setPopulation2020(String population2020) {
		this.population2020 = population2020;
	}
	public String getPopulation2015() {
		return population2015;
	}
	public void setPopulation2015(String population2015) {
		this.population2015 = population2015;
	}
	public String getPopulation1970() {
		return population1970;
	}
	public void setPopulation1970(String population1970) {
		this.population1970 = population1970;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getPopulation2022() {
		return population2022;
	}
	public void setPopulation2022(String population2022) {
		this.population2022 = population2022;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDensity() {
		return density;
	}
	public void setDensity(String density) {
		this.density = density;
	}
	public String getGrowthRate() {
		return growthRate;
	}
	public void setGrowthRate(String growthRate) {
		this.growthRate = growthRate;
	}
	public String getWorldPopulationPercentage() {
		return worldPopulationPercentage;
	}
	public void setWorldPopulationPercentage(String worldPopulationPercentage) {
		this.worldPopulationPercentage = worldPopulationPercentage;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
