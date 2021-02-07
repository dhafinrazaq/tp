---
layout: page
title: Maguire Ong's Project Portfolio Page
---

## Project: FitEgo

FitEgo is a desktop application for fitness instructors to schedule, and keep track of his/her customers' progress and payments in one place. 
It is faster compared to manually tracking administrative matters using alternative software like Excel and Google Calendar. The user interacts with it using a CLI, and it has a GUI created with JavaFX. 

It is written in Java, and has about 23 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to edit a session created by the user. (Pull request [\#83](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/83))
  * What it does: Allows the user to edit a session’s details like a session’s gym name, exercise type, start time and duration.
  * Justification: This feature improves the product significantly because a client may have changes made to his/her workout session, therefore the app should provide a way to make changes to the session.
  * Highlights: This enhancement affects existing commands and commands to be added in future. It required an in-depth analysis of design alternatives.
                It builds the foundation for edit features in FitEgo. The implementation was challenging as it required changes to existing commands.

* **New Feature**: Added the ability to edit a schedule created by the user. (Pull request [\#91](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/91))
  * What it does: Allows the user to edit a schedule’s details like a schedule’s updated session index, payment status, remark and weight.
  * Justification: This feature improves the product significantly because a user may have changes made to his schedules, therefore the app should provide a way to make update the changes to the schedule.
  * Highlights: This enhancement was similar to the edit session feature. However, the implementation was slightly different form the edit session feature and more variations of parameters were added to this feature. 
                Much consideration was put into this implementation's design for ease of usage of this feature from the user's end, therefore more time was required to create this feature.
  * Credits: Credits to teammates Bennett and Dhafin for adding additional parameters that can be edited in the feature.

<div style="page-break-after: always;"></div>

* **New Feature**: Added a dynamic header on the right side panel. (Pull requests [\#134](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/134), [\#138](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/138))
  * What it does: Allows the user to have a better idea of the time period searched for when using the “Session View” function.
  * Justification: This feature improves the product significantly because a user can better identify which time period it is referring to.
  * Highlights: This enhancement affects existing commands and commands to be added in future. 
                It required an good understanding of JavaFX and how it relates to the logic in both Session List panel and the user's input.
                The implementation too was challenging enough as a new user to JavaFX.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=maguireong&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=maguireong&tabRepo=AY2021S1-CS2103T-T13-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&until=2020-11-09)

* **Project management**:
  *	Necessary general code enhancements. e.g. Code Refactoring
  *	Maintain issue tracker.

* **Enhancements to existing features**:
  * Refactored code from Person to Client. (Pull request [\#45](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/45))

* **Documentation**:
  *	User Guide:
        * Added documentation for the features Add Client [\#45](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/45), Edit Session and Edit Schedule features. [\#153](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/153)
        * Reformat User Guide to ensure consistency. [\#231](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/231), [\#267](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/267)
    
  * Developer Guide:
    * Added Use Case, UML diagrams, description and implementation details of the Edit Session and Edit Schedule features. [\#153](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/153)
    * Reformat User Guide to ensure consistency. [\#231](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/231), [\#267](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/267)
    
* **Review or mentoring contributions**:
  * Discuss design and implementation details with group mates.
  * PRs reviewed (with non-trivial review comments): [\#96](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/96), [\#139](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/139), 
                                                     [\#137](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/137), [\#97](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/97)
  
* **Community**:
  * Reported bugs and suggestions for Group [CS2103T-W12-2](https://ay2021s1-cs2103t-w12-2.github.io/tp/UserGuide.html) on CATcher.
