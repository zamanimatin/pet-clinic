package org.springframework.samples.petclinic.utility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.visit.Visit;

import java.util.*;
public class PriceCalculatorTest {

	private PriceCalculator priceCalculator;
	private double baseCharge;
	private double basePricePerPet;

	LocalDate yearsBeforeToday(int year)
	{
		LocalDate localDate = LocalDate.now();

		return localDate.minusYears(year);
	}

	LocalDate daysBeforeToday(long days)
	{
		LocalDate localDate = LocalDate.now();

		return localDate.minusDays(days);
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
		LocalDate birthDate = yearsBeforeToday(ageOfALLPets);
		for(Pet pet: petList)
		{
			when(pet.getBirthDate()).thenReturn(birthDate);
		}
	}

	void setPetListVisits(List<Pet> petList, long daysFromLastVisit)
	{
		LocalDate daysBeforeToday = daysBeforeToday(daysFromLastVisit);

		for(Pet pet: petList)
		{
			Visit visit = mock(Visit.class);
			List<Visit> visits = new ArrayList<Visit>();
			visits.add(visit);
			when(visit.getDate()).thenReturn(daysBeforeToday);
			when(pet.getVisitsUntilAge(anyInt())).thenReturn(visits);
		}
	}
	@BeforeEach
	void init()
	{
		priceCalculator = new PriceCalculator();

	}

	@Test
	void Given_PetListWithVisits_When_DaysFromLastVisitUnderLimit_PetsAreInfants()
	{
		baseCharge = 1000;
		basePricePerPet = 100;

		long daysFromLastVisit = 101;
		int petListSize = 5;
		int ageOfALLPets = 2;

		List<Pet> petList = createPetList(petListSize);

		setPetListBirthDate(petList, ageOfALLPets);
		setPetListVisits(petList, daysFromLastVisit);

		double totalPrice = priceCalculator.calcPrice(petList, baseCharge, basePricePerPet);

		assertEquals(3512, totalPrice);
	}

	@Test
	void Given_PetListWithVisits_When_DaysFromLastVisitAboveLimit_PetsAreInfants()
	{
		baseCharge = 1000;
		basePricePerPet = 100;

		long daysFromLastVisit = 99;
		int petListSize = 5;
		int ageOfALLPets = 2;

		List<Pet> petList = createPetList(petListSize);

		setPetListBirthDate(petList, ageOfALLPets);
		setPetListVisits(petList, daysFromLastVisit);

		double totalPrice = priceCalculator.calcPrice(petList, baseCharge, basePricePerPet);

		assertEquals(2512, totalPrice);
	}

	@Test
	void Given_ListOf5Pets_When_PetsAreNotInfants()
	{
		baseCharge = 1000;
		basePricePerPet = 100;

		int petListSize = 10;
		int ageOfALLPets = 3;

		List<Pet> petList = createPetList(petListSize);

		setPetListBirthDate(petList, ageOfALLPets);

		double totalPrice = priceCalculator.calcPrice(petList, baseCharge, basePricePerPet);

		assertEquals(3280, totalPrice);
	}

	@Test
	void Given_ListOf10Pets_When_PetsAreNotInfants()
	{
		baseCharge = 1000;
		basePricePerPet = 100;

		int petListSize = 5;
		int ageOfALLPets = 3;

		List<Pet> petList = createPetList(petListSize);

		setPetListBirthDate(petList, ageOfALLPets);

		double totalPrice = priceCalculator.calcPrice(petList, baseCharge, basePricePerPet);

		assertEquals(600, totalPrice);
	}


	@AfterEach
	void tearDown()
	{

	}
}
