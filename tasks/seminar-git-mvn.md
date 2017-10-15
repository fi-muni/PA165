## Persistence Git & Maven
Do not forget to checkout branch seminar-git-mvn! Do not work in master! In this seminar you will work with standard git commands in multi-user, multi-branch environment and couple maven features presented on the lecture.

Make sure after checkout you first build the project by using mvn install

**Task 01**  
Your first task is to fork PA165 project and clone it to your environment. Then you should setup your local repository to use original PA165 project for pull and your forked project for push. Then, create SSH key pair and use it for pushing your changes to your remote repository.

**Task 02**
Create new feature branch. Push your branch to remote repository (add remote-tracking). Switch to newly created feature branch. Change junit version, make adjustments to project and commit your changes (local repository). TBD use feature from 4.10

**Task 03**
Switch to master branch and change junit version to 4.12 (this can typically happen when there is decision to update to new version project wide). Commit and push your changes.

**Task 04**
Switch to feature branch and rebase to master. Resolve your conflict (keep higher version). Merge changes from feature branch into master.

**Task 05**
Since your feature is complete and merged into master, add tag (v1.0_feature) to mark current version as version with completed feature.

**Task 06**
Make some more changes (something mvn, TBD) and commit them. Then, push all the changes and tags into remote repository (check on github that tag exists and try to use tags to navigate between different versions).

**Task 07** 
Remove no longer used branch from both local and remote repository.

**Extra Task01**
Modify a code in master branch and try to revert it in your working copy using git.

**Extra Task02**
Modify code in master branch, commit your change. Revert your change in repository.

**Extra Task03**
Make several adjustments to code, each with different commit. Cleanup your code into single commit before pushing it to remote repository.

**Extra Task 04**
Learn about stashing, figure out how you could use it in your daily work.
