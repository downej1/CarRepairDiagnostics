package com.ubiquisoft.evaluation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;

	private List<Part> parts;

	public Map<PartType, Integer> getMissingPartsMap() {
		Map<PartType, Integer> missingParts = new HashMap<PartType, Integer>();
		missingParts.put(PartType.ENGINE, 1);
		missingParts.put(PartType.ELECTRICAL, 1);
		missingParts.put(PartType.FUEL_FILTER, 1);
		missingParts.put(PartType.OIL_FILTER, 1);
		missingParts.put(PartType.TIRE, 4);
		if (parts.isEmpty()) return missingParts;
		for (Part p : parts) {
			switch (p.getType()) {
				case ENGINE:
					missingParts.remove(PartType.ENGINE);
					break;
				case ELECTRICAL:
					missingParts.remove(PartType.ELECTRICAL);
					break;
				case FUEL_FILTER:
					missingParts.remove(PartType.FUEL_FILTER);
					break;
				case OIL_FILTER:
					missingParts.remove(PartType.OIL_FILTER);
					break;
				case TIRE:
					if (missingParts.get(PartType.TIRE) == 1) {
						missingParts.remove(PartType.TIRE);
					}
					else {
						missingParts.put(PartType.TIRE, missingParts.get(PartType.TIRE) - 1);
					}
					break;
			}
		}

		return missingParts;
	}

	@Override
	public String toString() {
		return "Car{" +
				       "year='" + year + '\'' +
				       ", make='" + make + '\'' +
				       ", model='" + model + '\'' +
				       ", parts=" + parts +
				       '}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
