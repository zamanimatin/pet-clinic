package org.springframework.samples.petclinic.model.PriceCalculators;



import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;
import org.springframework.samples.petclinic.model.priceCalculators.PriceCalculator;
import org.springframework.samples.petclinic.model.priceCalculators.SimplePriceCalculator;

import java.util.Arrays;
import java.util.List;

public class SimplePriceCalculatorTest {

	private PriceCalculator simplePriceCalculator;
	private Pet pet;
	private double baseCharge, basePricePerPet;
	private UserType userType;

	@BeforeEach
	public void init()
	{
		simplePriceCalculator = new SimplePriceCalculator();
		pet = new Pet();
		baseCharge = 1000;
		basePricePerPet = 100;
	}

	@Test
	public void When_UserTypeNewAndPetsTypeNonRare()
	{
		PetType typeNonRareMock = mock(PetType.class);
		pet.setType(typeNonRareMock);

		List<Pet> petList = Arrays.asList(pet);

		userType = UserType.NEW;

		when(typeNonRareMock.getRare()).thenReturn(false);

		double result = simplePriceCalculator.calcPrice(petList, baseCharge, basePricePerPet, userType);

		double actual = 1100 * userType.discountRate;

		assertEquals(result, actual);

	}

	@Test
	public void When_UserTypeGoldAndPetsTypeRare()
	{

		PetType typeRareMock = mock(PetType.class);
		pet.setType(typeRareMock);

		List<Pet> petList = Arrays.asList(pet);

		userType = UserType.GOLD;

		when(typeRareMock.getRare()).thenReturn(true);

		double result = simplePriceCalculator.calcPrice(petList, baseCharge, basePricePerPet, userType);

		assertEquals(result, 1120);
	}

	@AfterEach
	public void tearDown()
	{

	}
}
