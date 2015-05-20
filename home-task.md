## Preparing

1. You should have GitHub account to do home task!

2. ssh ceritficate should be uploaded to allow you to push on GitHub. Check it here: https://github.com/settings/ssh
3. Clone latest version of test project we were workin in class
  ```
  git clone git@github.com:budabum/cucumber-java-skeleton.git bdd-hometask
  cd bdd-hometask
  git checkout inclass
  ```
  
  **Note:** you have to clone by git url, not https. Otherwise you won't be able to push.
  
  Now you have the latest version we done in class.
  
4. Create your own branch
  ```
  git checkout -b hometask/%YOUR-NAME%
  ```
  Replace %YOUR-NAME% with your real name (e.g. *hometask/alexey.lyanguzov*)
  
  Do your home task in this branch. Commit often. Push when ready or you want to save the sate on the server.
  
  **Send me notification (create pull request + skype) when you home task is ready.**
  
  Note: If you get 403 error on attempt to push - let me know - I'll add you into colobarator's list.

## API Testing

**TASK 1.1:** Create several tests (3 is enough but you can do more if you want) for java.util.ArrayList. Chose any method or methods you wish: http://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html

## REST Testing

**TASK 2.1:** Create several tests (2 is enough) for *delete /users?email=a@b.c* which will delete user for email you have provided.

**TASK 2.2:** Create test for edit user *post /users/edit?email=a@b.c* (request body format is similar to what *post /users/add* has) using scenario outlines (https://github.com/cucumber/cucumber/wiki/Scenario-Outlines).

## Web Testing

**TASK 3.1:** Create couple of tests for */web/listusers*. Note that */web/adduser* is not working, so you need to add expected users through the service call *post /users/add* and then check them on UI.

**TASK 3.2:** [Optional/Advanced] Make Web tests working with PageObject pattern.

**TASK 3.3:** [Optional/Advanced] Make browser to start only for scenarios where Web is tested.


