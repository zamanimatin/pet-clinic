@sample_annotation
Feature: Pet Service

#  Background: Sample General Preconditions Explanation
#    Given

  Scenario: An Owner Save a Pet
    Given There is a pet owner called "Amu Gholam"
    And There is some predefined pet types like "dog"
    When He performs save pet service to add a pet to his list
    Then The pet is saved successfully

  Scenario: An Owner Add a New Pet
    Given There is a pet owner called "Amu Gholam"
    When He performs new pet service to add a new pet to his list
    Then The new pet is saved successfully

  Scenario Outline: : Someone/thing (Doesn't or Does) Find a Owner
    Given There is a pet owner who his id is <ownerId>
    When Something performs find owner service to find a owner by its id <findOwnerId>
    Then The owner <isFound> found successfully

    Examples:
      | ownerId | findOwnerId | isFound |
      | 2       | 2           | is      |
      | 2       | 3           | is not  |

  Scenario Outline: Someone/thing (Doesn't or Does) Find a Pet
    Given There is a pet owner called "Amu Gholam"
    And There is some predefined pet types like "dog"
    And There is a pet who his id is <petId>
    When Something performs find pet service to find a pet by its id <findPetId>
    Then The pet <isFound> found successfully

    Examples:
      | petId | findPetId | isFound |
      | 2     | 2         | is      |
      | 2     | 3         | is not  |




