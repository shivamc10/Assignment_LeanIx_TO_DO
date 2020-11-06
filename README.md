# To_Do_Bck

How to start the To_Do_Bck application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/Assignment_LeanIx_TO_DO-1.0.jar server test.yml`
1. To check that your application is running enter url `http://localhost:8080`

Note:
1. Notification for exprired task is printed along with logs. The time is in format yyyy-MM-dd(2020-11-10). The service is running in paraller thread and checking the subtasks and tasks every minute.
2. Subtask doesn't have any more subtasks they are the basic entity of the system.
3. Subtasks can be retrievved via parent task only, but notification for the subtasks can be shown if its parent is still not expired.
4. Deleting parent task delete all the subtasks also. 

