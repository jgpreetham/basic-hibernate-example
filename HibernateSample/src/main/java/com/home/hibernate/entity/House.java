package com.home.hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author preetham
 */
@Entity
public class House
{
   @Id
   @GeneratedValue( strategy = GenerationType.AUTO )
   private int    id;
   private String name;
   @ManyToOne
   @JoinColumn( name = "OWNER_ID" )
   private Owner  owner; // mappedBy

   public int getId()
   {
      return id;
   }

   public void setId( int id )
   {
      this.id = id;
   }

   public Owner getOwner()
   {
      return owner;
   }

   public void setOwner( Owner owner )
   {
      this.owner = owner;
   }

   public String getName()
   {
      return name;
   }

   public void setName( String name )
   {
      this.name = name;
   }

   public House()
   {
      // TODO Auto-generated constructor stub
   }

   public House( String name )
   {
      this.name = name;
   }

}
