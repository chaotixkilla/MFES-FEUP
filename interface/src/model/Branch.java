package model;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Branch {
  private String name;
  private Repository repository;
  private VDMSeq commits = SeqUtil.seq();

  public void cg_init_Branch_1(final Repository repo, final String branchName) {

    name = branchName;
    repository = repo;
    return;
  }

  public Branch(final Repository repo, final String branchName) {

    cg_init_Branch_1(repo, branchName);
  }

  public Repository getRepository() {

    return repository;
  }

  public String getName() {

    return name;
  }

  public VDMSeq getCommits() {

    return Utils.copy(commits);
  }

  public void addCommit(final User user, final Commit commit) {

    commits = SeqUtil.conc(Utils.copy(commits), SeqUtil.seq(commit));
  }

  public Branch() {}

  public String toString() {

    return "Branch{"
        + "name := "
        + Utils.toString(name)
        + ", repository := "
        + Utils.toString(repository)
        + ", commits := "
        + Utils.toString(commits)
        + "}";
  }
}
