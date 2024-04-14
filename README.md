# OOPP Template Project

**Run the Project**

Navigate to the project and open your terminal.
Your terminal should look something like this: C:\...\oopp-team-23>
Now type the following comments:
./gradlew build, ./gradlew bootRun. This will build the project and start a server.
In the server output the password for the admin pages will be shown.
With ./gradlew run the client application can be started.
By inputting this multiple times multiple client instances
can be opened to test the websocket and long polling.

**Implemented Features**

- Live Language Switch: this feature has been fully implemented. On the start screen and the event
  overview page the user can switch the language. This is persisted through the whole application.
- Detailed Expenses: expenses can be given a date, all the expenses can be seen in an overview
  and can be filtered.
- Foreign Currency: the date and currency for an expense can be selected
- Open Debts: bank account numbers can be added to participants.
- Statistics: this feature has been fully implemented. Tags can be made, changed and deleted.
  A pie chart is shown on the statistics page.
- Email Notification: Email addresses can be added and changed for each participant

**HCL features**

- Accessibility:
    - Color Contrast:
      Buttons: ![img_1.png](img_1.png)
      Other text: ![img_2.png](img_2.png)
    - Keyboard Shortcuts:
      By pressing on the question mark in the menu a list will be shown of the available keyboard shortcuts.
    - Multi-modal visualization:
      When pressing on the buttons messages will be shown with if the action failed or not. These messages
      have an appropriate colour, text and picture. All the delete buttons are red.
- Navigation:
    - Logical Navigation: on evey page a menu is present which is the same for all pages.
    - Keyboard Navigation: by using tab buttons can be reached and there are also keyboard shortcuts.
    - Supporting Undo Changes: change operations in expenses have undo operations. On the edit
      expense page see the button with the trashcan on it.
- User Feedback:
    - Error Messages: invalid entries will not get accepted and an error will be shown. Unavailability
      of the server is also shown. A user can choose to use the default localhost:8080.
    - Informative Feedback: messages are shown when the user performs actions.
    - Confirmation for Key Actions: there are confirmation dialogs for irreversible delete operations.


**Long-polling and websocket**

- Long-polling implementation can be seen here xxx
- Websocket implementation can be seen here xxx



