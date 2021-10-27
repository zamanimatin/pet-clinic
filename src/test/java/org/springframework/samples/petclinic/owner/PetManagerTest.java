package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.utility.SimpleDI;
import org.springframework.samples.petclinic.visit.Visit;

import javax.management.OperationsException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PetManagerTest {

	private PetManager petManger;
	private PetTimedCache petsMock = Mockito.mock(PetTimedCache.class);;
	private OwnerRepository ownersMock = Mockito.mock(OwnerRepository.class);
	private Logger logMock = Mockito.mock(Logger.class);

	@BeforeEach
	void setUp()
	{
		petManger = new PetManager(petsMock, ownersMock, logMock);
	}

	@Test
//	behavior verification - Mockisty
	void testSavePetSuccess()
	{
//		setup
//		dummy: pet
		Pet pet = new Pet();
//		stub: owner
		Owner owner = Mockito.mock(Owner.class);

//		exercise
		petManger.savePet(pet, owner);
//		verify
		verify(owner).addPet(pet);
		verify(petsMock).save(pet);
	}

//	state verification - Classical, only Owner was mocked
	@Test
	void testGetOwnerPetTypes()
	{
//		setup
		PetType cat = new PetType();
		cat.setName("cat");
		PetType dog = new PetType();
		dog.setName("dog");

		Pet pet1 = new Pet();
		pet1.setType(cat);
		pet1.setName("jack");

		Pet pet2 = new Pet();
		pet2.setType(dog);
		pet2.setName("bill");

		Owner owner = Mockito.mock(Owner.class);
		Set <Pet> pets = new HashSet<Pet>();
		pets.addAll(Arrays.asList(new Pet[] {pet1, pet2}));
		owner.setPetsInternal(pets);
		owner.setId(1);

		Set<PetType> petsType = new HashSet<>();
		petsType.addAll(Arrays.asList(new PetType[] {cat, dog}));
		List<Pet> petsList = new ArrayList<>(pets);

//		expectation
		Mockito.when(petManger.findOwner(1)).thenReturn(owner);
		Mockito.when(owner.getPets()).thenReturn(petsList);


//		exercise
		Set<PetType> ResultTypes = petManger.getOwnerPetTypes(1);

//		verify
		assertEquals(ResultTypes, petsType);
	}

	@Test
//	behavior verification - Mockisty
	void testGetOwnerPets()
	{
//		setup
//		Stub: owner
		Owner ownerMock = Mockito.mock(Owner.class);
		ownerMock.setId(1);
//		dummy: petMock1, petMock2
		Pet petMock1 = Mockito.mock(Pet.class);
		Pet petMock2 = Mockito.mock(Pet.class);
		List <Pet> petsList = Arrays.asList(petMock1, petMock2);

//		expectation
		Mockito.when(ownersMock.findById(1)).thenReturn(ownerMock);
		Mockito.when(ownerMock.getPets()).thenReturn(petsList);

//		exercise
		List <Pet> ResultPets = petManger.getOwnerPets(1);

//		verify
		verify(ownersMock).findById(1);
		verify(ownerMock).getPets();
		assertEquals(ResultPets, petsList);

	}

//	state verificaion - Mockisty
	@Test
	void testFindOwnerIfInOwnerRepository()
	{
		//ownersMock: stub, logMock: dummy, petsMock: dummy

		//setup
		//owner1, owner2: dummy
		Owner owner1 = Mockito.mock(Owner.class);
		Owner owner2 = Mockito.mock(Owner.class);

		Mockito.when(ownersMock.findById(1)).thenReturn(owner1);
		Mockito.when(ownersMock.findById(2)).thenReturn(owner2);

		//exercise
		Owner resultOwner = petManger.findOwner(1);

		//verify
		assertEquals(resultOwner, owner1);
	}

//	state verification - Mockisty
	@Test
	void testFindOwnerIfNotInOwnerRepository()
	{

		//ownersMock: stub, logMock: dummy, petsMock: dummy

		//setup
		//owner1, owner2: dummy
		Owner owner1 = Mockito.mock(Owner.class);


		Mockito.when(ownersMock.findById(1)).thenReturn(owner1);
		Mockito.when(ownersMock.findById(2)).thenReturn(null);

		//exercise
		Owner resultOwner = petManger.findOwner(2);

		//verify
		assertEquals(resultOwner, null);
	}

	//	behavior verification - Mockisty
	@Test
	void Given_Owner_When_AddNewPet_Then_PetWillAdded()
	{
		//ownersMock: dummy, logMock: dummy

		//setup
		//owner: stub
		Owner owner = Mockito.mock(Owner.class);
		//pet: dummy
		Pet pet = petManger.newPet(owner);

		//verify
		verify(owner).addPet(any(Pet.class));


	}

	//	state verification - Mockisty
	@Test
	void Given_PetIdInCache_When_GivePetID_Then_PetWillBeGiven()
	{
		//ownersMock: dummy, logMock: dummy, petsMock: stub

		//setup
		//pet1, pet2: dummy
		Pet pet1 = Mockito.mock(Pet.class);
		Pet pet2 = Mockito.mock(Pet.class);

		Mockito.when(petsMock.get(1)).thenReturn(pet1);
		Mockito.when(petsMock.get(2)).thenReturn(pet2);

		//exercise
		Pet resultPet = petManger.findPet(1);

		//verify
		assertEquals(resultPet, pet1);
	}

	//	state verification - Mockisty
	@Test
	void Given_PetIdNotInCache_When_GivePetID_Then_NullWillBeGiven ()
	{
		//ownersMock: dummy, logMock: dummy, petsMock: stub

		//setup
		//pet1 dummy
		Pet petMock1 = Mockito.mock(Pet.class);

		Mockito.when(petsMock.get(1)).thenReturn(petMock1);
		Mockito.when(petsMock.get(2)).thenReturn(null);

		//exercise
		Pet resultPet = petManger.findPet(2);

		//verify
		assertEquals(resultPet, null);
	}

	//	state verification - Mockisty
	@Test
	void Given_OwnerHasPets_When_AskForOwnerPets_Then_PetsListWillBeGiven()
	{
		//
		Owner ownerMock = Mockito.mock(Owner.class);
		Pet petMock1 = Mockito.mock(Pet.class);
		Pet petMock2 = Mockito.mock(Pet.class);
		List<Pet> petList = Arrays.asList(petMock1, petMock2);

		when(ownersMock.findById(anyInt())).thenReturn(ownerMock);
		when(ownerMock.getPets()).thenReturn(petList);

		List<Pet> resultPetList = petManger.getOwnerPets(5);

		assertEquals(resultPetList, petList);
	}

	//	state verification - Mockisty
	@Test
	void Given_OwnerHasVisits_When_AskForOwnerVisits_Then_OwnerVisitsWillBeGiven()
	{
		//petsMock: stub, ownersMock: dummy, logMock: dummy

		//set up
		//visitMock1, visitMock2: dummy
		Visit visitMock1 = Mockito.mock(Visit.class);
		Visit visitMock2 = Mockito.mock(Visit.class);
		List<Visit> visitList = Arrays.asList(visitMock1, visitMock2);

		//startDate, endDate: dummy
		LocalDate startDate = LocalDate.of(2020, Month.JANUARY, 8);
		LocalDate endDate = LocalDate.of(2020, Month.JANUARY, 20);

		//petMock: stub
		Pet petMock = Mockito.mock(Pet.class);
		when(petsMock.get(anyInt())).thenReturn(petMock);
		when(petMock.getVisitsBetween(startDate, endDate)).thenReturn(visitList);

		//execution
		List<Visit> resultVisitList = petManger.getVisitsBetween(1, startDate, endDate);

		//verify
		assertEquals(visitList, resultVisitList);
	}

	@AfterEach
	void tearDown()
	{

	}
}
