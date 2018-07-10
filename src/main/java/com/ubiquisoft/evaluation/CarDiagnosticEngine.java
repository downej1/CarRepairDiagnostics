package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.Map;

public class CarDiagnosticEngine {

	public void executeDiagnostics(Car car) {
		if (car == null) throw new IllegalArgumentException("Car must not be null");
		if (!areFieldsValid(car)) return;
		if (arePartsMissing(car)) return;
		if (arePartsDamaged(car)) return;
		System.out.println("Diagnostics run successfully, all parts present and in working condition");
	}

	private boolean arePartsDamaged(Car car) {
		boolean partsDamaged = false;
		for (Part p : car.getParts()) {
			if (!(p.getCondition() == ConditionType.NEW || p.getCondition() == ConditionType.GOOD || p.getCondition() == ConditionType.WORN)) {
				printDamagedPart(p.getType(), p.getCondition());
				partsDamaged = true;
			}
		}
		return partsDamaged;
	}

	private boolean arePartsMissing(Car car) {
		Map<PartType, Integer> missingParts = car.getMissingPartsMap();
		boolean noPartsMissing = missingParts.isEmpty();
		if (noPartsMissing) {
			return false;
		}
		else {
			for (PartType p : missingParts.keySet()) {
				printMissingPart(p, missingParts.get(p));
			}
			return true;
		}
	}

	private boolean areFieldsValid(Car car) {
		 boolean fieldsValid = true;
		if (car.getMake() == null || car.getMake().equals("")) {
			printMissingField("Make");
			fieldsValid = false;
		}
		if (car.getModel() == null || car.getModel().equals("")) {
			printMissingField("Model");
			fieldsValid = false;
		}
		if (car.getYear() == null || car.getYear().equals("")) {
			printMissingField("Year");
			fieldsValid = false;
		}
		return fieldsValid;
	}

	private void printMissingField(String field) {
		System.out.println("Missing Field(s) Detected: " + field);
	}
	private void printMissingPart(PartType partType, Integer count) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

		System.out.println(String.format("Missing Part(s) Detected: %s - Count: %s", partType, count));
	}

	private void printDamagedPart(PartType partType, ConditionType condition) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

		System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
	}

	public static void main(String[] args) throws JAXBException {
		// Load classpath resource
		InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar.xml");

		// Verify resource was loaded properly
		if (xml == null) {
			System.err.println("An error occurred attempting to load SampleCar.xml");

			System.exit(1);
		}

		// Build JAXBContext for converting XML into an Object
		JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Car car = (Car) unmarshaller.unmarshal(xml);

		// Build new Diagnostics Engine and execute on deserialized car object.

		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

		diagnosticEngine.executeDiagnostics(car);

	}

}
