package model;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Repository {
  private User owner;
  private String name;
  private VDMSet collaborators = SetUtil.set();
  private VDMSet branches = SetUtil.set();
  private Object visibility = model.quotes.PublicQuote.getInstance();

  public void cg_init_Repository_1(final User user, final String repName) {

    owner = user;
    name = repName;
    branches = SetUtil.union(Utils.copy(branches), SetUtil.set(new Branch(this, "master")));
    return;
  }

  public Repository(final User user, final String repName) {

    cg_init_Repository_1(user, repName);
  }

  public User getOwner() {

    return owner;
  }

  public String getName() {

    return name;
  }

  public VDMSet getCollaborators() {

    return Utils.copy(collaborators);
  }

  public VDMSet getBranches() {

    return Utils.copy(branches);
  }

  public Object getVisibility() {

    return visibility;
  }

  public Branch getSpecificBranch(final String branchName) {

    for (Iterator iterator_11 = branches.iterator(); iterator_11.hasNext(); ) {
      Branch b = (Branch) iterator_11.next();
      if (Utils.equals(b.getName(), branchName)) {
        return b;
      }
    }
    return new Branch();
  }

  public VDMSet getAllCommitsID() {

    VDMSet IDs = SetUtil.set();
    for (Iterator iterator_12 = branches.iterator(); iterator_12.hasNext(); ) {
      Branch b = (Branch) iterator_12.next();
      for (Iterator iterator_13 = SeqUtil.elems(b.getCommits()).iterator();
          iterator_13.hasNext();
          ) {
        Commit c = (Commit) iterator_13.next();
        IDs = SetUtil.union(Utils.copy(IDs), SetUtil.set(c.getIdentifier()));
      }
    }
    return Utils.copy(IDs);
  }

  public Boolean isPublicRepository() {

    return Utils.equals(visibility, model.quotes.PublicQuote.getInstance());
  }

  public void toggleVisibility(final User user) {

    if (Utils.equals(visibility, model.quotes.PublicQuote.getInstance())) {
      visibility = model.quotes.PrivateQuote.getInstance();
    } else {
      visibility = model.quotes.PublicQuote.getInstance();
    }
  }

  public void makeRepositoryPublic(final User user) {

    visibility = model.quotes.PublicQuote.getInstance();
  }

  public void makeRepositoryPrivate(final User user) {

    visibility = model.quotes.PrivateQuote.getInstance();
  }

  public void addCollaborator(final User user, final User newCollab) {

    collaborators = SetUtil.union(Utils.copy(collaborators), SetUtil.set(newCollab));
  }

  public void removeCollaborator(final User user, final User oldCollab) {

    collaborators = SetUtil.diff(Utils.copy(collaborators), SetUtil.set(oldCollab));
  }

  public VDMSet getBranchesNames() {

    VDMSet names = SetUtil.set();
    for (Iterator iterator_14 = branches.iterator(); iterator_14.hasNext(); ) {
      Branch b = (Branch) iterator_14.next();
      names = SetUtil.union(Utils.copy(names), SetUtil.set(b.getName()));
    }
    return Utils.copy(names);
  }

  public void addBranch(final User user, final Branch branch) {

    branches = SetUtil.union(Utils.copy(branches), SetUtil.set(branch));
  }

  public void deleteBranch(final User user, final Branch branch) {

    branches = SetUtil.diff(Utils.copy(branches), SetUtil.set(branch));
  }

  public void mergeBranches(
      final User user, final Branch branchToMergeTo, final Branch branchMerged) {

    deleteBranch(user, branchMerged);
    for (Iterator iterator_15 = SeqUtil.elems(branchMerged.getCommits()).iterator();
        iterator_15.hasNext();
        ) {
      Commit c = (Commit) iterator_15.next();
      branchToMergeTo.addCommit(user, c);
      c.setBranch(branchToMergeTo);
    }
  }

  public Repository() {}

  public String toString() {

    return "Repository{"
        + "owner := "
        + Utils.toString(owner)
        + ", name := "
        + Utils.toString(name)
        + ", collaborators := "
        + Utils.toString(collaborators)
        + ", branches := "
        + Utils.toString(branches)
        + ", visibility := "
        + Utils.toString(visibility)
        + "}";
  }
}
