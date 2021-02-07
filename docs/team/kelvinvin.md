---
layout: page
title: Kelvin Wong's Project Portfolio Page
---

## Project: FitEgo

FitEgo is a desktop application for fitness instructors to schedule, and keep track of his/her customers' progress and payments in one place. 
It is faster compared to manually tracking administrative matters using alternative software like Excel and Google Calendar. The user interacts with it using a CLI, and it has a GUI created with JavaFX. 

It is written in Java, and has about 23 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to change view of Session List.
  * What it does: allows the user to filter the Session List to only those that start within the requested period.
  * Justification: This feature improves the product significantly because a user can now re-prioritise and update their timetable more conveniently.
  * Highlights: This enhancement affects existing session-related and schedule-related commands. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to the Logic component.
  * Credits: *Credits to teammates Tan Wei Jie for adding variable range ability and Maguire Ong for assisting with Ui-related matters of the Session List*
  
* **New Feature**: Add Session List to RightSideBar.
    * What it does: allows the user to view sessions, sorted by time, in one glance.
    * Credits: *{Reused code from the Client List to implement Session List}*

* **New Feature**: Added a weight unit utility class to facilitate conversion of units.
  * What it does: allows the user to both input and output weight in terms of kilogram or pounds.
  * Justification: This feature improves the product's usability significantly as it widens the user base to include anyone who uses Metric or Imperial systems.
  * Highlights: This enhancement affects the existing weight-related commands and graph. 
    The implementation was challenging because it was built to reduce coupling with other classes and required usage of Observer pattern to enable dynamic updates.
  
* **New Feature**: Added a Settings window.
  * What it does: allows the user to quickly open a window to edit settings using F4 key / CLI. It also saves the changes in UserPrefs for the next time the user starts up.
  * Justification: Currently used for weight unit but serves as a frame for any other editable settings to be added in future.
  * Highlights: It necessitated the implementation of an additional user preference field. 
  * Credits: *Reused code from the Help window to implement the Settings window*

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=kelvinvin&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&until=2020-11-09&tabOpen=true&tabType=authorship&tabAuthor=kelvinvin&tabRepo=AY2021S1-CS2103T-T13-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code)

* **Project management or Team-based tasks**:
  * Managed timely updates of demo animations for each iteration [v1.2](https://imgur.com/a/loBT8Cb), [v1.3](https://hackmd.io/Eo7Gsii8RTWRlDLykD35LQ)
  * Maintained [issue tracker](https://github.com/AY2021S1-CS2103T-T13-3/tp/issues)
  
* **Enhancements to existing features**:
  * Added wrap text to increase text readability for small resolutions. [\#218](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/218)
  * Refactored Delete and List commands to equivalent Client commands. [\#56](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/56), [\#55](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/55)
  * Added "Next Session" field in Client List to allow user to easily identify the earliest upcoming session with each client.
  
* **Documentation**:
  * User Guide:
    * Added documentation for the features `sview`, `cdel` and `settings` [\#229](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/229), [\#171](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/171), [\#159](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/159) 
    * Made cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#157](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/157)
    * Proof read the document for the team to ensure accurate grammar and consistency of commonly used terms [\#157](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/157), [\#23](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/23)
    * Created an annotated diagram of Ui components. [\#159](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/159)

  * Developer Guide:
    * Updated diagrams and description of the Logic component and `cdel` feature. [\#142](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/142)
    * Added implementation details, UML diagrams and design considerations for the `sview` feature. [\#266](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/266), [\#256](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/256)
    * Added captions for all figures.
    * Added View Session's use case and instructions for manual testing [\#142](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/142)
    * Added [\#249](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/249)
    
* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#233](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/233), [\#159](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/159), [\#149](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/149), [\#153](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/153)
  * Reported bugs and suggestions for Group [CS2103T-W10-1](https://ay2021s1-cs2103t-w10-1.github.io/tp/UserGuide.html) on CATcher.
  * Integrated the tab pane in Client Info Page with Wei Jie and Bennett.
