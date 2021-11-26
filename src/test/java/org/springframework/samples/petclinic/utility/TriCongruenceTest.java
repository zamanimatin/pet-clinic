package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(ReportingExtension.class)
@ClauseDefinition(clause = 'a', def = "t1arr[0] < 0")
@ClauseDefinition(clause = 'b', def = "t1arr[0] + t1arr[1] < t1arr[2]")
@ClauseDefinition(clause = 'c', def = "t1arr[0] != t2arr[0]")
@ClauseDefinition(clause = 'd', def = "t1arr[1] != t2arr[1]")
@ClauseDefinition(clause = 'e', def = "t1arr[2] != t2arr[2]")
class TriCongruenceTest {

	private static final Logger log = LoggerFactory.getLogger(TriCongruenceTest.class);


	@ClauseCoverage(
		predicate = "a || b",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = true)
		}
	)
	@Test
	public void Test1() {
		Triangle t1 = new Triangle(-2, 5, 7);
		Triangle t2 = new Triangle(-2, 5, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	@CACC(
		predicate = "a || b",
		majorClause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true)
		},
		predicateValue = true
	)
	@ClauseCoverage(
		predicate = "a || b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true)
		}
	)
	@Test
	public void Test2() {
		Triangle t1 = new Triangle(2, 3, 7);
		Triangle t2 = new Triangle(2, 3, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	@CACC(
		predicate = "a || b",
		majorClause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false)
		},
		predicateValue = false
	)
	@CACC(
		predicate = "a || b",
		majorClause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false)
		},
		predicateValue = false
	)
	@ClauseCoverage(
		predicate = "a || b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false)
		}
	)
	@Test
	public void Test3() {
		Triangle t1 = new Triangle(2, 5, 7);
		Triangle t2 = new Triangle(2, 5, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}

	@UniqueTruePoint(
		predicate = "c + d + e",
		dnf = "c + d + e",
		implicant = "c",
		valuations = {
			@Valuation(clause = 'c', valuation = true),
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false)
		}
	)
	@Test
	public void Test4() {
		Triangle t1 = new Triangle(2, 5, 7);
		Triangle t2 = new Triangle(3, 5, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	@UniqueTruePoint(
		predicate = "c + d + e",
		dnf = "c + d + e",
		implicant = "d",
		valuations = {
			@Valuation(clause = 'c', valuation = false),
			@Valuation(clause = 'd', valuation = true ),
			@Valuation(clause = 'e', valuation = false)
		}
	)
	@Test
	public void Test5() {
		Triangle t1 = new Triangle(2, 5, 7);
		Triangle t2 = new Triangle(2, 6, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	@UniqueTruePoint(
		predicate = "c + d + e",
		dnf = "c + d + e",
		implicant = "e",
		valuations = {
			@Valuation(clause = 'c', valuation = false),
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = true )
		}
	)
	@Test
	public void Test6() {
		Triangle t1 = new Triangle(2, 5, 7);
		Triangle t2 = new Triangle(2, 5, 8);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertFalse(areCongruent);
	}

	@NearFalsePoint(
		predicate = "c + d + e",
		dnf = "c + d + e",
		implicant = "c",
		clause = 'c',
		valuations = {
			@Valuation(clause = 'c', valuation = false),
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false)
		}
	)
	@NearFalsePoint(
		predicate = "c + d + e",
		dnf = "c + d + e",
		implicant = "d",
		clause = 'd',
		valuations = {
			@Valuation(clause = 'c', valuation = false),
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false)
		}
	)
	@NearFalsePoint(
		predicate = "c + d + e",
		dnf = "c + d + e",
		implicant = "e",
		clause = 'e',
		valuations = {
			@Valuation(clause = 'c', valuation = false),
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false)
		}
	)
	@Test
	public void Test7() {
		Triangle t1 = new Triangle(2, 5, 7);
		Triangle t2 = new Triangle(2, 5, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		log.debug("Triangles identified as '{}'.", areCongruent ? "Congruent" : "Not Congruent");
		Assertions.assertTrue(areCongruent);
	}

	/**
	 * We prove this phenomenon with a counter example.
	 */
	private static boolean questionTwo(boolean a, boolean b, boolean c, boolean d, boolean e) {

//		Suppose main predicate (f) as below
		boolean f = (a & b) | (b & !c);

//		Inverse of f
		boolean f_bar = (!b) | (!a & c);

		/*
		* Implicants are listed as below:
		* Implicants = {ab, b(!c), !b, (!a)c}
		*
		* Each implicant is prime, and non of them are redundant,
		* thus Unique True Points for each is as below:
		* ab = {TTT}, b(!c) = {FTF}, !b = {FFF, TFF, TFT}, (!a)c = {FTT}
		* Thus the UTPC set is as below:
		* {TTT, FTF, FFF, TFF, TFT, FTT}
		*
		* On the other hand for CUTPNFP, we find near false points for each implicant:
		* Implicant ab has 1 unique true point: {TTT}
		*	For clause a, we can pair unique true point TTT with near false point FTT
		*	For clause a, we can pair unique true point TTT with near false point TFT
		* Implicant b(!c) has 1 unique true point: {FTF}
		* 	For clause b, we can pair unique true point FTF with near false point FFF
		* 	For clause !c, we can pair unique true point FTF with near false point FTT
		* Implicant !b, has 3 unique true points: {FFF, TFF, TFT}
		* 	For clause !b, we can pair unique true point FFF with near false point FTF
		* Implicant (!a)c has 1 unique true point: {FTT}
		* 	For clause !a, we can pair unique true point FTT with near false point TTT
		* 	For clause c, we can pair unique true point FTT with near false point FTF
		* Thus CUTPNFP set is as below:
		* {TTT, FTF, FFF, FTT, TFT}
		*
		* A simple comparison between CUTPNFP set and UTPC set, will show that they are not equal
		* and CUTPNFP doesn't subsume UTPC. Although CUTPNFP does not subsume UTPC,
		*  CUTPNFP detects all fault classes that UTPC detects  (Converse is false)
		* */
		return f;
	}
}
