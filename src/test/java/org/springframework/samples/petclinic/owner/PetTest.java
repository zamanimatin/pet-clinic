package org.springframework.samples.petclinic.owner;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.junit.Assume.assumeTrue;


@RunWith(Theories.class)
public class PetTest {

	private static Visit visit1 = new Visit();
	private static Visit visit2 = new Visit();
	private static Visit visit3 = new Visit();
	private static Visit visit4 = new Visit();

	@DataPoints
	public static Visit[] visitList1()
	{
		LocalDate date1 = LocalDate.of(2020, Month.JANUARY	, 8);
		LocalDate date2 = LocalDate.of(2020, Month.FEBRUARY, 9);
		LocalDate date3 = LocalDate.of(2018, Month.JANUARY	, 7);
		LocalDate date4 = LocalDate.of(2018, Month.FEBRUARY, 18);

		visit1.setDate(date1);
		visit2.setDate(date2);
		visit3.setDate(date3);
		visit4.setDate(date4);

		return new Visit[]{visit1, visit2, visit3, visit4};
	}

	@Theory
	public void TestGetVisits(Visit visit1, Visit visit2)
	{

		Pet pet = new Pet();

		List<Visit> visitList = new ArrayList<Visit>();
		visitList.add(visit1);
		visitList.add(visit2);

		pet.setVisitsInternal(visitList);

		Comparator<Visit> compareByDate =
			(Visit v1, Visit v2) -> (v2.getDate().compareTo(v1.getDate()));
		Collections.sort(visitList, compareByDate);

		List<Visit> resultVisitList = pet.getVisits();

		assumeTrue(resultVisitList.size()==visitList.size());

		Assertions.assertEquals(visitList, resultVisitList);
		System.out.println("result");
		System.out.println(resultVisitList.get(0).getDate());
		System.out.println(resultVisitList.get(1).getDate());
		System.out.println("expected");
		System.out.println(visitList.get(0).getDate());
		System.out.println(visitList.get(1).getDate());
		System.out.println("Theory");
	}

}

