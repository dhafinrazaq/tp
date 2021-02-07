---
layout: page
title: Dhafin Razaq Oktoyuzan's Project Portfolio Page
---

## Project: FitEgo

FitEgo is a desktop application for fitness instructors to schedule, and keep track of his/her customers' progress and payments in one place. 
It is faster compared to manually tracking administrative matters using alternative software like Excel and Google Calendar. The user interacts with it using a CLI, and it has a GUI created with JavaFX. 

It is written in Java, and has about 23 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to add and delete schedules. (Pull Request [#81](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/81), [#96](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/96))
  * What it does: Allows the user to add and delete schedules. A schedule contains information about a client and the attended session. Thus, adding a schedule means that the specified client will come to a session.
  * Justification: This feature is core to the product because the user needs to be able to associate a client to a session. This is necessary for the other enhancements in the project such as the ability of tracking the sessions attended by a client and tracking which clients will attend a session.
  * Highlights: Before implementing this feature, a `Schedule` model must be implemented first. When implementing the `Schedule` model (which is then implemented as an association class of client and session) and how it should be stored in the storage, in-depth design analysis were needed. Creating the test cases were also challenging since it needs to test the integration with `Client` and `Session` objects.

* **New Feature**: Added the payment tracking feature (PR [#137](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/137))
  * What it does: Allows the user to track the clients' payment status by looking at either the right side panel or the client's detail view.
  * What I did: Associate each schedule (which contains a client and session) to a payment status (either paid or unpaid) and modify the right side bar and client detail's table to show the payment status.
  * Justification: This feature allows the user to easily spot whether a client has paid for a session by just looking at the color of the texts.
  * Highlights: The implementation requires a good understanding in UI (including JavaFX and CSS coloring) and how it relates to the logic.
  * Credits: *Credit to Kelvin for creating the right side bar and Bennett for creating client's detail table*

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&until=2020-11-09&tabAuthor=dhafinrazaq&tabRepo=AY2021S1-CS2103T-T13-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other)

* **Project management or team-based tasks**:
  * Contributed to `v1.3` and `v1.4` demo and non-feature-specific documentations on UG and DG.
  * Maintained issue tracker such as labelling and closing finished issues.

* **Enhancements to existing features**:
  * Modified and enhanced `cedit` from `edit` command. It will automatically show the edited client's detail view after executing a `cedit` command. (PR [#53](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/53), [\#165](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/165))
  * Modified `cdel` to check for associated Schedules before deleting the Client. (PR [#152](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/152))
  * Modified `Session` model (and its relation to `Storage`) to use interval as unique identifier. (PR [#95](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/95))
  * Added test cases for `Schedule` model. (PR [#158](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/158))
  * Adapted saving to `Storage` to include `Schedule` objects. (PR [#74](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/74))
  * Refactored `#isUnique()` into `#isIdentical()` so that the naming aligns with the expected output. (PR [#166](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/166))

* **Documentation**:
  * User Guide:
    * Added documentation for the features `schadd` and `schdel`. (PR [\#143](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/143))
    * Added documentation for the feature `cedit`. (PR [\#242](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/242))
    * Added information on duplicate client. (PR [\#230](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/230))
    * Updated FAQ's contact us link and other potential query. (PR [#234](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/234))
    * Proofread and fixed UG (includes fixing broken links and formatting). (PR [#173](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/173), [#163](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/163/files))
  * Developer Guide:
    * Added MSS and manual testing instructions for `schadd` and `schdel` feature. (PR [\#149](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/149))
    * Added implementation details of the `schadd` feature. (PR [\#149](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/149))
    * Updated the diagram of the `Storage` component. (PR [\#149](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/149))

* **Review or mentoring contributions**:
  * Regularly discuss design and implementation details with group mates.
  * Discuss bugs and feature or enhancement proposals during team meeting, via group communication channels, or via issues. (example: [issue \#141](https://github.com/AY2021S1-CS2103T-T13-3/tp/issues/141), [issue \#174](https://github.com/AY2021S1-CS2103T-T13-3/tp/issues/174)).
  * Review and comment on PRs regarding code quality (PR [#91](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/91)), implementation bug (PR [#226](https://github.com/AY2021S1-CS2103T-T13-3/tp/pull/226)), and other improvements.

* **Community**:
  * Asked questions in Gitter, one of which was about the convention of code integration.
  * Beyond this team: reported bugs and suggestion for other team via CATcher (examples: PR [#188](https://github.com/AY2021S1-CS2103T-W17-3/tp/issues/188))
