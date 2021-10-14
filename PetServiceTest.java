package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
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
	private PetService petService;

	private Pet expectedPet;
	private Pet pet1;
	private Pet pet2;
	private Pet pet3;

	@BeforeEach
	public void setUp()
	{
		PetTimedCache pets = null;
		OwnerRepository owners = null;
		Logger criticalLogger = null;
		petService = new PetService(pets, owners, criticalLogger);
	}
	public PetServiceTest(Pet expectedPet, p)
	@Parameterized.Parameters
	public static Collection<Object[]> parameters()
	{
		return Arrays.asList(new Object[][]{});
	}
}
