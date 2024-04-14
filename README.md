# OOPP Project Group 23

## **Run the Project**

1) Navigate to the project and open your terminal.
Your terminal should look something like this: C:\...\oopp-team-23>
Now type the following comments:
`./gradlew build`, `./gradlew bootRun`. This will build the project and start a server.
In the server output the password for the admin pages will be shown.
With `./gradlew run` the client application can be started.
By inputting this multiple times multiple client instances
can be opened to test the websocket and long polling.
2) Clone the GitLab Repository using the terminal `(git clone URL)`. Launch IntelliJ IDEA on your computer. Click `Open` to load the project into IntelliJ IDEA. Once the project is loaded, open Gradle in the sidebar. Now you can run the server with `bootRun` and the client with `run`.



## **Implemented Features**

- Live Language Switch: 
  - This feature has been fully implemented. 
  - On the start screen and the event overview page the user can switch the language. This is persisted through the whole application.
  - It is also possible to download a template file.
- Detailed Expenses: 
  - Expenses can be given a date
  - All the expenses can be seen in an overview (event overview)
  - In the overview, it is possible to filter by participant or to show all expenses. 
  - There is also a button to delete all expenses.
- Foreign Currency: 
  - The date and currency for an expense can be selected.
- Open Debts: 
  - Bank account numbers (IBAN + BIC) can be added to participants.
- Statistics:
  - This feature has been fully implemented. 
  - Tags can be made, changed/editted and deleted.
  - A pie chart is shown on the statistics page.
  - This pie chart can be filtered by tag or by participant
- Email Notification: 
  - Email addresses can be added and changed for each participant.
  - Email addresses are unique and it is not possible to add two participants with the same email.

## **HCL features**

- Accessibility:
    - Color Contrast:
      - The app Colour Contrast Analyser has been used to look at the values of the colours. The button
      colours
      pass all contrast tests.
    - Keyboard Shortcuts:
      - By pressing on the question mark in the menu a list will be shown of the available keyboard shortcuts.
    - Multi-modal visualization:
      - When pressing on the buttons messages will be shown with the result of the action (if the action failed or succeeded). These messages have an appropriate colour, text and picture. All the delete buttons are red.
- Navigation:
    - Logical Navigation: 
      - On evey page a menu is present which is the same for all pages.
    - Keyboard Navigation: 
      - By using _TAB_ buttons can be reached and there are also keyboard shortcuts.
    - Supporting Undo Changes: 
      - Change operations in expenses have undo operations. 
      - On the edit expense page, if the user presse the button with the trashcan, a table is visible with editted expenses. When pressing on a expense/row, the user can chose to reset the expense.
- User Feedback:
    - Error Messages: 
      - Invalid entries in textfields will not get accepted and an error will be shown. 
      - Unavailability of the server is also shown. A user can choose to use the default server (localhost:8080).
    - Informative Feedback: 
      - Messages are shown when the user performs actions.
    - Confirmation for Key Actions: 
      - There are confirmation dialogs for each irreversible delete operations.


## **Long-polling and websocket**

- Long-polling implementation:
  - Run 2 clients (see _Run the Project_).
  - Login with 1 client to the admin page and go to the Admin Dashoard.
  - When the user creates an event, it is immediately visible in the admin dashboard without refreshing the page.
  - Classes that implemented it: AdminDashboardCtrl, EventServerUtils.
- Websocket implementation:
  - Run 2 clients (see _Run the Project_).
  - Join the same event with both clients. 
  - When one client changes (adding/editing) the participant or expenses, it is immediately visible in the other clients EventOverview.
  - Classes that implemented it: WebsocketConfig, EventServerUtils, StartScreenCtrl
- Regular Polling implementation:
  - For the recent events in the StartScreenCtrl = when clientA is in the StartScreen and clientB joins an event, the recent event on StartScreen of clientA gets updated.  
  - For the event name = when clientA is in the EventOverview and clientB changes the event name, it is visible for clientA without refreshing the page.

## **To Contribute**
Contribute by downloading the language template file (_client/src/main/resources/languageJSONS/languageTemplate.json_), translating each value after the "_keyX_" to your new language, and submitting the file. 
Ensure translations display accurately and submit a pull request. Let's make our app accessible globally!

