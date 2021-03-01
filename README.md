# Awin CoffeeBreak

## Outline
The following classes in the application are used to manage a Development Team’s 
coffee break preferences - these things are very important!

The application allows the team to specify what they want to eat or drink during their 
morning coffee break.  The application can show everyone’s preferences in JSON, XML and 
HTML and is also able to send a notification to each member of the team on Slack so that 
they know what they will be having that day.

The rest of the office has got jealous and now wants to use the application in their 
teams.  Only the development team has Slack, so it is time to refactor the application so that it 
can be handed over to other teams that will receive their notifications by email.

## Task
1. Since the application was written in a hurry, and just for the development team, it is not 
very good or scalable code.  Your task is to refactor the existing code, strengthening it to 
make it more logical and reusable.
  
2. You should also implement the notification service that will send emails instead of Slack 
notifications.  You DO NOT need to write code that will actually send an email - just show 
how the class will fit into the application and provide similar functionality.

3. Explain your decisions.  This could be through your git activity for the work you change, and 
a separate document for other thoughts.  We're as interested in what you'd like to do as what you've done. 

## Approach
1. Included in the attached zip archive are classes and a `pom.xml` file to enable you 
to write and test your code.  We have implemented Spring Data for the entities and repositories, 
but you are welcome to create working code in any way that you see fit.
                                                                                                                          
2. You will no doubt be adding more classes during the refactoring.  Any code you provide in 
your answer will be considered as you intend it to go live, unless you specifically state 
otherwise.  It's ok to stub classes out, just as we've asked you to approach the new email notifications 
functionality, but you must reference this in your handover document.
 
3. Please provide clear instructions for setting up your solution if you intend it to be runnable.

4. This exercise is the proverbial 'piece of string', in that it's very difficult to set boundaries 
to it.  We are not expecting a finished product, and you will almost certainly include some loose 
ends.  This is fine, just ensure you refer to these in the handover document with an explanation of how 
you would see this completed.

5. A git repository is included so that you can show the changes and additions you make.  We will be 
analysing this to see your approach and to get an idea as to how long you spent on the task. 

6. The application should support Java 11.

7. There is no time limit set for this task, rather a time boundary.  Use the time you have available 
as best you can, and use your handover document to explain your prioritisation decisions and how you 
would have continued if you'd had the chance.

## Manifest
- `Controller\CoffeeBreakPreferenceController`
- `Entity\CoffeeBreakPreference`
- `Entity\StaffMember`
- `Repository\CoffeeBreakPreferenceRepository`
- `Repository\StaffMemberRepository`
- `Services\SlackNotifier`
- `Tests\AwinCoffeeBreakApplicationSpec`
- `Tests\Services\SlackNotifierSpec`
