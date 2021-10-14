package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.messages.internal.com.google.common.collect.Lists;
import org.junit.*;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;

import static  org.junit.Assert.*;
import java.util.*;

class OwnerTest {

	private List<Pet> petList;
	private Owner owner;
	private Pet pet;
	private List<String> petNameList;
	private int numberOfPets;

	public void creatPetList(int numberOfPets)
	{
		for(int i = 0; i < numberOfPets; i++)	{
			Pet pet = new Pet();
			petList.add(pet);
			owner.addPet(pet);
		}
	}

	public void nameSetter(List<String> petNameList, int numberOfPets)
	{
		for(int i = 0; i < numberOfPets; i++)	{
			petList.get(i).setName(petNameList.get(i));
		}
	}

	public void idSetter(int numberOfPets)
	{
		for(int i = 0; i < numberOfPets; i++)	{
			petList.get(i).setId(i);
		}
	}

	@BeforeEach
	public void setUp()
	{
		petList = new ArrayList<Pet>();
		owner = new Owner();
		pet = new Pet();
		petNameList = new ArrayList<String>();
		numberOfPets = 3;

		for (int i = 0; i < numberOfPets; i++)
		{
			String temp = "IN" + Integer.toString(i);
			petNameList.add(temp);
		}

		System.out.println("Set up");
	}

	@DisplayName("addPet")
	@Test
	public void TestAddPet()
	{
		//Execution
		petList.add(pet);
		owner.addPet(pet);
		//Result Verification
		Set<Pet> petSet = new HashSet<Pet>(petList);
		Assertions.assertEquals(petSet, owner.getPetsInternal());

		System.out.println("Test 1");
	}

	@DisplayName("removePet Positive")
	@Test
	public void TestRemovePetPositive()
	{
		//Delegated Setup
		creatPetList(numberOfPets);
		//Execution
		Pet pet = petList.get(1);
		petList.remove(pet);
		owner.removePet(pet);
		//Result Verification
		Set<Pet> petSet = new HashSet<Pet>(petList);
		Assertions.assertEquals(petSet, owner.getPetsInternal());

		System.out.println("Test 2");
	}

	@DisplayName("removePet Negative")
	@Test
	public void TestRemovePetNegative()
	{
		//Delegated Setup
		creatPetList(numberOfPets);
		//Execution
		owner.removePet(pet);
		//Result Verification
		Set<Pet> petSet = new HashSet<Pet>(petList);
		Assertions.assertEquals(petSet, owner.getPetsInternal());

		System.out.println("Test 3");

	}

	@DisplayName("getPet Positive, ignoreNew = false")
	@Test
	public void TestGetPetPositive()
	{
		creatPetList(numberOfPets);
		nameSetter(petNameList, numberOfPets);

		Pet resultPet = owner.getPet("IN1", false);

		Assertions.assertEquals(petList.get(1), resultPet);

		System.out.println("Test 4");
	}

	@DisplayName("getPet Negative, ignoreNew = false")
	@Test
	public void TestGetPetNegative1()
	{
		creatPetList(numberOfPets);
		nameSetter(petNameList, numberOfPets);

		Pet resultPet = owner.getPet("Not-IN", false);

		Assertions.assertEquals(null, resultPet);

		System.out.println("Test 5");
	}

	@DisplayName("getPet Negative, ignoreNew = true, Set ID")
	@Test
	public void TestGetPetNegative2()
	{
		// Set up
		creatPetList(numberOfPets);
		nameSetter(petNameList, numberOfPets);
		idSetter(numberOfPets);

		Pet resultPet =  owner.getPet("IN1", true);

		Assertions.assertEquals(petList.get(1),resultPet);

		System.out.println("Test 6");
	}

	@DisplayName("getPet Negative, ignoreNew = true, Set ID")
	@Test
	public void TestGetPetPositive2()
	{
		//Set up
		creatPetList(numberOfPets);
		nameSetter(petNameList, numberOfPets);
		idSetter(numberOfPets);

		Pet resultPet = owner.getPet("Not-IN", true);

		Assertions.assertEquals(null, resultPet);

		System.out.println("Test 7");
	}

	@DisplayName("getPets")
	@Test
	public void TestGetPets()
	{

		creatPetList(numberOfPets);
		List<String> rev = Lists.reverse(petNameList);
		nameSetter(rev, numberOfPets);
		petList = Lists.reverse(petList);

		List<Pet> resultPetList = owner.getPets();

		Assertions.assertEquals(petList, resultPetList);

		System.out.println("Test 8");
	}

	@AfterEach
	public void tearDown()
	{
		petList = null;
		owner = null;
		pet = null;
		petNameList = null;

		System.out.println("Tear Down");
	}
}
//@RunWith(Theory.class)
//class OwnerTestTheory
//{
//
//}
