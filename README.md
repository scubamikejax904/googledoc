# SmartThings Google Sheets Logging

step 1: Open spreadsheet https://docs.google.com/spreadsheets/d/1ebpE4c7P7X-fwd0X48Z_4hZzzrleKLiDd0sx7m-ebx4/edit?usp=sharing

step 2: Make a copy: Click File -> Make a Copy

step 3: Name spreadsheet whatever you want

step 4: Get your new spreadsheet id from URL, example:
https://docs.google.com/spreadsheets/d/17_zrk7Fh-4LKTRCIU3Ml2RruJ-WESyXfM5sIoyei8tk/edit#gid=0  
	example id is "17_zrk7Fh-4LKTRCIU3Ml2RruJ-WESyXfM5sIoyei8tk"
	
step 5: Open script: Click Tools -> Script Editor

Step 6: replace  "REPLACE ME WITH SPREADSHEET ID" with sheet id from step 4

Step 7: Deploy webapp: Click Publish -> Deploy as web app...

Step 8: Change Who has access to the app to "Anyone, even anonymous"

Step 9: Approve the access to the app

Step 10: Copy the url on the confirmation page:
https://script.google.com/macros/s/AKfycbz--wgluMsubLnhamicyJVFkYdcWw40SP9M5WoehX5ebBmUBGYV/exec

Step 11: Extract Url key for your new webapp, it is between /s/ and /exec: AKfycbz--wgluMsubLnhamicyJVFkYdcWw40SP9M5WoehX5ebBmUBGYV

Step 12 (Optional): Test out your new webapp, add this to the end of the url from step 10: ?Temp1=15&Temp2=30
ie: https://script.google.com/macros/s/AKfycbz--wgluMsubLnhamicyJVFkYdcWw40SP9M5WoehX5ebBmUBGYV/exec?Temp1=15&Temp2=30

Step 13 (Optional): Delete test data from spreadsheet, you should have new values in B2, B3, A1, A2, A3, delete all the test data

Step 14: In smartthings ide create new Smartapp From Code: https://github.com/cschwer/googleDocsLogging/blob/master/smartapps/cschwer/google-sheets-logging.src/google-sheets-logging.groovy

Step 15: In Smartthings App go to marketplace -> Smartapps -> My Apps -> Google Sheets Logging

Step 16: Select events you want to log under "Log devices..."

Step 17: Enter URL key from step 11 under "URL key"

Step 18: Click Done!
