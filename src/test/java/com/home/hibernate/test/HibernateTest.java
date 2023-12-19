package com.home.hibernate.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LazyInitializationException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.home.hibernate.entity.Address;
import com.home.hibernate.entity.Car;
import com.home.hibernate.entity.CarOwner;
import com.home.hibernate.entity.FourWheeler;
import com.home.hibernate.entity.House;
import com.home.hibernate.entity.Insurance;
import com.home.hibernate.entity.Owner;
import com.home.hibernate.entity.Person;
import com.home.hibernate.entity.Phone;
import com.home.hibernate.entity.TwoWheeler;
import com.home.hibernate.entity.Vehicle;

/**
 * @author preetham
 */
public class HibernateTest
{
   private static SessionFactory sessionFactory = null;

   @BeforeClass
   public static void setUp() throws Exception
   {
      sessionFactory = new Configuration().configure().buildSessionFactory();
   }

   @AfterClass
   public static void tearDown() throws Exception
   {
      sessionFactory.close();
   }

   @Test
   public void testSaveOperation()
   {
      System.out.println( "testSaveOperation begins ........ This is \"C\" of CRUD" );
      Person person = new Person( "James", "Bond", "007@jamesbond.com", new java.util.Date() );
      Person person2 = new Person( "Forest", "Gump", "forestgump@jamesbond.com", new java.util.Date() );

      Session session = sessionFactory.openSession();
      session.beginTransaction();

      session.save( person );
      session.save( person2 );

      session.getTransaction().commit();
      session.close();
      System.out.println( "testSaveOperation ends ......." );

   }

   @Test
   public void testRetriveOnePerson()
   {
      System.out.println( "testRetriveOnePerson begins .......This is \"R\" of CRUD" );
      testSaveOperation();
      Session session = sessionFactory.openSession();
      session.beginTransaction();

      Person p = session.get( Person.class, 1L );

      session.getTransaction().commit();
      System.out.println( "Retrieved person from DB is " + p );
      session.close();
      assertEquals( 1L, p.getId() );
      System.out.println( "testRetriveOnePerson ends ......." );

   }

   @Test
   public void testUpdateOnePerson()
   {
      System.out.println( "testRetriveOnePerson begins .......This is \"U\" of CRUD" );
      testSaveOperation();
      Session session = sessionFactory.openSession();
      session.beginTransaction();

      Person p = session.get( Person.class, 1L );
      System.out.println( "Retrieved person from DB is " + p );
      p.setFirstName( "Changing the name" );
      session.update( p );
      session.getTransaction().commit();
      System.out.println( "Updated the person, so retrieve one more time to confirm" );
      Person p2 = session.get( Person.class, 1L );
      System.out.println( "Retrieved person from DB after updation is " + p2 );
      session.close();
      assertEquals( 1L, p.getId() );
      System.out.println( "testRetriveOnePerson ends ......." );

   }

   @Test
   public void testDeletePerson()
   {
      System.out.println( "testRetriveOnePerson begins .......This is \"D\" of CRUD" );
      testSaveOperation();
      Session session = sessionFactory.openSession();
      session.beginTransaction();

      Person p = session.get( Person.class, 1L );
      System.out.println( "Retrieved person from DB is " + p );
      session.delete( p );
      session.getTransaction().commit();
      System.out.println( "Deleted the person, so retrieve one more time to confirm" );
      Person p2 = session.get( Person.class, 1L );
      System.out.println( "Retrieved person from DB after deletion is " + p2 );
      session.close();
      assertNull( p2 );
      System.out.println( "testRetriveOnePerson ends ......." );

   }

   @Test
   public void testEmbeddedObjectWithAttributeOverride()
   {
      System.out.println( "testEmbeddedObjectWithAttributeOverride begins" );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Person person = new Person( "James", "Bond", "007@jamesbond.com", new java.util.Date() );

      Address addr1 = new Address( "Baker street", "Bangalore", "Karnataka", "560034" );
      Address addr2 = new Address( "Walker street", "Chennai", "Tamil Nadu", "560021" );

      person.setHomeAddress( addr1 );
      person.setOfficeAddress( addr2 );

      session.save( person );
      session.getTransaction().commit();
      session.close();
      System.out.println( "testEmbeddedObjectWithAttributeOverride ends ......." );

   }

   @Test
   public void testElementCollection()
   {
      System.out.println( "testElementCollection begins" );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Person person = new Person( "James", "Bond", "007@jamesbond.com", new java.util.Date() );

      Insurance insurance = new Insurance( "ABC", "400" );
      Insurance insurance1 = new Insurance( "XYZ", "500" );
      person.getInsurances().add( insurance );
      person.getInsurances().add( insurance1 );

      session.save( person );
      session.getTransaction().commit();
      session.close();
      System.out.println( "testElementCollection ends ......." );

   }

   @Test( expected = LazyInitializationException.class )
   public void testLazyLoading()
   {
      System.out.println( "testLazyLoading begins ......." );
      testElementCollection();
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Person p = session.get( Person.class, 1L );
      session.getTransaction().commit();
      session.close();
      System.out.println( "Test case is passed if the LazyInitializationException occurs, if there is no expception next line will have the list of insurances" );
      System.out.println( p.getInsurances() );
      System.out.println( "testLazyLoading ends ......." );

   }

   @Test
   public void testOneToOneMapping()
   {
      System.out.println( "testOneToOne begins" );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Person person = new Person( "James", "Bond", "007@jamesbond.com", new java.util.Date() );

      Vehicle vehicle = new Vehicle( "Ferrari" );
      person.setVehicle( vehicle );
      session.save( vehicle );
      session.save( person );
      session.getTransaction().commit();
      session.close();
      System.out.println( "testOneToOne ends ......." );

   }

   @Test
   public void testOneToManyMapping()
   {
      System.out.println( "testOneToManyMapping begins" );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Person person = new Person( "James", "Bond", "007@jamesbond.com", new java.util.Date() );
      Phone p = new Phone( "samsung" );
      Phone p2 = new Phone( "Apple" );
      person.getPhones().add( p );
      person.getPhones().add( p2 );
      session.save( p );
      session.save( p2 );
      session.save( person );
      session.getTransaction().commit();
      session.close();
      System.out.println( "testOneToManyMapping ends ......." );

   }

   @Test
   public void testManyToOneMapping()
   {
      System.out.println( "testManyToOneMapping begins" );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      House h1 = new House( "house1" );
      House h2 = new House( "house2" );
      Owner o1 = new Owner( "Sherlock" );

      o1.getHouse().add( h1 );
      o1.getHouse().add( h2 );
      h1.setOwner( o1 );
      h2.setOwner( o1 );

      session.save( o1 );
      session.save( h1 );
      session.save( h2 );
      session.getTransaction().commit();
      session.close();
      System.out.println( "testManyToOneMapping ends ......." );

   }

   @Test
   public void testManyToManyMapping()
   {
      System.out.println( "testManyToManyMapping begins" );
      Session session = sessionFactory.openSession();
      session.beginTransaction();

      Car c = new Car( "Ferrari" );
      Car c1 = new Car( "BMW" );

      CarOwner o1 = new CarOwner( "owner1" );
      CarOwner o2 = new CarOwner( "owner2" );

      c.getOwners().add( o1 );
      c.getOwners().add( o2 );
      c1.getOwners().add( o1 );
      c1.getOwners().add( o2 );

      o1.getCars().add( c );
      o1.getCars().add( c1 );
      o2.getCars().add( c );
      o2.getCars().add( c1 );

      session.persist( o1 );// cascading on in entity class(CarOwner)
      session.persist( o2 );// cascading on in entity class(CarOwner)
      session.getTransaction().commit();
      session.close();
      System.out.println( "testManyToManyMapping ends ......." );

   }

   @Test
   public void testSingleTableStrategy()
   {
      System.out.println( "testSingleTableStrategy begins. Uncomment @Inheritance(strategy=InheritanceType.SINGLE_TABLE) in Vehicle class" );
      Session session = sessionFactory.openSession();
      session.beginTransaction();

      Vehicle v = new Vehicle( "Ferrari" );
      TwoWheeler bike = new TwoWheeler();
      bike.setSteeringHandle( "bike steering handle" );
      bike.setName( "pulsar" );

      FourWheeler car = new FourWheeler();
      car.setName( "alto" );
      car.setSteeringWheel( "car steering wheel" );

      session.save( car );
      session.save( bike );
      session.save( v );
      session.getTransaction().commit();
      session.close();
      System.out.println( "testSingleTableStrategy ends ......." );

   }

   @Test
   public void testTablePerClassStrategy()
   {
      System.out.println( "testTablePerClassStrategy begins. Uncomment @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) in Vehicle class " );
      Session session = sessionFactory.openSession();
      session.beginTransaction();

      Vehicle v = new Vehicle( "Ferrari" );
      TwoWheeler bike = new TwoWheeler();
      bike.setSteeringHandle( "bike steering handle" );
      bike.setName( "pulsar" );

      FourWheeler car = new FourWheeler();
      car.setName( "alto" );
      car.setSteeringWheel( "car steering wheel" );

      session.save( car );
      session.save( bike );
      session.save( v );
      session.getTransaction().commit();
      session.close();
      System.out.println( "testTablePerClassStrategy ends ......." );

   }

   @Test
   public void testTableJoinedStrategy()
   {
      System.out.println( "testTableJoinedStrategy begins. Uncomment @Inheritance(strategy=InheritanceType.JOINED) in Vehicle class " );
      Session session = sessionFactory.openSession();
      session.beginTransaction();

      Vehicle v = new Vehicle( "Ferrari" );
      TwoWheeler bike = new TwoWheeler();
      bike.setSteeringHandle( "bike steering handle" );
      bike.setName( "pulsar" );

      FourWheeler car = new FourWheeler();
      car.setName( "alto" );
      car.setSteeringWheel( "car steering wheel" );

      session.save( car );
      session.save( bike );
      session.save( v );
      session.getTransaction().commit();
      session.close();
      System.out.println( "testTableJoinedStrategy ends ......." );

   }

   @Test
   public void testPersistentObject()
   {
      System.out.println( "testPersistentObject begins ......." );
      Person person = new Person( "James", "Bond", "007@jamesbond.com", new java.util.Date() );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      System.out.println( "Savin the object with state " + person );
      session.save( person );
      System.out.println( "Saved the object and now updating using setter method" );
      person.setFirstName( "updated the name" );
      System.out.println( "The upadted object after saving is " + person );
      session.getTransaction().commit();
      session.close();
      session = sessionFactory.openSession();
      session.beginTransaction();
      Person p2 = session.get( Person.class, 1L );
      System.out.println( "Retrieved object is " + p2 );
      assertEquals( person.getFirstName(), p2.getFirstName() );
      System.out.println( "testPersistentObject ends ......." );

   }

   @Test
   public void testDetachedObject()
   {
      System.out.println( "testDetachedObject begins ......." );
      Person person = new Person( "James", "Bond", "007@jamesbond.com", new java.util.Date() );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      System.out.println( "Savin the object with state " + person );
      session.save( person );
      session.getTransaction().commit();
      session.close();
      System.out.println( "Saved the object and closed the session and now updating using setter method" );
      person.setFirstName( "updated the name" );
      System.out.println( "The upadted object after saving and closing the session is " + person );
      session = sessionFactory.openSession();
      session.beginTransaction();
      Person p2 = session.get( Person.class, 1L );
      System.out.println( "Retrieved object is " + p2 );
      assertNotEquals( person.getFirstName(), p2.getFirstName() );
      System.out.println( "testDetachedObject ends ......." );

   }

   @Test
   public void testTransientObject()
   {
      System.out.println( "testTransientObject begins ......" );
      testSaveOperation();
      Session session = sessionFactory.openSession();
      session.beginTransaction();

      Person p = session.get( Person.class, 1L );
      System.out.println( "Retrieved person from DB is " + p );
      session.delete( p );
      session.getTransaction().commit();
      System.out.println( "Deleted the person, so retrieve one more time to confirm. After deleteing the object becomes transient" );
      p.setFirstName( "Deleted Object name" );
      Person p2 = session.get( Person.class, 1L );
      System.out.println( "Retrieved person from DB after deletion is " + p2 );
      session.close();
      assertEquals( "Deleted Object name", p.getFirstName() );
      assertNull( p2 );
      System.out.println( "testTransientObject ends ......." );

   }

   @Test
   public void testPersistingDetachedObject()
   {
      System.out.println( "testPersistingDetachedObject begins .......This is \"R\" of CRUD" );
      Person person = new Person( "James", "Bond", "007@jamesbond.com", new java.util.Date() );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      System.out.println( "Savin the object with state " + person );
      session.save( person );
      session.getTransaction().commit();
      session.close();
      System.out.println( "Saved the object and closed the session and now updating using setter method. It's detached object now" );
      person.setFirstName( "updated the name" );
      System.out.println( "The upadted object after saving and closing the session is " + person );
      session = sessionFactory.openSession();
      session.beginTransaction();
      session.update( person );
      System.out.println( "Updated the object, so now it's persistent object" );
      person.setEmail( "udpated@email.com" );
      System.out.println( "Updated the object email id" + person );
      person.setEmail( "udpated2@email.com" );
      System.out.println( "Updated the object email id 2nd time" + person );
      Person p3 = session.get( Person.class, 1L );
      System.out.println( "Got the object from db " + p3 );
      assertEquals( person.getEmail(), p3.getEmail() );
      session.getTransaction().commit();
      session.close();
      System.out.println( "testPersistingDetachedObject ends ......." );

   }

   @Test
   public void testHQL()
   {
      System.out.println( "testHQL begins .......This is \"R\" of CRUD" );
      testSaveOperation();
      Session session = sessionFactory.openSession();
      session.beginTransaction();

      Query query = session.createQuery( "from Person where id>:personId" );
      long i = 0;
      query.setLong( "personId", i );
      List<Person> list = (List<Person>) query.list();

      session.getTransaction().commit();
      System.out.println( "Size of list is " + list.size() );
      assertEquals( 2, list.size() );
      session.close();
      System.out.println( "testHQL ends ......." );

   }

   @Test
   public void testNamedQueries()
   {
      System.out.println( "testNamedQueries begins ......." );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Phone p = new Phone( "samsung" );
      Phone p2 = new Phone( "apple" );
      session.save( p );
      session.save( p2 );
      session.getTransaction().commit();
      Query query = session.getNamedQuery( "Phone.byName" );
      query.setString( 1, "apple" );
      List<Phone> list = (List<Phone>) query.list();
      System.out.println( "Size of list is " + list.size() );
      assertEquals( 1, list.size() );
      session.close();
      System.out.println( "testNamedQueries ends ......." );

   }

   @Test
   public void testNamedNativeQueries()
   {
      System.out.println( "testNamedNativeQueries begins ......." );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Phone p = new Phone( "samsung" );
      Phone p2 = new Phone( "apple" );
      session.save( p );
      session.save( p2 );
      session.getTransaction().commit();
      Query query = session.getNamedQuery( "Phone.byId" );
      query.setInteger( "id", 1 );
      List<Phone> list = (List<Phone>) query.list();
      System.out.println( "Size of list is " + list.size() );
      assertEquals( 1, list.size() );
      session.close();
      System.out.println( "testNamedNativeQueries ends ......." );

   }

   @Test
   public void testCriteria()
   {
      System.out.println( "testNamedNativeQueries begins ......." );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Phone p = new Phone( "samsung" );
      Phone p2 = new Phone( "apple" );
      session.save( p );
      session.save( p2 );
      session.getTransaction().commit();

      Criteria criteria = session.createCriteria( Phone.class );
      criteria.add( Restrictions.or( Restrictions.eq( "name", "apple" ), Restrictions.between( "id", 1, 5 ) ) );

      List<Phone> list = (List<Phone>) criteria.list();
      System.out.println( "Size of list is " + list.size() );
      assertEquals( 2, list.size() );
      session.close();
      System.out.println( "testNamedNativeQueries ends ......." );

   }

   @Test
   public void testProjections()
   {
      System.out.println( "testProjections begins ......." );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Phone p = new Phone( "samsung" );
      Phone p2 = new Phone( "apple" );
      session.save( p );
      session.save( p2 );
      session.getTransaction().commit();

      Criteria criteria = session.createCriteria( Phone.class );
      criteria.setProjection( Projections.property( "id" ) ).addOrder( Order.desc( "id" ) );

      List<String> list = (List<String>) criteria.list();
      System.out.println( "Size of list is " + list.size() );
      assertEquals( 2, list.size() );
      session.close();
      System.out.println( "testProjections ends ......." );

   }

   @Test
   public void testExample()
   {
      System.out.println( "testExample begins ......." );
      Session session = sessionFactory.openSession();
      session.beginTransaction();
      Phone p = new Phone( "samsung" );
      Phone p2 = new Phone( "apple" );
      session.save( p );
      session.save( p2 );
      session.getTransaction().commit();

      Phone p3 = new Phone( "apple" );

      Example ex = Example.create( p3 );
      Criteria criteria = session.createCriteria( Phone.class );
      criteria.add( ex );
      List<Phone> list = (List<Phone>) criteria.list();
      System.out.println( "Size of list is " + list.size() );
      assertEquals( 1, list.size() );
      session.close();
      System.out.println( "testExample ends ......." );

   }

}
