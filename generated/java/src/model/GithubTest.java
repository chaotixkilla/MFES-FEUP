package model;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class GithubTest {
  private Github g = new Github();
  private Utilities u = new Utilities();
  private User u1 = new User("user1", "mail1", "password1");
  private User u2 = new User("user2", "mail2", "password2");
  private Repository rep;
  private Branch b;
  private Commit c;

  private void assertTrue(final Boolean cond) {

    return;
  }

  private void doTests() {

    assertTrue(Utils.empty(g.getUsers()));
    g.addUser(u1);
    assertTrue(SetUtil.inSet("user1", g.getAllUsernames()));
    g.addUser(u2);
    assertTrue(SetUtil.inSet("user2", g.getAllUsernames()));
    assertTrue(Utils.equals("password1", u1.getPassword()));
    assertTrue(Utils.equals("password2", u2.getPassword()));
    assertTrue(Utils.equals(u1, g.getUser("user1")));
    assertTrue(Utils.equals(SetUtil.set(u1, u2), g.getUsers()));
    assertTrue(Utils.equals("undef", g.getLoggedInUsername()));
    assertTrue(!(g.login("user1", "wrong password")));
    assertTrue(g.login("user1", "password1"));
    assertTrue(Utils.equals("user1", g.getLoggedInUsername()));
    assertTrue(Utils.empty(g.getRepositories()));
    g.createRepository(u1, "repName");
    rep = g.getSpecificOwnedRepository(u1, "repName");
    assertTrue(Utils.equals(SetUtil.set(rep), g.getUserAvailableRepositories(u1)));
    assertTrue(Utils.equals(rep, g.getSpecificAvailableRepository(u1, "repName")));
    assertTrue(Utils.equals(SetUtil.set("repName"), g.getAllAvailableRepositoriesName(u1)));
    assertTrue(Utils.equals(u1, rep.getOwner()));
    g.addCollaborator(u1, "repName", u2);
    assertTrue(Utils.equals(SetUtil.set(u2), rep.getCollaborators()));
    assertTrue(Utils.equals(SetUtil.set("repName"), g.getAllAvailableRepositoriesName(u2)));
    assertTrue(Utils.equals(SetUtil.set(rep), g.getUserAvailableRepositories(u2)));
    assertTrue(Utils.equals(SetUtil.set("master"), rep.getBranchesNames()));
    assertTrue(rep.isPublicRepository());
    rep.toggleVisibility(u1);
    assertTrue(!(rep.isPublicRepository()));
    rep.toggleVisibility(u1);
    assertTrue(rep.isPublicRepository());
    g.createBranch(u2, "repName", "branchName");
    assertTrue(Utils.equals(SetUtil.set("master", "branchName"), rep.getBranchesNames()));
    assertTrue(
        Utils.equals(
            SetUtil.set(rep.getSpecificBranch("master"), rep.getSpecificBranch("branchName")),
            rep.getBranches()));
    b = rep.getSpecificBranch("branchName");
    assertTrue(Utils.equals(rep, b.getRepository()));
    assertTrue(Utils.empty(b.getCommits()));
    g.createCommit(u2, "repName", "branchName", "1commitID", "commitMessage1");
    assertTrue(Utils.equals(1L, b.getCommits().size()));
    c = ((Commit) Utils.get(b.getCommits(), 1L));
    assertTrue(Utils.equals(u2, c.getCommitter()));
    assertTrue(Utils.equals(b, c.getBranch()));
    assertTrue(Utils.equals("commitMessage1", c.getMessage()));
    g.createCommit(u1, "repName", "branchName", "2commitID", "commitMessage2");
    assertTrue(Utils.equals(2L, b.getCommits().size()));
    g.removeCollaborator(u1, "repName", u2);
    g.logout();
    assertTrue(Utils.equals("undef", g.getLoggedInUsername()));
    assertTrue(g.login("user2", "password2"));
    g.deleteUser("user2");
    assertTrue(g.login("user1", "password1"));
    g.deleteRepository(u1, "repName");
    assertTrue(Utils.empty(g.getRepositories()));
    assertTrue(Utils.empty(g.getUserAvailableRepositories(u1)));
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
        + ", rep := "
        + Utils.toString(rep)
        + ", b := "
        + Utils.toString(b)
        + ", c := "
        + Utils.toString(c)
        + "}";
  }
}
