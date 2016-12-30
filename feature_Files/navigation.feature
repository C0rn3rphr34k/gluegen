Feature: Using the dashboard of a logged in user to navigate

Scenario: An authenticated user navigates to his profile
	Given the user is authenticated and at his dashboard
	When the user clicks his username
	Then the application should navigate to the users profile

Scenario: An authenticated user navigates to the news page
	Given the user is authenticated and at his dashboard
	When the user clicks "News" on the menu bar
	Then the application should navigate to the news page

Scenario: An authenticated user navigates to his inbox
	Given the user is authenticated and at his dashboard
	When the user clicks "Inbox" on the menu bar
	Then the application should navigate to the users inbox