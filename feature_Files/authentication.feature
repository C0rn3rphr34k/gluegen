Feature: Login and logout of a known user

Scenario: The user authenticates with valid credentials at the login portal
	Given the user is at the login portal
	And the user presents valid credentials
	When the user clicks the login button
	Then the user should be presented with his dashboard

Scenario: The user authenticates with an invalid password
	Given the user is at the login portal
	And the user presents an invalid password
	When the user clicks the login button
	Then the user should see the validation message "wrong password"

Scenario: The user authenticates with an unknown username
	Given the user is at the login portal
	And the user presents an unknown username
	When the user clicks the login button
	Then the user should be presented with the validation message "unknown username"

Scenario: The user is authenticated and logs out of his account
	Given the user is authenticated and is at his dashboard
	When the user clicks the "Logout" button on the menu bar
	Then the user should be presented with the login page