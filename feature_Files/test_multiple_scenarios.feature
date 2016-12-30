Feature: Login, logout, and account deletion of a registered user
As a registered user
I'd like to be able to login, logout and delete my user account
to have control over who uses my account

Scenario: Login with existing user credentials
Given a user "john" exists
When the user logs in with valid credentials
Then the user is presented with the dashboard

Scenario: Logout from the dahsboard
Given a user "john" is logged in
When the user clicks the logout button on the dashboard
Then the user is presented with the login page

Scenario: Deletion of a user account
Given a user "john" exists and is logged in
When the user chooses to delete the account
Then the user is required to enter the password
And the user is presented with the login page
And the message "Your account has been deleted successfully" is shown