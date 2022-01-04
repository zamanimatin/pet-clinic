package bdd;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import static org.junit.jupiter.api.Assertions.*;

public class PetServiceSteps {

	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	PetTypeRepository petTypeRepository;


	private Owner owner;
	private PetType petType;
	private Pet pet;
	private Integer ownerId;
	private Integer petId;

	void createOwner(String lastName)
	{
		owner = new Owner();
		owner.setFirstName("Agha");
		owner.setLastName(lastName);
		owner.setAddress("Khazaneh");
		owner.setCity("Tehran");
		owner.setTelephone("9092301202");
	}

	@Before("@sample_annotation")
	public void setup() {

	}

	@Given("There is some predefined pet types like {string}")
	public void thereIsSomePredefinedPetTypesLike(String petTypeName) {
		petType = new PetType();
		petType.setName(petTypeName);
		petTypeRepository.save(petType);
	}

	@Given("There is a pet owner called {string}")
	public void thereIsAPetOwnerCalled(String name) {
		createOwner(name);
		ownerRepository.save(owner);
	}

	//1
	@When("He performs save pet service to add a pet to his list")
	public void hePerformsSavePetService() {
		Pet pet = new Pet();
		pet.setType(petType);
		petService.savePet(pet, owner);
	}

	@Then("The pet is saved successfully")
	public void petIsSaved() {
		assertNotNull(petService.findPet(petType.getId()));
	}

	//2
	@When("He performs new pet service to add a new pet to his list")
	public void hePerformsNewPetService() {
		pet = petService.newPet(owner);
		pet.setId(5);
	}

	@Then("The new pet is saved successfully")
	public void newPetIsSaved() {
		assertNotNull(petService.findPet(pet.getId()));
		assertEquals(5, petService.findPet(pet.getId()).getId());
	}

	//3
	@Given("There is a pet owner who his id is {int}")
	public void thereIsAPetOwnerWithId(Integer ownerId) {
		createOwner("Masoud");
		owner.setId(ownerId);
		ownerRepository.save(owner);
		this.ownerId = ownerId;
	}

	@When("Something performs find owner service to find a owner by its id {int}")
	public void somethingPerformsFindOwner(Integer ownerId) {
		owner = petService.findOwner(ownerId);
	}

	@Then("The owner is found successfully")
	public void ownerIsFound() {
		assertNotNull(owner);
		assertEquals(ownerId, owner.getId());
	}

	@Then("The owner is not found successfully")
	public void ownerIsNotFound() {
		assertNotEquals(ownerId, owner.getId());
	}


	//4
	@Given("There is a pet who his id is {int}")
	public void thereIsAPetWithId(Integer petId) {
		Pet pet = new Pet();
		pet.setType(petType);
		pet.setId(petId);
		petService.savePet(pet, owner);
		this.petId = petId;
	}

	@When("Something performs find pet service to find a pet by its id {int}")
	public void somethingPerformsFindPet(Integer petId) {
		pet = petService.findPet(petId);
	}

	@Then("The pet is found successfully")
	public void petIsFound() {
		assertNotNull(pet);
		assertEquals(petId, pet.getId());
	}

	@Then("The pet is not found successfully")
	public void petIsNotFound() {
		assertNotEquals(petId, pet.getId());
	}
}
