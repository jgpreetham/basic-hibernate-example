package com.home.hibernate.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author preetham
 */
@Entity
@Table( name = "PERSON" )
public class Person
{
   @Id
   @GeneratedValue( strategy = GenerationType.AUTO )
   @Column( name = "id" )
   private long                  id;
   @Column( name = "FIRST_NAME" )
   private String                firstName;
   @Column( name = "LAST_NAME" )
   private String                lastName;
   @Column( name = "EMAIL" )
   private String                email;
   @Column( name = "JOINED_DATE" )
   @Temporal( TemporalType.DATE )
   private Date                  joinedDate;

   @Embedded
   @AttributeOverrides( {

   @AttributeOverride( name = "street", column = @Column( name = "OFFICE_STREET" ) ), @AttributeOverride( name = "city", column = @Column( name = "OFFICE_CITY" ) ), @AttributeOverride( name = "state", column = @Column( name = "OFFICE_STATE" ) ),
            @AttributeOverride( name = "pincode", column = @Column( name = "OFFICE_PINCODE" ) )

   } )
   private Address               officeAddress;

   @Embedded
   private Address               homeAddress;

   @ElementCollection
   // ( fetch = FetchType.EAGER ) //Uncomment the ( fetch = FetchType.EAGER ) for testing testEagerLoading
   @JoinTable( name = "PERSON_INSURANCES", joinColumns = @JoinColumn( name = "PERSON_ID" ) )
   private Collection<Insurance> insurances = new ArrayList<Insurance>();

   @OneToOne
   @JoinColumn( name = "VEHICLE_ID" )
   private Vehicle               vehicle;

   @OneToMany
   @JoinTable( name = "PERSON_PHONES", joinColumns = @JoinColumn( name = "PERSON_ID" ), inverseJoinColumns = @JoinColumn( name = "PHONE_ID" )

   )
   private Collection<Phone>     phones     = new ArrayList<>();

   public Person()
   {

   }

   public Person( String firstName, String lastName, String email, Date joinedDate )
   {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.joinedDate = joinedDate;

   }

   public Collection<Phone> getPhones()
   {
      return phones;
   }

   public void setPhones( Collection<Phone> phones )
   {
      this.phones = phones;
   }

   public Address getOfficeAddress()
   {
      return officeAddress;
   }

   public void setOfficeAddress( Address officeAddress )
   {
      this.officeAddress = officeAddress;
   }

   public Address getHomeAddress()
   {
      return homeAddress;
   }

   public void setHomeAddress( Address homeAddress )
   {
      this.homeAddress = homeAddress;
   }

   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName( String firstName )
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return lastName;
   }

   public void setLastName( String lastName )
   {
      this.lastName = lastName;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail( String email )
   {
      this.email = email;
   }

   public Date getJoinedDate()
   {
      return joinedDate;
   }

   public void setJoinedDate( Date joinedDate )
   {
      this.joinedDate = joinedDate;
   }

   public long getId()
   {
      return id;
   }

   public void setId( long id )
   {
      this.id = id;
   }

   public Collection<Insurance> getInsurances()
   {
      return insurances;
   }

   public void setInsurances( Collection<Insurance> insurance )
   {
      this.insurances = insurance;
   }

   public Vehicle getVehicle()
   {
      return vehicle;
   }

   public void setVehicle( Vehicle vehicle )
   {
      this.vehicle = vehicle;
   }

   @Override
   public String toString()
   {
      return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", joinedDate=" + joinedDate + ", officeAddress=" + officeAddress + ", homeAddress=" + homeAddress + ", insurances=" + insurances + "]";
   }

}
