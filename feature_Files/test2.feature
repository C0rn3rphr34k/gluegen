Feature: Reading a second feature file is fun
As a software test engineer I'd like gcgen to be able to read more than one file in a single run,
so if I point it to a directory with multiple feature files,
I get gluecode classes for all of them

Scenario: reading the first scenario
Given I perform a gcgen run on a directory with multiple files
When gcgen does it's thing
Then multiple gluecode classes should be created