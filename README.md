#Cucumber - BDD framework

##Introduction:
This framework helps us to execute the tests with BDD in parallel/sequential executions.It also generates serenity report.

##Pre-Requisites:
- Maven 
- Intellij version:2019.3
- Selenium version:3.5.3
- Cucumber version:1.2.5
- Serenity version:1.5.11
- Browser driver (make sure you have your desired browser driver and class path is set)

##Framework setup:
- Clone repository from here or download zip and set it up in your local workspace
- Setup java,maven.

##Framework components:
These framework contains following packages:
* page actions: Folder for placing all the reusable actions
* page object: Folder for placing all the page objects.
* step Definitions: Folder for placing all the step definition classes.
* utilities: Folder that contains pre-defined utils classes that can be used.
* cukesRunner: Class to perform sequentially execution where we can specify tags.
* browser_drivers: Folder for placing all drivers.
* features : Folder for placing all the feature files.
* config.properties: The file that used to specify the runner type,server url and can mention tags to execute in parallel.

##Running test:
We can execute test cases in three different ways as follows:
#####Use maven
open maven command window and execute test cases to generate serenity report in both parallel/sequential executions
> clean verify -DRunner=<parallel_name>/<CukesRunner_name>

#####Run a subset of Features or Scenarios
We can execute specific scenario/feature file by right click and run.

#####Running only the scenarios that failed in the previous run
These works as long as we have rerun enabled.
> clean verify -DRunner=<RetryRunner_name>
 
##Framework benefits:
- These framework helps us to run the test cases in parallel/sequential.
- We can generate serenity report.
- We can also execute rerun.