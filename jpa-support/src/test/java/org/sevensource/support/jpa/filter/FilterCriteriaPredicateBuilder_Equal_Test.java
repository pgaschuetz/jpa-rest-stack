package org.sevensource.support.jpa.filter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sevensource.support.jpa.configuration.JpaTestConfiguration;
import org.sevensource.support.jpa.filter.domain.Customer;
import org.sevensource.support.jpa.filter.domain.CustomerRepository;
import org.sevensource.support.jpa.filter.domain.CustomerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = JpaTestConfiguration.class)
@EntityScan(basePackageClasses=Customer.class)
@EnableJpaRepositories(basePackageClasses=CustomerRepository.class)
public class FilterCriteriaPredicateBuilder_Equal_Test {

	Customer person1;
	Customer person2;
	Customer person3;
	Customer company;
	Customer enterprise;
	
	final Instant instant0 = Instant.ofEpochMilli(0);
	final Instant instant2000 = ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneId.of("Z")).toInstant();
	
	final ZonedDateTime dateTime1900 = ZonedDateTime.of(1900, 12, 24, 20, 0, 0, 0, ZoneId.of("Z"));
	final ZonedDateTime dateTime2000 = ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneId.of("Z"));
	
	@Before
	public void before() {
		
		repository.deleteAllInBatch();
		person1 = repository.save(new Customer("John", "Doe", CustomerType.PERSON, 35, instant0, dateTime1900));
		person2 = repository.save(new Customer("Mary", "Blige", CustomerType.PERSON, 35, instant0, dateTime1900));
		person3 = repository.save(new Customer(null, "Marks", null, 15, null, null));
		company = repository.save(new Customer("Michael", "Huntington", CustomerType.COMPANY, 50, instant2000, dateTime2000));
		enterprise = repository.save(new Customer("Bill", "Gates", CustomerType.COMPANY, 65, instant2000, dateTime2000));
		repository.flush();
	}
	
	@Autowired
	CustomerRepository repository;
	
	FilterCriteriaPredicateBuilder<Customer> builder(FilterCriteria criteria) {
		return new FilterCriteriaPredicateBuilder<>(criteria);
	}
	
	@Test
	public void string_prop_equal_to() {		
		FilterCriteria criteria = new ComparisonFilterCriteria("firstname", ComparisonFilterOperator.EQUAL_TO, "John");
		FilterCriteriaPredicateBuilder<Customer> builder = builder(criteria);
		assertThat(repository.findAll(builder)).hasSize(1);
	}

	@Test
	public void string_prop_equal_to_with_null_arg() {		
		FilterCriteria criteria = new ComparisonFilterCriteria("firstname", ComparisonFilterOperator.EQUAL_TO, null);
		FilterCriteriaPredicateBuilder<Customer> builder = builder(criteria);
		assertThat(repository.findAll(builder)).hasSize(1);
	}
		
	@Test
	public void integer_prop_equal_to() {		
		FilterCriteria criteria = new ComparisonFilterCriteria("age", ComparisonFilterOperator.EQUAL_TO, 35);
		FilterCriteriaPredicateBuilder<Customer> builder = builder(criteria);
		assertThat(repository.findAll(builder)).hasSize(2);
	}
	
	@Test
	public void integer_prop_equal_to_with_string_arg() {		
		FilterCriteria criteria = new ComparisonFilterCriteria("age", ComparisonFilterOperator.EQUAL_TO, "35");
		FilterCriteriaPredicateBuilder<Customer> builder = builder(criteria);
		assertThat(repository.findAll(builder)).hasSize(2);
	}
	
	@Test
	public void integer_prop_equal_to_with_long_arg() {		
		FilterCriteria criteria = new ComparisonFilterCriteria("age", ComparisonFilterOperator.EQUAL_TO, 35L);
		FilterCriteriaPredicateBuilder<Customer> builder = builder(criteria);
		assertThat(repository.findAll(builder)).hasSize(2);
	}
	
	@Test
	public void integer_prop_equal_to_with_null_arg() {		
		FilterCriteria criteria = new ComparisonFilterCriteria("age", ComparisonFilterOperator.EQUAL_TO, null);
		FilterCriteriaPredicateBuilder<Customer> builder = builder(criteria);
		assertThat(repository.findAll(builder)).hasSize(0);
	}
	
	@Test
	public void enum_prop_equal_to() {
		FilterCriteria criteria = new ComparisonFilterCriteria("customerType", ComparisonFilterOperator.EQUAL_TO, CustomerType.PERSON);
		FilterCriteriaPredicateBuilder<Customer> builder = builder(criteria);
		assertThat(repository.findAll(builder)).hasSize(2);
	}
	
	@Test
	public void enum_prop_equal_to_with_null_arg() {
		FilterCriteria criteria = new ComparisonFilterCriteria("customerType", ComparisonFilterOperator.EQUAL_TO, null);
		FilterCriteriaPredicateBuilder<Customer> builder = builder(criteria);
		assertThat(repository.findAll(builder)).hasSize(1);
	}


	@Test
	public void instant_prop_equal_to() {		
		FilterCriteria criteria = new ComparisonFilterCriteria("registered", ComparisonFilterOperator.EQUAL_TO, instant0);
		FilterCriteriaPredicateBuilder<Customer> builder = builder(criteria);
		assertThat(repository.findAll(builder)).hasSize(2);
	}
	
	@Test
	public void string_prop_notequal_to() {		
		FilterCriteria criteria = new ComparisonFilterCriteria("firstname", ComparisonFilterOperator.NOT_EQUAL_TO, "John");
		FilterCriteriaPredicateBuilder<Customer> builder = builder(criteria);
		assertThat(repository.findAll(builder)).hasSize(4);
	}
	
	@Test
	public void string_prop_notequal_to_with_null_arg() {		
		FilterCriteria criteria = new ComparisonFilterCriteria("firstname", ComparisonFilterOperator.NOT_EQUAL_TO, null);
		FilterCriteriaPredicateBuilder<Customer> builder = builder(criteria);
		assertThat(repository.findAll(builder)).hasSize(4);
	}
	
	@Test
	public void enum_prop_not_equal_to() {
		FilterCriteria criteria = new ComparisonFilterCriteria("customerType", ComparisonFilterOperator.NOT_EQUAL_TO, CustomerType.PERSON);
		FilterCriteriaPredicateBuilder<Customer> builder = builder(criteria);
		assertThat(repository.findAll(builder)).hasSize(3);
	}
	
	@Test
	public void enum_prop_not_equal_to_with_null_arg() {
		FilterCriteria criteria = new ComparisonFilterCriteria("customerType", ComparisonFilterOperator.NOT_EQUAL_TO, null);
		FilterCriteriaPredicateBuilder<Customer> builder = builder(criteria);
		assertThat(repository.findAll(builder)).hasSize(4);
	}
}
