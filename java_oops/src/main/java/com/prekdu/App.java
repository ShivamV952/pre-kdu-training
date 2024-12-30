/** Provides classes for managing library resources and members. */
package com.prekdu;

import java.util.ArrayList;
import java.util.List;

/** Represents the status of a library resource. */
enum ResourceStatus {
  /** The resource is available for borrowing. */
  AVAILABLE,

  /** The resource has been borrowed by a library member. */
  BORROWED,

  /** The resource is reserved by a library member. */
  RESERVED
}

/** Represents the types of membership. */
enum MembershipType {
  /** Standard membership type. */
  STANDARD,

  /** Premium membership type. */
  PREMIUM
}

/** Represents the format of digital content. */
enum ContentFormat {
  /** PDF format for digital content. */
  PDF,

  /** EPUB format for digital content. */
  EPUB
}

interface Renewable {
  boolean renewLoan(LibraryMember member);
}

interface Reservable {
  void reserve(LibraryMember member);

  void cancelReservation(LibraryMember member);
}

/** Represents a library resource. */
abstract class LibraryResource {

  /** The ID of the resource. */
  private String resourceId;

  /** The title of the resource. */
  private String title;

  /** The current status of the resource. */
  private ResourceStatus status;

  /**
   * Constructor to initialize a library resource with an ID and title.
   *
   * @param id The ID of the resource.
   * @param t The title of the resource.
   */
  LibraryResource(final String id, final String t) {
    this.resourceId = id;
    this.title = t;
    this.status = ResourceStatus.AVAILABLE;
  }

  /**
   * @return The resource ID.
   */
  public String getResourceId() {
    return resourceId;
  }

  /**
   * @param id The resource ID to set.
   */
  public void setResourceId(final String id) {
    this.resourceId = id;
  }

  /**
   * @return The title of the resource.
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param t The title to set.
   */
  public void setTitle(final String t) {
    this.title = t;
  }

  /**
   * @return The current status of the resource.
   */
  public ResourceStatus getStatus() {
    return status;
  }

  /**
   * @param s The status to set.
   */
  public void setStatus(final ResourceStatus s) {
    this.status = s;
  }

  /**
   * Calculates the late fee.
   *
   * @param daysLate The number of days the resource is overdue.
   * @return The fee amount
   */
  public abstract double calculateLateFee(int daysLate);

  /**
   * @return The maximum loan period of the resource.
   */
  public abstract int getMaxLoanPeriod();
}

class Book extends LibraryResource implements Reservable, Renewable {
  /** The fee charged per day for a late return of a resource. */
  private static final double LATE_FEE_PER_DAY = 0.5;

  /** The maximum number of days a resource can be borrowed. */
  private static final int MAX_LOAN_PERIOD_DAYS = 14;

  /** The author of the book. */
  private String author;

  /** The ISBN of the book. */
  private String isbn;

  /** The library member who has reserved the book. */
  private LibraryMember reservedBy;

  Book(final String id, final String t, final String a, final String i) {
    super(id, t);
    this.author = a;
    this.isbn = i;
  }

  @Override
  public double calculateLateFee(final int daysLate) {
    return LATE_FEE_PER_DAY * daysLate;
  }

  @Override
  public int getMaxLoanPeriod() {
    return MAX_LOAN_PERIOD_DAYS; // 14 days
  }

  @Override
  public void reserve(final LibraryMember member) {
    if (this.getStatus() == ResourceStatus.BORROWED && reservedBy == null) {
      this.setStatus(ResourceStatus.RESERVED);
      reservedBy = member;
    } else if (this.getStatus() == ResourceStatus.RESERVED
        || this.getStatus() == ResourceStatus.AVAILABLE) {
      throw new IllegalStateException("Resource cannot be reserved");
    }
  }

  @Override
  public void cancelReservation(final LibraryMember member) {
    if (reservedBy != null && reservedBy.equals(member)) {
      reservedBy = null;
      this.setStatus(ResourceStatus.AVAILABLE);
    }
  }

  @Override
  public boolean renewLoan(final LibraryMember member) {
    return this.getStatus() == ResourceStatus.BORROWED;
  }

  /**
   * @return The author of the book.
   */
  public String getAuthor() {
    return author;
  }

  /**
   * @param a The author to set.
   */
  public void setAuthor(final String a) {
    this.author = a;
  }

  /**
   * @return The ISBN of the book.
   */
  public String getIsbn() {
    return isbn;
  }

  /**
   * @param i The ISBN to set.
   */
  public void setIsbn(final String i) {
    this.isbn = i;
  }
}

class DigitalContent extends LibraryResource implements Renewable {
  // Define constants at the class level
  /** The fee charged per day for a late return of a resource. */
  private static final double LATE_FEE_PER_DAY = 0.25;

  /** The maximum number of days a resource can be borrowed. */
  private static final int MAX_LOAN_PERIOD_DAYS = 7;

  /** The size of the digital content file, in megabytes (MB). */
  private double fileSize;

  /** The format of the digital content (e.g., PDF, EPUB). */
  private ContentFormat format;

  DigitalContent(
      final String resourceId,
      final String title,
      final double fileSiz,
      final ContentFormat formt) {
    super(resourceId, title);
    this.fileSize = fileSiz;
    this.format = formt;
  }

  @Override
  public double calculateLateFee(final int daysLate) {
    return LATE_FEE_PER_DAY * daysLate;
  }

  @Override
  public int getMaxLoanPeriod() {
    return MAX_LOAN_PERIOD_DAYS; // 7 days
  }

  @Override
  public boolean renewLoan(final LibraryMember member) {
    return true; // Digital content can always be renewed
  }

  /**
   * @return The file size of the digital content.
   */
  public double getFileSize() {
    return fileSize;
  }

  /**
   * @param fs The file size to set.
   */
  public void setFileSize(final double fs) {
    this.fileSize = fs;
  }

  /**
   * @return The format of the digital content.
   */
  public ContentFormat getFormat() {
    return format;
  }

  /**
   * @param ft The format to set.
   */
  public void setFormat(final ContentFormat ft) {
    this.format = ft;
  }
}

class Periodical extends LibraryResource implements Reservable, Renewable {
  // Define constants at the class level
  /** The late fee charged per day for overdue resources (in monetary units). */
  private static final double LATE_FEE_PER_DAY = 0.75;

  /** The maximum number of days a resource can be borrowed. */
  private static final int MAX_LOAN_PERIOD_DAYS = 7;

  /** The issue number of the periodical (e.g., the edition number). */
  private int issueNumber;

  /** The frequency of the periodical (e.g., weekly, monthly). */
  private String frequency;

  Periodical(final String id, final String t, final int isno, final String fq) {
    super(id, t);
    this.issueNumber = isno;
    this.frequency = fq;
  }

  /**
   * Calculates the late fee for a resource based on the number of overdue days.
   *
   * @param daysLate The number of days the resource is overdue.
   * @return The late fee for the resource.
   */
  @Override
  public double calculateLateFee(final int daysLate) {
    return LATE_FEE_PER_DAY * daysLate;
  }

  /**
   * Returns the maximum loan period for the resource.
   *
   * @return The maximum number of days the resource can be borrowed.
   */
  @Override
  public int getMaxLoanPeriod() {
    return MAX_LOAN_PERIOD_DAYS; // 7 days
  }

  @Override
  public void reserve(final LibraryMember member) {
    if (this.getStatus() == ResourceStatus.BORROWED) {
      throw new IllegalStateException("Resource already borrowed");
    }
    this.setStatus(ResourceStatus.RESERVED);
  }

  @Override
  public void cancelReservation(final LibraryMember member) {
    this.setStatus(ResourceStatus.AVAILABLE);
  }

  @Override
  public boolean renewLoan(final LibraryMember member) {
    return this.getStatus() == ResourceStatus.BORROWED;
  }

  /**
   * @return The issue number of the periodical.
   */
  public int getIssueNumber() {
    return issueNumber;
  }

  /**
   * @param isNo The issue number to set.
   */
  public void setIssueNumber(final int isNo) {
    this.issueNumber = isNo;
  }

  /**
   * @return The frequency of the periodical.
   */
  public String getFrequency() {
    return frequency;
  }

  /**
   * @param fq The frequency to set.
   */
  public void setFrequency(final String fq) {
    this.frequency = fq;
  }
}

class LibraryMember {
  /** The maximum number of resources a premium member can borrow. */
  private static final int MAX_PREMIUM_BORROW_LIMIT = 10;

  /** The maximum number of resources a standard member can borrow. */
  private static final int MAX_STANDARD_BORROW_LIMIT = 5;

  /** The unique identifier for the library member. */
  private String memberId;

  /** The membership type of the library member (e.g., PREMIUM, STANDARD). */
  private MembershipType membershipType;

  /** The list of library resources currently borrowed by the member. */
  private List<LibraryResource> borrowedResources;

  LibraryMember(final String id, final MembershipType type) {
    this.memberId = id;
    this.membershipType = type;
    this.borrowedResources = new ArrayList<>();
  }

  /**
   * @return The list of borrowed resources.
   */
  public List<LibraryResource> getBorrowedResources() {
    return borrowedResources;
  }

  /**
   * @return The member ID.
   */
  public String getMemberId() {
    return memberId;
  }

  /**
   * @param id The member ID to set.
   */
  public void setMemberId(final String id) {
    this.memberId = id; // 'this.memberId' refers to the field
  }

  private int getMaxBorrowLimit() {
    return membershipType == MembershipType.PREMIUM
        ? MAX_PREMIUM_BORROW_LIMIT
        : MAX_STANDARD_BORROW_LIMIT;
  }

  public void borrowResource(final LibraryResource resource) {
    if (borrowedResources.size() >= getMaxBorrowLimit()) {
      throw new MaximumLoanExceededException("Maximum loan limit reached");
    }
    if (resource.getStatus() == ResourceStatus.AVAILABLE
        || resource.getStatus() == ResourceStatus.RESERVED) {
      if (resource.getStatus() == ResourceStatus.RESERVED) {
        throw new ResourceNotAvailableException("Resource is reserved");
      }
      resource.setStatus(ResourceStatus.BORROWED);
      borrowedResources.add(resource);
    } else {
      throw new ResourceNotAvailableException("Resource is not available ");
    }
  }

  public void returnResource(final LibraryResource resource) {
    borrowedResources.remove(resource);
    resource.setStatus(ResourceStatus.AVAILABLE);
  }
}

class ResourceNotAvailableException extends RuntimeException {
  ResourceNotAvailableException(final String message) {
    super(message);
  }
}

class MaximumLoanExceededException extends RuntimeException {
  MaximumLoanExceededException(final String message) {
    super(message);
  }
}

class InvalidMembershipException extends RuntimeException {
  InvalidMembershipException(final String message) {
    super(message);
  }
}
