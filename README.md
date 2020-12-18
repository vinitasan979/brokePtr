# Broke Petr 
Broke Peter is a spending tracker that records your transactions and analyzes the users spending trends. This Android Application will allow users to see where and when they spend the most. Google firebase to store and manage the user’s data. Android Studio and Java will be used to implement the UI and all other aspects of this projects

## How to Run the app
* This app must be run using android studio. 
* Download project zip file and unpack 
* Open project on android studio 
* Create an emulator to view app 
    * https://developer.android.com/studio/run/managing-avds
    * Prefered emulator is Nexus 5x API 26
* click the play button to run the app 
* you can either create a new account or use the existing one
* Existing account 
    * username: cd@email.com
    * password: test123

## Functionality and Changes Made  
* **Home Page** : The Home page contain current date and users name.
    * *Changes: Total number of transaction was removed from this page. Instead a button that takes you to the page to add transaction was added. I did so because the button seems more useful and makes a less cluttered design*
* **User Reports Page**: Show average spending by category using a pie chart and the total Number of transactions logged 
    * Changes: Visual contain Average spending data per month was removed. This app does ot allow to filter by month and year. Hence, a particular month will include that month of all year, making the data inaccurate and does not serve the orginal purpose 
    * Displaying visuals for every month also creates a cluttered and filled UI. 
* **Login and Sign up Pages** : Uses firebase authenication to add a user,  log a user in, and display error messages. Automatically logged out when you exit the app for security. No requirements of these pages were changed. 
* **Transaction Dairy** : Display all previous transaction logged by the user. User can pick which category they wish to see or if they wish to see all transaction. No requirements of these pages were changed 
* **Search page** : User can search a product in the logs. Not case sensitive. Has to be an exact match.No requirements of these pages were changed.

## DEMO
<img src='https://github.com/vinitasan979/brokePtr/blob/master/brokePtr_demo.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />







