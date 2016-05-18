# SmartThings Google Sheets Logging

step 1: Create a new spreadsheet: http://docs.google.com/spreadsheets/create

step 2: Name the spreadsheet whatever you want

step 3: Get your new spreadsheet ID from URL. Example:
https://docs.google.com/spreadsheets/d/169v40OsFOaGHO6uQwuuMx2hlWK-wvYCzrr93FAWivHk/edit#gid=0  
	example id is "169v40OsFOaGHO6uQwuuMx2hlWK-wvYCzrr93FAWivHk"
	
![alt tag](img/stgsl4.png)

step 4: Add the single title "Time" in cell A1.  You might consider selecting View -> Freeze -> 1 Row 

step 5: Open script: Click Tools -> Script Editor.

![alt tag](img/stgsl5.png)

Step 6a: Copy the contents of Code.gs to replace the stub "function myFunction"

Step 6b: On line 4, Replace "REPLACE ME WITH SPREADSHEET ID" with sheet id from step 3

![alt tag](img/stgsl6.png)

Step 6c: If you change the name of the sheet, update it on line 5. (defaults to "Sheet1").

Step 6d: Name the project (change "Untitiled project")

Step 7: Deploy webapp: Click Publish -> Deploy as web app...

![alt tag](img/stgsl7.png)

Step 8: Change Who has access to the app to "Anyone, even anonymous".  Please note, if any one gets a hold of your published endpoint, they will be able to send data to your spreadsheet but they will not be able to view any of it.

![alt tag](img/stgsl8.png)

Step 9: Approve the access to the app

![alt tag](img/stgsl9.png)
![alt tag](img/stgsl9b.png)

Step 10: Copy the url on the confirmation page, Example:
https://script.google.com/macros/s/AKfycbzY2jj4l7RSpFYfN62xra0HmcXPQXAUI17z6KKHWiT3OYyhUC4/exec

![alt tag](img/stgsl10.png)

Step 11: Extract Url key for your new webapp, it is between /s/ and /exec: AKfycbzY2jj4l7RSpFYfN62xra0HmcXPQXAUI17z6KKHWiT3OYyhUC4

Step 12 (Optional): Test out your new webapp, add this to the end of the url from step 10: ?Temp1=15&Temp2=30
ie: https://script.google.com/macros/s/AKfycbzY2jj4l7RSpFYfN62xra0HmcXPQXAUI17z6KKHWiT3OYyhUC4/exec?Temp1=15&Temp2=30

Step 13 (Optional): Delete test data from spreadsheet, you should have new values in B1, C1, A2, B2, C2, delete all the test data

Step 14: In the SmartThings IDE, create a new Smartapp From Code using
smartapps/cschwer/google-sheets-logging.src/google-sheets-logging.groovy

Step 15: In Smartthings App go to marketplace -> Smartapps -> My Apps -> Google Sheets Logging

Step 16: Select events you want to log under "Log devices..."

Step 17: Enter URL key from step 11 under "URL key"

Step 18: Click Done!
