package model;
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Github {
  private VDMSet users = SetUtil.set();
  private VDMSet repositories = SetUtil.set();
  private String loggedInUsername = "undef";

  public void cg_init_Github_1() {

    return;
  }

  public Github() {

    cg_init_Github_1();
  }

  public VDMSet getUsers() {

    return Utils.copy(users);
  }

  public VDMSet getRepositories() {

    return Utils.copy(repositories);
  }

  public String getLoggedInUsername() {

    return loggedInUsername;
  }

  public User getUser(final String username) {

    for (Iterator iterator_1 = users.iterator(); iterator_1.hasNext(); ) {
      User u = (User) iterator_1.next();
      if (Utils.equals(username, u.getUsername())) {
        return u;
      }
    }
    return new User();
  }

  public VDMSet getAllUsernames() {

    VDMSet usernames = SetUtil.set();
    for (Iterator iterator_2 = users.iterator(); iterator_2.hasNext(); ) {
      User u = (User) iterator_2.next();
      usernames = SetUtil.union(Utils.copy(usernames), SetUtil.set(u.getUsername()));
    }
    return Utils.copy(usernames);
  }

  public VDMSet getAllEmails() {

    VDMSet emails = SetUtil.set();
    for (Iterator iterator_3 = users.iterator(); iterator_3.hasNext(); ) {
      User u = (User) iterator_3.next();
      emails = SetUtil.union(Utils.copy(emails), SetUtil.set(u.getEmail()));
    }
    return Utils.copy(emails);
  }

  public VDMSet getAllOwnedRepositoriesName(final User user) {

    VDMSet userRepos = SetUtil.set();
    for (Iterator iterator_4 = repositories.iterator(); iterator_4.hasNext(); ) {
      Repository r = (Repository) iterator_4.next();
      if (Utils.equals(r.getOwner(), user)) {
        userRepos = SetUtil.union(Utils.copy(userRepos), SetUtil.set(r.getName()));
      }
    }
    return Utils.copy(userRepos);
  }

  public Repository getSpecificOwnedRepository(final User user, final String repName) {

    Repository rep = null;
    for (Iterator iterator_5 = repositories.iterator(); iterator_5.hasNext(); ) {
      Repository r = (Repository) iterator_5.next();
      Boolean andResult_4 = false;

      if (Utils.equals(r.getOwner(), user)) {
        if (Utils.equals(r.getName(), repName)) {
          andResult_4 = true;
        }
      }

      if (andResult_4) {
        rep = r;
      }
    }
    return rep;
  }

  public VDMSet getAllAvailableRepositoriesName(final User user) {

    VDMSet userRepos = SetUtil.set();
    for (Iterator iterator_6 = repositories.iterator(); iterator_6.hasNext(); ) {
      Repository r = (Repository) iterator_6.next();
      Boolean orResult_2 = false;

      if (Utils.equals(r.getOwner(), user)) {
        orResult_2 = true;
      } else {
        orResult_2 = SetUtil.inSet(user, r.getCollaborators());
      }

      if (orResult_2) {
        userRepos = SetUtil.union(Utils.copy(userRepos), SetUtil.set(r.getName()));
      }
    }
    return Utils.copy(userRepos);
  }

  public VDMSet getUserAvailableRepositories(final User user) {

    VDMSet repos = SetUtil.set();
    for (Iterator iterator_7 = repositories.iterator(); iterator_7.hasNext(); ) {
      Repository r = (Repository) iterator_7.next();
      Boolean orResult_3 = false;

      if (Utils.equals(r.getOwner(), user)) {
        orResult_3 = true;
      } else {
        orResult_3 = SetUtil.inSet(user, r.getCollaborators());
      }

      if (orResult_3) {
        repos = SetUtil.union(Utils.copy(repos), SetUtil.set(r));
      }
    }
    return Utils.copy(repos);
  }

  public VDMSet getClonableRepositories(final User user) {

    VDMSet repos = getUserAvailableRepositories(user);
    for (Iterator iterator_8 = repositories.iterator(); iterator_8.hasNext(); ) {
      Repository r = (Repository) iterator_8.next();
      if (r.isPublicRepository()) {
        repos = SetUtil.union(Utils.copy(repos), SetUtil.set(r));
      }
    }
    return Utils.copy(repos);
  }

  public Repository getSpecificAvailableRepository(final User user, final String repName) {

    VDMSet availableRepos = getUserAvailableRepositories(user);
    for (Iterator iterator_9 = availableRepos.iterator(); iterator_9.hasNext(); ) {
      Repository r = (Repository) iterator_9.next();
      if (Utils.equals(r.getName(), repName)) {
        return r;
      }
    }
    return new Repository();
  }

  public void addUser(final User user) {

    users = SetUtil.union(Utils.copy(users), SetUtil.set(user));
  }

  public Boolean login(final String username, final String password) {

    if (Utils.equals(getUser(username).getPassword(), password)) {
      loggedInUsername = username;
      return true;
    }

    return false;
  }

  public void logout() {

    loggedInUsername = "undef";
  }

  public void deleteUser(final String username) {

    if (Utils.equals(username, loggedInUsername)) {
      logout();
    }

    for (Iterator iterator_10 = repositories.iterator(); iterator_10.hasNext(); ) {
      Repository r = (Repository) iterator_10.next();
      if (Utils.equals(r.getOwner(), getUser(username))) {
        repositories = SetUtil.diff(Utils.copy(repositories), SetUtil.set(r));
      }
    }
    users = SetUtil.diff(Utils.copy(users), SetUtil.set(getUser(username)));
  }

  public void createRepository(final User user, final String repName) {

    Repository newRepo = new Repository(user, repName);
    repositories = SetUtil.union(Utils.copy(repositories), SetUtil.set(newRepo));
  }

  public void deleteRepository(final User user, final String repName) {

    Repository repToDelete = getSpecificOwnedRepository(user, repName);
    repositories = SetUtil.diff(Utils.copy(repositories), SetUtil.set(repToDelete));
  }

  public void addCollaborator(final User owner, final String repName, final User newCollab) {

    Repository repo = getSpecificOwnedRepository(owner, repName);
    repo.addCollaborator(owner, newCollab);
  }

  public void removeCollaborator(final User owner, final String repName, final User oldCollab) {

    Repository repo = getSpecificOwnedRepository(owner, repName);
    repo.removeCollaborator(owner, oldCollab);
  }

  public void makeRepositoryPublic(final User user, final String repName) {

    Repository repo = getSpecificOwnedRepository(user, repName);
    repo.makeRepositoryPublic(user);
  }

  public void makeRepositoryPrivate(final User user, final String repName) {

    Repository repo = getSpecificOwnedRepository(user, repName);
    repo.makeRepositoryPrivate(user);
  }

  public void createBranch(final User creator, final String repName, final String branchName) {

    Repository rep = getSpecificAvailableRepository(creator, repName);
    Branch newBranch = new Branch(rep, branchName);
    rep.addBranch(creator, newBranch);
  }

  public void createCommit(
      final User committer,
      final String repName,
      final String branchName,
      final String commitID,
      final String commitMessage) {

    Repository rep = getSpecificAvailableRepository(committer, repName);
    Branch branch = rep.getSpecificBranch(branchName);
    Commit newCommit = new Commit(committer, branch, commitID, commitMessage);
    branch.addCommit(committer, newCommit);
  }

  public void deleteBranch(final User user, final String repName, final String branchName) {

    Repository rep = getSpecificAvailableRepository(user, repName);
    Branch branch = rep.getSpecificBranch(branchName);
    rep.deleteBranch(user, branch);
  }

  public void mergeBranches(
      final User user,
      final String repName,
      final String branchToMergeTo,
      final String branchMerged) {

    Repository rep = getSpecificAvailableRepository(user, repName);
    Branch branch1 = rep.getSpecificBranch(branchToMergeTo);
    Branch branch2 = rep.getSpecificBranch(branchMerged);
    rep.mergeBranches(user, branch1, branch2);
  }

  public String toString() {

    return "Github{"
        + "users := "
        + Utils.toString(users)
        + ", repositories := "
        + Utils.toString(repositories)
        + ", loggedInUsername := "
        + Utils.toString(loggedInUsername)
        + "}";
  }
}
