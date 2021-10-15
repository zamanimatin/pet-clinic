package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.utility.SimpleDI;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import static  org.junit.Assert.*;
import java.util.*;

@RunWith (Parameterized.class)
class PetServiceTest {
//	private PetService petService;
	private Integer ID;
	private boolean bool;
//	private Pet pet;
//	private static Pet pet1;
//	private static Pet pet2;
//	private static Pet pet3;
//	private static Pet pet4;

	@BeforeEach
	public void setUp()
	{
		System.out.println("salam");
//		pet1 = new Pet();
//		pet2 = new Pet();
//		pet3 = new Pet();
//		pet4 = new Pet();

//		pet1.setId(1);
//		pet2.setId(2);
//		pet3.setId(3);
//		pet4.setId(4);
//
//
//		PetRepository repository = null;
//		PetTimedCache petTimedCache = new PetTimedCache(repository);
//
//
//
//		OwnerRepository owners = null;
//		Logger criticalLogger = null;
//		petService = new PetService(petTimedCache, owners, criticalLogger);
	}
	// public PetServiceTest(Pet expectedPet, p)
	public PetServiceTest(Integer ID, Boolean bool) {
		System.out.println("salam1");
		this.ID = ID;
		this.bool = bool;
	 }
	@Parameterized.Parameters
	public static Collection<Object[]> parameters()
	{
		return Arrays.asList(new Object[][]{
			{false, 1},
			{true, 2}
		});
	}

	@ParameterizedTest
	public void TestFindPet()
	{
		System.out.println("Pet Id is " + Integer.toString(ID));
		//Pet resultPet = this.petService.findPet(ID);
		//System.out.println(resultPet);

	}
}

