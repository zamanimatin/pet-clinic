package org.springframework.samples.petclinic.model.PriceCalculators;


import org.joda.time.DateTime;
import org.joda.time.LocalDate;
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
import org.springframework.samples.petclinic.model.priceCalculators.CustomerDependentPriceCalculator;
import org.springframework.samples.petclinic.model.priceCalculators.PriceCalculator;
import org.springframework.samples.petclinic.model.priceCalculators.SimplePriceCalculator;

import java.util.*;

public class CustomerDependentPriceCalculatorTest {

	private PriceCalculator customerDPC;
	private double baseCharge;
	private double basePricePerPet;

	Date yearsBeforeToday(int year)
	{
		Calendar calendar = Calendar.getInstance();

		int yearsBeforeToday = calendar.get(Calendar.YEAR) - year;
		calendar.set(Calendar.YEAR, yearsBeforeToday);

		return calendar.getTime();
	}

	List<Pet> createPetList(int petListSize)
	{
		List<Pet> petList = new ArrayList<Pet>();

		for(int i=0; i<petListSize; i++)
		{
			Pet petMock = mock(Pet.class);
			petList.add(petMock);
		}

		return petList;
	}

	void setPetListBirthDate(List<Pet> petList, int ageOfALLPets)
	{
		Date birthDate = yearsBeforeToday(ageOfALLPets);

		for(Pet pet: petList)
		{
			when(pet.getBirthDate()).thenReturn(birthDate);
		}
	}

	void setPetListType(List<Pet> petList, PetType petType)
	{
		for(Pet pet: petList)
		{
			when(pet.getType()).thenReturn(petType);
		}
	}
	void setTypeExpectation(PetType petRareTypeMock, PetType petNonRareTypeMock)
	{
		when(petRareTypeMock.getRare()).thenReturn(true);
		when(petNonRareTypeMock.getRare()).thenReturn(false);
	}

	@BeforeEach
	void init()
	{
		customerDPC = new CustomerDependentPriceCalculator();

	}

	@Test
	void When_UserTypeNew_TypeRare_AgeInfant()
	{

		baseCharge = 1000;
		basePricePerPet = 100;
		UserType userType =  UserType.NEW;

		int petListSize = 5;
		int ageOfALLPets = 2;

		List<Pet> petList = createPetList(petListSize);

		PetType petRareTypeMock = mock(PetType.class);
		PetType petNonRareTypeMock = mock(PetType.class);

		setPetListBirthDate(petList, ageOfALLPets);

		setTypeExpectation(petRareTypeMock, petNonRareTypeMock);

		setPetListType(petList, petRareTypeMock);

		double totalPrice = customerDPC.calcPrice(petList, baseCharge, basePricePerPet, userType);

		assertEquals(1798, totalPrice);
	}

	@Test
	void When_UserTypeGold_TypeRare_AgeNonInfant()
	{

		baseCharge = 1000;
		basePricePerPet = 100;
		UserType userType =  UserType.GOLD;

		int petListSize = 10;
		int ageOfALLPets = 3;

		List<Pet> petList = createPetList(petListSize);

		PetType petRareTypeMock = mock(PetType.class);
		PetType petNonRareTypeMock = mock(PetType.class);

		setPetListBirthDate(petList, ageOfALLPets);

		setTypeExpectation(petRareTypeMock, petNonRareTypeMock);

		setPetListType(petList, petRareTypeMock);

		double totalPrice = customerDPC.calcPrice(petList, baseCharge, basePricePerPet, userType);

		assertEquals(1760, totalPrice);
	}

	@Test
	void When_UserTypeGold_TypeNonRare_AgeInfant()
	{

		baseCharge = 1000;
		basePricePerPet = 100;
		UserType userType =  UserType.GOLD;

		int petListSize = 5;
		int ageOfALLPets = 2;

		List<Pet> petList = createPetList(petListSize);

		PetType petRareTypeMock = mock(PetType.class);
		PetType petNonRareTypeMock = mock(PetType.class);

		setPetListBirthDate(petList, ageOfALLPets);

		setTypeExpectation(petRareTypeMock, petNonRareTypeMock);

		setPetListType(petList, petNonRareTypeMock);

		double totalPrice = customerDPC.calcPrice(petList, baseCharge, basePricePerPet, userType);

		assertEquals(1480, totalPrice);
	}

	@Test
	void When_UserTypeSilver_TypeNonRare_AgeNonInfant()
	{

		baseCharge = 1000;
		basePricePerPet = 100;
		UserType userType =  UserType.SILVER;

		int petListSize = 5;
		int ageOfALLPets = 3;

		List<Pet> petList = createPetList(petListSize);

		PetType petRareTypeMock = mock(PetType.class);
		PetType petNonRareTypeMock = mock(PetType.class);

		setPetListBirthDate(petList, ageOfALLPets);

		setPetListType(petList, petNonRareTypeMock);

		when(petRareTypeMock.getRare()).thenReturn(true);
		when(petNonRareTypeMock.getRare()).thenReturn(false);

		double totalPrice = customerDPC.calcPrice(petList, baseCharge, basePricePerPet, userType);

		assertEquals(500, totalPrice);
	}
	@AfterEach
	void tearDown()
	{

	}
}
