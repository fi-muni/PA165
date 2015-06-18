package cz.fi.muni.pa165.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
//In Derby, its forbiden to 'USER' is reserved keyword, we need to rename table 
@Table(name="Users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String passwordHash;
	
	@Column(nullable=false,unique=true)
	@Pattern(regexp=".+@.+\\....?")
	@NotNull
	private String email;
	@NotNull
	private String givenName;
	@NotNull
	private String surname;
	
	@Pattern(regexp="\\+?\\d+")
	private String phone;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date joinedDate;

	@OneToMany(mappedBy="user")
	private List<ProductComment> comments = new ArrayList<>();

	public long getId() {
		return id;
	}

	public List<ProductComment> getComments() {
		return Collections.unmodifiableList(comments);
	}
	
	protected void addComment(ProductComment c) {
		comments.add(c);
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getGivenName() {
		return givenName;
	}


	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
	
}
