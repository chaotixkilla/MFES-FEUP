package model;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Commit {
  private User committer;
  private Branch branch;
  private String identifier;
  private String message;

  public void cg_init_Commit_1(
      final User user,
      final Branch destinationBranch,
      final String id,
      final String commitMessage) {

    committer = user;
    branch = destinationBranch;
    identifier = id;
    message = commitMessage;
    return;
  }

  public Commit(
      final User user,
      final Branch destinationBranch,
      final String id,
      final String commitMessage) {

    cg_init_Commit_1(user, destinationBranch, id, commitMessage);
  }

  public User getCommitter() {

    return committer;
  }

  public Branch getBranch() {

    return branch;
  }

  public String getIdentifier() {

    return identifier;
  }

  public String getMessage() {

    return message;
  }

  public void setBranch(final Branch newBranch) {

    branch = newBranch;
  }

  public Commit() {}

  public String toString() {

    return "Commit{"
        + "committer := "
        + Utils.toString(committer)
        + ", branch := "
        + Utils.toString(branch)
        + ", identifier := "
        + Utils.toString(identifier)
        + ", message := "
        + Utils.toString(message)
        + "}";
  }
}
