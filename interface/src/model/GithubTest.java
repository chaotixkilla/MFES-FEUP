package model;
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class GithubTest {
  private Github g = new Github();
  private Utilities u = new Utilities();
  private User u1 = new User("user1", "mail1", "password1");
  private User u2 = new User("user2", "mail2", "password2");
  private User u3 = new User("user3", "mail3", "password3");
  private Repository rep;
  private Repository rep2;
  private Branch b;
  private Commit c;

  private void assertTrue(final Boolean cond) {

    return;
  }

  private void createUserTest() {

    assertTrue(Utils.empty(g.getUsers()));
    g.addUser(u1);
    g.addUser(u2);
    assertTrue(SetUtil.inSet("user1", g.getAllUsernames()));
    assertTrue(SetUtil.inSet("user2", g.getAllUsernames()));
    assertTrue(Utils.equals(u1, g.getUser("user1")));
    assertTrue(Utils.equals(u2, g.getUser("user2")));
    assertTrue(Utils.equals(SetUtil.set(u1, u2), g.getUsers()));
  }

  private void loginTest() {

    assertTrue(Utils.equals("undef", g.getLoggedInUsername()));
    assertTrue(!(g.login("user1", "wrong password")));
    assertTrue(g.login("user1", "password1"));
    assertTrue(Utils.equals("user1", g.getLoggedInUsername()));
  }

  private void createRepositoryTest() {

    assertTrue(Utils.empty(g.getRepositories()));
    g.createRepository(u1, "repName");
    rep = g.getSpecificOwnedRepository(u1, "repName");
    assertTrue(Utils.equals(SetUtil.set(rep), g.getUserAvailableRepositories(u1)));
    assertTrue(Utils.equals(rep, g.getSpecificAvailableRepository(u1, "repName")));
    assertTrue(Utils.equals(SetUtil.set("repName"), g.getAllAvailableRepositoriesName(u1)));
    assertTrue(Utils.equals(u1, rep.getOwner()));
    assertTrue(Utils.equals(SetUtil.set("master"), rep.getBranchesNames()));
    assertTrue(rep.isPublicRepository());
  }

  private void addCollaboratorTest() {

    assertTrue(Utils.empty(rep.getCollaborators()));
    g.addCollaborator(u1, "repName", u2);
    assertTrue(Utils.equals(SetUtil.set(u2), rep.getCollaborators()));
    assertTrue(Utils.equals(SetUtil.set("repName"), g.getAllAvailableRepositoriesName(u2)));
    assertTrue(Utils.equals(SetUtil.set(rep), g.getUserAvailableRepositories(u2)));
  }

  private void repositoryVisibilityTests() {

    assertTrue(rep.isPublicRepository());
    rep.toggleVisibility(u1);
    assertTrue(!(rep.isPublicRepository()));
    rep.toggleVisibility(u1);
    assertTrue(rep.isPublicRepository());
    rep.makeRepositoryPrivate(u1);
    assertTrue(Utils.equals(model.quotes.PrivateQuote.getInstance(), rep.getVisibility()));
    rep.makeRepositoryPublic(u1);
    assertTrue(Utils.equals(model.quotes.PublicQuote.getInstance(), rep.getVisibility()));
  }

  private void getClonableRepositoriesTest() {

    g.addUser(u3);
    g.makeRepositoryPrivate(u1, "repName");
    assertTrue(Utils.equals(model.quotes.PrivateQuote.getInstance(), rep.getVisibility()));
    assertTrue(Utils.equals(SetUtil.set(rep), g.getClonableRepositories(u1)));
    assertTrue(Utils.equals(SetUtil.set(rep), g.getClonableRepositories(u2)));
    assertTrue(Utils.empty(g.getClonableRepositories(u3)));
    g.makeRepositoryPublic(u1, "repName");
    assertTrue(Utils.equals(model.quotes.PublicQuote.getInstance(), rep.getVisibility()));
    assertTrue(Utils.equals(SetUtil.set(rep), g.getClonableRepositories(u1)));
    assertTrue(Utils.equals(SetUtil.set(rep), g.getClonableRepositories(u2)));
    assertTrue(Utils.equals(SetUtil.set(rep), g.getClonableRepositories(u3)));
  }

  private void createBranchTest() {

    g.createBranch(u2, "repName", "branchName");
    assertTrue(Utils.equals(SetUtil.set("master", "branchName"), rep.getBranchesNames()));
    assertTrue(
        Utils.equals(
            SetUtil.set(rep.getSpecificBranch("master"), rep.getSpecificBranch("branchName")),
            rep.getBranches()));
    b = rep.getSpecificBranch("branchName");
    assertTrue(Utils.equals(rep, b.getRepository()));
    assertTrue(Utils.empty(b.getCommits()));
  }

  private void deleteBranchTest() {

    g.createBranch(u2, "repName", "deleteBranchTest");
    assertTrue(SetUtil.inSet("deleteBranchTest", rep.getBranchesNames()));
    g.deleteBranch(u2, "repName", "deleteBranchTest");
    assertTrue(!(SetUtil.inSet("deleteBranchTest", rep.getBranchesNames())));
  }

  private void createCommitTest() {

    g.createCommit(u2, "repName", "branchName", "1commitID", "commitMessage1");
    assertTrue(Utils.equals(1L, b.getCommits().size()));
    c = ((Commit) Utils.get(b.getCommits(), 1L));
    assertTrue(Utils.equals(u2, c.getCommitter()));
    assertTrue(Utils.equals(b, c.getBranch()));
    assertTrue(Utils.equals("commitMessage1", c.getMessage()));
    g.createCommit(u1, "repName", "branchName", "2commitID", "commitMessage2");
    assertTrue(Utils.equals(2L, b.getCommits().size()));
  }

  private void mergeBranchesTest() {

    g.createBranch(u1, "repName", "newBranchName");
    g.createCommit(u1, "repName", "newBranchName", "3commitID", "commitMessage3");
    g.mergeBranches(u1, "repName", "branchName", "newBranchName");
    assertTrue(!(SetUtil.inSet("newBranchName", rep.getBranchesNames())));
    assertTrue(Utils.equals(3L, b.getCommits().size()));
    g.createBranch(u2, "repName", "newBranchName2");
    g.createCommit(u2, "repName", "newBranchName2", "4commitID", "commitMessage4");
    g.mergeBranches(u2, "repName", "branchName", "newBranchName2");
    assertTrue(!(SetUtil.inSet("newBranchName2", rep.getBranchesNames())));
    assertTrue(Utils.equals(4L, b.getCommits().size()));
  }

  private void removeCollaboratorTest() {

    assertTrue(Utils.equals(SetUtil.set(u2), rep.getCollaborators()));
    g.removeCollaborator(u1, "repName", u2);
    assertTrue(Utils.empty(rep.getCollaborators()));
  }

  private void logoutTest() {

    assertTrue(Utils.equals("user1", g.getLoggedInUsername()));
    g.logout();
    assertTrue(Utils.equals("undef", g.getLoggedInUsername()));
  }

  private void deleteUserTest() {

    assertTrue(Utils.equals(SetUtil.set(u1, u2, u3), g.getUsers()));
    assertTrue(g.login("user2", "password2"));
    g.deleteUser("user2");
    assertTrue(Utils.equals("undef", g.getLoggedInUsername()));
    assertTrue(Utils.equals(SetUtil.set(u1, u3), g.getUsers()));
    g.createRepository(u3, "repNameD");
    rep2 = g.getSpecificOwnedRepository(u3, "repNameD");
    assertTrue(Utils.equals(SetUtil.set(rep, rep2), g.getClonableRepositories(u1)));
    g.deleteUser("user3");
    assertTrue(Utils.equals(SetUtil.set(u1), g.getUsers()));
    assertTrue(Utils.equals(SetUtil.set(rep), g.getClonableRepositories(u1)));
  }

  private void deleteRepositoryTest() {

    assertTrue(Utils.equals(SetUtil.set(rep), g.getRepositories()));
    assertTrue(Utils.equals(SetUtil.set(rep), g.getUserAvailableRepositories(u1)));
    assertTrue(g.login("user1", "password1"));
    g.deleteRepository(u1, "repName");
    assertTrue(Utils.empty(g.getRepositories()));
    assertTrue(Utils.empty(g.getUserAvailableRepositories(u1)));
  }

  private void doTests() {

    createUserTest();
    loginTest();
    createRepositoryTest();
    addCollaboratorTest();
    repositoryVisibilityTests();
    getClonableRepositoriesTest();
    createBranchTest();
    deleteBranchTest();
    createCommitTest();
    mergeBranchesTest();
    removeCollaboratorTest();
    logoutTest();
    deleteUserTest();
    deleteRepositoryTest();
  }

  public static void main() {

    new GithubTest().doTests();
  }

  public GithubTest() {}

  public String toString() {

    return "GithubTest{"
        + "g := "
        + Utils.toString(g)
        + ", u := "
        + Utils.toString(u)
        + ", u1 := "
        + Utils.toString(u1)
        + ", u2 := "
        + Utils.toString(u2)
        + ", u3 := "
        + Utils.toString(u3)
        + ", rep := "
        + Utils.toString(rep)
        + ", rep2 := "
        + Utils.toString(rep2)
        + ", b := "
        + Utils.toString(b)
        + ", c := "
        + Utils.toString(c)
        + "}";
  }
}
