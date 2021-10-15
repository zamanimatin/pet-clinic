package org.springframework.samples.petclinic.owner;

import org.assertj.core.util.Sets;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class OwnerTestTheory
{
	private static Pet pet1 = new Pet();
	private static Pet pet2 = new Pet();
	private static Pet pet3 = new Pet();
	private static Pet pet4 = new Pet();

	@DataPoints
	public static Pet[] petList()
	{
		String name1 = "A1";
		String name2 = "A2";
		String name3 = "A3";
		String name4 = "A4";

		pet1.setName(name1);
		pet2.setName(name2);
		pet3.setName(name3);
		pet4.setName(name4);

		return new Pet[]{pet1, pet2, pet3, pet4};
	}

	@Theory
	public void TestGetPets(Pet pet1, Pet pet2)
	{

		Owner owner = new Owner();

		List<Pet> petList = new ArrayList<Pet>();
		petList.add(pet1);
		petList.add(pet2);

		owner.setPetsInternal(Sets.newHashSet(petList));

		Comparator<Pet> compareByDate =
			(Pet p1, Pet p2) -> (p1.getName().compareTo(p2.getName()));
		Collections.sort(petList, compareByDate);

		List<Pet> resultPetList = owner.getPets();

		assumeTrue(resultPetList.size()==petList.size());

		Assertions.assertEquals(petList, resultPetList);

		System.out.println("result");
		System.out.println(resultPetList.get(0).getName());
		System.out.println(resultPetList.get(1).getName());
		System.out.println("expected");
		System.out.println(petList.get(0).getName());
		System.out.println(petList.get(1).getName());
		System.out.println("Theory");
	}
}
