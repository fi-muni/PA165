## Seminar Git & Maven

**Task 01 (Git)**
Your first task is to setup SSH access to Github, fork PA165 project and clone the forked repository to your environment.
1. Generate your personal SSH key (https://help.github.com/articles/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent/#platform-linux)
2. Add your new SSH key to Github account (https://help.github.com/articles/adding-a-new-ssh-key-to-your-github-account/)
3. Click "Fork" button in top right corner and fork the original repository under your account.
4. Copy link from "Clone or download" popup (choose use SSH) and clone the repository to your local environment.
5. After all you should be able to setup your local repository to use original PA165 project for pull 
and your forked project for push (https://help.github.com/articles/configuring-a-remote-for-a-fork/).

**Task 02 (Git/Maven)**
Switch to **seminar-git-mvn** branch. Note that your are working with 2 remotes and therefore it's important to explicitly mention origin.
Try to build your project using `mvn install`, it should fail because source files contain lambda calls. Fix the `maven.compiler` settings in parent project 
and rebuild it. Ignore failing tests for now.
Import project **git-mvn-seminar** into your IDE along with its modules.
Check .gitignore file to avoid pushing class files and jar files into repository - modify it if necessary.
Commit changes into **seminar-git-mvn** branch.

**Task 03 (Git)**
Create new feature branch called **git-mvn-feature** from **seminar-git-mvn** and checkout it. Push your branch to remote repository (add remote-tracking). 
Change junit version to 4.11, fix any test which is still failing. Fix can be done in service itself - just follow the contract.
Uncomment method `sellCar(Car car)` in `CarShopStorageService` and create its implementation based on its contract. 
Stage all new and modified files and commit all changes to **git-mvn-feature** (local repository).

**Task 04 (Git/Maven)**
Switch to **seminar-git-mvn** branch and change junit version to 4.12 and uncomment `CarShopStorageServiceTest::testPriceCantBeNegative` for 
JUnit 4.12 (comment out 4.11 part). Verify that test is compilable (just compilable, it is expected to fail, that's OK, you will correct it in the next task).
Commit and push your changes.

**Task 05 (Git)**
Switch to feature branch (**git-mvn-feature**) and rebase it on **seminar-git-mvn** branch.
Resolve your conflicts (keep higher version), stage resolved files and continue rebase operation.
Push your changes to remote repository.
Merge **git-mvn-feature** branch to **seminar-git-mvn**. It should be Fast-forward merge.

**Task 06 (Git)**
Since your feature is complete and merged into **seminar-git-mvn**, add tag (v1.0_feature) to mark current version as version with completed 
feature.

**Task 07 (Git/Maven)**
Update project version to 0.0.2-SNAPSHOT and commit the change.
Then, push all the changes and tags into remote repository (check on github that tag exists and try to use tags to navigate between different versions).

**Task 08 (Git)**
Remove no longer used branch from both local and remote repository.

**Task 09 (Maven)**
Configure the project to use jacoco maven plugin (https://www.petrikainulainen.net/programming/maven/creating-code-coverage-reports-for-unit-and-integration-tests-with-the-jacoco-maven-plugin/, you can skip integration tests). It is used for generating test coverage reports.
Remember that any configuration and versioning should be kept in parent pom.xml and the actual usage of plugin should be specified within the project. After you run `mvn (clean) test`, you should find report under target/site directory of car-shop project. Do you understand difference between pluginManagement and plugins? Do you get idea what executions are for? Ask you seminar tutor in case you don't.
Find what part of CarShopStorageService is not covered by tests and write corresponding test to ensure full coverage.

**Task 10 (Maven)**
Configure maven profile called test-coverage for jacoco execution so it isn't executed by default (run by `mvn install -P test-coverage`). Profiles are handy in case you have long taking operations as part of your build and you want to execute them only under certain circumstances (eg. nightly builds).

--------------
Extra tasks in case you still have time.

**Extra Task 01 (Git)**
Modify a code in **seminar-git-mvn** branch and try to revert it in your working copy using git.

**Extra Task 02 (Git)**
Modify code in **seminar-git-mvn** branch, commit your change. Revert your change in repository.

**Extra Task 03 (Git)**
Make several adjustments to code, each with different commit. Cleanup your code into single commit before pushing it to remote repository.

**Extra Task 04 (Git)**
Learn about stashing, figure out how you could use it in your daily work.

**Extra Task 05 (Maven)**
Learn more about lifecycles, try to implement your own lifecycle.

**Extra Task 06 (Maven) - Quiz**
What could happen if you don't follow standardized mvn directory structure?
Why is it important to put all built artifacts into target folder?
What are differences between different dependency scopes?
