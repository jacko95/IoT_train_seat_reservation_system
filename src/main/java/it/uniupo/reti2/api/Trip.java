package it.uniupo.reti2.api;

import com.google.gson.annotations.SerializedName;

public class Trip {
	
	@SerializedName("@origin")
	public String origin;
	@SerializedName("@destination")
	public String destination;
	@SerializedName("@fare")
	public String fare;
	@SerializedName("@origTimeMin")
	public String origTimeMin;
	@SerializedName("@destTimeMin")
	public String destTimeMin;
	@SerializedName("@origTimeDate")
	public String origTimeDate;
	@SerializedName("@destTimeDate")
	public String destTimeDate;
	@SerializedName("@clipper")
	public String clipper;
	@SerializedName("@tripTime")
	public String tripTime;
	
}
