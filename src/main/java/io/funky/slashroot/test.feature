Feature: Parsing a feature file
As a software test automation engineer
I'd like to be able to generate glue code automatically with gcgen
In order to save a lot of time writing tests

Scenario: Reading a test feature file from the same directory
	Given there is a file called "test.feature" in the execution directory
	When I open that file for reading
	Then the file contents should get printed to the console